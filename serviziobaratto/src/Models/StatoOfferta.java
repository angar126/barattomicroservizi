package Models;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "statoofferta")
public class StatoOfferta {

	public static final String TIMESTAMP = "timestamp";
	public static final String STATO = "stato";
	public static final String ID_OFFERTA = "idOfferta";
	public static final String ID = "id";

	@DatabaseField(columnName = TIMESTAMP)
	Date timestamp;

	@DatabaseField(columnName = STATO)
	int stato;

	@DatabaseField(columnName = ID_OFFERTA)
	private int idOfferta;

	@DatabaseField(columnName = ID, generatedId = true)
	private int id;

	StatoOfferta() {
	}

	/**
	 * @param timestemp
	 * @param stato
	 * @param idOfferta
	 */
	public StatoOfferta(int stato, int idOfferta) {
		super();
		
		this.stato = stato;
		this.idOfferta = idOfferta;
		
		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis);
		this.timestamp =  date;
	}
	public StatoOfferta(int idOfferta) {
		super();
		
		this.stato = 0;
		this.idOfferta = idOfferta;
		
		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis);
		this.timestamp =  date;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int descrizione) {
		this.stato = descrizione;
	}

	public int getIdOfferta() {
		return idOfferta;
	}

	public void setIdOfferta(int idOfferta) {
		this.idOfferta = idOfferta;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public int getId() {
		return id;
	}

}
