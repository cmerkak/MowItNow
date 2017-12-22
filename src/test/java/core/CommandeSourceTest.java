package core;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bean.Commande;
import bean.Surface;
import bean.enumeration.NotationCardinale;

public class CommandeSourceTest {

	private File fichier;

	@Before
	public void beforeTest() {
		fichier = new File(this.getClass().getClassLoader()
				.getResource("testCommandeFile").getFile());
	}

	@Test
	public void testCreateCommande() {
		List<Commande> commandes = CommandeSource.createCommande(fichier);
		Assert.assertEquals(2, commandes.size());

		// 1 ere commande
		Assert.assertEquals(1, commandes.get(0).getPosition().getPosition()[0]);
		Assert.assertEquals(2, commandes.get(0).getPosition().getPosition()[1]);
		Assert.assertEquals(NotationCardinale.N, commandes.get(0).getPosition()
				.getOrientation());

		String operations = commandes.get(0).getOperations().stream()
				.map(i -> i.toString()).collect(Collectors.joining(""));
		Assert.assertEquals("GAGAGAGAA", operations);

		// 2eme commande
		Assert.assertEquals(3, commandes.get(1).getPosition().getPosition()[0]);
		Assert.assertEquals(3, commandes.get(1).getPosition().getPosition()[1]);
		Assert.assertEquals(NotationCardinale.E, commandes.get(1).getPosition()
				.getOrientation());
		operations = commandes.get(1).getOperations().stream()
				.map(i -> i.toString()).collect(Collectors.joining(""));
		Assert.assertEquals("AADAADADDA", operations);
	}

	@Test
	public void testInitSurface() {
		Surface surface;
		try {
			surface = CommandeSource.initSurface(fichier);
			Assert.assertEquals(5, surface.getMaxX());
			Assert.assertEquals(5, surface.getMaxY());
		} catch (Exception e) {
			Assert.fail();
		}
		
	}
}
