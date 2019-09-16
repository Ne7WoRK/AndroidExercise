package com.example.ivaylogeorgiev.androidexercise.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;
import java.util.Date;

import com.example.ivaylogeorgiev.androidexercise.Database.ExerciseDB;
import com.example.ivaylogeorgiev.androidexercise.R;
import com.example.ivaylogeorgiev.androidexercise.Utils.AlarmNotificationReceiver;

public class SettingsActivity extends AppCompatActivity {

    //Initialise elements of the Settings activity.

    Button btnSave;
    RadioButton easy,medium,hard;
    RadioGroup radioGroup;
    ExerciseDB exerciseDB;
    ToggleButton alarmStatus;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);


        //Get save button instance from xml layout.
        btnSave = (Button)findViewById(R.id.btnSave);


        //Get the option radio buttons instances from xml layout.
        radioGroup = (RadioGroup) findViewById(R.id.rdiGroup);
        easy = (RadioButton) findViewById(R.id.rdiEasy);
        medium = (RadioButton) findViewById(R.id.rdiMedium);
        hard = (RadioButton) findViewById(R.id.rdiHard);


        //Get the on/off alarm toggle button instance from xml layout
        alarmStatus = (ToggleButton) findViewById(R.id.switchAlarm);


        //Get the timepicker instance from xml layout.
        timePicker = (TimePicker) findViewById(R.id.timePicker);


        //Create an instance of the SQLite database.
        exerciseDB = new ExerciseDB(this);



        //Get the saved settings from database and show the latest chosen difficulty.
        int mode = exerciseDB.getDatabaseDifficulty();
        setDifficulty(mode);


        //On click the save button store the chosen workout difficulty in database and set an alarm if toggle button says ON.
        //Notify the user changes have taken place.
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveDifficulty();
                saveAlarm(alarmStatus.isChecked());
                Toast.makeText(SettingsActivity.this,"Option saved !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



    // This method will be used to check toggle button status and if toggle is on an alarm will be set
    // according to chosen timepicker user input (hour and minutes).

    private void saveAlarm(boolean checked) {

        if(checked) {

            AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent = new Intent(SettingsActivity.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            //Set alarm time according to timePicker provided user input (hour and minutes).

            Calendar calendar = Calendar.getInstance();
            Date today = Calendar.getInstance().getTime();
            calendar.set(today.getYear(),today.getMonth(),today.getDay(),timePicker.getHour(),timePicker.getHour(),timePicker.getMinute());

            //Set a daily repeat of the alarm.

            manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            //Debug alarm.
            Log.d("Debug","Alarm will wake up at: "+timePicker.getHour()+":"+timePicker.getMinute());
        }

        else {

            //Cancel the alarm
            Intent intent = new Intent(SettingsActivity.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }

    }



    // This method will check the users selected choice of exercise difficulty (easy,medium,hard)
    // and will save its integer value in SQlite database.

    private void saveDifficulty() {

        int optionId = radioGroup.getCheckedRadioButtonId();

        if(optionId == easy.getId())
            exerciseDB.saveDifficulty(0);

        else if (optionId == medium.getId())
            exerciseDB.saveDifficulty(1);

        else if (optionId == hard.getId())
            exerciseDB.saveDifficulty(2);
    }



    // This method will accept an integer value retrieved from DB and will adjust difficulty displayed in the UI according to
    // the latest saved level of difficulty stored in database.

    private void setDifficulty(int mode) {

        if (mode == 0)
            radioGroup.check(R.id.rdiEasy);

        else if (mode == 1)
            radioGroup.check(R.id.rdiMedium);

        else if (mode == 2)
            radioGroup.check(R.id.rdiHard);
    }
}
