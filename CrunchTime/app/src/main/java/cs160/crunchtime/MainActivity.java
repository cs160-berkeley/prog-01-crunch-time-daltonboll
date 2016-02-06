package cs160.crunchtime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import java.util.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    // Initialize mapping of exercises to the number of reps/minutes needed to burn 100 calories
    public Map<String, Double> unitFor100Calories = new HashMap<String, Double>();
    // Initialize mapping of exercises to "reps" or "minutes"
    public Map<String, String> exerciseToDuration = new HashMap<String, String>();

    // Static strings
    public String REPS = "reps";
    public String MINUTES = "minutes";

    // Spinner drop down
    Spinner exerciseSpinner;


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

        // Set up the spinner listener for when a new exercise has been selected
        exerciseSpinner = (Spinner) findViewById(R.id.exerciseSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.exercises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);

        exerciseSpinner.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        onSelectExercise();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        // nothing
                    }
                });

        // set up our hash map
        initializeHashMaps();
    }

    // Initialize our exercise HashMaps
    public void initializeHashMaps() {
        String reps = "reps";

        unitFor100Calories.put("Situps", 200.0);
        unitFor100Calories.put("Pushups", 350.0);
        unitFor100Calories.put("Jumping Jacks", 10.0);
        unitFor100Calories.put("Jogging", 12.0);

        exerciseToDuration.put("Situps", REPS);
        exerciseToDuration.put("Pushups", REPS);
        exerciseToDuration.put("Jumping Jacks", MINUTES);
        exerciseToDuration.put("Jogging", MINUTES);
    }

    // Call when the user clicks the burn calories button
    public void onClickConvertToCalories(View view) {
    TextView calorieNotification = (TextView) findViewById(R.id.caloriesBurned);
    Spinner exerciseSpinner = (Spinner) findViewById(R.id.exerciseSpinner);
        String exercise = String.valueOf(exerciseSpinner.getSelectedItem());

    EditText durationEntered = (EditText) findViewById(R.id.duration);
        int duration = Integer.parseInt(durationEntered.getText().toString());

    double caloriesBurned = getCalories(exercise, duration);
    calorieNotification.setText("Calories Burned: " + String.valueOf(caloriesBurned));
    }

    // Call when the user selects a new exercise from the Spinner. Updates the text
    // displayed next to the enter duration field
    public void onSelectExercise() {
        Spinner exerciseSpinner = (Spinner) findViewById(R.id.exerciseSpinner);
        String exercise = String.valueOf(exerciseSpinner.getSelectedItem());
        System.out.println("SELECTED EXERCISE = " + exercise);
        String durationType = exerciseToDuration.get(exercise);

        TextView enterDurationText = (TextView) findViewById(R.id.enterDuration);
        String newText = enterDurationText.getText().toString();
        System.out.println("newText is equal to " + newText);

        if (durationType.equals(REPS)) {
            newText = newText.replace(MINUTES, REPS);
            System.out.println("newText is now: " + newText);
        } else {
            newText = newText.replace(REPS, MINUTES);
            System.out.println("newText is now: " + newText);
        }

        enterDurationText.setText(newText);
    }

    // return the calculated calories burned from the specified exercise and duration
    double getCalories(String exercise, int duration) {
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
