public StatsData getAllStats() {
	String input = "";
	computeStats(input);
}

public StatsData getPlayerStats(String playerName) {
	computeStats(playerName);
}

private StatsData computeStats(String playerName) {
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

	if (playerName == "") {
		RollRecord[] records = database.getAllRecords();
		playerExists = true;
	} else {
		RollRecord[] records = database.getRecordsForUser(playerName);

		for (int index = 0; index < playerList.length; index++) {
		    if (playerList[index] == playerName) {
		        playerExists = true;
		    }
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
}