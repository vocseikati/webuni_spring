package hu.webuni.airport;

import hu.webuni.airport.services.DefaultDiscountService;
import hu.webuni.airport.services.DiscountService;
import hu.webuni.airport.services.PriceService;
import hu.webuni.airport.services.SpecialDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AirportApplication implements CommandLineRunner {

  @Autowired
  PriceService priceService;

  public static void main(String[] args) {
    SpringApplication.run(AirportApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println(priceService.getFinalPrice(200));
    System.out.println(priceService.getFinalPrice(20000));
  }

//  @Bean //ezzel nem működik, a config osztályban működik - @Primary-vel igen
//  public DiscountService discountService(){
//    return new SpecialDiscountService();
//  }
}
