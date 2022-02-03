package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Employee;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee, Long>,
    JpaSpecificationExecutor<Employee> {

  List<Employee> findBySalaryGreaterThan(Integer minSalary);

  List<Employee> findEmployeesByPositionName(String position);

  List<Employee> findAllByNameStartsWithIgnoreCase(String name);

  List<Employee> findByStartOfWorkBetween(LocalDateTime startDate, LocalDateTime endDate);

}
