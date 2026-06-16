import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class MusicPlayer {

    private Player player;

    public void play(String path) {

        System.err.println("START THREAD");

        new Thread(() -> {

            try {

                player = new Player(new FileInputStream(path));

                player.play();

            } catch (Exception e) {

                System.err.println("BLAD MP3:");
                e.printStackTrace();
            }

        }).start();
    }

    public void stop() {

        if (player != null) {
            player.close();
        }
    }
}