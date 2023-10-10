package GestioneDB;

import java.sql.SQLException;
import java.util.List;

import Models.*;

public interface IDatabase {
	public void gestisciDatabase() throws Exception;
	public Scambio getUltimoScambio() throws SQLException;
	public void deleteScambio() throws SQLException;
	public Account getAccountDB(String name, String password) throws SQLException;
	public List<Campo> getCampoDaIdCategoria(int id) throws SQLException;
	public List<Categoria> getFigli(int id) throws SQLException;
	public Categoria getCategoriaPerId(int id) throws SQLException;
	public Categoria getCategoriaPerNome(String nome) throws SQLException;
	public List<Categoria> getListaFoglie() throws SQLException;
	public Articolo getArticoloDaId(int id) throws SQLException;
	public List<Offerta> getListaStessoStatoOfferta(int stato)throws SQLException;
	public List<Offerta> getListaOffertaAutoreStato(int idAccount, int stato)throws SQLException;
	public List<Offerta> getListaOffertaAutore(int idAccount)throws SQLException;
	public Boolean isOffertaStato(int idArticolo, int stato)throws SQLException;
	public List<Articolo> getListaArticoli(int idCategoria)throws SQLException;
	public Offerta getOffertadaIdArticolo(int idArticolo)throws SQLException;
	public Offerta getOffertadaId(int idOfferta)throws SQLException;
	public List<Offerta> getListaOfferteInBarattoDaIdAccount(int idAccount)throws SQLException;
	public List<Campo> getListaCampi(int idCategoria)throws SQLException;
	public Baratto getBarattoOfferta(int idOfferta)throws SQLException;
	public List<Baratto> getBarattiScaduti(int giorniScadenza)throws SQLException;
	public void salvaCategoria(Categoria categoria)throws SQLException;
	public void salvaCampo(Campo campo)throws SQLException;
	public void salvaScambio(Scambio scambio) throws SQLException;
	public void aggiornaCategoria(Categoria categoria)throws SQLException;
	public void salvaAccount(Account account)throws SQLException;
	public boolean controlloNomeUtente(String name)throws SQLException;
	public void salvaAttributoArticolo(AttributoArticolo attributo)throws SQLException;
	public void salvaArticolo(Articolo articolo)throws SQLException;
	public void salvaOfferta(Offerta offerta)throws SQLException;
	public void salvaStatoOfferta(StatoOfferta statoOfferta)throws SQLException;
	public void aggiornaOfferta(Offerta offerta)throws SQLException;
	public void salvaBaratto(Baratto baratto)throws SQLException;
	public void eliminaBaratto(Baratto baratto)throws SQLException;
	public void aggiornaBaratto(Baratto baratto)throws SQLException;
	public void aggiornaScambio(Scambio scambio)throws SQLException;
	public List<Categoria> getAllCategorie()throws SQLException;
	
}
