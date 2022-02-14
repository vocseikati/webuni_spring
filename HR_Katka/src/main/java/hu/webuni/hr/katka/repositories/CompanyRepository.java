package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.AverageSalaryByPosition;
import hu.webuni.hr.katka.entities.Company;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

  //lekérdezheted azon cégeket, melyeknek van egy adott limitnél nagyobb fizetésű alkalmazottja
  @Query("select distinct c from Company c join c.employeesOfCompany e where e.salary > :minSalary")
  Page<Company> findByEmployeeWithSalaryHigherThan(Pageable pageable, int minSalary);

  //azon cégek, melyeknél az alkalmazottak száma meghalad egy adott limitet
  @Query("select c from Company c where size(c.employeesOfCompany) > :minEmployeeCount")
  List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);

//  egy id-vel adott cég alkalmazottainak átlagfizetését, beosztás szerint csoportosítva,
//  az átlagfizetések szerint csökkenő sorrendben
  @Query("select e.position.name as position, avg(e.salary) as averageSalary " +
      "from Company c inner join c.employeesOfCompany e " +
      "where c.id = :companyId group by e.position.name order by avg(e.salary) DESC")
  List<AverageSalaryByPosition> findAverageSalariesByPosition(Long companyId);

//  @Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employeesOfCompany")
  @EntityGraph("Company.full")
  @Query("select c from Company c")
  List<Company> findAllWithEmployees();

//  @Query("SELECT c FROM Company c LEFT JOIN FETCH c.employeesOfCompany where c.id = :id")
  @EntityGraph("Company.full")
  @Query("select c from Company c where c.id = :id")
  Optional<Company> findByIdWithEmployees(Long id);
}
