public class TypeChart {

    public static double multiplier(
            PokemonType attacker,
            PokemonType defender
    ) {

        switch (attacker) {

            case FIRE:

                if (defender == PokemonType.GRASS)
                    return 1.25;

                if (defender == PokemonType.WATER)
                    return 0.75;

                break;

            case WATER:

                if (defender == PokemonType.FIRE)
                    return 1.25;

                if (defender == PokemonType.GRASS)
                    return 0.75;

                break;

            case GRASS:

                if (defender == PokemonType.WATER)
                    return 1.25;

                if (defender == PokemonType.FIRE)
                    return 0.75;

                break;

            case ELECTRIC:

                if (defender == PokemonType.WATER)
                    return 1.25;

                if (defender == PokemonType.GRASS)
                    return 0.75;

                break;
        }

        return 1.0;
    }
}