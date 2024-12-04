import java.beans.EventHandler;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class RouletteView extends BorderPane {

    private Stage stage;      // Reference to the primary stage  
    private Pane chipOverlay; // Pane for placing chips
    private ComboBox<String> chipSelector; // Dropdown for chip selection

    public RouletteView(Stage stage) {
        this.stage = stage;   // Assign the stage
        setupView();

        // Set up the scene and add it to the stage
        Scene scene = new Scene(this, 800, 600);
        stage.setTitle("Roulette Wheel");
        stage.setScene(scene);
    }

     private void setupView() {
        Circle wheel = new Circle(100, Color.DARKGREEN); // Green circle as the wheel for now.
        wheel.setStroke(Color.BLACK);
        wheel.setStrokeWidth(2);


        chipOverlay = new Pane(); // Chip Overlay
        chipOverlay.setPickOnBounds(false); // Allows clicks to pass through transparent areas
        StackPane wheelContainer = new StackPane(wheel, chipOverlay);
        this.setCenter(wheelContainer);

        // Betting area
        GridPane bettingGrid = new GridPane();
        bettingGrid.setPadding(new Insets(10));
        bettingGrid.setHgap(5);
        bettingGrid.setVgap(5);
        bettingGrid.setAlignment(Pos.CENTER);

        // Example of betting (to be adjusted)
        for (int i = 0; i < 37; i++) {
            Button numberButton = new Button(String.valueOf(i));
            numberButton.setPrefSize(50, 50);
            bettingGrid.add(numberButton, i % 12, i / 12); // Arrange in a grid
        }

        // Color betting at the bottom (to be adjusted)
        HBox colorBetting = new HBox(10);
        colorBetting.setAlignment(Pos.CENTER);
        colorBetting.setPadding(new Insets(10));
        Button redBet = new Button("Red");
        redBet.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        Button blackBet = new Button("Black");
        blackBet.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Button greenBet = new Button("Green");
        greenBet.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        colorBetting.getChildren().addAll(redBet, blackBet, greenBet);

        // Place betting grid and color betting in the bottom part (to be adjusted)
        VBox bettingArea = new VBox(10, bettingGrid, colorBetting);
        bettingArea.setAlignment(Pos.CENTER);
        setBottom(bettingArea);

        // Chip selection dropdown (adjusting chip prices)
        chipSelector = new ComboBox<>();
        chipSelector.getItems().addAll("50", "100", "500", "1000");
        chipSelector.setValue("50"); // Default value
        HBox chipSelectionArea = new HBox(new Label("Select Chip: "), chipSelector);
        chipSelectionArea.setAlignment(Pos.CENTER);
        chipSelectionArea.setPadding(new Insets(10));

        // Play button at the top
        Button playButton = new Button("Play");
        playButton.setPrefSize(100, 50);
        VBox topArea = new VBox(playButton, chipSelectionArea);
        topArea.setAlignment(Pos.CENTER);
        setTop(topArea);
    }

    public void addChip(double x, double y) { // Method to later be used by controller to create chips on places bet.
        Circle chip = new Circle(10, Color.BLUE); // Temporary. Switch statement to be used later.
        chip.setTranslateX(x);
        chip.setTranslateY(y);
        chipOverlay.getChildren().add(chip);
    }

     

    public void show(){
        stage.show();
    }
}
