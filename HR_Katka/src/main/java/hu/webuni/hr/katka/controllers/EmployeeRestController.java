package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.mapper.EmployeeMapper;
import hu.webuni.hr.katka.models.Employee;
import hu.webuni.hr.katka.services.EmployeeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/employees")
public class EmployeeRestController {

  @Autowired
  EmployeeService employeeService;

  @Autowired
  EmployeeMapper employeeMapper;

  @GetMapping
  public List<EmployeeDto> fetchAllEmployees() {
    List<Employee> employees = employeeService.findAll();
    return employeeMapper.employeesToDtos(employees);
  }

  @GetMapping("/{id}")
  public EmployeeDto getEmployeeById(@PathVariable Long id) {
    Employee employeeById = employeeService.findById(id);
    return employeeMapper.employeeToDto(employeeById);
  }

  @PostMapping
  public EmployeeDto addNewEmployee(@RequestBody @Valid EmployeeDto employee) {
    validateFields(employee, "Employee cannot be null."); //todo: nem jelenik meg az üzenet miért?
    List<Employee> employees = employeeService.findAll();
    employee.setId((long) (employees.size() + 1));
    Employee savedEmployee = employeeService.save(employeeMapper.dtoToEmployee(employee));
    return employeeMapper.employeeToDto(savedEmployee);
  }

  @PutMapping("/{id}")
  public EmployeeDto modifyEmployeeById(@PathVariable Long id,
                                        @RequestBody EmployeeDto employee) {
    validateFields(employee, "Employee cannot be null.");
    validateFields(id, "Id cannot be null!");
    Employee modifiedEmployee = employeeService.modifyEmployee(id, employeeMapper.dtoToEmployee(employee));
    return employeeMapper.employeeToDto(modifiedEmployee);
  }

  @DeleteMapping("{id}")
  public void deleteEmployeeById(@PathVariable Long id) {
    validateFields(id, "Id cannot be null!");
    employeeService.delete(id);
  }

  @GetMapping("/overLimit")
  public Map<String, List<EmployeeDto>> getEmployeeBySalary(@RequestParam Integer limit) {
    Map<String, List<EmployeeDto>> employeesOverLimit = new HashMap<>();
    List<Employee> employeesByLimit = employeeService.getEmployeesOverLimit(limit);
    employeesOverLimit.put("Employees over limit", employeeMapper.employeesToDtos(employeesByLimit));
    return employeesOverLimit;
  }


  @PostMapping("/payRaise")
  public int getPayRaisePercent(@RequestBody EmployeeDto employee) {
    Employee convertedEmployee = employeeMapper.dtoToEmployee(employee);
    return employeeService.getPayRaisePercent(convertedEmployee);
  }

  private void validateFields(Object o, String message) {
    if (o instanceof String) {
      if (((String) o).isEmpty()) {
        throw new IllegalArgumentException(message);
      }
    }
    if (o == null) {
      throw new IllegalArgumentException(message);
    }
  }


}
