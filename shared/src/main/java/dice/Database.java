package dice;


public abstract class Database {
    public static Database create(String type) {
        if (type.equals("mock")) {
            return new MockDatabase();
        }
        else {
            return new ConcreteDatabase();
        }
    }

    public abstract RollRecord[] getAllRecords();
    public abstract RollRecord[] getRecordsForUser(String userId);
}

class MockDatabase extends Database {
    public RollRecord[] getAllRecords() {

        RollRecord recordA = new RollRecord("TAP", 0, 3, 13, 13);
        RollRecord recordB = new RollRecord("TAP", 0, 1, 3, 16);
        RollRecord recordC = new RollRecord("TAP", 0, 1, 6, 22);
        RollRecord recordD = new RollRecord("CHS", 0, 3, 18, 18);

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
            RollRecord recordD = new RollRecord("CHS", 0, 3, 18, 18);

            RollRecord[] array = {recordD};
            return array;
        }

    }
}

class ConcreteDatabase extends Database {
    public RollRecord[] getAllRecords() {
        return new RollRecord[1];
    }

    public RollRecord[] getRecordsForUser(String userId) {
        return new RollRecord[1];
    }
}
