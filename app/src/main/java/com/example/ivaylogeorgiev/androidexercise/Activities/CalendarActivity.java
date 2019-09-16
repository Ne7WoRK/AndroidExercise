package com.example.ivaylogeorgiev.androidexercise.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ivaylogeorgiev.androidexercise.Decorator.FinishedExerciseDecorator;
import com.example.ivaylogeorgiev.androidexercise.Database.ExerciseDB;
import com.example.ivaylogeorgiev.androidexercise.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.List;

import java.util.HashSet;

public class CalendarActivity extends AppCompatActivity {

    //Initialise elements of the Calendar activity.
    MaterialCalendarView materialCalendarView;
    ExerciseDB exerciseDB;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Initialise a default instance of the database.
        exerciseDB = new ExerciseDB(this);

        //Get calendar instance from xml layout.
        materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendar);

        //Get a list of long values representing all days that a user has performed a training.
        List<String> workoutDay = exerciseDB.getWorkoutDays();

        //Initialise a hash set that stores CalendarDay objects.
        HashSet<CalendarDay> convertedList = new HashSet<>();

        /**
         * Loop through the fetched list of long values from SQLite database.Convert them to dates and populate
         * the initialised hash set (convertedList). After that decorate all dates  .
         */
        for(String value:workoutDay)
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        materialCalendarView.addDecorator(new FinishedExerciseDecorator(convertedList));

    }
}
