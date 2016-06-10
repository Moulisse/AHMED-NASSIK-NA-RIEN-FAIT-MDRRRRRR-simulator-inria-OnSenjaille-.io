package moteur;

import java.util.ArrayList;
import java.util.List;

public class Ordonnanceur {
	
	private Partie part;
	
	Ordonnanceur(Partie p){
		part=p;
	}
	
	public void tour(){
		List<Integer> conds;
		int act;
		for(Personnage p : part.personnages()){
			conds = this.calculConditions(p);
			act = p.automate().action(p.etat(), conds);
			p.agir(act);
		}
	}
	
	//Calcul les conditions a tester pour un personnages
	//TODO
	
	public List<Integer> calculConditions(Personnage p){
		List<Integer> conditions = new ArrayList<Integer>();
		if (p.position().getY()!=0){
			conditions.add(11);
		}
		return conditions;
	}
	
}
