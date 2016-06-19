package moteur;

import java.util.Iterator;
import java.util.List;

//import org.jdom2.Element;

import moteur.Automate;
import moteur.Personnage;

public abstract class Personnage {
	

	protected int sante=1;
	protected int etat; //TODO limite des états de l'automate.
	protected Automate automate;
	protected Position pos;
	protected Partie partie;
	protected Joueur equipe; //equipe du personnage
	
	public Personnage(int e,Automate a,Position p){
		etat=e;
		automate=a;
		pos = p;
	}
	
	//!
	public void setEquipe(Joueur j){
		this.equipe=j;
	}
	
	public void beaten(){
		sante=0;
		partie.personnages().remove(this);
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
	
	public void suivreBalise(){
		int diffX = this.equipe.balise().position().getX()-this.pos.getX();
		int diffY = this.equipe.balise().position().getY()-this.pos.getY();
		double roll;
		
		if (diffX==0&&diffY<0){ //Balise au Nord
			this.avancer(0);
		} else if(diffX>0&&diffY<0){ //Balise au Nord-Est
			if(diffX>(-diffY)){
				this.avancer(1);
			}else if(diffX<((-diffY))){
				this.avancer(0);
			}else{
				roll=Math.random();
				if(roll>0.5){
					this.avancer(1);
				}else{
					this.avancer(0);
				}
			}
		} else if(diffX>0&&diffY==0){ //Balise a l'Est
			this.avancer(1);
		} else if(diffX>0&&diffY>0){ //Balise au Sud-Est
			if(diffX>(diffY)){
				this.avancer(1);
			}else if(diffX<(diffY)){
				this.avancer(2);
			}else{
				roll=Math.random();
				if(roll>0.5){
					this.avancer(1);
				}else{
					this.avancer(2);
				}
			}
		} else if(diffX==0&&diffY>0){ //Balise au Sud
			this.avancer(2);
		} else if(diffX<0&&diffY>0){ //Balise au Sud-Ouest
			if((-diffX)>diffY){
				this.avancer(1);
			}else if((-diffX)<diffY){
				this.avancer(0);
			}else{
				roll=Math.random();
				if(roll>0.5){
					this.avancer(3);
				}else{
					this.avancer(2);
				}
			}
		} else if(diffX<0&&diffY==0){ //Balise a l'Ouest
			this.avancer(3);
		} else if(diffX<0&&diffY<0){ //Balise au Nord-Ouest
			if((-diffX)>(-diffY)){
				this.avancer(3);
			}else if((-diffX)<(-diffY)){
				this.avancer(0);
			}else{
				roll=Math.random();
				if(roll>0.5){
					this.avancer(3);
				}else{
					this.avancer(0);
				}
			}
		} else if(diffX==0&&diffY==0){
			avancer(4);
		}
	}
	
	public void avancer(int code){
		switch (code){
		case 0 :	//Nord
			if(!this.partie().blocke(this.position().getX(),this.position().getY()-1)&&
					(this.partie().occupe(this.position().getX(), this.position().getY()-1)==null))
				pos.setY(position().getY()-1);
			break;
		case 1 : 	//Est
			if(!this.partie().blocke(this.position().getX()+1,this.position().getY())&&
					(this.partie().occupe(this.position().getX()+1, this.position().getY())==null))
				pos.setX(position().getX()+1);
			break;
		case 2 : 	//Sud
			if(!this.partie().blocke(this.position().getX(),this.position().getY()+1)&&
					(this.partie().occupe(this.position().getX(), this.position().getY()+1)==null))
				pos.setY(position().getY()+1);
			break;
		case 3 : 	//Ouest
			if(!this.partie().blocke(this.position().getX()-1,this.position().getY())&&
					(this.partie().occupe(this.position().getX()-1, this.position().getY())==null))
				pos.setX(position().getX()-1);
			break;
		default : 	//Sur Place
			break;
		}
	}
	
	public void rate(){
		
	}
	
	/*
	 * @ensure i<= nombre max d'etat
	 */
	public void setEtat(int e){
		etat = e;
	}
	
	public void updateEtat(int symbole){
		this.etat=this.automate.transition(etat, symbole);
	}
	/*
	public void setAutomateTransition(int t[][])
	{
		this.automate.transitions=t;
	}
	}*/

	//renvoie true s'il le personnage possède une action interdites
	public boolean actionIntedite(List<Integer> actionsInterdites){
		int code;
		
		int hauteurTransi=this.automate().getTransitions().length;

		/*List<Integer> actionsPersonnage=null;

		//on récupère la premiere colonne(conditions)
		for(int i=0;i<hauteurTransi;i++)
		{
			actionsPersonnage.add(this.automate().getTransitions()[i][0]);
		}*/
		
		
		
		Iterator<Integer> it=actionsInterdites.iterator();
		while(it.hasNext())
		{
		code= (int)it.next();
		
		for(int i=0;i<hauteurTransi;i++)
		{
			if(this.automate().getTransitions()[i][0]==code){
				System.out.println("AUTOMATE ERRONE ACTION DE VOTRE PERSONNAGE DE TYPE "+this.getClass()+" CONTIENT UNE ACTION NON AUTORISEE");
				System.exit(0);
				return true;
				}
		}
		

		
		}	
	return false;
	
	}
	
	public void stringPosition()
	{
		System.out.println("Position du perso : Y "+this.position().getY()+ "X : "+this.position().getX());

	}
	
	
	
	
}
