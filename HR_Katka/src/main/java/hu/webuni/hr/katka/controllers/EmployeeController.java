package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

  private List<EmployeeDto> employees = new ArrayList<>();

  {
    employees.add(new EmployeeDto(1L, "Kata", "leader", 100000,
        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
    employees.add(new EmployeeDto(2L, "Laca", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
  }

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/employees")
  public String listEmployees(Model model, @RequestParam(required = false) String error) {
    model.addAttribute("employees", employees);
    model.addAttribute("newEmployee", new EmployeeDto());
    if (error != null) {
      model.addAttribute("error", error);
    }
    return "index";
  }

  @PostMapping("/employees")
  public String addEmployee(EmployeeDto employee) {
    try {
      checkFields(employee);
      employee.setId((long) employees.size() + 1);
      employee.setStartOfWork(LocalDateTime.now());
      employees.add(employee);
    } catch (IllegalArgumentException ex) {
      return "redirect:/employees?error=" + ex.getMessage();
    }
    return "redirect:/employees";
  }

  @GetMapping("/employees/{id}")
  public String edit(@PathVariable Long id, Model model) {
    for (EmployeeDto employee : employees) {
      if (employee.getId().equals(id)) {
        model.addAttribute("employee", employee);
      }
    }
    return "edit";
  }

  @PostMapping("/edit")
  public String editEmployee(EmployeeDto employee) {
    for (EmployeeDto employeeDto : employees) {
      if (employeeDto.getId().equals(employee.getId())) {
        employeeDto.setName(employee.getName());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setStartOfWork(employee.getStartOfWork());
      }
    }
    return "redirect:/employees";
  }

  @GetMapping("employees/delete/{id}")
  public String deleteEmployee(@PathVariable Long id){
    employees.removeIf(employee -> employee.getId().equals(id));
    return "redirect:/employees";
  }

  private void checkFields(EmployeeDto employee) {
    if (employee == null) {
      throw new IllegalArgumentException("Employee must not be null!");
    }
    if (employee.getName() == null || employee.getName().isEmpty()) {
      throw new IllegalArgumentException("Name must not be null!");
    }
    if (employee.getPosition() == null || employee.getPosition().isEmpty()) {
      throw new IllegalArgumentException("Position must not be null!");
    }
    if (employee.getSalary() <= 0) {
      throw new IllegalArgumentException("Salary must be positive!");
    }
//    if (employee.getStartOfWork()==null){
//      throw new IllegalArgumentException("Date must not be null!");
//    }
  }

}
