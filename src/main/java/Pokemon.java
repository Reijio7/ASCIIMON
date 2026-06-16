import java.util.List;

public class Pokemon {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attack;
    private final List<String> ascii;

    public Pokemon(String name, int hp, int attack, List<String> ascii) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.ascii = ascii;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public List<String> getAscii() {
        return ascii;
    }

    public void damage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public boolean alive() {
        return hp > 0;
    }
}