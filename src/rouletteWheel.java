
import java.util.Random;

import javafx.scene.layout.BorderPane;

import javafx.scene.shape.Circle;


public class rouletteWheel extends BorderPane {
    private ArrayList<rouletteColor> wheel;
    private rouletteColor result;
    
    public rouletteWheel() {
        wheel = new ArrayList<>();
        initializeWheel();
    }

    
    private void initializeNumberPostions(double wheelRadius){
        numberPositionsX = new double[37];
        numberPositionsY = new double[37];
        double centerX = wheelRadius;
        double centerY = wheelRadius;
        for(int i = 0; i < 37; i++){
            double angle = i * (360.0 / 37);
            numberPositionsX[i] = centerX + wheelRadius * Math.cos(Math.toRadians(angle));
            numberPositionsY[i] = centerY + wheelRadius * Math.sin(Math.toRadians(angle));

        }
    }

    public int spin() { 
        int randomNumber = random.nextInt(37); 
        ball.setCenterX(numberPositionsX[randomNumber]); 
        ball.setCenterY(numberPositionsY[randomNumber]); 
        return randomNumber;
    }

    public int getWinningNumber(){
        return winningNumber;
    }

    public void setWinningNumber(int winningNumber){
        this.winningNumber = winningNumber;
    }
}