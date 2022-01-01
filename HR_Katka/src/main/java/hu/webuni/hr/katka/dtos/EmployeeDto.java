package hu.webuni.hr.katka.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDateTime;

public class EmployeeDto {

  @JsonView(Views.Internal.class)
  private Long id;
  @JsonView(Views.Internal.class)
  private String name;
  @JsonView(Views.Internal.class)
  private String position;
  @JsonView(Views.Internal.class)
  private Integer salary;
  @JsonView(Views.Internal.class)
  private LocalDateTime startOfWork;

  public EmployeeDto() {
  }

  public EmployeeDto(String name, String position, int salary, LocalDateTime startOfWork) {
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.startOfWork = startOfWork;
  }

  public EmployeeDto(Long id, String name, String position, int salary, LocalDateTime startOfWork) {
    this.id = id;
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.startOfWork = startOfWork;
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

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Integer getSalary() {
    return salary;
  }

  public void setSalary(Integer salary) {
    this.salary = salary;
  }

  public LocalDateTime getStartOfWork() {
    return startOfWork;
  }

  public void setStartOfWork(LocalDateTime startOfWork) {
    this.startOfWork = startOfWork;
  }
}
