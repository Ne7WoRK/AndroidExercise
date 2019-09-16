package com.example.ivaylogeorgiev.androidexercise.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ivaylogeorgiev.androidexercise.Adapter.RecyclerViewAdapter;
import com.example.ivaylogeorgiev.androidexercise.Model.Exercise;
import com.example.ivaylogeorgiev.androidexercise.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity {

    //Initialise elements of Exercise list activity
    List<Exercise> exercises = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        //Fill the list with Exercise objects.
        populateExerciseList();

        // Get list instance from xml layout.
        recyclerView = (RecyclerView)findViewById(R.id.list_ex);

        //Create new adapter using the exercise list and base context.
        adapter = new RecyclerViewAdapter(exercises, getBaseContext());

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    //Method used to populate exercise list with Exercise objects.
    private void populateExerciseList(){

        exercises.add(new Exercise(R.drawable.pushup, "Push ups"));
        exercises.add(new Exercise(R.drawable.deadbug, "Dead bug"));
        exercises.add(new Exercise(R.drawable.sideplank, "Side plank"));
        exercises.add(new Exercise(R.drawable.bicepscurl, "Biceps curl"));
        exercises.add(new Exercise(R.drawable.crunch, "Crunch"));
        exercises.add(new Exercise(R.drawable.dumbelsquat, "Dumbel squat"));
        exercises.add(new Exercise(R.drawable.dumbelstanding, "Dumbel standing"));
        exercises.add(new Exercise(R.drawable.lateralraise, "Lateral raise"));
        exercises.add(new Exercise(R.drawable.lowerbackcurl, "Lower back curl"));
        exercises.add(new Exercise(R.drawable.plank, "Plank"));
        exercises.add(new Exercise(R.drawable.shadowboxing, "Shadow boxing"));

    }

}
