package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.entities.BusinessType;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.CompanyType;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.CompanyTypeRepository;
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

  @Autowired
  CompanyTypeRepository companyTypeRepository;

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
    Employee testEmployee4 = new Employee("Baba", "developer", 500000,
        LocalDateTime.of(2015, 3, 1, 8, 0, 0));
    Employee testEmployee5 = new Employee("Piripaki", "backend", 600000,
        LocalDateTime.of(2013, 1, 1, 8, 0, 0));
    Employee testEmployee6 = new Employee("Dino", "frontend", 400000,
        LocalDateTime.of(2020, 11, 1, 8, 0, 0));

    Company testCompany1 = new Company("1234", "ÓE", "Bécsi út");
    Company testCompany2 = new Company("5678", "BGE", "Markó utca");
    Company testCompany3 = new Company("9999", "EXON", "Lajos utca");

    testEmployee1.setCompany(testCompany1);
    testEmployee2.setCompany(testCompany2);
    testEmployee3.setCompany(testCompany2);
    testEmployee4.setCompany(testCompany3);
    testEmployee5.setCompany(testCompany3);
    testEmployee6.setCompany(testCompany3);

    testCompany1.addEmployee(testEmployee1);
    testCompany2.addEmployee(testEmployee2);
    testCompany2.addEmployee(testEmployee3);
    testCompany3.addEmployee(testEmployee4);
    testCompany3.addEmployee(testEmployee5);
    testCompany3.addEmployee(testEmployee6);

    CompanyType companyType1 = new CompanyType("Nyrt.");
    CompanyType companyType2 = new CompanyType("Kft.");
    CompanyType companyType3 = new CompanyType("Zrt.");

    companyTypeRepository.saveAll(Arrays.asList(companyType1, companyType2, companyType3));

    testCompany1.setCompanyType(companyType1);
    testCompany2.setCompanyType(companyType2);
    testCompany3.setCompanyType(companyType3);

    companyRepository.saveAll(Arrays.asList(testCompany1, testCompany2, testCompany3));
    employeeRepository.saveAll(Arrays
        .asList(testEmployee1, testEmployee2, testEmployee3, testEmployee4, testEmployee5,
            testEmployee6));
  }
}
