public class Main {
    public static void main(String[] args) {
        Speler speler = new Speler(1000000, new Bord());
        Speler speler2 = new Speler(1000000, new Bord());
        Dobbelsteen dobbelsteen = new Dobbelsteen();
        for (int i = 0; i < 100; i++) {
            speler.stap(dobbelsteen.dobbelen());
            speler2.stap(dobbelsteen.dobbelen());
        }
    }
}