package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "campo")
public class Campo {

	public static final String NAME_CAMPO = "nome";
	public static final String OBBLIGATORIO = "obbligatorio";
	public static final String ID_CATEGORIA = "idCategoria";
	public static final String ID = "id";

	@DatabaseField(columnName = NAME_CAMPO, canBeNull = false)
	private String nome;

	@DatabaseField(columnName = OBBLIGATORIO, canBeNull = false)
	private boolean statoCompilazione;


	@DatabaseField(columnName = ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = ID_CATEGORIA) 
	private int idCategoria;

	Campo() {
	}

	/**
	 * @param nome
	 * @param obbligatorio
	 * @param idCategoria
	
	 * @param categoria
	 */
	public Campo(String nome, boolean statoCompilazione, int idCategoria) {
			this.nome = nome;
			this.statoCompilazione = statoCompilazione;
			this.idCategoria = idCategoria;
		}
	
	public Campo(String nome, boolean statoCompilazione) {
			this.nome = nome;
			this.statoCompilazione = statoCompilazione;
		}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public boolean getObbligatorio() {
		return statoCompilazione;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.statoCompilazione = obbligatorio;
	}

	public int getId() {
		return this.id;
	}

	public void setIdCategoria(int daSettare){
		this.idCategoria = daSettare;
	}
	
	public int getIdCategoria() {
		return idCategoria;
	}

}
