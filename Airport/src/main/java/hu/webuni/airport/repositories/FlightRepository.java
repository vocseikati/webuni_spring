package hu.webuni.airport.repositories;

import hu.webuni.airport.configurations.models.Airport;
import hu.webuni.airport.configurations.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

  Long countByIata(String iata);

  Long countByIataAndIdNot(String iata, long id);
}
