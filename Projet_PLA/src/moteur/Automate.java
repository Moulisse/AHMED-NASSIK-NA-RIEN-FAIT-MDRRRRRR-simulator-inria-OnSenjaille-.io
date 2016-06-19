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
				return transitions[i][etat+1];
		}
		return 0; //symbole non trouvé
	}
	
	//prend en entée l'état actuel et le symbole et renvoie le nouvelle état;
		public ActionFutur action(Personnage perso,List<Integer> symboles){
			List<ActionFutur> actionsPossibles = new ArrayList<ActionFutur>();
			int i;
			
			this.afficherActions();
			
			//i<position.getY()+ hauteur de l'automate d'action (nombre de conditions). 
			for(i=position.getY();i<position.getY()+this.transitions.length;i++){
			//System.out.println("position.getY()+i "+(position.getY()+i)+"position.getX()+etat+1 "+(position.getX()+etat+1));
				//System.out.println("[position.getY()+i] "+(position.getY()+i)+"[position.getX()] "+ (position.getX()));
				if (symboles.contains(partie.decor()[i][position.getX()].valeur())){
					
					if (partie.decor()[i][position.getX()+perso.etat+1].valeur()!=0){
						actionsPossibles.add(new ActionFutur(perso,
								partie.decor()[i][position.getX()+perso.etat+1].valeur(),
								transition(perso.etat(), partie.decor()[i][position.getX()].valeur())));
					}
				}
			}
			if (actionsPossibles.isEmpty())
				return new ActionFutur(perso,codes.Attendre,perso.etat());
			else{
				int roll = (int)(Math.random()*(actionsPossibles.size()));
				return actionsPossibles.get(roll);
			}
		}
		
		public void afficherTransitions(){
			for(int j=0;j<transitions.length;j++){
				for(int k=0;k<transitions[j].length;k++){
					System.out.print(transitions[j][k]+" ");
				}
				System.out.print("\n");
			}
		}
		
		public void afficherActions(){
			for(int i=this.position.getX();i<transitions.length;i++){
				for(int j=this.position.getY();j<transitions[i].length;j++){
					System.out.print(this.partie.decor()[i][j].valeur()+" | ");
				}
				System.out.print("\n");
			}
		}
		
		public int[][] getTransitions(){
			return this.transitions;
		}
		
		
		
	
}