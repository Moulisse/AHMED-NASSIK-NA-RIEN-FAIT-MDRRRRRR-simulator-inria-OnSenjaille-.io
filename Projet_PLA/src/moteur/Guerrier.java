package moteur;


public class Guerrier extends Personnage {

	public Guerrier(int e,Automate a, Position p){
		super(e,a, p);
	}
	
	public void frapper(int code){
		Personnage cible=null;	//verif au niveau de l'ordonanceur.

		switch (code){
		case 0 :	//Nord
			cible = this.partie.occupe(pos.getX(),pos.getY()-1);
			break;
		case 1 : 	//Est
			cible = this.partie.occupe(pos.getX()+1,pos.getY());
			break;
		case 2 : 	//Sud
			cible = this.partie.occupe(pos.getX(),pos.getY()+1);
			break;
		case 3 : 	//Ouest
			cible = this.partie.occupe(pos.getX()-1,pos.getY());
			break;
		default :
			cible = null;
			break;
		}
		if (cible != null)
			cible.beaten();
	}
}
