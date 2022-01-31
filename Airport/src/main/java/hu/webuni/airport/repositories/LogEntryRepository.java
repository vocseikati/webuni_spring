package hu.webuni.airport.repositories;

import hu.webuni.airport.models.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

}
