package moteur;


public class Peintre extends Personnage{
	
	public Peintre(int e,Automate a,Position p){
		super(e,a,p);
	}
	
	// TODO
	//1 = bleu
	// 2 = rouge
	public void peindreNordBleu(int couleur){
		int x = this.position().getX();	//verif au niveau de l'ordonanceur.
		int y = this.position().getY();
		this.partie.decor()[x][y-1].peindre(couleur);

	}
	public void peindreSudBleu(int couleur){
		int x = this.position().getX();	//verif au niveau de l'ordonanceur.
		int y = this.position().getY();
		this.partie.decor()[x][y+1].peindre(couleur);
	}
	public void peindreOuestBleu(int couleur){
		int x = this.position().getX();	//verif au niveau de l'ordonanceur.
		int y = this.position().getY();
		this.partie.decor()[x-1][y].peindre(couleur);
	}
	public void peindreEstBleu(int couleur){
		int x = this.position().getX();	//verif au niveau de l'ordonanceur.
		int y = this.position().getY();
		this.partie.decor()[x+1][y].peindre(couleur);
	}
	
}
