package MonAscenseur;

public abstract class Evenement {
    
    protected int date;
    
    public Evenement (int d) {
	date = d;
    }
    
    public abstract void traiter (Echeancier e, Ascenseur a);
    
    public abstract void affiche();
  /*  public void affiche (Guichet guichet) {
	System.out.print("[t=" + date + ",");
	this.afficheDetails(guichet);
	System.out.print("]");
    }
    
    public abstract void afficheDetails (Guichet guichet);*/
    
}
