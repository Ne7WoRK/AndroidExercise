package com.example.ivaylogeorgiev.androidexercise.Model;

/**
 * Created by ivaylogeorgiev on 15/03/2019.
 */

// This class is used to describe the model of exercise object, which consists of image id and exercise name.

public class Exercise {

    private int image_id;
    private String name;

    // Constructor of the Exercise class (object).
    public Exercise (int image_id, String name) {

        this.image_id = image_id;
        this.name = name;
    }

    // Getter for get image id.
    public int getImage_id() {

        return image_id;

    }

    // Setter for set image id.
    public void setImage_id(int image_id) {

        this.image_id = image_id;

    }

    // Getter of exercise name.
    public String getName() {

        return name;

    }

    // Setter for exercise name.
    public void setName(String name) {

        this.name = name;

    }
}

