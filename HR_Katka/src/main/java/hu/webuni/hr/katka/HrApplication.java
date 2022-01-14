package hu.webuni.hr.katka;

import hu.webuni.hr.katka.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

  @Autowired
  private SalaryService salaryService;

  public static void main(String[] args) {
    SpringApplication.run(HrApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    Employee testEmployee1 =
//        new Employee(1L, "Kata", "leader", 100000,
//            LocalDateTime.of(2011, 9, 1, 8, 0,0));
//
//    Employee testEmployee2 = new Employee(2L, "Laca", "referent", 90000,
//        LocalDateTime.of(2016, 9, 1, 8, 0, 0));
//
//    Employee testEmployee3 = new Employee(3L, "Zöldfülű", "assistant", 50000,
//        LocalDateTime.of(2019, 7, 1, 8, 0, 0));
//
//    salaryService.setSalary(testEmployee1);
//    System.out.println(testEmployee1.getSalary());
//
//    salaryService.setSalary(testEmployee2);
//    System.out.println(testEmployee2.getSalary());
//
//    salaryService.setSalary(testEmployee3);
//    System.out.println(testEmployee3.getSalary());
  }
}
