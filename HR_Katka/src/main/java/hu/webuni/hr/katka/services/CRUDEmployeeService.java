package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CRUDEmployeeService implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public Employee save(Employee employee) {
    validateFields(employee, "Employee cannot be null."); //todo: nem jelenik meg az üzenet miért?
    return employeeRepository.save(employee);
  }

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Employee findById(Long id) {
    return getEmployeeOrThrow(id);
  }

  public void delete(Long id) {
    validateFields(id, "Id cannot be null!");
    employeeRepository.delete(getEmployeeOrThrow(id));
  }

  public Employee modifyEmployee(Employee employee) {
    validateFields(employee, "Employee cannot be null."); //todo: ellenőrizni a hibaüzenetet
    validateFields(employee.getId(), "Id cannot be null!");
    return employeeRepository.save(employee);
  }

  public List<Employee> getEmployeesOverLimit(Integer limit) {
    validateFields(limit, "Limit cannot be null.");
    return employeeRepository.findBySalaryGreaterThan(limit);
  }

  private Employee getEmployeeOrThrow(Long id) {
    validateFields(id, "Id cannot be null.");
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }

  public List<Employee> findByPosition(String position) {
    validateFields(position, "Position cannot be null.");
    return employeeRepository.findEmployeesByPosition(position);
  }

  public List<Employee> findByName(String name) {
    validateFields(name, "Name cannot be null.");
    return employeeRepository.findAllByNameStartsWithIgnoreCase(name);
  }

  public List<Employee> findByStartOfWorkBetween(LocalDateTime startDate, LocalDateTime endDate) {
    validateFields(startDate, "Start date cannot be null.");
    validateFields(endDate, "End date cannot be null.");
    return employeeRepository.findByStartOfWorkBetween(startDate, endDate);
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
