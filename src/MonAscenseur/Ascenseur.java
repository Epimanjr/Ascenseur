package MonAscenseur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author blaise
 */
public class Ascenseur {

    private Etage[] etages;

    private int numEtageLePlusBas;
    private int numEtageLePlusHaut;

    Cabine cabine;

    public Ascenseur(int nombreEtage, int numEtageLePlusBas, Cabine cabine) {
        this.setNumEtageLePlusBas(numEtageLePlusBas);
        this.setNumEtageLePlusHaut(numEtageLePlusBas + nombreEtage);
        this.cabine = cabine;
        setEtages(nombreEtage, numEtageLePlusBas);
    }

    /**
     * Retourne le tableau d'étages de l'ascenseur, tous les étages sont valides.
     * @return 
     */
    public Etage[] getEtages() {
        return etages;
    }

    public void setEtages(Etage[] etages) {
        this.etages = etages;
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
    
    public void ajouterPassager(Etage depart, Passager p) {
        for(Etage e: etages) {
            if(e.equals(depart)) {
                e.ajouterPassager(p);
            }
        }
    }

    public Cabine getCabine() {
        return cabine;
    }
    
    /**
     * Récupère l'étage suivant de l'ascenseur.
     * @param etage
     * @return l'étage suivant
     */
    public Etage getEtageSuivant(Etage etage) {
        return this.etages[etage.getNumero() + 1 - this.numEtageLePlusBas];
    }
    
    
    /**
     * Récupère l'étage précédant de l'ascenseur.
     * @param etage
     * @return l'étage suivant
     */
    public Etage getEtagePrecedant(Etage etage) {
        return this.etages[etage.getNumero() - 1 - this.numEtageLePlusBas];
    }

    
    /**
     * Méthode qui affiche l'ascenseur
     *
     * @return
     */
    public void afficheLaSituation() {
        String s = "\n";
        for(int i=this.etages.length-1 ; i>=0;i--) {
            s += this.etages[i].affiche(cabine.getEtage());
        }
        
        s += cabine.toString();
        s += "Distance parcourue : " + cabine.getDistanceParcourue() + "\n";
        s += "Nombre de passagers descendus : " + cabine.getNombrePassagersSorties() + "\n";
        System.out.println(s);
    }

    public int getNumEtageLePlusBas() {
        return numEtageLePlusBas;
    }

    public void setNumEtageLePlusBas(int numEtageLePlusBas) {
        this.numEtageLePlusBas = numEtageLePlusBas;
    }

    public int getNumEtageLePlusHaut() {
        return numEtageLePlusHaut;
    }

    public void setNumEtageLePlusHaut(int numEtageLePlusHaut) {
        this.numEtageLePlusHaut = numEtageLePlusHaut;
    }
    
    
}
