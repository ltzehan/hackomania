package qihaoooooo.kyoho.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import qihaoooooo.kyoho.model.Boss;
import qihaoooooo.kyoho.model.Task;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "KyohoDatabase.db";
    public static final String TASKS_TABLE_NAME = "Tasks";
    public static final String USER_TABLE_NAME = "User";
    public static final String BOSS_TABLE_NAME = "Bosses";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + "( id INTEGER PRIMARY KEY, " +
                "title TEXT, completed INTEGER, expired INTEGER, attack INTEGER, imageid TEXT)");
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (username TEXT PRIMARY KEY, health INTEGER," +
                "exp INTEGER, attack INTEGER)");
        db.execSQL("CREATE TABLE " + BOSS_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "health INTEGER, maxhealth INTEGER, name TEXT, expvalue INTEGER, alive INTEGER)");

        db.execSQL("INSERT INTO " + USER_TABLE_NAME + " (username, health, exp, attack) VALUES " +
                "('', 0, 0, 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOSS_TABLE_NAME);
        onCreate(db);
    }

    public void newTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("id", task.getId());
        content.put("title", task.getTitle());
        content.put("completed", task.isCompleted()?0:1);
        content.put("expired", task.isExpired()?0:1);
        content.put("attack", task.getAttack());
        content.put("imageid", task.getImageId());

        db.insert(TASKS_TABLE_NAME, null, content);
    }

    public void newBoss(Boss boss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("name", boss.getName());
        content.put("health", boss.getHealth());
        content.put("maxhealth", boss.getMaxHealth());
        content.put("expvalue", boss.getExpValue());
        content.put("alive", boss.isAlive()?0:1);

        db.insert(BOSS_TABLE_NAME, null, content);
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("title", task.getTitle());
        content.put("completed", task.isCompleted()?0:1);
        content.put("expired", task.isExpired()?0:1);
        content.put("attack", task.getAttack());
        content.put("imageid", task.getImageId());

        db.update(TASKS_TABLE_NAME, content, "id="+task.getId(), null);
    }

    public void updateBoss(Boss boss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("name", boss.getName());
        content.put("health", boss.getHealth());
        content.put("maxhealth", boss.getMaxHealth());
        content.put("expvalue", boss.getExpValue());
        content.put("alive", boss.isAlive()?0:1);

        db.update(BOSS_TABLE_NAME, content, "alive=true", null);
    }

    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " WHERE " +
                " completed=0 AND expired=1", null);

        res.moveToFirst();
        while(!res.isAfterLast()){
            String title = res.getString(res.getColumnIndex("title"));
            int attack = Integer.parseInt(res.getString(res.getColumnIndex("attack")));
            String imageid = res.getString(res.getColumnIndex("imageid"));

            Task task = new Task(title, attack, imageid);
            tasks.add(task);
        }

        return tasks;
    }

    public Boss getCurrentBoss() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + BOSS_TABLE_NAME + " WHERE " +
                "alive=0", null);
        res.moveToFirst();

        String name = res.getString(res.getColumnIndex("name"));
        int health = Integer.parseInt(res.getString(res.getColumnIndex("health")));
        int maxhealth = Integer.parseInt(res.getString(res.getColumnIndex("maxhealth")));
        int expvalue = Integer.parseInt(res.getString(res.getColumnIndex("expvalue")));

        Boss boss = new Boss(name, maxhealth, expvalue);
        boss.setHealth(health);

        return boss;
    }
}
