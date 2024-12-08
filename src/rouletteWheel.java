import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

public class rouletteWheel extends BorderPane {
    private ArrayList<rouletteColor> wheel;
    private rouletteColor result;
    private double[] numberPositionsX;
    private double[] numberPositionsY;
    private Random random;
    private Circle ball;

    public rouletteWheel(Circle ball, double wheelRadius) {
        this.ball = ball;
        wheel = new ArrayList<>();
        random = new Random();
        initializeWheel();
        initializeNumberPositions(wheelRadius);
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

    private void initializeNumberPositions(double wheelRadius) {
        numberPositionsX = new double[38];
        numberPositionsY = new double[38];
        double centerX = wheelRadius;
        double centerY = wheelRadius;
        for (int i = 0; i < 38; i++) {
            double angle = i * (360.0 / 38);
            numberPositionsX[i] = centerX + wheelRadius * Math.cos(Math.toRadians(angle));
            numberPositionsY[i] = centerY + wheelRadius * Math.sin(Math.toRadians(angle));
        }
    }

    public int spin() {
        int randomNumber = random.nextInt(38);
        result = wheel.get(randomNumber);
        return randomNumber;
    }

    public rouletteColor getResult() {
        return result;
    }

    public ArrayList<rouletteColor> getWheel() {
        return wheel;
    }

    public double[] getNumberPosition(int number) {
        return new double[]{numberPositionsX[number], numberPositionsY[number]};
    }

    public Circle getBall() {
        return ball;
    }
}