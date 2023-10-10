package Models;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "scambio")
public class Scambio {

	public static final String PIAZZA = "piazza";
	public static final String LUOGO = "luogo";
	public static final String GIORNI = "giorni";
	public static final String ORARI = "orari";
	public static final String SCADENZA = "scadenza";

	public static final String ID = "id";

	@DatabaseField(columnName = PIAZZA, canBeNull = false)
	String piazza;

	@DatabaseField(columnName = LUOGO, canBeNull = false)
	String luogo;

	@DatabaseField(columnName = GIORNI, canBeNull = false)
	String giorni;

	@DatabaseField(columnName = ORARI)
	String orari;

	@DatabaseField(columnName = ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = SCADENZA)
	int scadenza;

	public Scambio() {

	}

	/**
	 * @param piazza
	 * @param luogo
	 * @param giorni
	 * @param orari
	 * @param scadenza
	 */
	public Scambio(String piazza, String luogo, String giorni, String orari, int scadenza) {
		super();
		this.piazza = piazza;
		this.luogo = luogo;
		this.giorni = giorni;
		this.orari = orari;
		this.scadenza = scadenza;
	}
	
	public Scambio(JSONObject obj) {
		super();
		this.id= obj.getInt("id");
		this.piazza = obj.getString(PIAZZA);
		this.luogo = obj.getString(LUOGO);
		this.giorni = obj.getString(GIORNI);
		this.orari = obj.getString(ORARI);
		this.scadenza = obj.getInt(SCADENZA);
	}

	/**
	 * @param piazza
	 */
	public Scambio(String piazza) {
		super();
		this.piazza = piazza;
	}

	public String getPiazza() {
		return piazza;
	}

	public void setPiazza(String piazza) {
		this.piazza = piazza;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getGiorni() {
		return giorni;
	}

	public void setGiorni(String giorni) {
		this.giorni = giorni;
	}

	public String getOrari() {
		return orari;
	}

	public void setOrari(String orari) {
		this.orari = orari;
	}

	public int getScadenza() {
		return scadenza;
	}
	public String getScadenzaString() {
		Integer i =scadenza;
		return i.toString();
	}

	public void setScadenza(int scadenza) {
		this.scadenza = scadenza;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("Piazza: " + this.piazza + "\n");
		sb.append("Luoghi: " + this.luogo + "\n");
		sb.append("Giorni: " + this.giorni + "\n");
		sb.append("Intervalli orari: " + this.orari + "\n");
		sb.append("Giorni validita' offerta: " + this.scadenza + "\n\n");
		return sb.toString();
	}

	public List<String> getListaLuoghi() {
		List<String> luoghi = Arrays.asList(luogo.split(", "));
		return luoghi;
	}

	public List<String> getListaGiorni() {
		List<String> luoghi = Arrays.asList(giorni.split(", "));
		return luoghi;
	}

	public List<String> getListaOrari() {
		List<String> ore = Arrays.asList(orari.split(", "));
		return ore;
	}
}
