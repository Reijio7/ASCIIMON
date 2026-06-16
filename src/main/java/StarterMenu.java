import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import java.util.List;

public class StarterMenu {

    public Pokemon chooseStarter(Screen screen, List<Pokemon> pokemons)
            throws Exception {

        int selected = 0;

        while (true) {

            screen.clear();

            int w = screen.getTerminalSize().getColumns();

            drawText(screen, w/2 - 8, 2, "CHOOSE POKEMON", TextColor.ANSI.GREEN);

            for (int i = 0; i < pokemons.size(); i++) {

                String line = (i == selected ? "► " : "  ") + pokemons.get(i).getName();

                drawText(
                        screen,
                        w/2 - 10,
                        5 + i * 2,
                        line,
                        i == selected ? TextColor.ANSI.YELLOW : TextColor.ANSI.WHITE
                );
            }

            screen.refresh();

            KeyStroke k = screen.pollInput();

            if (k != null) {

                if (k.getKeyType() == KeyType.ArrowUp) selected--;
                if (k.getKeyType() == KeyType.ArrowDown) selected++;

                if (selected < 0) selected = pokemons.size() - 1;
                if (selected >= pokemons.size()) selected = 0;

                if (k.getKeyType() == KeyType.Enter) {
                    return pokemons.get(selected);
                }
            }

            Thread.sleep(40);
        }
    }

    private void drawText(Screen s, int x, int y, String text, TextColor color) {

        for (int i = 0; i < text.length(); i++) {

            s.setCharacter(
                    x + i,
                    y,
                    new TextCharacter(text.charAt(i), color, TextColor.ANSI.BLACK)
            );
        }
    }
}