import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.geometry.Bounds;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import javafx.util.Duration;

public class roulletteController {
    /*
     * Manage interactions between the view and the model.
     Handle button clicks, chip placements, and coordination between
      the UI (RouletteView) and game state (RouletteWheel or other model classes).
     */
    private  RouletteView view;
    private  rouletteWheel wheel;

    public roulletteController(RouletteView view, rouletteWheel wheel){
        
        this.view = view;
        this.wheel = wheel;
        addEventHandlers();
        System.out.println("Controller activated");
    }

    private void addEventHandlers() {
        System.out.println("adding handlers");
        view.getSpinButton().setOnAction(event -> spinBall()); 
        view.getResetButton().setOnAction(event -> view.resetWheel());
    }

    public void spinAndHandleResult(){
        spinAndGetResult().thenAccept(result -> { 
            System.out.println("Ball landed on number: " + result); 
        }).exceptionally(e -> { 
            e.printStackTrace(); 
            return null; 
        });
    }

    public CompletableFuture<Integer> spinAndGetResult() { 
        return spinBall(); // Assuming this method returns the result after spinning 
    }

    private CompletableFuture<Integer> spinBall() { // Calculate the center of the wheelContainer 
        double wheelRadius = view.getWheelContainer().getWidth() / 2 - 15; // Adjust for ball size 
        Bounds bounds = view.getWheelContainer().localToScene(view.getWheelContainer().getBoundsInLocal()); 
        double centerX = bounds.getMinX() + bounds.getWidth() / 2; double centerY = bounds.getMinY() + bounds.getHeight() / 2; 
        Path circularPath = new Path(); circularPath.getElements().add(new MoveTo(centerX, centerY - wheelRadius)); 
        circularPath.getElements().add(new ArcTo(wheelRadius, wheelRadius, 0, centerX, centerY + wheelRadius, false, false)); 
        circularPath.getElements().add(new ArcTo(wheelRadius, wheelRadius, 0, centerX, centerY - wheelRadius, false, false)); 
        circularPath.getElements().add(new ClosePath()); PathTransition pathTransition = new PathTransition(); 
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
            System.out.println("Ball landed on number: " + randomNumber); 
            wheel.setWinningNumber(randomNumber);
            future.complete(randomNumber);
        }); 
        pathTransition.play(); 
        rotateTransition.play();
        return future;
        
    }

    
    

   
}