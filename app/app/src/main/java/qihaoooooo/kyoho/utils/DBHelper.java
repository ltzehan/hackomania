package qihaoooooo.kyoho.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import qihaoooooo.kyoho.model.Boss;
import qihaoooooo.kyoho.model.Task;
import qihaoooooo.kyoho.model.User;

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
        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + "( id TEXT PRIMARY KEY, " +
                "title TEXT, completed INTEGER, expired INTEGER, attack INTEGER, imageid TEXT)");
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (username TEXT PRIMARY KEY, health INTEGER, " +
                "exp INTEGER, attack INTEGER)");
        db.execSQL("CREATE TABLE " + BOSS_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "health INTEGER, maxhealth INTEGER, name TEXT, expvalue INTEGER, alive INTEGER)");

        db.execSQL("INSERT INTO " + USER_TABLE_NAME + " (username, health, exp, attack) VALUES " +
                "('USER', 100, 0, 0)");
        db.execSQL("INSERT INTO " + BOSS_TABLE_NAME + " (health, maxhealth, name, expvalue, alive) VALUES " +
                "(10, 10, 'Kyoho', 10, 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOSS_TABLE_NAME);
        onCreate(db);
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOSS_TABLE_NAME);

        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + "( id TEXT PRIMARY KEY, " +
                "title TEXT, completed INTEGER, expired INTEGER, attack INTEGER, imageid TEXT)");
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (username TEXT PRIMARY KEY, health INTEGER," +
                "exp INTEGER, attack INTEGER)");
        db.execSQL("CREATE TABLE " + BOSS_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "health INTEGER, maxhealth INTEGER, name TEXT, expvalue INTEGER, alive INTEGER)");

        db.execSQL("INSERT INTO " + USER_TABLE_NAME + " (username, health, exp, attack) VALUES " +
                "('USER', 100, 0, 0)");
        db.execSQL("INSERT INTO " + BOSS_TABLE_NAME + " (health, maxhealth, name, expvalue, alive) VALUES " +
                "(30, 30, 'Kyoho', 10, 0)");
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("health", user.getHealth());
        content.put("exp", user.getExp());
        content.put("attack", user.getAttack());

        db.update(USER_TABLE_NAME, content, "username='USER'", null);
    }

    public User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
        res.moveToFirst();

        String username = res.getString(res.getColumnIndex("username"));
        int health = res.getInt(res.getColumnIndex("health"));
        int exp = res.getInt(res.getColumnIndex("exp"));
        int attack = res.getInt(res.getColumnIndex("attack"));
        return new User(username, exp, health, attack);
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
        db.close();
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
        db.close();
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("title", task.getTitle());
        content.put("completed", task.isCompleted()?0:1);
        content.put("expired", task.isExpired()?0:1);
        content.put("attack", task.getAttack());
        content.put("imageid", task.getImageId());

        db.update(TASKS_TABLE_NAME, content, "id='"+task.getId()+"'", null);
        db.close();
    }

    public void updateBoss(Boss boss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("name", boss.getName());
        content.put("health", boss.getHealth());
        content.put("maxhealth", boss.getMaxHealth());
        content.put("expvalue", boss.getExpValue());
        content.put("alive", boss.isAlive()?0:1);

        db.update(BOSS_TABLE_NAME, content, "alive=0", null);
        db.close();
    }

    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " WHERE " +
                " completed=1 AND expired=1", null);

        if(res.moveToFirst()){
            do{
                String title = res.getString(res.getColumnIndex("title"));
                int attack = Integer.parseInt(res.getString(res.getColumnIndex("attack")));
                String imageid = res.getString(res.getColumnIndex("imageid"));
                String id = res.getString(res.getColumnIndex("id"));

                Task task = new Task(title, false, false, attack, id, imageid);
                tasks.add(task);
            } while(res.moveToNext());
        }
        db.close();
        return tasks;
    }

    public Boss getCurrentBoss() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + BOSS_TABLE_NAME + " WHERE " +
                "alive=0", null);
        Boss boss = null;
        if(res.moveToFirst()){
            String name = res.getString(res.getColumnIndex("name"));
            int health = Integer.parseInt(res.getString(res.getColumnIndex("health")));
            int maxhealth = Integer.parseInt(res.getString(res.getColumnIndex("maxhealth")));
            int expvalue = Integer.parseInt(res.getString(res.getColumnIndex("expvalue")));

            boss = new Boss(health, maxhealth, expvalue, true, name);
            boss.setHealth(health);
        }
        db.close();
        return boss;
    }
}

