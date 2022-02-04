package hu.webuni.airport.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;

import hu.webuni.airport.models.Airport;
import hu.webuni.airport.models.Flight;
import hu.webuni.airport.repositories.AirportRepository;
import hu.webuni.airport.repositories.FlightRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
class AirportServiceIT {

  @Autowired
  AirportService airportService;

  @Autowired
  AirportRepository airportRepository;

  @Autowired
  FlightRepository flightRepository;

  @BeforeEach
  public void init() {
    flightRepository.deleteAll();
    airportRepository.deleteAll();
  }

  @Test
  void testCreateFlight() {
    String flightNumber = "ABC123";
    long takeoff = createAirport("airport1", "iata1");
    long landing = createAirport("airport2", "iata2");
    LocalDateTime dateTime = LocalDateTime.now();
    long flightId = createFlight(flightNumber, takeoff, landing, dateTime);

    Optional<Flight> savedFlightOptional = flightRepository.findById(flightId);
    assertThat(savedFlightOptional).isNotEmpty();
    Flight savedFlight = savedFlightOptional.get();
    assertThat(savedFlight.getFlightNumber()).isEqualTo(flightNumber);
    assertThat(savedFlight.getTakeoffTime()).isCloseTo(dateTime, within(1, ChronoUnit.MICROS));
    assertThat(savedFlight.getTakeoff().getId()).isEqualTo(takeoff);
    assertThat(savedFlight.getLanding().getId()).isEqualTo(landing);
  }

  @Test
  void testFindFlightsByExample() {
    long airport1Id = createAirport("airport1", "iata1");
    long airport2Id = createAirport("airport2", "iata2");
    long airport3Id = createAirport("airport3", "2iata");
    createAirport("airport4", "3ata1");
    LocalDateTime takeoff = LocalDateTime.of(2021, 4, 23, 8, 0, 0);
    long flight1 = createFlight("ABC123", airport1Id, airport3Id, takeoff);
    long flight2 = createFlight("ABC1234", airport2Id, airport3Id, takeoff.plusHours(2));
    createFlight("BC123", airport1Id, airport3Id, takeoff);
    createFlight("ABC123", airport1Id, airport3Id, takeoff.plusDays(1));
    createFlight("ABC123", airport3Id, airport3Id, takeoff);

    Flight example = new Flight();
    example.setFlightNumber("ABC123");
    example.setTakeoffTime(takeoff);
    example.setTakeoff(new Airport("sasa", "iata"));
    List<Flight> foundFlights = this.airportService.findFlightsByExample(example);
    assertThat(foundFlights.stream().map(Flight::getId).collect(Collectors.toList()))
        .containsExactly(flight1, flight2);
  }

  private long createAirport(String name, String iata) {
    return airportRepository.save(new Airport(name, iata)).getId();
  }

  private long createFlight(String flightNumber, long takeoff, long landing,
                            LocalDateTime dateTime) {
    return airportService.createFlight(flightNumber, takeoff, landing, dateTime).getId();
  }
}