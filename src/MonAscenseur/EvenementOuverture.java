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
