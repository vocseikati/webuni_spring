package hu.webuni.hr.katka.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String position;
  private int salary;
  @Column(name = "entry_date")
  private LocalDateTime startOfWork;

  @ManyToOne
  private Company company;

  public Employee() {
  }

  public Employee(Long id, String name, String position, int salary,
                  LocalDateTime startOfWork) {
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

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
