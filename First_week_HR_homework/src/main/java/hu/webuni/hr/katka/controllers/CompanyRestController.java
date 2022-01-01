package hu.webuni.hr.katka.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.dtos.Views;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

  private List<CompanyDto> companies = new ArrayList<>();

  private List<EmployeeDto> employeeList1 = new ArrayList<>();

  {
    employeeList1.add(new EmployeeDto(1L, "Kata", "leader", 100000,
        LocalDateTime.of(2011, 9, 1, 8, 0, 0)));
    employeeList1.add(new EmployeeDto(2L, "Laca", "referent", 90000,
        LocalDateTime.of(2016, 9, 1, 8, 0, 0)));
  }

  private List<EmployeeDto> employeeList2 = new ArrayList<>();

  {
    employeeList2.add(new EmployeeDto(1L, "Test1", "test1", 1111,
        LocalDateTime.of(2010, 9, 1, 10, 0, 0)));
    employeeList2.add(new EmployeeDto(2L, "Test2", "test2", 9999,
        LocalDateTime.of(2021, 9, 1, 8, 0, 0)));
  }

  {
    companies.add(new CompanyDto(1L, "123ad", "BGE", "Markó utca", employeeList1));
    companies.add(new CompanyDto(2L, "456re", "ÓE", "Bécsi út", employeeList2));
  }

  @GetMapping(params = "full=true")
  @JsonView(Views.Internal.class)
  public ResponseEntity<Map<String, List<CompanyDto>>> listAllCompaniesFull() {
    return ResponseEntity.ok(getListAllResponse());
  }

  @GetMapping
  @JsonView(Views.Public.class)
  public ResponseEntity<Map<String, List<CompanyDto>>> listAllCompaniesWithOutEmployees() {
    return ResponseEntity.ok(getListAllResponse());
  }

  @GetMapping("{id}")
  @JsonView(Views.Public.class)
  public ResponseEntity<?> getCompanyByIdWithoutEmployees(@PathVariable Long id) {
    CompanyDto companyById = getCompanyById(id);
    if (companyById != null){
      return ResponseEntity.ok(companyById);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorMessage());
  }

  @GetMapping(value = "{id}", params = "full=true")
  @JsonView(Views.Internal.class)
  public ResponseEntity<?> getCompanyByIdFull(@PathVariable Long id) {
    CompanyDto companyById = getCompanyById(id);
    if (companyById != null){
      return ResponseEntity.ok(companyById);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorMessage());
  }

  @PostMapping
  public ResponseEntity<?> addNewCompany(@RequestBody CompanyDto company) {
    try {
      validateFields(company, "Company cannot be null.");
      validateFields(company.getRegistrationNumber(),
          "Registration Number cannot be null or empty.");
      validateFields(company.getName(), "Name cannot be null or empty.");
      validateFields(company.getAddress(), "Address cannot be null.");
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    company.setId((long) (companies.size() + 1));
    companies.add(company);
    URI location = URI.create(String.format("api/companies/%s", company.getId()));
    return ResponseEntity.created(location).body(company);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> modifyCompany(@PathVariable Long id,
                                         @RequestBody CompanyDto modifiedCompany) {
    for (CompanyDto company : companies) {
      if (company.getId().equals(id)) {
        if (modifiedCompany.getRegistrationNumber() != null) {
          company.setRegistrationNumber(modifiedCompany.getRegistrationNumber());
        }
        if (modifiedCompany.getName() != null) {
          company.setName(modifiedCompany.getName());
        }
        if (modifiedCompany.getAddress() != null) {
          company.setAddress(modifiedCompany.getAddress());
        }
        if (!modifiedCompany.getEmployeesOfCompany().isEmpty()) {
          company.setEmployeesOfCompany(modifiedCompany.getEmployeesOfCompany());
        }
        return ResponseEntity.ok(company);
      }
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorMessage());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteCompanyById(@PathVariable Long id) {
    for (CompanyDto company : companies) {
      if (company.getId().equals(id)) {
        companies.remove(company);
        return ResponseEntity.noContent().build();
      }
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorMessage());
  }

  private Map<String, List<CompanyDto>> getListAllResponse() {
    Map<String, List<CompanyDto>> map = new HashMap<>();
    map.put("companies", companies);
    return map;
  }

  private CompanyDto getCompanyById(Long id){
    for (CompanyDto company : companies) {
      if (company.getId().equals(id)){
        return company;
      }
    }
    return null;
  }

  private String getErrorMessage() {
    return new NotFoundException("There is no company with the provided id.").getMessage();
  }

  private void validateFields(Object o, String message) {
    if (o instanceof String) {
      if (((String) o).isEmpty()) {
        throw new IllegalArgumentException(message);
      }
    }
    if (o == null) {
      throw new IllegalArgumentException(message);
    }
  }
}
