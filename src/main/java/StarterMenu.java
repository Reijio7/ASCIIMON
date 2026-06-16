import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class StarterMenu {

    private final String[] starters = {
            "Bulbasaur",
            "Charmander",
            "Squirtle",
            "Pikachu"
    };

    public Pokemon chooseStarter() throws Exception {

        Screen screen =
                new DefaultTerminalFactory()
                        .createScreen();

        screen.startScreen();

        int selected = 0;

        while(true) {

            screen.clear();

            drawText(
                    screen,
                    2,
                    1,
                    "WYBIERZ STARTERA"
            );

            drawText(
                    screen,
                    2,
                    2,
                    "STRZALKI = WYBOR"
            );

            drawText(
                    screen,
                    2,
                    3,
                    "ENTER = POTWIERDZENIE"
            );

            for(int i = 0; i < starters.length; i++) {

                String text;

                if(i == selected) {
                    text = "> " + starters[i];
                }
                else {
                    text = "  " + starters[i];
                }

                drawText(
                        screen,
                        4,
                        6 + i,
                        text
                );
            }

            screen.refresh();

            KeyStroke key =
                    screen.readInput();

            if(key.getKeyType()
                    == KeyType.ArrowUp) {

                selected--;

                if(selected < 0) {
                    selected =
                            starters.length - 1;
                }
            }

            if(key.getKeyType()
                    == KeyType.ArrowDown) {

                selected++;

                if(selected >= starters.length) {
                    selected = 0;
                }
            }

            if(key.getKeyType()
                    == KeyType.Enter) {

                screen.stopScreen();

                if(selected == 0) {

                    return new Pokemon(
                            "Bulbasaur",
                            45,
                            49
                    );
                }

                if(selected == 1) {

                    return new Pokemon(
                            "Charmander",
                            39,
                            52
                    );
                }

                if(selected == 2) {

                    return new Pokemon(
                            "Squirtle",
                            44,
                            48
                    );
                }

                return new Pokemon(
                        "Pikachu",
                        35,
                        55
                );
            }
        }
    }

    private void drawText(
            Screen screen,
            int x,
            int y,
            String text) {

        for(int i = 0; i < text.length(); i++) {

            screen.setCharacter(
                    x + i,
                    y,
                    new TextCharacter(
                            text.charAt(i)
                    )
            );
        }
    }
}