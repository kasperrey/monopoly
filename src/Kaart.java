import java.util.Optional;

public record Kaart(int prijs, int huur, int pos, Optional<Speler> bezet) {
    public Kaart setBezet(Optional<Speler> speler) {
        return new Kaart(prijs(), huur(), pos(), speler);
    }

    public Kaart setHuur(int huur) {
        return new Kaart(prijs(), huur, pos(), bezet());
    }
}
