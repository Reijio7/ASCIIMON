public class Pokemon {

    private String name;
    private int hp;
    private int attack;

    public Pokemon(String name, int hp, int attack) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public void damage(int dmg) {
        hp -= dmg;

        if(hp < 0)
            hp = 0;
    }

    public boolean alive() {
        return hp > 0;
    }
}
