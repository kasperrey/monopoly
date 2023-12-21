import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Bord {
    private final ArrayList<Kaart> kaarten;

    public Bord() {
        kaarten = new ArrayList<>();
        kaarten.add(new Kaart(60000, 7500, 1, Optional.empty()));
        kaarten.add(new Kaart(60000, 7500, 3, Optional.empty()));
        kaarten.add(new Kaart(100000, 12500, 6, Optional.empty()));
        kaarten.add(new Kaart(100000, 12500, 8, Optional.empty()));
        kaarten.add(new Kaart(120000, 15000, 9, Optional.empty()));
        kaarten.add(new Kaart(140000, 17500, 11, Optional.empty()));
        kaarten.add(new Kaart(140000, 17500, 13, Optional.empty()));
        kaarten.add(new Kaart(160000, 20000, 14, Optional.empty()));
        kaarten.add(new Kaart(180000, 22500, 16, Optional.empty()));
        kaarten.add(new Kaart(180000, 22500, 18, Optional.empty()));
        kaarten.add(new Kaart(200000, 25000, 19, Optional.empty()));
        kaarten.add(new Kaart(350000, 43750, 38, Optional.empty()));
        kaarten.add(new Kaart(400000, 50000, 39, Optional.empty()));
        kaarten.add(new Kaart(100000, 15000, 5, Optional.empty()));
        kaarten.add(new Kaart(100000, 15000, 15, Optional.empty()));
        kaarten.add(new Kaart(100000, 15000, 25, Optional.empty()));
        kaarten.add(new Kaart(100000, 15000, 35, Optional.empty()));
    }

    public Optional<Kaart> getKaart(int pos) {
        for (Kaart kaart: kaarten) {
            if (kaart.pos() == pos) {
                return Optional.of(kaart);
            }
        }
        return Optional.empty();
    }

    public Kaart veranderBezet(Optional<Speler> speler, int pos) {
        for (int kaart = 0; kaart < kaarten.size(); kaart++) {
            if (kaarten.get(kaart).pos() == pos) {
                if (kaart > kaarten.size()-4) {
                    kaarten.set(kaart, kaarten.get(kaart).setBezet(speler));
                    return kaarten.get(kaart);
                } else {
                    return veranderBezetVervoer(speler, pos);
                }
            }
        }
        return new Kaart(0, 0, -1, Optional.empty());
    }

    private Kaart veranderBezetVervoer(Optional<Speler> speler, int pos) {
        if (speler.isPresent()) {
            for (int kaart = kaarten.size() - 4; kaart < kaarten.size(); kaart++) {
                if (kaarten.get(kaart).pos() == pos) {
                    kaarten.set(kaart, kaarten.get(kaart).setBezet(speler));
                }
            }
            treinPrijs(speler.get());
        } else {
            for (int kaart = kaarten.size() - 4; kaart < kaarten.size(); kaart++) {
                if (kaarten.get(kaart).bezet().isPresent()) {
                    treinPrijs(kaarten.get(kaart).bezet().get());
                    return kaarten.get(kaart);
                }
            }
        }
        return new Kaart(0, 0, -1, Optional.empty());
    }

    private int aantalVanSpeler(Speler speler) {
        int aantal = 0;
        for (Kaart kaart: kaarten) {
            if (kaart.bezet().isPresent()) {
                if (Objects.equals(kaart.bezet().get(), speler)) {
                    aantal++;
                }
            }
        }
        return aantal;
    }

    private void treinPrijs(Speler speler) {
        int aantal = aantalVanSpeler(speler);
        for (int kaartI = kaarten.size() - 4; kaartI < kaarten.size(); kaartI++) {
            Kaart kaart = kaarten.get(kaartI);
            if (kaart.bezet().isPresent()) {
                if (kaart.bezet().get() == speler) {
                    kaarten.set(kaartI, kaart.setHuur(15000 * (int) Math.pow(2, aantal)));
                }
            }
        }
    }
}
