package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Models.*;
import Util.MyMenu;
import InterfacciaUtente.*;
import ModelCsv.*;
import org.json.*;

public class Control {
	private int offertaAperta = 0;
	private int offertaRitirata = 1;
	private int offertaAccoppiata = 2;
	private int offertaSelezionata = 3;
	private int offertaInScambio = 4;
	private int offertaChiusa = 5;
	
	private int idUser;
	int idRadice = 1;
	
	private Model model;
	private ViewGerarchia viewGerarchia;
	private ViewElementi viewElementi;
	private ViewScambio viewScambio;
	private ViewFile viewFile;
	private ViewUtente viewUtente;

	/**
	 * @param db
	 */
	public Control() {
		super();
		
		this.model = new Model();
		this.viewGerarchia = new ViewGerarchia();
		this.viewScambio = new ViewScambio();
		this.viewElementi = new ViewElementi();
		this.viewFile = new ViewFile();
		this.viewUtente = new ViewUtente();
	}

/////////////////interfaccia gerarchia//////////////////
	public String richiediNomeCategoria() throws SQLException {
		String nome;
		do {
			nome = viewGerarchia.richiediNome();
		} while (model.checkUnivocitaNome(nome));
		return nome;
	}

	// metodo per creare una gerarchia
	public void creaGerarchia() throws SQLException {
		String nome = richiediNomeCategoria();
		String descrizione = viewGerarchia.richiediDescrizione();
		Categoria categoria = new Categoria(nome, descrizione, true, idRadice);
		model.salvaCategoria(categoria);
		creaDescrizioneGerarchia(categoria.getId());
		creaStatoConservazioneDescrizioneGerarchia(categoria.getId());
	}

	// metodo per creare un campo
	public void creaCampo(int idCategoria) throws SQLException {
		viewGerarchia.stampaStringaCampo();
		String nomeCampo = viewGerarchia.richiediNome();
		Boolean statoCompilazione = viewGerarchia.richiediStatoCompilazione();
		Campo campo = new Campo(nomeCampo, statoCompilazione, idCategoria);
		model.salvaCampo(campo);
	}

	// metodo per creare i campi richiesti
	public void creaCampi(int idCategoria) throws SQLException {
		int numeroCampi = viewGerarchia.richiediNumeroCampi();
		for (int i = 0; i < numeroCampi; i++) {
			creaCampo(idCategoria);
		}
	}

	// campo descrizione
	public void creaDescrizioneGerarchia(int idCategoria) throws SQLException {
		Boolean statoCompilazione = false;
		Campo campo = new Campo(viewElementi.descrizione(), statoCompilazione, idCategoria);
		model.salvaCampo(campo);
	}

	// campo stato di conservazione
	public void creaStatoConservazioneDescrizioneGerarchia(int idCategoria) throws SQLException {
		Boolean statoCompilazione = true;
		Campo campo = new Campo(viewElementi.statoConservazione(), statoCompilazione, idCategoria);
		model.salvaCampo(campo);
	}

	// metodo per far scegliere una categoria
	public Categoria sceltaCategoria() throws SQLException {
		List<Categoria> listaCategorie = model.getAllCategorie();
		List<Categoria> listaCategorieFiltrata = new ArrayList<>();

		// Iterazione sulla lista originale e filtraggio delle categorie
		for (Categoria categoria : listaCategorie) {
		    if (categoria.getId()!=idRadice) {
		        listaCategorieFiltrata.add(categoria);
		    }
		}
		int scelta = viewGerarchia.menuCategorie(listaCategorieFiltrata);
		return listaCategorieFiltrata.get(scelta);
	}

	// metodo per popolare una gerarchia
	public void popolaGerarchia() throws SQLException {
		Categoria categoriaPadre = sceltaCategoria();
		if (model.numeroFigliCategoria(categoriaPadre) < 2) {
			int numeroCategorieDaCreare = viewGerarchia.richiediNumeroCategorie();
			for (int i = 0; i < numeroCategorieDaCreare; i++) {
				creaCategoria(categoriaPadre);
			}
		} else {
			creaCategoria(categoriaPadre);
		}
	}

