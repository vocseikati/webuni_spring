package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.BusinessType;
import hu.webuni.hr.katka.entities.CompanyType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
  boolean existsByName(BusinessType name);

  List<CompanyType> findByName(String companyTypeName);
}
