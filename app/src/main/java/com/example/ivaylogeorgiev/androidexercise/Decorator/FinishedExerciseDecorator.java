package com.example.ivaylogeorgiev.androidexercise.Decorator;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

/**
 * Created by ivaylogeorgiev on 05/05/2019.
 */

public class FinishedExerciseDecorator implements DayViewDecorator {

    //Initialise elements of the decorator class.
    HashSet<CalendarDay> list;

    //A specialized Drawable that fills the Canvas with a specified color. (android docs).
    ColorDrawable colorDrawable;

    //Constructor of the decorator class.
    public FinishedExerciseDecorator(HashSet<CalendarDay> list){

        this.list = list;
        colorDrawable = new ColorDrawable(Color.parseColor("#E57373"));
    }

    //If day exists in hash set of days it should be decorated.
    public boolean shouldDecorate(CalendarDay day) {

        return list.contains(day);
    }

    //This method is used to decorate a calendar day with the color #E57373
    public void decorate(DayViewFacade view) {

        view.setBackgroundDrawable(colorDrawable);
    }
}
