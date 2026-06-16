import java.io.*;

public class Settings {

    public static boolean muted = false;

    private static final String FILE = "settings.txt";

    public static void load() {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.startsWith("muted=")) {
                    muted = Boolean.parseBoolean(line.split("=")[1]);
                }
            }

        } catch (Exception e) {
            System.out.println("No settings file, using default.");
        }
    }

    public static void save() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {

            pw.println("muted=" + muted);

        } catch (Exception e) {
            System.out.println("Cannot save settings");
        }
    }
}