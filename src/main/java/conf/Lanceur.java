package conf;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import core.CommandeSource;
import core.Tondeuse;
import bean.Commande;
import bean.Position;
import bean.Surface;

@Component
public class Lanceur {

	private static final Logger LOGGER = Logger.getLogger(Lanceur.class
			.getName());
	private File fichierDeCommandes;

	private static Queue<Commande> commandsFile = new LinkedList<Commande>();

	private Surface surface;

	@Scheduled(fixedRate = 5000)
	public void executer() {

		// Chargement du fichier contenant les commandes
		fichierDeCommandes = new File(this.getClass().getClassLoader()
				.getResource("fichierDeCommandes").getFile());

		// Initialisation de la surface
		try {
			surface = CommandeSource.initSurface(fichierDeCommandes);

			// Recuperation des commandes
			List<Commande> listDeCommande = CommandeSource
					.createCommande(fichierDeCommandes);
			commandsFile = new LinkedList<Commande>(listDeCommande);

			// Traitement des commandes
			int i = 0;
			while (!commandsFile.isEmpty()) {
				i++;
				// Recuperation de la commande
				Commande commande = commandsFile.poll();

				// Traitement de la commande
				Position position = new Tondeuse(commande, surface).deplacer();

				// Affichage de la position de la tondeuse
				LOGGER.info("Tondeuse n° " + i + ": Position : ("
						+ position.getPosition()[0] + ","
						+ position.getPosition()[1] + " Orientation :"
						+ position.getOrientation() + ")");
			}
		} catch (Exception exp) {
			LOGGER.error("Erreur lors de traitement des commandes "
					+ exp.getMessage());
		}
	}
}
