package GestioneDB;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import Models.*;

public class Database implements IDatabase {
int idRadice = 1;
	private Dao<Account, Integer> accountDao;
	private Dao<Categoria, Integer> categoriaDao;
	private Dao<Campo, Integer> campoDao;
	private Dao<Scambio, Integer> scambioDao;
	private Dao<Articolo, Integer> articoloDao;
	private Dao<AttributoArticolo, Integer> attributoArticoloDao;
	private Dao<Offerta, Integer> offertaDao;
	private Dao<StatoOfferta, Integer> statoOffertaDao;
	private Dao<Baratto, Integer> barattoDao;

	String DATABASE_URL = //"jdbc:postgresql://localhost:5432/postgres?user=postgres&password=baratto";
"jdbc:postgresql://postgres:5432/baratto_db?user=postgres&password=baratto";
	// 		"jdbc:sqlite:" + System.getProperty("user.dir") + "/data/baratto.db";

	public Database(Dao<Account, Integer> accountDao, Dao<Categoria, Integer> categoriaDao,
			Dao<Campo, Integer> campoDao, Dao<Scambio, Integer> scambioDao, Dao<Articolo, Integer> articoloDao,
			Dao<AttributoArticolo, Integer> attributoArticoloDao, Dao<Offerta, Integer> offertaDao,
			Dao<StatoOfferta, Integer> statoOffertaDao, Dao<Baratto, Integer> barattoDao, String DATABASE_URL) {

		this.accountDao = accountDao;
		this.categoriaDao = categoriaDao;
		this.campoDao = campoDao;
		this.scambioDao = scambioDao;
		this.articoloDao = articoloDao;
		this.attributoArticoloDao = attributoArticoloDao;
		this.offertaDao = offertaDao;
		this.statoOffertaDao = statoOffertaDao;
		this.barattoDao = barattoDao;

		this.DATABASE_URL = DATABASE_URL;
	}

	public Database() {
		accountDao = null;
	}

	/**
	 * Metodo per creare la connessione con le varie tabelle del database
	 * 
	 * @throws Exception
	 */
	public void setupDatabase(ConnectionSource connectionSource) throws Exception {

		accountDao = DaoManager.createDao(connectionSource, Account.class);
		categoriaDao = DaoManager.createDao(connectionSource, Categoria.class);
		campoDao = DaoManager.createDao(connectionSource, Campo.class);
		scambioDao = DaoManager.createDao(connectionSource, Scambio.class);
		articoloDao = DaoManager.createDao(connectionSource, Articolo.class);
		attributoArticoloDao = DaoManager.createDao(connectionSource, AttributoArticolo.class);
		offertaDao = DaoManager.createDao(connectionSource, Offerta.class);
		statoOffertaDao = DaoManager.createDao(connectionSource, StatoOfferta.class);
		barattoDao = DaoManager.createDao(connectionSource, Baratto.class);

		/**try {
			TableUtils.createTableIfNotExists(connectionSource, Account.class);
			TableUtils.createTableIfNotExists(connectionSource, Categoria.class);
			TableUtils.createTableIfNotExists(connectionSource, Campo.class);
			TableUtils.createTableIfNotExists(connectionSource, Scambio.class);
			TableUtils.createTableIfNotExists(connectionSource, Articolo.class);
			TableUtils.createTableIfNotExists(connectionSource, AttributoArticolo.class);
			TableUtils.createTableIfNotExists(connectionSource, Offerta.class);
			TableUtils.createTableIfNotExists(connectionSource, StatoOfferta.class);
			TableUtils.createTableIfNotExists(connectionSource, Baratto.class);
		} catch (Exception e) {
			System.out.println("errore createTableIfNotExists bug ormlite");
			e.printStackTrace();
		}
		boolean radiceEsiste = false;

		try {
		    Categoria radice = categoriaDao.queryForId(1);
		    if (radice != null) {
		        radiceEsiste = true;
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		if (!radiceEsiste) {
		    Categoria radice = new Categoria("radice", "radice", true);
		    salvaCategoria(radice);
		}**/
	}

	public Dao<Articolo, Integer> getArticoloDao() {
		return articoloDao;
	}

	public Dao<AttributoArticolo, Integer> getAttributoArticoloDao() {
		return attributoArticoloDao;
	}

	public Dao<Offerta, Integer> getOffertaDao() {
		return offertaDao;
	}

	public Dao<StatoOfferta, Integer> getStatoOffertaDao() {
		return statoOffertaDao;
	}

	public Dao<Categoria, Integer> getCategoriaDao() {
		return this.categoriaDao;
	}

	public Dao<Account, Integer> getAccountDao() {
		return this.accountDao;
	}

	public Dao<Campo, Integer> getCampoDao() {
		return campoDao;
	}

