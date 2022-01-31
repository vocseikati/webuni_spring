package hu.webuni.hr.katka.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Position {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Degree minDegree;

  public Position() {
  }

  public Position(String name, Degree minDegree) {
    this.name = name;
    this.minDegree = minDegree;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Degree getMinDegree() {
    return minDegree;
  }

  public void setMinDegree(Degree minDegree) {
    this.minDegree = minDegree;
  }


}
