package fr.formation.potager.bo;

public enum TypeExposition {

    SOLEIL("Exposition au soleil"),
    OMBRE("Exposition à l'ombre"),
    MI_OMBRE("Exposition à mi-ombre");

    private String description;

    TypeExposition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
