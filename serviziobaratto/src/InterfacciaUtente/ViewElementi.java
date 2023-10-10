package InterfacciaUtente;

import java.sql.SQLException;
import java.util.List;

import Models.Articolo;
import Models.Baratto;
import Models.Offerta;
import Util.InputDati;

public class ViewElementi extends View{
	//metodo per richiedere i valori dell'attributo dell'articolo
	public String valoreAttributo(boolean campoObbligatorio, String nomeCampo) {
		String valoreAttributo;
		if (campoObbligatorio) {
			valoreAttributo = Util.InputDati
					.leggiStringaNonVuota(nomeCampo + " CAMPO OBBILIGATORIO: " + "\nInserisci il valore: ");
		} else {
			valoreAttributo = Util.InputDati.leggiStringa(nomeCampo + " CAMPO NON OBBILIGATORIO: "
					+ "\nInserisci il valore oppure premi invio per continuare: ");
		}
		return valoreAttributo;
	}
	//metodo per chiedere la conferma dell'articolo
	public boolean richiestaConfermaArticolo(String nomeArticolo) {
		return InputDati.leggiBoolean("Vuoi salvare l'articolo " + nomeArticolo + "?");
	}
	// lista offerte con annessi articoli
		public void visualizzaElencoOfferte(List<Offerta> listaOfferte, List<Articolo> listaArticoli, String didascalia) {
			int i = 0;
			StringBuffer sb = new StringBuffer();
			sb.append(corniceTesto(didascalia));
			for (Offerta elemento : listaOfferte) {
				Articolo articolo = listaArticoli.get(i);
				System.out.println((i) + "\tID " + statoOffertaString(elemento.getStato()) + ":" + "\t" + elemento.getId()
						+ "\tArticolo: " + articolo.getNome());
				i++;
			}
		}
		//metodo per stampare l'elenco delle offerte
		public void stampaElencoOfferte(List<Offerta> listaOfferte, List<Articolo> listaArticoli) {
			visualizzaElencoOfferte(listaOfferte, listaArticoli, "Elenco di tutte le offerte");
		}
		// Menu offerte con annesso articolo
		public int menuOfferte(List<Offerta> listaOfferte, List<Articolo> listaArticoli) {
			visualizzaElencoOfferte(listaOfferte, listaArticoli, "\nQuale offerta vuoi selezionare? ");
			int scelta = InputDati.leggiIntero(SCEGLI, 0, listaOfferte.size() - 1);
			return scelta;
		}
		// richiedi attributi stringa
		public String inserisciAttributiString(Articolo articolo) {
			return corniceTesto("Inserisci gli attributi dell'articolo " + articolo.getNome());
		}
		/**
		 * Menu stato offerte
		 * 
		 * @param didascalia
		 * @return
		 * @throws SQLException
		 */
		public int menuStatoOfferte(String didascalia, String[] statoOfferteString) {
			int i = 0;
			System.out.println(corniceTesto(didascalia));
			for (String elemento : statoOfferteString) {
				System.out.println((i) + "\t" + elemento);
				i++;
			}
			int scelta = InputDati.leggiIntero("\nScegli un'opzione", 0, statoOfferteString.length);
			return scelta;
		}

		public int menuStato() {
			return menuStatoOfferte("Seleziona lo stato delle offerte di cui sei autore che vuoi visualizzare ",
					statoOfferteString);
		}

		public void stampaModificaOfferta(int idOfferta, String statoSuccessivo) {
			System.out.println("\nOfferta numero " + idOfferta + " -> " + statoSuccessivo);
		}

		public void stampaOffertaApertaBarattoString() {
			System.out.println("Seleziona un'offerta aperta da barattare: ");
		}

		public void aspettandoRisposteString() {
			System.out.println("\nStai aspettando delle risposte!");
		}

		public void propostaInviataString() {
			System.out.println("\nProposta inviata");
		}
	
		public String articoliInScambioString(Articolo articoloSelezionato, Articolo articoloAccoppiato,
				String dataScadenzaStringa) {
			return articoloSelezionato.getNome() + "\t<-(tuo)scambio(suo)->\t" + articoloAccoppiato.getNome()
					+ "\tScade il: " + dataScadenzaStringa;
		}

		public void stampaListaOfferteString() {
			System.out.println(corniceTesto("Lista offerte:"));
			
		}public int scegliPropostaString(int size) {
			return InputDati.leggiIntero("\nScegli a quale proposta sopra elencata rispondere: ", 0, size);
		}

		public void stampaPropostaBaratto(Offerta offerta, Baratto barattoAccettato) {
			System.out.println("Proposta :");
			if (offerta.getId() == barattoAccettato.getIdOffertaA()) {
				System.out.println("\n" + barattoAccettato.getPropostaScambioB());
			} else {
				System.out.println("\n" + barattoAccettato.getPropostaScambioA());
			}
		}
		// stampa ultima proposta
		public void stampaUltimaProposta(Baratto baratto, int idOfferta) {
			System.out.println("\nUltima proposta inviata");
			System.out.println(CORNICE);
			if (baratto.getIdOffertaA() == idOfferta) {
				System.out.println(baratto.getPropostaScambioA());
			} else {
				System.out.println(baratto.getPropostaScambioB());
			}
			System.out.println(CORNICE);
		}
		public String statoOffertaString(int statoOfferta) {
			return statoOfferteString[statoOfferta];
		}
		public String inviata() {
			return "Proposta inviata";
		}
		public boolean accettaOffertaString() {
			return InputDati.leggiBoolean("Vuoi accettare la proposta di scambio?");
		}

		public boolean rispondiPropostaString() {
			return InputDati.leggiBoolean("\nVuoi rispondere a una proposta di baratto?");
		}
		public void stampaAttesaRispostaString() {
			System.out.println("Sei in attesa di risposta");
		}
		public String descrizione() {
			return "Descrizione";
		}
		public String statoConservazione() {
			return "Stato di conservazione";
		}

		public void stampaTuoTurnoString() {
			System.out.println("E' il tuo turno. Devi rispondere o confermare per procedere all'incontro");
		}


}
