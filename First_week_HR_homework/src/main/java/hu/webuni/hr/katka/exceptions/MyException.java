package hu.webuni.hr.katka.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MyException extends RuntimeException{
  public MyException(String message) {
    super(message);
  }
}