	// metodo per far creare una caegoria all'utente
	void creaCategoria() throws SQLException {
		Categoria gerarchia = sceltaGerarchia();
		List<Categoria> listaCategorie = new ArrayList<Categoria>();
		model.creaListaDiscendenti(gerarchia, listaCategorie);
		if (listaCategorie.isEmpty()) listaCategorie.add(gerarchia);
		Categoria categoria = listaCategorie.get(viewGerarchia.menuCategorie(listaCategorie));
		creaCategoria(categoria);
	}
	

	// metodo per creare una categoria
	public void creaCategoria(Categoria categoriaPadre) throws SQLException {
		String nome = richiediNomeCategoria();
		String descrizione = viewGerarchia.richiediDescrizione();
		Categoria categoria = new Categoria(nome, descrizione, true, categoriaPadre.getId());
		categoriaPadre.setFoglia(false);
		model.salvaCategoria(categoria);
		model.aggiornaCategoria(categoriaPadre);
		creaCampi(categoria.getId());
	}

	// metodo per stampare tutte le info di tutte le categorie
	public void printAll() throws SQLException {
		List<Categoria> listaGerarchie = model.getFigli(idRadice);
		for (Categoria gerarchia : listaGerarchie) {
			viewGerarchia.stampaRadice(gerarchia, model.getListaCampi(gerarchia.getId()));
			List<Categoria> listaCategorie = new ArrayList<>();
			model.creaListaDiscendenti(gerarchia, listaCategorie);
			for (Categoria categoria : listaCategorie) {
				viewGerarchia.stampaCategoria(categoria, model.getListaCampi(categoria.getId()));
			}

		}
	}
	// interfaccia scambio

	// Metodo che restituisce un intervallo di tempo
	private IntervalloOra getIntervalloOrario() {
		viewScambio.richiediOraInizioString();
		LocalTime oraInizio = viewScambio.getOra();
		LocalTime oraFine;
		do {
			viewScambio.richiediOraFineString();
			oraFine = viewScambio.getOra();
		} while (oraFine.isBefore(oraInizio));
		IntervalloOra intervallo = new IntervalloOra(oraInizio, oraFine);
		return intervallo;
	}

	// Metodo per richiedere all'utente un intervallo aggiuntivo
	private IntervalloOra getIntervalloOrario(IntervalloOra intervallo) {
		viewScambio.richiediIntervalloString();
		LocalTime oraFine;
		LocalTime oraInizio;
		IntervalloOra newintervallo = null;
		do {
			oraInizio= richiediOraIniziale(intervallo);
			oraFine=richiediOraFine(intervallo, oraInizio);
			newintervallo = new IntervalloOra(oraInizio, oraFine);
		} while (controlloCompatibilitaIntervalloIntervallo(intervallo, newintervallo));

		return newintervallo;
	}
	//metodo per richiedere ora iniziale intervallo
	private LocalTime richiediOraIniziale(IntervalloOra intervallo) {
		LocalTime oraInizio;
		boolean inputValido = false;
		do {
			viewScambio.richiediOraInizioString();
			oraInizio = viewScambio.getOra();
			if (model.controlloCompatibilitaIntervalloOra(intervallo, oraInizio)) {
				viewScambio.oraNonValidaString();
			} else {
				inputValido = true;
			}
		} while (!inputValido);
		return oraInizio;
	}
	//metodo per richiedere ora finale intervallo
	private LocalTime richiediOraFine(IntervalloOra intervallo,LocalTime oraInizio) {
		LocalTime oraFine;
		boolean inputValido = false;
		do {
			viewScambio.richiediOraFineString();
			oraFine = viewScambio.getOra();
			if (oraFine.isAfter(oraInizio) && !model.controlloCompatibilitaIntervalloOra(intervallo, oraFine)) {
				inputValido = true;
			} else {
				viewScambio.oraNonValidaString();
			}
		} while (!inputValido);
		return oraFine;
	}

	// Metodo per controllare se un intervallo contiene un altro intervallo
	private boolean controlloCompatibilitaIntervalloIntervallo(IntervalloOra intervallo1, IntervalloOra intervallo2) {
		boolean validita = model.controlloCompatibilitaIntervalloIntervallo(intervallo1, intervallo2);
		if (validita)
			viewScambio.intervalloNonValidaString();
		return validita;
	}

