package ModelCsv;

	import com.opencsv.bean.CsvBindByPosition;

	public class CampoCsv {

	    @CsvBindByPosition(position = 0)
	    private int idCategoria;

	    @CsvBindByPosition(position = 1)
	    private String nome;

	    @CsvBindByPosition(position = 2)
	    private boolean obbligatorio;

		public int getIdCategoria() {
			return idCategoria;
		}

		public String getNome() {
			return nome;
		}

		public boolean isObbligatorio() {
			return obbligatorio;
		}

		
		}
