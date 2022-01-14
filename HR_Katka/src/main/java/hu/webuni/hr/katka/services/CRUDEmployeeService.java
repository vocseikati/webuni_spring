package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.models.Employee;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CRUDEmployeeService {

  @Autowired
  EmployeeRepository employeeRepository;

//  private List<Employee> employeeList = new ArrayList<>();
//
//  {
//    employeeList.add(new Employee(1L, "Kata", "leader", 100000,
//        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
//    employeeList.add(new Employee(2L, "Laca", "referent", 90000,
//        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
//  }
//
//  private Map<Long, Employee> employees = new HashMap<>();
//
//  {
//    employees.put(1L, employeeList.get(0));
//    employees.put(2L, employeeList.get(1));
//  }

  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Employee findById(Long id) {
    return getEmployeeOrThrow(id);
  }

  public void delete(Long id) {
    employeeRepository.delete(getEmployeeOrThrow(id));
  }

  public Employee modifyEmployee(Long id, Employee employee) {

    Employee originalEmployee = getEmployeeOrThrow(id);

    if (employee.getName() != null) {
      originalEmployee.setName(employee.getName());
    }
    if (employee.getPosition() != null) {
      originalEmployee.setPosition(employee.getPosition());
    }
    if (employee.getStartOfWork() != null) {
      originalEmployee.setStartOfWork(employee.getStartOfWork());
    }
    originalEmployee.setSalary(employee.getSalary());
    return originalEmployee;
  }

  public List<Employee> getEmployeesOverLimit(Integer limit) {
    List<Employee> employeesByLimit = new ArrayList<>();
    List<Employee> employees = findAll();
    for (Employee employee : employees) {
      if (employee.getSalary() > limit) {
        employeesByLimit.add(employee);
      }
    }
    return employeesByLimit;
  }

  private Employee getEmployeeOrThrow(Long id) {
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }

}
