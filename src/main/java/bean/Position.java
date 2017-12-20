package bean;

import bean.enumeration.NotationCardinale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Position {

	/** Vecteur de dimension 2 representant la position finale de la tendeuse */
	@Getter
	@Setter
	private int[] position;
	
	/** Orientation de la tendeuse apres deplacement */
	@Getter
	@Setter
	private NotationCardinale orientation;

}
