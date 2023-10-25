package fr.formation.potager.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Carre {

    @Id
    @GeneratedValue()
    private Integer idCarre;

    @Enumerated(EnumType.STRING)
    private TypeExposition TypeExposition;
    private String TypeSol;
    private Integer Surface;

    @ManyToOne
    private Potager potager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carre")
    private List<Plante> lstPlantes;

    public Carre(TypeExposition typeExposition, String typeSol, Integer surface, Potager potager) {
        TypeExposition = typeExposition;
        TypeSol = typeSol;
        Surface = surface;
        this.potager = potager;
    }
}
