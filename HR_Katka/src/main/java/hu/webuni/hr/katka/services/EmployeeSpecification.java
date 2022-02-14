package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.entities.Company_;
import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.entities.Employee_;
import hu.webuni.hr.katka.entities.Position_;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

  public static Specification<Employee> hasId(Long id) {
    return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
  }

  public static Specification<Employee> hasName(String name) {
    return (root, query, criteriaBuilder) -> criteriaBuilder
        .like(criteriaBuilder.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
  }

  public static Specification<Employee> hasPosition(String positionName) {
    return (root, cq, cb) -> cb
        .equal(root.get(Employee_.position).get(Position_.name), positionName);
  }

  public static Specification<Employee> hasSalary(int salary) {
    double min = salary * 0.95;
    double max = salary * 1.05;
    return (root, cq, cb) -> cb.between(root.get(Employee_.salary), (int) min, (int) max);
  }

  public static Specification<Employee> hasEntryDate(LocalDateTime entryDate) {
    LocalDateTime startOfDay = LocalDateTime.of(entryDate.toLocalDate(), LocalTime.of(0, 0));
    return (root, cq, cb) -> cb
        .between(root.get(Employee_.startOfWork), startOfDay, startOfDay.plusDays(1));
  }

  public static Specification<Employee> hasCompany(String companyName) {
    return (root, cq, cb) -> cb
        .like(cb.lower(root.get(Employee_.company).get(Company_.name)),
            companyName.toLowerCase() + "%");
  }
}
