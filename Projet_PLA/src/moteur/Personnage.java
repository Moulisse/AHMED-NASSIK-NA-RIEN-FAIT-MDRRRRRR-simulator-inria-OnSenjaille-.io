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
	
	public Partie partie(){
		return partie;
	}
	
	public Joueur equipe(){
		return equipe;
	}
	
	public void avancer(int code){
		switch (code){
		case 0 :	//Nord
			pos.setY(position().getY()-1);
		case 1 : 	//Est
			pos.setX(position().getX()+1);
		case 2 : 	//Sud
			pos.setY(position().getY()+1);
		case 3 : 	//Ouest
			pos.setX(position().getX()-1);
		case 4 : 	//Sur Place
		}
	}
	
	/*
	 * @ensure i<= nombre max d'etat
	 */
	public void setEtat(int e){
		etat = e;
	}
	/*
	public void setAutomateTransition(int t[][])
	{
		this.automate.transitions=t;
	}
	}*/
	

}
