import java.util.ArrayList;
import java.util.Optional;

public record SpecialeKaart(int position, int prijs, int maal, Optional<Speler> bezitter, int[] malers) {
    public SpecialeKaart setBezet(Optional<Speler> speler) {
        return new SpecialeKaart(position(), prijs(), maal(), speler, malers());
    }

    public SpecialeKaart setMaal(int maal) {
        return new SpecialeKaart(position(), prijs(), maal, bezitter(), malers());
    }
}
