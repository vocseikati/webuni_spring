package hu.webuni.airport.services;

import hu.webuni.airport.configurations.models.Airport;
import hu.webuni.airport.dtos.AirportDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AirportService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public Airport save(Airport airport) {
    checkUniqueIata(airport.getIata(), false);
    em.persist(airport);
    return airport;
  }

  @Transactional
  public Airport update(Airport airport) {
    checkUniqueIata(airport.getIata(), true);
    return em.merge(airport); //insert or update!
  }

  private void checkUniqueIata(String iata, boolean forUpdate) {
    TypedQuery<Long> query =
        em.createNamedQuery(forUpdate ? "Airport.countByIataAndIdNotIn" : "Airport.countByIata",
            Long.class)
            .setParameter("iata", iata);
    if (forUpdate){

    }
    Long count =
        query
            .getSingleResult();

    if (count > 0) {
      throw new NonUniqueException(iata);
    }
  }

  public List<Airport> findAll() {
    return em.createQuery("select a from Airport a", Airport.class).getResultList();
  }

  public Airport findById(long id) {
    return em.find(Airport.class, id);
  }

  @Transactional
  public void delete(long id) {
    em.remove(findById(id));
  }

}
