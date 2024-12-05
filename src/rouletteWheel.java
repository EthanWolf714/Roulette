import java.util.ArrayList;
import java.util.Random;

public class rouletteWheel {
    private Player player;
    private rouletteBall ball;
    private ArrayList<String> numbers;
    private ArrayList<String> colors;
    private String result;

    public rouletteWheel(){
        this.numbers = new ArrayList<>();
        this.colors = new ArrayList<>();
        this.ball = new rouletteBall();
        initializeWheel();
    }

    private void initializeWheel() {
        for (int i = 1; i <= 36; i++) {
            numbers.add(String.valueOf(i));
            assignColor(i);
        }
        // Add 0 and 00 for American roulette
        numbers.add("0");
        colors.add("Green");
        numbers.add("00");
        colors.add("Green");
    }

    public void spinWheel(){
        Random rand = new Random();
        result = numbers.get(rand.nextInt(numbers.size()));
    }

    public String getResult(){
        return result;
    }

    public ArrayList<String> getNumbers(){
        return numbers;
    }

    public ArrayList<String> getColors(){
        return colors;
    }

    private void assignColor(int number){
        if (number == 0) {
            colors.add("Green");
        } else if ((number >= 1 && number <= 10) || (number >= 19 && number <= 28)) {
            if (number % 2 == 0) {
                colors.add("Black");
            } else {
                colors.add("Red");
            }
        } else {
            if (number % 2 == 0) {
                colors.add("Red");
            } else {
                colors.add("Black");
            }
        }
    }
}