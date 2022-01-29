package hu.webuni.airport.services;

import hu.webuni.airport.models.Flight;
import org.springframework.data.jpa.domain.Specification;

public class FlightSpecification {

  public static Specification<Flight> hasId(Long id) {
    return (root, cq, cb) -> cb.equal(root.get("id"), id);
  }
}
