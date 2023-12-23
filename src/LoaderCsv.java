import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoaderCsv {
    private static List<String> loadFile(String fileName) throws IOException {
        List<String> lijnen = Files.readAllLines(Path.of(fileName));
        return lijnen.subList(1, lijnen.size());
    }

    public static ArrayList<SpecialeKaart> leesSpeciaal(String map) throws IOException {
        ArrayList<SpecialeKaart> specialeKaarten = new ArrayList<>();
        for (String line: loadFile(map+"speciaal.csv")) {
            String[] woord = line.split(",");
            specialeKaarten.add(new SpecialeKaart(Integer.parseInt(woord[1]), Integer.parseInt(woord[2]), Integer.parseInt(woord[3]),
                    Optional.empty(), new int[] {Integer.parseInt(woord[3]), Integer.parseInt(woord[4])}));
        }
        return specialeKaarten;
    }

    public static ArrayList<Kaart> leesKaart(String map) throws IOException {
        ArrayList<Kaart> kaarten = new ArrayList<>();
        for (String line: loadFile(map+"straten.csv")) {
            String[] woord = line.split(",");
            kaarten.add(new Kaart(Integer.parseInt(woord[3]), Integer.parseInt(woord[5]), Integer.parseInt(woord[2]),
                    Optional.empty(), new int[] {Integer.parseInt(woord[5]), Integer.parseInt(woord[6]), Integer.parseInt(woord[7]),
                    Integer.parseInt(woord[8]), Integer.parseInt(woord[9]), Integer.parseInt(woord[10])}, woord[1]));
        }
        return kaarten;
    }

    public static ArrayList<Tax> leesTax(String map) throws IOException {
        ArrayList<Tax> taxses = new ArrayList<>();
        for (String line: loadFile(map+"tax.csv")) {
            String[] woord = line.split(",");
            taxses.add(new Tax(Integer.parseInt(woord[1]), Integer.parseInt(woord[2])));
        }
        return taxses;
    }

    public static ArrayList<TreinKaart> leesTrein(String map) throws IOException {
        ArrayList<TreinKaart> treinen = new ArrayList<>();
        for (String line: loadFile(map+"treinen.csv")) {
            String[] woord = line.split(",");
            treinen.add(new TreinKaart(Integer.parseInt(woord[2]), Integer.parseInt(woord[3]), Integer.parseInt(woord[1]), Optional.empty(),
                    new int[] {Integer.parseInt(woord[3]), Integer.parseInt(woord[4]), Integer.parseInt(woord[5]), Integer.parseInt(woord[6])}));
        }
        return treinen;
    }
}
