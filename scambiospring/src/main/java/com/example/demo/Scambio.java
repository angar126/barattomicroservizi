package com.example.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity( name = "scambio")
@Table
public class Scambio {
	@Id
	@SequenceGenerator(
			name = "scambio_sequence",
			sequenceName = "scambio_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "scambio_sequence")
	private Long id;
	private String piazza;
	private String luogo;
	private String giorni;
	private String orari;
	private int scadenza;

	public Scambio() {
	};

	public Scambio(String piazza, String luogo, String giorni, String orari, int scadenza) {
		super();
		this.piazza = piazza;
		this.luogo = luogo;
		this.giorni = giorni;
		this.orari = orari;
		this.scadenza = scadenza;
	}
	
	public Scambio(String piazza) {
		super();
		this.piazza = piazza;
	}

	public String getPiazza() {
		return piazza;
	}

	public void setPiazza(String piazza) {
		this.piazza = piazza;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getGiorni() {
		return giorni;
	}

	public void setGiorni(String giorni) {
		this.giorni = giorni;
	}

	public String getOrari() {
		return orari;
	}

	public void setOrari(String orari) {
		this.orari = orari;
	}

	public int getScadenza() {
		return scadenza;
	}

	public void setScadenza(int scadenza) {
		this.scadenza = scadenza;
	}

	public Long getId() {
		return id;
	}

	
}
