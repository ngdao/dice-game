import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;

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

        assertThat(stats.getTotalRolls(), equalTo(4));
        assertThat(stats.getAvgRollsPerGame(), equalTo(2.0));
        assertThat(stats.getCumulativeScore(), equalTo(40));
        assertThat(stats.getAvgScore(), equalTo(20.0));
        assertThat(stats.getAvgNumDiceUsed(), equalTo(2.0));
    }
}
