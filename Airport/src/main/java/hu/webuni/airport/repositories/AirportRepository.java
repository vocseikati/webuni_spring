package hu.webuni.airport.repositories;

import hu.webuni.airport.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {

  Long countByIata(String iata);

  Long countByIataAndIdNot(String iata, long id);
}