	public Dao<Scambio, Integer> getScambioDao() {
		return scambioDao;
	}

	public Dao<Baratto, Integer> getBarattoDao() {
		return barattoDao;
	}

	/**
	 * Metodo per stabilire la connessione con il database
	 * 
	 * @throws Exception
	 */
	public void gestisciDatabase() throws Exception {
		ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
		try {
			setupDatabase(connectionSource);
		} finally {
			// chiude la connesione
			if (connectionSource != null) {
				connectionSource.close();
			}
		}

	}

	/**
	 * Metodo per cercare uno Scambio da id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Scambio getUltimoScambio() throws SQLException {
		List<Scambio> lista = this.getScambioDao().queryForAll();
		// .query(this.getScambioDao().queryBuilder().where().eq(Models.Scambio.ID,
		// id).prepare());
		if (lista.isEmpty())
			return null;
		return lista.get(lista.size() - 1);

	}

	/**
	 * Metodo per eliminare le righe di Scambio
	 * 
	 * @throws SQLException
	 */
	public void deleteScambio() throws SQLException {
		DeleteBuilder<Scambio, Integer> deleteBuilder = getScambioDao().deleteBuilder();
		deleteBuilder.where().isNotNull("id");
		deleteBuilder.delete();
	}

	/**
	 * Metodo per restituire un account presente nel database
	 * 
	 * @param nome_Account
	 * @param password_Account
	 * @return account
	 * @throws SQLException
	 */
	public Account getAccountDB(String name, String password) throws SQLException {
		List<Account> accountList;
		Account account = null;
		accountList = getAccountDao().query(getAccountDao().queryBuilder().where().eq(Account.NAME_FIELD_NAME, name)
				.and().eq(Account.PASSWORD_FIELD_NAME, password).prepare());

		if (accountList.isEmpty())
			return null;
		account = accountList.get(0);

		return account;
	}

