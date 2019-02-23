package qihaoooooo.kyoho.model;

public class User {
    private String username;
    private int exp;

    public User(String username, int exp) {
        this.username = username;
        this.exp = exp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
