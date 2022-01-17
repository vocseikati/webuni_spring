package hu.webuni.hr.katka.dtos;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

public class EmployeeDto {

  private Long id;
  @NotEmpty(message = "Name must have a value.")
  private String name;
  @NotEmpty(message = "Title must have a value.")
  private String title;
  @Positive(message = "Salary must be positive.")
  private Integer salary;
  @Past(message = "Entry date must be in the past.")
  private LocalDateTime entryDate;

  private CompanyDto company;

  public EmployeeDto() {
  }

  public EmployeeDto(Long id, String name, String title, int salary, LocalDateTime entryDate) {
    this.id = id;
    this.name = name;
    this.title = title;
    this.salary = salary;
    this.entryDate = entryDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getSalary() {
    return salary;
  }

  public void setSalary(Integer salary) {
    this.salary = salary;
  }

  public LocalDateTime getEntryDate() {
    return entryDate;
  }

  public void setEntryDate(LocalDateTime entryDate) {
    this.entryDate = entryDate;
  }

  public CompanyDto getCompany() {
    return company;
  }

  public void setCompany(CompanyDto company) {
    this.company = company;
  }
}
