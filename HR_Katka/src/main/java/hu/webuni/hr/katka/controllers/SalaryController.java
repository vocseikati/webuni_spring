package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/salary")
@RestController
public class SalaryController {

  @Autowired
  SalaryService salaryService;

  //	@PutMapping("/{positionName}/raiseMin/{minSalary}")
  @PutMapping("/{companyId}/{positionName}/raiseMin/{minSalary}")
  public void raiseMinSalary(@PathVariable String positionName, @PathVariable int minSalary, @PathVariable long companyId) {
    salaryService.raiseMinSalary(companyId, positionName, minSalary);
  }
}
