package fr.formation.potager;

import fr.formation.potager.bll.PotagerManager;
import fr.formation.potager.bll.TailleCarresDepassePotagerException;
import fr.formation.potager.bo.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PotagerApplication implements CommandLineRunner {

    @Autowired
    PotagerManager potagerManager;

    public static void main(String[] args) {
        SpringApplication.run(PotagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Potager potager = potagerManager.createPotager("Fond du jardin", "Le clos des plantes", 300, "Asse-Le-Riboul");

        try {
            Carre carre1 = potagerManager.createCarre(TypeExposition.SOLEIL, "Argileux", 140, potager.getIdPotager());

            Plante plante1 = potagerManager.createPlante("Tomate", TypePlante.FRUIT, "Variété A", 20, carre1.getIdCarre());
            Plante plante2 = potagerManager.createPlante("Carotte", TypePlante.RACINE, "Variété B", 40, carre1.getIdCarre());
            Plante plante3 = potagerManager.createPlante("Laitue", TypePlante.FEUILLE, "Variété C", 60, carre1.getIdCarre());
            // ** Plus de place
            // Plante plante4 = potagerManager.createPlante("Laitue", TypePlante.FEUILLE, "Variété C", 80, carre1.getIdCarre());

            Action action1 = potagerManager.createAction(LocalDate.now().plusDays(1), "Arroser", "Carré 1", carre1.getIdCarre());
            Action action2 = potagerManager.createAction(LocalDate.now().plusDays(2), "Planter", "Carré 1", carre1.getIdCarre());
            Action action3 = potagerManager.createAction(LocalDate.now().plusDays(3), "Récolter", "Carré 1", carre1.getIdCarre());
            Action action4 = potagerManager.createAction(LocalDate.now().plusDays(4), "Fertiliser", "Carré 1", carre1.getIdCarre());
        } catch (TailleCarresDepassePotagerException e) {
            System.err.println("La taille des carrés dépasse la taille du potager. Veuillez ajuster la taille du carré.");
        }
        try {
            Carre carre2 = potagerManager.createCarre(TypeExposition.OMBRE, "Sableux", 150, potager.getIdPotager());
            Plante plante1 = potagerManager.createPlante("Carotte", TypePlante.RACINE, "Variété B", 20, carre2.getIdCarre());
            Plante plante2 = potagerManager.createPlante("Epinard", TypePlante.FEUILLE, "Variété B", 40, carre2.getIdCarre());
            Plante plante3 = potagerManager.createPlante("Poivron", TypePlante.FRUIT, "Variété F", 40, carre2.getIdCarre());

            Action action2 = potagerManager.createAction(LocalDate.now().plusDays(10), "Récolter", "Carré 1", carre2.getIdCarre());
            // Une plante du meme nom et meme variété.
            // Plante plante5 = potagerManager.createPlante("Carotte", TypePlante.RACINE, "Variété B", 20, carre2.getIdCarre());
            // ** Une plante de trop du même nom
            // Plante plante4 = potagerManager.createPlante("Carotte", TypePlante.FEUILLE, "Variété D", 80, carre2.getIdCarre());
            // ** Erreur de date
            // Action action1 = potagerManager.createAction(LocalDate.now().minusDays(3), "Récolter", "Carré 1", carre2.getIdCarre());

        } catch (TailleCarresDepassePotagerException e) {
            System.err.println("La taille des carrés dépasse la taille du potager. Veuillez ajuster la taille du carré.");
        }

        potagerManager.visualiserPotager(potager.getIdPotager());
        potagerManager.getActionsDesDeuxProchainesSemaines();

        String nomPlante = "Nom de la plante"; // Remplacez par le nom de la plante que vous recherchez
        String varietePlante = "Variété de la plante"; // Laissez vide si vous ne spécifiez pas de variété

    }
    }

