package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Employee;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findBySalaryGreaterThan(Integer minSalary);

  List<Employee> findEmployeesByPosition(String position);

  List<Employee> findAllByNameStartsWithIgnoreCase(String name);

  List<Employee> findByStartOfWorkBetween(LocalDateTime startDate, LocalDateTime endDate);

}
