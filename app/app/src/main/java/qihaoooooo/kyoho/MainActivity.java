package qihaoooooo.kyoho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import qihaoooooo.kyoho.model.Task;
import qihaoooooo.kyoho.model.TaskAdapter;

public class MainActivity extends AppCompatActivity {

    // task container
    private RecyclerView taskRecycleView;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO get tasks from server
        // tasks = serverHandler.getTaskList();

        //
        //  Set up RecyclerView for task list
        //
        taskRecycleView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecycleView.setHasFixedSize(true);

        taskAdapter = new TaskAdapter(tasks);
        taskRecycleView.setAdapter(taskAdapter);

        taskLayoutManager = new LinearLayoutManager(this);
        taskRecycleView.setLayoutManager(taskLayoutManager);

    }



}
