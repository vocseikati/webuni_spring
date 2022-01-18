package hu.webuni.hr.katka.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String registrationNumber;
  private String name;
  private String address;

  @OneToMany(mappedBy = "company")
  private List<Employee> employeesOfCompany = new ArrayList<>();

  public Company() {
  }

  public Company(Long id, String registrationNumber, String name, String address,
                 List<Employee> employeesOfCompany) {
    this.id = id;
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.address = address;
    this.employeesOfCompany = employeesOfCompany;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<Employee> getEmployeesOfCompany() {
    return employeesOfCompany;
  }

  public void setEmployeesOfCompany(List<Employee> employeesOfCompany) {
    this.employeesOfCompany = employeesOfCompany;
  }

  public void addEmployee(Employee employee){
    if (employeesOfCompany == null){
      this.employeesOfCompany = new ArrayList<>();
    }
    this.employeesOfCompany.add(employee);
    employee.setCompany(this);
  }
}
