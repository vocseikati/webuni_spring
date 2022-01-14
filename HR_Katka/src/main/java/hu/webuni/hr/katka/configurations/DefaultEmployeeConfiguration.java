package hu.webuni.hr.katka.configurations;

import hu.webuni.hr.katka.services.DefaultEmployeeService;
import hu.webuni.hr.katka.services.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class DefaultEmployeeConfiguration {

  @Bean
  public EmployeeService employeeService() {
    return new DefaultEmployeeService();
  }
}
