package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Company;
import hu.webuni.hr.katka.entities.Employee;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findBySalaryGreaterThan(Integer minSalary);

  @Query(value = "SELECT DISTINCT company_id FROM employees WHERE salary > :salaryLimit",
      nativeQuery = true)
  List<Long> findCompanyIdsByEmployeeSalaryGraterThan(Integer salaryLimit);

  List<Employee> findEmployeesByPosition(String position);

  List<Employee> findAllByNameStartsWithIgnoreCase(String name);

  List<Employee> findByStartOfWorkBetween(LocalDateTime startDate, LocalDateTime endDate);

}
