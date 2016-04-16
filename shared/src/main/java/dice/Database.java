package dice;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public abstract class Database {
    public static Database create(String type, String filename) {
        if (type.equals("mock")) {
            return new MockDatabase();
        }
        else {
            return new ConcreteDatabase(filename);
        }
    }

    public abstract RollRecord[] getAllRecords();
    public abstract RollRecord[] getRecordsForUser(String userId);
    public abstract void addRoll(RollRecord roll);
}

class MockDatabase extends Database {
    public RollRecord[] getAllRecords() {

        RollRecord recordA = new RollRecord("TAP", 0, 3, 13, 13);
        RollRecord recordB = new RollRecord("TAP", 0, 1, 3, 16);
        RollRecord recordC = new RollRecord("TAP", 0, 1, 6, 22);
        RollRecord recordD = new RollRecord("CHS", 1, 3, 18, 18);

        RollRecord[] array = {recordA, recordB, recordC, recordD};

        return array;
    }

    public RollRecord[] getRecordsForUser(String userId) {

        if (userId.equals("TAP")) {
            RollRecord recordA = new RollRecord("TAP", 0, 3, 13, 13);
            RollRecord recordB = new RollRecord("TAP", 0, 1, 3, 16);
            RollRecord recordC = new RollRecord("TAP", 0, 1, 6, 22);

            RollRecord[] array = {recordA, recordB, recordC};
            return array;
        }
        else {
            RollRecord recordD = new RollRecord("CHS", 1, 3, 18, 18);

            RollRecord[] array = {recordD};
            return array;
        }

    }

    public void addRoll(RollRecord roll) {
    }
}

class ConcreteDatabase extends Database {
    private final String filename;

    public ConcreteDatabase(String filename) {
        this.filename = filename;
    }

    public RollRecord[] getAllRecords() {

        List<RollRecord> records = new ArrayList<RollRecord>();

        CSVReader reader = getReader();

        Iterator<String[]> iter = reader.iterator();

        while (iter.hasNext()) {
            String[] line = iter.next();
            String userId = line[0];
            int gameId = Integer.parseInt(line[1]);
            int numDice = Integer.parseInt(line[2]);
            int rollValue = Integer.parseInt(line[3]);
            int score = Integer.parseInt(line[4]);
            RollRecord record = new RollRecord(userId, gameId, numDice,
                                               rollValue, score);
            records.add(record);
        }

        return records.toArray(new RollRecord[records.size()]);
    }

    public RollRecord[] getRecordsForUser(String compUserId) {

        List<RollRecord> records = new ArrayList<RollRecord>();

        CSVReader reader = getReader();

        Iterator<String[]> iter = reader.iterator();

        while (iter.hasNext()) {
            String[] line = iter.next();

            String userId = line[0];

            if (userId.equals(compUserId)) {
                int gameId = Integer.parseInt(line[1]);
                int numDice = Integer.parseInt(line[2]);
                int rollValue = Integer.parseInt(line[3]);
                int score = Integer.parseInt(line[4]);
                RollRecord record = new RollRecord(userId, gameId, numDice,
                                                   rollValue, score);
                records.add(record);
            }
        }

        return records.toArray(new RollRecord[records.size()]);
    }

    public void addRoll(RollRecord roll) {
        CSVWriter writer = getWriter();
        String[] line = {
            roll.getUserId(),
            Integer.toString(roll.getGameId()),
            Integer.toString(roll.getNumDice()),
            Integer.toString(roll.getRollValue()),
            Integer.toString(roll.getScore())
        };

        writer.writeNext(line);
        try {
            writer.close();
        }
        catch (IOException e) {
        }
    }

    private CSVReader getReader() {
        // ensure file exists
        try {
            File f = new File(filename);
            f.createNewFile();
        }
        catch (IOException e) {
            System.out.println("Error creating file: " + filename);
        }

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filename);
        }
        catch(IOException e) {
            // TODO: handle properly
            System.out.println("Error opening file");
        }

        return new CSVReader(fileReader);
    }

    private CSVWriter getWriter() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename, true);
        }
        catch(IOException e) {
            // TODO: handle properly
            System.out.println("Error opening file");
        }

        return new CSVWriter(fileWriter);
    }

}
