package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.configurations.HrConfigurationProperties;
import hu.webuni.hr.katka.models.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService extends CRUDEmployeeService implements EmployeeService{

  @Autowired
  HrConfigurationProperties config;

  @Override
  public int getPayRaisePercent(Employee employee) {
    return config.getDef().getPercent();
  }
}
