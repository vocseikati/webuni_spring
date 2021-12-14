package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping
  public ResponseEntity<Map<String, List<CompanyDto>>> listAllCompanies() {
    Map<String, List<CompanyDto>> map = new HashMap<>();
    map.put("companies", companies);
    return ResponseEntity.ok(map);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
    for (CompanyDto company : companies) {
      if (company.getId().equals(id)) {
        return ResponseEntity.ok(company);
      }
    }
    String error =
        new NotFoundException("There is no company with the provided id.").getMessage();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @PostMapping
  public ResponseEntity<?> addNewCompany(@RequestBody CompanyDto company){
    try {
      validateFields(company, "Company cannot be null.");
      validateFields(company.getRegistrationNumber(), "Registration Number cannot be null or empty.");
      validateFields(company.getName(), "Name cannot be null or empty.");
      validateFields(company.getAddress(), "Address cannot be null.");
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    company.setId((long) (companies.size() + 1));
    companies.add(company);
    URI location = URI.create(String.format("api/employees/%s", company.getId()));
    return ResponseEntity.created(location).body(company);
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
