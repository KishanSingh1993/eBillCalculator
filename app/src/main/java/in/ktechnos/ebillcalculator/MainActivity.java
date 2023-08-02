package in.ktechnos.ebillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import in.ktechnos.ebillcalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ViewModal viewmodal;
    private int lastMeterReading,currentMeterReading;
    public static final String EXTRA_ID = "in.ktechnos.ebillcalculator.EXTRA_ID";
    public static final String SERVICE_NUMBER = "in.ktechnos.ebillcalculator.SERVICE_NUMBER";
    public static final String CURRENT_READING = "in.ktechnos.ebillcalculator.CURRENT_READING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btSave.setVisibility(View.GONE);
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        viewmodal.getAllCourses().observe(this, new Observer<List<MeterModal>>() {
            @Override
            public void onChanged(List<MeterModal> models) {
                //when the data is changed in our models we are adding that list to our adapter class.
                Log.d("Last Data",models.size()+"");
                if(models.size()!=0){
                    int getIndex = models.size() -1;
                    binding.etPreviousReading.setText(models.get(getIndex).getCurrentReadings());
                    lastMeterReading = Integer.parseInt(models.get(getIndex).getCurrentReadings());
                }
                else {

                    binding.etPreviousReading.setText("0");
                }

            }
        });

        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {

                String currentText=binding.etCurrentReading.getText().toString().trim();
                String previousText=binding.etPreviousReading.getText().toString().trim();
                String serviceText=binding.serviceNumber.getText().toString().trim();

                currentMeterReading = Integer.parseInt(binding.etCurrentReading.getText().toString());

                if(!currentText.equals("") && !previousText.equals("") && !serviceText.equals(""))
                {

                    if(!(serviceText.length() == 10)){

                        binding.serviceNumber.setError("Please Enter A Valid Service Number");
                    }
                    else {

                        if (currentMeterReading<lastMeterReading) {

                            binding.etCurrentReading.setError("Please Enter A Valid Data");

                        }
                        else {

                            int finalReading = Integer.parseInt(binding.etCurrentReading.getText().toString()) - Integer.parseInt(binding.etPreviousReading.getText().toString());

                            calculateBill(finalReading);

                            binding.finalReport.setText(String.format("Your Electricity Bill For This Month Is:- $%d ", calculateBill(finalReading)));

                            binding.btSave.setVisibility(View.VISIBLE);
                        }

                    }

                }
                else
                {
                    binding.etCurrentReading.setError("Please Enter Data");
                    binding.etPreviousReading.setError("Please Enter Data");
                    binding.serviceNumber.setError("Please Enter Data");

                }

            }
        });

        binding.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse(binding.serviceNumber.getText().toString(),binding.etCurrentReading.getText().toString());
                MeterModal model = new MeterModal(binding.serviceNumber.getText().toString(),binding.etCurrentReading.getText().toString());
                viewmodal.insert(model);
                Intent intent = new Intent(getApplicationContext(), HistoryTable.class);
                startActivity(intent);

                binding.btSave.setVisibility(View.GONE);
            }
        });

    }

    public static int calculateBill(int units)
    {

        if (units <= 100) {

            Log.d("Bill",units*10+"");

            return units * 5;
        }
        else if (units <= 500) {

            int billData = (100 * 10)
                    + (units - 100)
                    * 15;
            Log.d("Bill",billData+"");

            return (100 * 5)
                    + (units - 100)
                    * 8;
        }

        else {

            int billData = (100 * 5)
                    + (400 * 8)
                    + (units - 500)
                    * 10;
            Log.d("Bill",billData+"");

            return billData;
        }
    }

    private void saveCourse(String courseName, String courseDescription) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our course detail.
        data.putExtra(SERVICE_NUMBER, courseName);
        data.putExtra(CURRENT_READING, courseDescription);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data
        Toast.makeText(this, "Data has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}