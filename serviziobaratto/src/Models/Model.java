package Models;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import GestioneDB.Database;
import GestioneDB.IDatabase;

public class Model {
	private IDatabase db;
	private MicorserviceScambio microscambio;
	private int idRadice=1;

	/**
	 * @throws Exception
	 * 
	 */
	public Model() {
		super();
		try {
			this.db = gestisciDB();
			this.microscambio=new MicorserviceScambio();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Account getAccount(String nome, String password) throws SQLException {
		return db.getAccountDB(nome, password);
	}

	public boolean controlloNomeUtente(String name) throws SQLException {
		if (db != null) { // Verifica se l'oggetto db � stato inizializzato
			return db.controlloNomeUtente(name);
		} else {
			throw new IllegalStateException("L'oggetto db non � stato inizializzato correttamente");
		}
	}

	public void salvaAccount(Account account) throws SQLException {
		db.salvaAccount(account);
	}

	public void salvaCategoria(Categoria categoria) throws SQLException {
		db.salvaCategoria(categoria);
	}

	public void salvaCampo(Campo campo) throws SQLException {
		db.salvaCampo(campo);
	}

	public void aggiornaCategoria(Categoria categoria) throws SQLException {
		db.aggiornaCategoria(categoria);
	}

	public List<Campo> getListaCampo(Categoria categoria) throws SQLException {
		return db.getCampoDaIdCategoria(categoria.getId());
	}

	public void salvaAttributoArticolo(AttributoArticolo attributo) throws SQLException {
		db.salvaAttributoArticolo(attributo);
	}

	public List<Offerta> getListaOfferte(int idUser, int stato) throws SQLException {
		return db.getListaOffertaAutoreStato(idUser, stato);
	}

	public Categoria getCategoria(int idCategoria) throws SQLException {
		return db.getCategoriaPerId(idCategoria);
	}

	public void salvaArticolo(Articolo articolo) throws SQLException {
		db.salvaArticolo(articolo);
	}

	public Articolo getArticolo(int idArticolo) throws SQLException {
		return db.getArticoloDaId(idArticolo);
	}

	public List<Categoria> getFigli(int idPadre) throws SQLException {
		return db.getFigli(idPadre);
	}

	public List<Campo> getListaCampi(int idCategoria) throws SQLException {
		return db.getListaCampi(idCategoria);
	}

	public List<Offerta> getListaStessoStatoOfferta(int offertaInScambio) throws SQLException {
		return db.getListaStessoStatoOfferta(offertaInScambio);
	}

	public List<Integer> getListaIdCampi(int idCategoria) throws SQLException {
		List<Integer> listaIdCampi = new ArrayList<Integer>();
		List<Campo> listaCampiCategoria = getListaCampi(idCategoria);
		for (Campo campo : listaCampiCategoria) {
			listaIdCampi.add(campo.getId());
		}
		return listaIdCampi;
	}

	public Offerta getOffertadaIdArticolo(int idArticolo) throws SQLException {
		return db.getOffertadaIdArticolo(idArticolo);
	}

	public Offerta getOfferta(int idOfferta) throws SQLException {
		return db.getOffertadaId(idOfferta);
	}

	public List<Offerta> getListaOffertaAutore(int idUser) throws SQLException {
		return db.getListaOffertaAutore(idUser);
	}

	public List<Categoria> getAllCategorie() throws SQLException {
		return db.getAllCategorie();
	}

	public boolean salvaArticolo(boolean scelta, Articolo articolo) throws SQLException {
		if (scelta) {
			salvaArticolo(articolo);
			return true;
		}
		return false;
	}

	public List<Articolo> getListaArticoli(int idCategoria) throws SQLException {
		return db.getListaArticoli(idCategoria);
	}

	public boolean isOffertaStato(int idArticolo, int stato) throws SQLException {
		return db.isOffertaStato(idArticolo, stato);
	}

	public void salvaOfferta(Offerta offerta) throws SQLException {
		db.salvaOfferta(offerta);
	}

	public void aggiornaOfferta(Offerta offerta) throws SQLException {
		db.aggiornaOfferta(offerta);
	}

	public void salvaStatoOfferta(StatoOfferta statoOfferta) throws SQLException {
		db.salvaStatoOfferta(statoOfferta);
	}

	public void salvaBaratto(Baratto baratto) throws SQLException {
		db.salvaBaratto(baratto);
	}

	public Scambio getUltimoScambio() throws SQLException {
		return microscambio.getUltimoMicroserviceScambio();
	}

	public List<Baratto> getBarattiScaduti(int scadenza) throws SQLException {
		return db.getBarattiScaduti(scadenza);
	}

	public void eliminaBaratto(Baratto baratto) throws SQLException {
		db.eliminaBaratto(baratto);
	}

	public List<Offerta> getListaOfferta(int idUser, int stato) throws SQLException {
		return db.getListaOffertaAutoreStato(idUser, stato);
	}

	public Baratto getBaratto(int idOfferta) throws SQLException {
		return db.getBarattoOfferta(idOfferta);
	}

	public void aggiornaBaratto(Baratto baratto) throws SQLException {
		db.aggiornaBaratto(baratto);
	}

	public void salvaScambio(Scambio scambio) throws SQLException {
		microscambio.salvaMicroserviceScambio(scambio);
	}

	/**
	 * Metodo che restituisce la lista degli articoli nel medesimo stato
	 * 
	 * @param idCategoria
	 * @param stato
	 * @return lista
	 * @throws SQLException
	 */
	public List<Articolo> getListaArticoliOfferte(int idCategoria, int stato) throws SQLException {
		List<Articolo> listaArticoli = getListaArticoli(idCategoria);
		ArrayList<Articolo> elementiDaRimuovere = new ArrayList<>();
		for (Articolo elemento : listaArticoli) {
			if (!isOffertaStato(elemento.getId(), stato))
				elementiDaRimuovere.add(elemento);
		}
		listaArticoli.removeAll(elementiDaRimuovere);
		return listaArticoli;
	}

	// metodo per creare lista articoli da lista offerte
	public List<Articolo> getListaArticoli(List<Offerta> listaOfferte) throws SQLException {
		List<Articolo> listaArticoli = new ArrayList();
		for (Offerta elemento : listaOfferte) {
			Articolo articolo = getArticolo(elemento.getIdArticolo());
			listaArticoli.add(articolo);
		}
		return listaArticoli;
	}

	// metodo per creare lista categorie percorso fino id=0
	public List<Integer> getListaPercorsoCategorie(Categoria categoria, List<Integer> lista) throws SQLException {
		if (categoria.getId() == idRadice) {
			return lista;
		} else {
			int idPadre = categoria.getIdPadre();
			lista.add(categoria.getId());
			categoria = getCategoria(idPadre);
			getListaPercorsoCategorie(categoria, lista);
		}
		return lista;
	}

	// metodo per creare la lista campi per attributi articolo
	public List<Campo> getListaCampiArticolo(Articolo articolo) throws SQLException {
		int idCategoria = articolo.getIdCategoria();
		Categoria categoria = getCategoria(idCategoria);
		List<Integer> listaCategorie = new ArrayList<>();
		List<Campo> listaCampi = new ArrayList<>();
		listaCategorie = getListaPercorsoCategorie(categoria, listaCategorie);
		for (int id : listaCategorie) {
			List<Campo> listaCampiCategoria = getListaCampi(id);
			listaCampi.addAll(listaCampiCategoria);
		}
		return listaCampi;

	}

//metodo per controllare se nome gi� presente
	public boolean checkUnivocitaNome(String nome) throws SQLException {
		List<Categoria> listaRadici = getAllCategorie();
		List<String> listaNomi = new ArrayList<>();
		for (Categoria categoria : listaRadici) {
			listaNomi.add(categoria.getNome());
		}
		if (listaNomi.contains(nome)) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo che restituisce la categoria generatrice della gerarchia
	 * 
	 * @param categoria_figlia
	 * @return categoria_generatrice
	 * @throws SQLException
	 */
	public Categoria getPadreSupremo(Categoria categoria) throws SQLException {
		if (categoria.getIdPadre() == idRadice) {
			return categoria;
		} else {
			Categoria cat = getCategoria(categoria.getIdPadre());
			categoria = getPadreSupremo(cat);
		}
		return categoria;
	}

	/**
	 * Metodo per identificare il numero di categorie con lo stesso nome
	 * 
	 * @param categoria
	 * @return conta_nomeCategorie
	 * @throws SQLException
	 */
	private int checkDoppione(Categoria categoria) throws SQLException {
		String nome = categoria.getNome();
		int conta = 0;
		for (Categoria all : getAllCategorie()) {
			if (all.getNome().equals(nome)) {
				conta++;
			}
		}
		return conta;
	}

	// conta numero figli
	public int numeroFigliCategoria(Categoria categoria) throws SQLException {
		ArrayList<Categoria> listaFigli = new ArrayList<>();
		return creaListaDiscendenti(categoria, listaFigli).size();
	}

	/**
	 * Metodo per creare la lista delle categorie discendenti da una categoria
	 * 
	 * @param categoria
	 * @param lista_categorie
	 * @return lista_categorie
	 * @throws SQLException
	 */
	public List<Categoria> creaListaDiscendenti(Categoria categoria, List<Categoria> listaCategorie)
			throws SQLException {

		List<Categoria> figli = getFigli(categoria.getId());

		listaCategorie.addAll(figli);
		Iterator<Categoria> i = figli.iterator();
		while (i.hasNext()) {
			Categoria f = (Categoria) i.next();
			creaListaDiscendenti(f, listaCategorie);
		}
		return listaCategorie;
	}

	/**
	 * Metodo per creare la lista di tutte le foglie presenti nel ramo della
	 * categoria padre
	 * 
	 * @param categoria
	 * @return
	 * @throws SQLException
	 */
	public List<Categoria> creaListaFoglieRamoGerarchia(Categoria categoria) throws SQLException {
		ArrayList<Categoria> listaFoglie = new ArrayList<>();
		if (getFigli(categoria.getId()).isEmpty()) {
			listaFoglie.add(categoria);
			return listaFoglie;
		}
		creaListaDiscendenti(categoria, listaFoglie);
		ArrayList<Categoria> elementiDaRimuovere = new ArrayList<>();
		for (Categoria elemento : listaFoglie) {
			if (elemento.getFoglia() == false)
				elementiDaRimuovere.add(elemento);
		}
		listaFoglie.removeAll(elementiDaRimuovere);
		return listaFoglie;
	}

	/**
	 * Metodo che restituisce la lista delle offerte relative alla lista di articoli
	 * 
	 * @param listaArticoli
	 * @return
	 * @throws SQLException
	 */
	public List<Offerta> getListaOfferte(List<Articolo> listaArticoli) throws SQLException {
		List<Offerta> listaOfferte = new ArrayList<>();
		for (Articolo elemento : listaArticoli) {
			Offerta offerta = getOffertadaIdArticolo(elemento.getId());
			listaOfferte.add(offerta);
		}
		return listaOfferte;
	}

	// metodo che restituisce la lista delle offerte con cui si puo barattare
	public List<Offerta> getListaOfferteBarattare(Offerta offerta, int idUser, int statoOfferta) throws SQLException {
		List<Articolo> listaArticoli = getListaArticoli(offerta, statoOfferta);
		List<Offerta> listaOfferta = getListaOfferte(listaArticoli);
		List<Offerta> elementiDaRimuovere = new ArrayList<>();
		for (Offerta o : listaOfferta) {
			if (o.getIdUser() == idUser)
				elementiDaRimuovere.add(o);
		}
		listaOfferta.removeAll(elementiDaRimuovere);
		return listaOfferta;
	}

	// metodo che restituisce la lista degli articoli nello stesso stato della
	// stessa categoria dell'offerta
	public List<Articolo> getListaArticoli(Offerta offerta, int stato) throws SQLException {
		int idArticoloA = offerta.getIdArticolo();
		Articolo articoloA = getArticolo(idArticoloA);
		int idCategoriaA = articoloA.getIdCategoria();
		return getListaArticoliOfferte(idCategoriaA, stato);
	}

	/**
	 * Metodo per creare e salvare un'offerta
	 * 
	 * @param articolo
	 * @throws SQLException
	 */
	public void creaOfferta(Articolo articolo, int idUser) throws SQLException {
		Offerta offerta = new Offerta(articolo.getId(), idUser);
		salvaOfferta(offerta);
		StatoOfferta statoOfferta = new StatoOfferta(offerta.getId());
		salvaStatoOfferta(statoOfferta);
	}

	/**
	 * Metodo per modificare lo stato di un'offerta e salvarla
	 * 
	 * @param idOfferta
	 * @param stato
	 * @return stringa
	 * @throws SQLException
	 */
	public void modificaOfferta(int idOfferta, int stato) throws SQLException {
		Offerta offerta = getOfferta(idOfferta);
		offerta.setStato(stato);
		aggiornaOfferta(offerta);
		aggiornaStatoOfferta(offerta, stato);
	}

	// metodo per aggiornare lo stato dell'offerta
	public void aggiornaStatoOfferta(Offerta offerta, int nuovoStatoOfferta) throws SQLException {
		StatoOfferta statoOfferta = new StatoOfferta(nuovoStatoOfferta, offerta.getId());
		// modificaOfferta(offerta, nuovoStatoOfferta);
		salvaStatoOfferta(statoOfferta);
	}

	// Metodo che controlla la lista dei baratti aperti e rimuove i baratti scaduti
	public void controlloOfferteInBaratto(int statoScadenza) throws SQLException {
		Scambio scambio = getUltimoScambio();
		int scadenza = scambio.getScadenza();

		List<Baratto> listaBarattoScadute = getBarattiScaduti(scadenza);
		for (Baratto baratto : listaBarattoScadute) {
			int idOffertaA = baratto.getIdOffertaA();
			int idOffertaB = baratto.getIdOffertaB();
			modificaOfferta(idOffertaA, statoScadenza);
			modificaOfferta(idOffertaB, statoScadenza);
			eliminaBaratto(baratto);
		}

	}

	// Metodo per controllare se un orario � all'inteno di un intervallo
	public boolean controlloCompatibilitaIntervalloOra(IntervalloOra intervallo, LocalTime timecontrollo) {
		boolean validita = timecontrollo.isAfter(intervallo.getInizio())
				&& timecontrollo.isBefore(intervallo.getFine());

		return validita;
	}

	// Metodo per controllare se un intervallo contiene un altro intervallo
	public boolean controlloCompatibilitaIntervalloIntervallo(IntervalloOra intervallo1, IntervalloOra intervallo2) {
		boolean validita1 = intervallo1.getInizio().isBefore(intervallo2.getInizio())
				&& intervallo1.getFine().isAfter(intervallo2.getFine());
		boolean validita2 = intervallo2.getInizio().isBefore(intervallo1.getInizio())
				&& intervallo2.getFine().isAfter(intervallo1.getFine());
		boolean validita = validita1 || validita2;

		return validita;
	}

	// metodo per richiamare il db
	private static Database gestisciDB() throws Exception {
		Database myDataBase = new Database();
		myDataBase.gestisciDatabase();
		return myDataBase;
	}

}