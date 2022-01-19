package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.mapper.EmployeeMapper;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.services.EmployeeService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    Employee savedEmployee = employeeService.save(employeeMapper.dtoToEmployee(employee));
    return employeeMapper.employeeToDto(savedEmployee);
  }

  @PutMapping("/{id}")
  public EmployeeDto modifyEmployeeById(@PathVariable Long id,
                                        @RequestBody EmployeeDto employee) {
    employee.setId(id);
    Employee modifiedEmployee =
        employeeService.modifyEmployee(employeeMapper.dtoToEmployee(employee));
    return employeeMapper.employeeToDto(modifiedEmployee);
  }

  @DeleteMapping("{id}")
  public void deleteEmployeeById(@PathVariable Long id) {
    employeeService.delete(id);
  }

  @GetMapping("/overLimit")
  public Map<String, List<EmployeeDto>> getEmployeeBySalary(@RequestParam Integer limit) {
    Map<String, List<EmployeeDto>> employeesOverLimit = new HashMap<>();
    List<Employee> employeesByLimit = employeeService.getEmployeesOverLimit(limit);
    employeesOverLimit
        .put("Employees over limit", employeeMapper.employeesToDtos(employeesByLimit));
    return employeesOverLimit;
  }

  @PostMapping("/payRaise")
  public int getPayRaisePercent(@RequestBody EmployeeDto employee) {
    Employee convertedEmployee = employeeMapper.dtoToEmployee(employee);
    return employeeService.getPayRaisePercent(convertedEmployee);
  }

  @GetMapping("/position")
  public List<EmployeeDto> getByPosition(@RequestParam String position) {
    List<Employee> byPosition = employeeService.findByPosition(position);
    return employeeMapper.employeesToDtos(byPosition);
  }

  @GetMapping("/name")
  public List<EmployeeDto> getByName(@RequestParam String name) {
    List<Employee> byName = employeeService.findByName(name);
    return employeeMapper.employeesToDtos(byName);
  }

  @GetMapping("/entryDate")
  public List<EmployeeDto> getByEntryDate(@RequestParam LocalDateTime startDate,
                                          @RequestParam LocalDateTime endDate) {
    List<Employee> employees =
        employeeService.findByStartOfWorkBetween(startDate, endDate);
    return employeeMapper.employeesToDtos(employees);
  }


}
