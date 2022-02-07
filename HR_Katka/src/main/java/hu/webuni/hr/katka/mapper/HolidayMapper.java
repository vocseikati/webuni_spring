package hu.webuni.hr.katka.mapper;

import hu.webuni.hr.katka.dtos.HolidayDto;
import hu.webuni.hr.katka.entities.Holiday;
import java.util.List;
import javax.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

  List<HolidayDto> holidaysToDtos(List<Holiday> holiday);

  @Mapping(source = "employee.id", target = "employeeId")
  HolidayDto holidayToDto(Holiday holidayRequest);

//  @Mapping(target = "employee.id", ignore = true)
  @Mapping(source = "employeeId", target = "employee.id")
  Holiday dtoToHolidayRequest(HolidayDto holidayDto);

  List<Holiday> dtosToHolidays(List<HolidayDto> holidayDtos);
}
