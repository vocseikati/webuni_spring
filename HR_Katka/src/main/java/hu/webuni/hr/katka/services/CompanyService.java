package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.entities.AverageSalaryByPosition;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.CompanyTypeRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired
  CompanyRepository companyRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  CompanyTypeRepository companyTypeRepository;

  public List<Company> findAll() {
    return companyRepository.findAll();
  }

  public Company findById(Long id) {
    return getCompanyOrThrow(id);
  }

  public Company save(Company company) {
    validateFields(company, "Company cannot be null.");
    if (company.getCompanyType() != null) {
      companyTypeRepository.save(company.getCompanyType());
    }
    return companyRepository.save(company);
  }

  public void delete(Long id) {
    validateFields(id, "Id cannot be null!");
    Company company = getCompanyOrThrow(id);
    companyRepository.delete(company);
  }

  public Company modifyCompany(Company company) {
    validateFields(company, "Company cannot be null.");
    validateFields(company.getId(), "Company Id cannot be null.");
    getCompanyOrThrow(company.getId());

    return companyRepository.save(company);
  }

  public Company addNewEmployeeToCompany(Long id, Employee newEmployee) {
    validateFields(id, "Id cannot be null.");
    validateFields(newEmployee, "Employee cannot be null.");
    Company company = getCompanyOrThrow(id);
    company.addEmployee(newEmployee);
    employeeRepository.save(newEmployee);
    return company;
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
    employeeRepository.save(employeeToRemove);
    return company;
  }

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

  public List<Company> getCompaniesWithEmployeesOverLimit(Integer limit) {
    return companyRepository.findByEmployeeWithSalaryHigherThan(limit);
  }

  public List<Company> getCompaniesOverEmployeesNumber(Integer limit) {
    return companyRepository.findByEmployeeCountHigherThan(limit);
  }

  public List<AverageSalaryByPosition> getSalaryStats(Long companyid) {
    return companyRepository.findAverageSalariesByPosition(companyid);
  }

  private Company getCompanyOrThrow(Long id) {
    Optional<Company> companyById = companyRepository.findById(id);
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
