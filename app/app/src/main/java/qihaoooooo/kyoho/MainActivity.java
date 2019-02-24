package qihaoooooo.kyoho;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qihaoooooo.kyoho.model.Boss;
import qihaoooooo.kyoho.model.Task;
import qihaoooooo.kyoho.model.TaskAdapter;
import qihaoooooo.kyoho.model.User;
import qihaoooooo.kyoho.utils.DBHelper;
import qihaoooooo.kyoho.utils.HerokuHelper;
import qihaoooooo.kyoho.view.PixelTextView;
import qihaoooooo.kyoho.view.SquareImageView;
import qihaoooooo.kyoho.view.TaskRecyclerView;
import qihaoooooo.kyoho.view.ValueBar;

public class MainActivity extends AppCompatActivity {

    // task container
    private TaskRecyclerView taskRecycleView;
    private TaskRecyclerView.Adapter taskAdapter;
    private TaskRecyclerView.LayoutManager taskLayoutManager;
    private PixelTextView noTaskTextView;

    private List<Task> tasks;

    // value bars
    private ValueBar healthBar;
    public static ValueBar attackBar;
    private ValueBar bossBar;

    private TextView expTextView;

    private SquareImageView rip;
    private ImageView bossImage;
    private Animation ripAnim;
    private Animation yeetAnim;
    private Animation slideAnim;

    public static DBHelper myDB;
    public static Boss currentBoss;
    public static User user;

    // Funky double click stuff plz ignore
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        hideStatusBar();
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);
        // myDB.reset();

        ArrayList<Task> currentTasks = myDB.getAllTasks();
        if(currentTasks.size()==0){
            ArrayList<Task> testSet = HerokuHelper.getTasks(HerokuHelper.API_URL_TASKS);
            for(Task t: testSet){
                System.out.println(t.getTitle());
                myDB.newTask(t);
            }
        }

        rip = findViewById(R.id.rip);
        bossImage = findViewById(R.id.bossImage);
        ripAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rip);
        ripAnim.setAnimationListener(
            new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                    rip.setVisibility(View.VISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    rip.startAnimation(yeetAnim);
                }
            }
        );

        yeetAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.yeetus);
        yeetAnim.setAnimationListener(
            new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation arg0) {
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {
                }
                @Override
                public void onAnimationEnd(Animation arg0) {
                    rip.setVisibility(View.INVISIBLE);
                    bossImage.setVisibility(View.INVISIBLE);
                    bossImage.startAnimation(slideAnim);
                }
            }
        );

        slideAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        slideAnim.setAnimationListener(
                new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                        bossImage.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                    }
                }
        );

        // Initialise boss and user
        user = myDB.getUser();
        currentBoss = myDB.getCurrentBoss();

        healthBar = findViewById(R.id.healthBar);
        attackBar = findViewById(R.id.attackBar);
        bossBar = findViewById(R.id.bossHealthBar);
        expTextView = findViewById(R.id.expTextView);
        expTextView.setText(user.getExp()+"");

        healthBar.setBarProp(100,user.getHealth());
        attackBar.setBarProp(20, user.getAttack());
        bossBar.setBarProp(currentBoss.getMaxHealth(), currentBoss.getHealth());

        // TODO get tasks from server
        // tasks = serverHandler.getTaskList();

        // tasks = new ArrayList<>();
        tasks = myDB.getAllTasks();
//        myDB.newTask(new Task("Doot",10,"pyramid"));
//        tasks.add(new Task("Test",10,"pyramid"));
//        tasks.add(new Task("boop", 8, "pills"));

        //
        //  Set up RecyclerView for task list
        //
        noTaskTextView = (PixelTextView) findViewById(R.id.noTaskTextView);
        taskRecycleView = (TaskRecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecycleView.setHasFixedSize(true);
        taskRecycleView.setNoTaskTextView(noTaskTextView);

        noTaskTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Task> bonusTasks = HerokuHelper.getTasks(HerokuHelper.API_URL_TASKS_BONUS);
                tasks.addAll(bonusTasks);
                Log.e("asdf", "added tasks");
                taskRecycleView.addedTasks();
            }
        });

        taskAdapter = new TaskAdapter(tasks);
        taskRecycleView.setAdapter(taskAdapter);

        taskLayoutManager = new LinearLayoutManager(this);
        taskRecycleView.setLayoutManager(taskLayoutManager);

        RelativeLayout bossLayout = findViewById(R.id.bossContainer);

        bossLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 250){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if(user.getAttack()>0) {
                    user.incrementAttack(-1);
                    attackBar.decrValue(1);
                    currentBoss.decrementHealth();
                    bossBar.decrValue(1);

                    if(currentBoss.getHealth()<=0) {
                        animBossKill();

                        user.incrementExp(currentBoss.getExpValue());
                        expTextView.setText(user.getExp()+"");
                        currentBoss.setAlive(false);
                        Boss newBoss = new Boss("Boss", currentBoss.getMaxHealth()+10,
                                currentBoss.getExpValue()+5);
                        myDB.updateBoss(currentBoss);
                        myDB.newBoss(newBoss);

                        currentBoss = newBoss;
                        bossBar.setBarProp(currentBoss.getMaxHealth(), currentBoss.getHealth());
                    }

                    myDB.updateUser(user);
                    myDB.updateBoss(currentBoss);

                    System.out.println("USERRRRR"+user.getAttack());
                    System.out.println("BOSSSS"+currentBoss.getHealth());
                }
            }
        });

    }

    private void animBossKill() {
        int h = bossImage.getLayoutParams().height / 2;
        rip.getLayoutParams().height = h;

        rip.startAnimation(ripAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideStatusBar();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideStatusBar();
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
