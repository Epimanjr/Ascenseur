package MonAscenseur;

public class LoiDePoisson {
    /*
      Distribution selon la loi de Poisson pris dans Donald Knuth, 
      semi-numerical algorithms, pages 117-118.
      Me faire signe si j'ai fais une erreur (colnet@loria.fr).
    */

    private int tempsMoyen;
    // tempsMoyen est le temps moyen entre deux occurrences d'un même évènement.
   
    private PressRandomNumberGenerator random;
    // Générateur aléatoire uniforme.
   
    private double p;
    // Le p de la loi de poisson.
   
    LoiDePoisson (int germe, int tm) {
	/* Pour le germe, prendre n'importe quelle valeur plus grande que 0.
	   L'argument tm fixe le tempsMoyen entre deux occurrences.
	 */
	tempsMoyen = tm;
	random = new PressRandomNumberGenerator(germe);
	p = Math.pow(2.7182818284590452353602, (double)tempsMoyen);
	p = 1.0 / p;
    }
   
    private double next_q (double q) {
	double u;
	u = random.doubleSuivant();
	return q * u;
    }
   
    public int suivant () {
	/*
	  Donne le temps de l'occurrence suivante.
	 */
	int r = 0;
	double q = next_q(1.0);
	while (q > p) {
	    r = r + 1;
	    q = next_q(q);
	}
	return r;
    }

}
