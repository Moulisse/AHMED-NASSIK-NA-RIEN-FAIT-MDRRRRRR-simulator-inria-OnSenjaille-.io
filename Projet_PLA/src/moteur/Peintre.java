package moteur;


public class Peintre extends Personnage{
	
	public Peintre(int e,Automate a,Position p){
		super(e,a,p);
	}
	
	// TODO
	//1 = bleu
	// 2 = rouge*
	
	public void peindre(int direction,int couleur){
		int x = this.position().getX();	//verif au niveau de l'ordonanceur.
		int y = this.position().getY();
		if (couleur==1||couleur==2){
			switch (direction){
			case 0 :	//Nord
				this.partie.decor()[x][y-1].peindre(couleur);
				break;
			case 1 : 	//Est
				this.partie.decor()[x+1][y].peindre(couleur);
				break;
			case 2 : 	//Sud
				this.partie.decor()[x][y+1].peindre(couleur);
				break;
			case 3 : 	//Ouest
				this.partie.decor()[x-1][y].peindre(couleur);
				break;
			default : 	//Sur Place
			}
		}
	}
}
