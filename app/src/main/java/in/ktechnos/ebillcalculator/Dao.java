package in.ktechnos.ebillcalculator;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    // below method is use to
    // add data to database.
    @Insert
    void insert(MeterModal model);

    // below method is use to update
    // the data in our database.
    @Update
    void update(MeterModal model);

    // below line is use to delete a
    // specific course in our database.
    @Delete
    void delete(MeterModal model);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    // below line is to read all the courses from our database.
    // in this we are ordering our courses in ascending order
    // with our course name.
    @Query("SELECT * FROM course_table ORDER BY currentReadings ASC")
    LiveData<List<MeterModal>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE id = :id")
    MeterModal findByUserId(String id);
}
