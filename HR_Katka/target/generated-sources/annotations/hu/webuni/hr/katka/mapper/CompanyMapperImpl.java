package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.models.Company;
import hu.webuni.hr.katka.models.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-11T00:04:02+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Eclipse Adoptium)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public List<CompanyDto> companiesToDtos(List<Company> companies) {
        if ( companies == null ) {
            return null;
        }

        List<CompanyDto> list = new ArrayList<CompanyDto>( companies.size() );
        for ( Company company : companies ) {
            list.add( companyToDto( company ) );
        }

        return list;
    }

    @Override
    public CompanyDto companyToDto(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

        companyDto.setEmployeesOfCompany( employeeListToEmployeeDtoList( company.getEmployeesOfCompany() ) );
        companyDto.setId( company.getId() );
        companyDto.setRegistrationNumber( company.getRegistrationNumber() );
        companyDto.setName( company.getName() );
        companyDto.setAddress( company.getAddress() );

        return companyDto;
    }

    @Override
    public Company dtoToCompany(CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( companyDto.getId() );
        company.setRegistrationNumber( companyDto.getRegistrationNumber() );
        company.setName( companyDto.getName() );
        company.setAddress( companyDto.getAddress() );
        company.setEmployeesOfCompany( employeeDtoListToEmployeeList( companyDto.getEmployeesOfCompany() ) );

        return company;
    }

    protected EmployeeDto employeeToEmployeeDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId( employee.getId() );
        employeeDto.setName( employee.getName() );
        employeeDto.setPosition( employee.getPosition() );
        employeeDto.setSalary( employee.getSalary() );
        employeeDto.setStartOfWork( employee.getStartOfWork() );

        return employeeDto;
    }

    protected List<EmployeeDto> employeeListToEmployeeDtoList(List<Employee> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeDto> list1 = new ArrayList<EmployeeDto>( list.size() );
        for ( Employee employee : list ) {
            list1.add( employeeToEmployeeDto( employee ) );
        }

        return list1;
    }

    protected Employee employeeDtoToEmployee(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( employeeDto.getId() );
        employee.setName( employeeDto.getName() );
        employee.setPosition( employeeDto.getPosition() );
        if ( employeeDto.getSalary() != null ) {
            employee.setSalary( employeeDto.getSalary() );
        }
        employee.setStartOfWork( employeeDto.getStartOfWork() );

        return employee;
    }

    protected List<Employee> employeeDtoListToEmployeeList(List<EmployeeDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Employee> list1 = new ArrayList<Employee>( list.size() );
        for ( EmployeeDto employeeDto : list ) {
            list1.add( employeeDtoToEmployee( employeeDto ) );
        }

        return list1;
    }
}
