import java.util.List;

public class Pokemon {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attack;

    private final PokemonType type;

    private final List<String> ascii;

    public Pokemon(
            String name,
            int hp,
            int attack,
            PokemonType type,
            List<String> ascii
    ) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.type = type;
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

    public PokemonType getType() {
        return type;
    }

    public List<String> getAscii() {
        return ascii;
    }

    public void damage(int dmg) {
        hp -= dmg;

        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean alive() {
        return hp > 0;
    }
}