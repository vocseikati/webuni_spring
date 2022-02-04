package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.HolidayDto;
import hu.webuni.hr.katka.entities.Holiday;
import hu.webuni.hr.katka.mapper.HolidayMapper;
import hu.webuni.hr.katka.services.HolidayService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/holidays")
public class HolidayRestController {

  @Autowired
  HolidayService holidayService;

  @Autowired
  HolidayMapper holidayMapper;

  @GetMapping
  public List<HolidayDto> getAllHolidayRequests(){
    List<Holiday> holidays = holidayService.getAllHolidays();
    return holidayMapper.holidaysToDtos(holidays);
  }

  @GetMapping("/{id}")
  public HolidayDto getHolidayById(@PathVariable long id){
    return holidayMapper.holidayToDto(holidayService.getHolidayById(id));
  }

  
}
