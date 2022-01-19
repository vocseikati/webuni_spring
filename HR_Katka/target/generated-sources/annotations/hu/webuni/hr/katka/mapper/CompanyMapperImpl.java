package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.CompanyDto;
import hu.webuni.hr.katka.dtos.CompanyTypeDto;
import hu.webuni.hr.katka.dtos.EmployeeDto;
import hu.webuni.hr.katka.entities.BusinessType;
import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.CompanyType;
import hu.webuni.hr.katka.entities.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-19T14:03:39+0100",
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
        companyDto.setCompanyType( companyTypeToCompanyTypeDto( company.getCompanyType() ) );

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
        companyDto.setCompanyType( companyTypeToCompanyTypeDto( company.getCompanyType() ) );

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
        company.setCompanyType( companyTypeDtoToCompanyType( companyDto.getCompanyType() ) );

        return company;
    }

    @Override
    public EmployeeDto employeeToDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setEntryDate( employee.getStartOfWork() );
        employeeDto.setTitle( employee.getPosition() );
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

        employee.setStartOfWork( employeeDto.getEntryDate() );
        employee.setPosition( employeeDto.getTitle() );
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

    protected CompanyTypeDto companyTypeToCompanyTypeDto(CompanyType companyType) {
        if ( companyType == null ) {
            return null;
        }

        CompanyTypeDto companyTypeDto = new CompanyTypeDto();

        companyTypeDto.setId( companyType.getId() );
        if ( companyType.getName() != null ) {
            companyTypeDto.setName( companyType.getName().name() );
        }

        return companyTypeDto;
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

    protected CompanyType companyTypeDtoToCompanyType(CompanyTypeDto companyTypeDto) {
        if ( companyTypeDto == null ) {
            return null;
        }

        CompanyType companyType = new CompanyType();

        companyType.setId( companyTypeDto.getId() );
        if ( companyTypeDto.getName() != null ) {
            companyType.setName( Enum.valueOf( BusinessType.class, companyTypeDto.getName() ) );
        }

        return companyType;
    }
}
