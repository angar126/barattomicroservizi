package Models;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "baratto")
public class Baratto {
	public static final String ID = "id";
	public static final String ID_OFFERTA_A = "idOffertaA";
	public static final String ID_OFFERTA_B = "idOffertaB";
	public static final String PROPOSTA_SCAMBIO_A = "propostaScambioA";
	public static final String PROPOSTA_SCAMBIO_B = "propostaScambioB";
	public static final String ULTIMA_RISPOSTA_A = "ultimaRispostaA";
	public static final String TIMESTAMP = "timestamp";

	@DatabaseField(columnName = ID, generatedId = true)
	private int id;
	@DatabaseField(columnName = ID_OFFERTA_A)
	private int idOffertaA;
	@DatabaseField(columnName = ID_OFFERTA_B)
	private int idOffertaB;
	@DatabaseField(columnName = PROPOSTA_SCAMBIO_A)
	private String propostaScambioA;
	@DatabaseField(columnName = PROPOSTA_SCAMBIO_B)
	private String propostaScambioB;
	@DatabaseField(columnName = ULTIMA_RISPOSTA_A)
	private boolean ultimaRispostaA;
	@DatabaseField(columnName = TIMESTAMP)
	private Date timestamp;

	public Baratto() {
	}

	public Baratto(int idOffertaA, int idOffertaB, String propostaScambioA) {
		super();
		this.idOffertaA = idOffertaA;
		this.idOffertaB = idOffertaB;
		this.propostaScambioA = propostaScambioA;
		this.ultimaRispostaA = true;
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		this.timestamp = date;
	}

	public int getId() {
		return id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	private void setTimestamp() {
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		this.timestamp = date;
	}

	public int getIdOffertaA() {
		return idOffertaA;
	}

	public void setIdOffertaA(int idOffertaA) {
		this.idOffertaA = idOffertaA;
	}

	public int getIdOffertaB() {
		return idOffertaB;
	}

	public void setIdOffertaB(int idOffertaB) {
		this.idOffertaB = idOffertaB;
	}

	public String getPropostaScambioA() {
		return propostaScambioA;
	}

	public void setPropostaScambioA(String propostaScambioA) {
		this.propostaScambioA = propostaScambioA;
		this.ultimaRispostaA = true;
		setTimestamp();
	}

	public String getPropostaScambioB() {
		return propostaScambioB;
	}

	public void setPropostaScambioB(String propostaScambioB) {
		this.propostaScambioB = propostaScambioB;
		this.ultimaRispostaA = false;
		setTimestamp();
	}

	public boolean getUltimaRispostaA() {
		return ultimaRispostaA;
	}

	/**
	 * Metodo che restituisce l'offerta che deve rispondere
	 * 
	 * @return idOfferta
	 */
	public int getOffertaInRisposta() {
		if (ultimaRispostaA) {
			return idOffertaB;
		} else
			return idOffertaA;
	}

}
