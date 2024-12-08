import java.util.ArrayList;
import java.util.Random;
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

public class rouletteWheel extends BorderPane {
    private ArrayList<rouletteColor> wheel;
    private rouletteColor result;
    
    public rouletteWheel() {
        wheel = new ArrayList<>();
        initializeWheel();
    }

    private void initializeWheel() {
        // Add numbers and colors to the wheel
        for (int i = 1; i <= 36; i++) {
            if ((i >= 1 && i <= 10) || (i >= 19 && i <= 28)) {
                if (i % 2 == 0) {
                    wheel.add(new Black(i));
                } else {
                    wheel.add(new Red(i));
                }
            } else {
                if (i % 2 == 0) {
                    wheel.add(new Red(i));
                } else {
                    wheel.add(new Black(i));
                }
            }
        }
        // Add 0 and 00
        wheel.add(new Green(0));
        wheel.add(new Green(37)); // 37 represents 00
    }

    public void spinWheel() {
        Random rand = new Random();
        result = wheel.get(rand.nextInt(wheel.size()));
    }

    public rouletteColor getResult() {
        return result;
    }

    public ArrayList<rouletteColor> getWheel() {
        return wheel;
    }
}