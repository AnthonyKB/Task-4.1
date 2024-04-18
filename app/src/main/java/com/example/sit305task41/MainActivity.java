package com.example.sit305task41;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    //Using the other java class files created
    private List<Task> taskList;
    private RecyclerView RecyclerView;
    private Database Database;
    private Adapter Adapter;


    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database = new Database(this);
        RecyclerView = findViewById(R.id.recyclerView); //Finds a view under 'recyclerView' from the activity_main.xml to connect the backend code with the UI
        RecyclerView.setLayoutManager(new LinearLayoutManager(this)); //Setting the layout of the contents

        //Listening to the button on the front page of the app
        //Once clicked, MainActivity and ValidationAndEdit classes will start
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener()
        {

            @Override public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ValidationAndEdit.class));
            }
        });
    }


    //onResume aims to 'resume' the task list, where the data will be loaded through the database
    @Override protected void onResume()
    {
        super.onResume(); //When the task is resumed
        loadTasks();      //The loadTasks function will be used, which uses the database's stored data to load the tasks
    }

    //Self explanatory, loadTasks will be used to load tasks through the values stored in the database
    private void loadTasks()
    {
        taskList = Database.getTasks(); //Using the 'getTasks' from the Database class file to retrieve all task data
        Adapter = new Adapter(this, taskList); //Setting up a new adapter using the taskList
        RecyclerView.setAdapter(Adapter); //Setting a recycler view up for the adapter
    }


}
