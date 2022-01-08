package hu.webuni.airport.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hu.webuni.airport.dtos.AirportDto;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirportControllerIT {

  private static final String BASE_URI = "/api/airports";

  @Autowired
  WebTestClient webTestClient;

  @Test
  void testThatCreatedAirportIsListed() {
    List<AirportDto> airportsBefore = getAllAirports();
    AirportDto newAirport = new AirportDto(5, "faésdjfa", "IGB");
    createAirport(newAirport);

    List<AirportDto> airportsAfter = getAllAirports();
    assertThat(airportsAfter.subList(0, airportsBefore.size()))
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyElementsOf(airportsBefore);
    assertThat(airportsAfter.get(airportsAfter.size() - 1))
        .usingRecursiveComparison()
        .isEqualTo(newAirport);
  }

  private void createAirport(AirportDto newAirport) {
    webTestClient.post().uri(BASE_URI).bodyValue(newAirport)
        .exchange().expectStatus().isOk();
  }

  private List<AirportDto> getAllAirports() {
    List<AirportDto> responseList = webTestClient
        .get()
        .uri(BASE_URI)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(AirportDto.class)
        .returnResult().getResponseBody();
    Objects.requireNonNull(responseList).sort(Comparator.comparingLong(AirportDto::getId));
    return responseList;
  }

}