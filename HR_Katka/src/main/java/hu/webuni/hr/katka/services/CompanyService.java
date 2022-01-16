package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired
  CompanyRepository companyRepository;

  @Autowired
  EmployeeRepository employeeRepository;

//  private Map<Long, Company> companies = new HashMap<>();
//
//  private List<Employee> employeeList1 = new ArrayList<>();
//
//  {
//    employeeList1.add(new Employee(1L, "Kata", "leader", 100000,
//        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
//    employeeList1.add(new Employee(2L, "Laca", "referent", 90000,
//        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
//  }
//
//  private List<Employee> employeeList2 = new ArrayList<>();
//
//  {
//    employeeList2.add(new Employee(1L, "Test1", "test1", 1111,
//        LocalDateTime.of(2010, 9, 1, 10, 0, 0)));
//    employeeList2.add(new Employee(2L, "Test2", "test2", 9999,
//        LocalDateTime.of(2021, 9, 1, 8, 0, 0)));
//  }
//
//  {
//    companies.put(1L, new Company(1L, "123ad", "BGE", "Markó utca", employeeList1));
//    companies.put(2L, new Company(2L, "456re", "ÓE", "Bécsi út", employeeList2));
//  }

  public List<Company> findAll() {
    return companyRepository.findAll();
  }

  public Company findById(Long id) {
    return getCompanyOrThrow(id);
  }

  public Company save(Company company) {
    return companyRepository.save(company);
  }

  public void delete(Long id) {
    Company company = getCompanyOrThrow(id);
    companyRepository.delete(company);
  }

  public Company modifyCompany(Long id, Company company) {
    Company originalCompany = getCompanyOrThrow(id);

    if (company.getRegistrationNumber() != null) {
      originalCompany.setRegistrationNumber(company.getRegistrationNumber());
    }
    if (company.getName() != null) {
      originalCompany.setName(company.getName());
    }
    if (company.getAddress() != null) {
      originalCompany.setAddress(company.getAddress());
    }
    if (!company.getEmployeesOfCompany().isEmpty()) {
      originalCompany.setEmployeesOfCompany(company.getEmployeesOfCompany());
    }
    return originalCompany;
  }

  public Company addNewEmployeeToCompany(Long id, Employee newEmployee) {
    Company company = getCompanyOrThrow(id);
    List<Employee> employeeList = company.getEmployeesOfCompany();
    employeeList.add(newEmployee);
    employeeRepository.save(newEmployee);
    return company;
  }

  public Company deleteEmployeeFromCompany(Long id, Long employeeId) {
    Company company = getCompanyOrThrow(id);
    Employee employeeToRemove = getEmployeeOrThrow(employeeId);

    List<Employee> employeeList = company.getEmployeesOfCompany();
    if (!employeeList.contains(employeeToRemove)) {
      throw new IllegalArgumentException(
          "This employee does not participate in the given company.");
    }
    employeeList.remove(employeeToRemove);
    return company;
  }

  public Company modifyAllEmployeesFromCompany(Long id, List<Employee> newEmployees) {
    Company company = getCompanyOrThrow(id);
    company.setEmployeesOfCompany(newEmployees);
    return company;
  }

  private Company getCompanyOrThrow(Long id) {
    Optional<Company> companyById = companyRepository.findById(id);
    if (companyById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
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
