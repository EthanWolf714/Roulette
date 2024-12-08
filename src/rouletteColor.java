public abstract class rouletteColor {
    protected int multiplier;
    protected int number;

    public rouletteColor(int num){
        this.number = num;
    }

    public int getNumber(){
        return number;
    }

    public abstract int getMultiplier();

}
