package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categoria")
public class Categoria {

	public static final String NAME_CATEGORIA = "nome";
	public static final String DESCRIZIONE_CATEGORIA = "descrizione";
	public static final String FOGLIA_CATEGORIA = "foglia";
	public static final String ID_PADRE = "idPadre";
	public static final String ID = "id";

	@DatabaseField(columnName = NAME_CATEGORIA, canBeNull = false)
	String nome;

	@DatabaseField(columnName = DESCRIZIONE_CATEGORIA, canBeNull = false)
	String descrizione;

	@DatabaseField(columnName = FOGLIA_CATEGORIA)
	Boolean foglia;
	
	@DatabaseField(columnName = ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = ID_PADRE)
	private int idPadre;

	/**
	 * @param nome
	 * @param descrizione
	 * @param foglia
	 * @param id
	 * @param idPadre
	 * @param campi
	 */

	public Categoria(String nome, String descrizione, boolean foglia) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.foglia = foglia;

	}


	/**
	 * @param nome
	 * @param descrizione
	 * @param foglia
	 * @param id
	 * @param idPadre
	 */
	public Categoria(String nome, String descrizione, Boolean foglia, int idPadre) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.foglia = foglia;
		this.idPadre = idPadre;
	}

	public Categoria(){
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getFoglia() {
		return foglia;
	}

	public void setFoglia(Boolean foglia) {
		this.foglia = foglia;
	}

	public int getIdPadre() {
		return this.idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	public void setId(int id) {
	    this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

}