	// Metodo per chiedere all'utente l'inserimento degli intervalli (max 2
	// intervalli)
	public String intervalli() {
		StringBuilder sb = new StringBuilder();
		viewScambio.intervalliString();
		IntervalloOra intervallo1 = getIntervalloOrario();
		sb.append(viewScambio.stampaIntervallo(intervallo1));
		boolean sceltaintervallo = viewScambio.richiediAltroIntervallo();
		if (sceltaintervallo) {
			IntervalloOra intervallo2 = getIntervalloOrario(intervallo1);
			sb.append(", " + viewScambio.stampaIntervallo(intervallo2));
		}
		// System.out.println(sb);
		return sb.toString();
	}

	// Metodo per impostare i giorni in cui e' possibile effettuare lo scambio
	public void impostaGiorni(Scambio scambio) {
		ArrayList<Integer> giorni = viewScambio.richiediImpostaGiorni(scambio);
		scambio.setGiorni(viewScambio.listaGiorniTestuale(giorni));
	}

	// Metodo per creare i parametri di scambio
	void creaParametriScambio() throws SQLException {

		Scambio scambio = new Scambio();
		scambio.setPiazza(viewScambio.chiediPiazza());
		scambio.setLuogo(viewScambio.getStringLuoghi());
		impostaGiorni(scambio);
		scambio.setOrari(intervalli());
		scambio.setScadenza(viewScambio.impostaScadenza());
		model.salvaScambio(scambio);
	}

	// Metodo per stampare i paraetri di scambio
	void stampaScambio() {
		Scambio scambio;
		try {
			scambio = model.getUltimoScambio();
			viewScambio.stampaStringa(viewScambio.stampaScambio(scambio));
		} catch (Exception e) {
			viewScambio.noElementi();
		}
	}

	// Metodo per richiedere i parametri dello scambio
	public String selezionaPropostaScambio() throws SQLException {
		Scambio scambio = model.getUltimoScambio();
		return viewScambio.richiediPropostaScambio(scambio);
	}

	////////////////////////// interfaccia elementi////////////////////
	// metodo per settare e salvare gli attributi di un articolo
	public void setAttributiArticolo(Articolo articolo) throws SQLException {
		System.out.println(viewElementi.inserisciAttributiString(articolo));
		List<Campo> listaCampiArticolo = model.getListaCampiArticolo(articolo);
		int idArticolo = articolo.getId();
		for (Campo campo : listaCampiArticolo) {
			String valoreAttributo = viewElementi.valoreAttributo(campo.getObbligatorio(), campo.getNome());
			AttributoArticolo attributo = new AttributoArticolo(valoreAttributo, campo.getId(), idArticolo);
			model.salvaAttributoArticolo(attributo);
		}

	}

	// metodo per confermare articolo
	public boolean confermaArticolo(Articolo articolo) throws SQLException {
		boolean scelta = viewElementi.richiestaConfermaArticolo(articolo.getNome());
		return model.salvaArticolo(scelta, articolo);
	}

	// metodo per visualizzare tutte le offerte di un utente
	public void visualizzaTutteLeOfferte() throws SQLException {
		List<Offerta> listaOfferte = model.getListaOffertaAutore(idUser);
		List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
		viewElementi.stampaElencoOfferte(listaOfferte, listaArticoli);
	}

	// metodo per visualizzare tutte le offerte in scambio
	public void visualizzaTutteOfferteInScambio() throws SQLException {
		List<Offerta> listaOfferte = model.getListaStessoStatoOfferta(offertaInScambio);
		List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
		viewElementi.stampaElencoOfferte(listaOfferte, listaArticoli);
	}

	// Metodo per visualizzare tutte le offerte chiuse
	public void visualizzaTutteOfferteChiuse() throws SQLException {
		List<Offerta> listaOfferte = model.getListaStessoStatoOfferta(offertaChiusa);
		List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
		viewElementi.stampaElencoOfferte(listaOfferte, listaArticoli);
	}

	// metodo per scegliere una gerarchia
	public Categoria sceltaGerarchia() throws SQLException {
		List<Categoria> listaRadici = model.getFigli(idRadice);
		int sceltaRadice = viewGerarchia.menuGerarchie(listaRadici);
		return listaRadici.get(sceltaRadice);
	}

