package core;

import org.junit.Test;

import conf.Lanceur;

public class LanceurTest {

	private Lanceur lanceur = new Lanceur();

	@Test
	public void testApplication() {
		lanceur.executer();
	}
}
