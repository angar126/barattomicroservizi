package InterfacciaUtente;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import Models.*;
import Util.InputDati;
import Util.MyMenu;

public abstract class View {
	protected String[] statoOfferteString = { "Offerta aperta", "Offerta ritirata", "Offerta accoppiata",
			"Offerta selezionata", "Offerta in scambio", "Offerta chiusa" };
	private static final String INSERISCI_NOME = "Inserisci il Nome : ";
	protected static final String SCEGLI = "\nScegli un'opzione";
	protected static final String NUMERO_CAMPI_NATIVI = "Numero campi nativi: ";
	private static final String INSERISCI_DESCRIZIONE = "Inserisci la Descrizione : ";
	
	private static final String NO_ELEMENTI = "Non ci sono elementi";
	public static final String CORNICE = "--------------------------------\n";
	
	protected final static String HEADER_MENU = "Scegli un opzione";
	protected static final String MSG_USCITA = "Alla prossima :)";
	
	public View() {
	}
//metodo di formattazione del testo
	public String corniceTesto(String testo) {
		StringBuffer sb = new StringBuffer();
		sb.append(CORNICE + "\n");
		sb.append(testo + "\n");
		sb.append(CORNICE + "\n");
		return sb.toString();
	}

	//metodo per stampare stringa no elementi
	public void noElementi() {
		System.out.println(NO_ELEMENTI);
	}

	// richiedi nome
	public String richiediNome() {
		return InputDati.leggiStringaNonVuota(INSERISCI_NOME);
	}

	// richiedi descrizione
	public String richiediDescrizione() {
		return InputDati.leggiStringaNonVuota(INSERISCI_DESCRIZIONE);
	}

	public String dataString(Baratto baratto, Scambio scambio) {
		Date dataCreazione = baratto.getTimestamp();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(dataCreazione);
		calendario.add(Calendar.DAY_OF_MONTH, scambio.getScadenza());
		Date dataScadenza = new java.sql.Date(calendario.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(dataScadenza);
	}

	public void stampaRigaMenuString(int i, String stringa) {
		System.out.println(i + "\t" + stringa);
	}

	public void stampaStringa(String stringa) {
		System.out.println(stringa);
	}

	// Metodo per stampare menu di lista di stringhe
	public int menuStringhe(List<String> lista, String didascalia) {
		int i = 0;
		System.out.println(corniceTesto(didascalia));
		for (String elemento : lista) {
			System.out.println((i) + "\t" + elemento);
			i++;
		}
		int scelta = InputDati.leggiIntero(SCEGLI, 0, lista.size() - 1);
		return scelta;
	}
	
	//stampa msg uscita
	public void stampaMsgUscita() {
		System.out.println(MSG_USCITA);
	}
	
}
