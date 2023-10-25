package fr.formation.potager.dal;

import fr.formation.potager.bo.Carre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreDAO extends CrudRepository<Carre, Integer> {
    Carre getOne(Integer carreId);
}
