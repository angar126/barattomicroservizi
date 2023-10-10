package InterfacciaUtente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Models.Baratto;
import Models.IntervalloOra;
import Models.Scambio;
import Util.InputDati;
import Util.MyMenu;

public class ViewScambio extends View {
	protected static String[] elencoGiorni = { "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato",
	"Domenica" };
	
	public String chiediPiazza() {
		return InputDati.leggiStringaNonVuota("\nInserisci la citta' in cui avverranno gli scambi: ");
	}

	// Metodo che crea la stringa dei luoghi per lo scambio
	public String getStringLuoghi() {
		StringBuilder sb = new StringBuilder();
		int numeroLuoghi = InputDati.leggiInteroConMinimo("\nQuanti luoghi vuoi inserire?(minimo 1)", 1);
		for (int i = 0; i < numeroLuoghi; i++) {
			int a = i + 1;
			System.out.println("\n------LUOGO NUMERO " + a + "/" + numeroLuoghi + "------");
			String luogo = InputDati.leggiStringaNonVuota("Inserisci il luogo " + a + ": ");
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(luogo);
		}
		return sb.toString();
	}


	// Metodo che consente all'utente di selezionare i giorni
	public ArrayList<Integer> selezionaListaGiorni() {
		int numGiorno;
		ArrayList<Integer> giorni = new ArrayList<Integer>();
		MyMenu menuGiorni = new MyMenu("Selezione il giorno per gli scambi: ", elencoGiorni);
		do {
			numGiorno = menuGiorni.scegli();
			if (numGiorno == 0) {
				return giorni;
			} else if (giorni.contains(numGiorno - 1)) {
				System.out.println("Giorno già selezionato");
			} else {
				giorni.add(numGiorno - 1);
				System.out.println("\nVuoi aggiungere un altro giorno?");
			}
		} while (numGiorno != 0);
		return giorni;
	}

	// Metodo che stampa i giorni della settimana ordinati (da domenica a sabato)
	// passati tramite una lista in formato int
	public String listaGiorniTestuale(ArrayList<Integer> lista) {
		Collections.sort(lista);
		String stringa = elencoGiorni[lista.get(0)];
		for (int i = 1; i < lista.size(); i++) {
			stringa = stringa + ", " + elencoGiorni[lista.get(i)];
		}
		return stringa;
	}

	// Metodo che chiede l'ora all'utente
	public LocalTime getOra() {
		int minuti;
		int ora;
		do {
			ora = InputDati.leggiInteroNonNegativo("\nInserisci l'ora (formato 0-23) :");

		} while (!(ora <= 23));
		do {
			minuti = InputDati.leggiInteroNonNegativo("Inserisci i minuti (formato 00 o 30) :");

		} while (!(minuti == 00 || minuti == 30));
		LocalTime time = LocalTime.of(ora, minuti);
		return time;
	}

	public void richiediOraInizioString() {
		System.out.println("\nInserisci l'ora di inizio intervallo");
	}

	public void richiediOraFineString() {
		System.out.println("\nInserisci l'ora di fine intervallo");
	}

	public void richiediIntervalloString() {
		System.out.println("\nInserisci l'intervallo oraio in cui effettuare lo scambio");
	}

	public void oraNonValidaString() {
		System.out.println("\nOra non valida");
	}

	public void intervalloNonValidaString() {
		System.out.println("\nIntervallo non valida");
	}

	public void intervalliString() {
		System.out.println("\nInserisci l'intervallo in cui e' possibile effettuare lo scambio (>1, <2)");
	}

	public boolean richiediAltroIntervallo() {
		return InputDati.leggiBoolean("\nVuoi inserire un altro intervallo?");
	}

	public void richiediGiorniString() {
		System.out.println("\nSeleziona i giorni per lo scambio(almeno 1):");
	}

	

	// Metodo per chiedere all'utente i giorni in cui e' possibile effettuare lo
	// scambio
	public ArrayList<Integer> richiediImpostaGiorni(Scambio scambio) {
		ArrayList<Integer> giorni = new ArrayList<Integer>();
		do {
			richiediGiorniString();
			giorni = selezionaListaGiorni();
			if (!giorni.isEmpty())
				stampaStringa(listaGiorniTestuale(giorni));
		} while (giorni.isEmpty());
		return giorni;
	}

	// Metodo per chiedere il numero di giorni entro il quale è possibile effettuare
	// lo scambio
	public int impostaScadenza() {
		int scadenza = InputDati.leggiInteroConMinimo(
				"\nImposta il numero massimo di giorni entro cui il fruitore puo' accettre una proposta: ", 1);
		return scadenza;
	}
	/**
	 * Metodo per richiedere il luogo della proposta di scambio
	 * 
	 * @param scambio
	 * @return luogo
	 * @postcondition luogo != null
	 */
	public String getLuogoProposta(Scambio scambio) {
		List<String> luoghi = scambio.getListaLuoghi();
		int scelta = menuStringhe(luoghi, "Scegli il luogo da proporre per lo scambio: ");
		return luoghi.get(scelta);
	}

	/**
	 * Metodo per richiedere il giorno della settimana per la proposta di scambio
	 * 
	 * @param scambio
	 * @return giorno
	 * @postcondition giorno != null
	 */
	public String getGiornoProposta(Scambio scambio) {
		List<String> giorni = scambio.getListaGiorni();
		int scelta = menuStringhe(giorni, "\nScegli il giorno da proporre per lo scambio");
		return giorni.get(scelta);
	}

	/**
	 * Metodo per far scegliere l'intervallo da utilizzare
	 * 
	 * @param scambio
	 * @return ora
	 * @postcondition ora != null
	 */
	public String getIntervalloOrarioProposta(Scambio scambio) {
		List<String> orari = scambio.getListaOrari();
		int scelta = menuStringhe(orari, "Scegli l'intervallo orario");
		return orari.get(scelta);
	}

