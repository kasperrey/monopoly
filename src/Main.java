import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Speler speler = new Speler(1000000, new Bord());
        Speler speler2 = new Speler(1000000, new Bord());
        Speler speler3 = new Speler(1000000, new Bord());
        Dobbelsteen dobbelsteen = new Dobbelsteen();
        Random random = new Random();
        boolean verloren1 = false;
        boolean verloren2 = false;
        boolean verloren3 = false;
        while ((!verloren1) && (!verloren2) && (!verloren3)) {
            speler.stap(dobbelsteen.dobbelen(), true);
            speler2.stap(dobbelsteen.dobbelen(), false);
            speler3.stap(dobbelsteen.dobbelen(), random.nextBoolean());
            verloren1 = speler.verloren();
            verloren2 = speler2.verloren();
            verloren3 = speler3.verloren();
        }
        System.out.print("speler1 is verloren is ");
        System.out.println(verloren1);
        System.out.print("speler2 is verloren is ");
        System.out.println(verloren2);
        System.out.print("speler3 is verloren is ");
        System.out.println(verloren3);
    }
}