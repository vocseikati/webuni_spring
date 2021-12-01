package hu.webuni.airport.services;

import hu.webuni.airport.configurations.AirportConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultDiscountService implements DiscountService{

  @Autowired
  AirportConfigProperties config;

  @Override
  public int getDiscountPercent(int totalPrice) {
    return config.getDiscount().getDef().getPercent();
  }
}
