package core;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import bean.Commande;
import bean.Position;
import bean.Surface;
import bean.enumeration.NotationCardinale;
import bean.enumeration.Operations;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class Tondeuse {

	@Getter
	@Setter
	private Commande commande;

	@Getter
	@Setter
	private Surface surface;

	public Position deplacer() throws Exception{
		Position position = new Position();

		// Recuperation des donnees de la commande a executer
		calculerPosition(commande.getPosition(), position,
				commande.getOperations());

		return position;
	}

	private void calculerPosition(Position positionInitiale,
			Position positionFinale, List<String> cmds) throws Exception {
		NotationCardinale directionFinale = positionInitiale.getOrientation();
		positionFinale.setPosition(positionInitiale.getPosition());

		for (String cmd : cmds) {
			Operations operation = Operations.valueOf(cmd);
			directionFinale = calculerDirection(directionFinale, operation);
			positionFinale.setOrientation(directionFinale);
			positionFinale.setPosition(calculerPosition(positionFinale,
					surface, operation, directionFinale));
		}
	}

	private int[] calculerPosition(Position positionInitiale, Surface surface,
			Operations operation, NotationCardinale orientation) throws Exception{
		int posInitX = positionInitiale.getPosition()[0];
		int posInitY = positionInitiale.getPosition()[1];
		if (Operations.A.equals(operation)) {

			switch (orientation) {
			case N:
				if (posInitY + 1 <= surface.getMaxY())
					posInitY++;
				break;
			case S:
				if (posInitY - 1 >= 0)
					posInitY--;
				break;
			case W:
				if (posInitX - 1 >= 0)
					posInitX--;
				break;
			case E:
				if (posInitX + 1 <= surface.getMaxX())
					posInitX++;
				break;

			default:
				break;
			}
		}
		return new int[] { posInitX, posInitY };
	}

	private NotationCardinale calculerDirection(
			NotationCardinale directionInitiale, Operations operation) throws Exception {
		NotationCardinale direction = directionInitiale;
		switch (operation) {
		case D:
			switch (direction) {
			case N:
				direction = NotationCardinale.E;
				break;
			case S:
				direction = NotationCardinale.W;
				break;
			case E:
				direction = NotationCardinale.S;
				break;
			case W:
				direction = NotationCardinale.N;
				break;

			default:
				break;
			}
			break;

		case G:
			switch (direction) {
			case N:
				direction = NotationCardinale.W;
				break;
			case S:
				direction = NotationCardinale.E;
				break;
			case E:
				direction = NotationCardinale.N;
				break;
			case W:
				direction = NotationCardinale.S;
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
		return direction;
	}
}
