package be.kasperreynders.monopoly;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Bord bord = new Bord();
        Speler speler = new Speler(1500, bord);
        Speler speler2 = new Speler(1500, bord);
        Speler speler3 = new Speler(1500, bord);
        bord.addSpeler(speler);
        bord.addSpeler(speler2);
        bord.addSpeler(speler3);
        Dobbelsteen dobbelsteen = new Dobbelsteen();
        Random random = new Random();
        boolean verloren1 = false;
        boolean verloren2 = false;
        boolean verloren3 = false;
        while ((!verloren1) && (!verloren2) && (!verloren3)) {
            speler.stap(dobbelsteen.dobbelenMet2(), true, 7, true);
            speler2.stap(dobbelsteen.dobbelenMet2(), false, 0, false);
            speler3.stap(dobbelsteen.dobbelenMet2(), random.nextBoolean(), random.nextInt(0, 7), random.nextBoolean());
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