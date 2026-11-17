public class Raumschiff {

    private int reaktor = 100;
    private int huelle = 100;
    private int kuehlung = 100;

    public int getReaktor() {
        return reaktor;
    }

    public int getHuelle() {
        return huelle;
    }

    public int getKuehlung() {
        return kuehlung;
    }

    public void tick() {

        if (kuehlung <= 20) {
            reaktor -= 8;
        } else {
            reaktor -= 5;
        }

        huelle -= 4;
        kuehlung -= 6;

        begrenzen();
    }

    public void reaktorReparieren() {

        if (kuehlung >= 10) {
            kuehlung -= 10;
            reaktor += 15;
        }

        begrenzen();
    }

    public void huelleReparieren() {

        huelle += 20;

        begrenzen();
    }

    public void kuehlungAuffuellen() {

        kuehlung += 25;

        begrenzen();
    }

    public void asteroidTreffer() {

        reaktor -= 15;
        huelle -= 20;

        begrenzen();
    }

    public void notfallSchaden() {

        reaktor -= 10;
        huelle -= 10;

        begrenzen();
    }

    public boolean istZerstoert() {
        return reaktor <= 0 || huelle <= 0;
    }

    private void begrenzen() {

        reaktor = Math.max(0, Math.min(100, reaktor));
        huelle = Math.max(0, Math.min(100, huelle));
        kuehlung = Math.max(0, Math.min(100, kuehlung));
    }
}