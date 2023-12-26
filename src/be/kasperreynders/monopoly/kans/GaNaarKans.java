package be.kasperreynders.monopoly.kans;

import be.kasperreynders.monopoly.*;

import java.util.ArrayList;
import java.util.Optional;

public class GaNaarKans implements KansKaart {
    private final int[] plekken;
    private final String type;
    private final Bord bord;
    private final Dobbelsteen dobbelsteen;
    private final int terug;

    public GaNaarKans(int[] plekken, String type, Bord bord, Dobbelsteen dobbelsteen, int terug) {
        this.plekken = plekken;
        this.type = type;
        this.bord = bord;
        this.dobbelsteen = dobbelsteen;
        this.terug = terug;
    }

    @Override
    public void voerUit(Speler speler) {
        if (!type.equals("terug")) {
            int dicht = dichtsBij(speler.getPos());
            speler.setPos(dicht);
            speler.overLijn(speler.getPos());
            switch (type) {
                case "treinDicht" -> treinDichtKaart(dicht, speler);
                case "speciaal" -> specialeKaart(dicht, speler);
                case "kaart" -> kaart(dicht, speler);
                case "trein" -> treinKaart(speler, dicht);
            }
        } else {
            int pos = speler.getPos()-terug;
            if (pos < 0) {
                pos += 40;
            }
            speler.setPos(pos);
        }
    }

    private int dichtsBij(int pos) {
        int dichts = plekken[0];
        for (int p: plekken) {
            if (Math.abs(p-pos) < Math.abs(dichts-pos)) {
                dichts = p;
            }
        }
        return dichts;
    }

    private void kaart(int dicht, Speler speler) {
        Optional<Kaart> kaartOptional = bord.getKaart(dicht);
        if (kaartOptional.isPresent()) {
            Kaart kaart = kaartOptional.get();
            if (kaart.bezitter().isPresent()) {
                speler.betalen(kaart.huur(), kaart.bezitter().get());
            } else {
                speler.koopKaart(dicht, kaart.prijs(), "kaart");
            }
        }
    }

    private void treinDichtKaart(int dicht, Speler speler) {
        Optional<TreinKaart> kaartOptional = bord.getTreinKaart(dicht);
        if (kaartOptional.isPresent()) {
            TreinKaart kaart = kaartOptional.get();
            if (kaart.bezitter().isPresent()) {
                speler.betalen(kaart.huur()*2, kaart.bezitter().get());
            } else {
                speler.koopKaart(dicht, kaart.prijs(), type);
            }
        }
    }

    private void specialeKaart(int dicht, Speler speler) {
        Optional<SpecialeKaart> kaartOptional = bord.getSpecialeKaart(dicht);
        if (kaartOptional.isPresent()) {
            SpecialeKaart kaart = kaartOptional.get();
            if (kaart.bezitter().isPresent()) {
                int[] gedobbeld = dobbelsteen.dobbelenMet2();
                speler.betalen(10*(gedobbeld[0]+gedobbeld[1]), kaart.bezitter().get());
            } else {
                speler.koopKaart(dicht, kaart.prijs(), type);
            }
        }
    }

    private void treinKaart(Speler speler, int dicht) {
        Optional<TreinKaart> kaartOptional = bord.getTreinKaart(dicht);
        if (kaartOptional.isPresent()) {
            TreinKaart kaart = kaartOptional.get();
            if (kaart.bezitter().isPresent()) {
                speler.betalen(kaart.huur(), kaart.bezitter().get());
            }
        }
    }
}
