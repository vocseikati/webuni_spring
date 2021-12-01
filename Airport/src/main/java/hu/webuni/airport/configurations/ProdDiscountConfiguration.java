package hu.webuni.airport.configurations;

import hu.webuni.airport.services.DefaultDiscountService;
import hu.webuni.airport.services.DiscountService;
import hu.webuni.airport.services.SpecialDiscountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdDiscountConfiguration {

  @Bean
  public DiscountService discountService() {
    return new SpecialDiscountService();
  }
}
