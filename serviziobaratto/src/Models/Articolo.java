package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "articolo")
public class Articolo {
	public static final String ID_CATEGORIA = "idCategoria";

	public static final String ID = "id";
	public static final String NOME = "nome";

	@DatabaseField(columnName = ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = ID_CATEGORIA)
	private int idCategoria;
	@DatabaseField(columnName = NOME)
	private String nome;


	Articolo() {
	}

	/**
	 * @param idCategoria
	 * @param nome
	 */
	public Articolo(int idCategoria, String nome, int idAccount) {
		super();
		this.idCategoria = idCategoria;
		this.nome = nome;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

}
