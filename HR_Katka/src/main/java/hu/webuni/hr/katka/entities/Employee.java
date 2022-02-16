package hu.webuni.hr.katka.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int salary;
  @Column(name = "entry_date")
  private LocalDateTime startOfWork;

  @ManyToOne
  private Company company;

  @ManyToOne
  private Position position;

  @OneToMany(mappedBy = "employee")
  private List<Holiday> holidayRequests;

  @ManyToOne
  private Employee boss;

  public Employee() {
  }

  public Employee(String name, int salary, LocalDateTime startOfWork) {
    this.name = name;
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

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
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

  public List<Holiday> getHolidayRequests() {
    return holidayRequests;
  }

  public void setHolidayRequests(List<Holiday> holidayRequests) {
    this.holidayRequests = holidayRequests;
  }

  public void addHolidayRequest(Holiday holidayRequest)
  {
    if(this.holidayRequests == null)
      this.holidayRequests = new ArrayList<>();

    this.holidayRequests.add(holidayRequest);
    holidayRequest.setEmployee(this);
  }

  public Employee getBoss() {
    return boss;
  }

  public void setBoss(Employee boss) {
    this.boss = boss;
  }
}
