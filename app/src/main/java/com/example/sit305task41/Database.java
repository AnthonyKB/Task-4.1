package com.example.sit305task41;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


//Database class will handle all of the database-related storing of data, using the SQLite package


public class Database extends SQLiteOpenHelper {

        //Initialising the database name and version
        private static final String DATABASE_NAME = "taskManagerApp.db";
        private static final int DATABASE_VERSION = 1;

        public Database(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        //This method is relevant to create the activity
        @Override public void onCreate(SQLiteDatabase db)
        {
                db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, dueDate INTEGER)");
        }


        //This method is releveant when the database needs to be upgraded
        @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
                db.execSQL("DROP TABLE IF EXISTS tasks");
                onCreate(db);
        }

        //This getTasks function will gather the data to be used in the main operation of the app
        //The purpose of this will be shown when we want to resume the task, where this function will assist in loading all of the data
        public List<Task> getTasks()
        {
                List<Task> taskList = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase(); //Opening the database to write

                //Cursor allows us to read through the rows and columns of the task list
                Cursor cursor = db.query("tasks", null, null, null, null, null, "dueDate ASC");

                //This if do statement will iterate through the rows
                //Each column will return with -1 if the column does not exist
                if (cursor.moveToFirst())
                {
                        do
                        {
                                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                                @SuppressLint("Range") String dueDate = cursor.getString(cursor.getColumnIndex("dueDate"));

                                taskList.add(new Task(id, title, description, dueDate)); //Adding new 'id, title, description, dueDate' for each iteration
                        } while (cursor.moveToNext()); //This will move to the next row
                }
                cursor.close(); //closes cursor and stops iterating through the task list
                return taskList;
        }

        //This function will remove tasks and update the database
        public void deleteTask(int id)
        {
                SQLiteDatabase db = this.getWritableDatabase(); //Opening the database to write

                db.delete("tasks", "id=?", new String[]{String.valueOf(id)}); //Deleting task from the database
                db.close();
        }

        //This function will add tasks and update the database
        public void addTask(String title, String description, String dueDate)
        {
                SQLiteDatabase db = this.getWritableDatabase(); //Opening the database to write

                ContentValues ContentValues = new ContentValues(); //Creating a new contentvalues, which will be used to store values
                ContentValues.put("title", title); //Storing title as a string based on user input
                ContentValues.put("description", description); //Storing description as a string based on user input
                ContentValues.put("dueDate", dueDate); //Storing due date as a string based on user input

                db.insert("tasks", null, ContentValues); //Inserting the values from ContentValues into the database
        }

        //This function will update the task in the database
        //*for editing tasks
        public int updateTask(Task task) {
                SQLiteDatabase db = this.getWritableDatabase(); //Opening the database to write

                ContentValues ContentValues2 = new ContentValues(); //Creating a new contentvalues, which will be used to store values
                ContentValues2.put("title", task.getTitle()); //Storing title using 'getTitle' from Task.java
                ContentValues2.put("description", task.getDescription()); //Storing description using 'getDescription' from Task.java
                ContentValues2.put("dueDate", task.getDueDate()); //Storing due date using 'getDueDate' from Task.java

                //This updates the task in the database using the data stored in ContentValues2
                int Update = db.update("tasks", ContentValues2, "id=?", new String[]{String.valueOf(task.getId())});
                return Update; //Returns the number of rows affected
        }
}