package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.entities.CompanyType;
import hu.webuni.hr.katka.entities.Position;
import hu.webuni.hr.katka.repositories.CompanyTypeRepository;
import hu.webuni.hr.katka.repositories.PositionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/companyTypes")
public class CompanyTypeRestController {

  @Autowired
  CompanyTypeRepository companyTypeRepository;

  @GetMapping
  public List<CompanyType> listAllCompanyType(){
    return companyTypeRepository.findAll();
  }

  @PostMapping
  public CompanyType addCompanyType(@RequestBody CompanyType type){
    if (companyTypeRepository.existsByName(type.getName())){
      throw new IllegalArgumentException("Existed name.");
    }
    return companyTypeRepository.save(type);
  }

  @PutMapping("/{id}")
  public CompanyType modifyCompanyType(@PathVariable Long id){
    Optional<CompanyType> byId = companyTypeRepository.findById(id);
    if (byId.isEmpty()){
      throw new IllegalArgumentException("There is no company type with the provided id.");
    }
    return companyTypeRepository.save(byId.get());
  }
}
