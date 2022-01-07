package hu.webuni.airport.controllers;

import hu.webuni.airport.configurations.models.Airport;
import hu.webuni.airport.dtos.AirportDto;
import hu.webuni.airport.mapper.AirportMapper;
import hu.webuni.airport.services.AirportService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/airports")
public class AirportRestController {

  @Autowired
  AirportService airportService;

  @Autowired
  AirportMapper airportMapper;

  @GetMapping
  public List<AirportDto> getAll() {
    return airportMapper.airportsToDtos(airportService.findAll());
  }

  @GetMapping("/{id}")
  public AirportDto getById(@PathVariable long id) {
    Airport airport = airportService.findById(id);

    if (airport != null){
      return airportMapper.airportToDto(airport);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto) {
        Airport airport = airportService.save(airportMapper.dtoToAirport(airportDto));
        return airportMapper.airportToDto(airport);
  }


//
//  @PutMapping("/{id}") //ami az url-ben jön, az a mérvadó!
//  public ResponseEntity<AirportDto> modifyAirport(@RequestBody AirportDto airportDto,
//                                                  @PathVariable long id) {
//    checkUniqueIata(airportDto.getIata());
//    if (!airports.containsKey(id)) {
//      return ResponseEntity.notFound().build();
//    }
//    airportDto.setId(id);
//    airports.put(id, airportDto);
//    return ResponseEntity.ok(airportDto);
//  }
//
//  @DeleteMapping("/{id}")
//  public void deleteAirport(@PathVariable long id) {
//    airports.remove(id);
//  }

}
