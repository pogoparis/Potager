package fr.formation.potager.bo;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class LocalisationPlante {
    private String nomPlante;
    private String varietePlante;
    private String nomPotager;
    private String nomCarre;
    private int quantite;

    public LocalisationPlante(String nomPlante, String varietePlante, String nomPotager, String nomCarre, int quantite) {
        this.nomPlante = nomPlante;
        this.varietePlante = varietePlante;
        this.nomPotager = nomPotager;
        this.nomCarre = nomCarre;
        this.quantite = quantite;
    }
}