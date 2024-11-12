package framespackage;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class UtenteFrame extends JFrame {

    public UtenteFrame() {
        setTitle("Pagina Utente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Sei un utente normale.", JLabel.CENTER);
        add(label);

        setLocationRelativeTo(null); // Centra la finestra
    }
}
