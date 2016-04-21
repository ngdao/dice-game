package dice;


public class StatsData {
    private final int totalRolls;
    private final double avgRollsPerGame;
    private final int cumulativeScore;
    private final double avgScore;
    private final double avgNumDiceUsed;
    private final int maxScore;

    public int getTotalRolls() {
        return totalRolls;
    }

    public double getAvgRollsPerGame() {
        return avgRollsPerGame;
    }

    public int getCumulativeScore() {
        return cumulativeScore;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public double getAvgNumDiceUsed() {
        return avgNumDiceUsed;
    }

    public int getMaxScore() {
        return maxScore;
    }

    // Builder Pattern adapted from here:
    // http://www.informit.com/articles/article.aspx?p=1216151&seqNum=2
    public static class Builder {

        private int totalRolls = 0;
        private double avgRollsPerGame = 0.0;
        private int cumulativeScore = 0;
        private double avgScore = 0.0;
        private double avgNumDiceUsed = 0.0;
        private int maxScore = 0;

        public Builder totalRolls(int val) {
            totalRolls = val;
            return this;
        }

        public Builder avgRollsPerGame(double val) {
            avgRollsPerGame = val;
            return this;
        }

        public Builder cumulativeScore(int val) {
            cumulativeScore = val;
            return this;
        }

        public Builder avgScore(double val) {
            avgScore = val;
            return this;
        }

        public Builder avgNumDiceUsed(double val) {
            avgNumDiceUsed = val;
            return this;
        }

        public Builder maxScore(int val) {
            maxScore = val;
            return this;
        }

        public StatsData build() {
            return new StatsData(this);
        }
    }

    private StatsData(Builder builder) {
        totalRolls = builder.totalRolls;
        avgRollsPerGame = builder.avgRollsPerGame;
        cumulativeScore = builder.cumulativeScore;
        avgScore = builder.avgScore;
        avgNumDiceUsed = builder.avgNumDiceUsed;
        maxScore = builder.maxScore;
    }
}