	// metodo per richiedere la categoria foglia
	public Categoria getCategoriaFoglia() throws SQLException {
		Categoria radice = sceltaGerarchia();
		List<Categoria> listaFoglieRadice = model.creaListaFoglieRamoGerarchia(radice);
		int sceltaCategoria = viewGerarchia.menuCategorie(listaFoglieRadice);
		Categoria categoriaScelta = listaFoglieRadice.get(sceltaCategoria);
		return categoriaScelta;

	}

//Metodo per visualizzare le offerte in un determinato stato richiesto
	public void visualizzaOfferteStato() throws SQLException {
		int stato = viewElementi.menuStato();
		List<Offerta> listaOfferte = model.getListaOfferte(idUser, stato);
		if (listaOfferte.isEmpty()) {
			viewElementi.noElementi();
			return;
		}
		List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
		
		viewElementi.stampaElencoOfferte(listaOfferte, listaArticoli);
	}

	/**
	 * Metodo per menu offerte aperte per categoria
	 * 
	 * @throws SQLException
	 */
	public void stampaOfferteAperteCategoria() throws SQLException {
		stampaOfferteCategoria(offertaAperta);
	}

	/**
	 * Metodo per menu offerte in scambio per categoria
	 * 
	 * @throws SQLException
	 */
	public void stampaOfferteInScambioCategoria() throws SQLException {
		stampaOfferteCategoria(offertaInScambio);
	}

	/**
	 * Metodo per menu offerte chiuse per categoria
	 * 
	 * @throws SQLException
	 */
	public void stampaOfferteChiuseCategoria() throws SQLException {
		stampaOfferteCategoria(offertaChiusa);
	}

	/**
	 * Metodo per stampare le offerte di un determinato stato
	 * 
	 * @param stato
	 * @throws SQLException
	 * @precondition listaOfferte != null
	 */
	public void stampaOfferteCategoria(int stato) throws SQLException {
		Categoria categoriaFoglia = getCategoriaFoglia();
		List<Articolo> listaArticoli = model.getListaArticoliOfferte(categoriaFoglia.getId(), stato);
		List<Offerta> listaOfferte = model.getListaOfferte(listaArticoli);
		if (listaOfferte.isEmpty()) {
			viewElementi.noElementi();
		} else {
			viewElementi.stampaElencoOfferte(listaOfferte, listaArticoli);
		}
	}

	// Metodo per selezionare l'offerta con cui si vuole barattare la propria
	// offerta aperta
	public Offerta getOfferteAperteB(Offerta offerta) throws SQLException {
		List<Offerta> listaOfferte = model.getListaOfferteBarattare(offerta, idUser, offertaAperta);
		if (!listaOfferte.isEmpty()) {
			List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
			int offertaScelta = viewElementi.menuOfferte(listaOfferte, listaArticoli);
			Offerta offertaB = listaOfferte.get(offertaScelta);
			return offertaB;
		}
		return null;
	}

	// Metodo per richiedere un'offerta aperta
	public Offerta getOffertaAperta() throws SQLException {

		List<Offerta> listaOfferte = model.getListaOfferte(idUser, offertaAperta);
		if (listaOfferte.isEmpty()) {
			viewElementi.noElementi();
		} else {
			List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
			int scelta = viewElementi.menuOfferte(listaOfferte, listaArticoli);
			Offerta offerta = listaOfferte.get(scelta);
			return offerta;
		}
		return null;
	}

	// metodo per modificare una offerta
	public void modificaOfferta(Offerta offerta, int stato) throws SQLException {
		model.modificaOfferta(offerta.getId(), stato);
		viewElementi.stampaModificaOfferta(offerta.getId(), viewElementi.statoOffertaString(stato));
	}

	// Metodo per consentire all'utente di ritirare un'offerta scelta
	public void ritiraOfferta() throws SQLException {
		Offerta offerta = getOffertaAperta();
		if (offerta != null) {
			modificaOfferta(offerta, offertaRitirata);
		}
	}

	// Metodo per creare e salvare un articolo dalle informazioni ricevute dall'user
	public Articolo creaArticolo(int idUser) throws SQLException {
		String nome = viewElementi.richiediNome();
		int idCategoria = getCategoriaFoglia().getId();
		Articolo articolo = new Articolo(idCategoria, nome, idUser);
		return articolo;
	}

