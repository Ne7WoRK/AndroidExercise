package com.example.ivaylogeorgiev.androidexercise.Adapter;

/**
 * Created by ivaylogeorgiev on 31/03/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivaylogeorgiev.androidexercise.R;
import com.example.ivaylogeorgiev.androidexercise.Model.Exercise;
import com.example.ivaylogeorgiev.androidexercise.Utils.ViewExercise;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //Elements of the adapter class.
    private List<Exercise> exercises;
    private Context context;

    //Connects the XML items with java variables
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.ex_img);
            text = (TextView) itemView.findViewById(R.id.ex_name);
        }
    }


    //Constructor
    public RecyclerViewAdapter(List<Exercise> exerciseList, Context context) {
        this.exercises = exerciseList;
        this.context = context;

    }

    //Instantiating the layout of the page
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise, parent, false);

        return new ViewHolder(itemView);

    }


    //Binding the data ot the design elements.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Exercise exercise = exercises.get(position);
        holder.image.setImageResource(exercises.get(position).getImage_id());
        holder.text.setText(exercises.get(position).getName());

        //A click listener to allow the user to go to the specified exercise and view its details.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Using intent to call a new activity of View exercise.
                Intent intent = new Intent(context, ViewExercise.class);
                intent.putExtra("image_id", exercise.getImage_id());
                intent.putExtra("name", exercise.getName());
                context.startActivity(intent);

            }
        });

    }


    //Defines the number of items in the list
    public int getItemCount() {

        return exercises.size();

    }

}
