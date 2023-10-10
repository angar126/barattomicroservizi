package ModelCsv;

import com.opencsv.bean.CsvToBeanBuilder;

import GestioneDB.Database;
import Models.Campo;
import Models.Categoria;
import Models.Scambio;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.sql.SQLException;
import java.util.List;

public class LettoreCsv {

	// String filePath = new File("").getAbsolutePath();
	// filePath.concat(fileName);
	/**
	 * Metodo che restituisce la lista delle CategorieCsv presenti nel file
	 * @return lista
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public List<CategoriaCsv> leggiFileCategoria() throws IllegalStateException, FileNotFoundException {
		String fileName = System.getProperty("user.dir")+"/files/categoria.csv";

		List<CategoriaCsv> listaCsv = new CsvToBeanBuilder(new FileReader(fileName)).withType(CategoriaCsv.class)
				.build().parse();
		return listaCsv;
	}
	/**
	 * Metodo che restituisce la lista dei CampiCsv presenti nel file
	 * @return lista
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public List<CampoCsv> leggiFileCampo() throws IllegalStateException, FileNotFoundException {
		String fileName =  System.getProperty("user.dir")+"/files/campo.csv";

		List<CampoCsv> beans = new CsvToBeanBuilder(new FileReader(fileName)).withType(CampoCsv.class).build().parse();
		return beans;
	}
	/**
	 * Metodo che restituisce la lista degli ScambiCsv presenti nel file
	 * @return lista
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public List<ScambioCsv> leggiFileScambio() throws IllegalStateException, FileNotFoundException {
		String fileName =  System.getProperty("user.dir")+"/files/scambio.csv";

		List<ScambioCsv> beans = new CsvToBeanBuilder(new FileReader(fileName)).withType(ScambioCsv.class).build()
				.parse();
		return beans;
	}
	/**
	 * Metodo che restituisce la lista delle RelazioniCategorieCsv presenti nel file
	 * @return lista
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 */
	public List<RelazioneCategorieCsv> leggiFileRelazioneCategorie() throws IllegalStateException, FileNotFoundException {
		String fileName =  System.getProperty("user.dir")+"/files/relazioni.csv";

		List<RelazioneCategorieCsv> beans = new CsvToBeanBuilder(new FileReader(fileName))
				.withType(RelazioneCategorieCsv.class).build().parse();
		return beans;
	}

}
