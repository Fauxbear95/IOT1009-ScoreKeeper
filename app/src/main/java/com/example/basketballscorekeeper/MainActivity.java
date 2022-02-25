package com.example.basketballscorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    //declaring variables
    private int team1Score = 0;
    private int team2Score = 0;
    Button removeButton, addButton, resetButton;
    TextView team1ScoreTV, team2ScoreTV;
    RadioGroup numPtsRG;
    int numPts;
    Switch teamSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    //method that activates anytime something is clicked
    @Override
    public void onClick(View view) {
        // variables only necessary within this method
        long checkedId = numPtsRG.getCheckedRadioButtonId();
        boolean switchState = teamSwitch.isChecked();

        //checks which option is selected in the radiobutton group
        // must be done before pts are allocated
        if (checkedId == R.id._1pointOption){
            numPts = 1;
        }
        else if (checkedId == R.id._2pointOption){
            numPts = 2;
        }
        else if(checkedId == R.id._3pointOption){
            numPts = 3;
        }
        else
        {
            numPts = 2;
        }

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
    private void updateScreen(){
        team1ScoreTV.setText(Integer.toString(team1Score));
        team2ScoreTV.setText(Integer.toString(team2Score));
    }
}