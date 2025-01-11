package frames_package;

import javax.swing.*;

import adminpanels_package.AdminAddEventPanel;
import adminpanels_package.AdminAddLuogoPanel;
import adminpanels_package.AdminAddSectorsPanel;
import adminpanels_package.AdminDetailsLuogoPanel;
import adminpanels_package.AdminHomePanel;
import adminpanels_package.AdminModifyEventPanel;
import adminpanels_package.AdminModifyLuogoPanel;
import database_package.Database;
import login_package.LoginPanel;
import login_package.RegistrationPanel;
import userpanels_package.UserHomePanel;
import userpanels_package.UserViewEventPanel;
import userpanels_package.UserViewLuogoPanel;
import utils_package.LookAndFeelUtil;

import java.awt.*;
import java.text.ParseException;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    public AdminHomePanel adminHomePanel;
    public UserHomePanel userHomePanel;
    private AdminModifyEventPanel adminModifyEventPanel;
    private AdminModifyLuogoPanel adminModifyLuogoPanel;
    private AdminAddLuogoPanel adminAddLuogoPanel;
    private AdminAddEventPanel adminAddEventPanel;
    private UserViewLuogoPanel userViewLuogoPanel;
    private UserViewEventPanel userViewEventPanel;

    public MainFrame() {
        Database.createTables();
        setTitle("Sistema di Accesso e Registrazione");

        // Configurazione della finestra principale
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout principale
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inizializzazione dei pannelli
        loginPanel = new LoginPanel(this.cardLayout, mainPanel, e -> cardLayout.show(mainPanel, "Registration"));
        registrationPanel = new RegistrationPanel(e -> cardLayout.show(mainPanel, "Login"));
        adminHomePanel = new AdminHomePanel();
        adminModifyLuogoPanel = new AdminModifyLuogoPanel();
        adminAddLuogoPanel = new AdminAddLuogoPanel();
        adminAddEventPanel = new AdminAddEventPanel();
        userHomePanel = new UserHomePanel();
        userViewLuogoPanel = new UserViewLuogoPanel();
        //userViewEventPanel = new UserViewEventPanel();
        

        // Aggiunta dei pannelli al layout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(adminHomePanel, "Admin Home");
        mainPanel.add(adminModifyLuogoPanel, "Admin Modify Luogo");
        mainPanel.add(adminAddLuogoPanel, "Admin Add Luogo");
        mainPanel.add(adminAddEventPanel, "Admin Add Event");
        mainPanel.add(userHomePanel, "User Home");
        mainPanel.add(userViewLuogoPanel, "User View Luogo");
        //mainPanel.add(userViewEventPanel, "User View Event");

        // Configurazione delle azioni nei pulsanti
        adminHomePanel.setSwitchToModifyEventAction(e -> {
            try {
				adminModifyEventPanel = new AdminModifyEventPanel();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            adminModifyEventPanel.setSwitchToDetailsEventAction(this);
            mainPanel.add(adminModifyEventPanel, "Admin Modify Event");
            adminHomePanel.setContentPanel(adminModifyEventPanel);
        });

        adminHomePanel.setSwitchToModifyLuogoAction(e -> {
            adminModifyLuogoPanel = new AdminModifyLuogoPanel();
            adminModifyLuogoPanel.setSwitchToDetailsLuogoAction(this); // Passaggio del riferimento al MainFrame
            mainPanel.add(adminModifyLuogoPanel, "Admin Modify Luogo");
            adminHomePanel.setContentPanel(adminModifyLuogoPanel);
        });

        adminHomePanel.setSwitchToAddLuogoAction(e -> adminHomePanel.setContentPanel(adminAddLuogoPanel));
        adminHomePanel.setSwitchToAddEventAction(e -> {
        	adminAddEventPanel.setSwitchToAddSectorsAction(this);
        	adminAddEventPanel.updateSectorsDropdown();
        	adminAddEventPanel.updateLocationDropdown();
        	adminHomePanel.setContentPanel(adminAddEventPanel);
        	
        
        });

        adminHomePanel.setLogoutAction(e -> {
            loginPanel.resetFields();
            cardLayout.show(mainPanel, "Login");
        });
        
        userHomePanel.setSwitchToViewLuogoAction(e -> userHomePanel.setContentPanel(userViewLuogoPanel));
        userHomePanel.setSwitchToViewEventAction(e-> {
        
        	try {
				userViewEventPanel = new UserViewEventPanel();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	userViewEventPanel.setSwitchToDetailsEventAction(this);
        	mainPanel.add(userViewEventPanel, "User View Event");
            userHomePanel.setContentPanel(userViewEventPanel);
        
        });
        
        userHomePanel.setLogoutAction(e -> {
            loginPanel.resetFields();
            cardLayout.show(mainPanel, "Login");
        });
        
        add(mainPanel);

        // Mostra la schermata di login iniziale
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
    	LookAndFeelUtil.setCrossPlatformLookAndFeel();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
