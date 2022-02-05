package hu.webuni.hr.katka.services;

import hu.webuni.hr.katka.entities.Holiday;
import java.util.List;

public interface HolidayService {

  List<Holiday> getAllHolidays();

  Holiday getHolidayById(long id);

  Holiday addHolidayRequest(Holiday request, Long employeeId);

  Holiday approveHoliday(long id);

  Holiday modifyHoliday(Holiday holiday);

  void deleteHolidayRequest(long id);
}
