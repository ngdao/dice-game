import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;
import java.nio.file.*;

import dice.*;


public class TestStatsProcessor {
    private StatsProcessor proc;

    @Before
    public void setUp() {
        Database db = Database.create("mock", "dummyFilename");
        proc = StatsProcessor.create("concrete", db);
    }

    @Test
    public void getPlayerList() {
        String[] playerList = proc.getPlayerList();

        assertThat(playerList.length, equalTo(2));
        assertThat(playerList[0], equalTo("TAP"));
        assertThat(playerList[1], equalTo("CHS"));
    }

    @Test
    public void getAllStats() {
        StatsData stats = proc.getAllStats();

        assertThat(stats.getTotalRolls(), equalTo(5));
        assertEquals(1.666667, stats.getAvgRollsPerGame(), 0.00001);
        assertThat(stats.getCumulativeScore(), equalTo(46));
        assertThat(stats.getAvgScore(), equalTo(15.0));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(2.0));
    }

    @Test
    public void getPlayerStats() {
        StatsData stats = proc.getPlayerStats("CHS");
        StatsData stats2 = proc.getPlayerStats("TAP");

        assertThat(stats.getAvgRollsPerGame(), equalTo(1.0));
        assertThat(stats2.getAvgRollsPerGame(), equalTo(3.0));
    }

    @Test
    public void maxScore() {
        StatsData stats = proc.getAllStats();
        assertThat(stats.getMaxScore(), equalTo(22));
    }

    @Test
    public void maxScoreSingleRecord() {
        StatsData stats = proc.getPlayerStats("CHS");
        assertThat(stats.getMaxScore(), equalTo(18));
    }

    @Test
    public void getLeaderboard() {
        LeaderboardEntry[] leaderboard = proc.getLeaderboard();

        assertThat(leaderboard.length, equalTo(2));
        assertThat(leaderboard[0].getPlayerName(), equalTo("TAP"));
        assertThat(leaderboard[0].getHighestScore(), equalTo(22));
        assertThat(leaderboard[1].getPlayerName(), equalTo("CHS"));
        assertThat(leaderboard[1].getHighestScore(), equalTo(18));
    }

    @Test
    public void getEmptyAllStats() {
        Path path = Paths.get("src/test/resources/tempTestDb.csv");
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Database db = Database.create("concrete", path.toString());
        proc = StatsProcessor.create("concrete", db);
        StatsData stats = proc.getAllStats();

        assertThat(stats.getTotalRolls(), equalTo(0));
        assertThat(stats.getAvgRollsPerGame(), equalTo(0.0));
        assertThat(stats.getCumulativeScore(), equalTo(0));
        assertThat(stats.getAvgScore(), equalTo(0.0));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(0.0));
        assertThat(stats.getMaxScore(), equalTo(0));
    }
}
