package cs160.crunchtime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Spinner;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    // Initialize mapping of exercises to time/reps needed to burn 100 calories
    public Map<String, Double> unitFor100Calories = new HashMap<String, Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // set up our hash map
        initializeCalorieHashMap();
    }

    // Initialize our HashMap of exercises to units for 100 calories
    public void initializeCalorieHashMap() {
        unitFor100Calories.put("Situps", 200.0);
        unitFor100Calories.put("Pushups", 350.0);
        unitFor100Calories.put("Jumping Jacks", 10.0);
        unitFor100Calories.put("Jogging", 12.0);
    }

    // Call when the user clicks the burn calories button
    public void onClickConvertToCalories(View view) {
    System.out.println("BUTTON WAS CLICKED!");
    TextView exercises = (TextView) findViewById(R.id.exercises);
    exercises.setText("Let's burn some calories!");
    Spinner exerciseSpinner = (Spinner) findViewById(R.id.exercise_spinner);
    String selectedExercise = String.valueOf(exerciseSpinner.getSelectedItem());
    System.out.println("The item we have selected is: " + selectedExercise);

    double caloriesBurned = getCalories(selectedExercise, 10, "reps");
    exercises.setText("Calories Burned: " + String.valueOf(caloriesBurned));
    }

    // return the calculated calories burned from the specified exercise and duration
    double getCalories(String exercise, int duration, String repsOrMinutes) {
        double caloriesBurned = 0.0;
        double conversionFactorFor100Calories = unitFor100Calories.get(exercise);
        caloriesBurned = (duration / conversionFactorFor100Calories) * 100;
        return round(caloriesBurned);
    }

    // round a Double to two decimal places
    double round(double number) {
        double rounded = number;
        rounded = rounded * 100;
        rounded = Math.round(rounded);
        rounded = rounded / 100;
        return rounded;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
