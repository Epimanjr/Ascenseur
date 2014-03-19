package MonAscenseur;

import java.util.LinkedList;

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
public class Cabine {

    /**
     * Etage courant (celui le plus proche de la cabine).
     */
    private Etage etage;

    /**
     * Les passagers actuellement dans la cabine.
     */
    private final Passager[] passagers;

    /**
     * Priorité de la cabine avec la codification : '-' : arrêtée '^' : monte
     * 'v' : descend
     */
    private char priorite;

    /**
     * Représente l'asenseur dans lequel se trouve la cabine
     */
    private Ascenseur ascenseur;

    /**
     * Représente la vitesse de la cabine.
     */
    private int vitesse;

    /**
     * Distance parcourue de la cabine.
     */
    private int distanceParcourue;

    /**
     * Variable incrémentée dès qu'un passager est sorti de la cabine.
     */
    private int nombrePassagersSorties;

    private final boolean barbare;

    public Cabine(Etage etage, int vitesse, boolean modeBarbare) {
        //Initialisation de l'étage

        this.etage = etage;
        this.passagers = new Passager[8]; // 8 personnes dans la cabine

        this.priorite = '-';
        this.vitesse = vitesse;

        this.barbare = modeBarbare;
        if(this.barbare) {
            System.out.println(getColor(91)+"Mode barbare lancé !"+getColor(0));
        }
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

        int placeLibre = this.passagers.length - nombrePassagers();
        return placeLibre;
    }

    /**
     * Pour tester si la cabine est vide ou non.
     *
     * @return
     */
    public boolean estVide() {
        return this.nombrePassagers() == 0;
    }

