package in.ktechnos.ebillcalculator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class MeterModal {

    @PrimaryKey(autoGenerate = true)

    // variable for our id.
    private int id;

    // below line is a variable
    // for course name.
    private String currentReadings;

    private String serviceNumber;


    public MeterModal(String serviceNumber,String currentReadings) {
        this.serviceNumber = serviceNumber;
        this.currentReadings = currentReadings;


    }

    public String getCurrentReadings() {
        return currentReadings;
    }

    public void setCurrentReadings(String currentReadings) {
        this.currentReadings = currentReadings;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
