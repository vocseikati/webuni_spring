package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

  private final EmployeeService employeeService;

  public SalaryService(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  public void setSalary(Employee employee) {
    int payRaisePercent = employeeService.getPayRaisePercent(employee);
    employee.setSalary(employee.getSalary() / 100 * (100 + payRaisePercent));
  }


}
