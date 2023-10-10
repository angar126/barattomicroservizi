package InterfacciaUtente;

import java.sql.SQLException;
import java.util.List;

import Models.Campo;
import Models.Categoria;
import Models.Scambio;
import Util.InputDati;

public class ViewGerarchia extends View {
	// metodo menu categorie
		public int menuListaCategorie(List<Categoria> lista, String didascalia) {
			int i = 0;
			StringBuffer sb = new StringBuffer();
			sb.append(didascalia + "\n");
			for (Categoria elemento : lista) {
				sb.append((i) + "\t" + elemento.getNome() + "\n");
				i++;
			}
			System.out.println(sb.toString());
			int scelta = InputDati.leggiIntero(SCEGLI, 0, lista.size());
			return scelta;
		}
		public int menuGerarchie(List<Categoria> listaGerarchie) {
			return menuListaCategorie(listaGerarchie, "A quale gerarchia appartiene?");
		}

		public int menuCategorie(List<Categoria> listaCategorie) {
			return menuListaCategorie(listaCategorie, "\nQuale categoria si vuole selezionare?: ");
		}
		// richiedi numero campi
		public int richiediNumeroCampi() {
			return InputDati.leggiInteroNonNegativo(NUMERO_CAMPI_NATIVI);
		}
		// richiedi stato compilazione
		public boolean richiediStatoCompilazione() {
			return InputDati.leggiBoolean("Lo stato di compliazione e' OBBLIGATORIO? ");
		}

		// richiedi numero categorie
		public int richiediNumeroCategorie() {
			return InputDati.leggiInteroConMinimo("Quante sottocategorie vuoi inserire?(minimo 2)", 2);
		}
		// stampa la categoria e i suoi campi
		public void stampaCategoria(Categoria categoria, List<Campo> listaCampi) {
			StringBuffer sb = new StringBuffer();
			sb.append(stampaCategoria(categoria));
			for (Campo campo : listaCampi) {
				sb.append(stampaCampo(campo) + "\n");
			}
			System.out.println(sb.toString());

		}
		public void stampaRadice(Categoria categoria, List<Campo> listaCampi) {
			StringBuffer sb = new StringBuffer();
			sb.append(corniceTesto("GERARCHIA"));
			sb.append(stampaCategoria(categoria) + "\n");
			for (Campo campo : listaCampi) {
				sb.append(stampaCampo(campo) + "\n");
			}
			System.out.println(sb.toString());

		}


		//stampa riga albero gerarchia
		public String rigaAlbero(int profondita, String nome) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < profondita; i++) {
				sb.append("-");
			}
			sb.append(" " + nome + "\n");
			return sb.toString();
		}
		//stampa campo
		public String stampaCampo(Campo campo) {
			return "Campo [nome= " + campo.getNome() + ", Obbligatorio= " + campo.getObbligatorio() + ", id="
					+ campo.getId() + ", idCategoria=" + campo.getIdCategoria() + "]";
		}
	//stampa categoria
		public String stampaCategoria(Categoria categoria) {
			StringBuffer sb = new StringBuffer();

			sb.append((categoria.getIdPadre() == 0) ? ("\n\n-------CATEGORIA PADRE-------\n")
					: ("\n\n-------FIGLIO-------\n"));
			sb.append("Nome gerarchia: " + categoria.getNome() + "\n");
			sb.append("ID: " + categoria.getId() + "\n");
			sb.append("ID PADRE: " + categoria.getIdPadre() + "\n");
			sb.append("Descrizione gerarchia: " + categoria.getDescrizione() + "\n\n");

			return sb.toString();
		}
	//stampa categoria in poche righe
		public String stampaCategoriaBreve(Categoria categoria) {
			return "Categoria [nome=" + categoria.getNome() + ", descrizione=" + categoria.getDescrizione() + ", foglia="
					+ categoria.getFoglia() + ", id=" + categoria.getId() + ", idPadre=" + categoria.getIdPadre() + "]";
		}
		public void stampaScambio(Scambio scambio) throws SQLException {

			try {
				ViewScambio viewScambio = new ViewScambio();
				System.out.println(viewScambio.stampaScambio(scambio));
			} catch (Exception e) {
				System.out.println("Parametri scambio non impostati");
			}
}
		public void stampaStringaCampo() {
			System.out.println("\nInserisci i valori del campo:");
		}
		}
