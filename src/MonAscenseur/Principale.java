package MonAscenseur;

import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blaise
 */
public class Principale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean barbare = false;
        
        if(args.length == 1) {
            if(args[0].equals("-d")) {
                barbare = true;
            }
        }
        
        //Création de la cabine
        Cabine cabine = new Cabine(new Etage(6), 10, barbare);
        //Initialisation de l'ascenseur
        Ascenseur ascenseur = new Ascenseur(8,-1, cabine);
        cabine.setAscenseur(ascenseur);
        //Création de l'échéancier 
        Echeancier echeancier = new Echeancier(); 
        //Création d'un premier évènement
        echeancier.ajouter(new EvenementArrivee(0));
 
        
        // Boucle principale du simulateur: 
	while ( ! echeancier.estVide() ) {

            //Affichage du simulateur
	    System.out.println("\n\n\n\n----- Etat actuel du simulateur -----");
	    ascenseur.afficheLaSituation();
            echeancier.afficheLaSituation();

	   System.out.println("Taper sur Enter pour faire un nouveau pas de simulation.");
	    try {
		int justePourAttendreIci = System.in.read();
	    }
	    catch (IOException e) {}

	    Evenement evenement = echeancier.retourneEtEnlevePremier();
	    evenement.traiter(echeancier, ascenseur);
	}
    }
    
}
