package com.example.basketballscorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    //declaring variables
    private int team1Score = 0;
    private int team2Score = 0;
    Button removeButton, addButton, resetButton;
    TextView team1ScoreTV, team2ScoreTV;
    RadioGroup numPtsRG;
    int numPts = 2;
    Switch teamSwitch;
    SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //preferences.edit().clear().commit(); //this is used in case it's saving a setting that's breaking the app

        //references to views
        addButton  = findViewById(R.id.addbutton);
        removeButton = findViewById(R.id.removebutton);
        resetButton = findViewById(R.id.resetbutton);
        team1ScoreTV = findViewById(R.id.Team1Score);
        team2ScoreTV = findViewById(R.id.Team2Score);
        teamSwitch = findViewById(R.id.scoreSwitch);
        numPtsRG = findViewById(R.id.scoreChoiceRadioGroup);

        //click listeners
        addButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        numPtsRG.setOnCheckedChangeListener(this);
    }

    /** This is runs when the program is started and it retrieves my values */
    public void onStart()
    {
        super.onStart();
        team1Score = preferences.getInt("Team1Score", 0);
        team1ScoreTV.setText(Integer.toString(team1Score));
        team2Score = preferences.getInt("Team2Score", 0);
        team2ScoreTV.setText(Integer.toString(team2Score));
        int numPts = preferences.getInt("NumPts", 2);

        if (numPts == 1)
        {
            numPtsRG.check(R.id._1pointOption);
        }
        else if (numPts == 2)
        {
            numPtsRG.check(R.id._2pointOption);
        }
        else if (numPts == 3)
        {
            numPtsRG.check(R.id._3pointOption);
        }
        else
        {
            numPtsRG.check(R.id._2pointOption);
        }
        updateScreen();
    }
    /** This is runs when the program is stopped and it saves my values */
    public void onStop(){
        SharedPreferences.Editor editor = preferences.edit();
        boolean saveValues = preferences.getBoolean("save_values_preferences", false);
        if (saveValues)
        {
            editor.putInt("Team1Score", team1Score);
            editor.putInt("Team2Score", team2Score);
            editor.putInt("NumPts", numPts);
        }
        else
        {
            editor.clear();
            editor.putBoolean("save_values_preferences", false);
        }
        //editor.commit();
        editor.apply();
        super.onStop();
    }

    /** This creates the menu for the buttons to be added*/
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    /** This is my method to read from the activity bar when a button is clicked*/
    public boolean onOptionsItemSelected(MenuItem item){
        long id = item.getItemId();
        if (id == R.id.about_menu)
        {
            Toast.makeText(this,
                    "Developer: Daniel Faubert \nStudent Number: A00124623\n" +
                            "Updated: April 14th, 2022",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.settings_menu)
        {
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    //method that activates anytime something is clicked
    /** This is my method to register clicks for everything but the radio buttons */
    public void onClick(View view) {
        // variables only necessary within this method
        boolean switchState = teamSwitch.isChecked();

        //checks which option is selected in the radiobutton group
        // must be done before pts are allocated


        //checks the state of the switch to decide where to allocate the new pts
        //must be done before the pts are allocated
        if (switchState == true)
        {
            if (view.getId() == R.id.addbutton)
            {
                //decided not to cap the number of points that could be added
                team2Score = team2Score + numPts;
            }
            else if (view.getId() == R.id.removebutton)
            {
                team2Score = team2Score - numPts;
                // score can't be less than 0
                if (team2Score < 0)
                {
                    team2Score = 0;
                }
            }
        }
        else
        {
            if (view.getId() == R.id.addbutton)
            {
                team1Score = team1Score + numPts;
            }
            else if (view.getId() == R.id.removebutton)
            {
                team1Score = team1Score - numPts;
                if (team1Score < 0)
                {
                    team1Score = 0;
                }
            }
        }

        // created a reset button so the app can be reset and reused as needed
        if (view.getId() == R.id.resetbutton)
        {
            team1Score = 0;
            team2Score = 0;
        }
        // calls the update method after each onClick event, that way the screen updates
        // whenever anything changes
        updateScreen();
    }
    /** This is my method to update the screen when anything is changed */
    private void updateScreen(){
        team1ScoreTV.setText(Integer.toString(team1Score));
        team2ScoreTV.setText(Integer.toString(team2Score));
    }

    /** This is my method to update the number of pts variable when a radio button is clicked*/
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        if (id == R.id._1pointOption){
            numPts = 1;
        }
        else if (id == R.id._2pointOption){
            numPts = 2;
        }
        else if(id == R.id._3pointOption){
            numPts = 3;
        }
        else
        {
            numPts = 2;
        }
        updateScreen();
    }
}