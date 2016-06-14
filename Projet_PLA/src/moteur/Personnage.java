package moteur;

public abstract class Personnage {
	

	protected int sante=1;
	protected int etat; //TODO limite des états de l'automate.
	protected Automate automate;
	protected Position pos;
	protected Partie partie;
	protected Joueur equipe; //equipe du personnage
	
	Personnage(int e,Automate a,Position p){
		etat=e;
		automate=a;
		pos = p;
	}
	
	public void beaten(){
		sante=0;
	}
	
	public void setPartie(Partie p){
		this.partie=p;
	}
	
	public int etat(){
		return etat;
	}
	
	public Automate automate(){
		return automate;
	}
	
	public Position position(){
		return pos;
	}
	
	public void agir(int action){
		switch(action){
		case 1 : this.avancerNord();
		}
	}
	
	public void avancerNord(){
		pos.setY(pos.getY()-1);	//verif au niveau de l'ordonanceur.
	}
	public void avancerEst(){
		pos.setY(pos.getX()+1);	//verif au niveau de l'ordonanceur.
	}
	public void avancerSud(){
		pos.setY(pos.getY()+1);	//verif au niveau de l'ordonanceur.
	}
	public void avancerOuest(){
		pos.setY(pos.getX()-1);	//verif au niveau de l'ordonanceur.
	}
	
	/*
	 * @ensure i<= nombre max d'etat
	 */
	public void setEtat(int e){
		etat = e;
	}
	

}
