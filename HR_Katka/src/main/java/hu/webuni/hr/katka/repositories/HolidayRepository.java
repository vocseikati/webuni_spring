package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
