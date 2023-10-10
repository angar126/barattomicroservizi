package test;

import org.junit.Test;
import Models.*;
import java.sql.SQLException;

import org.junit.Assert;

//test blackbox + assert
public class TestJunit {
	
	//testMicroserviziocambio
	@Test
	public void scambioTest() throws SQLException {
		boolean assertionbool = false;
		Scambio scambio = new Scambio("Piazza","luogo1, luogo2","Lunedi, Martedi","01:00:00 - 02:30:00, 04:00:00 - 05:00:00",99);
		Model model = new Model();
		model.salvaScambio(scambio);
		var result = model.getUltimoScambio();
		if (result instanceof Scambio)
			assertionbool = true;
		Assert.assertTrue(assertionbool);
		Assert.assertNotNull(result);
		Assert.assertTrue(scambio.getPiazza().equals(result.getPiazza()));
		Assert.assertTrue(scambio.getLuogo().equals(result.getLuogo()));
		Assert.assertTrue(scambio.getGiorni().equals(result.getGiorni()));
		Assert.assertTrue(scambio.getOrari().equals(result.getOrari()));
		Assert.assertTrue(scambio.getScadenza()==(result.getScadenza()));
	}
}