	// Metodo per richiedere e pubblicare tutte le informazioni inerenti a un
	// articolo ed i suoi attributi
	public void pubblicazioneArticolo() throws SQLException {
		Articolo articolo = creaArticolo(idUser);
		if (confermaArticolo(articolo)) {
			setAttributiArticolo(articolo);
			model.creaOfferta(articolo, idUser);
		}
	}

	// metodo menu crea baratto
	public void creaBaratto() throws SQLException {
		viewElementi.stampaOffertaApertaBarattoString();
		Offerta offertaA = getOffertaAperta();
		if (offertaA != null) {
			Offerta offertaB = getOfferteAperteB(offertaA);
			if (offertaB == null) {
				viewElementi.noElementi();
			} else {
				viewElementi.propostaInviataString();
				modificaOfferta(offertaA, offertaAccoppiata);
				modificaOfferta(offertaB, offertaSelezionata);
				Baratto baratto = new Baratto(offertaA.getId(), offertaB.getId(), viewElementi.inviata());
				model.salvaBaratto(baratto);
			}
		}
	}

	// Metodo che controlla la lista dei baratti aperti e rimuove i baratti scaduti
	// portando lo stato delleofferte in aperto
	public void controlloOfferteInBaratto() throws SQLException {
		model.controlloOfferteInBaratto(offertaAperta);
	}

	// Metodo per visualizzare le offerte nello stato per un baratto
	public List<Baratto> visualizzaOfferte(int stato) throws SQLException {
		List<Baratto> listaBaratti = new ArrayList();
		List<Offerta> listaOfferte = model.getListaOfferta(idUser, stato);
		if (listaOfferte.isEmpty()) {
			viewElementi.noElementi();
		} else {
			int i = 0;
			viewElementi.stampaListaOfferteString();
			for (Offerta offerta : listaOfferte) {
				int idOfferta = offerta.getId();
				Baratto baratto = model.getBaratto(idOfferta);
				if (idOfferta == baratto.getOffertaInRisposta()) {
					listaBaratti.add(baratto);
					Articolo articoloSelezionato = model.getArticolo(offerta.getIdArticolo());
					Offerta offertaAccoppiata= model.getOfferta(baratto.getIdOffertaA());
					if (offertaAccoppiata.getId()==offerta.getId())offertaAccoppiata=model.getOfferta(baratto.getIdOffertaB());
					Articolo articoloAccoppiato = model.getArticolo(offertaAccoppiata.getIdArticolo());
					Scambio scambio = model.getUltimoScambio();
					String dataScadenzaStringa = viewElementi.dataString(baratto, scambio);
					String stringa = viewElementi.articoliInScambioString(articoloSelezionato, articoloAccoppiato,
							dataScadenzaStringa);
					viewElementi.stampaRigaMenuString(i, stringa);
				}
				i++;

			}
			if (listaBaratti.isEmpty())
				viewElementi.aspettandoRisposteString();
		}
		return listaBaratti;
	}

	// Metodo per gestire la risposta a una proposta di baratto
	public void rispondiPropostaBaratto(List<Baratto> listaBaratti) throws SQLException {
		int sceltaProposta = viewElementi.scegliPropostaString(listaBaratti.size() - 1);
		Baratto barattoAccettato = listaBaratti.get(sceltaProposta);

		Offerta offerta = model.getOfferta(barattoAccettato.getOffertaInRisposta());
		if (offerta.getStato() == offertaInScambio) {
			creaRispostaBaratto(offerta, barattoAccettato);
		} else
			creaPropostaBaratto(offerta, barattoAccettato);
	}

//Metodo per creare la risposta per un baratto
	public void creaRispostaBaratto(Offerta offerta, Baratto barattoAccettato) throws SQLException {
		viewElementi.stampaPropostaBaratto(offerta, barattoAccettato);
		boolean accettaOfferta = viewElementi.accettaOffertaString();
		if (accettaOfferta) {
			model.modificaOfferta(barattoAccettato.getIdOffertaA(), offertaChiusa);
			model.modificaOfferta(barattoAccettato.getIdOffertaB(), offertaChiusa);
			// potrei anche salvare lo stato dei baratti aperto e chiuso se volessi
			// mantenere l'informazione
			model.eliminaBaratto(barattoAccettato);
		} else {
			creaPropostaBaratto(offerta, barattoAccettato);
		}
	}

//metodo per menu offerte
	public void menuOfferteBarattoStato(int stato) throws SQLException {
		List<Baratto> listaBaratti = visualizzaOfferte(stato);
		
		if (!listaBaratti.isEmpty()) {
			boolean propostaBaratto = viewElementi.rispondiPropostaString();
			if (propostaBaratto) {
				rispondiPropostaBaratto(listaBaratti);
			}
		}
	}

