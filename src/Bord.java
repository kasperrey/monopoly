import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Bord {
    private final ArrayList<Kaart> kaarten;
    private final ArrayList<SpecialeKaart> specialeKaarten;
    private final ArrayList<TreinKaart> treinKaarten;
    private final ArrayList<Tax> taxen;

    Bord() {
        String map = "bord data/";
        try {
            kaarten = LoaderCsv.leesKaart(map);
            specialeKaarten = LoaderCsv.leesSpeciaal(map);
            treinKaarten = LoaderCsv.leesTrein(map);
            taxen = LoaderCsv.leesTax(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Kaart> getKaart(int pos) {
        for (Kaart kaart: kaarten) {
            if (kaart.pos() == pos) {
                return Optional.of(kaart);
            }
        }
        return Optional.empty();
    }

    public Optional<TreinKaart> getTreinKaart(int pos) {
        for (TreinKaart treinKaart: treinKaarten) {
            if (treinKaart.pos() == pos) {
                return Optional.of(treinKaart);
            }
        }
        return Optional.empty();
    }

    public Optional<SpecialeKaart> getSpecialeKaart(int pos) {
        for (SpecialeKaart specialeKaart: specialeKaarten) {
            if (specialeKaart.pos() == pos) {
                return Optional.of(specialeKaart);
            }
        }
        return Optional.empty();
    }

    public void koopSpeciaalBezit(Speler speler, int pos) {
        for (int specialeKaart = 0; specialeKaart < specialeKaarten.size(); specialeKaart++) {
            if (specialeKaarten.get(specialeKaart).pos() == pos) {
                specialeKaarten.set(specialeKaart, specialeKaarten.get(specialeKaart).setBezet(Optional.of(speler)));
            }
        }
        speciaalPrijs();
    }

    public int verkoopSpeciaalBezit(Speler speler) {
        for (int specialeKaart = 0; specialeKaart < specialeKaarten.size(); specialeKaart++) {
            SpecialeKaart kaart = specialeKaarten.get(specialeKaart);
            if (Objects.equals(kaart.bezitter(), Optional.of(speler))) {
                specialeKaarten.set(specialeKaart, kaart.setBezet(Optional.empty()));
                speciaalPrijs();
                return kaart.prijs();
            }
        }
        return 0;
    }

    public void koopTreinBezit(Speler speler, int pos) {
        for (int treinKaart = 0; treinKaart < treinKaarten.size(); treinKaart++) {
            if (treinKaarten.get(treinKaart).pos() == pos) {
                treinKaarten.set(treinKaart, treinKaarten.get(treinKaart).setBezet(Optional.of(speler)));
            }
        }
        treinPrijs(speler);
    }

    public int verkoopTreinBezit(Speler speler) {
        for (int treinKaart = 0; treinKaart < treinKaarten.size(); treinKaart++) {
            if (Objects.equals(treinKaarten.get(treinKaart).bezitter(), Optional.of(speler))) {
                kaarten.set(treinKaart, kaarten.get(treinKaart).setBezet(Optional.empty()));
                return kaarten.get(treinKaart).prijs();
            }
        }
        treinPrijs(speler);
        return 0;
    }

    public void koopBezet(Speler speler, int pos) {
        for (int kaart = 0; kaart < kaarten.size(); kaart++) {
            if (kaarten.get(kaart).pos() == pos) {
                kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.of(speler)));
            }
        }
    }

    public int verkoopBezet(Speler speler) {
        for (int kaart = 0; kaart < kaarten.size(); kaart++) {
            if (Objects.equals(kaarten.get(kaart).bezitter(), Optional.of(speler))) {
                kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.empty()));
                return kaarten.get(kaart).prijs();
            }
        }
        return 0;
    }

    private int aantalVanSpeler(Speler speler) {
        int aantal = 0;
        for (TreinKaart treinKaart: treinKaarten) {
            if (Objects.equals(treinKaart.bezitter(), Optional.of(speler))) {
                aantal++;
            }
        }
        return aantal-1;
    }

    private void treinPrijs(Speler speler) {
        int aantal = aantalVanSpeler(speler);
        for (int kaartI = 0; kaartI < treinKaarten.size(); kaartI++) {
            TreinKaart treinKaart = treinKaarten.get(kaartI);
            if (Objects.equals(treinKaart.bezitter(), Optional.of(speler))) {
                treinKaarten.set(kaartI, treinKaart.setHuur(treinKaart.huures()[aantal]));
            }
        }
    }

    private void speciaalPrijs() {
        boolean allebij = allebij();
        for (int kaartI = 0; kaartI < specialeKaarten.size(); kaartI++) {
            SpecialeKaart specialeKaart = specialeKaarten.get(kaartI);
            if (specialeKaart.bezitter().isPresent()) {
                specialeKaarten.set(kaartI, specialeKaart.setMaal(allebij ? specialeKaart.malers()[1]: specialeKaart.malers()[0]));
            } else {
                specialeKaarten.set(kaartI, specialeKaart.setMaal(specialeKaart.malers()[0]));
            }
        }
    }

    public ArrayList<Kaart> getKaartenBySpeler(Speler speler) {
        ArrayList<Kaart> kaartenVanSpeler = new ArrayList<>();
        for (Kaart kaart: kaarten) {
            if (Objects.equals(kaart.bezitter(), Optional.of(speler))) {
                kaartenVanSpeler.add(kaart);
            }
        }
        return kaartenVanSpeler;
    }

    public ArrayList<TreinKaart> getTreinKaartenBySpeler(Speler speler) {
        ArrayList<TreinKaart> kaartenVanSpeler = new ArrayList<>();
        for (TreinKaart treinKaart: treinKaarten) {
            if (Objects.equals(treinKaart.bezitter(), Optional.of(speler))) {
                kaartenVanSpeler.add(treinKaart);
            }
        }
        return kaartenVanSpeler;
    }

    public ArrayList<SpecialeKaart> getSpecialeKaartenBySpeler(Speler speler) {
        ArrayList<SpecialeKaart> specialeKaartenVanSpeler = new ArrayList<>();
        for (SpecialeKaart specialeKaart: specialeKaarten) {
            if (Objects.equals(specialeKaart.bezitter(), Optional.of(speler))) {
                specialeKaartenVanSpeler.add(specialeKaart);
            }
        }
        return specialeKaartenVanSpeler;
    }

    private boolean allebij() {
        return (specialeKaarten.get(0).bezitter() == specialeKaarten.get(1).bezitter());
    }

    public int betalen(int pos) {
        for (Tax tax: taxen) {
            if (tax.pos() == pos) {
                return tax.betalen();
            }
        }
        return 0;
    }

    public int verkoopBezit(Speler speler) {
        if (!getKaartenBySpeler(speler).isEmpty()) {
            return verkoopBezet(speler);
        } else if (!getTreinKaartenBySpeler(speler).isEmpty()) {
            return verkoopTreinBezit(speler);
        } else if (!getSpecialeKaartenBySpeler(speler).isEmpty()) {
            return verkoopSpeciaalBezit(speler);
        }
        return 0;
    }
}
