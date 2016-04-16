package dice;

import java.util.ArrayList;


public abstract class StatsProcessor {

    protected Database database;

    public static StatsProcessor create(String type, Database database) {
        if (type.equals("mock")) {
            return new MockStatsProcessor(database);
        }
        else {
            return new ConcreteStatsProcessor(database);
        }
    }

    public static StatsProcessor create() {
        Database db = Database.create("mock", "dummyFilename");
        return create("mock", db);
    }

    public abstract String[] getPlayerList();
    public abstract StatsData getPlayerStats(String playerName);
    public abstract StatsData getAllStats();
    public abstract LeaderboardEntry[] getLeaderboard();
}


class MockStatsProcessor extends StatsProcessor {

    public MockStatsProcessor(Database db) {
        database = db;
    }

    public String[] getPlayerList() {
        String[] list = new String[3];
        list[0] = "ABC";
        list[1] = "DEF";
        list[2] = "GHI";
        return list;
    }

    public StatsData getPlayerStats(String playerName) {

        StatsData stats;

        if (playerName.equals("ABC")) {
            stats = new StatsData.Builder()
                .totalRolls(100)
                .avgRollsPerGame(4.3)
                .cumulativeScore(2000)
                .avgScore(18.2)
                .avgNumDiceUsed(2.1)
                .build();
        }
        else if (playerName.equals("DEF")) {
            stats = new StatsData.Builder()
                .totalRolls(200)
                .avgRollsPerGame(2.2)
                .cumulativeScore(4000)
                .avgScore(19.0)
                .avgNumDiceUsed(1.8)
                .build();
        }
        else if (playerName.equals("GHI")) {
            stats = new StatsData.Builder()
                .totalRolls(300)
                .avgRollsPerGame(4.0)
                .cumulativeScore(5000)
                .avgScore(22.1)
                .avgNumDiceUsed(1.2)
                .build();
        }
        else {
            // defaults
            stats = new StatsData.Builder().build();
        }

        return stats;
    }

    public StatsData getAllStats() {
        StatsData stats = new StatsData.Builder()
            .totalRolls(1001)
            .avgRollsPerGame(3.4)
            .cumulativeScore(12000)
            .avgScore(17.2)
            .avgNumDiceUsed(1.4)
            .build();
        return stats;
    }

    public LeaderboardEntry[] getLeaderboard() {
        LeaderboardEntry entryA = new LeaderboardEntry("TAP", 46);
        LeaderboardEntry entryB = new LeaderboardEntry("CCS", 22);
        LeaderboardEntry entryC = new LeaderboardEntry("AAA", 20);

        LeaderboardEntry[] array = {entryA, entryB, entryC};
        return array;
    }
}


/**
 * A class to process the stats collected in our database (CSV file)
 */
class ConcreteStatsProcessor extends StatsProcessor {

    public ConcreteStatsProcessor(Database db) {
        database = db;
    }

    public String[] getPlayerList() {
        RollRecord[] records = database.getAllRecords();

        ArrayList<String> output = new ArrayList();

        String prevName = "";
        for (int index = 0; index < records.length; index++) {

            String name = records[index].getUserId();
            if (!name.equals(prevName)) {
                // found one we haven't added yet
                output.add(name);
            }

            prevName = name;
        }

        // convert ArrayList<String> to String[]
        return output.toArray(new String[output.size()]);
    }

    public StatsData getPlayerStats(String playerName) {
        RollRecord[] records = database.getRecordsForUser(playerName);
        boolean playerExists = false;
        String[] playerList = getPlayerList();
        int dbLength = records.length;
        int totalRolls = dbLength;
        int totalGames = 0;
        int cumulativeScore = 0;
        int totalDiceUsed = 0;
        double avgRolls = 0.0;
        double avgScore = 0.0;
        double avgNumDiceUsed = 0.0;

        // check to see if the Given player name exists
        for (int index = 0; index < playerList.length; index++) {
            if (playerList[index] == playerName) {
                playerExists = true;
            }
        }

        if (playerExists) {
            for (int index = 0; index < dbLength; index++) {
                int temp = 0;
                RollRecord record = records[index];
                totalGames = record.getGameId();
                totalDiceUsed += record.getNumDice();
            }

            avgNumDiceUsed = totalDiceUsed / dbLength;
            totalGames = totalGames + 1;
            avgRolls = totalRolls / totalGames;

            if (dbLength > 1) {
                for (int index = 1; index < dbLength; index++) {
                    int[] tempArray = new int[totalGames];
                    int temp = 0;
                    RollRecord record = records[index];
                    RollRecord previousRecord = records[index - 1];
                    if (record.getGameId() != previousRecord.getGameId()) {
                        cumulativeScore += previousRecord.getScore();
                    }
                    if (index == dbLength - 1) {
                        cumulativeScore += record.getScore();
                    }
                }
            } else {
                RollRecord record = records[0];
                cumulativeScore = record.getScore();
            }

            avgScore = cumulativeScore / totalGames;

            return new StatsData.Builder()
                .totalRolls(dbLength)
                .avgRollsPerGame(avgRolls)
                .cumulativeScore(cumulativeScore)
                .avgScore(avgScore)
                .avgNumDiceUsed(avgNumDiceUsed)
                .build();
        }


        // Pull data out of records here

        return computeStats(records);
    }

    public StatsData getAllStats() {
        RollRecord[] records = database.getAllRecords();

        int dbLength = records.length;
        int totalRolls = dbLength;
        int totalGames = 0;
        int cumulativeScore = 0;
        int totalDiceUsed = 0;
        double avgRolls = 0.0;
        double avgScore = 0.0;
        double avgNumDiceUsed = 0.0;

        for (int index = 0; index < dbLength; index++) {
            int temp = 0;
            RollRecord record = records[index];
            totalGames = record.getGameId();
            totalDiceUsed += record.getNumDice();
        }

        avgNumDiceUsed = totalDiceUsed / dbLength;
        totalGames = totalGames + 1;
        avgRolls = totalRolls / totalGames;

        if (dbLength > 1) {
            for (int index = 1; index < dbLength; index++) {
                int[] tempArray = new int[totalGames];
                int temp = 0;
                RollRecord record = records[index];
                RollRecord previousRecord = records[index - 1];
                if (record.getGameId() != previousRecord.getGameId()) {
                    cumulativeScore += previousRecord.getScore();
                }
                if (index == dbLength - 1) {
                    cumulativeScore += record.getScore();
                }
            }
        } else {
            RollRecord record = records[0];
            cumulativeScore = record.getScore();
        }

        avgScore = cumulativeScore / totalGames;

        return new StatsData.Builder()
            .totalRolls(dbLength)
            .avgRollsPerGame(avgRolls)
            .cumulativeScore(cumulativeScore)
            .avgScore(avgScore)
            .avgNumDiceUsed(avgNumDiceUsed)
            .build();
    }

    private StatsData computeStats(RollRecord[] records) {
        return new StatsData.Builder().build();
    }

    public LeaderboardEntry[] getLeaderboard() {
        return new LeaderboardEntry[1];
    }
}
