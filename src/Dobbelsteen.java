import java.util.Random;

public class Dobbelsteen {
    private final Random random;

    Dobbelsteen() {
        this.random = new Random();
    }

    public int dobbelen() {
        return random.nextInt(12) + 1;
    }
}
