/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Station;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.DataSource;

/**
 *
 * @author aweld
 */
public class ServiceStation implements IService<Station>{
Connection cnx ;

public ServiceStation(){
    this.cnx= DataSource.getInstance().getConnection();
}

    @Override
    public void ajouter(Station t) {
        try {
            String req = "INSERT INTO station ( nom, adresse) VALUES ('" + t.getNom() + "','" + t.getAdresse() + "')";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Station ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Station t) {
        try {
            String req = "UPDATE station SET nom ='" + t.getNom() + "', adresse = '" + t.getAdresse() + "' WHERE id = '" + t.getId() + "'";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Station modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM station WHERE id = '" + id + "'";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Station supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Station getOne(Station t) {
        Station s = new Station();
        try {
            String req = "SELECT * FROM station WHERE 'id' = '" + t.getId() + "'";
            
            Statement stm = cnx.createStatement();
            ResultSet rs=  stm.executeQuery(req);
            
            s.setId(rs.getInt(1));
            s.setNom(rs.getString("nom"));
            s.setAdresse(rs.getString("adresse"));
            System.out.println("Station affiché");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;

    }


    @Override
    public List<Station> getAll() {
      String req = "SELECT * FROM station";
      ArrayList<Station> stations = new ArrayList();
      Statement stm;
    try {
        stm = this.cnx.createStatement();
    
        ResultSet rs=  stm.executeQuery(req);
    while (rs.next()){
        Station s = new Station();
        s.setId(rs.getInt(1));
        s.setNom(rs.getString("nom"));
        s.setAdresse(rs.getString("adresse"));
        
        stations.add(s);
    }
        
    } catch (SQLException ex) {
    
        System.out.println(ex.getMessage());
    
    }
    return stations;
    }
    
    
}