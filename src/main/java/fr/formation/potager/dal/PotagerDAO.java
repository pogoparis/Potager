package fr.formation.potager.dal;

import fr.formation.potager.bo.Potager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotagerDAO extends CrudRepository<Potager, Integer> {
}
