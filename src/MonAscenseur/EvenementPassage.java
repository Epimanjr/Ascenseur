package MonAscenseur;

/**
 *
 * @author Maxime Blaise
 * @author Antoine Nosal
 */
public class EvenementPassage extends Evenement {
    
    Etage etageSuivant;

    public EvenementPassage(int d) {
        super(d);
    }

    /**
     *
     * @param date
     * @param etageSuivant
     */
    public EvenementPassage(int date, Etage etageSuivant) {
        super(date);
        this.etageSuivant = etageSuivant;
    }
    
/*
Se produit au moment précis où les portes de la cabine viennent juste de terminer leur fermeture.
Sachant également que le temps mis par la cabine pour aller d’un étage à un autre est constant et vaut exactement 14 dixièmes de seconde,
l’algorithme de traitement de FPC ajoute le prochain PPC à la bonne date.
Attention au cas particulier ou l’immeuble serait vide (rare mais possible !).
*/

    @Override
    public void traiter(Echeancier e, Ascenseur a) {
        a.getCabine().action(e, etageSuivant, date);
    }

    @Override
    public void affiche() {
        System.out.println("["+date + ", PCP, "+etageSuivant.getNumero()+"], ");
    }
    
}
