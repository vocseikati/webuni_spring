package hu.webuni.airport.security;

import hu.webuni.airport.models.AirportUser;
import hu.webuni.airport.repositories.UserRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AirportUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AirportUser airportUser = userRepository.findById(username)
        .orElseThrow(()-> new UsernameNotFoundException(username));


    return new User(username, airportUser.getPassword(),
        airportUser.getRoles().stream().map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
  }
}
