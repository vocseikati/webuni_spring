package hu.webuni.airport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private final DiscountService discountService;

  public PriceService(DiscountService discountService) {
    this.discountService = discountService;
  }

  public int getFinalPrice (int price){
    return (int) (price/100.0 * (100 - discountService.getDiscountPercent(price)));
  }
}
