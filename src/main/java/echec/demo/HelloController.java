package echec.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane chessboard;

    public void initialize() {
        // Initialization code if needed
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        // Code to handle piece movements
        // Use event.getSource() to get the clicked square
        // Use chessboard.getRowIndex(node) and chessboard.getColumnIndex(node) to get the position
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}