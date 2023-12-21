import java.util.Optional;

public class Speler {
    private final Bord bord;
    private int geld;
    private int pos = 0;

    public Speler(int geld, Bord bord) {
        this.geld = geld;
        this.bord = bord;
    }

    public void addGeld(int geld) {
        this.geld += geld;
    }

    public void stap(int stappen, boolean kopen) {
        pos = overLijn(pos + stappen);
        geld -= bord.betalen(pos);
        kaart(kopen);
        specialeKaart(stappen, kopen);
    }

    private void specialeKaart(int stappen, boolean kopen) {
        Optional<SpecialeKaart> specialeKaartOptional = bord.getSpecialeKaart(pos);
        if (specialeKaartOptional.isPresent()) {
            SpecialeKaart specialeKaart = specialeKaartOptional.get();
            if (specialeKaart.bezitter().isPresent()) {
                int prijs = specialeKaart.maal() * stappen;
                while (geld < (prijs)) {
                    int p = bord.verkoopSpeciaalBezit(pos);
                    geld += (p - p / 7);
                }
                specialeKaart.bezitter().get().addGeld(prijs);
                geld -= prijs;
            } else {
                if (geld >= specialeKaart.prijs() && kopen) {
                    bord.koopSpeciaalBezit(this, pos);
                    geld -= specialeKaart.prijs();
                }
            }
        }
    }

    private void kaart(boolean kopen) {
        Optional<Kaart> kaart = bord.getKaart(pos);
        if (kaart.isPresent()) {
            Kaart echteKaart = kaart.get();
            if (echteKaart.bezet().isPresent()) {
                while (geld < echteKaart.huur()) {
                    int p = bord.verkoopBezet(this, pos);
                    geld += (p-p/7);
                }
                echteKaart.bezet().get().addGeld(echteKaart.huur());
                geld -= echteKaart.huur();
            } else {
                if (geld >= echteKaart.prijs() && kopen) {
                    bord.koopBezet(this, pos);
                    geld -= echteKaart.prijs();
                }
            }
        }
    }

    private int overLijn(int pos) {
        if (pos >= 40) {
            geld += 200000;
            return pos - 40;
        }
        return pos;
    }

    public boolean verloren() {
        return (geld < 0);
    }
}
