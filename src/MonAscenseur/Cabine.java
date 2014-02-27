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
    private final Passager[] passagers;

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
        for (Passager p : this.passagers) {
            if (p == null) {
                System.out.println(getColor(96) + "\tPassager null !" + getColor(0));
            } else {
                System.out.println(getColor(96) + "\tPassager NOT null !" + getColor(0));
            }
        }
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
        System.out.println(getColor(96) + "\tNombre passagers :  !" + nombrePassagers() + getColor(0));

        int placeLibre = this.passagers.length - nombrePassagers();
        System.out.println(getColor(96) + "\tPlace libre  :  !" + placeLibre + getColor(0));
        return placeLibre;
    }

    public boolean estVide() {
        return this.nombrePassagers() == 0;
    }

    public void remplirCabine(LinkedList list) {
        System.out.println(getColor(91) + "Remplissage de la cabine ~~ " + this.etage + getColor(0));
        boolean cabineVide = this.estVide();
        int i = 0, et = 0; //et = étage
        Passager passager;
        
        System.out.println(getColor(94)+"\t\tTaille file attente : "+list.size());

        //Tant qu'il y a de la place
        while ((placeLibreDansCabine() > 0) && (i < list.size())) {
            System.out.println(getColor(94) + "\t\twhile - remplir" + getColor(0));
            //On parcourt la liste d'attente de l'étage !

            //On récupère le passager
            passager = (Passager) list.get(i);

            if (this.doitPrendrePassager(passager)) {
                //Alors on ajoute le passager dans la cabine
                System.out.println(getColor(92) + "\tDoit prendre passager !" + getColor(0));
                this.ajoutePassager(passager);
                //Et on l'enlève de la file d'attente
                list.remove(i);
            } else {
                i++;
            }
        }

        System.out.println(getColor(91) + "Fin remplissage" + getColor(0));
    }

    @SuppressWarnings("empty-statement")
    public void ajoutePassager(Passager p) {
        System.out.println(getColor(91) + "Début Ajoute Passager" + getColor(0));
        if (this.placeLibreDansCabine() > 0) {

            boolean ajoute = false;
            int it = 0;
            while (!ajoute) {
                if (this.passagers[it] == null) {
                    this.passagers[it] = p;
                    ajoute = true;
                    System.out.println(getColor(92) + "\tPassager ajouté !" + getColor(0));
                } else {
                    it++;
                }
            }

        }

        System.out.println(getColor(91) + "Fin Ajoute passager" + getColor(0));
    }

    public void action(Echeancier e, Etage etage, int date) {
        System.out.println(getColor(91) + "Début traitement PCP" + getColor(0));
        
        //Remplissage de la cabine
        //this.remplirCabine(this.etage.getFileAttente());
        
        //La cabine change d'étage
         this.setEtage(etage);

        this.setDistanceParcourue(this.getDistanceParcourue() + 1);
        
        System.out.println("~~~((((Numéro de l'étage courant : "+this.etage.getNumero());
        System.out.println("Plus haut : "+ascenseur.getNumEtageLePlusHaut());
        System.out.println("Plus bas : "+ascenseur.getNumEtageLePlusBas());
                
        
        if(this.etage.getNumero() == ascenseur.getNumEtageLePlusHaut() || this.etage.getNumero() == ascenseur.getNumEtageLePlusBas()) {
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

        if(!traiterAppels) {
            this.inversePriorite();
            traiterAppels = traiter(e, date);
        }
        
        //Si il n'y pas eu d'appels à traiter, cabine à l'arrêt
        if (!traiterAppels) {
            this.setPriorite('-');
        }

       
        System.out.println(getColor(91) + "FIN traitement PCP" + getColor(0));
    }

    /**
     * On traite les appels externes,
     *
     * @param e
     * @param date
     * @return
     */
    public boolean traiterAppelsExternes(Echeancier e, int date) {
        System.out.println(getColor(91) + "Début Traiter appels externes" + getColor(0));
        if (this.getPriorite() == '^') {
            //On parcourt les étages supérieurs
            Etage et = this.getEtage();
            while ((et = this.ascenseur.getEtageSuivant(et)) != null) {
                System.out.println(getColor(91) + "while - externes - 1" + getColor(0));
                //Si des gens attendent sur le palier
                if (et.getFileAttente().size() > 0) {
                    
                    System.out.println("Ajout PCP : "+etage);
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtageSuivant(etage)));

                    System.out.println(getColor(91) + "Fin traiter appels externes" + getColor(0));
                    return true;
                }
            }
        } else if (this.getPriorite() == 'v') {
            //On parcourt les étages inférieurs
            Etage et = this.getEtage();
            while ((et = this.ascenseur.getEtagePrecedant(et)) != null) {
                System.out.println("while - externes - 2");
                //Si des gens attendent sur le palier
                if (et.getFileAttente().size() > 0) {
                    System.out.println("Ajout PCP : "+etage);
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtagePrecedant(etage)));

                    System.out.println(getColor(91) + "Fin traiter appels externes" + getColor(0));
                    return true;
                }
            }
        }

        System.out.println(getColor(91) + "Fin traiter appels externes - false" + getColor(0));
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

        System.out.println(getColor(91) + "Début traiter appels internes" + getColor(0));

        for (Passager passager : this.passagers) {
            if (passager != null) {
                //S'il veut monter et que la cabine monte, => new PCP
                if (passager.getEtageDestination().getNumero() > this.etage.getNumero() && this.getPriorite() == '^') {
                    System.out.println("Ajout PCP : "+etage);
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtageSuivant(etage)));
                    return true;
                } else if (passager.getEtageDestination().getNumero() < this.etage.getNumero() && this.getPriorite() == 'v') {
                    System.out.println("Ajout PCP : "+etage);
                    e.ajouter(new EvenementPassage(date + 1, this.ascenseur.getEtagePrecedant(etage)));
                    return true;
                }

            }
        }

        System.out.println(getColor(91) + "FIN traiter appels internes" + getColor(0));
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
        //S'il veut monter
        if (p.getEtageDestination().getNumero() > this.etage.getNumero()) {
            this.setPriorite('^');
            //On crée l'évènement PCP sur l'étage suivant
            System.out.println("Ajout PCP : démarrer ~~ "+etage);
            e.ajouter(new EvenementPassage(date, this.ascenseur.getEtageSuivant(this.getEtage())));
            //Sinon
        } else {
            this.setPriorite('v');
            //On crée l'évènement PCP sur l'étage précédant
            System.out.println("Ajout PCP : démarrer ~~"+etage);
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

    public boolean monte() {
        return this.getPriorite() == '^';
    }

    public boolean descend() {
        return this.getPriorite() == 'v';
    }

    private boolean doitPrendrePassager(Passager p) {
        int min = this.ascenseur.getNumEtageLePlusBas();
        int max = this.ascenseur.getNumEtageLePlusHaut();

        int etageTraite = this.getEtage().getNumero();
        int etagePassager = p.getEtageDestination().getNumero();
        
        System.out.println("PRIORITE : "+this.getPriorite());
        System.out.println("DESCEND : "+descend());
        System.out.println("ETAGE PASSAGER : "+(etagePassager < etageTraite));

        return (etageTraite == min || etageTraite == max || (monte() && etagePassager > etageTraite) || (descend() && etagePassager < etageTraite) || !this.enMouvement() || this.estVide());
    }

    /**
     * Inverse la priorité de la cabine.
     */
    public void inversePriorite() {
        System.out.println("INVERSION !!");
        if (this.getPriorite() == 'v') {
            this.setPriorite('^');
        } else {
            this.setPriorite('v');
        }
    }

    public static String getColor(int i) {
        return "\033[" + i + "m";
    }

    /**
     * Traite les appels internes et externes
     * @param e
     * @param date
     * @return 
     */
    private boolean traiter(Echeancier e, int date) {
        
        //On traite les appels internes en fonction de la priorité
        boolean res = traiterAppelsInternes(e, date);
        if(!res) {
            //Si c'est pas bon, on traite les appels externes en fonction de la priorité
            res = traiterAppelsExternes(e, date);
       
        }
        
        return res;
    }

}
