package hu.webuni.hr.katka.services;

import static hu.webuni.hr.katka.services.ValidationService.validateFields;

import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.entities.Holiday;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import hu.webuni.hr.katka.repositories.HolidayRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultHolidayService implements HolidayService {

  @Autowired
  HolidayRepository holidayRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  @Override
  public List<Holiday> getAllHolidays() {
    return holidayRepository.findAll();
  }

  @Override
  public Holiday getHolidayById(long id) {
    return getHolidayOrThrow(id);
  }

  private Holiday getHolidayOrThrow(long id) {
    Optional<Holiday> byId = holidayRepository.findById(id);
    if (byId.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return byId.get();
  }

  @Override
  @Transactional
  public Holiday addHolidayRequest(Holiday request, Long employeeId) {
    Employee employee = getEmployeeOrThrow(employeeId);
    request.setApproved(false);
    employee.addHolidayRequest(request);
    return holidayRepository.save(request);
  }

  @Override
  public Holiday approveHoliday(long id) {
    return null;
  }

  @Override
  public Holiday modifyHoliday(Holiday holiday) {
    return null;
  }

  @Override
  public void deleteHolidayRequest(long id) {

  }

  private Employee getEmployeeOrThrow(Long id) {
    validateFields(id, "Id cannot be null.");
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }
}
