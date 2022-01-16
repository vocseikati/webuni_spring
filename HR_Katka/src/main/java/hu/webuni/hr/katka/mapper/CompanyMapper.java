package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.entities.Company;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  List<CompanyDto> companiesToDtos(List<Company> companies);

  CompanyDto companyToDto(Company company);

  Company dtoToCompany(CompanyDto companyDto);
}
