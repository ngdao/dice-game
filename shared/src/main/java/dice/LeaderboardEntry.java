package dice;

public class LeaderboardEntry {

    private String playerName;
    private int highestScore;

    public LeaderboardEntry(String playerName, int highestScore) {
        this.playerName = playerName;
        this.highestScore = highestScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHighestScore() {
        return highestScore;
    }
}
