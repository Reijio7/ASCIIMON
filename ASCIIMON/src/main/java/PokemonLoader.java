import java.io.*;
import java.util.*;

public class PokemonLoader {

    public static List<Pokemon> load() throws Exception {

        List<Pokemon> list = new ArrayList<>();

        InputStream is =
            PokemonLoader.class
                .getResourceAsStream("/pokemon.txt");

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(is));

        String line;

        while((line = br.readLine()) != null) {

            line = line.trim();

            if(line.isEmpty()) {
                continue;
            }

            String[] data = line.split(",");

            if(data.length < 3) {

                System.out.println(
                    "Bledna linia: " + line
                );

                continue;
            }

            list.add(
                new Pokemon(
                    data[0],
                    Integer.parseInt(data[1]),
                    Integer.parseInt(data[2])
                )
            );
        }

        return list;
    }
}
