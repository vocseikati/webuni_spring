package hu.webuni.hr.katka;

import hu.webuni.hr.katka.services.InitDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

  @Autowired
  private InitDBService initDBService;

  public static void main(String[] args) {
    SpringApplication.run(HrApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    initDBService.insertTestData();
//    initDBService.clearDB();
  }
}
