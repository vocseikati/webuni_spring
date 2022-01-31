package hu.webuni.hr.katka.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PositionDetailsByCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private int minSalary;

  @ManyToOne
  private Company company;

  @ManyToOne
  private Position position;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getMinSalary() {
    return minSalary;
  }

  public void setMinSalary(int minSalary) {
    this.minSalary = minSalary;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

}
