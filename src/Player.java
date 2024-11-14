public class Player {
    private int balance;
    private String name;
    private int currentBet;

    public Player(String name, int startingBalance){
        this.name = name;
        this.balance = startingBalance;
        this.currentBet = 0;
    }

    public void placeBet(int bet){


    }

    public void undoBet(){

    }

    public int getCurrentBet(){
        return currentBet;
    }

    public int getBalance() {
        return balance;
    }

    public String getName(){
        return name;
    }

}