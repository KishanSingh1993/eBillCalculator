package in.ktechnos.ebillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HistoryTable extends AppCompatActivity {

    private RecyclerView coursesRV;
    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_table);

        coursesRV = findViewById(R.id.rvData);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting a new activity for adding a new course and passing a constant value in it.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //setting layout manager to our adapter class.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);
        //initializing adapter for recycler view.
        final CourseRVAdapter adapter = new CourseRVAdapter();
        //setting adapter class for recycler view.
        coursesRV.setAdapter(adapter);
        //passing a data from view modal.
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);
        //below line is use to get all the courses from view modal.
        viewmodal.getAllCourses().observe(this, new Observer<List<MeterModal>>() {
            @Override
            public void onChanged(List<MeterModal> models) {
                //when the data is changed in our models we are adding that list to our adapter class.
                adapter.submitList(models);
            }
        });
    }
}