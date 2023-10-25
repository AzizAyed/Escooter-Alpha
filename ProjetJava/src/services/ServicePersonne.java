package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.Personne;
import tools.DataSource;

public class ServicePersonne implements IService<Personne> {
    Connection cnx;

    public ServicePersonne() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Personne t) {
        try {
            String req = "INSERT INTO personne (nom, prenom, cin, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getPrenom());
            ps.setInt(3, t.getCin());
            ps.setString(4, t.getEmail());
            ps.executeUpdate();
            System.out.println("Personne ajoutée avec succès.");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de la personne : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(Personne t) {
        try {
            String req = "UPDATE personne SET nom = ?, prenom = ?, email = ? WHERE cin = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getPrenom());
            ps.setString(3, t.getEmail());
            ps.setInt(4, t.getCin());
            ps.executeUpdate();
            System.out.println("Personne modifiée avec succès.");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification de la personne : " + ex.getMessage());
        }
    }

public void supprimer(String nom, String prenom) {
    try {
        String req = "DELETE FROM personne WHERE nom = ? AND prenom = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, nom); // Set the parameter for 'nom'
        ps.setString(2, prenom); // Set the parameter for 'prenom'
        int deletedRows = ps.executeUpdate();
        if (deletedRows > 0) {
            System.out.println("Personne supprimée avec succès.");
        } else {
            System.out.println("Aucune personne avec le nom et prénom spécifiés n'a été trouvée.");
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la suppression de la personne : " + ex.getMessage());
    }
}


    @Override
    public Personne getOne(Personne t) {
        try {
            String req = "SELECT * FROM personne WHERE cin = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getCin());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Personne p = new Personne();
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                return p;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la recherche de la personne : " + ex.getMessage());
        }
        return null;
    }

    public List<Personne> getAll() {
        String req = "SELECT * FROM personne";
        List<Personne> personnes = new ArrayList<>();

        try (PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Personne p = new Personne();
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                personnes.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des personnes : " + ex.getMessage());
        }

        return personnes;
    }

    public List<Personne> searchByNomAndPrenom(String nom, String prenom) {
        List<Personne> personnes = new ArrayList<>();
        String query = "SELECT * FROM personne WHERE nom LIKE ? AND prenom LIKE ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, "%" + nom + "%");
            ps.setString(2, "%" + prenom + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Personne personne = new Personne();
                    personne.setNom(rs.getString("nom"));
                    personne.setPrenom(rs.getString("prenom"));
                    personne.setEmail(rs.getString("email"));
                    personnes.add(personne);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error searching for Personne: " + ex.getMessage());
        }
        return personnes;
    }

    @Override
    public List<Personne> getAll(Personne t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void supprimer(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
