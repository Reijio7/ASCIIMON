import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class PostBattleMenu {

    private int selected = 0;

    public int show(Screen screen) throws Exception {

        while (true) {

            screen.clear();

            draw(screen, 10, 5, "=== KONIEC WALKI ===");

            draw(
                    screen,
                    10,
                    8,
                    selected == 0
                            ? "> PLAY AGAIN"
                            : "  PLAY AGAIN"
            );

            draw(
                    screen,
                    10,
                    10,
                    selected == 1
                            ? "> RETURN TO MENU"
                            : "  RETURN TO MENU"
            );

            screen.refresh();

            KeyStroke key = screen.readInput();

            if (key.getKeyType() == KeyType.ArrowUp) {
                selected = Math.max(0, selected - 1);
            }

            if (key.getKeyType() == KeyType.ArrowDown) {
                selected = Math.min(1, selected + 1);
            }

            if (key.getKeyType() == KeyType.Enter) {
                return selected + 1;
            }
        }
    }

    private void draw(
            Screen screen,
            int x,
            int y,
            String text
    ) {

        for (int i = 0; i < text.length(); i++) {

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