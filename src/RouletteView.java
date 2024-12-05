import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        // Create the roulette wheel
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

        // Add betting options to the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                Label numberLabel = new Label(String.valueOf(i * 12 + j + 1));
                numberLabel.setStyle("-fx-border-color: black; -fx-padding: 5;");
                bettingGrid.add(numberLabel, j, i);
            }
        }

        // Add 0 and 00
        Label zeroLabel = new Label("0");
        zeroLabel.setStyle("-fx-border-color: black; -fx-padding: 5;");
        bettingGrid.add(zeroLabel, 0, 3);
        Label doubleZeroLabel = new Label("00");
        doubleZeroLabel.setStyle("-fx-border-color: black; -fx-padding: 5;");
        bettingGrid.add(doubleZeroLabel, 1, 3);

        this.setBottom(bettingGrid);

        // Chip selector and buttons
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);

        chipSelector = new ComboBox<>();
        chipSelector.getItems().addAll("1", "5", "10", "25", "50", "100");
        chipSelector.setValue("1");

        Button spinButton = new Button("Spin");
        Button resetButton = new Button("Reset");

        controls.getChildren().addAll(new Label("Select Chip:"), chipSelector, spinButton, resetButton);
        this.setRight(controls);
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
