package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "attributiarticolo")
public class AttributoArticolo {
	public static final String VALORE = "valore";
	public static final String ID_CAMPO = "idCampo";
	public static final String ID_ARTICOLO = "idArticolo";
	public static final String ID = "id";

	@DatabaseField(columnName = VALORE)
	private String valore;

	@DatabaseField(columnName = ID_CAMPO)
	private int idCampo;

	@DatabaseField(columnName = ID, generatedId = true)
	private int id;
	
	@DatabaseField(columnName = ID_ARTICOLO) 
	private int idArticolo;

	AttributoArticolo() {
	}

	/**
	 * @param valore
	 * @param idCampo
	 * @param idArticolo
	 */
	public AttributoArticolo(String valore, int idCampo, int idArticolo) {
		super();
		this.valore = valore;
		this.idCampo = idCampo;
		this.idArticolo = idArticolo;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public int getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	public int getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(int idArticolo) {
		this.idArticolo = idArticolo;
	}

	public int getId() {
		return id;
	}
}
