package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.models.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CRUDEmployeeService {

  private List<Employee> employeeList = new ArrayList<>();

  {
    employeeList.add(new Employee(1L, "Kata", "leader", 100000,
        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
    employeeList.add(new Employee(2L, "Laca", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
  }

  private Map<Long, Employee> employees = new HashMap<>();

  {
    employees.put(1L, employeeList.get(0));
    employees.put(2L, employeeList.get(1));
  }

  public Employee save(Employee employee) {
    employee.setId(employees.size() + 1L);
    employees.put(employee.getId(), employee);
    return employee;
  }

  public List<Employee> findAll() {
    return new ArrayList<>(employees.values());
  }

  public Employee findById(Long id) {
    Employee employeeById = employees.get(id);
    if (employeeById == null) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById;
  }

  public void delete(Long id) {
    if (!employees.containsKey(id)) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    employees.remove(id);
  }

  public Employee modifyEmployee(Long id, Employee employee) {
    if (!employees.containsKey(id)) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    Employee originalEmployee = employees.get(id);

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
    for (Map.Entry<Long, Employee> entry : employees.entrySet()) {
      Employee employee = entry.getValue();
      if (employee.getSalary() > limit) {
        employeesByLimit.add(employee);
      }
    }
    return employeesByLimit;
  }

}
