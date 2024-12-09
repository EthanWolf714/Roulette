import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BettingBoard {
    private Map<Player, Map<String, Integer>> bets;
    private rouletteController controller;

    public BettingBoard(rouletteController controller) {
        this.controller = controller;
        bets = new HashMap<>();
    }

    public void placeBet(Player player, int amount, String betOption) {
        if (amount <= player.getBalance()) {
            player.placeBet(amount);
            bets.computeIfAbsent(player, k -> new HashMap<>()).put(betOption, amount);
            controller.getView().updateBalance(player.getBalance());
            System.out.println(player.getName() + " placed a bet of $" + amount + " on " + betOption);
        } else {
            System.out.println("Insufficient balance to place bet.");
        }
    }

    public void resolveBets(rouletteColor winningNumber) {
        String winningOption = determineWinningOption(winningNumber);
        for (Map.Entry<Player, Map<String, Integer>> entry : bets.entrySet()) {
            Player player = entry.getKey();
            Map<String, Integer> playerBets = entry.getValue();
            for (Map.Entry<String, Integer> betEntry : playerBets.entrySet()) {
                String betOption = betEntry.getKey();
                int betAmount = betEntry.getValue();
                if (betOptionMatches(betOption, winningOption, winningNumber.getNumber())) {
                    int winnings = calculateWinnings(betAmount, betOption);
                    player.winBet(winnings);
                    System.out.println(player.getName() + " won $" + winnings + "!");
                } else {
                    System.out.println(player.getName() + " lost their bet on " + betOption);
                }
            }
            player.clearBet();
        }
        bets.clear();
        controller.getView().updateBalance(controller.getPlayer().getBalance());
    }

    private boolean betOptionMatches(String betOption, String winningOption, int winningNumber) {
        switch (betOption) {
            case "Red":
            case "Black":
            case "Odd":
            case "Even":
            case "1-18":
            case "19-36":
                return betOption.equalsIgnoreCase(winningOption);
            case "1st 12":
                return winningNumber >= 1 && winningNumber <= 12;
            case "2nd 12":
                return winningNumber >= 13 && winningNumber <= 24;
            case "3rd 12":
                return winningNumber >= 25 && winningNumber <= 36;
            case "Column 1":
                return winningNumber % 3 == 1;
            case "Column 2":
                return winningNumber % 3 == 2;
            case "Column 3":
                return winningNumber % 3 == 0;
            default:
                try {
                    int betNumber = Integer.parseInt(betOption);
                    return betNumber == winningNumber;
                } catch (NumberFormatException e) {
                    return false;
                }
        }
    }

    private int calculateWinnings(int betAmount, String betOption) {
        switch (betOption) {
            case "Red":
            case "Black":
            case "Odd":
            case "Even":
            case "1-18":
            case "19-36":
                return betAmount * 2;
            case "1st 12":
            case "2nd 12":
            case "3rd 12":
            case "Column 1":
            case "Column 2":
            case "Column 3":
                return betAmount * 3;
            default:
                return betAmount * 36;
        }
    }

    private String determineWinningOption(rouletteColor winningNumber) {
        if (winningNumber instanceof Green) {
            return "Green";
        } else if (winningNumber instanceof Black) {
            return "Black";
        } else {
            return "Red";
        }
    }
}
