package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.entities.Employee;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  List<EmployeeDto> employeesToDtos(List<Employee> employees);

  List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);

  @Mapping(target = "entryDate", source = "startOfWork")
  @Mapping(target = "title", source = "position.name")
  @Mapping(target = "company.employeesOfCompany", ignore = true)
  EmployeeDto employeeToDto(Employee employee);

  @InheritInverseConfiguration
  Employee dtoToEmployee(EmployeeDto employeeDto);

}
