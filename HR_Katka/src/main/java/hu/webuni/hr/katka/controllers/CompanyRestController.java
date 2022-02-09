package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.entities.AverageSalaryByPosition;
import hu.webuni.hr.katka.mapper.CompanyMapper;
import hu.webuni.hr.katka.mapper.EmployeeMapper;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.services.CompanyService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping
  public List<CompanyDto> listAllCompanies(@RequestParam(required = false) Boolean full) {
    List<Company> companies = companyService.findAll(full);
    return mapCompanies(full, companies);
  }

  @GetMapping("{id}")
  public CompanyDto getCompanyById(@PathVariable Long id,
                                   @RequestParam(required = false) Boolean full) {
    Company company = companyService.findById(id, full);
    return mapCompany(full, company);
  }

  @PostMapping
  public CompanyDto addNewCompany(@RequestBody @Valid CompanyDto company) {
    Company savedCompany = companyService.addCompany(companyMapper.dtoToCompany(company));
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
    companyDto.setId(id);
    Company modifiedCompany =
        companyService.modifyCompany(companyMapper.dtoToCompany(companyDto));
    return companyMapper.companyToDto(modifiedCompany);
  }

  @DeleteMapping("{id}")
  public void deleteCompanyById(@PathVariable Long id) {
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

  @GetMapping("/employeesAboveSalary")
  public List<CompanyDto> getCompaniesWithAboveSalary(@RequestParam Integer limit,
                                                      @RequestParam(required = false)
                                                          Boolean full,
                                                      @SortDefault("id") Pageable pageable) {
    List<Company> companies =
        companyService.getCompaniesWithEmployeesOverLimit(pageable, limit);
    return mapCompanies(full, companies);
  }

  @GetMapping("/employeesAboveNumber")
  public List<CompanyDto> getCompaniesAboveNumberOfEmployees(@RequestParam Integer limit,
                                                             @RequestParam(required = false)
                                                                 Boolean full) {
    List<Company> companies =
        companyService.getCompaniesOverEmployeesNumber(limit);
    return mapCompanies(full, companies);
  }

  @GetMapping("/{id}/salaryStats")
  public List<AverageSalaryByPosition> getSalaryStatsById(@PathVariable long id) {
    return companyService.getSalaryStats(id);
  }

  private List<CompanyDto> mapCompanies(Boolean full, List<Company> companies) {
    if (isFull(full)) {
      return companyMapper.companiesToDtos(companies);
    } else {
      return companyMapper.companiesToSummaryDtos(companies);
    }
  }

  private CompanyDto mapCompany(Boolean full, Company company) {
    if (isFull(full)) {
      return companyMapper.companyToDto(company);
    } else {
      return companyMapper.companyToSummaryDto(company);
    }
  }

  private boolean isFull(Boolean full) {
    return full != null && full;
  }
}
