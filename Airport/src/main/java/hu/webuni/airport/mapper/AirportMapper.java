package hu.webuni.airport.mapper;

import org.mapstruct.Mapper;

import hu.webuni.airport.models.Airport;
import hu.webuni.airport.dtos.AirportDto;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportMapper {

  List<AirportDto> airportsToDtos(List<Airport> airports);

  AirportDto airportToDto(Airport airport);

  Airport dtoToAirport(AirportDto airportDto);
}
