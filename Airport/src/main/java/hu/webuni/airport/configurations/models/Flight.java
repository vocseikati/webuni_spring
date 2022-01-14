package hu.webuni.airport.configurations.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Flight {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Airport takeoff;

  @ManyToOne
  private Airport landing;

  private String flightNumber;
  private LocalDateTime takeoffTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Airport getTakeoff() {
    return takeoff;
  }

  public void setTakeoff(Airport takeoff) {
    this.takeoff = takeoff;
  }

  public Airport getLanding() {
    return landing;
  }

  public void setLanding(Airport landing) {
    this.landing = landing;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public LocalDateTime getTakeoffTime() {
    return takeoffTime;
  }

  public void setTakeoffTime(LocalDateTime takeoffTime) {
    this.takeoffTime = takeoffTime;
  }
}
