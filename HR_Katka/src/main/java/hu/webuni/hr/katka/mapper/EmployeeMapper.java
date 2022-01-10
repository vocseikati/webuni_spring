package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.models.Employee;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  List<EmployeeDto> employeesToDtos(List<Employee> employees);

  EmployeeDto employeeToDto(Employee employee);

  Employee dtoToEmployee(EmployeeDto employeeDto);

}
