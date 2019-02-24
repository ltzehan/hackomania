package qihaoooooo.kyoho.model;

public class User {
    private String username;
    private int exp;
    private int health;
    private int attack;

    public User(String username, int exp, int health, int attack) {
        this.username = username;
        this.exp = exp;
        this.health = health;
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void incrementHealth(int increment){
        int newHealth = this.health + increment;
        if (newHealth>100) this.health = 100;
        else if (newHealth<0) this.health = 0;
        else this.health = newHealth;
    }

    public void incrementAttack(int increment){
        int newAttack = this.attack +increment;
        if (newAttack>20) this.attack = 20;
        else if (newAttack<0) this.attack = 0;
        else this.attack = newAttack;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

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

    public void incrementExp(int increment) { this.exp += increment; }
}
