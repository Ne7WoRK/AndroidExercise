package com.example.ivaylogeorgiev.androidexercise.Utils;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivaylogeorgiev.androidexercise.Database.ExerciseDB;
import com.example.ivaylogeorgiev.androidexercise.R;


public class ViewExercise extends AppCompatActivity {

    //Initialise elements of the View exercise activity.
    int image_id;
    String name;

    TextView title;
    TextView timer;
    ImageView detail_image;

    Button btnStart;
    Boolean isRunning = false;

    //Database instance.
    ExerciseDB exerciseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        //Create an instance of the SQLite database.
        exerciseDB = new ExerciseDB(this);

        //Get the timer,title and detail image instances from xml layout.
        timer = (TextView)findViewById(R.id.timer);
        title = (TextView)findViewById(R.id.title);
        detail_image = (ImageView) findViewById(R.id.detail_image);

        //Get the start button instance from xml layout.
        btnStart = (Button)findViewById(R.id.btnStart);

        //On click of button start
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isRunning)
                {
                    btnStart.setText("DONE");

                    //Initialise difficulty level integer
                    int timeLimit = 0;

                    //Check the stored level difficulty in database and update timeLimit integer accordingly.
                    if(exerciseDB.getDatabaseDifficulty() == 0)
                        timeLimit = DifficultyLevels.TIME_LIMIT_EASY;

                    else if(exerciseDB.getDatabaseDifficulty() == 1)
                        timeLimit = DifficultyLevels.TIME_LIMIT_MEDIUM;

                    else if(exerciseDB.getDatabaseDifficulty() == 2)
                        timeLimit = DifficultyLevels.TIME_LIMIT_HARD;

                    //Start a countdown timer based on the retrieved timeLimit integer in millis.
                    new CountDownTimer(timeLimit,1000){

                        @Override
                        public void onTick(long l) {
                            timer.setText(""+l/1000);

                        }

                        //On finish promp the user a message the exercise has finished.
                        @Override
                        public void onFinish() {

                            Toast.makeText(ViewExercise.this,"Finish !!!",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }.start();
                }
                else
                {
                    Toast.makeText(ViewExercise.this,"Finish !!!",Toast.LENGTH_SHORT).show();
                    finish();
                }

                isRunning = !isRunning;
            }
        });

        timer.setText("");

        // If this intent has been started by another activity.
        if(getIntent() != null)

            {
                // Get image id and name id from the activity that started this intent.
                image_id = getIntent().getIntExtra("image_id", -1);
                name = getIntent().getStringExtra("name");

                //Set image and name of exercise.
                detail_image.setImageResource(image_id);
                title.setText(name);

            }

    }
}
