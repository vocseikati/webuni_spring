package hu.webuni.hr.katka.models;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

public class Employee {

  private Long id;
  @NotEmpty(message = "Name must have a value.")
  private String name;
  @NotEmpty(message = "Position must have a value.")
  private String position;
  @Positive
  private int salary;
  @Past(message = "Entry date must be in the past.")
  private LocalDateTime startOfWork;

  public Employee() {
  }

  public Employee(Long id, String name, String position, int salary, LocalDateTime startOfWork) {
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

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public LocalDateTime getStartOfWork() {
    return startOfWork;
  }

  public void setStartOfWork(LocalDateTime startOfWork) {
    this.startOfWork = startOfWork;
  }
}
