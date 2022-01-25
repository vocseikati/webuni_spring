package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Position;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {

  boolean existsByName(String name);

  List<Position> findByName(String positionName);
}
