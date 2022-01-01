package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.models.Employee;

public interface EmployeeService {

  //megadja hány százalékos fizetésemelés jár egy adott alkalmazottnak
  int getPayRaisePercent(Employee employee);
}
