package InterfacciaUtente;

import Util.InputDati;
import Util.MyMenu;

public class ViewUtente extends View{
	//menu
		private static final String VISUALIZZA_ALBERO_GERARCHIA = "Visualizza Albero Gerarchie";
		private static final String VISUALIZZA_INFORMAZIONI = "Visualizza Informazioni";
		private static final String INTRODUCI_NUOVA_CAT = "Introduci una nuova gerarchia di categorie";
		private static final String AGGIUNGI_SOTTOCAT = "Popola la gerarchia";
		private static final String CREA_SCAMBIO = "Crea Scambio";
		private static final String CREA_ARTICOLO_OFFERTA = "Crea Articolo/Offerta";
		private static final String RITIRA_OFFERTA = "Ritira Offerta";
		private static final String VISUALIZZA_OFFERTA_APERTA_CATEGORIA = "Visualizza le offerte aperte di una categoria";
		private static final String VISUALIZZA_OFFERTE_STATO = "Visualizza le tue offerte per stato";
		private static final String VISUALIZZA_OFFERTA_SCAMBIO_CATEGORIA = "Visualizza le offerte in scambio di una categoria";
		private static final String VISUALIZZA_OFFERTA_CHIUSA_CATEGORIA = "Visualizza le offerte chiuse di una categoria";
		private static final String CREA_BARATTO = "Crea offerta baratto";
		private static final String VISUALIZZA_OFFERTE_SELEZIONATE = "Visualizza le offerte selezionate";
		private static final String VISUALIZZA_OFFERTE_RISPOSTA = "Offerte in scambio in attesa di risposta";
		private static final String VISUALIZZA_OFFERTE_PUBBLICATE = "Visualizza tutte le offerte pubblicate";
		private static final String VISUALIZZA_RISPOSTA = "Visualizza risposte offerte in scambio";
		private static final String IMPORTAZIONI_FILE = "Importazioni da file";
		
		//menu fruitore
		public MyMenu menuFruitore() {
		String[] vociMenu = { VISUALIZZA_INFORMAZIONI, CREA_ARTICOLO_OFFERTA, RITIRA_OFFERTA,
				VISUALIZZA_OFFERTA_APERTA_CATEGORIA, VISUALIZZA_OFFERTE_STATO, CREA_BARATTO,
				VISUALIZZA_OFFERTE_SELEZIONATE, VISUALIZZA_OFFERTE_RISPOSTA, VISUALIZZA_OFFERTE_PUBBLICATE,
				VISUALIZZA_RISPOSTA,"microservizio" };

		return new MyMenu(HEADER_MENU, vociMenu);
		}
		//menu configuratore
		public MyMenu menuConfiguratore() {
			String[] vociMenu = { INTRODUCI_NUOVA_CAT, AGGIUNGI_SOTTOCAT, VISUALIZZA_ALBERO_GERARCHIA,
					VISUALIZZA_INFORMAZIONI, CREA_SCAMBIO, VISUALIZZA_OFFERTA_APERTA_CATEGORIA,
					VISUALIZZA_OFFERTA_SCAMBIO_CATEGORIA, VISUALIZZA_OFFERTA_CHIUSA_CATEGORIA, IMPORTAZIONI_FILE };

			return new MyMenu(HEADER_MENU, vociMenu);

		}
		public int menuIniziale() {
			System.out.println("\nBenvenuto, seleziona un'opzione");
			return InputDati.leggiIntero("\n1. Login\n2. Registrati come fruitore\n\nScelta:", 1, 2);
		}

		public String richiediUser() {
			return InputDati.leggiStringaNonVuota("Inserisci Username: ");
		}

		public String richiediPassword() {
			return InputDati.leggiStringaNonVuota("Inserisci Password: ");
		}

		public void stampaLoginString() {
			System.out.println("\n-------LOGIN-------");
		}

		public void stampaErroreNome() {
			System.out.println("Nome gia REGISTRATO!");
		}

		public void stampaAccessoFruitore() {
				System.out.println("\n-------Stai accedendo come FRUITORE-------");
			} 
		public void stampaAccessoConfiguratore() {
				System.out.println("\\n-------Accesso eseguito come CONFIGURATORE-------");
			}
	

		public String richiediCreaUser() {
			return InputDati.leggiStringaNonVuota("Crea Username: ");
		}

		public String richiediCreaPassword() {
			return InputDati.leggiStringaNonVuota("Crea Password: ");
		}

		public void stampaNoAccount() {
			System.out.println("Account non trovato");
		}
		
}
