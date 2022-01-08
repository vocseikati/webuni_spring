package hu.webuni.hr.katka.models;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import java.util.ArrayList;
import java.util.List;

public class Company {
  private Long id;
  private String registrationNumber;
  private String name;
  private String address;
  private List<EmployeeDto> employeesOfCompany = new ArrayList<>();

  public Company() {
  }

  public Company(Long id, String registrationNumber, String name, String address,
                 List<EmployeeDto> employeesOfCompany) {
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

  public List<EmployeeDto> getEmployeesOfCompany() {
    return employeesOfCompany;
  }

  public void setEmployeesOfCompany(List<EmployeeDto> employeesOfCompany) {
    this.employeesOfCompany = employeesOfCompany;
  }
}
