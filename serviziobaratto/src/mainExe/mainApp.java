package mainExe;

import com.j256.ormlite.logger.LocalLog;

import Control.Control;
import GestioneDB.Database;
import Models.Account;
import Util.InputDati;

public class mainApp {
	public static void main(String[] args) throws Exception {
		// Istanza main
		new mainApp().doMain(args);
	}

	private void doMain(String[] args) {

		/*
		 * Riga di codice per evitare di stampare tutto il debug che si origina dalle
		 * query del DataBase
		 */
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
		
		try {
			Control control =new Control();
			Account account = null;
			account = control.log(account);
			control.sceltaTipoMenu(account);
		} catch (Exception e) {
			System.out.println("Qualcosa e' andato storto! Arrivederci");
			e.printStackTrace();
		}

	}
}
