package MonAscenseur;

import java.util.*;

public class Echeancier {
    
    private LinkedList listeEvenements;
    
    public Echeancier () {
	listeEvenements = new LinkedList();
    }
    
    public void ajouter (Evenement e) {
	int pos = 0;
	while ( pos < listeEvenements.size() ) {
	    if (((Evenement) listeEvenements.get(pos)).date > e.date) {
		listeEvenements.add(pos, e);
		return;
	    } else {
		pos++;
	    }
	}
	listeEvenements.add(pos, e);
    }
    
    public Evenement retourneEtEnlevePremier () {
	Evenement e = (Evenement) listeEvenements.getFirst();
	listeEvenements.removeFirst();
	return e;
    }
    
    public boolean estVide() {
	return (listeEvenements.size() == 0);
    }
    
    public void afficheLaSituation() {
	System.out.print("Echeancier :");
	int index = 0;
	while ( index < listeEvenements.size() ) {
	    ((Evenement) listeEvenements.get(index)).affiche();
	    index++;
        }
    }
    
}
