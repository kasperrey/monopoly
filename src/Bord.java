import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Bord {
    private final ArrayList<Kaart> kaarten;
    private final ArrayList<SpecialeKaart> specialeKaarten;

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
        specialeKaarten = new ArrayList<>();
        specialeKaarten.add(new SpecialeKaart(12, 150000, 4000, Optional.empty()));
        specialeKaarten.add(new SpecialeKaart(28, 150000, 4000, Optional.empty()));
    }

    public Optional<Kaart> getKaart(int pos) {
        for (Kaart kaart: kaarten) {
            if (kaart.pos() == pos) {
                return Optional.of(kaart);
            }
        }
        return Optional.empty();
    }

    public Optional<SpecialeKaart> getSpecialeKaart(int pos) {
        for (SpecialeKaart specialeKaart: specialeKaarten) {
            if (specialeKaart.position() == pos) {
                return Optional.of(specialeKaart);
            }
        }
        return Optional.empty();
    }

    public void koopSpeciaalBezit(Speler speler, int pos) {
        for (int specialeKaart = 0; specialeKaart < specialeKaarten.size(); specialeKaart++) {
            if (specialeKaarten.get(specialeKaart).position() == pos) {
                specialeKaarten.set(specialeKaart, specialeKaarten.get(specialeKaart).setBezet(Optional.of(speler)));
            }
        }
        speciaalPrijs();
    }

    public int verkoopSpeciaalBezit(int pos) {
        for (int specialeKaart = 0; specialeKaart < specialeKaarten.size(); specialeKaart++) {
            SpecialeKaart kaart = specialeKaarten.get(specialeKaart);
            if (kaart.position() == pos) {
                specialeKaarten.set(specialeKaart, kaart.setBezet(Optional.empty()));
                speciaalPrijs();
                return kaart.prijs();
            }
        }
        return 0;
    }

    public void koopBezet(Speler speler, int pos) {
        for (int kaart = 0; kaart < kaarten.size(); kaart++) {
            if (kaarten.get(kaart).pos() == pos) {
                if (kaart > kaarten.size()-4) {
                    kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.of(speler)));
                } else {
                    koopBezetVervoer(speler, pos);
                }
            }
        }
    }

    public int verkoopBezet(Speler speler, int pos) {
        for (int kaart = 0; kaart < kaarten.size(); kaart++) {
            if (kaarten.get(kaart).pos() == pos) {
                if (kaart > kaarten.size()-4) {
                    kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.empty()));
                    return kaarten.get(kaart).prijs();
                } else {
                    return verkoopBezetVervoer(speler, pos);
                }
            }
        }
        return 0;
    }

    private int verkoopBezetVervoer(Speler speler, int pos) {
        for (int kaart = kaarten.size() - 4; kaart < kaarten.size(); kaart++) {
            if (kaarten.get(kaart).pos() == pos) {
                kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.empty()));
            }
        }
        treinPrijs(speler);
        return kaarten.get(kaarten.size()-1).prijs();
    }

    private void koopBezetVervoer(Speler speler, int pos) {
        for (int kaart = kaarten.size() - 4; kaart < kaarten.size(); kaart++) {
            if (kaarten.get(kaart).pos() == pos) {
                kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.of(speler)));
            }
        }
        treinPrijs(speler);
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

    private void speciaalPrijs() {
        boolean allebij = allebij();
        for (int kaartI = 0; kaartI < specialeKaarten.size(); kaartI++) {
            SpecialeKaart specialeKaart = specialeKaarten.get(kaartI);
            if (specialeKaart.bezitter().isPresent()) {
                specialeKaarten.set(kaartI, specialeKaart.setMaal(allebij ? 10000: 4000));
            } else {
                specialeKaarten.set(kaartI, specialeKaart.setMaal(4000));
            }
        }
    }

    private boolean allebij() {
        return (specialeKaarten.get(0).bezitter() == specialeKaarten.get(1).bezitter());
    }

    public int betalen(int pos) {
        if (pos == 4)
            return 200000;
        if (pos == 38)
            return 100000;
        return 0;
    }
}
