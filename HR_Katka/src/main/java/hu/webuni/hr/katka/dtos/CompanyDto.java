package hu.webuni.hr.katka.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.katka.entities.CompanyType;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;

public class CompanyDto {

  private Long id;
  @NotEmpty(message = "Registration number must have a value.")
  private String registrationNumber;
  @NotEmpty(message = "Name must have a value.")
  private String name;
  @NotEmpty(message = "Address must have a value.")
  private String address;

  private List<EmployeeDto> employeesOfCompany = new ArrayList<>();

  private CompanyType companyType;

  public CompanyDto() {
  }

  public CompanyDto(Long id,
                    @NotEmpty(message = "Registration number must have a value.") String registrationNumber,
                    @NotEmpty(message = "Name must have a value.") String name,
                    @NotEmpty(message = "Address must have a value.") String address,
                    CompanyType companyType) {
    this.id = id;
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.address = address;
    this.companyType = companyType;
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

  public CompanyType getCompanyType() {
    return companyType;
  }

  public void setCompanyType(CompanyType companyType) {
    this.companyType = companyType;
  }
}
