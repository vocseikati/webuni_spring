package hu.webuni.hr.katka.services;

import static hu.webuni.hr.katka.services.ValidationService.validateFields;

import hu.webuni.hr.katka.entities.AverageSalaryByPosition;
import hu.webuni.hr.katka.entities.BusinessType;
import hu.webuni.hr.katka.entities.CompanyType;
import hu.webuni.hr.katka.entities.Position;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.CompanyTypeRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;

import hu.webuni.hr.katka.repositories.PositionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

  @Autowired
  CompanyRepository companyRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  CompanyTypeRepository companyTypeRepository;

  @Autowired
  PositionRepository positionRepository;

  public List<Company> findAll(Boolean full) {
    boolean notFull = full == null || !full;
    if (notFull) {
      return companyRepository.findAll();
    } else {
      return companyRepository.findAllWithEmployees();
    }
  }

//  public List<Company> findAllWithEmployees() {
//    return companyRepository.findAllWithEmployees();
//  }

  public Company findById(Long id, Boolean full) {
    return getCompanyOrThrow(id, full);
  }

  @Transactional
  public Company addCompany(Company company) {
    setCompanyType(company);
    return companyRepository.save(company);
  }

  private void setCompanyType(Company company) {
    if (company.getCompanyType() != null) {
      CompanyType companyType;
      String companyTypeName = company.getCompanyType().getName().name();
      BusinessType type = BusinessType.fromString(companyTypeName);

      List<CompanyType> companyTypes = companyTypeRepository.findByName(type);
      if (companyTypes.isEmpty()) {
        companyType = companyTypeRepository.save(new CompanyType(type));
      } else {
        companyType = companyTypes.get(0);
      }
      company.setCompanyType(companyType);
    }
  }

  public void delete(Long id) {
    validateFields(id, "Id cannot be null!");
    Company company = getCompanyOrThrow(id);
    companyRepository.delete(company);
  }

  @Transactional
  public Company modifyCompany(Company company) {
    validateFields(company, "Company cannot be null.");
    validateFields(company.getId(), "Company Id cannot be null.");
    getCompanyOrThrow(company.getId());

    return companyRepository.save(company);
  }

  @Transactional
  public Company addNewEmployeeToCompany(Long id, Employee newEmployee) {
    validateFields(id, "Id cannot be null.");
    validateFields(newEmployee, "Employee cannot be null.");
    Company company = getCompanyOrThrow(id);

    setPosition(newEmployee);

    company.addEmployee(newEmployee);
    employeeRepository.save(newEmployee);
    return company;
  }

  private void setPosition(Employee employee) {
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

  public Company deleteEmployeeFromCompany(Long id, Long employeeId) {
    validateFields(id, "Company Id cannot be null.");
    validateFields(employeeId, "Employee Id cannot be null.");
    Company company = getCompanyOrThrow(id);
    Employee employeeToRemove = getEmployeeOrThrow(employeeId);

    List<Employee> employeeList = company.getEmployeesOfCompany();
    if (!employeeList.contains(employeeToRemove)) {
      throw new IllegalArgumentException(
          "This employee does not participate in the given company.");
    }
    employeeToRemove.setCompany(null);
    employeeList.remove(employeeToRemove);
    employeeRepository.save(employeeToRemove); // HA TRANSACTIONAL AKKOR EZ TÖRÖLHETŐ
    return company;
  }

  @Transactional
  public Company modifyAllEmployeesFromCompany(Long id, List<Employee> newEmployees) {
    validateFields(id, "Id cannot be null.");
    validateFields(newEmployees, "List of employees cannot be null.");
    Company company = getCompanyOrThrow(id);
    company.getEmployeesOfCompany().forEach(e -> e.setCompany(null));
    company.getEmployeesOfCompany().clear();
    for (Employee employee : newEmployees) {
      company.addEmployee(employee);
      employeeRepository.save(employee);
    }
    return company;
  }

  public List<Company> getCompaniesWithEmployeesOverLimit(Pageable pageable, Integer limit) {
    return companyRepository.findByEmployeeWithSalaryHigherThan(pageable, limit).getContent();
  }

  public List<Company> getCompaniesOverEmployeesNumber(Integer limit) {
    return companyRepository.findByEmployeeCountHigherThan(limit);
  }

  public List<AverageSalaryByPosition> getSalaryStats(Long companyid) {
    return companyRepository.findAverageSalariesByPosition(companyid);
  }

  private Company getCompanyOrThrow(Long id, Boolean full) {
    boolean notFull = full == null || !full;
    Optional<Company> companyById;
    if (notFull) {
      companyById = companyRepository.findById(id);
    } else {
      companyById = companyRepository.findByIdWithEmployees(id);
    }
    if (companyById.isEmpty()) {
      throw new NotFoundException("There is no company with the provided id.");
    }
    return companyById.get();
  }

  private Company getCompanyOrThrow(Long id) {
    Optional<Company> companyById= companyRepository.findById(id);
    if (companyById.isEmpty()) {
      throw new NotFoundException("There is no company with the provided id.");
    }
    return companyById.get();
  }

  private Employee getEmployeeOrThrow(Long id) {
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }

}
