package hu.webuni.hr.katka.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.entities.BusinessType;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.CompanyType;
import hu.webuni.hr.katka.entities.Degree;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.entities.Position;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.CompanyTypeRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import hu.webuni.hr.katka.repositories.PositionRepository;
import hu.webuni.hr.katka.services.CompanyService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CompanyRestControllerIT {

  public static final String BASE_URI = "api/companies";

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  CompanyRepository companyRepository;

  @Autowired
  PositionRepository positionRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  CompanyTypeRepository companyTypeRepository;

  @Test
  public void save_Company_WorksCorrectly() {
    List<CompanyDto> companiesBefore = getAllCompanies();

    CompanyDto newCompany =
        new CompanyDto(0L, "1234", "TEST1", "testaddress", null);

    saveCompany(newCompany)
        .expectStatus()
        .isOk();

    List<CompanyDto> comaniesAfter = getAllCompanies();

    assertThat(comaniesAfter.size()).isEqualTo(companiesBefore.size() + 1);
    assertThat(comaniesAfter.get(comaniesAfter.size() - 1))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(newCompany);
  }

  @Test
  public void save_EmployeeToCompany_WorksCorrectly() {
    List<CompanyDto> companies = getAllCompanies();
    Company company = companyRepository.findById(companies.get(0).getId())
        .orElseThrow(() -> new NotFoundException("There is no company with the provided id."));

    List<Employee> employeesBefore = company.getEmployeesOfCompany();

    Employee newEmployee =
        new Employee("Test1", 100, LocalDateTime.of(2019, 1, 1, 8, 0, 0));

    Position position = new Position("tester", Degree.HIGH_SCHOOL);
    positionRepository.save(position);

    newEmployee.setPosition(position);

    Employee savedEmployee = employeeRepository.save(newEmployee);
    company.addEmployee(savedEmployee);

    List<Employee> employeesAfter = company.getEmployeesOfCompany();

    assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size() + 1);
    assertThat(employeesAfter.get(employeesAfter.size() - 1))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(newEmployee);
  }

  private List<CompanyDto> getAllCompanies() {
    List<CompanyDto> responseList = webTestClient
        .get()
        .uri(BASE_URI)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(CompanyDto.class)
        .returnResult()
        .getResponseBody();
    Objects.requireNonNull(responseList).sort(Comparator.comparing(CompanyDto::getId));
    return responseList;
  }

  private WebTestClient.ResponseSpec saveCompany(CompanyDto newCompany) {
    return webTestClient
        .post()
        .uri(BASE_URI)
        .bodyValue(newCompany)
        .exchange();
  }

  private WebTestClient.ResponseSpec saveEmployee(EmployeeDto newEmployee) {
    return webTestClient
        .post()
        .uri("api/employees")
        .bodyValue(newEmployee)
        .exchange();
  }

}