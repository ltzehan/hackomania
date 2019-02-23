package qihaoooooo.kyoho.model;

public class Boss {

    private int health;
    private int maxHealth;
    private int expValue;
    private String name;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxhealth(int maxHealth) {
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
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.expValue = expValue;
    }
}
