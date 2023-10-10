package ModelCsv;

import com.opencsv.bean.CsvBindByPosition;

public class ScambioCsv {

	@CsvBindByPosition(position = 0)
	private String piazza;

	@CsvBindByPosition(position = 1)
	private String luogo;

	@CsvBindByPosition(position = 2)
	private String giorni;

	@CsvBindByPosition(position = 3)
	private String orari;
	
	@CsvBindByPosition(position = 4)
	private int scadenza;

	public String getPiazza() {
		return piazza;
	}

	public String getLuogo() {
		return luogo;
	}

	public String getGiorni() {
		return giorni;
	}

	public String getOrari() {
		return orari;
	}

	public int getScadenza() {
		return scadenza;
	}
	
}
