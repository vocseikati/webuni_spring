package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.models.Company;
import hu.webuni.hr.katka.models.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  private Map<Long, Company> companies = new HashMap<>();

  private List<Employee> employeeList1 = new ArrayList<>();

  {
    employeeList1.add(new Employee(1L, "Kata", "leader", 100000,
        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
    employeeList1.add(new Employee(2L, "Laca", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
  }

  private List<Employee> employeeList2 = new ArrayList<>();

  {
    employeeList2.add(new Employee(1L, "Test1", "test1", 1111,
        LocalDateTime.of(2010, 9, 1, 10, 0, 0)));
    employeeList2.add(new Employee(2L, "Test2", "test2", 9999,
        LocalDateTime.of(2021, 9, 1, 8, 0, 0)));
  }

  {
    companies.put(1L, new Company(1L, "123ad", "BGE", "Markó utca", employeeList1));
    companies.put(2L, new Company(2L, "456re", "ÓE", "Bécsi út", employeeList2));
  }

  public List<Company> findAll() {
    return new ArrayList<>(companies.values());
  }

  public Company findById(Long id) {
    Company companyById = companies.get(id);
    if (companyById == null) {
      throw new NotFoundException("There is no company with the provided id.");
    }
    return companyById;
  }

  public Company save(Company company) {
    company.setId(companies.size() + 1L);
    companies.put(companies.size() + 1L, company);
    return company;
  }

  public void delete(Long id) {
    if (!companies.containsKey(id)) {
      throw new NotFoundException("There is no company with the provided id.");
    }
    companies.remove(id);
  }

  public Company modifyCompany(Long id, Company company) {
    if (!companies.containsKey(id)) {
      throw new NotFoundException("There is no company with the provided id.");
    }
    Company originalCompany = companies.get(id);

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
    Company companyById = findById(id);
    List<Employee> employeesOfCompany = companyById.getEmployeesOfCompany();
    newEmployee.setId((long) (employeesOfCompany.size() + 1));
    employeesOfCompany.add(newEmployee);
    return companyById;
  }

  public Company deleteEmployeeFromCompany(Long id, Long employeeId) {
    Company companyById = findById(id);
    List<Employee> employeesOfCompany = companyById.getEmployeesOfCompany();
    for (Employee employee : employeesOfCompany) {
      if (employee.getId().equals(employeeId)) {
        employeesOfCompany.remove(employee);
        return companyById;
      }
    }
    throw new NotFoundException("There is no employee with the provided id.");
  }

  public Company modifyAllEmployeesFromCompany(Long id, List<Employee> newEmployees) {
    Company companyById = findById(id);
    companyById.setEmployeesOfCompany(newEmployees);
    return companyById;
  }
}
