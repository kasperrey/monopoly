import java.util.Optional;

public record Kaart(int prijs, int huur, int pos, Optional<Speler> bezitter, int[] huures) {
    public Kaart setBezet(Optional<Speler> speler) {
        return new Kaart(prijs(), huur(), pos(), speler, huures());
    }

    public Kaart setHuur(int huur) {
        return new Kaart(prijs(), huur, pos(), bezitter(), huures());
    }
}
