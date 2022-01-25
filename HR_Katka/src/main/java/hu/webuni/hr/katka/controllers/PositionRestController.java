package hu.webuni.hr.katka.controllers;

import hu.webuni.hr.katka.entities.Position;
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
@RequestMapping("api/positions")
public class PositionRestController {

  @Autowired
  PositionRepository positionRepository;

  @GetMapping
  public List<Position> listAllPositions(){
    return positionRepository.findAll();
  }

  @PostMapping
  public Position addPosition(@RequestBody Position position){
    if (positionRepository.existsByName(position.getName())){
      throw new IllegalArgumentException("Existed name.");
    }
    return positionRepository.save(position);
  }

  @PutMapping("/{id}")
  public Position modifyPosition(@PathVariable Long id, @RequestBody Position position){
    position.setId(id);
    Optional<Position> byId = positionRepository.findById(id);
    if (byId.isEmpty()){
      throw new IllegalArgumentException("There is no position with the provided id.");
    }
    return positionRepository.save(position);
  }
}
