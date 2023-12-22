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
        int betalen = bord.betalen(pos);
        while (geld < betalen) {
            int p = bord.verkoopBezit(this);
            if (p == 0) {
                System.out.println("betalen");
                break;
            }
            geld += (p-p/7);
        }
        geld -= betalen;
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
                    int p = bord.verkoopBezit(this);
                    if (p == 0)
                        break;
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
            if (echteKaart.bezitter().isPresent()) {
                while (geld < echteKaart.huur()) {
                    int p = bord.verkoopBezit(this);
                    if (p == 0)
                        break;
                    geld += (p-p/7);
                }
                echteKaart.bezitter().get().addGeld(echteKaart.huur());
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
