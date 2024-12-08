import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class board {
    private Map<Player, Integer> bets;
    private roulletteController controller;

    public board(roulletteController controller){
        this.controller = controller;
        bets = new HashMap<>();

    }

    public void placeBet(Player player, int amount, String betOption){
        if(amount <= player.getBalance()){
            player.placeBet(amount);
            bets.put(player, amount);
            System.out.println(player.getName() + " placed a bet of $ " + amount + " on " + betOption);

        }else{
            System.out.println("Insufficient balance to place bet.");
        }
    }
    
  public void resolveBets() { 
     
        controller.spinAndGetResult().thenAccept(winningNumber -> {
            // Get the winning number 
        String winningOption = determineWinningOption(winningNumber); 
        for (Map.Entry<Player, Integer> entry : bets.entrySet()) { 
            Player player = entry.getKey(); int betAmount = entry.getValue(); 
            if (betOptionMatches(player.getCurrentBet(), winningOption)) { 
                int winnings = calculateWinnings(betAmount, winningOption); 
                player.winBet(winnings); 
                System.out.println(player.getName() + " won $" + winnings + "!"); 
            } else { 
                System.out.println(player.getName() + " lost their bet."); 
            } 
            player.clearBet(); 
        } // Clear all bets after resolving 
        bets.clear(); 
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
        
    
   
  }
    private boolean betOptionMatches(int betOption, String winningOption) { 
       try{
        int betNumber = Integer.parseInt(winningOption);
        return betOption == betNumber;
       }catch(NumberFormatException e){
         // Handle non-integer bet options (like Red, Black, etc.)
            return winningOption.equalsIgnoreCase("Red") && betOption % 2 != 0 || 
            winningOption.equalsIgnoreCase("Black") && betOption % 2 == 0;
        }

    } 
    private int calculateWinnings(int betAmount, String betOption) { 
        if(betOption.equalsIgnoreCase("Red") || betOption.equalsIgnoreCase("Black")){
            return betAmount * 2;
        }else{
            return betAmount * 36; //payout for exactNumber match
        }
         
        // Example: simple 1:1 payout 
    } 
    private String determineWinningOption(int winningNumber) { 
        if(winningNumber == 0){
            return "0";
        }else if(winningNumber % 2 == 0){
            return "Black";
        }else{
            return "Red"; 
        }
        
        
    }
    
}
