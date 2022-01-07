package hu.webuni.airport.controllers;

import hu.webuni.airport.dtos.AirportDto;
import hu.webuni.airport.services.NonUniqueException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/airports")
public class AirportRestController {

  private Map<Long, AirportDto> airports = new HashMap<>();

  {
    airports.put(1L, new AirportDto(1, "abc", "XYZ"));
    airports.put(2L, new AirportDto(2, "def", "URW"));
  }

  @GetMapping
  public List<AirportDto> getAll() {
    return new ArrayList<>(airports.values());
  }

  @GetMapping("/{id}")
  public AirportDto getById(@PathVariable long id) {
    AirportDto airportDto = airports.get(id);
//    if (airportDto != null){
//      return ResponseEntity.ok(airportDto);
//    }
//    return ResponseEntity.notFound().build();
    if (airportDto != null) {
      return airportDto;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto) {
        checkUniqueIata(airportDto.getIata());
    airports.put(airportDto.getId(), airportDto);
    return airportDto;
  }

  private void checkUniqueIata(String iata) {
    Optional<AirportDto> airportWithSameIata =
        airports.values().stream().filter(a -> a.getIata().equals(iata)).findAny();
    if (airportWithSameIata.isPresent()){
      throw new NonUniqueException(iata);
    }
  }

  @PutMapping("/{id}") //ami az url-ben jön, az a mérvadó!
  public ResponseEntity<AirportDto> modifyAirport(@RequestBody AirportDto airportDto,
                                                  @PathVariable long id) {
    checkUniqueIata(airportDto.getIata());
    if (!airports.containsKey(id)) {
      return ResponseEntity.notFound().build();
    }
    airportDto.setId(id);
    airports.put(id, airportDto);
    return ResponseEntity.ok(airportDto);
  }

  @DeleteMapping("/{id}")
  public void deleteAirport(@PathVariable long id) {
    airports.remove(id);
  }

}
