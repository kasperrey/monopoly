import java.util.Random;

public class Dobbelsteen {
    private final Random random;

    public Dobbelsteen() {
        this.random = new Random();
    }

    public int dobbelen() {
        return random.nextInt(6) + 1;
    }
}
