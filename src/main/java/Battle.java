import java.util.Random;

public class Battle {

    private Pokemon player;
    private Pokemon enemy;

    public Battle(
            Pokemon player,
            Pokemon enemy) {

        this.player = player;
        this.enemy = enemy;
    }

    public void start() throws Exception {

        while(player.alive() &&
              enemy.alive()) {

            draw();

            System.out.println();
            System.out.println(
                    "[A] ATAK"
            );

            int key =
                    System.in.read();

            if(key == 'a' ||
               key == 'A') {

                attackAnimation();

                enemy.damage(
                        player.getAttack()
                );

                if(enemy.alive()) {

                    player.damage(
                            enemy.getAttack()
                    );
                }
            }
        }

        draw();

        System.out.println();

        if(player.alive()) {

            System.out.println(
                    "WYGRANA!"
            );
        }
        else {

            System.out.println(
                    "PRZEGRANA!"
            );
        }
    }

    private void draw() {

        for(int i=0;i<30;i++) {
            System.out.println();
        }

        System.out.println(
                "================================"
        );

        System.out.println(
                player.getName()
                + " HP: "
                + player.getHp()
        );

        System.out.println();

        printPokemon(
                player.getName()
        );

        System.out.println();
        System.out.println(
                "-------------- VS --------------"
        );

        System.out.println();

        printPokemon(
                enemy.getName()
        );

        System.out.println();

        System.out.println(
                enemy.getName()
                + " HP: "
                + enemy.getHp()
        );

        System.out.println(
                "================================"
        );
    }

    private void attackAnimation()
            throws Exception {

        for(int i=0;i<15;i++) {

            System.out.print(">");

            Thread.sleep(30);
        }

        System.out.println(
                " BOOM!"
        );

        Thread.sleep(300);
    }

    private void printPokemon(
            String name) {

        if(name.equals("Pikachu")) {

            System.out.println(
                    " (\\__/)"
            );

            System.out.println(
                    " (o.o )"
            );

            System.out.println(
                    " /|_|\\\\"
            );
        }

        else if(name.equals(
                "Charmander")) {

            System.out.println(
                    "  /^\\\\"
            );

            System.out.println(
                    " (o o)"
            );

            System.out.println(
                    " / V \\\\"
            );
        }

        else if(name.equals(
                "Bulbasaur")) {

            System.out.println(
                    "  ___"
            );

            System.out.println(
                    " (o o)"
            );

            System.out.println(
                    " / V \\\\"
            );
        }

        else if(name.equals(
                "Squirtle")) {

            System.out.println(
                    " _____"
            );

            System.out.println(
                    "( o o )"
            );

            System.out.println(
                    " / ^ \\\\"
            );
        }

        else {

            System.out.println(
                    " [???]"
            );
        }
    }
}