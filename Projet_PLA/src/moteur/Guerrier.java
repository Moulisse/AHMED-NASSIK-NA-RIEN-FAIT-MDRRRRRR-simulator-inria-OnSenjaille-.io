package moteur;


public class Guerrier extends Personnage {

	Guerrier(int e,Automate a, Position p){
		super(e,a, p);
	}
	
	public void frapperNord(){
		Personnage cible = this.partie.occupe(pos.getX(),pos.getY()-1);	//verif au niveau de l'ordonanceur.
		if (cible!=null){
			if(this.sante!=0){
				cible.beaten();
			}
			else{	//Bataille
				if(Math.random()<(1/3)){
					
				}else if()
			}
		}
			
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
	
}
