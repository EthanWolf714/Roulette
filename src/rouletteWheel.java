import java.util.ArrayList;

public class rouletteWheel {
    private Player player;
    private rouletteBall ball;
    private ArrayList<Integer> numbers;

    public rouletteWheel(){
        this.numbers = new ArrayList<>();
        this.ball = new rouletteBall();
    }

    public void spinWheel(){
        
    }

    public int getResult(){
        return 1;
    }


    public ArrayList<Integer> getNUmbers(){
        return numbers;
    }

    public void assignColor(int number){

    }
}
