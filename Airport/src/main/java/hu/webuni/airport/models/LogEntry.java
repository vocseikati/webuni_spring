package hu.webuni.airport.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LogEntry {

  @Id
  @GeneratedValue
  private long id;

  private LocalDateTime timestamp;
  private String description;

  public LogEntry() {
  }

  public LogEntry(String description) {
    this.description = description;
    this.timestamp = LocalDateTime.now();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
