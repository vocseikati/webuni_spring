package hu.webuni.airport.controllers;

import hu.webuni.airport.dtos.AirportDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AirportTLController {

  private List<AirportDto> allAirports = new ArrayList<>();

  {
    allAirports.add(new AirportDto(1, "Ferenc Liszt Airport", "BUD"));
  }

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/airports")
//  public String listAirports(Map<String, Object> model) {
//    model.put("airports", allAirports);
//    model.put("newAirport", new AirportDto());
//    return "airports";
//  }
  public String listAirports(Model model) {
    model.addAttribute("airports", allAirports);
    model.addAttribute("newAirport", new AirportDto());
    return "airports";
  }

  @PostMapping("/airports")
  public String addAirport(AirportDto airport) {
    allAirports.add(airport);
    return "redirect:airports";
  }
}