	// Metodo per creare una nuova proposta di baratto
	public Baratto creaPropostaBaratto(Offerta offerta, Baratto barattoAccettato) throws SQLException {

		String proposta = selezionaPropostaScambio();
		if (offerta.getId() == barattoAccettato.getIdOffertaA()) {
			barattoAccettato.setPropostaScambioA(proposta);
		} else {
			barattoAccettato.setPropostaScambioB(proposta);
		}
		model.aggiornaBaratto(barattoAccettato);
		model.modificaOfferta(barattoAccettato.getIdOffertaA(), offertaInScambio);
		model.modificaOfferta(barattoAccettato.getIdOffertaB(), offertaInScambio);
		return barattoAccettato;
	}

	/**
	 * Metodo per visualizzare le offerte selezionate e gli articoli in scambio
	 * 
	 * @throws SQLException
	 */
	public void visualizzaOffertaSelezionate() throws SQLException {
		menuOfferteBarattoStato(offertaSelezionata);
	}

	/**
	 * Metodo per visualizzare le offerte in scambio
	 * 
	 * @throws SQLException
	 */
	public void visualizzaOffertaInScambio() throws SQLException {
		menuOfferteBarattoStato(offertaInScambio);
	}

	/**
	 * Metodo per visualizzare l'ultima risposta di un baratto dell'autore ad esso
	 * collegato
	 * 
	 * @throws SQLException
	 * @precondition listaOfferte != null
	 */
	public void visualizzaUltimaRisposteOfferteInScambio() throws SQLException {
		List<Offerta> listaOfferte = model.getListaOfferta(idUser, offertaInScambio);
		if (listaOfferte.isEmpty()) {
			viewElementi.noElementi();
			return;
		}
		List<Articolo> listaArticoli = model.getListaArticoli(listaOfferte);
		int scelta = viewElementi.menuOfferte(listaOfferte, listaArticoli);
		int idOfferta = listaOfferte.get(scelta).getId();
		Baratto baratto = model.getBaratto(idOfferta);
		viewElementi.stampaUltimaProposta(baratto, idOfferta);
		if (idOfferta == baratto.getOffertaInRisposta()) {
			viewElementi.stampaAttesaRispostaString();
		} else {
			viewElementi.stampaTuoTurnoString();
		}

	}

//metodo di menu per stampare le informazioni gerarchie e scambio
	public void stampaInformazioni() throws SQLException {
		printAll();
		viewGerarchia.stampaScambio(model.getUltimoScambio());
	}

	// interfaccia

	private String PASSWORD_CONFIG = "0000";
	private String USER_CONFIG = "config";

//Metodo per reperire le credenziali dell'utente
	public Account login(Account account) throws SQLException {
		// controlla la lista dei baratti aperti TOLTO PER POSTGRESQL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		try {
			controlloOfferteInBaratto();
		} catch (NullPointerException e) {
		}
		int sceltaUtente = viewUtente.menuIniziale();
		if (sceltaUtente == 1) {
			viewUtente.stampaLoginString();
			account = controlloUtente(account);
		} else {
			String nomeFruitore;
			do {
				nomeFruitore = viewUtente.richiediCreaUser();
			} while (controlloNomeUtente(nomeFruitore) && nomeFruitore != USER_CONFIG);
			String passwordFruitore = viewUtente.richiediCreaPassword();
			account = setCredenzialiFruitore(nomeFruitore, passwordFruitore);
		}
		return account;

	}

