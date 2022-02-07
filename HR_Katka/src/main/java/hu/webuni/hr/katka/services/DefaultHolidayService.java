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

  @Override
  @Transactional
  public Holiday addHolidayRequest(Holiday request, Long employeeId) {
    Employee employee = getEmployeeOrThrow(employeeId);
    request.setApproved(false);
    employee.addHolidayRequest(request);
    return holidayRepository.save(request);
  }

  @Override
  @Transactional
  public Holiday approveHoliday(Long id) {
    Holiday holiday = getHolidayOrThrow(id);
    isApproved(holiday);
    holiday.setApproved(true);
    return holidayRepository.save(holiday);
  }

  @Override
  @Transactional
  public Holiday modifyHoliday(Holiday holiday, Long employeeId) {
    Holiday originalHoliday = getHolidayById(holiday.getId());
    Employee employee = getEmployeeOrThrow(employeeId);
    isApproved(originalHoliday);
    holiday.setEmployee(employee);
    holiday.setApproved(false);
    holiday.setCreatedAt();
    employeeRepository.save(employee);
    return holidayRepository.save(holiday);
  }

  @Override
  public void deleteHolidayRequest(Long id) {
    Holiday holidayById = getHolidayById(id);
    isApproved(holidayById);
    holidayById.getEmployee().getHolidayRequests()
        .remove(holidayById); //LazyInitializationException
  }

  @Override
  public List<Holiday> getHolidayRequestOfEmployee(Long employeeId) {
//    Employee employee = getEmployeeOrThrow(employeeId); //todo
//    return holidayRepository;
    return null;
  }

  private void isApproved(Holiday holiday) {
    List<Holiday> allByApprovedIsTrue = holidayRepository.findAllByApprovedIsTrue();
    if (allByApprovedIsTrue.contains(holiday)) {
      throw new IllegalArgumentException("This is already an approved holiday request.");
    }
  }

  private Employee getEmployeeOrThrow(Long id) {
    validateFields(id, "Id cannot be null.");
    Optional<Employee> employeeById = employeeRepository.findById(id);
    if (employeeById.isEmpty()) {
      throw new NotFoundException("There is no employee with the provided id.");
    }
    return employeeById.get();
  }

  private Holiday getHolidayOrThrow(long id) {
    Optional<Holiday> byId = holidayRepository.findById(id);
    if (byId.isEmpty()) {
      throw new NotFoundException("There is no holiday request with the provided id.");
    }
    return byId.get();
  }
}
