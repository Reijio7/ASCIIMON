import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        MainMenu menu = new MainMenu();
        int choice = menu.show(screen);

        if (choice == 2) {
            screen.stopScreen();
            return;
        }



        IntroController controller = new IntroController();

        screen.stopScreen();

        Thread introThread = new Thread(() ->
                IntroPlayer.play("intro.mp4", controller)
        );

        introThread.start();
        while (!controller.isSkipped()) {



            int c = System.in.available() > 0 ? System.in.read() : -1;

            if (c == '\n' || c == ' ') {
                controller.skip();
                break;
            }

            Thread.sleep(30);
        }

        introThread.join();


        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();


        List<Pokemon> pokemons = PokemonLoader.load();

        StarterMenu starterMenu = new StarterMenu();
        Pokemon player = starterMenu.chooseStarter(screen, pokemons);

        Random r = new Random();
        Pokemon enemy;

        do {
            enemy = pokemons.get(r.nextInt(pokemons.size()));
        } while (enemy.getName().equals(player.getName()));





        Battle battle = new Battle(player, enemy, screen);
        battle.start();

        screen.stopScreen();
    }
}