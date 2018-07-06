package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
EditText etWeight;
EditText etHeight;
Button btnCalc;
Button btnReset;
TextView tvDate;
TextView tvBMI;
TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight=findViewById(R.id.etWeight);
        etHeight=findViewById(R.id.etHeight);
        btnCalc=findViewById(R.id.btnCalc);
        btnReset=findViewById(R.id.btnReset);
        tvDate=findViewById(R.id.tvDate);
        tvBMI=findViewById(R.id.tvBMI);
        tvResult=findViewById(R.id.tvResult);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double weight = Double.parseDouble(etWeight.getText().toString());
                double height = Double.parseDouble(etHeight.getText().toString());


                {
                    if (weight < 2.5) {
                        Toast.makeText(MainActivity.this, "Please enter Valid input, Minimum weight is 2.5Kg", Toast.LENGTH_SHORT).show();
                    } else if (weight > 300) {
                        Toast.makeText(MainActivity.this, "Please enter Valid input,Maximum weight is 300Kg", Toast.LENGTH_SHORT).show();
                    } else if (height < 0.1) {
                        Toast.makeText(MainActivity.this, "Please enter Valid input, Minimum height is 0.1 metre", Toast.LENGTH_SHORT).show();
                    } else if (height > 2.5) {
                        Toast.makeText(MainActivity.this, "Please enter Valid input, Maximum height is 2.5 metre", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                }
                double BMI = (weight / (height * height));
                if (BMI < 18.5) {
                    tvResult.setText("You are underweight");
                } else if (BMI <= 24.9) {
                    tvResult.setText("Your BMI is normal");
                } else if (BMI <= 29.9) {
                    tvResult.setText("You are overweight");
                } else {

                    tvResult.setText("You are obese");
                }
                tvBMI.setText("Last calculated BMI is :" + BMI);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText("Last calculated date is :" + datetime);
            }


        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        String strWeight = etWeight.getText().toString();
        String strHeight = etHeight.getText().toString();
        String BMI = tvBMI.getText().toString();
        String date = tvDate.getText().toString();
        String result = tvResult.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); // Step 1: Get the Default SharedPreferences
        SharedPreferences.Editor prefEdit = prefs.edit();  // Step 2: Get the editor
        prefEdit.putString("weight",strWeight); // Step 3: Save the values Add key-vallue pair
        prefEdit.putString("height",strHeight);
        prefEdit.putString("bmi",BMI);
        prefEdit.putString("date",date);
        prefEdit.putString("result",result);
        prefEdit.commit(); //Step 4:save changes into shared preferences
    }
   // Retrieving from shared preference
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1: Get the Default SharedPreferences
        String strWeight = prefs.getString("weight","");
        String strHeight = prefs.getString("height","");
        String BMI = prefs.getString("bmi","");
        String date = prefs.getString("date","");
        String result = prefs.getString("result","");

        etWeight.setText(strWeight);
        etHeight.setText(strHeight);
        tvResult.setText(result);
        tvBMI.setText(BMI);
        tvDate.setText(date);

        // Step 3: Update UI element accordingly
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit(); //for update later
                prefEdit.clear();
                prefEdit.commit();
                etHeight.setText("");
                etWeight.setText("");
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last calculated BMI:");
                tvResult.setText("");
            }
        });

    }
}
