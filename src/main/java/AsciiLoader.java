import java.io.*;
import java.util.*;

public class AsciiLoader {

    public static List<String> load(String name) {

        try {

            String file = "/ascii/" + name.toLowerCase() + ".txt";

            InputStream is = AsciiLoader.class.getResourceAsStream(file);

            if (is == null) {
                return List.of("[NO ASCII]");
            }

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is));

            List<String> lines = new ArrayList<>();

            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            return lines;

        } catch (Exception e) {
            return List.of("[ASCII ERROR]");
        }
    }
}