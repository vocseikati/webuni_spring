package hu.webuni.hr.katka.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends ResponseStatusException {

  public NotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
