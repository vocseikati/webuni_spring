package hu.webuni.airport.services;

import hu.webuni.airport.models.LogEntry;
import hu.webuni.airport.repositories.LogEntryRepository;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEntryService {

  @Autowired
  LogEntryRepository logEntryRepository;

  public void createLog(String description){
    callBackendSystem();
    logEntryRepository.save(new LogEntry((description)));
  }

  private void callBackendSystem() {
    if (new Random().nextInt(4) == 1){
      throw new RuntimeException();
    }
  }
}
