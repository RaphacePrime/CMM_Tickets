package utils_package;

import javax.swing.UIManager;


public class LookAndFeelUtil {
    public static void setCrossPlatformLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
