package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
