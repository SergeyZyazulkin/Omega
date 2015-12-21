package pvms.omega.model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Leaderboard implements Serializable {

    private static final int RECORDS_COUNT = 20;

    private ArrayList<Record> easyRecords;
    private ArrayList<Record> normalRecords;
    private ArrayList<Record> hardRecords;

    public Leaderboard() {
        easyRecords = new ArrayList<>();
        normalRecords = new ArrayList<>();
        hardRecords = new ArrayList<>();

        for (int i = 0; i < RECORDS_COUNT; ++i) {
            easyRecords.add(new Record(i + 1, "---------------", -1));
            normalRecords.add(new Record(i + 1, "---------------", -1));
            hardRecords.add(new Record(i + 1, "---------------", -1));
        }
    }

    public Leaderboard(ArrayList<Record> easyRecords,
                       ArrayList<Record> normalRecords, ArrayList<Record> hardRecords) {
        this.easyRecords = easyRecords;
        this.normalRecords = normalRecords;
        this.hardRecords = hardRecords;
    }

    public ArrayList<Record> getEasyRecords() {
        return easyRecords;
    }

    public void setEasyRecords(ArrayList<Record> easyRecords) {
        this.easyRecords = easyRecords;
    }

    public ArrayList<Record> getNormalRecords() {
        return normalRecords;
    }

    public void setNormalRecords(ArrayList<Record> normalRecords) {
        this.normalRecords = normalRecords;
    }

    public ArrayList<Record> getHardRecords() {
        return hardRecords;
    }

    public void setHardRecords(ArrayList<Record> hardRecords) {
        this.hardRecords = hardRecords;
    }

    public void addEasyRecord(String name, int score) {
        for (int i = 0; i < RECORDS_COUNT; ++i) {
            if (easyRecords.get(i).getScore() <= score) {
                easyRecords.remove(RECORDS_COUNT - 1);
                easyRecords.add(i, new Record(i, name, score));
                return;
            }
        }
    }

    public void addNormalRecord(String name, int score) {
        for (int i = 0; i < RECORDS_COUNT; ++i) {
            if (normalRecords.get(i).getScore() <= score) {
                normalRecords.remove(RECORDS_COUNT - 1);
                normalRecords.add(i, new Record(i, name, score));
                return;
            }
        }
    }

    public void addHardRecord(String name, int score) {
        for (int i = 0; i < RECORDS_COUNT; ++i) {
            if (hardRecords.get(i).getScore() <= score) {
                hardRecords.remove(RECORDS_COUNT - 1);
                hardRecords.add(i, new Record(i, name, score));
                return;
            }
        }
    }

    public void save(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("records.rec", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (IOException ignored) {}
    }

    public static Leaderboard load(Context context) {
        try {
            FileInputStream fis = context.openFileInput("records.rec");
            ObjectInputStream is = new ObjectInputStream(fis);
            Leaderboard leaderboard = (Leaderboard) is.readObject();
            is.close();
            fis.close();
            return leaderboard;
        } catch (IOException | ClassNotFoundException e) {
            return new Leaderboard();
        }
    }
}
