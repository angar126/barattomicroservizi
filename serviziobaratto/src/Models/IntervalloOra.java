package Models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IntervalloOra {
	private LocalTime inizio;
	private LocalTime fine;
	/**
	 * @param inizio
	 * @param fine
	 */
	public IntervalloOra(LocalTime inizio, LocalTime fine) {
		super();
		this.inizio = inizio;
		this.fine = fine;
	}
	public IntervalloOra() {
		super();
	}
	public LocalTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalTime inizio) {
		this.inizio = inizio;
	}
	public LocalTime getFine() {
		return fine;
	}
	public void setFine(LocalTime fine) {
		this.fine = fine;
	}
	@Override
	public String toString() {
		String stringaintervallo=this.inizio.format(DateTimeFormatter.ISO_TIME)+" - "+this.fine.format(DateTimeFormatter.ISO_TIME);
		return stringaintervallo;
	}

}
