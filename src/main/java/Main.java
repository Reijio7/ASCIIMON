import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        Settings.load();

        boolean introPlayed = false;

        while (true) {

            Screen screen =
                    new DefaultTerminalFactory().createScreen();

            screen.startScreen();

            MusicPlayer menuMusic = new MusicPlayer();

            if (!Settings.muted) {
                menuMusic.play("menu.mp3");
            }

            MainMenu menu = new MainMenu();
            int choice = menu.show(screen);

            menuMusic.stop();

            if (choice == 1) {
                SettingsMenu settings = new SettingsMenu();
                settings.show(screen);
                continue;
            }

            if (choice == 2) {
                screen.stopScreen();
                return;
            }

            screen.stopScreen();

            if (!introPlayed) {

                IntroController controller =
                        new IntroController();

                Thread introThread =
                        new Thread(() ->
                                IntroPlayer.play(
                                        "intro.mp4",
                                        controller
                                )
                        );

                introThread.start();

                while (!controller.isSkipped()) {

                    int c =
                            System.in.available() > 0
                                    ? System.in.read()
                                    : -1;

                    if (c == '\n' || c == ' ') {
                        controller.skip();
                        break;
                    }

                    Thread.sleep(30);
                }

                introThread.join();

                introPlayed = true;
            }

            boolean playAgain = true;

            while (playAgain) {

                screen =
                        new DefaultTerminalFactory()
                                .createScreen();

                screen.startScreen();

                List<Pokemon> pokemons =
                        PokemonLoader.load();

                StarterMenu starterMenu =
                        new StarterMenu();

                Pokemon player =
                        starterMenu.chooseStarter(
                                screen,
                                pokemons
                        );

                Random r = new Random();

                Pokemon enemy =
                        pokemons.get(
                                r.nextInt(pokemons.size())
                        );

                Battle battle =
                        new Battle(
                                player,
                                enemy,
                                screen
                        );

                battle.start();

                PostBattleMenu post =
                        new PostBattleMenu();

                int result =
                        post.show(screen);

                screen.stopScreen();

                if (result == 1) {
                    continue; 
                }

                if (result == 2) {
                    playAgain = false; 
                }
            }
        }
    }
}