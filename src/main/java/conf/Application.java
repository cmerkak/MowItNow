package conf;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import bean.Commande;
import bean.Position;
import bean.Surface;
import core.CommandeSource;
import core.Tondeuse;

@SpringBootApplication(scanBasePackages="core")
@EnableScheduling
public class Application {
	
	private static final Logger LOGGER = Logger.getLogger(Application.class
			.getName());
	
	private File fichierDeCommandes;
	
	@Setter
	@Value("${nom.et.chemin.fichier.commande}")
	private String nomEtCheminDuFichierCommande;

	private static Queue<Commande> commandsFile = new LinkedList<Commande>();

	private Surface surface;
	
	@Autowired
	private CommandeSource producteurCommandes;

	/** La dexieme tache ne se lance que lorsque la premiere se termine */
	@Scheduled(fixedDelayString = "${delai.entre.deux.taches.en.milliseconds}")
	public void executer() {

		// Initialisation de la surface
		try {
			// Chargement du fichier contenant les commandes
			fichierDeCommandes = new File(nomEtCheminDuFichierCommande);
			surface = producteurCommandes.initSurface(fichierDeCommandes);

			// Recuperation des commandes
			List<Commande> listDeCommande = producteurCommandes
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
			LOGGER.error("Erreur(s) lors de traitement des commandes "
					+ exp.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		//Gestion en spring boot
		SpringApplication.run(Application.class);
	}
}
