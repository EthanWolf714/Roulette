import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class Chip extends Circle{

    public Chip(double x, double y, int bet){
        super(x, y, 10);

        if(bet == 1){
            setFill(Color.WHITE);
            setStroke(Color.BLACK);
        }
        else if(bet == 5){
            setFill(Color.YELLOW);
            setStroke(Color.BLACK);
        }
        else if(bet == 10){
            setFill(Color.GREEN);
            setStroke(Color.BLACK);
        }
        else if(bet == 25){
            setFill(Color.BLUE);
            setStroke(Color.BLACK);
        }
        else if(bet == 50){
            setFill(Color.RED);
            setStroke(Color.BLACK);
        }
        else if(bet == 100){
            setFill(Color.BLACK);
            setStroke(Color.WHITE);
        }
    }
}