//metodo per controllare le credenziali
	public Account controlloUtente(Account account) throws SQLException {
		String nome = viewUtente.richiediUser();
		String password = viewUtente.richiediPassword();

		// se entra con credenziali predefinite, gli fa settare le nuove credenziali
		if (nome.equals(USER_CONFIG) && password.equals(PASSWORD_CONFIG)) {
			
			account = setCredenzialiConfiguratore(nome, password); // nuove credenziali configuratore
		} else {
			account = model.getAccount(nome, password);
		}
		return account;
	}

	// Metodo per controllare se un nome e' gia' presente nel database
	private boolean controlloNomeUtente(String name) throws SQLException {
		boolean in = model.controlloNomeUtente(name);
		if (in)
			viewUtente.stampaErroreNome();
		return in;
	}

	// Metodo impostare le credenziali di un Configuratore
	private Account setCredenzialiConfiguratore(String nome, String password) throws SQLException {
		String nomeConfiguratore = null;
		viewUtente.stampaAccessoConfiguratore();
		do {
			nomeConfiguratore = viewUtente.richiediCreaUser();
		} while (controlloNomeUtente(nomeConfiguratore));
		String passwordConfiguratore = viewUtente.richiediCreaPassword();
		Account account = new Configuratore(nomeConfiguratore, passwordConfiguratore);
		model.salvaAccount(account);
		return account;
	}
	// Metodo impostare le credenziali di un Fruitore
	private Account setCredenzialiFruitore(String nome, String password) throws SQLException {
		Account account = new Fruitore(nome, password);
		viewUtente.stampaAccessoFruitore();
		model.salvaAccount(account);
		return account;
	}

	// Metodo per controllare le credenziali dell'utente
	public Account log(Account account) throws SQLException {
		boolean approvato = true;
		do {
			approvato = true;
			account = login(account);

			if (account == null) {
				viewUtente.stampaNoAccount();
				approvato = false;
			} else {
				this.idUser = account.getId();
			}

		} while (!approvato);
		return account;
	}

//metodo per visualizzare il menu corretto in base al tipo dell account
	public void sceltaTipoMenu(Account account) throws SQLException, IOException {
		if (account.getType() == 1) {
			viewMenuConfiguratore();
		} else {
			viewMenuFruitori();
		}
	}

