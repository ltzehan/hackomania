package qihaoooooo.kyoho.model;

public class Task {

    private String title;
    private long endDate;
    private boolean completed;
    private boolean expired;
    private int attack;

    public Task(String title, long endDate, int attack) {
        this.title = title;
        this.endDate = endDate;
        this.attack = attack;

        this.completed = false;
        this.expired = false;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeLeft() {
        // TODO integrate with unix time
        return "Expires in <time>";
    }

}