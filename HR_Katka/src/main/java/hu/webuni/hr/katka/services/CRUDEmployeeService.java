package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.models.Employee;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CRUDEmployeeService implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

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
    List<Employee> employeesByLimit;
    List<Employee> employees = findAll();
    employeesByLimit = employees.stream().filter(employee -> employee.getSalary() > limit)
        .collect(Collectors.toList());
    return employeesByLimit;
  }

  private Employee getEmployeeOrThrow(Long id) {
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }

  public List<Employee> findByPosition(String position) {
    return employeeRepository.findEmployeesByPosition(position);
  }

  public List<Employee> findByName(String name) {
    return employeeRepository.findAllByNameStartsWithIgnoreCase(name);
  }

  public List<Employee> findByStartOfWorkBetween(LocalDateTime startDate, LocalDateTime endDate) {
    return employeeRepository.findByStartOfWorkBetween(startDate, endDate);
  }
}
