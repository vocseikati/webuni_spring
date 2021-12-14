package hu.webuni.hr.katka.dtos;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {

  private String registrationNumber;
  private String name;
  private String address;

  private List<EmployeeDto> employeeOfCompany = new ArrayList<>();

  public CompanyDto() {
  }

  public CompanyDto(String registrationNumber, String name, String address,
                    List<EmployeeDto> employeeOfCompany) {
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.address = address;
    this.employeeOfCompany = employeeOfCompany;
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
