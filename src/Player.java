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
        if(bet <= balance){
            currentBet = bet;
            balance -= bet;

        }else{
            System.out.println("Insufficient balance to place bet.");

        }

    }

    public void undoBet(){
        balance += currentBet;
        currentBet = 0;
    }


    public void clearBet() { 
        currentBet = 0; 
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

    public void winBet(int amount) {
        balance += amount;
    }
}