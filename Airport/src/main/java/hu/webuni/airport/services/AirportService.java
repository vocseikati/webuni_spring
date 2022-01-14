package hu.webuni.airport.services;

import hu.webuni.airport.configurations.models.Airport;
import hu.webuni.airport.configurations.models.Flight;
import hu.webuni.airport.repositories.AirportRepository;
import hu.webuni.airport.repositories.FlightRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AirportService {

  @Autowired
  AirportRepository airportRepository;

  @Autowired
  FlightRepository flightRepository;

//  @PersistenceContext
//  EntityManager em;

  @Transactional
  public Airport save(Airport airport) {
    checkUniqueIata(airport.getIata(), null);
//    em.persist(airport);
    return airportRepository.save(airport);
  }

  @Transactional
  public Airport update(Airport airport) {
    checkUniqueIata(airport.getIata(), airport.getId());
    if (airportRepository.existsById(airport.getId())){
      return airportRepository.save(airport);
    }
//    return em.merge(airport); //insert or update!
    throw new NoSuchElementException();
  }

  private void checkUniqueIata(String iata, Long id) {
    boolean forUpdate = id != null;
    Long count =
        forUpdate ? airportRepository.countByIataAndIdNot(iata, id) : airportRepository.countByIata(iata);

    if (count > 0) {
      throw new NonUniqueException(iata);
    }
  }

  public List<Airport> findAll() {
//    return em.createQuery("select a from Airport a", Airport.class).getResultList();
    return airportRepository.findAll();
  }

  public Optional<Airport> findById(long id) {
//    return em.find(Airport.class, id);
    return airportRepository.findById(id);
  }

  @Transactional
  public void delete(long id) {
//    em.remove(findById(id));
    airportRepository.deleteById(id);
  }

  @Transactional
  public void createFlight(){
    Flight flight = new Flight();
    flight.setFlightNumber("adhfads");
    flight.setTakeoff(airportRepository.findById(1L).get());
    flight.setTakeoff(airportRepository.findById(3L).get());
    flight.setTakeoffTime(LocalDateTime.of(2022,1,1,8,6));
    flightRepository.save(flight);
  }

}