    /**
     * Méthode qui se charge de remplir correctement la cabine.
     *
     * @param list
     * @return
     */
    public int remplirCabine(LinkedList list) {

        int i = 0, temps = 0;
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

                // 4 dixième de seconde
                temps += 4;
            } else {
                i++;
            }
        }

        return temps;
    }

    /**
     * Ajoute un passager dans la cabine.
     *
     * @param p
     */
    @SuppressWarnings("empty-statement")
    public void ajoutePassager(Passager p) {
        if (this.placeLibreDansCabine() > 0) {

            boolean ajoute = false;
            int it = 0;
            while (!ajoute) {
                if (this.passagers[it] == null) {
                    this.passagers[it] = p;
                    ajoute = true;
                } else {
                    it++;
                }
            }

        }
    }

    /**
     * Méthode appelée lors du traitement d'un PCP
     *
     * @param e
     * @param etage
     * @param date
     */
    public void actionAvantOuverture(Echeancier e, Etage etage, int date) {
        //La cabine change d'étage
        this.setEtage(etage);

        this.setDistanceParcourue(this.getDistanceParcourue() + 1);

        if (this.etage.getNumero() == ascenseur.getNumEtageLePlusHaut() || this.etage.getNumero() == ascenseur.getNumEtageLePlusBas()) {
            this.inversePriorite();
        }

        // Test OPC
        if (doitOuvrirPorteCabine()) {
            e.ajouter(new EvenementOuverture(date + 1));
        } else {
            actionApresFermeture(e, date);
        }
    }

    /**
     * Méthode appelé après l'ouverture des portes.
     *
     * @param e
     * @param date
     */
    public void actionApresOuverture(Echeancier e, int date) {
        // Temps total, arrêt, ouverture et 
        int temps = 5;
        //On fait descendre les passagers qui veulent aller à l'étage courant
        for (int i = 0; i < this.passagers.length; i++) {
            Passager p = this.passagers[i];
            if (p != null) {
                if (p.getEtageDestination().equals(etage)) {
                    //Il veut aller ici
                    this.passagers[i] = null;
                    this.setNombrePassagersSorties(this.getNombrePassagersSorties() + 1);

                    // 4 dixième de seconde
                    temps += 4;
                }
            }
        }

        //Remplissage de la cabine
        temps += this.remplirCabine(this.etage.getFileAttente());

        // Générer fermeture cabine
        e.ajouter(new EvenementFermeture(date + temps));
    }

    /**
     * Méthode appelée après la fermeture des portes (ou bien si la cabine n'a
     * pas besoin de s'arrêter)
     *
     * @param e
     * @param date
     */
    public void actionApresFermeture(Echeancier e, int date) {
        System.out.println("Cabine fermé !");

        // On traite une première fois les appels
        boolean traiterAppels = traiter(e, date);

        if (!traiterAppels) {
            this.inversePriorite();
            // Un seconde fois si jamais rien
            traiterAppels = traiter(e, date);
        }

        //Si il n'y pas eu d'appels à traiter, cabine à l'arrêt
        if (!traiterAppels) {
            this.setPriorite('-');
        }
    }

    public boolean doitOuvrirPorteCabine() {

        // Si un passager veut aller à l'étage courant.
        for (Passager p : this.passagers) {
            if (p != null) {
                if (p.getEtageDestination().equals(etage)) {
                    //Il veut aller ici
                    return true;
                }
            }
        }

        // Ou bien si un passager se trouve sur le palier
        LinkedList list = this.getEtage().getFileAttente();
        int i = 0;
        Passager passager;

        //Tant qu'il y a de la place
        while ((placeLibreDansCabine() > 0) && (i < list.size())) {
            //On parcourt la liste d'attente de l'étage !

            //On récupère le passager
            passager = (Passager) list.get(i);

            if (this.doitPrendrePassager(passager)) {
                return true;
            } else {
                i++;
            }
        }

        return false;
    }

    /**
     * Méthode appelée lors du traitement d'un PCP
     *
     * @param e
     * @param etage
     * @param date
     */
    public void action(Echeancier e, Etage etage, int date) {

        System.out.println("Début action");

        //La cabine change d'étage
        this.setEtage(etage);

        this.setDistanceParcourue(this.getDistanceParcourue() + 1);

        if (this.etage.getNumero() == ascenseur.getNumEtageLePlusHaut() || this.etage.getNumero() == ascenseur.getNumEtageLePlusBas()) {
            this.inversePriorite();
        }

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
        this.remplirCabine(this.etage.getFileAttente());

        boolean traiterAppels = traiter(e, date);

        if (!traiterAppels) {
            this.inversePriorite();
            traiterAppels = traiter(e, date);
        }

        //Si il n'y pas eu d'appels à traiter, cabine à l'arrêt
        if (!traiterAppels) {
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
            Etage et = this.getEtage();
            while ((et = this.ascenseur.getEtageSuivant(et)) != null) {
                //Si des gens attendent sur le palier
                if (et.getFileAttente().size() > 0) {

                    System.out.println("Début ajouter appel externe 1");
                    e.ajouter(new EvenementPassage(date + vitesse, this.ascenseur.getEtageSuivant(etage)));
                    System.out.println("Fin ajouter appel externe 1");

                    return true;
                }
            }
        } else if (this.getPriorite() == 'v') {
            //On parcourt les étages inférieurs
            Etage et = this.getEtage();
            while ((et = this.ascenseur.getEtagePrecedant(et)) != null) {
                //Si des gens attendent sur le palier
                if (et.getFileAttente().size() > 0) {
                    System.out.println("Début ajouter appel externe 2");
                    e.ajouter(new EvenementPassage(date + vitesse, this.ascenseur.getEtagePrecedant(etage)));
                    System.out.println("Fin ajouter appel externe 2");
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
                    System.out.println("Début ajouter appel interne 1");
                    e.ajouter(new EvenementPassage(date + vitesse, this.ascenseur.getEtageSuivant(etage)));
                    System.out.println("Fin ajouter appel interne 1");
                    return true;
                } else if (passager.getEtageDestination().getNumero() < this.etage.getNumero() && this.getPriorite() == 'v') {
                    System.out.println("Début ajouter appel interne 2");
                    e.ajouter(new EvenementPassage(date + vitesse, this.ascenseur.getEtagePrecedant(etage)));
                    System.out.println("Fin ajouter appel interne 2");
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
     * @param p
     */
    public void demarrer(Echeancier e, int date, Passager p) {
        System.out.println("Démarrage cabine");
        //S'il veut monter
        if (p.getEtageDestination().getNumero() > this.etage.getNumero()) {
            this.setPriorite('^');
            //On crée l'évènement PCP sur l'étage suivant
            e.ajouter(new EvenementPassage(date + vitesse, this.ascenseur.getEtageSuivant(this.getEtage())));
            //Sinon
        } else {
            this.setPriorite('v');
            //On crée l'évènement PCP sur l'étage précédant
            e.ajouter(new EvenementPassage(date + vitesse, this.ascenseur.getEtagePrecedant(this.getEtage())));
        }

        System.out.println("Fin démarrage");
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

    /**
     * Pour savoir si la cabine est à l'arrêt ou non.
     *
     * @return
     */
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
        for (Passager p : this.getPassagers()) {
            if (p != null) {
                s += "//" + p.toString() + "\\";
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

    /**
     * Retourne vrai si la cabine est en train de monter.
     *
     * @return
     */
    public boolean monte() {
        return this.getPriorite() == '^';
    }

    /**
     * Retourne vrai si la cabine est en train de descendre.
     *
     * @return
     */
    public boolean descend() {
        return this.getPriorite() == 'v';
    }

    /**
     * Méthode pour savoir si on doit prendre un passager ou non.
     *
     * @param p
     * @return
     */
    private boolean doitPrendrePassager(Passager p) {
        /*int min = this.ascenseur.getNumEtageLePlusBas();
         int max = this.ascenseur.getNumEtageLePlusHaut();

         int etageTraite = this.getEtage().getNumero();
         int etagePassager = p.getEtageDestination().getNumero();

         return (etageTraite == min || etageTraite == max || (monte() && etagePassager > etageTraite) || (descend() && etagePassager < etageTraite) || !this.enMouvement() || this.estVide());*/
        if (barbare) {
            return true;
        } else {
            int min = this.ascenseur.getNumEtageLePlusBas();
            int max = this.ascenseur.getNumEtageLePlusHaut();

            int etageTraite = this.getEtage().getNumero();
            int etagePassager = p.getEtageDestination().getNumero();

            return (etageTraite == min || etageTraite == max || (monte() && etagePassager > etageTraite) || (descend() && etagePassager < etageTraite) || !this.enMouvement() || this.estVide());
        }
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

    /**
     * Permet simplement de colorer le terminal LINUX.
     *
     * @param i
     * @return
     */
    public static String getColor(int i) {
        return "\033[" + i + "m";
    }

    /**
     * Traite les appels internes et externes
     *
     * @param e
     * @param date
     * @return
     */
    private boolean traiter(Echeancier e, int date) {

        //On traite les appels internes en fonction de la priorité
        boolean res = traiterAppelsInternes(e, date);
        if (!res) {
            //Si c'est pas bon, on traite les appels externes en fonction de la priorité
            res = traiterAppelsExternes(e, date);

        }

        return res;
    }

}
