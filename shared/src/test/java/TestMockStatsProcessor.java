import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;

import dice.*;

public class TestMockStatsProcessor {

    private Database db = Database.create("mock");
    private StatsProcessor proc = StatsProcessor.create("mock", db);

    @Test
    public void getPlayerList() {
        String[] expected = {"ABC", "DEF", "GHI"};
        String[] observed = proc.getPlayerList();
        assertThat(expected, equalTo(observed));
    }

    @Test
    public void getPlayerStatsABC() {
        StatsData stats = proc.getPlayerStats("ABC");
        assertThat(stats.getTotalRolls(), equalTo(100));
        assertThat(stats.getAvgRollsPerGame(), equalTo(4.3));
        assertThat(stats.getCumulativeScore(), equalTo(2000));
        assertThat(stats.getAvgScore(), equalTo(18.2));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(2.1));
    }

    @Test
    public void getPlayerStatsDEF() {
        StatsData stats = proc.getPlayerStats("DEF");
        assertThat(stats.getTotalRolls(), equalTo(200));
        assertThat(stats.getAvgRollsPerGame(), equalTo(2.2));
        assertThat(stats.getCumulativeScore(), equalTo(4000));
        assertThat(stats.getAvgScore(), equalTo(19.0));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(1.8));
    }

    @Test
    public void getPlayerStatsGHI() {
        StatsData stats = proc.getPlayerStats("GHI");
        assertThat(stats.getTotalRolls(), equalTo(300));
        assertThat(stats.getAvgRollsPerGame(), equalTo(4.0));
        assertThat(stats.getCumulativeScore(), equalTo(5000));
        assertThat(stats.getAvgScore(), equalTo(22.1));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(1.2));
    }

    @Test
    public void getAllStats() {
        StatsData stats = proc.getAllStats();
        assertThat(stats.getTotalRolls(), equalTo(1001));
        assertThat(stats.getAvgRollsPerGame(), equalTo(3.4));
        assertThat(stats.getCumulativeScore(), equalTo(12000));
        assertThat(stats.getAvgScore(), equalTo(17.2));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(1.4));
    }

    @Test
    public void getLeaderboard() {
        LeaderboardEntry[] entries = proc.getLeaderboard();
        assertThat(entries.length, equalTo(3));
        assertThat(entries[0].getPlayerName(), equalTo("TAP"));
        assertThat(entries[1].getPlayerName(), equalTo("CCS"));
        assertThat(entries[2].getPlayerName(), equalTo("AAA"));
        assertThat(entries[0].getHighestScore(), equalTo(46));
        assertThat(entries[1].getHighestScore(), equalTo(22));
        assertThat(entries[2].getHighestScore(), equalTo(20));
    }
}
