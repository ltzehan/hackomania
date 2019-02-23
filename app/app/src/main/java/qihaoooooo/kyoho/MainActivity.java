package qihaoooooo.kyoho;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import qihaoooooo.kyoho.model.Boss;
import qihaoooooo.kyoho.model.Task;
import qihaoooooo.kyoho.model.TaskAdapter;
import qihaoooooo.kyoho.model.User;
import qihaoooooo.kyoho.utils.DBHelper;
import qihaoooooo.kyoho.utils.HerokuHelper;
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

    public DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEST
        myDB = new DBHelper(this);
        myDB.reset();

        ArrayList<Task> testSet = HerokuHelper.getTasks();
        for(Task t: testSet){
            System.out.println(t.getTitle());
            myDB.newTask(t);
        }
        //TEST

        healthBar = findViewById(R.id.healthBar);
        attackBar = findViewById(R.id.attackBar);
        expBar = findViewById(R.id.expBar);
        bossBar = findViewById(R.id.bossHealthBar);

        // TODO get tasks from server
        // tasks = serverHandler.getTaskList();
        // tasks = new ArrayList<>();
        tasks = myDB.getAllTasks();
        tasks.add(new Task("Test",10,"pyramid.png"));
        tasks.add(new Task("boop", 8, "pills.png"));

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
