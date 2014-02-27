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
public class Ascenseur {

    /**
     * Tableau d'étages, qui représente l'immeuble.
     */
    private Etage[] etages;

    /**
     * Entier qui stocke le numéro de l'étage le plus haut.
     */
    private int numEtageLePlusBas;
    
    /**
     * Entier qui stocke le numéro de l'étage le plus bas.
     */
    private int numEtageLePlusHaut;

    /**
     * Objet cabine, qui appartient à l'ascenseur.
     */
    Cabine cabine;

    /**
     * Construit un ascenseur avec une cabine.
     * 
     * @param nombreEtage
     * @param numEtageLePlusBas
     * @param cabine 
     */
    public Ascenseur(int nombreEtage, int numEtageLePlusBas, Cabine cabine) {
        //Initialisation des attributs
        this.setNumEtageLePlusBas(numEtageLePlusBas);
        this.setNumEtageLePlusHaut(numEtageLePlusBas + nombreEtage - 1);
        this.cabine = cabine;
        
        //Modification du tableau d'étages
        setEtages(nombreEtage, numEtageLePlusBas);
    }

    /**
     * Retourne le tableau d'étages de l'ascenseur, tous les étages sont
     * valides.
     *
     * @return
     */
    public Etage[] getEtages() {
        return etages;
    }

    /**
     * Méthode qui génére la liste des étages en fonction du nombre d'étage et
     * de l'étage le plus bas.
     *
     * @param nombreEtage
     * @param numEtageLePlusBas
     */
    public void setEtages(int nombreEtage, int numEtageLePlusBas) {
        this.etages = new Etage[nombreEtage];
        for (int i = 0; i < nombreEtage; i++) {
            this.etages[i] = new Etage(numEtageLePlusBas);
            numEtageLePlusBas++;
        }
    }

    public boolean ajouterPassager(Passager p) {
        Etage depart = p.getEtageDepart();
        for (Etage e : etages) {
            if (e.equals(depart)) {
                e.ajouterPassager(p);
                return true;
            }
        }

        return false;
    }

    public Cabine getCabine() {
        return cabine;
    }

    /**
     * Récupère l'étage suivant de l'ascenseur.
     *
     * @param etage
     * @return l'étage suivant
     */
    public Etage getEtageSuivant(Etage etage) {
        int nb = etage.getNumero() + 1 - this.numEtageLePlusBas;
        if (nb < this.etages.length) {
            return this.etages[nb];
        }
        return null;
    }

    /**
     * Récupère l'étage précédant de l'ascenseur.
     *
     * @param etage
     * @return l'étage suivant
     */
    public Etage getEtagePrecedant(Etage etage) {
        int nb = etage.getNumero() - 1 - this.numEtageLePlusBas;
        if (nb >= 0) {
            return this.etages[nb];
        }
        return null;
    }

    /**
     * Méthode qui affiche l'ascenseur
     *
     */
    public void afficheLaSituation() {
        String s = "\n";
        for (int i = this.etages.length - 1; i >= 0; i--) {
            s += this.etages[i].affiche(cabine.getEtage());
        }

        s += cabine.toString();
        s += "Distance parcourue : " + cabine.getDistanceParcourue() + "\n";
        s += "Nombre de passagers descendus : " + cabine.getNombrePassagersSorties() + "\n";
        System.out.println(s);
    }

    /**
     * Permet de récupérer le numéro de l'étage le plus bas.
     * 
     * @return 
     */
    public int getNumEtageLePlusBas() {
        return numEtageLePlusBas;
    }

    /**
     * Modifie le numéro de l'étage le plus bas.
     * 
     * @param numEtageLePlusBas 
     */
    public void setNumEtageLePlusBas(int numEtageLePlusBas) {
        this.numEtageLePlusBas = numEtageLePlusBas;
    }

    /**
     * Permet de récupérer le numéro de l'étage le plus haut.
     * 
     * @return 
     */
    public int getNumEtageLePlusHaut() {
        return numEtageLePlusHaut;
    }

     /**
     * Modifie le numéro de l'étage le plus haut.
     * 
     * @param numEtageLePlusHaut 
     */
    public void setNumEtageLePlusHaut(int numEtageLePlusHaut) {
        this.numEtageLePlusHaut = numEtageLePlusHaut;
    }

}
