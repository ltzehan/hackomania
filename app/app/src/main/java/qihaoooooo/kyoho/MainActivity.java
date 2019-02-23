package qihaoooooo.kyoho;

import android.app.admin.SystemUpdatePolicy;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.instacart.library.truetime.TrueTime;

import java.util.ArrayList;
import java.util.List;

import qihaoooooo.kyoho.model.Task;
import qihaoooooo.kyoho.model.TaskAdapter;
import qihaoooooo.kyoho.view.TaskRecyclerView;
import qihaoooooo.kyoho.view.ValueBar;

public class MainActivity extends AppCompatActivity {

    // task container
    private TaskRecyclerView taskRecycleView;
    private TaskRecyclerView.Adapter taskAdapter;
    private TaskRecyclerView.LayoutManager taskLayoutManager;

    private List<Task> tasks;

    // value bars
    private ValueBar healthBar;
    private ValueBar attackBar;
    private ValueBar expBar;
    private ValueBar bossBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        healthBar = findViewById(R.id.healthBar);
        attackBar = findViewById(R.id.attackBar);
        expBar = findViewById(R.id.expBar);
        bossBar = findViewById(R.id.bossHealthBar);

        try {
            TrueTime.build().initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO get tasks from server
        // tasks = serverHandler.getTaskList();
        tasks = new ArrayList<>();
//        tasks.add(new Task("Test",10000,20));
//        tasks.add(new Task("boop", 4206969, 22222));
//        tasks.add(new Task("oof", 1, 1));
//        tasks.add(new Task("oof3", 1, 1));
//        tasks.add(new Task("oof2", 1, 1));

        //
        //  Set up RecyclerView for task list
        //
        taskRecycleView = (TaskRecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecycleView.setHasFixedSize(true);
        taskRecycleView.setNoTaskTextView(findViewById(R.id.noTaskTextView));

        taskAdapter = new TaskAdapter(tasks);
        taskRecycleView.setAdapter(taskAdapter);

        taskLayoutManager = new LinearLayoutManager(this);
        taskRecycleView.setLayoutManager(taskLayoutManager);




    }



}
