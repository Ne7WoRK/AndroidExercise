package com.example.ivaylogeorgiev.androidexercise.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ivaylogeorgiev.androidexercise.R;

public class MainActivity extends AppCompatActivity {

    // Defining buttons of the main activity.
    Button btnExercises,btnSettings,btnCalendar;
    ImageView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get main activity buttons id's from layout.
        btnExercises = (Button)findViewById(R.id.btnExercises);
        btnSettings = (Button)findViewById(R.id.btnSetting);
        btnCalendar = (Button)findViewById(R.id.btnCalendar);
        start = (ImageView) findViewById(R.id.startDaily);

        // Using intent to start DailyExercise activity on click of image button play (start).
        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,DailyTrainingActivity.class);
                startActivity(intent);
            }
        });

        // Using intent to go to Calendar activity on click of button calendar.
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });

        // Using intent to go to Settings activity on click of button settings.
        btnSettings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);

            }

        });

        // Using intent to go to ListExercises activity on click of button exercises.
        btnExercises.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ExerciseListActivity.class);
                startActivity(intent);

            }

        });

    }
}
