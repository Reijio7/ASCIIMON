import java.io.*;
import java.util.*;

public class PokemonLoader {

    public static List<Pokemon> load() {

        try {

            List<Pokemon> list = new ArrayList<>();

            InputStream is =
                    PokemonLoader.class.getResourceAsStream("/pokemon.txt");

            if (is == null) {
                throw new RuntimeException("Brak pokemon.txt");
            }

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is));

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] d = line.split(",");

                String name = d[0];
                int hp = Integer.parseInt(d[1]);
                int atk = Integer.parseInt(d[2]);

                list.add(
                        new Pokemon(
                                name,
                                hp,
                                atk,
                                AsciiLoader.load(name)
                        )
                );
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}