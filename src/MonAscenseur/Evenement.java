package MonAscenseur;

public abstract class Evenement {
    
    protected int date;
    
    public Evenement (int d) {
	date = d;
    }
    
    public abstract void traiter (Echeancier e, Ascenseur a);
    
    public abstract void affiche();

    
}