	/**
	 * Metodo che restituisce i campi presente sul database di una categoria
	 * passando il suo id
	 * 
	 * @param id
	 * @return lista_campi
	 * @throws SQLException
	 */
	public List<Campo> getCampoDaIdCategoria(int id) throws SQLException {

		List<Campo> lista = getCampoDao()
				.query(getCampoDao().queryBuilder().where().eq(Campo.ID_CATEGORIA, id).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce la lista di categorie figlie di una data categoria
	 * identificata dal suo id
	 * 
	 * @param id
	 * @return lista_categorie_figlie
	 * @throws SQLException
	 */
	public List<Categoria> getFigli(int id) throws SQLException {
		List<Categoria> l = getCategoriaDao()
				.query(getCategoriaDao().queryBuilder().where().eq(Categoria.ID_PADRE, id).prepare());
		return l;
	}

	/**
	 * Metodo che restituisce la categoria presente sul database dal suo id
	 * 
	 * @param id
	 * @return categoria
	 * @throws SQLException
	 */
	public Categoria getCategoriaPerId(int id) throws SQLException {
		List<Categoria> lista = getCategoriaDao()
				.query(getCategoriaDao().queryBuilder().where().eq(Models.Categoria.ID, id).prepare());
		return lista.get(0);
	}

	/**
	 * Metodo che restituisce la categoria presente sul database dal nome
	 * 
	 * @param nome
	 * @return categoria
	 * @throws SQLException
	 */
	public Categoria getCategoriaPerNome(String nome) throws SQLException {
		List<Categoria> lista = getCategoriaDao()
				.query(getCategoriaDao().queryBuilder().where().eq(Models.Categoria.NAME_CATEGORIA, nome).prepare());
		return lista.get(0);
	}

	/**
	 * Metodo che restituisce la lista di tutte le categoria foglia
	 * 
	 * @return lista
	 * @throws SQLException
	 */
	public List<Categoria> getListaFoglie() throws SQLException {
		List<Categoria> lista = getCategoriaDao()
				.query(getCategoriaDao().queryBuilder().where().eq(Models.Categoria.FOGLIA_CATEGORIA, true).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce l'articolo con quell'id
	 * 
	 * @param id
	 * @return articolo
	 * @throws SQLException
	 */
	public Articolo getArticoloDaId(int id) throws SQLException {

		List<Articolo> lista = getArticoloDao()
				.query(getArticoloDao().queryBuilder().where().eq(Articolo.ID, id).prepare());
		return lista.get(0);
	}

	/**
	 * Metodo che restituisce la lista delle offerte con lo stesso stato
	 * 
	 * @param stato
	 * @return
	 * @throws SQLException
	 */
	public List<Offerta> getListaStessoStatoOfferta(int stato) throws SQLException {
		List<Offerta> lista = getOffertaDao()
				.query(getOffertaDao().queryBuilder().where().eq(Offerta.STATO, stato).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce la lista delle offerte con lo stesso id account e
	 * stato
	 * 
	 * @param id    autore
	 * @param stato
	 * @return lista
	 * @throws SQLException
	 */
	public List<Offerta> getListaOffertaAutoreStato(int idAccount, int stato) throws SQLException {
		List<Offerta> lista = getOffertaDao().query(getOffertaDao().queryBuilder().where()
				.eq(Offerta.ID_USER, idAccount).and().eq(Offerta.STATO, stato).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce la lista delle offerte con lo stesso id account
	 * 
	 * @param idAccount
	 * @return lista
	 * @throws SQLException
	 */
	public List<Offerta> getListaOffertaAutore(int idAccount) throws SQLException {
		List<Offerta> lista = getOffertaDao()
				.query(getOffertaDao().queryBuilder().where().eq(Offerta.ID_USER, idAccount).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce vero se una articolo � presente in un offerta con lo
	 * stato indicato
	 * 
	 * @param idArticolo
	 * @param stato
	 * @return vero se presente
	 * @throws SQLException
	 */
	public Boolean isOffertaStato(int idArticolo, int stato) throws SQLException {
		List<Offerta> lista = getOffertaDao().query(getOffertaDao().queryBuilder().where()
				.eq(Offerta.ID_ARTICOLO, idArticolo).and().eq(Offerta.STATO, stato).prepare());
		return !lista.isEmpty();
	}

	/**
	 * Metodo che restituisce la liste degli articoli con lo stesso id categoria
	 * 
	 * @param idCategoria
	 * @return lista
	 * @throws SQLException
	 */
	public List<Articolo> getListaArticoli(int idCategoria) throws SQLException {
		List<Articolo> lista = getArticoloDao()
				.query(getArticoloDao().queryBuilder().where().eq(Articolo.ID_CATEGORIA, idCategoria).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce l'offerta relativa all'idArticolo associato
	 * 
	 * @param idArticolo
	 * @return offerta
	 * @throws SQLException
	 */
	public Offerta getOffertadaIdArticolo(int idArticolo) throws SQLException {
		List<Offerta> lista = getOffertaDao()
				.query(getOffertaDao().queryBuilder().where().eq(Offerta.ID_ARTICOLO, idArticolo).prepare());
		return lista.get(0);
	}

	/**
	 * Metodo che restituisce l'offerta relativa all'id associato
	 * 
	 * @param idOfferta
	 * @return offerta
	 * @throws SQLException
	 */
	public Offerta getOffertadaId(int idOfferta) throws SQLException {
		List<Offerta> lista = getOffertaDao()
				.query(getOffertaDao().queryBuilder().where().eq(Offerta.ID, idOfferta).prepare());
		return lista.get(0);
	}

	/**
	 * Metodo che restituisce la lista delle offerte attualmente valide in baratto
	 * di un determinato account (stato diverso da 0 , 1 , 5)
	 * 
	 * @param idAccount
	 * @return lista offerte
	 * @throws SQLException
	 */
	public List<Offerta> getListaOfferteInBarattoDaIdAccount(int idAccount) throws SQLException {
		List<Offerta> lista = getOffertaDao().query(getOffertaDao().queryBuilder().where()
				.eq(Offerta.ID_USER, idAccount).and().not(getOffertaDao().queryBuilder().where().le(Offerta.STATO, 1))
				.and().not(getOffertaDao().queryBuilder().where().eq(Offerta.STATO, 5)).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce la lista dei campi di una categria
	 * 
	 * @param idCategoria
	 * @return lista campi
	 * @throws SQLException
	 */
	public List<Campo> getListaCampi(int idCategoria) throws SQLException {
		List<Campo> lista = getCampoDao()
				.query(getCampoDao().queryBuilder().where().eq(Campo.ID_CATEGORIA, idCategoria).prepare());
		return lista;
	}

	/**
	 * Metodo che restituisce il baratto in cui � coinvolta l' offerta
	 * 
	 * @param idOfferta
	 * @return baratto
	 * @throws SQLException
	 */
	public Baratto getBarattoOfferta(int idOfferta) throws SQLException {
		List<Baratto> lista = getBarattoDao().query(getBarattoDao().queryBuilder().where()
				.eq(Baratto.ID_OFFERTA_B, idOfferta).or().eq(Baratto.ID_OFFERTA_A, idOfferta).prepare());
		return lista.get(0);
	}

	/**
	 * Metodo che restituisce la lista dei baratti scaduti
	 * 
	 * @param giorniScadenza
	 * @return lista baratti
	 * @throws SQLException
	 */
	public List<Baratto> getBarattiScaduti(int giorniScadenza) throws SQLException {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, -giorniScadenza);

		Date dataScadenza = new java.sql.Date(calendario.getTimeInMillis());
		List<Baratto> lista = getBarattoDao()
				.query(getBarattoDao().queryBuilder().where().lt(Baratto.TIMESTAMP, dataScadenza).prepare());
		return lista;
	}

	/**
	 * Metodo per salvare una categoria nel db
	 * 
	 * @param categoria
	 * @throws SQLException
	 */
	public void salvaCategoria(Categoria categoria) throws SQLException {
			categoriaDao.create(categoria);
	}

	/**
	 * Metodo per salvare un campo nel db
	 * 
	 * @param campo
	 * @throws SQLException
	 */
	public void salvaCampo(Campo campo) throws SQLException {
		getCampoDao().create(campo);
	}

	/**
	 * Metodo per salvare uno scambio nel db
	 * 
	 * @param scambio
	 * @throws SQLException
	 */
	public void salvaScambio(Scambio scambio) throws SQLException {
		getScambioDao().create(scambio);
	}

	/**
	 * Metodo per aggiornare una categoria nel db
	 * 
	 * @param categoria
	 * @throws SQLException
	 */
	public void aggiornaCategoria(Categoria categoria) throws SQLException {
		getCategoriaDao().update(categoria);
	}

	/**
	 * Metodo per salvare un account
	 * 
	 * @param account
	 * @throws SQLException
	 */
	public void salvaAccount(Account account) throws SQLException {
		getAccountDao().create(account);
	}

	/**
	 * Metodo per controllare se un nome � gi� presente nel db
	 * 
	 * @param name
	 * @return bool
	 * @throws SQLException
	 */
	public boolean controlloNomeUtente(String name) throws SQLException {
		/**
		 * try { List<Account> accounts = getAccountDao()
		 * .query(getAccountDao().queryBuilder().where().eq(Account.NAME_FIELD_NAME,
		 * name).prepare()); if (!accounts.isEmpty()) { return true; } } catch
		 * (SQLException e) { e.printStackTrace(); } return false;
		 **/
		try {
			if (accountDao != null) {
				List<Account> accounts = accountDao
						.query(accountDao.queryBuilder().where().eq(Account.NAME_FIELD_NAME, name).prepare());
				if (!accounts.isEmpty()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo per salvare l'attributo articolo
	 * 
	 * @param attributo
	 * @throws SQLException
	 */
	public void salvaAttributoArticolo(AttributoArticolo attributo) throws SQLException {
		getAttributoArticoloDao().create(attributo);
	}

	/**
	 * Metodo per salvare l'articolo
	 * 
	 * @param articolo
	 * @throws SQLException
	 */
	public void salvaArticolo(Articolo articolo) throws SQLException {
		getArticoloDao().create(articolo);
	}

	/**
	 * Metodo per salvare l'offerta
	 * 
	 * @param offerta
	 * @throws SQLException
	 */
	public void salvaOfferta(Offerta offerta) throws SQLException {
		getOffertaDao().create(offerta);
	}

	/**
	 * Metodo per salvare lo stato dell'offerta
	 * 
	 * @param statoOfferta
	 * @throws SQLException
	 */
	public void salvaStatoOfferta(StatoOfferta statoOfferta) throws SQLException {
		getStatoOffertaDao().create(statoOfferta);
	}

	/**
	 * Metodo per aggiornare l'offerta
	 * 
	 * @param offerta
	 * @throws SQLException
	 */
	public void aggiornaOfferta(Offerta offerta) throws SQLException {
		getOffertaDao().update(offerta);
	}

	/**
	 * Metodo per salvare il baratto
	 * 
	 * @param baratto
	 * @throws SQLException
	 */
	public void salvaBaratto(Baratto baratto) throws SQLException {
		getBarattoDao().create(baratto);
	}

	/**
	 * Metodo per eliminare il baratto
	 * 
	 * @param baratto
	 * @throws SQLException
	 */
	public void eliminaBaratto(Baratto baratto) throws SQLException {
		getBarattoDao().delete(baratto);
	}

	/**
	 * Metodo per aggiornare il baratto
	 * 
	 * @param baratto
	 * @throws SQLException
	 */
	public void aggiornaBaratto(Baratto baratto) throws SQLException {
		getBarattoDao().update(baratto);
	}

	/**
	 * Metodo per aggiornare lo scambio
	 * 
	 * @param scambio
	 * @throws SQLException
	 */
	public void aggiornaScambio(Scambio scambio) throws SQLException {
		getScambioDao().update(scambio);
	}

	/**
	 * Metodo per ritornare la lista di tutte le categorie
	 * 
	 * @return lista categorie
	 * @throws SQLException
	 */
	public List<Categoria> getAllCategorie() throws SQLException {
		List<Categoria> lista = this.getCategoriaDao().queryForAll();
		return lista;
	}

}
