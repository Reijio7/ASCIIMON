import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Main {

    public static void main(String[] args)
            throws Exception {

        Screen screen =
                new DefaultTerminalFactory()
                        .createScreen();

        screen.startScreen();

        MainMenu menu =
                new MainMenu();

        int choice =
                menu.show(screen);

        screen.stopScreen();

        if(choice == 2) {
            System.exit(0);
        }

        StarterMenu starter =
                new StarterMenu();

        Pokemon player =
                starter.chooseStarter();

        System.out.println(
                "Wybrano: "
                + player.getName()
        );

        
    }
}