package com.jmc.kmdsekoiacarepath.Models;

import com.jmc.kmdsekoiacarepath.Views.ViewFactory;

public class Model {
    private static Model instance;
    private ViewFactory viewFactory;
    private int currentUserId; // To store the logged-in user's ID

    private Model() {
        viewFactory = new ViewFactory();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }
}
