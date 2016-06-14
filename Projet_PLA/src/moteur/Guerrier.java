package moteur;


public class Guerrier extends Personnage {

	Guerrier(int e,Automate a, Position p){
		super(e,a, p);
	}
	
	public void frapperNord(){
		Personnage cible = this.partie.occupe(pos.getX(),pos.getY()-1);	//verif au niveau de l'ordonanceur.
		cible
	}
	public void frapperEst(){
		pos.setY(pos.getX()+1);	//verif au niveau de l'ordonanceur.
	}
	public void frapperSud(){
		pos.setY(pos.getY()+1);	//verif au niveau de l'ordonanceur.
	}
	public void frapperOuest(){
		pos.setY(pos.getX()-1);	//verif au niveau de l'ordonanceur.
	}
	
}
