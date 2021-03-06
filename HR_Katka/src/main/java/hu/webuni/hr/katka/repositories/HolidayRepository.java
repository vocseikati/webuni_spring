package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Holiday;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface HolidayRepository
    extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {

  @EntityGraph(attributePaths = "employee")
  @Query("select h from Holiday h")
  List<Holiday> findAllHoliday();

  List<Holiday> findAllByApprovedIsTrue();

  List<Holiday> findAllByEmployee_Id(Long employeeId);
}
