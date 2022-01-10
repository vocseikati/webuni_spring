package hu.webuni.hr.katka.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.dtos.Views;
import hu.webuni.hr.katka.mapper.CompanyMapper;
import hu.webuni.hr.katka.mapper.EmployeeMapper;
import hu.webuni.hr.katka.models.Company;
import hu.webuni.hr.katka.models.Employee;
import hu.webuni.hr.katka.services.CompanyService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  CompanyService companyService;

  @Autowired
  CompanyMapper companyMapper;

  @Autowired
  EmployeeMapper employeeMapper;

  @GetMapping(params = "full=true")
  @JsonView(Views.Internal.class)
  public List<CompanyDto> listAllCompaniesFull() {
    List<Company> companies = companyService.findAll();
    return companyMapper.companiesToDtos(companies);
  }

  @GetMapping
  @JsonView(Views.Public.class)
  public List<CompanyDto> listAllCompaniesWithOutEmployees() {
    List<Company> companies = companyService.findAll();
    return companyMapper.companiesToDtos(companies);
  }

  @GetMapping("{id}")
  @JsonView(Views.Public.class)
  public CompanyDto getCompanyByIdWithoutEmployees(@PathVariable Long id) {
    Company companyById = companyService.findById(id);
    return companyMapper.companyToDto(companyById);
  }

  @GetMapping(value = "{id}", params = "full=true")
  @JsonView(Views.Internal.class)
  public CompanyDto getCompanyByIdFull(@PathVariable Long id) {
    Company companyById = companyService.findById(id);
    return companyMapper.companyToDto(companyById);
  }

  @PostMapping
  public CompanyDto addNewCompany(@RequestBody @Valid CompanyDto company) {
    validateFields(company, "Company cannot be null.");
    Company savedCompany = companyService.save(companyMapper.dtoToCompany(company));
    return companyMapper.companyToDto(savedCompany);
  }

  @PostMapping("{id}/employees")
  public CompanyDto addNewEmployee(@PathVariable Long id,
                                   @RequestBody @Valid EmployeeDto newEmployee) {
    Company company =
        companyService.addNewEmployeeToCompany(id, employeeMapper.dtoToEmployee(newEmployee));
    return companyMapper.companyToDto(company);
  }

  @PutMapping("{id}")
  public CompanyDto modifyCompany(@PathVariable Long id,
                                  @RequestBody CompanyDto companyDto) {
    validateFields(companyDto, "Company cannot be null.");
    validateFields(id, "Id cannot be null!");
    Company modifiedCompany =
        companyService.modifyCompany(id, companyMapper.dtoToCompany(companyDto));
    return companyMapper.companyToDto(modifiedCompany);
  }

  @DeleteMapping("{id}")
  public void deleteCompanyById(@PathVariable Long id) {
    validateFields(id, "Id cannot be null!");
    companyService.delete(id);
  }

  @DeleteMapping("{id}/employees/{employeeId}")
  public CompanyDto deleteEmployeeFromCompanyById(@PathVariable Long id,
                                                  @PathVariable Long employeeId) {
    Company company = companyService.deleteEmployeeFromCompany(id, employeeId);
    return companyMapper.companyToDto(company);
  }

  @PutMapping("/{id}/employees")
  public CompanyDto replaceAllEmployees(@PathVariable Long id,
                                        @RequestBody List<EmployeeDto> employees) {
    List<Employee> newEmployees = employeeMapper.dtosToEmployees(employees);
    Company company = companyService.modifyAllEmployeesFromCompany(id, newEmployees);
    return companyMapper.companyToDto(company);
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
