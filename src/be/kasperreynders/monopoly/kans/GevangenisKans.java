package be.kasperreynders.monopoly.kans;

import be.kasperreynders.monopoly.Speler;

public class GevangenisKans implements KansKaart {
    private final String type;

    public GevangenisKans(String type) {
        this.type = type;
    }

    @Override
    public void voerUit(Speler speler) {
        if (type.equals("uit")) {
            speler.magUitGevangenis = true;
        } else {
            speler.inGevangenis = true;
            speler.setPos(10);
        }
    }
}
