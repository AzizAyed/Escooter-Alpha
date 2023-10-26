/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Offre;
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
public class ServiceOffre implements IService<Offre>{
    Connection cnx ;
    
    public ServiceOffre(){
    this.cnx= DataSource.getInstance().getConnection();
}

    @Override
    public void ajouter(Offre t) {
        try {
            String req = "INSERT INTO offre (nom, prix, duree) VALUES ('" + t.getNom() + "','" + t.getPrix() + "','" + t.getDuree()+ "')";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Offre ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Offre t) {
        try {
            String req = "UPDATE offre SET nom ='" + t.getNom() + "', prix = '" + t.getPrix()+ "', duree = '" + t.getDuree()+ "' WHERE id = '" + t.getId() + "'";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Offre modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM offre WHERE id = '" + id + "'";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Offre supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Offre getOne(Offre t) {
        Offre o = new Offre();
        try {
            String req = "SELECT * FROM offre WHERE 'id' = '" + t.getId() + "'";
            
            Statement stm = cnx.createStatement();
            ResultSet rs=  stm.executeQuery(req);
            
            o.setId(rs.getInt(1));
            o.setNom(rs.getString("nom"));
            o.setPrix(rs.getFloat(2));
            o.setDuree(rs.getInt(3));
            System.out.println("Offre affiché");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return o;

    }

    @Override
    public List<Offre> getAll() {
      String req = "SELECT * FROM offre";
      ArrayList<Offre> offre = new ArrayList();
      Statement stm;
    try {
        stm = this.cnx.createStatement();
    
        ResultSet rs=  stm.executeQuery(req);
    while (rs.next()){
        Offre o = new Offre();
        o.setId(rs.getInt(1));
        o.setNom(rs.getString("nom"));
        o.setPrix(rs.getFloat(3));
        o.setDuree(rs.getInt(4));
        
        offre.add(o);
    }
        
    } catch (SQLException ex) {
    
        System.out.println(ex.getMessage());
    
        }
    return offre;
    }
}
