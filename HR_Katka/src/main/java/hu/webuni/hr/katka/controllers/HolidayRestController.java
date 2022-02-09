package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.dtos.HolidayDto;
import hu.webuni.hr.katka.entities.Holiday;
import hu.webuni.hr.katka.mapper.HolidayMapper;
import hu.webuni.hr.katka.services.HolidayService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/holidays")
public class HolidayRestController {

  @Autowired
  HolidayService holidayService;

  @Autowired
  HolidayMapper holidayMapper;

  @GetMapping
  public List<HolidayDto> getAllHolidayRequests() {
    List<Holiday> holidays = holidayService.getAllHolidays();
    return holidayMapper.holidaysToDtos(holidays);
  }

  @GetMapping("/{id}")
  public HolidayDto getHolidayById(@PathVariable Long id) {
    return holidayMapper.holidayToDto(holidayService.getHolidayById(id));
  }

  @PostMapping
  public HolidayDto addHolidayRequest(@RequestBody @Valid HolidayDto request) {
    Holiday holiday = holidayMapper.dtoToHolidayRequest(request);
    Holiday holidayRequest = holidayService.addHolidayRequest(holiday, request.getEmployeeId());
    return holidayMapper.holidayToDto(holidayRequest);
  }

  @PutMapping("/{id}")
  public HolidayDto modifyHolidayRequest(@RequestBody @Valid HolidayDto request,
                                         @PathVariable Long id) {
    request.setId(id);
    request.setEmployeeId(request.getEmployeeId());
    Holiday holiday = holidayMapper.dtoToHolidayRequest(request);
    Holiday modifiedHolidayRequest = holidayService.modifyHoliday(holiday, request.getEmployeeId());
    return holidayMapper.holidayToDto(modifiedHolidayRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteHolidayRequest(@PathVariable Long id) {
    holidayService.deleteHolidayRequest(id);
  }

  @PutMapping("/approve/{id}")
  public HolidayDto approveHolidayRequest(@PathVariable Long id, @RequestParam Boolean status) {
    Holiday holiday = holidayService.approveHoliday(id, status);
    return holidayMapper.holidayToDto(holiday);
  }

  @GetMapping("/employees/{employeeId}")
  public List<HolidayDto> getHolidayRequestOfEmployee(@PathVariable Long employeeId) {
    List<Holiday> holidayRequestOfEmployee = holidayService.getHolidayRequestOfEmployee(employeeId);
    return holidayMapper.holidaysToDtos(holidayRequestOfEmployee);
  }

}
