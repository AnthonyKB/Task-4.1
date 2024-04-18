package com.example.sit305task41;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

//This java file handles the editing and validation of the application

public class ValidationAndEdit extends AppCompatActivity
{

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit); //Setting the content layout of the 'edit.xml' file

        //Finds a view under id's from the 'edit.xml' file to connect the backend code with the UI
        //Final is a non-access modifier which indicates that the value is non-changeable
        final EditText editTitle = findViewById(R.id.editTitle);
        final EditText editDescription = findViewById(R.id.editDescription);
        final EditText editDueDate = findViewById(R.id.editDueDate);
        final Button buttonSave = findViewById(R.id.saveTask);

        // Initialising isEditMode and taskId
        boolean isEditMode = getIntent().getBooleanExtra("editMode", false); //The default values for 'editmode' will be 'false' which indicates that the values cant be edited
        int taskId = getIntent().getIntExtra("taskId", -1);  //Default value for 'taskId' will be '-1'
        Database Database = new Database(this);

        //If the intent has 'extra' taskId's, meaning if that if there is more than 1 task id (more than 1 task),
        // then the 'if' function will be used.
        if (getIntent().hasExtra("taskId"))
        {

            //Receives the 'title, description, and due date' of the 'extra' tasks
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String dueDate = getIntent().getStringExtra("dueDate");

            //Using earlier initialised code (at the top of the page) in conjunction with the 'setText'
            // functions from the 'Task.java' to edit the given text in a task
            editTitle.setText(title);
            editDescription.setText(description);
            editDueDate.setText(dueDate);
        }


        //Validation
        buttonSave.setOnClickListener(new View.OnClickListener()
        {

            @Override public void onClick(View v)
            { //On click (when adding a task) validation will search if
                                         // text and date formats are correct
                                         //These values will be used for the validation below
                String title = editTitle.getText().toString().trim();             //Initialising 'title' as a string, which is set the the value of 'editTitle'
                String description = editDescription.getText().toString().trim(); //Initialising 'description' as a string, which is set the the value of 'editDescription'
                String dueDate = editDueDate.getText().toString().trim();         //Initialising 'dueDate' as a string, which is set the the value of 'editDueDate'

                //Validation to check if text or date is empty
                if (title.isEmpty()) // title.isEmpty will check whether the 'title' value is empty
                                     //If it is, an error will be return to the user before returning
                {
                    Toast.makeText(ValidationAndEdit.this, "Title cannot be empty, please enter a title", Toast.LENGTH_SHORT).show(); //This will create an error message shown to the user
                    return;
                }

                if (description.isEmpty()) //description.isEmpty will check whether the 'description' value is empty
                                           //If it is, an error will be return to the user before returning
                {
                    Toast.makeText(ValidationAndEdit.this, "Description cannot be empty, please enter a description", Toast.LENGTH_SHORT).show(); //This will create an error message shown to the user
                    return;
                }

                if (dueDate.isEmpty()) //dueDate.isEmpty will check whether the 'dueDate' value is empty
                                       //If it is, an error will be return to the user before returning
                {
                    Toast.makeText(ValidationAndEdit.this, "Date cannot be empty, please enter a date", Toast.LENGTH_SHORT).show(); //This will create an error message shown to the user
                    return;
                }


                //Saving tasks through database
                if (isEditMode && taskId != -1) //If 'edit mode' is active and the taskId is valid, then the values will be saved to the database
                {
                    Database.updateTask(new Task(taskId, title, description, dueDate)); //updating the database with the 'title, description, dueDate' as parameters
                    Toast.makeText(ValidationAndEdit.this, "Task updated", Toast.LENGTH_SHORT).show(); //Visual confirmation for success for the user
                }
                else {
                    Database.addTask(title, description, dueDate); //Updating the database using the 'title, description, dueDate' parameters
                    Toast.makeText(ValidationAndEdit.this, "Task added", Toast.LENGTH_SHORT).show(); //Visual confirmation for success for the user
                }

                finish(); //End of validation, it will return to running the rest of the application
                          //If validation goes well, and no values are empty, then the application will instantly 'finish'
            }
        });
    }
}

