package qihaoooooo.kyoho.model;

public class Boss {
    private int health;
    private int maxHealth;
    private int expValue;
    private boolean alive;
    private String name;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {this.health--;}

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getExpValue() {
        return expValue;
    }

    public void setExpValue(int expValue) {
        this.expValue = expValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boss(String name, int maxHealth, int expValue){
        this.alive = true;
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.expValue = expValue;
    }

    public Boss(int health, int maxHealth, int expValue, boolean alive, String name) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.expValue = expValue;
        this.alive = alive;
        this.name = name;
    }
}
