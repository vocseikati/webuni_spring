package hu.webuni.hr.katka.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import hu.webuni.hr.katka.dtos.EmployeeDto;
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
class EmployeeRestControllerIT {

  public static final String BASE_URI = "api/employees";

  @Autowired
  WebTestClient webTestClient;

  @Test
  public void save_Employee_WorksCorrectly() {
    List<EmployeeDto> employeesBefore = getAllEmployees();

    EmployeeDto newEmployee =
        new EmployeeDto(0L, "Test1", "Test title1", 100,
            LocalDateTime.of(2019, 1, 1, 8, 0, 0));
    saveEmployee(newEmployee)
        .expectStatus()
        .isOk();

    List<EmployeeDto> employeesAfter = getAllEmployees();

    assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size() + 1);
    assertThat(employeesAfter.get(employeesAfter.size() - 1))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(newEmployee);
  }

  @Test
  public void saveEmployee_WithInvalidTitle_ThrowException() {
    List<EmployeeDto> employeesBefore = getAllEmployees();
    EmployeeDto invalidEmployee =
        new EmployeeDto(0L, "Test1", "", 100,
            LocalDateTime.of(2019, 1, 1, 8, 0, 0));

    saveEmployee(invalidEmployee)
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.message").isEqualTo("Title must have a value.");

    List<EmployeeDto> employeesAfter = getAllEmployees();
    assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
  }

  @Test
  public void saveEmployee_WithInvalidSalary_ThrowException() {
    List<EmployeeDto> employeesBefore = getAllEmployees();
    EmployeeDto invalidEmployee =
        new EmployeeDto(0L, "Test", "test", -100,
            LocalDateTime.of(2019, 1, 1, 8, 0, 0));

    saveEmployee(invalidEmployee)
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.message").isEqualTo("Salary must be positive.");

    List<EmployeeDto> employeesAfter = getAllEmployees();
    assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
  }

  @Test
  public void updateEmployee_WorksCorrectly() {
    EmployeeDto employee =
        new EmployeeDto(0L, "Test1", "Test title1", 100,
            LocalDateTime.of(2019, 1, 1, 8, 0, 0));

    EmployeeDto savedEmployee = saveEmployee(employee)
        .expectStatus()
        .isOk()
        .expectBody(EmployeeDto.class)
        .returnResult()
        .getResponseBody();
    List<EmployeeDto> employeesBefore = getAllEmployees();

    savedEmployee.setName("Put");

    modifyEmployee(savedEmployee).expectStatus().isOk();

    List<EmployeeDto> employeesAfter = getAllEmployees();

    assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
    assertThat(employeesAfter.get(employeesAfter.size()-1))
        .usingRecursiveComparison()
        .isEqualTo(savedEmployee);
  }

  private List<EmployeeDto> getAllEmployees() {
    List<EmployeeDto> responseList =
        webTestClient
            .get()
            .uri(BASE_URI)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(EmployeeDto.class)
            .returnResult()
            .getResponseBody();
    Objects.requireNonNull(responseList).sort(Comparator.comparing(EmployeeDto::getId));
    return responseList;
  }

  private ResponseSpec saveEmployee(EmployeeDto newEmployee) {
    return webTestClient
        .post()
        .uri(BASE_URI)
        .bodyValue(newEmployee)
        .exchange();
  }

  private ResponseSpec modifyEmployee(EmployeeDto employee){
    String path = BASE_URI + "/" + employee.getId();
    return webTestClient
        .put()
        .uri(path)
        .bodyValue(employee)
        .exchange();
  }

}