
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;
import java.io.File;

import dice.*;


public class TestDatabase {
    String dbFilename = "src/test/resources/testDatabase.csv";
    Database db = Database.create("concrete", dbFilename);

    //@Test
    //public void getAllRecordsEmptyFileDoesNotExist() {
    //    String dbFilename = "ichiba.csv";
    //    Database db = Database.create("concrete", dbFilename);
    //    RollRecord[] records = db.getAllRecords();
    //}

    @Test
    public void getAllRecords() {
        RollRecord[] records = db.getAllRecords();

        assertThat(records.length, equalTo(6));

        assertThat(records[0], equalTo(new RollRecord("TAP", 0, 3, 10, 10)));
        assertThat(records[1], equalTo(new RollRecord("TAP", 0, 3, 10, 20)));
        assertThat(records[2], equalTo(new RollRecord("TAP", 1, 3, 12, 12)));
        assertThat(records[3], equalTo(new RollRecord("TAP", 1, 3, 6, 18)));
        assertThat(records[4], equalTo(new RollRecord("TAP", 1, 1, 6, 0)));
        assertThat(records[5], equalTo(new RollRecord("CHS", 1, 1, 6, 0)));
    }

    @Test
    public void getRecordsForUserTAP() {
        RollRecord[] records = db.getRecordsForUser("TAP");

        assertThat(records.length, equalTo(5));

        assertThat(records[0], equalTo(new RollRecord("TAP", 0, 3, 10, 10)));
        assertThat(records[1], equalTo(new RollRecord("TAP", 0, 3, 10, 20)));
        assertThat(records[2], equalTo(new RollRecord("TAP", 1, 3, 12, 12)));
        assertThat(records[3], equalTo(new RollRecord("TAP", 1, 3, 6, 18)));
        assertThat(records[4], equalTo(new RollRecord("TAP", 1, 1, 6, 0)));
    }

    @Test
    public void getRecordsForUserCHS() {
        RollRecord[] records = db.getRecordsForUser("CHS");

        assertThat(records.length, equalTo(1));

        assertThat(records[0], equalTo(new RollRecord("CHS", 1, 1, 6, 0)));
    }

    @Test
    public void addRollSingle() {
        String path = "src/test/resources/tempTestDb.csv";
        File f = new File(path);
        f.delete();

        Database db = Database.create("concrete", path);
        RollRecord roll = new RollRecord("CHS", 1, 1, 6, 0);
        db.addRoll(roll);

        RollRecord[] records = db.getAllRecords();
        assertThat(records.length, equalTo(1));
        assertThat(records[0], equalTo(new RollRecord("CHS", 1, 1, 6, 0)));
    }

    @Test
    public void addRollMultiple() {
        String path = "src/test/resources/tempTestDb.csv";
        File f = new File(path);
        f.delete();

        Database db = Database.create("concrete", path);
        RollRecord roll = new RollRecord("CHS", 1, 1, 6, 6);
        db.addRoll(roll);
        roll = new RollRecord("CHS", 1, 1, 3, 9);
        db.addRoll(roll);
        roll = new RollRecord("CHS", 1, 3, 7, 16);
        db.addRoll(roll);
        roll = new RollRecord("TAP", 2, 3, 12, 12);
        db.addRoll(roll);

        RollRecord[] records = db.getAllRecords();
        assertThat(records.length, equalTo(4));
        assertThat(records[0], equalTo(new RollRecord("CHS", 1, 1, 6, 6)));
        assertThat(records[1], equalTo(new RollRecord("CHS", 1, 1, 3, 9)));
        assertThat(records[2], equalTo(new RollRecord("CHS", 1, 3, 7, 16)));
        assertThat(records[3], equalTo(new RollRecord("TAP", 2, 3, 12, 12)));
    }
}
