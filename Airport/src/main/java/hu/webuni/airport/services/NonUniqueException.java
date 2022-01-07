package hu.webuni.airport.services;

public class NonUniqueException extends RuntimeException{

  public NonUniqueException(String iata) {
    super("Existing iata: " + iata);
  }
}
