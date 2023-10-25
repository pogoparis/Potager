package fr.formation.potager.dal;

import fr.formation.potager.bo.Action;
import fr.formation.potager.bo.Carre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActionDAO extends CrudRepository<Action, Integer> {
    List<Action> findByCarre(Carre carre);

    @Query("SELECT a FROM Action a WHERE a.date BETWEEN :debut AND :fin")
    List<Action> findActionsDesDeuxProchainesSemaines(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
