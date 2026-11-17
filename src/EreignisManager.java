import java.util.Random;

public class EreignisManager {

    private final Random zufall = new Random();

    private int naechsterAsteroid;
    private boolean notfall;
    private int notfallSekunden;

    public EreignisManager() {
        naechsterAsteroid = 8 + zufall.nextInt(9);
    }

    public boolean tick() {

        naechsterAsteroid--;

        if (naechsterAsteroid <= 0) {

            notfall = true;
            notfallSekunden = 6;

            naechsterAsteroid = 8 + zufall.nextInt(9);

            return true;
        }

        return false;
    }

    public boolean notfallTick() {

        if (!notfall) {
            return false;
        }

        notfallSekunden--;

        return notfallSekunden <= 0;
    }

    public void notfallBeheben() {

        notfall = false;
        notfallSekunden = 0;
    }

    public boolean hatNotfall() {

        return notfall;
    }

    public int getNotfallSekunden() {

        return notfallSekunden;
    }
}
