package core;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import bean.Commande;
import bean.Position;
import bean.Surface;
import bean.enumeration.NotationCardinale;

@Component
public class ProducteurCommande {

	private static final Logger LOGGER = Logger
			.getLogger(ProducteurCommande.class.getName());

	private static final String SEPARATEUR_VIDE = "";

	private static final String SEPARATEUR_LIGNE = System
			.getProperty("line.separator");

	private static final String SEPARATEUR_BLANC = " ";

	private static final String UTF8 = "UTF-8";

	private static final int POSITION_1 = 1;

	private static final int POSITION_2 = 2;

	public Surface initSurface(File fichierDeCommande) throws Exception {
		try {

			List<String> xEtY = (List<String>) Files
					.lines(fichierDeCommande.toPath()).limit(1)
					.collect(Collectors.toList());

			String[] indices = xEtY.get(0).split(SEPARATEUR_BLANC);
			int maxX = Integer.parseInt(indices[0]);
			int maxY = Integer.parseInt(indices[1]);

			LOGGER.info("initialisation de la surface avec les parametres ("
					+ maxY + " et " + maxY + ")");
			return new Surface(maxX, maxY);

		} catch (Exception exp) {
			LOGGER.fatal("Erreur(s) lors de l'initialisation de la surface "
					+ exp.getMessage());
			throw exp;
		}
	}

	public List<Commande> creerCommande(File fichier) throws Exception {
		List<Commande> commandes = new ArrayList<Commande>();

		try {
			String[] contenuFichier = readFile(fichier);
			int j;
			for (int i = 1; i < contenuFichier.length; i += 2) {

				Commande commande = new Commande();

				// Recuperation de la position initiale et de l'orientation
				String positionComplete = contenuFichier[i];
				setPositionEtOrientation(commande, positionComplete);

				j = i + 1;
				// Recuperation des operations
				String operations = contenuFichier[j];
				setOperations(commande, operations);
				commandes.add(commande);
			}

		} catch (Exception exp) {
			LOGGER.error("Erreur(s) lors de la lecture des commandes "
					+ exp.getMessage());
			throw exp;
		}
		return commandes;
	}

	private static String[] readFile(File fichier) throws Exception {
		String contenu = FileUtils.readFileToString(fichier, UTF8);
		return StringUtils.split(contenu, SEPARATEUR_LIGNE);
	}

	private static void setOperations(Commande commande, String operations) {
		List<String> ops = Arrays.asList(operations.split(SEPARATEUR_VIDE));
		commande.setOperations(ops);
	}

	private void setPositionEtOrientation(Commande commande,
			String positionComplete) throws Exception {

		// Recuperation de la position
		Position position = new Position();
		String positionInitiale = StringUtils.split(positionComplete,
				SEPARATEUR_BLANC)[0];

		int positionX = getX(positionInitiale);
		int positionY = getY(positionInitiale);

		int[] pos = { positionX, positionY };
		position.setPosition(pos);

		// Recuperation de l orientation
		String orientation = StringUtils.split(positionComplete,
				SEPARATEUR_BLANC)[1];

		position.setOrientation(NotationCardinale.valueOf(orientation));
		commande.setPosition(position);
	}

	private int getX(String position) throws Exception {

		String posX = StringUtils.substring(position, 0, POSITION_1);
		return Integer.parseInt(posX);
	}

	private int getY(String position) throws Exception {

		String posY = StringUtils.substring(position, POSITION_1, POSITION_2);
		return Integer.parseInt(posY);
	}
}
