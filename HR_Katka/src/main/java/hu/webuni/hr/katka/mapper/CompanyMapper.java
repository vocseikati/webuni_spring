package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  List<CompanyDto> companiesToDtos(List<Company> companies);

  @IterableMapping(qualifiedByName = "summary")
  List<CompanyDto> companiesToSummaryDtos(List<Company> companies);

  CompanyDto companyToDto(Company company);

  @Mapping(target = "employeesOfCompany", ignore = true)
  @Named("summary")
  CompanyDto companyToSummaryDto(Company company);

  Company dtoToCompany(CompanyDto companyDto);

  @Mapping(target = "entryDate", source = "startOfWork")
  @Mapping(target = "title", source = "position")
  @Mapping(target = "company", ignore = true)
  EmployeeDto employeeToDto(Employee employee);

  @InheritInverseConfiguration
  Employee dtoToEmployee(EmployeeDto employeeDto);
}
