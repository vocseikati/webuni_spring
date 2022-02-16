package hu.webuni.hr.katka.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.repositories.CompanyRepository;
import hu.webuni.hr.katka.repositories.CompanyTypeRepository;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import hu.webuni.hr.katka.repositories.PositionRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CompanyRestControllerIT {

  public static final String BASE_URI = "api/companies";

  @Autowired
  WebTestClient webTestClient;

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

    CompanyDto company = getCompanyById(3L);

    List<EmployeeDto> employeesBefore = company.getEmployeesOfCompany();

    EmployeeDto newEmployee =
        new EmployeeDto(0L, "Test1", "Test title", 100, LocalDateTime.of(2019, 1, 1, 8, 0, 0));

    addEmployeeToCompany(company.getId(), newEmployee)
        .expectStatus()
        .isOk();

    saveEmployee(newEmployee)
        .expectStatus()
        .isOk();

    saveCompany(company)
        .expectStatus()
        .isOk();


    List<EmployeeDto> employeesAfter = company.getEmployeesOfCompany();

    assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size() + 1);
    assertThat(employeesAfter.get(employeesAfter.size() - 1))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(newEmployee);
  }

  private ResponseSpec addEmployeeToCompany(long companyId, EmployeeDto newEmployeeDto) {
    String path = BASE_URI + "/" + companyId + "/employees";
    return webTestClient
        .post()
        .uri(path)
        .bodyValue(newEmployeeDto)
        .exchange()
        .expectStatus()
        .isOk();
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

  private CompanyDto getCompanyById(Long id) {
    CompanyDto responseBody = webTestClient
        .get()
        .uri(BASE_URI + "/" + id)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CompanyDto.class)
        .returnResult()
        .getResponseBody();
    return responseBody;
  }

  private ResponseSpec saveCompany(CompanyDto newCompany) {
    return webTestClient
        .post()
        .uri(BASE_URI)
        .bodyValue(newCompany)
        .exchange();
  }

  private ResponseSpec saveEmployee(EmployeeDto newEmployee) {
    return webTestClient
        .post()
        .uri("api/employees")
        .bodyValue(newEmployee)
        .exchange();
  }

}