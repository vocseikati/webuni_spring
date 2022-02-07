package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.entities.Position;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-06T10:49:49+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9.1 (JetBrains s.r.o.)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public List<EmployeeDto> employeesToDtos(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeDto> list = new ArrayList<EmployeeDto>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( employeeToDto( employee ) );
        }

        return list;
    }

    @Override
    public List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos) {
        if ( employeeDtos == null ) {
            return null;
        }

        List<Employee> list = new ArrayList<Employee>( employeeDtos.size() );
        for ( EmployeeDto employeeDto : employeeDtos ) {
            list.add( dtoToEmployee( employeeDto ) );
        }

        return list;
    }

    @Override
    public EmployeeDto employeeToDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setEntryDate( employee.getStartOfWork() );
        employeeDto.setTitle( employeePositionName( employee ) );
        employeeDto.setId( employee.getId() );
        employeeDto.setName( employee.getName() );
        employeeDto.setSalary( employee.getSalary() );
        employeeDto.setCompany( companyToCompanyDto( employee.getCompany() ) );

        return employeeDto;
    }

    @Override
    public Employee dtoToEmployee(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setPosition( employeeDtoToPosition( employeeDto ) );
        employee.setStartOfWork( employeeDto.getEntryDate() );
        employee.setId( employeeDto.getId() );
        employee.setName( employeeDto.getName() );
        if ( employeeDto.getSalary() != null ) {
            employee.setSalary( employeeDto.getSalary() );
        }
        employee.setCompany( companyDtoToCompany( employeeDto.getCompany() ) );

        return employee;
    }

    private String employeePositionName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Position position = employee.getPosition();
        if ( position == null ) {
            return null;
        }
        String name = position.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected CompanyDto companyToCompanyDto(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setId( company.getId() );
        companyDto.setRegistrationNumber( company.getRegistrationNumber() );
        companyDto.setName( company.getName() );
        companyDto.setAddress( company.getAddress() );
        companyDto.setCompanyType( company.getCompanyType() );

        return companyDto;
    }

    protected Position employeeDtoToPosition(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Position position = new Position();

        position.setName( employeeDto.getTitle() );

        return position;
    }

    protected Company companyDtoToCompany(CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( companyDto.getId() );
        company.setRegistrationNumber( companyDto.getRegistrationNumber() );
        company.setName( companyDto.getName() );
        company.setAddress( companyDto.getAddress() );
        company.setEmployeesOfCompany( dtosToEmployees( companyDto.getEmployeesOfCompany() ) );
        company.setCompanyType( companyDto.getCompanyType() );

        return company;
    }
}
