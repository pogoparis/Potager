package fr.formation.potager.dal;

import fr.formation.potager.bo.LocalisationPlante;
import fr.formation.potager.bo.Plante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanteDAO extends CrudRepository <Plante, Integer> {

        @Query(value = "SELECT p.potager_id, p.carre_id, COUNT(*) AS quantite " +
                "FROM Plante p " +
                "WHERE p.nom = :nomPlante " +
                "AND (:varietePlante IS NULL OR p.variete = :varietePlante) " +
                "GROUP BY p.potager_id, p.carre_id",
                nativeQuery = true)
        List<Object[]> localiserPlante(@Param("nomPlante") String nomPlante, @Param("varietePlante") String varietePlante);
    }
