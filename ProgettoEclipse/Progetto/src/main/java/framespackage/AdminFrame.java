package framespackage;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AdminFrame extends JFrame {

    public AdminFrame() {
        setTitle("Pagina Admin");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Sei un amministratore.", JLabel.CENTER);
        add(label);

        setLocationRelativeTo(null); // Centra la finestra
    }
}

