package ModelCsv;

import com.opencsv.bean.CsvBindByPosition;

public class CategoriaCsv {

    @CsvBindByPosition(position = 0)
    private String nome;

    @CsvBindByPosition(position = 1)
    private String descrizione;

    @CsvBindByPosition(position = 2)
    private boolean foglia;

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public boolean getFoglia() {
		return foglia;
	}



}