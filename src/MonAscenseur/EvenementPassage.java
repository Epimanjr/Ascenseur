package MonAscenseur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        a.getCabine().action(e, etageSuivant, date);
    }

    @Override
    public void affiche() {
        System.out.println("["+date + ", PCP, "+etageSuivant.getNumero()+"], ");
    }
    
}
