package tn.edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import tn.edu.esprit.entities.Personne;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;

    private String url;
    private String user = "root";
    private String password = "";
    public Connection connection;

    private DataSource() {
        this.url = "jdbc:mysql://localhost:3306/esprit";
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL Server!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.cnx;
    }

    // Method to retrieve all Personne records from the database
    public List<Personne> getAllPersonnes() {
        List<Personne> personnes = new ArrayList<>();

        try {
            String query = "SELECT * FROM personne";
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
               
                String email = resultSet.getString("email");

                Personne personne = new Personne(nom, prenom, email);
                personnes.add(personne);
            }
        } catch (SQLException ex) {
            System.out.println("Error while fetching Personne records: " + ex.getMessage());
        }

        return personnes;
    }
}
