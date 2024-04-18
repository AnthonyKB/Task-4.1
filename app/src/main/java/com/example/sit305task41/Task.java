package com.example.sit305task41;


import androidx.recyclerview.widget.RecyclerView;

//This Java class will be used to define what is contained within a task
//Furthermore, the setters and getters will be located here too


public class Task { //Public class 'Task' which initialises 'id, title, description,
                    // and dueDate' variables which will be used below
    private int id;
    private String title;
    private String description;
    private String dueDate;

    public Task(int id, String title, String description, String dueDate) //Creating 'Task' function which contains all of the variables above
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    //Creating the getters, which upon use will return the value of the set variable.
    public int getId() { //For example, this one will return the value of the id
        return id;
    }

    public String getTitle() { //Or this one will return the value of the task title
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }


    //Creating setters which upon use, will set the value of a set variable
    public void setId(int id) { //For example, this one will change the value of the 'id' variable
        this.id = id;
    }

    public void setTitle(String title) { //Or this one would change the value of the 'title' variable
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}







