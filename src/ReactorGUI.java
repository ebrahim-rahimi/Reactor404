import javax.swing.*;
import java.awt.*;

public class ReactorGUI {

    private final Raumschiff schiff;
    private final EreignisManager ereignisse;

    private JFrame fenster;

    private JLabel status;

    private JProgressBar reaktorBar;
    private JProgressBar huelleBar;
    private JProgressBar kuehlungBar;

    private JButton notfallButton;

    private int sekunden = 0;

    public ReactorGUI() {

        schiff = new Raumschiff();
        ereignisse = new EreignisManager();

        baueGUI();

        Timer timer = new Timer(1000, e -> spielTick());
        timer.start();
    }

    private void baueGUI() {

        fenster = new JFrame("[ REACTOR_404 ]");

        JPanel haupt = new JPanel(new BorderLayout());

        JPanel anzeige = new JPanel(new GridLayout(0, 1, 5, 5));
        JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 5));

        haupt.setBackground(Color.BLACK);
        anzeige.setBackground(Color.BLACK);
        anzeige.setForeground(Color.WHITE);
        buttons.setBackground(Color.BLACK);

        status = new JLabel(
                "SHIP CONTROL",
                SwingConstants.CENTER);

        status.setForeground(new Color(114, 160, 211));

        reaktorBar = neuerBalken();
        huelleBar = neuerBalken();
        kuehlungBar = neuerBalken();

        JButton reaktorBtn = new JButton("REAKTOR +15");
        JButton huelleBtn = new JButton("HUELLE +20");
        JButton kuehlungBtn = new JButton("KUEHLUNG +25");

        notfallButton = new JButton("NOTFALL-REPARATUR");
        notfallButton.setBackground(Color.RED);
        notfallButton.setVisible(false);

        reaktorBtn.addActionListener(e -> {
            schiff.reaktorReparieren();
            aktualisieren();
        });

        huelleBtn.addActionListener(e -> {
            schiff.huelleReparieren();
            aktualisieren();
        });

        kuehlungBtn.addActionListener(e -> {
            schiff.kuehlungAuffuellen();
            aktualisieren();
        });

        notfallButton.addActionListener(e -> {

            ereignisse.notfallBeheben();

            status.setText(
                    "[ REACTOR_404 ] // NOTFALL BEHOBEN");

            notfallButton.setVisible(false);
        });

        anzeige.add(status);

        anzeige.add(new JLabel("REAKTOR"));
        anzeige.add(reaktorBar);

        anzeige.add(new JLabel("HUELLE"));
        anzeige.add(huelleBar);

        anzeige.add(new JLabel("KUEHLUNG"));
        anzeige.add(kuehlungBar);

        buttons.add(reaktorBtn);
        buttons.add(huelleBtn);
        buttons.add(kuehlungBtn);
        buttons.add(notfallButton);

        haupt.add(anzeige, BorderLayout.CENTER);
        haupt.add(buttons, BorderLayout.SOUTH);

        fenster.add(haupt);

        aktualisieren();

        fenster.setSize(650, 350);
        fenster.setLocationRelativeTo(null);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setVisible(true);
    }

    private JProgressBar neuerBalken() {

        JProgressBar bar = new JProgressBar(0, 100);

        bar.setStringPainted(true);
        bar.setForeground(new Color(31, 139, 225));
        bar.setBackground(Color.LIGHT_GRAY);

        return bar;
    }

    private void spielTick() {

        sekunden++;

        schiff.tick();

        if (ereignisse.tick()) {

            schiff.asteroidTreffer();

            Toolkit.getDefaultToolkit().beep();

            notfallButton.setVisible(true);

            status.setText("ASTEROIDENEINSCHLAG! NOTFALL!");
        }

        if (ereignisse.hatNotfall()) {

            status.setText("<html>NOTFALL!<br>Noch " + ereignisse.getNotfallSekunden() + " Sekunden</html>");
        }

        if (ereignisse.notfallTick()) {

            schiff.notfallSchaden();

            Toolkit.getDefaultToolkit().beep();

            ereignisse.notfallBeheben();

            notfallButton.setVisible(false);
        }

        aktualisieren();

        if (schiff.istZerstoert()) {

            fenster.setBackground(Color.RED);

            JOptionPane.showMessageDialog(fenster, "GAME OVER\n" + "Überlebt: " + sekunden + " Sekunden");

            System.exit(0);
        }
    }

    private void aktualisieren() {

        reaktorBar.setValue(schiff.getReaktor());
        huelleBar.setValue(schiff.getHuelle());
        kuehlungBar.setValue(schiff.getKuehlung());

        setBar(reaktorBar);
        setBar(huelleBar);
        setBar(kuehlungBar);
    }

    private void setBar(JProgressBar bar) {

        if (bar.getValue() <= 20) {
            bar.setForeground(Color.RED);
        } else {
            bar.setForeground(new Color(70, 178, 245));
        }
    }
}