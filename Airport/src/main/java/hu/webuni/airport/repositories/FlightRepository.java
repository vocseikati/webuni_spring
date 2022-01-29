package hu.webuni.airport.repositories;

import hu.webuni.airport.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}
