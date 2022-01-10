package hu.webuni.hr.katka.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;

public class CompanyDto {

  @JsonView(Views.Public.class)
  private Long id;
  @JsonView(Views.Public.class)
  @NotEmpty(message = "Registration number must have a value.")
  private String registrationNumber;
  @JsonView(Views.Public.class)
  @NotEmpty(message = "Name must have a value.")
  private String name;
  @JsonView(Views.Public.class)
  @NotEmpty(message = "Address must have a value.")
  private String address;
  @JsonView(Views.Internal.class)
  private List<EmployeeDto> employeesOfCompany = new ArrayList<>();

  public CompanyDto() {
  }

  public CompanyDto(Long id, String registrationNumber, String name, String address,
                    List<EmployeeDto> employeesOfCompany) {
    this.id = id;
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.address = address;
    this.employeesOfCompany = employeesOfCompany;
  }

  public CompanyDto(Long id, String registrationNumber, String name, String address) {
    this.id = id;
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public List<EmployeeDto> getEmployeesOfCompany() {
    return employeesOfCompany;
  }

  public void setEmployeesOfCompany(List<EmployeeDto> employeesOfCompany) {
    this.employeesOfCompany = employeesOfCompany;
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
}
