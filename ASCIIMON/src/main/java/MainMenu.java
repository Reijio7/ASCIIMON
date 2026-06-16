import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class MainMenu {

    private final String[] options = {
            "START GAME",
            "OPTIONS",
            "EXIT"
    };

    private final int STAR_COUNT = 120;

    private final int[] starX =
            new int[STAR_COUNT];

    private final int[] starY =
            new int[STAR_COUNT];

    private boolean initialized = false;

    private long startTime =
            System.currentTimeMillis();

    private void initStars(Screen screen) {

        if(initialized)
            return;

        initialized = true;

        int width =
                screen.getTerminalSize()
                        .getColumns();

        int height =
                screen.getTerminalSize()
                        .getRows();

        for(int i=0;i<STAR_COUNT;i++) {

            starX[i] =
                    (int)(Math.random()*width);

            starY[i] =
                    (int)(Math.random()*height);
        }
    }

    private void drawStars(Screen screen) {

        int width =
                screen.getTerminalSize()
                        .getColumns();

        int height =
                screen.getTerminalSize()
                        .getRows();

        for(int i=0;i<STAR_COUNT;i++) {

            char c;

            double r =
                    Math.random();

            if(r < 0.7)
                c='.';
            else if(r < 0.9)
                c='*';
            else
                c='+';

            TextColor color;

            double colorRoll =
                    Math.random();

            if(colorRoll < 0.7)
                color =
                        TextColor.ANSI.WHITE;
            else if(colorRoll < 0.9)
                color =
                        TextColor.ANSI.CYAN;
            else
                color =
                        TextColor.ANSI.BLUE;

            screen.setCharacter(
                    starX[i],
                    starY[i],
                    new TextCharacter(
                            c,
                            color,
                            TextColor.ANSI.BLACK
                    )
            );

            if(Math.random() < 0.03) {

                starX[i] =
                        (int)(Math.random()*width);

                starY[i] =
                        (int)(Math.random()*height);
            }
        }
    }

    public int show(Screen screen)
            throws Exception {

        initStars(screen);

        int selected = 0;

        while(true) {

            screen.clear();

            int width =
                    screen.getTerminalSize()
                            .getColumns();

            int height =
                    screen.getTerminalSize()
                            .getRows();

            int centerY =
                    height / 2;

            drawStars(screen);

            drawFrame(
                    screen,
                    0,
                    0,
                    width - 1,
                    height - 1
            );

            String[] logo = {

" █████╗ ███████╗ ██████╗██╗██╗███╗   ███╗ ██████╗ ███╗   ██╗",
"██╔══██╗██╔════╝██╔════╝██║██║████╗ ████║██╔═══██╗████╗  ██║",
"███████║███████╗██║     ██║██║██╔████╔██║██║   ██║██╔██╗ ██║",
"██╔══██║╚════██║██║     ██║██║██║╚██╔╝██║██║   ██║██║╚██╗██║",
"██║  ██║███████║╚██████╗██║██║██║ ╚═╝ ██║╚██████╔╝██║ ╚████║",
"╚═╝  ╚═╝╚══════╝ ╚═════╝╚═╝╚═╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝"

            };

            int logoY =
                    centerY - 14;

            for(int i=0;i<logo.length;i++) {

                drawText(
                        screen,
                        centerX(
                                width,
                                logo[i]
                        ),
                        logoY + i,
                        logo[i],
                        TextColor.ANSI.GREEN
                );
            }



            int menuY =
                    centerY - 1;

            for(int i=0;i<options.length;i++) {

                String text;

                if(i == selected)
                    text =
                            "► " +
                            options[i];
                else
                    text =
                            "  " +
                            options[i];

                drawText(
                        screen,
                        centerX(
                                width,
                                text
                        ),
                        menuY + i * 2,
                        text,
                        i == selected
                                ? TextColor.ANSI.YELLOW
                                : TextColor.ANSI.WHITE
                );
            }

            boolean blink =

                    ((System.currentTimeMillis()
                            - startTime)
                            / 500)
                            % 2 == 0;

            if(blink) {

                String press =
                        "PRESS ENTER";

                drawText(
                        screen,
                        centerX(
                                width,
                                press
                        ),
                        menuY + 9,
                        press,
                        TextColor.ANSI.CYAN
                );
            }

            String info =
                    "ARROWS = MOVE | ENTER = SELECT";

            drawText(
                    screen,
                    centerX(
                            width,
                            info
                    ),
                    height - 3,
                    info,
                    TextColor.ANSI.GREEN
            );

            screen.refresh();

            KeyStroke key =
                    screen.pollInput();

            if(key != null) {

                if(key.getKeyType()
                        == KeyType.ArrowUp) {

                    selected--;

                    if(selected < 0)
                        selected =
                                options.length - 1;
                }

                if(key.getKeyType()
                        == KeyType.ArrowDown) {

                    selected++;

                    if(selected >= options.length)
                        selected = 0;
                }

                if(key.getKeyType()
                        == KeyType.Enter) {

                    return selected;
                }
            }

            Thread.sleep(33);
        }
    }

    private int centerX(
            int width,
            String text) {

        return
                (width - text.length()) / 2;
    }

    private void drawFrame(
            Screen screen,
            int x,
            int y,
            int w,
            int h) {

        for(int i=x;i<=w;i++) {

            screen.setCharacter(
                    i,
                    y,
                    new TextCharacter('=')
            );

            screen.setCharacter(
                    i,
                    h,
                    new TextCharacter('=')
            );
        }

        for(int i=y;i<=h;i++) {

            screen.setCharacter(
                    x,
                    i,
                    new TextCharacter('|')
            );

            screen.setCharacter(
                    w,
                    i,
                    new TextCharacter('|')
            );
        }
    }

    private void drawText(
            Screen screen,
            int x,
            int y,
            String text,
            TextColor color) {

        for(int i=0;i<text.length();i++) {

            screen.setCharacter(
                    x + i,
                    y,
                    new TextCharacter(
                            text.charAt(i),
                            color,
                            TextColor.ANSI.BLACK
                    )
            );
        }
    }
}
