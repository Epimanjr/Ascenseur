package MonAscenseur;

//OPC

/**
 *
 * @author blaise
 */
public class EvenementOuverture extends Evenement {
    
    /**
     * Constructeur qui se contente d'appeler le constructeur de sa classe parent.
     * @param d une date
     */
    public EvenementOuverture(int d) {
        super(d);
    }

/*
Se produit au moment très précis où les portes de la cabine viennent juste de terminer leur ouverture.
Cet événement est généré par le traitement d’un événement PCP quand la cabine doit s’arrêter.
Il faut 5 dixièmes de seconde pour arrêter la cabine et ouvrir les portes.
Une personne met exactement 4 dixièmes de seconde pour entrer ou sortir.
Ceci permet de prédire l’événement FPC en fonction du nombre de personnes qui entrent et sortent.
*/
    
    @Override
    public void traiter(Echeancier e, Ascenseur a) {
        //TODO
    }
    
    /**
     * Affichage d'un événement OPC.
     */
    @Override
    public void affiche() {
        System.out.println("OPC");
    }
    
}
