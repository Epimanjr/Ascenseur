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
     * Priorité de la cabine avec la codification : '-' : arrêtée '^' : monte 'v' : descend
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
     *
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
        for (int i = 0; i < this.passagers.length; i++) {
            Passager p = this.passagers[i];
            if (p != null) {
                if (p.getEtageDestination().equals(etage)) {
                    //Il veut aller ici
                    this.passagers[i] = null;
                    this.setNombrePassagersSorties(this.getNombrePassagersSorties() + 1);
                }
            }
        }

        //Remplissage de la cabine
        this.remplirCabine(etage.getFileAttente());

        boolean traiterAppels = false;

        //Si la cabine n'est pas vide
        if (!estVide()) {
            //On traite les appels internes
            if (!traiterAppelsInternes(e, date)) {
                this.inversePriorite();
                traiterAppels = traiterAppelsInternes(e, date);
            }
        } else {
            //On traite les appels externes
            traiterAppels = traiterAppelsExternes(e, date);
        }

        //Si il n'y pas eu d'appels à traiter, cabine à l'arrêt
        if (traiterAppels == false) {
            this.setPriorite('-');
        }

    }

    /**
     * On traite les appels externes,
     *
     * @param e
     * @param date
     * @return
     */
    public boolean traiterAppelsExternes(Echeancier e, int date) {
        if (this.getPriorite() == '^') {
            //On parcourt les étages supérieurs
            Etage et;
            while ((et = this.ascenseur.getEtageSuivant(this.getEtage())) != null) {
                //Si des gens attendent sur le palier
                if (et.getFileAttente().size() > 0) {
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtageSuivant(etage)));
                    return true;
                }
            }
        } else if (this.getPriorite() == 'v') {
            //On parcourt les étages inférieurs
            Etage et;
            while ((et = this.ascenseur.getEtagePrecedant(this.getEtage())) != null) {
                //Si des gens attendent sur le palier
                if (et.getFileAttente().size() > 0) {
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtagePrecedant(etage)));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * On traite les demandes des passagers dans la cabine
     *
     * @param e
     * @param date
     * @return
     */
    public boolean traiterAppelsInternes(Echeancier e, int date) {

        for (Passager passager : this.passagers) {
            if (passager != null) {
                //S'il veut monter et que la cabine monte, => new PCP
                if (passager.getEtageDestination().getNumero() > this.etage.getNumero() && this.getPriorite() == '^') {
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtageSuivant(etage)));
                    return true;
                } else if (passager.getEtageDestination().getNumero() < this.etage.getNumero() && this.getPriorite() == 'v') {
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtagePrecedant(etage)));
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Si la cabine est arrêté, on la démarre !
     *
     * @param e
     * @param date
     */
    public void demarrer(Echeancier e, int date, Passager p) {
        //S'il veut monter
        if (p.getEtageDestination().getNumero() > this.etage.getNumero()) {
            this.setPriorite('^');
            //On crée l'évènement PCP sur l'étage suivant
            e.ajouter(new EvenementPassage(date, this.ascenseur.getEtageSuivant(this.getEtage())));
        //Sinon
        } else {
            this.setPriorite('v');
            //On crée l'évènement PCP sur l'étage précédant
            e.ajouter(new EvenementPassage(date, this.ascenseur.getEtagePrecedant(this.getEtage())));
        }
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

    /**
     * Inverse la priorité de la cabine.
     */
    public void inversePriorite() {
        if (this.getPriorite() == 'v') {
            this.setPriorite('^');
        } else {
            this.setPriorite('v');
        }
    }

}
