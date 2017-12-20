package core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.Commande;
import bean.Position;
import bean.Surface;
import bean.enumeration.NotationCardinale;

public class TondeuseTest {

	private Commande commande1;

	private Commande commande2;

	private Surface surface = new Surface(5, 5);

	private Tondeuse tondeuse1;

	private Position positionTondeuse1;

	private Position positionInitialeCmd1 = new Position(new int[] { 1, 2 },
			NotationCardinale.N);
	
	private List<String> operationsCmd1 = Arrays.asList("G", "A", "G", "A",
			"G", "A", "G", "A", "A");
	
	private Tondeuse tondeuse2;

	private Position positionTondeuse2;

	private Position positionInitialeCmd2 = new Position(new int[] { 3, 3 },
			NotationCardinale.E);
	
	private List<String> operationsCmd2 = Arrays.asList("A","A","D","A","A","D","A","D","D","A");

	@Before
	public void init() {
		commande1 = new Commande(positionInitialeCmd1, operationsCmd1);
		tondeuse1 = new Tondeuse(commande1, surface);
		positionTondeuse1 = tondeuse1.deplacer();
		
		commande2 = new Commande(positionInitialeCmd2, operationsCmd2);
		tondeuse2 = new Tondeuse(commande2, surface);
		positionTondeuse2 = tondeuse2.deplacer();
	}

	@Test
	public void testDeplacementTondeuses() {
		//Tondeuse 1
		assertEquals(NotationCardinale.N, positionTondeuse1.getOrientation());
		assertEquals(1, positionTondeuse1.getPosition()[0]);
		assertEquals(3, positionTondeuse1.getPosition()[1]);
		
		//Tondeuse 2
		assertEquals(NotationCardinale.E, positionTondeuse2.getOrientation());
		assertEquals(5, positionTondeuse2.getPosition()[0]);
		assertEquals(1, positionTondeuse2.getPosition()[1]);
	}
}
