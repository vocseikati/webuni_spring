package hu.webuni.hr.katka.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

public enum BusinessType {

  BT, KFT, ZRT, NYRT;

  @JsonCreator
  public static BusinessType fromString(String value){
    switch (value) {
      case "BT":
        return BusinessType.BT;
      case "KFT":
        return BusinessType.KFT;
      case "ZRT":
        return BusinessType.ZRT;
      case "NYRT":
        return BusinessType.NYRT;
    }
    throw new IllegalArgumentException("Unexpected enum value " + value);
  }

//  BT("Bt."),
//  KFT("Kft."),
//  ZRT("Zrt."),
//  NYRT("Nyrt.");
//
//  private String type;
//
//  BusinessType(String type) {
//    this.type = type;
//  }
//
//  @JsonCreator
//  public static BusinessType decode(final String type) {
//    return Stream.of(BusinessType.values()).filter(targetEnum -> targetEnum.type.equals(type)).findFirst().orElse(null);
//  }
//
//  @JsonValue
//  public String getType() {
//    return type;
//  }
//
//  @Override
//  public String toString() {
//    return type;
//  }
}
