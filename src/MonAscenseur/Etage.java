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
 */
public class Etage {
    /**
     * Numéro de l'étage.
     */
    private final int numero;
    
    /**
     * File d'attente du palier correspondant.
     */
    private final LinkedList<Passager> fileAttente;
    
    /**
     * Loi de poisson.
     */
    private final LoiDePoisson arrivees;

    /**
     * Constructeur qui initialise un étage en fonction du numéro entré en paramètre.
     * @param numero 
     */
    public Etage(int numero) {
        this.numero = numero;
        this.fileAttente = new LinkedList<Passager>();
        this.arrivees = new LoiDePoisson(1, 3);
    }
    
    /**
     * Retourne true si la file d'attente est vide.
     * @return 
     */
    public boolean estVide() {
        return fileAttente.isEmpty();
    }
    
    public void ajouterPassager(Passager p) {
        this.fileAttente.add(p);
    }

    public LinkedList<Passager> getFileAttente() {
        return fileAttente;
    }

    public LoiDePoisson getArrivees() {
        return arrivees;
    }
    
    

    /**
     * Méthode qui premet de récupérer le numéro de l'étage.
     * @return numero
     */
    public int getNumero() {
        return numero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    /**
     * Méthode qui compare deux Etages
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etage other = (Etage) obj;
        return this.numero == other.numero;
    }
    
    public String affiche(Etage cabine) {
        String s ="";
        //Espace, pour faire un affichage jolie
        if(numero < 10 && numero >= 0) {
            s += " ";
        }
        //Affichage du numéro
        s += numero;
        
        //Test sur la cabine
        if(this.equals(cabine)) {
            s+= " C [  ]: ";
        }
        else {
            s+="    [] : ";
        }
        
        //Affichage de la file d'attente
        for(Passager p : fileAttente) {
            s += p.toString() + ", ";
        }
        s += "\n";
        
        
        return s;
    }
    
    
    /**
     * Méthode qui affiche un étage
     * @return 
     */
    @Override
    public String toString() {
        return ("Etage "+numero+" ");
    }
    
    
}
