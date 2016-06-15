package moteur;


public class Guerrier extends Personnage {

	Guerrier(int e,Automate a, Position p){
		super(e,a, p);
	}
	
	/*
	
	public void frapperNord(){
		Personnage cible = this.partie.occupe(pos.getX(),pos.getY()-1);	//verif au niveau de l'ordonanceur.
		if (cible!=null)
				cible.beaten();
	}
	public void frapperEst(){
		Personnage cible = this.partie.occupe(pos.getX(),pos.getX()+1);	//verif au niveau de l'ordonanceur.
		if (cible!=null)
			cible.beaten();
	}
	public void frapperSud(){
		Personnage cible = this.partie.occupe(pos.getX(),pos.getY()+1);	//verif au niveau de l'ordonanceur.
		if (cible!=null)
			cible.beaten();
	}
	public void frapperOuest(){
		Personnage cible = this.partie.occupe(pos.getX(),pos.getX()-1);	//verif au niveau de l'ordonanceur.
		if (cible!=null)
			cible.beaten();
	}
	*/
}
