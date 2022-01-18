package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitDBService {

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  CompanyRepository companyRepository;

  public void clearDB() {
    employeeRepository.deleteAll();
    companyRepository.deleteAll();
  }

  public void insertTestData() {
    Employee testEmployee1 =
        new Employee("Kata", "leader", 100000,
            LocalDateTime.of(2011, 9, 1, 8, 0, 0));
    Employee testEmployee2 = new Employee("Bea", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0));
    Employee testEmployee3 = new Employee("Beginner", "assistant", 50000,
        LocalDateTime.of(2019, 7, 1, 8, 0, 0));
    Company testCompany1 = new Company("1234", "ÓE", "Bécsi út");
    Company testCompany2 = new Company("5678", "BGE", "Markó utca");
    testEmployee1.setCompany(testCompany1);
    testEmployee2.setCompany(testCompany2);
    testEmployee3.setCompany(testCompany2);
    testCompany1.addEmployee(testEmployee1);
    testCompany2.addEmployee(testEmployee2);
    testCompany2.addEmployee(testEmployee3);

    companyRepository.saveAll(Arrays.asList(testCompany1, testCompany2));
    employeeRepository.saveAll(Arrays.asList(testEmployee1, testEmployee2, testEmployee3));
  }
}
