package hu.webuni.hr.katka.services;

import static hu.webuni.hr.katka.services.ValidationService.validateFields;

import hu.webuni.hr.katka.entities.Employee;
import hu.webuni.hr.katka.entities.Holiday;
import hu.webuni.hr.katka.exceptions.NotFoundException;
import hu.webuni.hr.katka.repositories.EmployeeRepository;
import hu.webuni.hr.katka.repositories.HolidayRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class DefaultHolidayService implements HolidayService {

  @Autowired
  HolidayRepository holidayRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  EmployeeService employeeService;

  @Override
  public List<Holiday> getAllHolidays() {
//    return holidayRepository.findAllHoliday();
    return holidayRepository.findAll();
  }

  @Override
  public Holiday getHolidayById(Long id) {
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
  public Holiday approveHoliday(Long holidayId, Long bossId, Boolean status) {
    Holiday holiday = getHolidayOrThrow(holidayId);
    isApproved(holiday);
    Employee employee = holiday.getEmployee();
    employee.setBoss(employeeService.findById(bossId)); //teszteléshez
    Employee boss = employee.getBoss();
    if (boss == null || !employee.getBoss().getId().equals(bossId)){
      throw new IllegalArgumentException("Boss id invalid.");
    }
    holiday.setBoss(employeeService.findById(bossId));
    holiday.setApproved(status);
    holiday.setApprovedAt(LocalDateTime.now());
    return holiday;
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
    holidayRepository.deleteById(id);
  }

  @Override
  public List<Holiday> getHolidayRequestOfEmployee(Long employeeId) {
    getEmployeeOrThrow(employeeId);
    return holidayRepository.findAllByEmployee_Id(employeeId);
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

  private Holiday getHolidayOrThrow(Long id) {
    Optional<Holiday> byId = holidayRepository.findById(id);
    if (byId.isEmpty()) {
      throw new NotFoundException("There is no holiday request with the provided id.");
    }
    return byId.get();
  }

  public List<Holiday> findHolidaysByExample(Holiday example) {
    LocalDateTime createDateTimeStart = example.getCreatedAt();
    String employeeName = example.getEmployee().getName();
    Boolean approved = example.getApproved();
    LocalDate startOfHolidayRequest = example.getStartDate();
    LocalDate endOfHolidayRequest = example.getEndDate();

    Specification<Holiday> spec = Specification.where(null);

    if (approved != null) {
      spec = spec.and(HolidaySpecification.hasApproved(approved));
    }
//    if (createDateTimeStart != null) {
//      spec = spec.and(
//          HolidaySpecification.createDateIsBetween(createDateTimeStart, createDateTimeEnd));
//    }
    if (StringUtils.hasText(employeeName)) {
      spec = spec.and(HolidaySpecification.hasEmployeeName(employeeName));
    }

    if (startOfHolidayRequest != null) {
      spec = spec.and(HolidaySpecification.isEndDateGreaterThan(startOfHolidayRequest));
    }
    if (endOfHolidayRequest != null) {
      spec = spec.and(HolidaySpecification.isStartDateLessThan(endOfHolidayRequest));
    }
    return holidayRepository.findAll(spec);
  }


}
