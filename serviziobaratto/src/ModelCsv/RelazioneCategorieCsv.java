package ModelCsv;

import com.opencsv.bean.CsvBindByPosition;

public class RelazioneCategorieCsv {
	@CsvBindByPosition(position = 0)
    private int idCategoria;

    @CsvBindByPosition(position = 1)
    private int idPadre;

	public int getIdCategoria() {
		return idCategoria;
	}

	public int getIdPadre() {
		return idPadre;
	}
}
