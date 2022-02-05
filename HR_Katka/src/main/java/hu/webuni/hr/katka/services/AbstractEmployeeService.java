package hu.webuni.hr.katka.services;

import static hu.webuni.hr.katka.services.ValidationService.validateFields;

import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Position;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import hu.webuni.hr.katka.repositories.PositionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public abstract class AbstractEmployeeService implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Transactional
  public Employee save(Employee employee) {
    clearCompanyAndSetPosition(employee);
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

  @Transactional
  public Employee modifyEmployee(Employee employee) {
    validateFields(employee.getId(), "Id cannot be null!");
    Employee originalEmployee = getEmployeeOrThrow(employee.getId());
    if (employee.getCompany() == null) {
      employee.setCompany(originalEmployee.getCompany());
    }
    clearCompanyAndSetPosition(employee);
    return employeeRepository.save(employee);
  }

  public List<Employee> getEmployeesOverLimit(Integer limit) {
    validateFields(limit, "Limit cannot be null.");
    return employeeRepository.findBySalaryGreaterThan(limit);
  }

  public List<Employee> findByPosition(String position) {
    validateFields(position, "Position cannot be null.");
    return employeeRepository.findEmployeesByPositionName(position);
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

  private Employee getEmployeeOrThrow(Long id) {
    validateFields(id, "Id cannot be null.");
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }

  private void clearCompanyAndSetPosition(Employee employee) {
//    employee.setCompany(null);
    Position position = null;
    String positionName = employee.getPosition().getName();
    if (positionName != null) {
      List<Position> positionsByName = positionRepository.findByName(positionName);
      if (positionsByName.isEmpty()) {
        position = positionRepository.save(new Position(positionName, null));
      } else {
        position = positionsByName.get(0);
      }
    }
    employee.setPosition(position);
  }

  public List<Employee> findEmployeesByExample(Employee example) {
    Long id = example.getId();
    String name = example.getName();
    String positionName = null;
    String companyName = null;
    Position position = example.getPosition();
    int salary = example.getSalary();
    Company company = example.getCompany();
    LocalDateTime entryDate = example.getStartOfWork();

    Specification<Employee> spec = Specification.where(null);

    if (id != null && id > 0) {
      spec = spec.and(EmployeeSpecification.hasId(id));
    }

    if (StringUtils.hasText(name)) {
      spec = spec.and(EmployeeSpecification.hasName(name));
    }

    if (position != null) {
      positionName = position.getName();
    }

    if (StringUtils.hasText(positionName)) {
      spec = spec.or(EmployeeSpecification.hasPosition(positionName));
    }

    if (salary > 0) {
      spec = spec.and(EmployeeSpecification.hasSalary(salary));
    }

    if (entryDate != null) {
      spec = spec.and(EmployeeSpecification.hasEntryDate(entryDate));
    }

    if (company != null && company.getName() != null) {
      companyName = company.getName();
    }

    if (StringUtils.hasText(companyName)) {
      spec = spec.and(EmployeeSpecification.hasCompany(companyName));
    }

    return employeeRepository.findAll(spec, Sort.by("id"));
  }

}
