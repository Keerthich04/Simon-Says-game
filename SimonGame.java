import java.util.ArrayList;
import java.util.Random;

public class SimonGame {
    private ArrayList<String> pattern;
    private int playerIndex;
    private String[] colors = {"Pink", "Green", "Blue", "Lavender"};
    private Random random;
    private int maxRounds;

    public SimonGame(int maxRounds) {
        pattern = new ArrayList<>();
        random = new Random();
        this.maxRounds = maxRounds;
        resetGame();
    }

    public void resetGame() {
        pattern.clear();
        playerIndex = 0;
        addNextStep();
    }

    public void addNextStep() {
        pattern.add(colors[random.nextInt(colors.length)]);
        playerIndex = 0;
    }

    public ArrayList<String> getPattern() {
        return pattern;
    }

    public boolean checkMove(String color) {
        if (!pattern.get(playerIndex).equals(color)) {
            return false;
        }
        playerIndex++;
        return true;
    }

    public boolean isRoundComplete() {
        return playerIndex == pattern.size();
    }

    public boolean isGameWon() {
        return pattern.size() >= maxRounds;
    }
}
