package hu.webuni.hr.katka.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HolidayDto {

  private long id;
  @NotNull(message = "Start date must have a value.")
  private LocalDate startDate;
  @NotNull(message = "Start date must have a value.")
  private LocalDate endDate;
  private Boolean approved;

  @NotNull(message = "Employee id must have a value.")
  private Long employeeId;
//  @NotNull(message = "Boss id must have a value.")
//  private Long bossId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createdAt = LocalDateTime.now();

  public HolidayDto() {
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

//  public long getBossId() {
//    return bossId;
//  }
//
//  public void setBossId(long bossId) {
//    this.bossId = bossId;
//  }
}
