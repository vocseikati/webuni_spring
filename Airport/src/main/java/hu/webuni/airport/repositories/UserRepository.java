package hu.webuni.airport.repositories;

import hu.webuni.airport.models.AirportUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AirportUser, String> {
}
