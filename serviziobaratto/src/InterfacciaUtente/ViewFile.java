package InterfacciaUtente;

import Util.MyMenu;

public class ViewFile extends View {
	//messaggio di avvenuto salvataggio file
		public void stampaFile(String stringa) {
			System.out.println("Salvataggio " + stringa);
		}
	
		private static final String CATEGORIE ="Importa file categorie";
		private static final String CAMPI ="Importa file campi";
		private static final String SCAMBIO ="Importa file scambio";
		private static final String RELAZIONI ="Importa file relazioni categorie";
		
		//menu importazioni
		public MyMenu menuImportazioni() {
			String[] vociMenu = { CATEGORIE, CAMPI, SCAMBIO, RELAZIONI };

			return new MyMenu(HEADER_MENU, vociMenu);
		}
}
