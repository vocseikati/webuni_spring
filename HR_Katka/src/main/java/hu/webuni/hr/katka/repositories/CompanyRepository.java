package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.Company;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

//  @Query("select ")
//  List<Company> findByEmployeesOfCompanyOverNumber(Integer numberOfEmployees);
}
