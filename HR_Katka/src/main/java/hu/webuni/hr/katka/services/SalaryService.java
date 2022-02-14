package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.entities.PositionDetailsByCompany;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import hu.webuni.hr.katka.repositories.PositionDetailsByCompanyRepository;
import hu.webuni.hr.katka.repositories.PositionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

  private EmployeeService employeeService;
  private PositionRepository positionRepository;
  private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
  private EmployeeRepository employeeRepository;

  public SalaryService(EmployeeService employeeService, PositionRepository positionRepository,
                       PositionDetailsByCompanyRepository positionDetailsByCompanyRepository,
                       EmployeeRepository employeeRepository) {
    super();
    this.employeeService = employeeService;
    this.positionRepository = positionRepository;
    this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
    this.employeeRepository = employeeRepository;
  }

  public void setNewSalary(Employee employee) {
    int newSalary = employee.getSalary() * (100 + employeeService.getPayRaisePercent(employee)) / 100;
    employee.setSalary(newSalary);
  }

  @Transactional
  public void raiseMinSalary(Long companyId, String positionName, int minSalary) {
//		positionRepository.findByName(positionName)
//		.forEach(p ->{
//			p.setMinSalary(minSalary);
//			p.getEmployees().forEach(e ->{
//				if(e.getSalary()<minSalary)
//					e.setSalary(minSalary);
//			});
//		});

    List<PositionDetailsByCompany> positionDetails = positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyId);
    positionDetails.forEach(pd -> pd.setMinSalary(minSalary));
    positionDetailsByCompanyRepository.updateSalaries(companyId, positionName, minSalary);
  }

}
