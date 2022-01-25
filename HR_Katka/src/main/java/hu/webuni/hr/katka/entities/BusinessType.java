package hu.webuni.hr.katka.entities;

public enum BusinessType {

  BT, KFT, ZRT, NYRT;

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

}
