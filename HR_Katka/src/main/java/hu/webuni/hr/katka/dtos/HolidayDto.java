package hu.webuni.hr.katka.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;

public class HolidayDto {

  private long id;
  @NotEmpty(message = "Start date must have a value.")
  private LocalDate startDate;
  @NotEmpty(message = "Start date must have a value.")
  private LocalDate endDate;
  private Boolean approved;

  private long employeeId;
  private long bossId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createdAt = LocalDateTime.now();

  public HolidayDto() {
  }

  public HolidayDto(LocalDate startDate, LocalDate endDate, LocalDateTime createdAt,
                    Boolean approved) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.createdAt = createdAt;
    this.approved = approved;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Boolean getApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  public long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(long employeeId) {
    this.employeeId = employeeId;
  }

  public long getBossId() {
    return bossId;
  }

  public void setBossId(long bossId) {
    this.bossId = bossId;
  }
}
