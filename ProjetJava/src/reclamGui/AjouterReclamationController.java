/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamGui;

import entities.Etat;
import entities.Reclamation;
import entities.TypeRec;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tools.DataSource;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author med amine nsir
 */
public class AjouterReclamationController implements Initializable {
        
    
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    private Button EXIT;
    
  
    @FXML
    private ComboBox<TypeRec> combobox;
    @FXML
    private TextArea description;

   

    
    @FXML
    private TextField titre;
    Connection cnx;
    @FXML
    private Button Home;
    @FXML
    private Button send;
    @FXML
    private Button OFFRES;
    @FXML
    private Button LOGOUT;
    public User getUserById(int iduser) {
    String sql = "SELECT * FROM user WHERE iduser = ?";

    try (PreparedStatement statement = cnx.prepareStatement(sql)) {
        statement.setInt(1, iduser);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            User user = new User();
            user.setIduser(resultSet.getInt("iduser"));
            return user;
        }
    } catch (SQLException e) {
        // Gérez les exceptions SQL ici, affichez des messages d'erreur si nécessaire
        
    }

    return null;
}









   
    @FXML
  public void SEND() {
        this.cnx = DataSource.getInstance().getConnection();

    // Récupération du titre, de la description et du ComboBox
    String titreText = titre.getText();
    String descriptionText = description.getText();
    String typeRecValue = combobox.getValue() != null ? combobox.getValue().toString() : null;

    // Vérification des champs vides et du ComboBox
    if (titreText.isEmpty() && descriptionText.isEmpty() && typeRecValue == null) {
        // Aucun champ n'est rempli et aucun type n'est sélectionné
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs et sélectionner un type de réclamation.");
        alert.showAndWait();
        return; // Arrêtez le traitement de la réclamation
    } else if (titreText.isEmpty()) {
        // Le champ du titre est vide
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir le champ du titre.");
        alert.showAndWait();
        return; // Arrêtez le traitement de la réclamation
    } else if (descriptionText.isEmpty()) {
        // Le champ de la description est vide
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir le champ de la description.");
        alert.showAndWait();
        return; // Arrêtez le traitement de la réclamation
    } else if (typeRecValue == null) {
        // Aucun type n'est sélectionné
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un type de réclamation.");
        alert.showAndWait();
        return; // Arrêtez le traitement de la réclamation
    }

    // Vérification des mots interdits
    if (contientMotsInterdits(titreText) || contientMotsInterdits(descriptionText)) {
        // Le titre ou la description contiennent des mots interdits, affichez un message d'erreur
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le titre ou la description contiennent des mots interdits.");
        alert.showAndWait();
        return; // Arrêtez le traitement de la réclamation
    }

    // Si tout est correct, continuez avec l'insertion de la réclamation.
    try {
        String sql = "INSERT INTO reclamation (iduser, titre, description, date, typeRec, etat) VALUES (?, ?, ?, ?, ?, 'Non_TRAITE')";

        TypeRec typeRec = TypeRec.valueOf(typeRecValue);

        Reclamation reclamation = new Reclamation();
        reclamation.setIduser(5);  // ID de l'utilisateur (par exemple, fixé à 5)

        reclamation.setTitre(titreText);
        reclamation.setDescription(descriptionText);
        java.util.Date currentDate = new java.util.Date();
        reclamation.setDate(new Date(currentDate.getTime()));
        reclamation.setTypeRec(typeRec);

        Etat etat = Etat.NON_TRAITE;
        reclamation.setEtat(etat);

        PreparedStatement prepare = cnx.prepareStatement(sql);
        prepare.setInt(1, reclamation.getIduser());  // Paramètre pour l'ID de l'utilisateur
        prepare.setString(2, reclamation.getTitre());
        prepare.setString(3, reclamation.getDescription());
        prepare.setDate(4, new java.sql.Date(reclamation.getDate().getTime()));
        prepare.setString(5, reclamation.getTypeRec().getLibelle());
        prepare.executeUpdate();

        titre.setText("");
        description.setText("");
        combobox.setValue(null);

        // Afficher une alerte pour informer l'utilisateur
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Réclamation créée avec succès!");
        alert.showAndWait();
    } catch (SQLException e) {
        e.printStackTrace();
        // ... (votre code pour gérer les exceptions SQL)
    }
}



    // Fonction pour vérifier si une chaîne de caractères contient des mots interdits
    private boolean contientMotsInterdits(String text) {
        String[] motsInterdits = {"mot1", "mot2", "mot3"};

        for (String motInterdit : motsInterdits) {
            if (text.toLowerCase().contains(motInterdit.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    // ...







    
    
    
    @FXML
    public void EXIT (){
         System.exit(0);
        
    }
private String typeData[] = {"RECLAMATION", "INCIDENT", "AVIS"};

    @FXML
    public void comboBox() {
    List<TypeRec> list = new ArrayList<>();

    // Ajoutez ici vos instances de TypeRec
    list.add(TypeRec.RECLAMATION);
    list.add(TypeRec.INCIDENT);
    list.add(TypeRec.AVIS);

    ObservableList<TypeRec> dataList = FXCollections.observableArrayList(list);
    combobox.setItems(dataList);
}

        

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         comboBox();
    }
@FXML
    public void Home(ActionEvent event) throws IOException {
        if (Home.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("HomeUser.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }    

    @FXML
   private void OFFRES(ActionEvent event) throws IOException {
        if (OFFRES.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("ChoixOffres.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    
}

    @FXML
    private void LOGOUT(ActionEvent event) throws IOException {
        if (LOGOUT.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("Signin.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    }
}