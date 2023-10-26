/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamGui;

import animatefx.animation.BounceIn;
import entities.Offre;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.Notifications;
import services.ServiceOffre;


/**
 * FXML Controller class
 *
 * @author Nazih
 */
public class ChoixOffresController implements Initializable {

    @FXML
    private Button EXIT;
    @FXML
    private ComboBox<Offre> comOff;
    @FXML
    private Button HOME;
    @FXML
    private Button RECLA;
    @FXML
    private Button LOGOUT;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceOffre service = new ServiceOffre();
        List<Offre> lo = service.getAll();
        ObservableList<Offre> olo = FXCollections.observableArrayList(lo);
        comOff.setItems(olo);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }    

    @FXML
    public void EXIT (){
        System.exit(0);   
    }

    @FXML
    private void Imprimer(ActionEvent event) {
        if (comOff.getSelectionModel().getSelectedItem()==null) {
            new BounceIn(comOff).play();
        } else {
            System.out.println("Creating the PDF");
            
            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
            int userSelection = fileChooser.showSaveDialog(null);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                
                String nm = comOff.getSelectionModel().getSelectedItem().getNom();
                int dr = comOff.getSelectionModel().getSelectedItem().getDuree();

                Date today = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.add(Calendar.MONTH, dr);
                Date endDate = calendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String startDateStr = dateFormat.format(today);
                String endDateStr = dateFormat.format(endDate);

                try {
                    PDDocument doc = new PDDocument();
                    PDPage page = new PDPage(new PDRectangle(74.25f, 105.83f)); // Set the page size to A7

                    // Create a yellow background
                    PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                    contentStream.setNonStrokingColor(Color.YELLOW);
                    contentStream.addRect(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                    contentStream.fill();

                    // Add a picture as the background
                    PDImageXObject image = PDImageXObject.createFromFile("C:/Users/aweld/Downloads/ProjetJava/src/image/shutterstock_1864450102-scaled (1).jpg", doc);
                    contentStream.drawImage(image, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

                    // Add text content with a smaller font size and reduced line spacing
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 5); // Smaller font size
                    contentStream.setNonStrokingColor(Color.WHITE); // Set text color

                    // Add user name, offer name, today's date, and the end date
                    String userName = "Foulen";
                    String offerName = "Ben Foulen";

                    float x = 10; // X position
                    float y = page.getMediaBox().getHeight() - 10; // Y position
                    float lineSpacing = 15; // Reduce the line spacing

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, y);
                    contentStream.newLineAtOffset(0, -lineSpacing); // Reduce the line spacing
                    contentStream.showText("Offre: " + nm);
                    contentStream.newLineAtOffset(0, -lineSpacing); // Reduce the line spacing
                    contentStream.showText("Date Debut: " + startDateStr);
                    contentStream.newLineAtOffset(0, -lineSpacing); // Reduce the line spacing
                    contentStream.showText("Date Fin: " + endDateStr);
                    contentStream.endText();

                    contentStream.close();
                    doc.addPage(page);

                    // Save the PDF file
                    doc.save(fileToSave);
                    doc.close();
                    System.out.println("File created");

                    Notifications.create()
                        .title("PDF créé")
                        .text("FECLICITATION POUR VOTRE OFFRE")
                        .hideCloseButton()
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.BOTTOM_RIGHT)
                        .show();
                    
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
            System.err.println("Erreur");
            }
        }
    }

    @FXML
    public void Home(ActionEvent event) throws IOException {
        if (HOME.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("HomeUser.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
        }
    
}

    @FXML
    private void RECLA(ActionEvent event) throws IOException {
        if (RECLA.isFocused()) {
        Parent view4 = FXMLLoader.load(getClass().getResource("AjouterReclamation.fxml"));
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