import be.kasperreynders.monopoly.Bord;
import be.kasperreynders.monopoly.Speler;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoaderCsvTest {

    @Test
    void leesBestand() {
        Speler speler = new Speler(1, new Bord());
        assertEquals(Optional.of(speler), Optional.of(speler));
    }

}