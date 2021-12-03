package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.configurations.HrConfigurationProperties;
import hu.webuni.hr.katka.models.Employee;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartEmployeeService implements EmployeeService {

  @Autowired
  HrConfigurationProperties config;

  @Override
  public int getPayRaisePercent(Employee employee) {

    TreeMap<Double, Integer> limits = config.getSmart().getLimits();
    double workedYears =
        ChronoUnit.MONTHS.between(employee.getStartOfWork(), LocalDateTime.now()) / 12.0;
    Double limit = limits.floorKey(workedYears);
    if (limit == null){
      return 0;
    }
    return limit.intValue();
//    másik megoldás:
//    Map.Entry<Double, Integer> floorEntry = limits.floorEntry(workedYears);
//    return floorEntry == null ? 0 : floorEntry.getValue();


//    LocalDate now = LocalDateTime.now().toLocalDate();
//    LocalDate startOfWorkDate = employee.getStartOfWork().toLocalDate();
//    Period period = Period.between(startOfWorkDate, now);
//    if (period.getYears() >= config.getSmart().getLimit1()) {
//      return config.getSmart().getPercent1();
//    }
//    if (period.getYears() >= config.getSmart().getLimit2()) {
//      return config.getSmart().getPercent2();
//    }
//    if (period.getYears() >= config.getSmart().getLimit3() && period.getMonths() >= 6) {
//      return config.getSmart().getPercent3();
//    }
//    return 0;
  }
}
