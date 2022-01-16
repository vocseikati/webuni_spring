package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
