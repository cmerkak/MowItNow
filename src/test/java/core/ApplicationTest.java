package core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import conf.Application;

public class ApplicationTest {

	private Application app = new Application();

	private String nomFichier = "testCommandeFile";

	private String cheminEtNomDuFichier;

	@Before
	public void init() {
		cheminEtNomDuFichier = this.getClass().getClassLoader().getResource(nomFichier)
				.getPath();
		app.setNomEtCheminDuFichierCommande(cheminEtNomDuFichier);
	}

	@Test
	public void testApplication() {
		try {

			app.executer();
		} catch (Exception exp) {
			Assert.fail(exp.getMessage());
		}
	}
}
