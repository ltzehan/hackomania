package qihaoooooo.kyoho.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import qihaoooooo.kyoho.model.Task;
import qihaoooooo.kyoho.model.User;

public class HerokuHelper {
    private static final String API_URL_TASKS = "http://immense-headland-53219.herokuapp.com/tasks";
    private static final String API_URL_USER = "http://immense-headland-53219.herokuapp.com/user";
    private static final String API_URL_LEADERBOARD = "http://immense-headland-53219.herokuapp.com/leaderboard";

    public static ArrayList<Task> getTasks(){
        ArrayList<Task> taskArrayList = new ArrayList<>();
        String stringJSON = getRequest(API_URL_TASKS);

        try{
            JSONObject jsonObj = new JSONObject(stringJSON);
            JSONArray tasksJSONArray = jsonObj.getJSONArray("");

            for( int i=0; i<tasksJSONArray.length(); i++) {
                JSONObject t = tasksJSONArray.getJSONObject(i);
                String title = t.getString("title");
                int attack = t.getInt("attack");
                String imageid = t.getString("imageid");

                taskArrayList.add(new Task(title, attack, imageid));
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        System.out.println(taskArrayList);
        return taskArrayList;
    }

    public static ArrayList<User> getLeaderboard(){
        ArrayList<User> users = new ArrayList<>();
        String stringJSON = getRequest(API_URL_LEADERBOARD);

        try{
            JSONObject jsonObj = new JSONObject(stringJSON);
            JSONArray usersJSONArray = jsonObj.getJSONArray("");

            for( int i=0; i<usersJSONArray.length(); i++){
                JSONObject u = usersJSONArray.getJSONObject(i);
                String username = u.getString("username");
                int exp = u.getInt("exp");

                users.add(new User(username, exp));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void postUser(User user){
        try{
            URL url = new URL(API_URL_USER);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            JSONObject userJSON = new JSONObject();
            userJSON.put("username", user.getUsername());
            userJSON.put("exp", user.getExp());

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(userJSON.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User user){
        try{
            URL url = new URL(API_URL_USER);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PATCH");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            JSONObject userJSON = new JSONObject();
            userJSON.put("username", user.getUsername());

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(userJSON.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRequest(String urlstring){
        String requeststring = "";
        try {
            URL url = new URL(urlstring);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(con.getInputStream());

            Scanner s = new Scanner(in).useDelimiter("\\A");
            requeststring = s.hasNext() ? s.next() : "";

            System.out.println(requeststring);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requeststring;
    }

    public static void main(String[] args) {
        getTasks();
    }
}