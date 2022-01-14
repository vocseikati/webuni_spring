package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.models.Employee;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {

  //megadja hány százalékos fizetésemelés jár egy adott alkalmazottnak
  int getPayRaisePercent(Employee employee);
  Employee save(Employee employee);
  List<Employee> findAll();
  Employee findById(Long id);
  void delete(Long id);
  Employee modifyEmployee (Long id, Employee employee);
  List<Employee> getEmployeesOverLimit(Integer limit);
  List<Employee> findByPosition(String position);
  List<Employee> findByName(String name);
  List<Employee> findByStartOfWorkBetween(LocalDateTime startDate, LocalDateTime endDate);
}
