package dice;

public class RollRecord {
    private String userId;
    private int gameId;
    private int numDice;
    private int rollValue;
    private int score;

    /**
     * Creates an object after a game has been completed. The goal of this object is to provide details in the database.
     * 
     * @param  userId     The players name in the game. Consists of three letters, usually the players initials.
     * @param  gameId     ID associated with a particualar game.
     * @param  numDice    Number of dice the player chooses to roll.
     * @param  rollValue  The total value of all dice rolled by the player.
     * @param  rollsCount Number of rolls by the player before the game was finished.
     */
    public RollRecord(String userId, int gameId, int numDice, int rollValue,
                      int score) {
        this.userId = userId;
        this.gameId = gameId;
        this.numDice = numDice;
        this.rollValue = rollValue;
        this.score = score;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RollRecord)) {
            return false;
        }

        RollRecord that = (RollRecord) other;

        return this.userId.equals(that.userId) &&
            this.gameId == that.gameId &&
            this.numDice == that.numDice &&
            this.rollValue == that.rollValue &&
            this.score == that.score;
        }

    /**
     * Function to return the userId
     * @return string -> userId The players name in the game. Consists of three letters, usually the players initials.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Function to return the gameId
     * @return int -> gameId ID associated with a particualar game.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Function to return the total number of dice used in a game.
     * @return int -> numDice Number of dice the player chooses to roll.
     */
    public int getNumDice() {
        return numDice;
    }

    /**
     * Function to return the total value of all dice rolled by the player.
     * @return int -> rollValue The total value of all dice rolled by the player.
     */
    public int getRollValue() {
        return rollValue;
    }

    /**
     * Function to return the total score for a player.
     * @return int -> score The score after the game is finished.
     */
    public int getScore() {
        return score;
    }
}
