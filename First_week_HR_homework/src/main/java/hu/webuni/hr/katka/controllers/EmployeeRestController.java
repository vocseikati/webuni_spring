package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.models.Employee;
import hu.webuni.hr.katka.services.EmployeeService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  private List<EmployeeDto> employeeList = new ArrayList<>();

  {
    employeeList.add(new EmployeeDto(1L, "Kata", "leader", 100000,
        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
    employeeList.add(new EmployeeDto(2L, "Laca", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
  }

  private Map<Long, EmployeeDto> employees = new HashMap<>();

  {
    employees.put(1L, employeeList.get(0));
    employees.put(2L, employeeList.get(1));
  }


  @GetMapping("/map")
  public ResponseEntity<Map<Long, EmployeeDto>> fetchAllEmployees() {
    return ResponseEntity.ok(employees);
  }

  @GetMapping("/listmap")
  public ResponseEntity<Map<String, List<EmployeeDto>>> listAllEmployees() {
    Map<String, List<EmployeeDto>> listMap = new HashMap<>();
    listMap.put("employees", employeeList);
    return ResponseEntity.ok(listMap);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
    validateFields(id, "Id cannot be null!");
    EmployeeDto employee = employees.get(id);
    if (employee != null) {
      return ResponseEntity.ok(employee);
    }
    String error =
        new NotFoundException("There is no employee with the provided id.").getMessage();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @PostMapping
  public ResponseEntity<Object> addNewEmployee(@RequestBody EmployeeDto employee) {
    try {
      validateFields(employee, "Employee cannot be null.");
      validateFields(employee.getName(), "Name cannot be null or empty.");
      validateFields(employee.getPosition(), "Position cannot be null or empty.");
      validateFields(employee.getSalary(), "Salary cannot be null.");
      validateFields(employee.getStartOfWork(), "Date cannot be null.");
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    employee.setId((long) (employees.size() + 1));
    employees.put((long) (employees.size() + 1), employee);
    URI location = URI.create(String.format("api/employees/%s", employee.getId()));
    return ResponseEntity.created(location).body(employee);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> modifyEmployeeById(@PathVariable Long id,
                                              @RequestBody EmployeeDto employee) {
    validateFields(employee, "Employee cannot be null.");
    validateFields(id, "Id cannot be null!");
    if (!employees.containsKey(id)) {
      String error =
          new NotFoundException("There is no employee with the provided id.").getMessage();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    EmployeeDto originalEmployee = employees.get(id);
    if (employee.getName() != null) {
      originalEmployee.setName(employee.getName());
    }
    if (employee.getPosition() != null) {
      originalEmployee.setPosition(employee.getPosition());
    }
    if (employee.getSalary() != null) {
      originalEmployee.setSalary(employee.getSalary());
    }
    if (employee.getStartOfWork() != null) {
      originalEmployee.setStartOfWork(employee.getStartOfWork());
    }
    return ResponseEntity.ok(originalEmployee);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {
    validateFields(id, "Id cannot be null!");
    if (!employees.containsKey(id)) {
      String error =
          new NotFoundException("There is no employee with the provided id.").getMessage();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    EmployeeDto deletedEmployee = employees.get(id);
    employees.remove(id);
    return ResponseEntity.ok(deletedEmployee);
  }

  @GetMapping
  public ResponseEntity<Map<String, List<EmployeeDto>>> getEmployeeBySalary(
      @RequestParam Integer limit) {

    Map<String, List<EmployeeDto>> employeesOverLimit = new HashMap<>();
    List<EmployeeDto> employeesByLimit = new ArrayList<>();
    for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
      EmployeeDto employee = entry.getValue();
      if (employee.getSalary() > limit) {
        employeesByLimit.add(employee);
      }
    }
    employeesOverLimit.put("Employees over limit", employeesByLimit);
    return ResponseEntity.ok(employeesOverLimit);
  }

  @PostMapping("/payRaise")
  public int getPayRaisePercent(@RequestBody EmployeeDto employee) {
    Employee convertedEmployee = new Employee(employee.getId(), employee.getName(), employee.getPosition(),
        employee.getSalary(), employee.getStartOfWork());
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
