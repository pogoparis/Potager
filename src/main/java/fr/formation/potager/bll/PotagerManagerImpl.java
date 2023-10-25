package fr.formation.potager.bll;


import fr.formation.potager.bo.*;
import fr.formation.potager.dal.ActionDAO;
import fr.formation.potager.dal.CarreDAO;
import fr.formation.potager.dal.PlanteDAO;
import fr.formation.potager.dal.PotagerDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PotagerManagerImpl implements PotagerManager {

    @Autowired
    PotagerDAO potagerDAO;
    @Autowired
    PlanteDAO planteDAO;
    @Autowired
    CarreDAO carreDAO;
    @Autowired
    ActionDAO actionDAO;

    @Transactional
    @Override
    public Action createAction(LocalDate date, String evenement, String lieu, Integer carreId) {
        // Vérification de la date de l'action
        LocalDate dateDuJour = LocalDate.now();
        if (date.isBefore(dateDuJour)) {
            System.err.println("La date de l'action doit être ultérieure à la date du jour.");
            return null;
        }

        Carre carre = carreDAO.findById(carreId).orElse(null);
        if (carre == null) {
            System.err.println("Le Carré n'existe pas");
            return null;
        }

        Action action = new Action(date, evenement, lieu);
        actionDAO.save(action);

        return action;
    }


    @Transactional
    @Override
    public Potager createPotager(String localisation, String nom, Integer surface, String ville) {
        Potager potager = new Potager(localisation, nom, surface, ville);
        return potagerDAO.save(potager);
    }

    @Transactional
    @Override
    public Carre createCarre(TypeExposition typeExposition, String typeSol, Integer surface, Integer potagerId) {
        Potager potager = potagerDAO.findById(potagerId).orElse(null);
        if (potager == null) {
            System.err.println("Potager Inexistant");
        }
        // Calcul de la somme des tailles des carrés existants
        float sumOfExistingCarreSizes = potager.getLstCarres()
                .stream()
                .map(Carre::getSurface)
                .reduce(0, Integer::sum);
        // ajout du carré
        float totalSizeAfterAddingNewCarre = sumOfExistingCarreSizes + surface;

        if (totalSizeAfterAddingNewCarre > potager.getSurface()) {
            throw new TailleCarresDepassePotagerException("La taille des carrés dépasse la taille du potager.");
        }

        Carre carre = new Carre(typeExposition, typeSol, surface, potager);
        return carreDAO.save(carre);
    }

    @Override
    @Transactional
    public List<Action> getActionsDesDeuxProchainesSemaines() {
        LocalDate dateDebut = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
        LocalDate dateFin = dateDebut.plusWeeks(2);
        System.out.println("------- Actions des deux prochaines semaines -------------");
        List<Action> actions =  actionDAO.findActionsDesDeuxProchainesSemaines(dateDebut, dateFin);
        for (Action action : actions) {
            String dateFormatee = action.getDate().format(formatter);
            System.out.println(action.getEvenement() + " - " + action.getLieu());
            System.out.println("Début de l'action : " + dateFormatee);
        }
        return actionDAO.findActionsDesDeuxProchainesSemaines(dateDebut, dateFin);
    }

    private boolean verifierContraintes(Carre carre, String nom, String variete) {
        // Vérifier le nombre de plantes du même nom dans le carré
        long plantesMemeNom = carre.getLstPlantes().stream()
                .filter(plante -> plante.getNom().equals(nom) && plante.getVariete().equals(variete))
                .count();
        return plantesMemeNom < 3;
    }
    @Transactional
    @Override
    public Plante createPlante(String nom, TypePlante typePlante, String variete, Integer surfaceOccupee, Integer carreId) {
        Carre carre = carreDAO.findById(carreId).orElse(null);
        if (carre == null) {
            System.err.println("Le Carré n'existe pas");
            return null;
        }

        // Vérifier le nombre de plantes du même nom et variété dans le carré
        long plantesMemeNomVariete = carre.getLstPlantes().stream()
                .filter(plante -> plante.getNom().equals(nom) && plante.getVariete().equals(variete))
                .count();
        if (plantesMemeNomVariete >= 1) {
            System.err.println("Il y a déjà une plante du même nom et de la même variété dans ce carré. Vous ne pouvez pas en ajouter plus.");
            return null;
        }

        if (!verifierContraintes(carre, nom, variete)) {
            System.out.println("Il y a déjà 3 plantes du même nom et de la même variété dans ce carré. Vous ne pouvez pas en ajouter plus.");
            return null;
        }

        float surfaceOccupeeParPlantes = carre.getLstPlantes()
                .stream()
                .map(Plante::getSurfaceOccupee)
                .reduce(0, Integer::sum);

        if (surfaceOccupeeParPlantes + surfaceOccupee > carre.getSurface()) {
            System.out.println("La surface occupée par les plantes dépasse celle du carré.");
            return null;
        }

        Plante plante = new Plante(nom, typePlante, variete, surfaceOccupee);
        plante.setCarre(carre);
        carre.getLstPlantes().add(plante);

        carreDAO.save(carre);
        planteDAO.save(plante);

        return plante;
    }

    @Transactional
    @Override
    public void visualiserPotager(Integer potagerId) {
        Potager potager = potagerDAO.findById(potagerId).orElse(null);
        if (potager != null) {
            List<Carre> carres = potager.getLstCarres();
            System.out.println("************** MON POTAGER ***************");
            System.out.println("    Potager : " + potager.getNom());
            System.out.println("    " + potager.getLocalisation() + " - " + potager.getVille());
            System.out.println("******************************************");

            for (Carre carre : carres) {
                System.out.println("---- Carre : " + carre.getIdCarre() + " - Surface : " + carre.getSurface());
                System.out.println("Type d'exposition : " + carre.getTypeExposition() + " - Type de sol : " + carre.getTypeSol());

                List<Plante> plantes = carre.getLstPlantes();
                for (Plante plante : plantes) {
                    System.out.println("    * " + plante.getNom() + " - Type : " + plante.getTypePlante());
                    System.out.println("      " +plante.getVariete() + " Surface occupée : " + plante.getSurfaceOccupee() + " cm²");
                }
            }
        }
    }

}
