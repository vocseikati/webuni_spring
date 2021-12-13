package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/employees")
public class EmployeeRestController {

  private List<EmployeeDto> employeeList = new ArrayList<>();

  {
    employeeList.add(new EmployeeDto(1L, "Kata", "leader", 100000,
        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
    employeeList.add(new EmployeeDto(2L, "Laca", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
  }

  private Map<Long, EmployeeDto> employees = new HashMap<>();

  {
    employees.put(1L, employeeList.get(1));
    employees.put(2L, employeeList.get(0));
  }


  @GetMapping("/map")
  public ResponseEntity<Map<Long, EmployeeDto>> fetchAllEmployees() {
    return ResponseEntity.ok(employees);
  }

  @GetMapping("/list")
  public ResponseEntity<Map<String, List<EmployeeDto>>> listAllEmployees() {
    Map<String, List<EmployeeDto>> listMap = new HashMap<>();
    listMap.put("employees", employeeList);
    return ResponseEntity.ok(listMap);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
    validateFields(id, "Id cannot be null!");
    for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
      if (entry.getValue().getId().equals(id)) {
        return ResponseEntity.ok(entry.getValue());
      }
    }
    String error =
        new NotFoundException("There is no employee with the provided id.").getMessage();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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
