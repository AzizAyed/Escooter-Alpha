/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop4se5;

import java.util.List;
import tn.edu.esprit.tools.DataSource;
import tn.edu.esprit.services.ServiceStation;
import tn.edu.esprit.services.ServiceOffre;
import tn.edu.esprit.entities.Station;
import tn.edu.esprit.entities.Offre;

/**
 *
 * @author Nazih
 */
public class Workshop4SE5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataSource.getInstance();
    
    ServiceOffre service = new ServiceOffre();
    //Station station = new Station(1,"ben arous","mourouj");
    
    //service.ajouter(station);
    
    //service.modifier(station);
    
    //service.supprimer(1);
    
    /*Station st = service.getOne(station);
    System.out.println("ID Station: " + st.getId());
    System.out.println("Nom: " + st.getNom());
    System.out.println("Adresse: " + st.getAdresse());*/
    
    
   List<Offre> of = service.getAll(); 
   for(Offre a : of){
            System.out.println("ID Offre: " + a.getId());
            System.out.println("Nom: " + a.getNom());
            System.out.println("Prix: " + a.getPrix());
            System.out.println("Durée: " + a.getDuree());
       }
        
    }
    }
    
