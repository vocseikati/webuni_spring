package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.HolidayDto;
import hu.webuni.hr.katka.entities.Holiday;
import java.util.List;
import javax.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

  List<HolidayDto> holidaysToDtos(List<Holiday> holidays);

  @Mapping(source = "employee.id", target = "employeeId")
  @Mapping(source = "boss.id", target = "bossId")
  HolidayDto holidayToDto(Holiday holidayRequest);

//  @Mapping(target = "employee.id", ignore = true)
  @Mapping(target = "employee", ignore = true)
  @Mapping(target = "boss", ignore = true)
  Holiday dtoToHolidayRequest(HolidayDto holidayDto);

  List<Holiday> dtosToHolidays(List<HolidayDto> holidayDtos);
}