	/**
	 * Metodo per creare la liste degli orari dell'intervallo
	 * 
	 * @param intervallo
	 * @return orari
	 */
	public List<String> orariInIntervallo(IntervalloOra intervallo) {
		List<String> orari = new ArrayList<>();
		LocalTime ora = intervallo.getInizio();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		while (!ora.isAfter(intervallo.getFine())) {
			orari.add(ora.format(formatter));
			ora = ora.plusMinutes(30);
		}

		return orari;
	}

	/**
	 * Metodo per convertire la stringa del singolo intervallo in IntervalloOra
	 * 
	 * @param orario
	 * @return intervallo
	 */
	public IntervalloOra convertiStringIntervalloOra(String orario) {
		String[] orariString = orario.trim().split("-");
		LocalTime inizio = LocalTime.parse(orariString[0].trim());
		LocalTime fine = LocalTime.parse(orariString[1].trim());
		return new IntervalloOra(inizio, fine);
	}

	/**
	 * Metodo per richiedere l'ora dalla proposta di scambio
	 * 
	 * @param scambio
	 * @return ora
	 * @postcondition ora != null
	 */
	public String getOraProposta(Scambio scambio) {
		String intervallo = getIntervalloOrarioProposta(scambio);
		IntervalloOra intervalloOra = convertiStringIntervalloOra(intervallo);
		List<String> orari = orariInIntervallo(intervalloOra);
		int scelta = menuStringhe(orari, "Scegli l'ora da proporre per lo scambio");
		return orari.get(scelta);
	}

	

	/**
	 * Metodo che restituisce la data per lo scambio richiesta al cliente
	 * 
	 * @param scambio
	 * @return data
	 */
	public String getDataProposta(Scambio scambio) {
		String giorniScambio = scambio.getGiorni();
		List<Integer> giorniInt = convertiGiorni(giorniScambio);
		LocalDate data = scegliDataPerScambio(giorniInt);
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public List<Integer> convertiGiorni(String giorniStringa) {
		List<Integer> giorniPossibili = new ArrayList<>();
		String[] giorni = giorniStringa.split(",");
		for (String giorno : giorni) {
			String nomeGiorno = giorno.trim();
			for (int i = 0; i < elencoGiorni.length; i++) {
				if (nomeGiorno.equalsIgnoreCase(elencoGiorni[i])) {
					int j = i + 1;
					giorniPossibili.add(j);
					break;
				}
			}
		}
		return giorniPossibili;
	}

	/**
	 * Metodo per far scegliere la data all'utente per lo scambio
	 * 
	 * @param giorniInt
	 * @return data
	 * @precondition giorniInt != null
	 */
	public LocalDate scegliDataPerScambio(List<Integer> giorniInt) {
		// LocalDate oggi = LocalDate.now();
		List<LocalDate> datePossibili = getDatePossibili(giorniInt);// , oggi, oggi.plusMonths(1));
		if (datePossibili.isEmpty()) {
			throw new IllegalArgumentException("Non ci sono date disponibili per lo scambio");
		}
		System.out.println(corniceTesto("Scegli una data per lo scambio (max 1 mese):"));
		for (int i = 0; i < datePossibili.size(); i++) {
			System.out.println((i) + "\t" + datePossibili.get(i).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		int scelta = InputDati.leggiIntero("Scegli un'opzione: ", 0, datePossibili.size() - 1);
		return datePossibili.get(scelta);
	}

	/**
	 * Metodo per generare le date possibili da oggi entro un mese
	 * 
	 * @param giorniInt
	 * @return lista date
	 * @precondition giorniInt != null
	 */
	public List<LocalDate> getDatePossibili(List<Integer> giorniInt) {
		LocalDate oggi = LocalDate.now();
		LocalDate fineMese = oggi.plusMonths(1);
		List<LocalDate> datePossibili = new ArrayList<>();
		for (LocalDate data = oggi; !data.isAfter(fineMese); data = data.plusDays(1)) {
			if (giorniInt.contains(data.getDayOfWeek().getValue())) {
				datePossibili.add(data);
			}
		}

		return datePossibili;
	}

	/**
	 * Metodo per richiedere i parametri dello scambio
	 * 
	 * @param scambio
	 * @return proposta
	 * @throws SQLException
	 * @precondition scambio != null
	 * @postcondition luogo != null; giorni != null; ora != null;
	 */
	public String richiediPropostaScambio(Scambio scambio) throws SQLException {
		System.out.println("\nScegli i parametri per la proposta di scambio");
		String luogo = getLuogoProposta(scambio);
		String giorno = getDataProposta(scambio);
		String ora = getOraProposta(scambio);
		return "Luogo: " + luogo + "\nGiorno: " + giorno + "\nOra: " + ora;
	}//stampa intervallo orario
	public String stampaIntervallo(IntervalloOra intervallo) {
		String stringaintervallo = intervallo.getInizio().format(DateTimeFormatter.ISO_TIME) + " - "
				+ intervallo.getFine().format(DateTimeFormatter.ISO_TIME);
		return stringaintervallo;
	}//stampa scambio
	public String stampaScambio(Scambio scambio) {
		StringBuffer sb = new StringBuffer();
		sb.append("Piazza: " + scambio.getPiazza() + "\n");
		sb.append("Luoghi: " + scambio.getLuogo() + "\n");
		sb.append("Giorni: " + scambio.getGiorni() + "\n");
		sb.append("Intervalli orari: " + scambio.getOrari() + "\n");
		sb.append("Giorni validita' offerta: " + scambio.getScadenza() + "\n\n");
		return sb.toString();
	}
}
