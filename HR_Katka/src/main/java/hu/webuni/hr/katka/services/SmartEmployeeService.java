package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.configurations.HrConfigurationProperties;
import hu.webuni.hr.katka.entities.Employee;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartEmployeeService extends CRUDEmployeeService {

  @Autowired
  HrConfigurationProperties config;

  @Override
  public int getPayRaisePercent(Employee employee) {

    TreeMap<Double, Integer> limits = config.getSmart().getLimits();
    double workedYears =
        ChronoUnit.MONTHS.between(employee.getStartOfWork(), LocalDateTime.now()) / 12.0;
    Double limit = limits.floorKey(workedYears);
    if (limit == null) {
      return 0;
    }
    return limit.intValue();
//    másik megoldás:
//    Map.Entry<Double, Integer> floorEntry = limits.floorEntry(workedYears);
//    return floorEntry == null ? 0 : floorEntry.getValue();
  }
}
