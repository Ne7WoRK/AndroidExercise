package com.example.ivaylogeorgiev.androidexercise.Activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.List;

import com.example.ivaylogeorgiev.androidexercise.Database.ExerciseDB;
import com.example.ivaylogeorgiev.androidexercise.Model.Exercise;
import com.example.ivaylogeorgiev.androidexercise.R;
import com.example.ivaylogeorgiev.androidexercise.Utils.DifficultyLevels;

import java.util.ArrayList;


public class DailyTrainingActivity extends AppCompatActivity {

    //Initialise the components of the daily training activity.
    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountdown, txtTimer, ex_name;
    LinearLayout layoutGetReady;

    /**
     * Integer value that is used to show the current exercise ID. On start of daily exercise session
     * this integer is incremented and its value is used to get the next exercise element from list of Exercise objects.
     *
     */
    int ex_id = 0;

    //List that stores objects of type Exercise.
    List<Exercise> list = new ArrayList<>();

    // Initialise database object
    ExerciseDB exerciseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);

        //Populate list of exercises with Exercise objects.
        exerciseData();

        // Instance of the local SQLite database.
        exerciseDB = new ExerciseDB(this);

        //Get start button instance from xml layout.
        btnStart = (Button) findViewById(R.id.btnStart);

        //Get exercise image instance from xml layout.
        ex_image = (ImageView)findViewById(R.id.detail_image);

        //Get additional exercise elements instances from xml layout
        txtCountdown = (TextView)findViewById(R.id.txtCountDown);
        txtGetReady = (TextView)findViewById(R.id.txtGetReady);
        txtTimer = (TextView)findViewById(R.id.timer);
        ex_name = (TextView)findViewById(R.id.title);

        layoutGetReady = (LinearLayout) findViewById(R.id.layout_get_ready);

        //Set exercise information from list of exercises according to exercise id (initially 0 element).
        setExerciseInformation(ex_id);

        //On click of button start
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //If pressed button text shows start display get ready countdown and change button text to done.
                if (btnStart.getText().toString().toLowerCase().equals("start")){

                    showGetReady();
                    btnStart.setText("done");
                }

                /**
                 * If pressed button text shows done check what is the saved level of difficulty in database
                 * and cancel timer.
                 */

                else if (btnStart.getText().toString().toLowerCase().equals("done")){

                    if(exerciseDB.getDatabaseDifficulty() == 0)
                        easyCountDownTimer.cancel();

                    else if(exerciseDB.getDatabaseDifficulty() == 1)
                        mediumCountDownTimer.cancel();

                    else if(exerciseDB.getDatabaseDifficulty() == 2)
                        hardCountDownTimer.cancel();

                    restTimeCountDown.cancel();

                    //If current exercise id is smaller than list size show the user a rest timer of 10 seconds
                    if (ex_id < list.size())
                    {
                        showRestTime();

                        //Increment exercise id on successful completion of exercise.
                        ex_id++;
                        txtTimer.setText("");
                    }
                    //Otherwise congratulate the user for finished workout.
                    else
                        showFinished();

                }

                /**
                 * If pressed button text shows "skip", check what is the saved level of difficulty in database
                 * and cancel timer.
                 */
                else if(btnStart.getText().toString().toLowerCase().equals("skip")) {
                    if(exerciseDB.getDatabaseDifficulty() == 0)
                        easyCountDownTimer.cancel();

                    else if(exerciseDB.getDatabaseDifficulty() == 1)
                        mediumCountDownTimer.cancel();

                    else if(exerciseDB.getDatabaseDifficulty() == 2)
                        hardCountDownTimer.cancel();

                    //Cancel rest timer.
                    restTimeCountDown.cancel();

                    /**
                     * If current exercise id is less than list size show next exercise information using
                     * setExerciseInformation void method.
                     */

                    if (ex_id < list.size()) {

                        setExerciseInformation(ex_id);

                    }
                    //Otherwise, congratulate the user for the finished training.
                    else{
                        showFinished();

                    }
                }

            }
        });
    }



    //This method will hide UI elements and populate the screen with a rest timer that will
    // be used to give a break of the users after exercise.
    private void showRestTime() {

        //Hide exercise image.
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);

        //Set button text to Skip (skip break).
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        //Launch the rest timer.
        restTimeCountDown.start();

        //Change get ready text to rest time.
        txtGetReady.setText("Rest time");

    }




    //This method is used to show a get ready message before exercise is launched.
    private void showGetReady() {

        //Hide image,button from UI.
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);

        //Show timer and get ready layout.
        txtTimer.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("Get ready");

        //Get ready count down 6 seconds.
        new CountDownTimer(6000,1000){

            //Display remaining seconds on tick of the count down timer.
            @Override
            public void onTick(long l) {

                txtCountdown.setText(""+(l-1000)/1000);
            }

            //On finish show next exercise by executing the void show exercise method.
            @Override
            public void onFinish() {

                showExercises();
            }
        }.start();
    }

    /**
     * This method will show the next exercise according to item id. If all exercises are done
     * congratulate the user on completing the workout.
     */
    private void showExercises() {

        if (ex_id < list.size()) {

            //Set visibility of image and button
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);

            //Hide get ready layout from interface.
            layoutGetReady.setVisibility(View.INVISIBLE);

            //Check database for level of difficulty stored by the user.
            if(exerciseDB.getDatabaseDifficulty() == 0)
                easyCountDownTimer.start();

            else if(exerciseDB.getDatabaseDifficulty() == 1)
                mediumCountDownTimer.start();

            else if(exerciseDB.getDatabaseDifficulty() == 2)
                hardCountDownTimer.start();

            //Set data for exercise according to current exercise id.
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());

        }
        //If all exercises performed congratulate the user on finished work out.
        else
            showFinished();
    }



    /**
     * This method shows a congratulation message on workout completion and saves a string (long)
     * representation of current day in database.
     */
    private void showFinished() {

        //Hide image,button and timer from layout.
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);

        //Show layout
        layoutGetReady.setVisibility(View.VISIBLE);

        //Modify layout get ready to show congratulation message.
        txtGetReady.setText("Finished !!!");
        txtCountdown.setText("Congratulations !\n You're done with today's training");
        txtCountdown.setTextSize(20);

        //Save string representation of long in database. This value will be used to determined which days user has performed training.
        exerciseDB.saveWorkoutDay(""+Calendar.getInstance().getTimeInMillis());
    }



    //The easy mode countdown timer.
    CountDownTimer easyCountDownTimer = new CountDownTimer(DifficultyLevels.TIME_LIMIT_EASY,1000) {

        //On tick show the remaining time to the user.
        public void onTick(long l) {

            txtTimer.setText(""+(l/1000));
        }

        // On finish of the timer show next exercise or congratulate the user on successful completion of the training.
        @Override
        public void onFinish() {

            if (ex_id < list.size()-1){

                //Increment exercise id.
                ex_id++;
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }

            //Otherwise congratulate the user for finished workout.
            else {
                showFinished();
            }

        }
    };




    //The medium mode countdown timer.
    CountDownTimer mediumCountDownTimer = new CountDownTimer(DifficultyLevels.TIME_LIMIT_MEDIUM,1000) {

        //On tick show the remaining time to the user.
        @Override
        public void onTick(long l) {

            txtTimer.setText(""+(l/1000));
        }

        // On finish of the timer show next exercise or congratulate the user on successful completion of the training.
        @Override
        public void onFinish() {

            if (ex_id < list.size()-1){

                //Increment exercise id.
                ex_id++;
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }

            //Otherwise congratulate the user for finished workout.
            else {

                showFinished();
            }

        }
    };



    //The hard mode countdown timer.
    CountDownTimer hardCountDownTimer = new CountDownTimer(DifficultyLevels.TIME_LIMIT_HARD,1000) {

        //On tick show the remaining time to the user.
        @Override
        public void onTick(long l) {

            txtTimer.setText(""+(l/1000));
        }

        // On finish of the timer show next exercise or congratulate the user on successful completion of the training.
        @Override
        public void onFinish() {

            if (ex_id < list.size()-1){

                ex_id++;
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }

            //Otherwise congratulate the user for finished workout.
            else {
                showFinished();
            }

        }
    };



    /**
     * This rest countdown timer, which is set to run for 10 seconds will be used
     * after each exercise, allowing users to have a break after an exercise.
     */

    CountDownTimer restTimeCountDown = new CountDownTimer(10000,1000) {
        // On tick show the time remaining.
        public void onTick(long l) {

            txtCountdown.setText(""+(l/1000));
        }

        //On finish show exercise information according to the exercise id integer value.
        public void onFinish() {

            setExerciseInformation(ex_id);
            showExercises();
        }
    };




    // This method shows exercise information according to the passed exercise ID and hides get ready layout.

    private void setExerciseInformation(int id) {

        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");

        ex_image.setVisibility(View.VISIBLE);
        ex_name.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.INVISIBLE);
    }



    /**
     * This method is used to populate the list of exercises with Exercise objects.
     * Each Exercise object is represented by gif image and string exercise name.
     */

    private void exerciseData() {

        list.add(new Exercise(R.drawable.pushup, "Push ups"));
        list.add(new Exercise(R.drawable.deadbug, "Dead bug"));
        list.add(new Exercise(R.drawable.sideplank, "Side plank"));
        list.add(new Exercise(R.drawable.bicepscurl, "Biceps curl"));
        list.add(new Exercise(R.drawable.crunch, "Crunch"));
        list.add(new Exercise(R.drawable.dumbelsquat, "Dumbel squat"));
        list.add(new Exercise(R.drawable.dumbelstanding, "Dumbel standing"));
        list.add(new Exercise(R.drawable.lateralraise, "Lateral raise"));
        list.add(new Exercise(R.drawable.lowerbackcurl, "Lower back curl"));
        list.add(new Exercise(R.drawable.plank, "Plank"));
        list.add(new Exercise(R.drawable.shadowboxing, "Shadow boxing"));

    }

}


