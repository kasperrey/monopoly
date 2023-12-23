import java.util.Optional;

public class Speler {
    private final Bord bord;
    private int geld;
    private int pos = 0;

    Speler(int geld, Bord bord) {
        this.geld = geld;
        this.bord = bord;
    }

    public void addGeld(int geld) {
        this.geld += geld;
    }

    public void stap(int stappen, boolean kopen) {
        pos = overLijn(pos + stappen);
        int betalen = bord.betalen(pos);
        verkopen(betalen);
        geld -= betalen;
        kaart(kopen);
        treinKaart(kopen);
        specialeKaart(stappen, kopen);
    }

    private void specialeKaart(int stappen, boolean kopen) {
        Optional<SpecialeKaart> specialeKaartOptional = bord.getSpecialeKaart(pos);
        if (specialeKaartOptional.isPresent()) {
            SpecialeKaart specialeKaart = specialeKaartOptional.get();
            if (specialeKaart.bezitter().isPresent()) {
                int prijs = specialeKaart.maal() * stappen;
               verkopen(prijs);
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

    private void treinKaart(boolean kopen) {
        Optional<TreinKaart> treinKaartOptional = bord.getTreinKaart(pos);
        if (treinKaartOptional.isPresent()) {
            TreinKaart treinKaart = treinKaartOptional.get();
            if (treinKaart.bezitter().isPresent()) {
                int prijs = treinKaart.huur();
                verkopen(prijs);
                treinKaart.bezitter().get().addGeld(prijs);
                geld -= prijs;
            } else {
                if (geld >= treinKaart.prijs() && kopen) {
                    bord.koopTreinBezit(this, pos);
                    geld -= treinKaart.prijs();
                }
            }
        }
    }

    private void verkopen(int prijs) {
        while (geld < (prijs)) {
            int p = bord.verkoopBezit(this);
            if (p == 0)
                break;
            geld += (p - p / 7);
        }
    }

    private void kaart(boolean kopen) {
        Optional<Kaart> kaart = bord.getKaart(pos);
        if (kaart.isPresent()) {
            Kaart echteKaart = kaart.get();
            if (echteKaart.bezitter().isPresent()) {
                verkopen(echteKaart.huur());
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
