package MonAscenseur;

import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author blaise
 */
public class Cabine {

    /**
     * Etage courant (celui le plus proche de la cabine).
     */
    private Etage etage;

    /**
     * Les passagers actuellement dans la cabine.
     */
    private Passager[] passagers;

    /**
     * Priorité de la cabine avec la codification : '-' : arrêtée '^' : monte
     * 'v' : descend
     */
    private char priorite;

    private Ascenseur ascenseur;

    private int vitesse;
    
    private int distanceParcourue;
    private int nombrePassagersSorties;

    public Cabine(Etage etage, int vitesse) {
        //Initialisation de l'étage

        this.etage = etage;
        this.passagers = new Passager[8]; // 8 personnes dans la cabine
        this.priorite = '-';
        this.vitesse = vitesse;
    }

    public int getNombrePassagersSorties() {
        return nombrePassagersSorties;
    }

    public void setNombrePassagersSorties(int nombrePassagersSorties) {
        this.nombrePassagersSorties = nombrePassagersSorties;
    }
    
    

    public Ascenseur getAscenseur() {
        return ascenseur;
    }

    public void setAscenseur(Ascenseur ascenseur) {
        this.ascenseur = ascenseur;
    }

    /**
     * Récupère la distance parcourue au total.
     * @return 
     */
    public int getDistanceParcourue() {
        return distanceParcourue;
    }

    public void setDistanceParcourue(int distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }
    
    

    /**
     * Parcours le tableau de passagers et retourne la liste des cases non null.
     *
     * @return
     */
    public int nombrePassagers() {
        int nb = 0;
        for (Passager passager : passagers) {
            if (passager != null) {
                nb++;
            }
        }
        return nb;

    }

    public int placeLibreDansCabine() {
        return this.passagers.length - nombrePassagers();
    }

    public boolean estVide() {
        return this.nombrePassagers() == 0;
    }

    public void remplirCabine(LinkedList list) {
        boolean cabineVide = this.estVide();
        int i = 0, et = 0; //et = étage
        Passager passager;

        //Tant qu'il y a de la place
        while ((placeLibreDansCabine() > 0) && (i < list.size())) {
            //On parcourt la liste d'attente de l'étage !

            //On récupère le passager
            passager = (Passager) list.get(i);

            if (this.doitPrendrePassager(passager)) {
                //Alors on ajoute le passager dans la cabine
                this.ajoutePassager(passager);
                //Et on l'enlève de la file d'attente
                list.remove(i);
            } else {
                i++;
            }
        }
    }

    @SuppressWarnings("empty-statement")
    public void ajoutePassager(Passager p) {
        if (this.placeLibreDansCabine() > 0) {

            for (int i = 0; passagers[i] != null; i++) {
                passagers[i] = p;
            }

        }
    }

    public void action(Echeancier e, Etage etage, int date) {
        //La cabine change d'étage
        this.setEtage(etage);
        
        this.setDistanceParcourue(this.getDistanceParcourue() + 1);

        //On fait descendre les passagers qui veulent aller à l'étage courant
        for(int i=0;i<this.passagers.length;i++) {
            Passager p = this.passagers[i];
            if(p != null ) {
                if(p.getEtageDestination().equals(etage)) {
                    //Il veut aller ici
                    this.passagers[i] = null;
                    this.setNombrePassagersSorties(this.getNombrePassagersSorties() + 1);
                }
            }
        }
        
        //Remplissage de la cabine
        etage.remplirCabine(this);
        
        int new_date = date + this.ascenseur.getCabine().getEtage().getArrivees().suivant();
        
        //On traite à nouveau les appels 
        if(this.traiterAppels(e, new_date) == 0) {
            this.demarrer(e,  new_date);
        }
        
        //Si la cabine ne bouge pas, on la relance.
        if(!enMouvement()) {
            this.demarrer(e,  new_date);
        }
    }

