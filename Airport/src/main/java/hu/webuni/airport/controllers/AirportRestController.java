package hu.webuni.airport.controllers;

import hu.webuni.airport.dtos.AirportDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airports")
public class AirportRestController {

  private Map<Long, AirportDto> airports = new HashMap<>();
  {
    airports.put(1L, new AirportDto(1, "abc", "XYZ"));
    airports.put(2L, new AirportDto(2, "def", "URW"));
  }
  @GetMapping
  public List<AirportDto> getAll(){
    return new ArrayList<>(airports.values());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AirportDto> getById(@PathVariable long id){
    AirportDto airportDto = airports.get(id);
    if (airportDto != null){
      return ResponseEntity.ok(airportDto);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public AirportDto createAirport(@RequestBody AirportDto airportDto){
    airports.put(airportDto.getId(), airportDto);
    return airportDto;
  }

  @PutMapping("/{id}") //ami az url-ben jön, az a mérvadó!
  public ResponseEntity<AirportDto> modifyAirport(@RequestBody AirportDto airportDto,
  @PathVariable long id){
    if (!airports.containsKey(id)){
      return ResponseEntity.notFound().build();
    }
    airportDto.setId(id);
    airports.put(id, airportDto);
    return ResponseEntity.ok(airportDto);
  }

  @DeleteMapping("/{id}")
  public void deleteAirport(@PathVariable long id){
    airports.remove(id);
  }

}
