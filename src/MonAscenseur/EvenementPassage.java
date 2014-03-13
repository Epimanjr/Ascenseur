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

    @Override
    public void traiter(Echeancier e, Ascenseur a) {
        System.out.println("Début traitement PCP");
        a.getCabine().actionAvantOuverture(e, etageSuivant, date);
        System.out.println("Fin traitement PCP");
    }

    @Override
    public void affiche() {
        System.out.println("["+date + ", PCP, "+etageSuivant.getNumero()+"], ");
    }
    
}
