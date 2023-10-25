package fr.formation.potager.bll;


import fr.formation.potager.bo.*;

import java.time.LocalDate;
import java.util.List;


public interface PotagerManager {

    List<Action> getActionsDesDeuxProchainesSemaines();
    Plante createPlante(String nom, TypePlante typePlante, String variete, Integer surfaceOccupee, Integer carreId);
    Carre createCarre(TypeExposition typeExposition, String typeSol, Integer surface, Integer potagerId);
    Potager createPotager(String localisation, String nom, Integer surface, String ville);
    Action createAction(LocalDate date, String evenement, String lieu, Integer carreId);
    void visualiserPotager(Integer potagerId);

}
