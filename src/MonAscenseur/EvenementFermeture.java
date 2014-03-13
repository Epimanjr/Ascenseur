package MonAscenseur;

//FPC
/**
 *
 * @author blaise
 */
public class EvenementFermeture extends Evenement {

    /**
     * Constructeur qui se contente d'appeler le constructeur de sa classe
     * parent.
     *
     * @param d une date
     */
    public EvenementFermeture(int d) {
        super(d);
    }

    /*
     Se produit au moment précis où les portes de la cabine viennent juste de terminer leur fermeture.
     Sachant également que le temps mis par la cabine pour aller d’un étage à un autre est constant et vaut exactement 14 dixièmes de seconde,
     l’algorithme de traitement de FPC ajoute le prochain PPC à la bonne date.
     Attention au cas particulier ou l’immeuble serait vide (rare mais possible !).
     */
    @Override
    public void traiter(Echeancier e, Ascenseur a) {
        System.out.println("Ouverture Porte Cabine !");
        a.getCabine().actionApresFermeture(e, date);
        System.out.println("Fin traitement FPC");
    }

    /**
     * Affichage d'un événement OPC.
     */
    @Override
    public void affiche() {
        System.out.println("FPC");
    }

}
