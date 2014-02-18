package MonAscenseur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maxime Blaise
 */
public class Passager {
    /**
     * Numéro du passager.
     */
    private int numero; 
	
    /**
     * Nombre de passagers construits depuis le début de la simulation.
     */
    private static int compteurDePassagersConstruits = 0;
    
    /**
     * Etage de départ.
     */
    private Etage etageDepart;
    
    /**
     * Etage de destination du passager.
     */
    private Etage etageDestination;
    
    /**
     * Date d'apparition du passager.
     */
    private int dateApparition;
    
    /**
     * Constructeur sans paramètre.
     */
    public Passager() {
        compteurDePassagersConstruits++;
        numero = compteurDePassagersConstruits;
    }

    /**
     * Constructeur qui initialise un passager avec toutes les informations (étages et date)
     * @param etageDepart objet Etage
     * @param etageDestination objet Etage
     * @param dateApparition 
     */
    public Passager(int dateApparition) {
        compteurDePassagersConstruits++;
        numero = compteurDePassagersConstruits;
        
        this.etageDepart = genererEtage();
        this.etageDestination = genererEtage();
        this.dateApparition = dateApparition;
    }
    
    public static Etage genererEtage() {
        return new Etage((int)(( Math.random() * 10 )-1));
    }
    
    /**
     * Méthode qui retourne une chaîne de caractères qui permet d'afficher un passager.
     * @return 
     */
    public String toString() {
        char c = 'v';
        if(etageDestination.getNumero() > etageDepart.getNumero()) {
            c = '^';
        }
        return ("#"+getNumero()+":"+getEtageDepart().getNumero()+c+getEtageDestination().getNumero()+":"+getDateApparition());
    }

    // GETTERS AND SETTERS
    
    /**
     * Méthode qui récupère le numéro du client
     * @return numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Retourne le nombre de passagers construits depuis le début de la simulation.
     * @return compteurDePassagersConstruits
     */
    public static int getCompteurDePassagersConstruits() {
        return compteurDePassagersConstruits;
    }

    /**
     * Méthode qui retourne l'étage de départ du passager.
     * @return etageDepart
     */
    public Etage getEtageDepart() {
        return etageDepart;
    }

    /**
     * Méthode qui retourne l'étage de destination du passager.
     * @return etageDestination
     */
    public Etage getEtageDestination() {
        return etageDestination;
    }

    /**
     * Méthode qui permet de récupérer la date d'apparition du passager.
     * @return dateApparition
     */
    public int getDateApparition() {
        return dateApparition;
    }
   
    
}
