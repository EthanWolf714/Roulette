import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import javafx.util.Duration;

public class roulletteController {
    private RouletteView view;
    private rouletteWheel wheel;
    private Player player;
    private BettingBoard bettingBoard;

    public roulletteController(RouletteView view, rouletteWheel wheel) {
        this.view = view;
        this.wheel = wheel;
        this.player = new Player("Player", 500); // Starting balance of 500
        this.bettingBoard = new BettingBoard(this);
        addEventHandlers();
        System.out.println("Controller activated");
    }

    private void addEventHandlers() {
        System.out.println("adding handlers");
        view.getSpinButton().setOnAction(event -> spinAndHandleResult());
        view.getResetButton().setOnAction(event -> resetGame());
        view.getBettingBoard().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double x = event.getX();
            double y = event.getY();
            placeBet(x, y);
        });
    }

    public void spinAndHandleResult() {
        spinAndGetResult().thenAccept(result -> {
            System.out.println("Ball landed on number: " + result);
            bettingBoard.resolveBets();
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<Integer> spinAndGetResult() {
        return spinBall(); // Assuming this method returns the result after spinning
    }

    private CompletableFuture<Integer> spinBall() {
        double wheelRadius = view.getWheelImageView().getFitWidth() / 2 - 15; // Adjust for ball size
        Bounds bounds = view.getWheelImageView().localToScene(view.getWheelImageView().getBoundsInLocal());
        double centerX = bounds.getMinX() + bounds.getWidth() / 2;
        double centerY = bounds.getMinY() + bounds.getHeight() / 2;

        Path circularPath = new Path();
        circularPath.getElements().add(new MoveTo(centerX, centerY - wheelRadius));
        circularPath.getElements().add(new ArcTo(wheelRadius, wheelRadius, 0, centerX, centerY + wheelRadius, false, false));
        circularPath.getElements().add(new ArcTo(wheelRadius, wheelRadius, 0, centerX, centerY - wheelRadius, false, false));
        circularPath.getElements().add(new ClosePath());

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(5));
        pathTransition.setPath(circularPath);
        pathTransition.setNode(view.getBall());
        pathTransition.setCycleCount(PathTransition.INDEFINITE); // To keep the ball moving continuously
        pathTransition.setInterpolator(Interpolator.LINEAR); // Linear for constant speed

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(5), view.getWheelImageView());
        rotateTransition.setByAngle(360);
        rotateTransition.setInterpolator(Interpolator.LINEAR); // Linear for constant speed
        rotateTransition.setCycleCount(1);

        CompletableFuture<Integer> future = new CompletableFuture<>();
        rotateTransition.setOnFinished(event -> {
            pathTransition.stop();
            int randomNumber = wheel.spin();
            double[] position = wheel.getNumberPosition(randomNumber);
            view.getBall().setTranslateX(position[0]);
            view.getBall().setTranslateY(position[1]);
            System.out.println("Ball landed on number: " + randomNumber);
            future.complete(randomNumber);
        });

        pathTransition.play();
        rotateTransition.play();
        return future;
    }

    private void placeBet(double x, double y) {
        // Check if the click is within the betting board image
        Bounds bounds = view.getBettingBoard().getBoundsInLocal();
        if (bounds.contains(x, y)) {
            // Determine the bet position based on the coordinates
            String position = determineBetPosition(x, y);
            if (position != null) {
                // Get the bet amount
                int betAmount = Integer.parseInt(view.getChipSelector().getValue());
                // Check if the player has sufficient balance
                if (player.getBalance() >= betAmount) {
                    // Add a chip to the betting board
                    view.addChipToBoard(x, y, betAmount);
                    System.out.println("Placed bet on: " + position);
                    // Update balance
                    player.placeBet(betAmount);
                    view.updateBalance(player.getBalance());
                } else {
                    System.out.println("Insufficient balance to place bet.");
                }
            }
        }
    }

    private String determineBetPosition(double x, double y) {
        // Placeholder logic to determine the bet position based on the coordinates
        // This should be replaced with actual logic based on the layout of the betting board
        for (Map.Entry<String, double[]> entry : view.getBettingPositions().entrySet()) {
            double[] coords = entry.getValue();
            if (Math.abs(coords[0] - x) < 25 && Math.abs(coords[1] - y) < 25) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void resetGame() {
        player.undoBet();
        view.updateBalance(player.getBalance());
        view.resetWheel();
    }

    public Player getPlayer() {
        return player;
    }

    public RouletteView getView() {
        return view;
    }
}