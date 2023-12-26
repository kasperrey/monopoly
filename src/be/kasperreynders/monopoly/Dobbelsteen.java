package be.kasperreynders.monopoly;

import java.util.Random;

public class Dobbelsteen {
    private final Random random;

    Dobbelsteen() {
        this.random = new Random();
    }

    private int dobbelen() {
        return random.nextInt(6) + 1;
    }

    public int[] dobbelenMet2() {
        return new int[] {dobbelen(), dobbelen()};
    }
}
