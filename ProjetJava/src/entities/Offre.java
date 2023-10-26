/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author aweld
 */
public class Offre {private int id;
    private String nom;
    private Float prix;
    private int duree;

    public Offre() {
    }

    public Offre(String nom, Float prix, int duree) {
        this.nom = nom;
        this.prix = prix;
        this.duree = duree;
    }

    public Offre(int id, String nom, Float prix, int duree) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Float getPrix() {
        return prix;
    }

    public int getDuree() {
        return duree;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return nom + ": Coute " + prix + " DT pour une duree de " + duree + " mois";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Offre other = (Offre) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
