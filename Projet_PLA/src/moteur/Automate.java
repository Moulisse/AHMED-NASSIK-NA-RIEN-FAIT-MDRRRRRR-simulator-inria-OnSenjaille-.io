package moteur;


public class Automate {
	
	private int transitions[][]; //TODO restrictions
	private Position position; //coordonnées de l'automate sur le décor de la partie.
	private Partie partie; //TODO ensure (actionX,actionY)>partie.decor()limite du décor 
	//TODO HELP AHMED
	Automate(){};
	
	//provisoire
	Automate(int t[][],Position pos,Partie p){
		transitions=t;
		position=pos;
		partie=p;
	}
	
	//prend en entée l'état actuel et le symbole et renvoie le nouvelle état;
	public int transition(int etat,int symbole){
		for(int i=0;i<transitions.length;i++){
			if (transitions[i][0]==symbole) 
				return transitions[i][etat];
		}
		return 0; //symbole non trouvé
	}
	
	//prend en entée l'état actuel et le symbole et renvoie le nouvelle état;
		public int action(int etat,int symbole){
			int i;
			for(i=0;i<position.getY();i++){
				if (partie.decor()[position.getY()+i][position.getX()].valeur()==symbole) 
				return partie.decor()[position.getY()+i][position.getX()].valeur();
			}
			return 0; //symbole non trouvé
		}
	
}