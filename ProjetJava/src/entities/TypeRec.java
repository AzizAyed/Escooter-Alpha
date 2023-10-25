package entities;

/**
 * Cette énumération représente les différents types de réclamations possibles.
 */
public enum TypeRec {
    INCIDENT("Incident"),
    RECLAMATION("Réclamation"),
    AVIS("Avis");

    private final String libelle;

    TypeRec(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getNom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
