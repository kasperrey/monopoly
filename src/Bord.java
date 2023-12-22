import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Bord {
    private final ArrayList<Kaart> kaarten = new ArrayList<>();
    private final ArrayList<SpecialeKaart> specialeKaarten = new ArrayList<>();

    public Bord() {
        setKaartenUp();
        setSpecialeKaartenUp();
    }

    public Optional<Kaart> getKaart(int pos) {
        for (Kaart kaart: kaarten) {
            if (kaart.pos() == pos) {
                return Optional.of(kaart);
            }
        }
        return Optional.empty();
    }

    private void setKaartenUp() {
        kaarten.add(new Kaart(60000, 7500, 1, Optional.empty(), new int[] {7500}));
        kaarten.add(new Kaart(60000, 7500, 3, Optional.empty(), new int[] {7500}));
        kaarten.add(new Kaart(100000, 12500, 6, Optional.empty(), new int[] {12500}));
        kaarten.add(new Kaart(100000, 12500, 8, Optional.empty(), new int[] {12500}));
        kaarten.add(new Kaart(120000, 15000, 9, Optional.empty(), new int[] {15000}));
        kaarten.add(new Kaart(140000, 17500, 11, Optional.empty(), new int[] {17500}));
        kaarten.add(new Kaart(140000, 17500, 13, Optional.empty(), new int[] {17500}));
        kaarten.add(new Kaart(160000, 20000, 14, Optional.empty(), new int[] {20000}));
        kaarten.add(new Kaart(180000, 22500, 16, Optional.empty(), new int[] {22500}));
        kaarten.add(new Kaart(180000, 22500, 18, Optional.empty(), new int[] {22500}));
        kaarten.add(new Kaart(200000, 25000, 19, Optional.empty(), new int[] {25000}));
        kaarten.add(new Kaart(350000, 43750, 38, Optional.empty(), new int[] {43750}));
        kaarten.add(new Kaart(400000, 50000, 39, Optional.empty(), new int[] {50000}));
        kaarten.add(new Kaart(100000, 15000, 5, Optional.empty(), new int[] {15000, 30000, 60000, 120000}));
        kaarten.add(new Kaart(100000, 15000, 15, Optional.empty(), new int[] {15000, 30000, 60000, 120000}));
        kaarten.add(new Kaart(100000, 15000, 25, Optional.empty(), new int[] {15000, 30000, 60000, 120000}));
        kaarten.add(new Kaart(100000, 15000, 35, Optional.empty(), new int[] {15000, 30000, 60000, 120000}));
    }
    private void setSpecialeKaartenUp() {
        int[] waarden = {4000, 10000};
        specialeKaarten.add(new SpecialeKaart(12, 150000, 4000, Optional.empty(), waarden));
        specialeKaarten.add(new SpecialeKaart(28, 150000, 4000, Optional.empty(), waarden));
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

    public int verkoopBezet(Speler speler) {
        for (int kaart = 0; kaart < kaarten.size(); kaart++) {
            if (Objects.equals(kaarten.get(kaart).bezitter(), Optional.of(speler))) {
                if (kaart > kaarten.size()-4) {
                    kaarten.set(kaart, kaarten.get(kaart).setBezet(Optional.empty()));
                    return kaarten.get(kaart).prijs();
                } else {
                    return verkoopBezetVervoer(speler, kaarten.get(kaart).pos());
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
            if (kaart.bezitter().isPresent()) {
                if (Objects.equals(kaart.bezitter().get(), speler)) {
                    aantal++;
                }
            }
        }
        return aantal-1;
    }

    private void treinPrijs(Speler speler) {
        int aantal = aantalVanSpeler(speler);
        for (int kaartI = kaarten.size() - 4; kaartI < kaarten.size(); kaartI++) {
            Kaart kaart = kaarten.get(kaartI);
            if (kaart.bezitter().isPresent()) {
                if (kaart.bezitter().get() == speler) {
                    kaarten.set(kaartI, kaart.setHuur(kaart.huures()[aantal]));
                }
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
        if (pos == 4)
            return 200000;
        if (pos == 38)
            return 100000;
        return 0;
    }

    public int verkoopBezit(Speler speler) {
        if (!getKaartenBySpeler(speler).isEmpty()) {
            return verkoopBezet(speler);
        } else if (!getSpecialeKaartenBySpeler(speler).isEmpty()) {
            return verkoopSpeciaalBezit(speler);
        }
        return 0;
    }
}
