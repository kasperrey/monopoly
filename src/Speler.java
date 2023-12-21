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

    public void stap(int stappen) {
        pos = overLijn(pos + stappen);
        Optional<Kaart> kaart = bord.getKaart(pos);
        if (kaart.isPresent()) {
            Kaart echteKaart = kaart.get();
            if (echteKaart.bezet().isPresent()) {
                while (geld < echteKaart.huur()) {
                    Kaart k = bord.veranderBezet(Optional.empty(), pos);
                    geld += (k.prijs()-k.prijs()/7);
                }
                echteKaart.bezet().get().addGeld(echteKaart.huur());
                geld -= echteKaart.huur();
            } else {
                if (geld >= echteKaart.prijs()) {
                    bord.veranderBezet(Optional.of(this), pos);
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
}
