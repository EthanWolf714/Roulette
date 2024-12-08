import javafx.animation.RotateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class RouletteView extends BorderPane {

    private Stage stage;      // Reference to the primary stage  
    private Pane chipOverlay; // Pane for placing chips
    private ComboBox<String> chipSelector; // Dropdown for chip selection
    private Circle ball; // Roulette ball
    private ImageView wheelImageView; // ImageView for the roulette wheel
    private ImageView bettingBoardImageView; // ImageView for the betting board
    private StackPane bettingBoardPane; // StackPane for the betting board
    private Button spinButton; 
    private Button resetButton;
    private StackPane wheelContainer;
    private Map<String, double[]> bettingPositions; // Map to store betting positions
    private Label balanceLabel; // Label to display player balance

    public RouletteView(Stage stage) {
        System.out.println("Initializing view");
        this.stage = stage;   // Assign the stage
        setupView();

        Scene scene = new Scene(this, 800, 600);
        stage.setTitle("Roulette Wheel");
        stage.setScene(scene);
        stage.setResizable(false);
    }

    private void setupView() {
        // Set background image
        System.out.println("Setting up view components");
        Image backgroundImage = new Image("file:img/Casino-banner-Casino-Background-Graphics-35985674-1.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);
        this.getChildren().add(backgroundImageView);

        // Create the roulette wheel with an image
        Image wheelImage = new Image("file:img/americanroulette.png");
        wheelImageView = new ImageView(wheelImage);
        wheelImageView.setFitWidth(325); // Increase the size of the wheel
        wheelImageView.setFitHeight(325);

        // Create the roulette ball
        ball = new Circle(10, Color.WHITE); // Increase the size of the ball

        chipOverlay = new Pane(); // Chip Overlay
        chipOverlay.setPickOnBounds(false); // Allows clicks to pass through transparent areas
        wheelContainer = new StackPane(wheelImageView, ball, chipOverlay);
        wheelContainer.setAlignment(Pos.CENTER);
        this.setCenter(wheelContainer);

        // Create the betting board with an image
        Image bettingBoardImage = new Image("file:img/bettingboard.jpg");
        bettingBoardImageView = new ImageView(bettingBoardImage);
        bettingBoardImageView.setPreserveRatio(true); // Preserve the aspect ratio
        bettingBoardImageView.setFitWidth(600); // Adjust the width to fit nicely
        bettingBoardImageView.setFitHeight(150); // Adjust the height to fit nicely

        bettingBoardPane = new StackPane(bettingBoardImageView);
        bettingBoardPane.setPadding(new Insets(10));
        bettingBoardPane.setAlignment(Pos.CENTER);

        this.setBottom(bettingBoardPane);

        // Initialize betting positions
        initializeBettingPositions(bettingBoardImageView.getBoundsInLocal());

        // Chip selector and buttons
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_LEFT); // Align to the left
        controls.setTranslateX(-50); // Move to the left

        chipSelector = new ComboBox<>();
        chipSelector.getItems().addAll("1", "5", "10", "25", "50", "100");
        chipSelector.setValue("1");

        // Set font for labels and buttons
        Font font = Font.font("Arial", FontWeight.BOLD, 14);
        Label chipLabel = new Label("Select Chip:");
        chipLabel.setFont(font);
        chipLabel.setTextFill(Color.WHITE);

        spinButton = new Button("Spin");
        System.out.println("Spin button initialized: " + (spinButton != null));
        spinButton.setFont(font);
        spinButton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");

        resetButton = new Button("Reset");
        System.out.println("Reset button initialized: " + (resetButton != null));
        resetButton.setFont(font);
        resetButton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");

        // Balance label
        balanceLabel = new Label("Balance: $500");
        balanceLabel.setFont(font);
        balanceLabel.setTextFill(Color.WHITE);

        controls.getChildren().addAll(chipLabel, chipSelector, spinButton, resetButton, balanceLabel); 
        System.out.println("Controls added to the view");
        this.setRight(controls);
    }

    private void initializeBettingPositions(Bounds bounds) {
        bettingPositions = new HashMap<>();
        double width = bounds.getWidth();
        double height = bounds.getHeight();

        // Example positions, these should be adjusted based on the actual layout of the betting board
        bettingPositions.put("0", new double[]{width * 0.05, height * 0.1});
        bettingPositions.put("00", new double[]{width * 0.1, height * 0.1});
        for (int i = 1; i <= 36; i++) {
            bettingPositions.put(String.valueOf(i), new double[]{width * (0.05 + (i % 12) * 0.075), height * (0.2 + (i / 12) * 0.1)});
        }
        // Add positions for other bets
        bettingPositions.put("1st 12", new double[]{width * 0.05, height * 0.8});
        bettingPositions.put("2nd 12", new double[]{width * 0.2, height * 0.8});
        bettingPositions.put("3rd 12", new double[]{width * 0.35, height * 0.8});
        bettingPositions.put("Column 1", new double[]{width * 0.5, height * 0.1});
        bettingPositions.put("Column 2", new double[]{width * 0.5, height * 0.3});
        bettingPositions.put("Column 3", new double[]{width * 0.5, height * 0.5});
        bettingPositions.put("1-18", new double[]{width * 0.65, height * 0.1});
        bettingPositions.put("19-36", new double[]{width * 0.65, height * 0.3});
        bettingPositions.put("Red", new double[]{width * 0.8, height * 0.1});
        bettingPositions.put("Black", new double[]{width * 0.8, height * 0.3});
        bettingPositions.put("Odd", new double[]{width * 0.95, height * 0.1});
        bettingPositions.put("Even", new double[]{width * 0.95, height * 0.3});
    }

    public Circle getBall(){
        return ball;
    }

    public ImageView getWheelImageView() {
        return wheelImageView;
    }

    public Button getSpinButton() {
        return spinButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public ComboBox<String> getChipSelector() {
        return chipSelector;
    }

    public void addChipToBoard(double x, double y, int betAmount) {
        Chip chip = new Chip(x, y, betAmount);
        chipOverlay.getChildren().add(chip);
    }


    public void clearChips() {
        chipOverlay.getChildren().clear();
    }

    public void show() {
        stage.show();
    }

    public StackPane getWheelContainer(){
        return wheelContainer;
    }

    public void resetWheel() {
        clearChips();
        System.out.println("Chips cleared");
    }

    public void updateBalance(int balance) {
        balanceLabel.setText("Balance: $" + balance);
    }

    public ImageView getBettingBoard() {
        return bettingBoardImageView;
    }

    public Map<String, double[]> getBettingPositions() {
        return bettingPositions;
    }
}