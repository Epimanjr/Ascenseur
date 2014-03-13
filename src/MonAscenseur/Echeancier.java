package MonAscenseur;

import java.util.*;

/**
 * 
 * @author Maxime Blaise
 * @author Antoine Nosal
 */
public class Echeancier {
    
    /**
     * Représente la liste des événements à traiter.
     */
    private final LinkedList listeEvenements;
    
    /**
     *  Construction de l'échéancier 
     */
    public Echeancier () {
        //Initialisation
	listeEvenements = new LinkedList();
    }
    
    /**
     * Ajoute un événement à la liste.
     * 
     * @param e 
     */
    public void ajouter (Evenement e) {
	int pos = 0;
	while ( pos < listeEvenements.size() ) {
            
	    if (((Evenement) listeEvenements.get(pos)).date > e.date) {
		listeEvenements.add(pos, e);
	    } else {
		pos++;
	    }
	}
	listeEvenements.add(pos, e);
    }
    
    /**
     * Récupère le premier énénement pour le traiter.
     * 
     * @return 
     */
    public Evenement retourneEtEnlevePremier () {
	Evenement e = (Evenement) listeEvenements.getFirst();
	listeEvenements.removeFirst();
	return e;
    }
    
    /**
     * Retourne vrai si la liste d'événements est vide.
     * @return 
     */
    public boolean estVide() {
	return (listeEvenements.size() == 0);
    }
    
    /**
     * Affiche la situation de l'échéancier à un temps t.
     * 
     */
    public void afficheLaSituation() {
	System.out.print("Echeancier :");
	int index = 0;
	while ( index < listeEvenements.size() ) {
	    ((Evenement) listeEvenements.get(index)).affiche();
	    index++;
        }
    }
    
}
