import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;
import java.io.File;
import dice.*;

public class TestDiceGame {

    @Test
    public void testRoll1Die() {
        for (int rollIdx = 0; rollIdx < 1000; rollIdx++) {

            DiceGame game = null;
            try {
                Database db = Database.create("mock", "dummyFilename");
                game = DiceGame.create("AAA", db);
            }
            catch (InvalidUsernameException e) {
            }

            try {
                RollResult roll = game.roll(1);
                assertTrue(roll.sum() >= 1 && roll.sum() <= 6);
            }
            catch (RollAfterGameOverException e) {
                fail();
            }
        }
    }

    @Test
    public void testRoll2Dice() {
        for (int rollIdx = 0; rollIdx < 1000; rollIdx++) {

            DiceGame game = null;
            try {
                Database db = Database.create("mock", "dummyFilename");
                game = DiceGame.create("AAA", db);
            }
            catch (InvalidUsernameException e) {
            }

            try {
                RollResult roll = game.roll(2);
                assertTrue(roll.sum() >= 2 && roll.sum() <= 12);
            }
            catch (RollAfterGameOverException e) {
                fail();
            }
        }
    }

    @Test
    public void testRoll3Dice() {
        for (int rollIdx = 0; rollIdx < 1000; rollIdx++) {

            DiceGame game = null;
            try {
                Database db = Database.create("mock", "dummyFilename");
                game = DiceGame.create("AAA", db);
            }
            catch(InvalidUsernameException e) {
            }

            try {
                RollResult roll = game.roll(3);
                assertTrue(roll.sum() >= 3 && roll.sum() <= 18);
            }
            catch (RollAfterGameOverException e) {
                fail();
            }
        }
    }

    @Test
    public void testGetCurrentTotal() {

        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AAA", db);
        }
        catch (InvalidUsernameException e) {
        }

        try {
            int total = game.roll(1).sum();
            total += game.roll(1).sum();
            total += game.roll(1).sum();

            int currentTotal = game.getCurrentTotal();
            assertEquals(total, currentTotal);
        }
        catch (RollAfterGameOverException e) {
            fail();
        }
    }

    @Test
    public void testGetScoreLessThan23() {

        DiceGame game = null;

        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AAA", db);
        }
        catch (InvalidUsernameException e) {
        }

        try {
            RollResult roll = game.roll(3);

            assertEquals(roll.sum(), game.getScore());
        }
        catch (RollAfterGameOverException e) {
            fail();
        }
    }

    @Test
    public void testGetScoreEqualTo23() {

        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AAA", db);
        }
        catch (InvalidUsernameException e) {
        }

        int total = 0;
        int index = 0;

        // hack. go until we randomly get a score of 23
        do {
            try {
                total += game.roll(1).sum();
            }
            catch (RollAfterGameOverException e) {
                fail();
            }

            if (total > 23) {
                try {
                    Database db = Database.create("mock", "dummyFilename");
                    game = DiceGame.create("AAA", db);
                }
                catch (InvalidUsernameException e) {
                }
                total = 0;
            }

            // failsafe
            index++;
            if (index > 10000) {
                fail();
            }

        } while (total != 23);

        assertEquals(46, game.getScore());
    }

    @Test
    public void testGetScoreOver23() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AAA", db);
        }
        catch (InvalidUsernameException e) {
        }

        try {
            int total = game.roll(3).sum();
            
            while (total < 23) {
                total += game.roll(3).sum();
            }

            assertThat(game.getScore(), anyOf(equalTo(0), equalTo(46)));
        }
        catch (RollAfterGameOverException e) {
            fail();
        }
    }
    
    @Test
    public void testZeroScore() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AAA", db);
        }
        catch (InvalidUsernameException e) {
        }

        assertEquals(0, game.getScore());
    }

    @Test
    public void usernameDefault() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AAA", db);
        }
        catch (InvalidUsernameException e) {
        }
        assertThat(game.getUsername(), equalTo("AAA"));
    }

    @Test
    public void usernameCustom() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("ABC", db);
        }
        catch (InvalidUsernameException e) {
            fail();
        }

        assertThat(game.getUsername(), equalTo("ABC"));
    }

    @Test
    public void usernameBlankRaisesException() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("", db);
            fail();
        }
        catch (InvalidUsernameException e) {
            assertThat(e.getMessage(), is("Blank username"));
        }
    }
    @Test
    public void usernameTooShortRaisesException() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("AB", db);
            fail();
        }
        catch (InvalidUsernameException e) {
            assertThat(e.getMessage(), is("Username 'AB' too short"));
        }
    }

    @Test
    public void usernameTooLongRaisesException() {
        DiceGame game = null;
        try {
            Database db = Database.create("mock", "dummyFilename");
            game = DiceGame.create("ABCD", db);
            fail();
        }
        catch (InvalidUsernameException e) {
            assertThat(e.getMessage(), is("Username 'ABCD' too long"));
        }
    }

    @Test
    public void rollSavesToDatabase() {
        String dbPath = "src/test/resources/tempTestDbDiceGame.csv";
        File f = new File(dbPath);
        f.delete();

        Database db = Database.create("concrete", dbPath);
        DiceGame game = null;
        try {
            game = DiceGame.create("TAP", db);
        }
        catch (InvalidUsernameException e) {
        }

        int rollValue = 0;
        try {
            rollValue = game.roll(3).sum();
        }
        catch (RollAfterGameOverException e) {
        }

        RollRecord[] records = db.getAllRecords();

        assertThat(records.length, equalTo(1));
        
        RollRecord rec = records[0];

        assertThat(rec.getUserId(), equalTo("TAP"));
        assertThat(rec.getGameId(), equalTo(0));
        assertTrue(rec.getRollValue() >= 3 && rec.getRollValue() <= 18);
        assertThat(rec.getNumDice(), equalTo(3));
        assertThat(rec.getScore(), equalTo(rollValue));
    }

    @Test
    public void gameIdIncrements() {
        String dbPath = "src/test/resources/tempTestDbDiceGame.csv";
        File f = new File(dbPath);
        f.delete();

        Database db = Database.create("concrete", dbPath);

        DiceGame gameA = null;
        DiceGame gameB = null;
        try {
            gameA = DiceGame.create("TAP", db);
            gameB = DiceGame.create("CHS", db);
        }
        catch (InvalidUsernameException e) {
        }

        try {
            gameA.roll(3);
            gameB.roll(3);
        }
        catch (RollAfterGameOverException e) {
        }

        RollRecord[] records = db.getAllRecords();

        assertThat(records.length, equalTo(2));
        assertThat(records[0].getGameId(), equalTo(0));
        assertThat(records[0].getUserId(), equalTo("TAP"));
        assertThat(records[1].getGameId(), equalTo(1));
        assertThat(records[1].getUserId(), equalTo("CHS"));
    }
}
