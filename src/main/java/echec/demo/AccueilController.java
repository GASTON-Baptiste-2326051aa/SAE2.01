package echec.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button boutonJvJlog;

    @FXML
    private Button boutonJvBlog;

    @FXML
    private TableView<Map<String, String>> tableView;


    @FXML
    private TableColumn<Map<String, String>, String> prenomColumn;

    @FXML
    private TableColumn<Map<String, String>, String> nomColumn;

    @FXML
    private TableColumn<Map<String, String>, String> matchesColumn;

    @FXML
    private TableColumn<Map<String, String>, String> victoriesColumn;

    private ButtonController buttonController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // Initialisation des boutons Controller pour savoir quel mode de jeu est choisi.

        buttonController = new ButtonController();
        if (boutonJvJlog != null) {
            buttonController.initButtonLogJvJ(boutonJvJlog);
        }
        if (boutonJvBlog != null) {
            buttonController.initButtonLogJvB(boutonJvBlog);
        }

        prenomColumn.setCellValueFactory(param -> {
            String value = param.getValue().get("prenom");
            return new SimpleStringProperty(value);
        });

        nomColumn.setCellValueFactory(param -> {
            String value = param.getValue().get("nom");
            return new SimpleStringProperty(value);
        });

        matchesColumn.setCellValueFactory(param -> {
            String value = param.getValue().get("matches");
            return new SimpleStringProperty(value);
        });

        victoriesColumn.setCellValueFactory(param -> {
            String value = param.getValue().get("victories");
            return new SimpleStringProperty(value);
        });

        tableView.setItems(loadDataFromCSV("src/main/resources/joueurs.csv"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);




    }
    /**
     * Charge les données à partir d'un fichier CSV spécifié.
     *
     * @param fileName Le nom du fichier CSV à partir duquel les données doivent être chargées.
     * @return Une liste observable de cartes (Map) où chaque carte représente une ligne de données du fichier CSV.
     *         Chaque carte associe le nom d'une colonne à la valeur correspondante de cette ligne.
     *         Les colonnes sont représentées par des clés de type chaîne de caractères (String).
     * @author Baptiste Gaston
     */
    private ObservableList<Map<String, String>> loadDataFromCSV(String fileName) {
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Skip header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Map<String, String> item = new HashMap<>();
                    item.put("prenom", parts[0]);
                    item.put("nom", parts[1]);
                    item.put("matches", parts[2]);
                    item.put("victories", parts[3]);
                    data.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
