package com.example.sit305task41;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder>
{
    private Context context; //Using 'context' for various uses
    private List<Task> taskList; //List from 'Task' java file

    public Adapter(Context context, List<Task> taskList)
    {
        this.context = context; //The current instance of 'context' is the value of context
        this.taskList = taskList != null ? taskList : new ArrayList<>(); //If taskList is not null, then the value is 'taskList',
                                                                         // or else, a new ArrayList is made
    }


    @Override public int getItemCount() //Initialising 'getItemCount' which returns the size of the task list in integer value
    {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder
    {
        //Initialising TextView widgets for title, description, and date
        TextView titleView;
        TextView descriptionView;
        TextView dateView;

        //Initialising button widgets for the edit and delete buttons.
        Button buttonEdit;
        Button buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            //Finds a view under id's from the 'tasks.xml' file to connect the backend code with the UI
            //Then assigning the widgets that were initialised above to these values
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            dateView = itemView.findViewById(R.id.dateView);
            buttonEdit = itemView.findViewById(R.id.editButton);
            buttonDelete = itemView.findViewById(R.id.deleteButton);
        }
    }


    @NonNull @Override public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.tasks, parent, false);
        return new TaskViewHolder(view);
    }


    @Override public void onBindViewHolder(@NonNull TaskViewHolder holder, int position)
    {
        Task task = taskList.get(position);
        holder.titleView.setText(task.getTitle());
        holder.descriptionView.setText(task.getDescription());
        holder.dateView.setText(task.getDueDate());

        //On click listener for the 'delete' button. This determines what happens when this button is pressed
        holder.buttonDelete.setOnClickListener(new View.OnClickListener()
        {

            //On click, this function will remove the task from the current position, notify the task list
            // and database, and then update both before notifying the user with a message
            @Override public void onClick(View v)
            {
                Database Database = new Database(context);
                Database.deleteTask(task.getId()); //Delete the task where the value is at the current id's

                taskList.remove(position); //Removing the task from the task list at the current position
                notifyItemRemoved(position); //Notifying the adapter that the task has been removed at the current position
                notifyItemRangeChanged(position, taskList.size());
                Toast.makeText(context, "Task removed from list", Toast.LENGTH_SHORT).show(); //Notify the user through text that the task has been removed from the list
            }
        });

        //On click listener for the 'edit' button. This determines what happens when this button is pressed
        holder.buttonEdit.setOnClickListener(new View.OnClickListener()
        {

            @Override public void onClick(View v)
            {
                //Gathering the values from the task to display on the edit screen
                Intent Intent = new Intent(context, ValidationAndEdit.class);
                Intent.putExtra("taskId", task.getId());
                Intent.putExtra("title", task.getTitle());
                Intent.putExtra("description", task.getDescription());
                Intent.putExtra("dueDate", task.getDueDate());
                context.startActivity(Intent);

                Database Database = new Database(context);
                Database.deleteTask(task.getId()); //Deleting the task in the database at the current task list id

                //Removing the task from the task list, and then notifying the adapter
                taskList.remove(position); //Removing the task from the taskList at the current position
                notifyItemRemoved(position); //Notifying the adapter that the task has been removed
                notifyItemRangeChanged(position, taskList.size());
                Toast.makeText(context, "Task removed from list", Toast.LENGTH_SHORT).show(); //Notifying the user through text that the task has been removed from the list

            }
        });



    }

}
