package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 */
@DatabaseTable(tableName = "accounts")
public class Account {

	// for QueryBuilder to be able to find the fields
	public static final String NAME_FIELD_NAME = "nome";
	public static final String PASSWORD_FIELD_NAME = "password";
	public static final String TYPE_FIELD_NAME = "tipo";

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false, unique = true)
	private String name;
	@DatabaseField(columnName = PASSWORD_FIELD_NAME)
	private String password;
	@DatabaseField(columnName = TYPE_FIELD_NAME)
	private int accountType;

	Account() {
		// all persisted classes must define a no-arg constructor with at least package
		// visibility
	}

	public Account(String name) {
		this.name = name;
	}

	public Account(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Account(String name, String password, int accountType) {
		this.name = name;
		this.password = password;
		this.accountType = accountType;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return accountType;
	}

	public String getName() {
		return name;
	}

	public void setAccountType(int type) {
		this.accountType = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		return name.equals(((Account) other).name);
	}
}