    /**
     * Si la cabine est arrêté, on la démarre !
     *
     * @param e
     * @param date
     */
    public void demarrer(Echeancier e, int date) {
        //On créer un passager, et on calcule la nouvelle date de l'échéancier
        Passager p = null;
        int new_date = date + this.ascenseur.getCabine().getEtage().getArrivees().suivant();

        if (!this.estVide()) {
            //Cabine non vide, on cherche un passager et on traite sa demande
            for (int i = 0; p == null; i++) {
                p = passagers[i];
            }

            //On a maintanant un passager non null, on récupère son étage
            int etagePassager = p.getEtageDestination().getNumero();
            if (etagePassager > this.getEtage().getNumero()) {
                //On doit monter ! + ajout de l'événement
                this.setPriorite('^');
                e.ajouter(new EvenementPassage(new_date, ascenseur.getEtageSuivant(this.getEtage())));
            } else {
                //On doit descendre.
                this.setPriorite('v');
                e.ajouter(new EvenementPassage(new_date, ascenseur.getEtagePrecedant(this.getEtage())));
            }
        } else {
            //Cabine vide ! On doit traiter les appels
            traiterAppels(e, new_date);
        }
    }

    public int traiterAppels(Echeancier echeancier, int date) {
        for (Etage e : ascenseur.getEtages()) {
            //Etage vide ?
            boolean etageVide = e.estVide();

            if (!etageVide) {
                int numeroEtage = e.getNumero();

                if (numeroEtage > this.getEtage().getNumero()) {
                    //On doit monter ! + ajout de l'événement
                    this.setPriorite('^');
                    echeancier.ajouter(new EvenementPassage(date, ascenseur.getEtageSuivant(this.getEtage())));
                } else {
                    //On doit descendre.
                    this.setPriorite('v');
                    echeancier.ajouter(new EvenementPassage(date, ascenseur.getEtagePrecedant(this.getEtage())));
                }

                return 1;
            }
        }

        return 0;
    }

    /**
     * Permet de récupérer la vitesse de la cabine.
     *
     * @return
     */
    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public boolean enMouvement() {
        return (this.priorite != '-');
    }

    /**
     * Méthode qui affiche le contenu de la cabine.
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "Contenu de la cabine : ";
        if (this.passagers[0] != null) {
            s += this.passagers[0].toString();
        }
        for (int i = 1; i < this.passagers.length; i++) {
            if (this.passagers[i] != null) {
                s += ", " + this.passagers[i];
            }
        }
        s += "\nPriorité de la cabine : " + this.getPriorite() + "\n";
        return s;
    }

    /**
     * Méthode qui retourne l'étage courant.
     *
     * @return etage
     */
    public Etage getEtage() {
        return etage;
    }

    /**
     * Méthode qui permet de modifier l'étage courant
     *
     * @param etage
     */
    public void setEtage(Etage etage) {
        this.etage = etage;
    }

    /**
     * Méthode qui retourne la liste des passagers présents dans la cabine.
     *
     * @return passagers
     */
    public Passager[] getPassagers() {
        return passagers;
    }

    /**
     * Méthode qui récupère la priorité actuelle de la cabine.
     *
     * @return priorite
     */
    public char getPriorite() {
        return priorite;
    }

    /**
     * Méthode qui permet de modifier la priorité de la cabine.
     *
     * @param priorite
     */
    public void setPriorite(char priorite) {
        this.priorite = priorite;
    }

    public boolean monte() {
        return this.getPriorite() == '^';
    }

    public boolean descend() {
        return this.getPriorite() == 'v';
    }

    private boolean doitPrendrePassager(Passager p) {
        int min = this.ascenseur.getNumEtageLePlusBas();
        int max = this.ascenseur.getNumEtageLePlusHaut();

        int etage = this.getEtage().getNumero();
        int etagePassager = p.getEtageDestination().getNumero();

        if (etage == min || etage == max || (monte() && etagePassager > etage) || (descend() && etagePassager < etage) || !this.enMouvement()) {
            return true;
        }

        return false;
    }

}
