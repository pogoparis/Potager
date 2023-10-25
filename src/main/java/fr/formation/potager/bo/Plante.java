package fr.formation.potager.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Plante {

    @Id
    @GeneratedValue
    private Integer idPlante;
    private String nom;
    @Enumerated(EnumType.STRING)
    private TypePlante typePlante;
    private String variete;
    private Integer surfaceOccupee;

    @ManyToOne
    private Carre carre;

    public Plante(String nom, TypePlante typePlante, String variete, Integer surfaceOccupee) {
        this.nom = nom;
        this.typePlante = typePlante;
        this.variete = variete;
        this.surfaceOccupee = surfaceOccupee;
    }
}
