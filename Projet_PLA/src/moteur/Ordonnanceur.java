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
		List<ActionFutur> actions = new ArrayList<ActionFutur>();
		for(Personnage p : part.personnages()){
			conds = this.calculConditions(p);
			act = p.automate().action(p.etat(), conds);
			actions.add(new ActionFutur(p,act));
		}
		actions.sort(null);
		double roll;
		for(ActionFutur a : actions){
			for(ActionFutur b : actions){
				if(a.type()==TypeAction.FRAPPE&&b.type()==TypeAction.FRAPPE){
					if(a.cible()==b.perso().position()&&b.cible()==a.perso().position()){ //Si les guerriers s'attaque tout les deux
						roll = Math.random();
						if (roll<=0.33){
							a.setType(TypeAction.RATE);
						}else if(roll>=0.66){
							b.setType(TypeAction.RATE);
						} //si 0.33<roll<0.66 les guerrier s'entretue 
					}
				}
				if(a.type()==TypeAction.PEINT&&b.type()==TypeAction.PEINT){
					if(a.cible()==b.cible()){ //Si deux peintre peignent la même case
						if (!a.sameColor(b)){
							roll = Math.random();
							if (roll<=0.5){
								a.setType(TypeAction.RATE);
							}else{
								b.setType(TypeAction.RATE);
							}
						}
					}
				}
				if(a.type()==TypeAction.MOUVEMENT&&b.type()==TypeAction.MOUVEMENT){	
					if(a.cible()==b.cible()){ //Si deux personnages avance sur la même case.
						roll = Math.random();
						if (roll<=0.50){
							a.setType(TypeAction.RATE);
						}else{
							b.setType(TypeAction.RATE);
						}
					}
				}
			}
		}
		for(ActionFutur a : actions){
			a.executer();
		}
	}
	
	//Calcul les conditions a tester pour un personnages
	//TODO
	
	public List<Integer> calculConditions(Personnage p){
		List<Integer> conditions = new ArrayList<Integer>();
		
		/*
		 * if (p.position().getY()!=0){
		 * conditions.add(11);
		 * }
		*/
		
		//Personnage au nord
		if (p.partie().occupe(p.position().getX(), p.position().getY()-1)!=null){
			
			//Allie
			if(p.partie().occupe(p.position().getX(), p.position().getY()-1).equipe()==p.equipe()){
				conditions.add(8);
			}
			else 
			{
				conditions.add(5);
			}
		}
		
		//Personnage a l'ouest
		if (p.partie().occupe(p.position().getX()+1, p.position().getY())!=null){
			
			//Allie
			if(p.partie().occupe(p.position().getX()+1, p.position().getY()).equipe()==p.equipe()){
				conditions.add(11);
			}
			else 
			{
				conditions.add(8);
			}
		}
		
		//Personnage a l'est
		if (p.partie().occupe(p.position().getX()-1, p.position().getY())!=null){
			
			//Allie
			if(p.partie().occupe(p.position().getX()-1, p.position().getY()).equipe()==p.equipe()){
				conditions.add(9);
			}
			else 
			{
				conditions.add(6);
			}
		}
		
		//Personnage au sud
		if (p.partie().occupe(p.position().getX(), p.position().getY()+1)!=null){
			
			//Allie
			if(p.partie().occupe(p.position().getX(), p.position().getY()+1).equipe()==p.equipe()){
				conditions.add(10);
			}
			else 
			{
				conditions.add(7);
			}
		}
		
		return conditions;
	}
	
}
