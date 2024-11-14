package loginpackage;


import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import classespackage.Utente;
import databasepackage.Database;
import framespackage.AdminFrame;
import framespackage.UtenteFrame;



public class Main {
    public static void main(String[] args) throws Exception {
        Database.createTables();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nScegli un'opzione:");
            System.out.println("1. Registrati");
            System.out.println("2. Accedi");
            System.out.println("3. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma newline

            if (scelta == 1) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                
                String password; 
                boolean controlloPW=false;
                do {
                	System.out.print("Password: ");
                	password = scanner.nextLine();
                    if(password.length()>7)
                    {
                    	controlloPW=true;
                    }
                    else
                    {
                    	System.out.println("La password deve essere di almeno 8 caratteri!");
                    }
                }while(controlloPW==false);
                
                String codiceFiscale; 
                boolean controlloCF=false;
                do {
                	System.out.print("Codice Fiscale: ");
                    codiceFiscale = scanner.nextLine();
                    if(codiceFiscale.length()==16)
                    {
                    	controlloCF=true;
                    }
                    else
                    {
                    	System.out.println("Hai inserito un codice fiscale errato!");
                    }
                }while(controlloCF==false);
                codiceFiscale.toUpperCase();
                
                String telefono;
                boolean controlloTel=false;
                do {
                	System.out.println("Numero di telefono: ");
                	telefono=scanner.nextLine();
                	if(telefono.length()==10)
                	{
                		controlloTel=true;
                	}
                	else
                	{
                		System.out.println("Hai inserito un numero di cellulare non valido!");
                	}
                }while(controlloTel==false);
                
                
                String email;
               
                boolean controlloEmail=false;
                
                do {
                	System.out.println("Email: ");
                	email=scanner.nextLine();
                	if(IsEmailValid(email))
                	{
                		controlloEmail=true;
                	}
                	else
                	{
                	System.out.println("Hai inserito un'email in un formato non corretto!");	
                	}
                }while(controlloEmail==false);
                   
                System.out.print("Vuoi fare richiesta per diventare admin e poter creare e gestire eventi? (s/N): ");
                String adminString = scanner.nextLine();
                boolean admin;

                if (adminString.equalsIgnoreCase("S")) {
                    admin = true;
                } else {
                    admin = false;
                }

                Utente nuovoUtente = new Utente(username, password, codiceFiscale, telefono, email,  admin);
                Registrazione.registraUtente(nuovoUtente);
            } else if (scelta == 2) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                Utente utenteLoggato = Login.autentica(username, password);
                if (utenteLoggato != null) {
                    System.out.println("Accesso riuscito! Benvenuto " + utenteLoggato.getUsername());
                    if (utenteLoggato.isAdmin()) {
                        System.out.println("Sei un amministratore. Carico pagina...");
                        Thread.sleep(2000);
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            new AdminFrame().setVisible(true);
                        });
                    } else {
                        System.out.println("Sei un utente normale. Carico pagina...");
                        Thread.sleep(2000);
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            new UtenteFrame().setVisible(true);
                        });
                    }
                }
            } else if (scelta == 3) {
                System.out.println("Uscita dal sistema.");
                break;
            } else {
                System.out.println("Scelta non valida.");
            }
        }
        scanner.close();
    }

    
    //funzione per validare email
	private static boolean IsEmailValid(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		
	}
}


