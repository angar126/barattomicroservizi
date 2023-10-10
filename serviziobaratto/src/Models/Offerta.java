package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "offerta")
public class Offerta {

	public static final String STATO = "stato";
	public static final String ID_ARTICOLO = "idArticolo";
	public static final String ID_USER = "idAccount";
	public static final String ID = "id";

	@DatabaseField(columnName = STATO)
	int stato;

	@DatabaseField(columnName = ID_ARTICOLO)
	private int idArticolo;

	@DatabaseField(columnName = ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = ID_USER)
	private int idUser;

	private Offerta() {
	}
	/**
	 * @param stato
	 * @param idArticolo
	 * @param idUSer
	 */
	public Offerta(int stato, int idArticolo, int idUser) {
		super();
		this.stato = stato;
		this.idArticolo = idArticolo;
		this.idUser = idUser;
	}
	public Offerta(int idArticolo, int idUser) {
		super();
		this.stato = 0;
		this.idArticolo = idArticolo;
		this.idUser = idUser;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public int getIdArticolo() {
		return idArticolo;
	}
	public void setIdArticolo(int idArticolo) {
		this.idArticolo = idArticolo;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUSer) {
		this.idUser = idUSer;
	}
	public int getId() {
		return id;
	}
}
