package fr.formation.potager.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAction;
    private LocalDate date;
    private String evenement;
    private String lieu;

    @ManyToOne
    @JoinColumn(name = "carre_id") // Assurez-vous que le nom de la colonne est correct
    private Carre carre;

    public Action(LocalDate date, String evenement, String lieu) {
        this.date = date;
        this.evenement = evenement;
        this.lieu = lieu;
    }

}