import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TextCharacter;

import java.util.Random;

public class Battle {

    private final Pokemon player;
    private final Pokemon enemy;
    private final Screen screen;
    private final Random random = new Random();

    private String lastAction = "";
    private String effectMessage = "";

    private int specialCharge = 0;

    private static final int MAX_SPECIAL = 100;
    private static final int CHARGE_PER_ATTACK = 25;

    public Battle(Pokemon player, Pokemon enemy, Screen screen) {
        this.player = player;
        this.enemy = enemy;
        this.screen = screen;
    }

    public void start() throws Exception {

        while (player.alive() && enemy.alive()) {

            draw();
            screen.refresh();

            KeyStroke key;

            do {
                key = screen.pollInput();
                Thread.sleep(20);
            } while (key == null);

            if (key.getKeyType() == KeyType.Character) {

                char c = key.getCharacter();

                if (c == 'a' || c == 'A') {
                    playerAttack(false);
                }

                if (c == 's' || c == 'S') {
                    playerAttack(true);
                }
            }
        }

        lastAction = player.alive() ? "WYGRANA!" : "PRZEGRANA!";

        draw();
        screen.refresh();

        Thread.sleep(1500);
    }

    private void playerAttack(boolean special) throws Exception {

        int dmg;

        if (special) {

            if (specialCharge < MAX_SPECIAL) {

                lastAction = "Special nie jest naladowany!";

                draw();
                screen.refresh();

                return;
            }

            specialCharge = 0;

            dmg = player.getAttack() + 10 + random.nextInt(10);
            lastAction = player.getName() + " SPECJAL za " + dmg;

        } else {

            dmg = player.getAttack() + random.nextInt(5);

            specialCharge += CHARGE_PER_ATTACK;

            if (specialCharge > MAX_SPECIAL) {
                specialCharge = MAX_SPECIAL;
            }

            lastAction = player.getName() + " atakuje za " + dmg;
        }

        double bonus =
                TypeChart.multiplier(
                        player.getType(),
                        enemy.getType()
                );

        dmg = (int)(dmg * bonus);

        if (bonus == 1.25) {
            
            effectMessage = "SUPER EFFECTIVE!";

            lastAction =
                    player.getName()
                    + " hits for "
                    + dmg + " dmg";

        }
        else if (bonus == 0.75) {

            effectMessage = "NOT VERY EFFECTIVE...";

            lastAction =
                    player.getName()
                    + " hits for "
                    + dmg + " dmg";

        }
        else {

            lastAction =
                    player.getName()
                    + " hits for "
                    + dmg + " dmg";

        }

        enemy.damage(dmg);

        draw();
        screen.refresh();

        Thread.sleep(400);

        if (enemy.alive()) {
            enemyTurn();
        }
    }

    private void enemyTurn() throws Exception {

        int dmg = enemy.getAttack() + random.nextInt(6);

        double bonus =
                TypeChart.multiplier(
                        enemy.getType(),
                        player.getType()
                );

        dmg = (int)(dmg * bonus);

        if (bonus == 1.25) {

            effectMessage = "SUPER EFFECTIVE!";

            lastAction =
                    enemy.getName()
                    + " attacks for "
                    + dmg + " dmg";
        }
        else if (bonus == 0.75) {

            effectMessage = "NOT VERY EFFECTIVE...";

            lastAction =
                    enemy.getName()
                    + " attacks for "
                    + dmg + " dmg";
        }
        else {

            effectMessage = "";

            lastAction =
                    enemy.getName()
                    + " attacks for "
                    + dmg + " dmg";
        }

        player.damage(dmg);

        draw();
        screen.refresh();

        Thread.sleep(500);
    }

    private void draw() throws Exception {

        screen.clear();

        int w = screen.getTerminalSize().getColumns();

        drawText(
            2,
            1,
            "YOU: " + player.getName()
            + " [" + player.getType() + "]"
        );
        drawText(2, 2, hpBar(player));
        drawText(2, 3, specialBar());



        int playerX = 2;
        int playerY = 4;

        drawAscii(playerX, playerY, player);

        int playerAsciiHeight = player.getAscii().size();

        int ex = w / 2;

        drawText(
            ex,
            1,
            "ENEMY: " + enemy.getName()
            + " [" + enemy.getType() + "]"
        );
        drawText(ex, 2, hpBar(enemy));

        drawAscii(ex, 4, enemy);

        int enemyAsciiHeight = enemy.getAscii().size();

        int uiY = Math.max(playerY + playerAsciiHeight, 15);

        String specialText =
            specialCharge >= MAX_SPECIAL
                    ? "[S] SPECIAL READY"
                    : "[S] SPECIAL " + specialCharge + "%";


        drawText(2, uiY, "[A] ATTACK   " + specialText);
        drawText(2, uiY + 2, lastAction);
        drawText(2, uiY + 3, effectMessage);


    }

    private String hpBar(Pokemon p) {

        int bars = (p.getHp() * 20) / p.getMaxHp();

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < 20; i++) {
            sb.append(i < bars ? "#" : "-");
        }

        sb.append("] ")
          .append(p.getHp())
          .append("/")
          .append(p.getMaxHp());

        return sb.toString();
    }




    private String specialBar() {

        int bars = (specialCharge * 10) / MAX_SPECIAL;

        StringBuilder sb = new StringBuilder();

        sb.append("SP [");

        for (int i = 0; i < 10; i++) {
            sb.append(i < bars ? "#" : "-");
        }

        sb.append("] ");
        sb.append(specialCharge);
        sb.append("%");

        return sb.toString();
    }


    private void drawAscii(int x, int y, Pokemon p) {

        int row = 0;

        for (String line : p.getAscii()) {
            drawText(x, y + row, line);
            row++;
        }
    }

    private void drawText(int x, int y, String text) {

        if (text == null) return;

        for (int i = 0; i < text.length(); i++) {

            screen.setCharacter(
                    x + i,

                    y,
                    new TextCharacter(text.charAt(i))
            );
        }
    }
}