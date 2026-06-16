import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class SettingsMenu {

    private int selected = 0;

    private final String[] options = {
            "MUTE",
            "BACK"
    };

    public void show(Screen screen) throws Exception {

        while (true) {

            screen.clear();

            draw(screen, 5, 2, "SETTINGS");

            String muteValue = Settings.muted ? "ON" : "OFF";

            draw(screen, 5, 5,
                    (selected == 0 ? "> " : "  ")
                    + "MUTE : " + muteValue
            );

            draw(screen, 5, 7,
                    (selected == 1 ? "> " : "  ")
                    + "BACK"
            );

            screen.refresh();

            KeyStroke key = screen.pollInput();

            if (key != null) {

                if (key.getKeyType() == KeyType.ArrowUp) {
                    selected--;
                    if (selected < 0) selected = 1;
                }

                if (key.getKeyType() == KeyType.ArrowDown) {
                    selected++;
                    if (selected > 1) selected = 0;
                }

                if (key.getKeyType() == KeyType.Enter) {

                    if (selected == 0) {
                        Settings.muted = !Settings.muted;
                        Settings.save();
                    }

                    if (selected == 1) {
                        return;
                    }
                }
            }

            Thread.sleep(40);
        }
    }

    private void draw(Screen s, int x, int y, String text) {

        for (int i = 0; i < text.length(); i++) {
            s.setCharacter(x + i, y,
                    new TextCharacter(text.charAt(i),
                            TextColor.ANSI.WHITE,
                            TextColor.ANSI.BLACK));
        }
    }
}