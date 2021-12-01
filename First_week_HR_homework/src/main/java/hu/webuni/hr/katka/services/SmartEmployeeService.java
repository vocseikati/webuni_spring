package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.configurations.HrConfigurationProperties;
import hu.webuni.hr.katka.models.Employee;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartEmployeeService implements EmployeeService {

  @Autowired
  HrConfigurationProperties config;

  @Override
  public int getPayRaisePercent(Employee employee) {

    LocalDate now = LocalDateTime.now().toLocalDate();
    LocalDate startOfWorkDate = employee.getStartOfWork().toLocalDate();
    Period period = Period.between(startOfWorkDate, now);

    if (period.getYears() >= config.getSmart().getLimit1()) {
      return config.getSmart().getPercent1();
    }
    if (period.getYears() >= config.getSmart().getLimit2()) {
      return config.getSmart().getPercent2();
    }
    if (period.getYears() >= config.getSmart().getLimit3() && period.getMonths() >= 6) {
      return config.getSmart().getPercent3();
    }
    return 0;
  }
}
