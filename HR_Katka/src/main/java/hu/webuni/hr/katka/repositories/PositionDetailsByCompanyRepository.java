package hu.webuni.hr.katka.repositories;

import hu.webuni.hr.katka.entities.PositionDetailsByCompany;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PositionDetailsByCompanyRepository extends
    JpaRepository<PositionDetailsByCompany, Long> {

  List<PositionDetailsByCompany> findByPositionNameAndCompanyId(String positionName, long companyId);

  @Modifying
  //nem működik!
//	@Query("UPDATE Employee e "
//			+ "SET e.salary = :minSalary "
//			+ "WHERE e.position.name = :position "
//			+ "AND e.company.id=:companyId "
//			+ "AND e.salary < :minSalary")
  @Query("UPDATE Employee e "
      + "SET e.salary = :minSalary "
      + "WHERE e.id IN "
      + "(SELECT e2.id FROM Employee e2 "
      + "WHERE e2.position.name = :position "
      + "AND e2.company.id=:companyId "
      + "AND e2.salary < :minSalary)")
  int updateSalaries(long companyId, String position, int minSalary);

}
