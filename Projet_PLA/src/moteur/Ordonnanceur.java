package moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordonnanceur {
	
	final int Code_Mur=65;
	
	//code conditions
	
	//Ennemi eloigne
	final int EnnemiEl_N=1;
	final int EnnemiEl_E=2;
	final int EnnemiEl_S=3;
	final int EnnemiEl_O=4;
	
	//Ennemi (proche)
	final int Ennemi_N=5;
	final int Ennemi_E=6;
	final int Ennemi_S=7;
	final int Ennemi_O=8;
	
	//Allie (proche)
	final int Allie_N=9;
	final int Allie_E=10;
	final int Allie_S=11;
	final int Allie_O=12;
	
	//Case eloignee blanche
	final int CaseElW_N=13;
	final int CaseElW_E=14;
	final int CaseElW_S=15;
	final int CaseElW_O=16;
	
	//Case eloignee rouge
	final int CaseElR_N=17;
	final int CaseElR_E=18;
	final int CaseElR_S=19;
	final int CaseElR_O=20;
	
	//Case eloignee bleue
	final int CaseElB_N=21;
	final int CaseElB_E=22;
	final int CaseElB_S=23;
	final int CaseElB_O=24;
	
	//Case blanche (proche)
	final int CaseW_N=25;
	final int CaseW_E=26;
	final int CaseW_S=27;
	final int CaseW_O=28;
	final int CaseW_C=29;
	
	//Case rouge (proche) 
	final int CaseR_N=30;
	final int CaseR_E=31;
	final int CaseR_S=32;
	final int CaseR_O=33;
	final int CaseR_C=34;
	
	//Case bleue (proche) 
	final int CaseB_N=35;
	final int CaseB_E=36;
	final int CaseB_S=37;
	final int CaseB_O=38;
	final int CaseB_C=39;

	//Mur (proche)
	final int Mur_N=40;
	final int Mur_E=41;
	final int Mur_S=42;
	final int Mur_O=43;
	
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
		Collections.sort(actions);
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
							a.setCible(4); //Avancer sur place.
						}else{
							b.setCible(4);
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
				conditions.add(Allie_N);
			}
			else //Ennemi 
			{
				conditions.add(Ennemi_N);
			}
		}
		
		//Personnage a l'est
		if (p.partie().occupe(p.position().getX()-1, p.position().getY())!=null){
			
			//Allie
			if(p.partie().occupe(p.position().getX()+1, p.position().getY()).equipe()==p.equipe()){
				conditions.add(Allie_E);
			}
			else //Ennemi 
			{
				conditions.add(Ennemi_E);
			}
		}
		
		//Personnage au sud
		if (p.partie().occupe(p.position().getX(), p.position().getY()+1)!=null){
			
			//Allie
			if(p.partie().occupe(p.position().getX(), p.position().getY()+1).equipe()==p.equipe()){
				conditions.add(Allie_S);
			}
			else //Ennemi 
			{
				conditions.add(Ennemi_S);
			}
		}
		
		//Personnage a l'ouest
		if (p.partie().occupe(p.position().getX()+1, p.position().getY())!=null){
		
			//Allie
			if(p.partie().occupe(p.position().getX()-1, p.position().getY()).equipe()==p.equipe()){
				conditions.add(Allie_O);
			}	
			else //Ennemi 
			{
				conditions.add(Ennemi_O);
			}
		}
		
		//CaseBlanche(N)
		if(p.partie().decor()[p.position().getX()][p.position().getY()-1].couleur()==0)
		{
			conditions.add(CaseW_N);
		}
		else if(p.partie().decor()[p.position().getX()][p.position().getY()-1].couleur()==1)
		//CaseBleu(N)
		{
			conditions.add(CaseB_N);
		}
		else
		//CaseRouge(N)
		{
			conditions.add(CaseR_N);
		}
		
		//CaseBlanche(E)
		if(p.partie().decor()[p.position().getX()+1][p.position().getY()].couleur()==0)
		{
			conditions.add(CaseW_E);
		}
		else if(p.partie().decor()[p.position().getX()+1][p.position().getY()].couleur()==1)
		//CaseBleu(E)	
		{
			conditions.add(CaseB_E);
		}
		else
		//CaseRouge(E)
		{
			conditions.add(CaseR_E);
		}
			
		
		//CaseBlanche(S)
		if(p.partie().decor()[p.position().getX()][p.position().getY()+1].couleur()==0)
		{
			conditions.add(CaseW_S);
		}
		else if(p.partie().decor()[p.position().getX()][p.position().getY()+1].couleur()==1)
		//CaseBleu(S)
		{
			conditions.add(CaseB_S);
		}
		else
		//CaseRouge(S)
		{
			conditions.add(CaseR_S);
		}
		
		//CaseBlanche(O)
		if(p.partie().decor()[p.position().getX()-1][p.position().getY()].couleur()==0)
		{
			conditions.add(CaseW_O);
		}
		else if(p.partie().decor()[p.position().getX()-1][p.position().getY()].couleur()==1)
		//CaseBleu(O)
		{
			conditions.add(CaseB_O);
		}
		else
		//CaseRouge(O)
		{
			conditions.add(CaseR_O);
		}
		
		//CaseBlanche(cellule actuelle)
		if(p.partie().decor()[p.position().getX()][p.position().getY()].couleur()==0)
		{
			conditions.add(CaseW_C);
		}
		else if(p.partie().decor()[p.position().getX()][p.position().getY()].couleur()==1)
		//CaseBleu(Cellule actuelle)	
		{
			conditions.add(CaseB_C);
		}
		else
		{
			conditions.add(CaseR_C);
		}
		
		
		//Mur(N)
		if(p.partie().decor()[p.position().getX()][p.position().getY()-1].valeur()==Code_Mur)
		{
			conditions.add(Mur_N);
		}
	
		//Mur(E)
		if(p.partie().decor()[p.position().getX()+1][p.position().getY()].valeur()==Code_Mur)
		{
			conditions.add(Mur_E);
		}
			
		//Mur(S)
		if(p.partie().decor()[p.position().getX()][p.position().getY()+1].valeur()==Code_Mur)
		{
			conditions.add(Mur_S);
		}
		
		//Mur(O)
		if(p.partie().decor()[p.position().getX()-1][p.position().getY()].valeur()==Code_Mur)
		{
			conditions.add(Mur_O);
		}
		
		Personnage p_NO=p.partie().occupe(p.position().getX()-1,p.position().getY()-1);
		Personnage p_NE=p.partie().occupe(p.position().getX()+1,p.position().getY()-1);
		Personnage p_SE=p.partie().occupe(p.position().getX()+1,p.position().getY()+1);
		Personnage p_SO=p.partie().occupe(p.position().getX()-1,p.position().getY()+1);

		Personnage p_N=p.partie().occupe(p.position().getX(),p.position().getY()-2);
		Personnage p_E=p.partie().occupe(p.position().getX()+2,p.position().getY());
		Personnage p_S=p.partie().occupe(p.position().getX(),p.position().getY()-2);
		Personnage p_O=p.partie().occupe(p.position().getX()-2,p.position().getY());
				
		boolean EnnemiN=false;
		boolean EnnemiE=false;
		boolean EnnemiS=false;
		boolean EnnemiO=false;
		
		//quelqun au NW
		if(p_NO!=null)
		{
			//ennemi au NW
			if (p_NO.equipe()!=p.equipe())
			{
				conditions.add(EnnemiEl_N);
				conditions.add(EnnemiEl_O);
				EnnemiN=true;
				EnnemiO=true;
			}
			
		}
		
		if(p_SE!=null)
		{
			if (p_SE.equipe()!=p.equipe())
			{
				conditions.add(EnnemiEl_S);
				conditions.add(EnnemiEl_E);
				EnnemiS=true;
				EnnemiE=true;
			}
		}

		if(p_NE!=null)
		{
			if (p_NE.equipe()!=p.equipe())
			{
				if (!EnnemiN){
					conditions.add(EnnemiEl_N);
					EnnemiN=true;}
				
				if (!EnnemiE){
					conditions.add(EnnemiEl_E);
					EnnemiE=true;}
			}
		}
		
		if(p_SO!=null)
		{
			if (p_SO.equipe()!=p.equipe())
			{
				if (!EnnemiS){
					conditions.add(EnnemiEl_S);
					EnnemiS=true;}
				
				if (!EnnemiO){
					conditions.add(EnnemiEl_O);
					EnnemiO=true;}
			}
		}
		
		if(!EnnemiN && p_N!=null)
		{
			if(p_N.equipe()==p.equipe()){
				conditions.add(EnnemiEl_N);}
		}
		
		if(!EnnemiE && p_E!=null)
		{
			if(p_E.equipe()==p.equipe()){
				conditions.add(EnnemiEl_E);}
		}
		
		if(!EnnemiS && p_S!=null)
		{
			if(p_S.equipe()==p.equipe()){
				conditions.add(EnnemiEl_S);}
		}
		
		if(!EnnemiO && p_O!=null)
		{
			if(p_O.equipe()==p.equipe()){
				conditions.add(EnnemiEl_O);}
		}
		
		Cellule c_N =p.partie().decor()[p.position().getX()][p.position().getY()-2];
		Cellule c_E =p.partie().decor()[p.position().getX()+2][p.position().getY()];
		Cellule c_S =p.partie().decor()[p.position().getX()][p.position().getY()+2];
		Cellule c_O =p.partie().decor()[p.position().getX()-2][p.position().getY()];

		Cellule c_NO =p.partie().decor()[p.position().getX()-1][p.position().getY()-1];
		Cellule c_NE =p.partie().decor()[p.position().getX()+1][p.position().getY()-1];
		Cellule c_SE =p.partie().decor()[p.position().getX()+1][p.position().getY()+1];
		Cellule c_SO =p.partie().decor()[p.position().getX()-1][p.position().getY()+1];

		boolean blancheN=false;
		boolean blancheE=false;
		boolean blancheS=false;
		boolean blancheO=false;
		
		boolean bleueN=false;
		boolean bleueE=false;
		boolean bleueS=false;
		boolean bleueO=false;

		boolean rougeN=false;
		boolean rougeE=false;
		boolean rougeS=false;
		boolean rougeO=false;

		
		return conditions;
	}
	
}
