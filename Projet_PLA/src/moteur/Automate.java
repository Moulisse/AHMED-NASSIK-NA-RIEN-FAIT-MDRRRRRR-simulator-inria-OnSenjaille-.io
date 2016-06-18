package moteur;

import java.util.ArrayList;
import java.util.List;


public class Automate {
	
	private int transitions[][]; //TODO restrictions
	private Position position; //coordonnées de l'automate d'action sur le décor de la partie.
	private Partie partie; //TODO ensure (actionX,actionY)>partie.decor()limite du décor 
	//TODO HELP AHMED
	Automate(){};
	
	//provisoire
	public Automate(int t[][],Position pos,Partie p){
		transitions=t;
		position=pos;
		partie=p;
	}
	
	//PREMIERE COLONNE EST LES CONDITIONS
	//prend en entée l'état actuel et le symbole et renvoie le nouvelle état;
	public int transition(int etat,int symbole){
		for(int i=0;i<transitions.length;i++){
			if (transitions[i][0]==symbole) 
				return transitions[i][etat];
		}
		return 0; //symbole non trouvé
	}
	
	//prend en entée l'état actuel et le symbole et renvoie le nouvelle état;
		public int action(int etat,List<Integer> symboles){
			List<Integer> actionsPossibles = new ArrayList<Integer>();
			int i;
			//i<position.getY()+ hauteur de l'automate d'action (nombre de conditions). 
			for(i=position.getY();i<position.getY()+this.transitions.length;i++){
			//System.out.println("position.getY()+i "+(position.getY()+i)+"position.getX()+etat+1 "+(position.getX()+etat+1));
				System.out.println("[position.getY()+i] "+(position.getY()+i)+"[position.getX()] "+ (position.getX()));
				if (symboles.contains(partie.decor()[i][position.getX()].valeur()))
				
				actionsPossibles.add(partie.decor()[i][position.getX()+etat+1].valeur());
			}
			if (actionsPossibles.isEmpty())
				return 0;
			else{
				int roll = (int)(Math.random()*(actionsPossibles.size()));
				return actionsPossibles.get(roll);
			}
		}
		
		
		
		public int[][] getTransitions(){
			return this.transitions;
		}
		
		
		
	
}