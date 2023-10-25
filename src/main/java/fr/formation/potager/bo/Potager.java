package fr.formation.potager.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Potager {

    @Id
    @GeneratedValue
    private Integer idPotager;
    private String localisation;
    private String nom;
    private Integer surface;
    private String ville;

    @OneToMany(mappedBy = "potager", fetch = FetchType.EAGER)
    List<Carre> lstCarres;

    public Potager(String localisation, String nom, Integer surface, String ville) {
        this.localisation = localisation;
        this.nom = nom;
        this.surface = surface;
        this.ville = ville;
    }
}
