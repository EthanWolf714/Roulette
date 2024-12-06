import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class roulletteController {
    /*
     * Manage interactions between the view and the model.
     Handle button clicks, chip placements, and coordination between
      the UI (RouletteView) and game state (RouletteWheel or other model classes).
     */
    private final RouletteView view;
    private final rouletteWheel wheel;

    public roulletteController(Stage stage){
        this.view = new RouletteView(stage);
        this.wheel = new rouletteWheel();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Handle Spin Button Click
        Button spinButton = (Button) view.lookup(".button");
        spinButton.setOnAction(event -> {
            wheel.spinWheel();
            updateView();
        });

        // Handle Reset Button Click
        Button resetButton = (Button) view.lookup(".button");
        resetButton.setOnAction(event -> {
            resetWheel();
        });
    }

    private void updateView() {
        rouletteColor result = wheel.getResult();
        // Update the view with the result
        // For simplicity, let's just print the result to the console
        System.out.println("Wheel Result: " + result.getNumber() + " " + result.getColor());

        // Update the ball position based on the result
        updateBallPosition(result.getNumber());
    }

    private void updateBallPosition(int number) {
        // Logic to update the ball position based on the number
        // This is a placeholder and should be replaced with actual logic
        double angle = calculateAngleForNumber(number);
        view.updateBallPosition(angle);
    }

    private double calculateAngleForNumber(int number) {
        // Placeholder logic to calculate the angle for a given number
        // This should be replaced with actual logic based on the wheel layout
        return number * 10; // Example: each number is 10 degrees apart
    }

    private void resetWheel() {
        // Reset the wheel and clear any placed chips
        // For simplicity, let's just print a reset message to the console
        System.out.println("Wheel Reset");
        view.clearChips();
    }
}