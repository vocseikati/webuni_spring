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
    date = "2022-02-14T21:37:57+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9.1 (JetBrains s.r.o.)"
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
    public List<CompanyDto> companiesToSummaryDtos(List<Company> companies) {
        if ( companies == null ) {
            return null;
        }

        List<CompanyDto> list = new ArrayList<CompanyDto>( companies.size() );
        for ( Company company : companies ) {
            list.add( companyToSummaryDto( company ) );
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
        companyDto.setCompanyType( company.getCompanyType() );

        return companyDto;
    }

    @Override
    public CompanyDto companyToSummaryDto(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto companyDto = new CompanyDto();

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
        company.setCompanyType( companyDto.getCompanyType() );

        return company;
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
        employee.setCompany( dtoToCompany( employeeDto.getCompany() ) );

        return employee;
    }

    protected List<EmployeeDto> employeeListToEmployeeDtoList(List<Employee> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeDto> list1 = new ArrayList<EmployeeDto>( list.size() );
        for ( Employee employee : list ) {
            list1.add( employeeToDto( employee ) );
        }

        return list1;
    }

    protected List<Employee> employeeDtoListToEmployeeList(List<EmployeeDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Employee> list1 = new ArrayList<Employee>( list.size() );
        for ( EmployeeDto employeeDto : list ) {
            list1.add( dtoToEmployee( employeeDto ) );
        }

        return list1;
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

    protected Position employeeDtoToPosition(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Position position = new Position();

        position.setName( employeeDto.getTitle() );

        return position;
    }
}