//menu configuratore
	public void viewMenuConfiguratore() throws SQLException, IOException {
		MyMenu menu = viewUtente.menuConfiguratore();

		boolean termina = false;

		do {
			switch (menu.scegli()) {
			case 1:
				creaGerarchia();
				break;
			case 2:
				popolaGerarchia();
				break;
			case 3:
				stampaGerarchia();
				break;
			case 4:
				printAll();
				break;
			case 5:
				creaParametriScambio();
				break;
			case 6:
				stampaOfferteAperteCategoria();
				break;
			case 7:
				stampaOfferteInScambioCategoria();
				break;
			case 8:
				stampaOfferteChiuseCategoria();
				break;
			case 9:
				viewMenuImportazioni();

				break;
			case 0:
				termina = exit();
				break;
			}
		} while (!termina);
	}

	private boolean exit() {
		boolean termina;
		termina = true;
		viewUtente.stampaMsgUscita();
		return termina;
	}

	/**
	 * Metodo che restituisce il Menu per la classe configuratore
	 * 
	 * @throws SQLException
	 */
	private void viewMenuFruitori() throws SQLException {

		MyMenu menu = viewUtente.menuFruitore();

		boolean termina = false;

		do {
			switch (menu.scegli()) {
			case 1:
				stampaInformazioni();
				break;
			case 2:
				pubblicazioneArticolo();
				break;
			case 3:
				ritiraOfferta();
				break;
			case 4:
				stampaOfferteAperteCategoria();
				break;
			case 5:
				visualizzaOfferteStato();
				break;
			case 6:
				creaBaratto();
				break;
			case 7:
				visualizzaOffertaSelezionate();
				break;
			case 8:
				visualizzaOffertaInScambio();
				break;
			case 9:
				visualizzaTutteLeOfferte();
				break;
			case 10:
				visualizzaUltimaRisposteOfferteInScambio();
				break;
			case 0:
				termina = exit();
				break;
			}
		} while (!termina);
	}

	/**
	 * Metodo per creare la stampa dell'albero gerarchico
	 * 
	 * @param stringbuilder
	 * @param categoria
	 * @param numero_profondita'
	 * @return stringbuilder
	 * @throws SQLException
	 */
	private StringBuilder printAlbero(StringBuilder sb, Categoria c, int n) throws SQLException {
		int profondita = 0;
		int idCategoria = c.getId();
		List<Categoria> figli = model.getFigli(idCategoria);
		for (int j = 0; j < n; j++) {
			profondita = profondita + 1;
		}
		sb.append(viewGerarchia.rigaAlbero(profondita, c.getNome()));
		n++;

		Iterator<Categoria> i = figli.iterator();
		while (i.hasNext()) {
			Categoria f = (Categoria) i.next();
			printAlbero(sb, f, n);
		}
		return sb;
	}



	/**
	 * Metodo per stampare l'albero delle gerarchie
	 * 
	 * @throws SQLException
	 */
	void stampaGerarchia() throws SQLException {
		int profondita = 0;
		Categoria categoria = model.getCategoria(idRadice);
		StringBuilder sb = new StringBuilder();
		printAlbero(sb, categoria, profondita);
		viewGerarchia.stampaStringa(sb.toString());
	}

	// menu importazioni file

	public void viewMenuImportazioni() throws SQLException, IOException {
		MyMenu menu = viewFile.menuImportazioni();

		boolean termina = false;

		do {
			switch (menu.scegli()) {
			case 1:
				lettoreCategoria();
				break;
			case 2:
				lettoreCampo();
				break;
			case 3:
				lettoreScambio();
				break;
			case 4:
				lettoreRelazioneCategorieCsv();
				break;

			case 0:
				termina = exit();
				break;
			}
		} while (!termina);
	}

	/**
	 * Metodo che salva le categorie importate
	 * 
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public void lettoreCategoria() throws SQLException, IllegalStateException, FileNotFoundException {
		LettoreCsv lettore = new LettoreCsv();
		List<CategoriaCsv> listaCsv = lettore.leggiFileCategoria();

		for (CategoriaCsv categoriaCsv : listaCsv) {
			Categoria categoria = new Categoria(categoriaCsv.getNome(), categoriaCsv.getDescrizione(),
					categoriaCsv.getFoglia());
			viewFile.stampaFile(viewGerarchia.stampaCategoriaBreve(categoria));
			model.salvaCategoria(categoria);
		}
	}

	/**
	 * Metodo che salva i campi importati
	 * 
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public void lettoreCampo() throws SQLException, IllegalStateException, FileNotFoundException {
		LettoreCsv lettore = new LettoreCsv();
		List<CampoCsv> listaCsv = lettore.leggiFileCampo();

		for (CampoCsv campoCsv : listaCsv) {
			Campo campo = new Campo(campoCsv.getNome(), campoCsv.isObbligatorio(), campoCsv.getIdCategoria());
			viewFile.stampaFile(viewGerarchia.stampaCampo(campo));
			model.salvaCampo(campo);
		}
	}

	/**
	 * Metodo che salva gli scambi importate
	 * 
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public void lettoreScambio() throws SQLException, IllegalStateException, FileNotFoundException {
		LettoreCsv lettore = new LettoreCsv();
		List<ScambioCsv> listaCsv = lettore.leggiFileScambio();

		for (ScambioCsv scambioCsv : listaCsv) {
			Scambio scambio = new Scambio(scambioCsv.getPiazza(), scambioCsv.getLuogo(), scambioCsv.getGiorni(),
					scambioCsv.getOrari(), scambioCsv.getScadenza());
			viewFile.stampaFile(viewScambio.stampaScambio(scambio));
			model.salvaScambio(scambio);
		}
	}

	/**
	 * Metodo che salva le relazioni tra categorie importate
	 * 
	 * @throws SQLException
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public void lettoreRelazioneCategorieCsv() throws SQLException, IllegalStateException, FileNotFoundException {
		LettoreCsv lettore = new LettoreCsv();
		List<RelazioneCategorieCsv> listaCsv = lettore.leggiFileRelazioneCategorie();

		for (RelazioneCategorieCsv relazioneCsv : listaCsv) {
			Categoria categoria = model.getCategoria(relazioneCsv.getIdCategoria());
			categoria.setIdPadre(relazioneCsv.getIdPadre());
			viewFile.stampaFile(viewGerarchia.stampaCategoriaBreve(categoria));
			model.aggiornaCategoria(categoria);
			;
		}
	}

}
