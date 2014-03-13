package MonAscenseur;

//APP

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blaise
 */
public class EvenementArrivee extends Evenement {
    
    private final Passager passager;
    
    /**
     * Constructeur qui se contente d'appeler le constructeur de sa classe parent.
     * @param d une date
     */
    public EvenementArrivee(int d) {
        super(d);
        
        //Initialisation du passager
        passager = new Passager(d);
        
    }

    @Override
    public void traiter(Echeancier e, Ascenseur a) {
      //int calculDate = date + a.getCabine().getEtage().getArrivees().suivant();
  
        //Ajout du passager à la file d'attente du palier
        Etage etageDepart = this.passager.getEtageDepart();
        
        if(!a.ajouterPassager(this.passager)) {
            
            System.out.println("Erreur ajout passager !!!");
        }
        
        //On remet la cabine en mouvement si elle est à l'arrêt (-)
        if(!a.getCabine().enMouvement()) {
            a.getCabine().demarrer(e, this.date+1, this.passager);
        }
        
        //Ajout de l'événement à l'échéancier
        e.ajouter(new EvenementArrivee(etageDepart.getArrivees().suivant()));
    }
    
    /**
     * Affichage d'un événement APP.
     */
    @Override
    public void affiche() {
        System.out.println("["+this.passager.getNumero() + ", APP], ");
    }
    
}
