package hu.webuni.airport.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PriceServiceWithNonProdIT {

  @Autowired
  PriceService priceService;

  @Test
  void testGetFinalPrice(){
    int newPrice = priceService.getFinalPrice(100);
    assertThat(newPrice).isEqualTo(90);
  }

  @Test
  void testGetFinalPriceWithHighPrice(){
    int newPrice = priceService.getFinalPrice(11000);
    assertThat(newPrice).isEqualTo(9900);
  }

  
}