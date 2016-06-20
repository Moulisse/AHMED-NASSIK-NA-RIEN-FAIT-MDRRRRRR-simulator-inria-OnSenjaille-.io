package moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ordonnanceur {
	
	
	private Partie part;
	
	Ordonnanceur(Partie p){
		part=p;
	}
	
	public void tour(){
		List<Integer> conds;
		ActionFutur act;
		List<ActionFutur> actions = new ArrayList<ActionFutur>();
		for(Personnage p : part.personnages()){
			//System.out.println("LE PERSONNAGE EST EN POS : X= "+p.position().getX()+" Y="+p.position().getY());
			conds = this.calculConditions(p);
			act = p.automate().action(p, conds);
			actions.add(act);
		}
		Collections.sort(actions);
		double roll;
		for(ActionFutur a : actions){
			for(ActionFutur b : actions){
				if(!a.equals(b)){
					if(a.type().equals(TypeAction.FRAPPE)&&b.type().equals(TypeAction.FRAPPE)){
						if(a.cible().equals(b.perso().position())&&b.cible().equals(a.perso().position())){ //Si les guerriers s'attaque tout les deux
							roll = Math.random();
							if (roll<=0.33){
								a.setType(TypeAction.RATE);
							}else if(roll>=0.66){
								b.setType(TypeAction.RATE);
							} //si 0.33<roll<0.66 les guerrier s'entretue 
						}
					}
					if(a.type().equals(TypeAction.PEINT)&&b.type().equals(TypeAction.PEINT)){
						if(a.cible().equals(b.cible())){ //Si deux peintre peignent la même case
							if (!a.sameColor(b)){
								roll = Math.random();
								System.out.print("Roll : "+roll);
								if (roll<=0.5){
									a.setType(TypeAction.RATE);
								}else{
									b.setType(TypeAction.RATE);
								}
							}
						}
					}
					if(a.type().equals(TypeAction.MOUVEMENT)&&b.type().equals(TypeAction.MOUVEMENT)){	
						if(a.cible().equals(b.cible())){ //Si deux personnages avance sur la même case.
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
		int X=p.partie().decor()[0].length;
		int Y=p.partie().decor().length;
	
				
		Cellule c_NO=p.partie().decor()[p.position().getY()-1][p.position().getX()-1];
		Cellule c_NE=p.partie().decor()[p.position().getY()-1][p.position().getX()+1];
		Cellule c_SE=p.partie().decor()[p.position().getY()+1][p.position().getX()+1];
		Cellule c_SO =p.partie().decor()[p.position().getY()+1][p.position().getX()-1];

		Personnage p_NO=p.partie().occupe(p.position().getX()-1,p.position().getY()-1);
		Personnage p_NE=p.partie().occupe(p.position().getX()+1,p.position().getY()-1);
		Personnage p_SE=p.partie().occupe(p.position().getX()+1,p.position().getY()+1);
		Personnage p_SO=p.partie().occupe(p.position().getX()-1,p.position().getY()+1);

		Personnage p_N=p.partie().occupe(p.position().getX(),p.position().getY()-2);
		Personnage p_S=p.partie().occupe(p.position().getX(),p.position().getY()-2);
		Personnage p_E=p.partie().occupe(p.position().getX()+2,p.position().getY());
		Personnage p_O=p.partie().occupe(p.position().getX()-2,p.position().getY());

		Cellule c_N =p.partie().decor()[p.position().getY()-2][p.position().getX()];
		Cellule c_S =p.partie().decor()[p.position().getY()+2][p.position().getX()];
		Cellule c_E =p.partie().decor()[p.position().getY()][p.position().getX()+2];
		Cellule c_O =p.partie().decor()[p.position().getY()][p.position().getX()-2];


		boolean EnnemiN=false;
		boolean EnnemiE=false;
		boolean EnnemiS=false;
		boolean EnnemiO=false;
		
		boolean BlancEl_N=false;
		boolean BlancEl_E=false;
		boolean BlancEl_S=false;
		boolean BlancEl_O=false;
		
		boolean RougeEl_N=false;
		boolean RougeEl_E=false;
		boolean RougeEl_S=false;
		boolean RougeEl_O=false;
		
		boolean BleueEl_N=false;
		boolean BleueEl_E=false;
		boolean BleueEl_S=false;
		boolean BleueEl_O=false;

		
		if(p.position().getY()-1>=0)
		{
			//Personnage au nord
			if (p.partie().occupe(p.position().getX(), p.position().getY()-1)!=null){
				
				//Allie
				if(p.partie().occupe(p.position().getX(), p.position().getY()-1).equipe()==p.equipe()){
					conditions.add(codes.allieNord);
				}
				else //Ennemi 
				{
					conditions.add(codes.ennemieNord);
				}
			}
			
			//CaseBlanche(N)
			//System.out.println("acces decor["+(p.position().getX())+"]["+(p.position().getY()-1)+"]");
			if(p.partie().decor()[p.position().getY()-1][p.position().getX()].couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheNord);
			}
			else if(p.partie().decor()[p.position().getY()-1][p.position().getX()].couleur()==codes.bleu)
			//CaseBleu(N)
			{
				conditions.add(codes.caseBleuNord);
			}
			else if(p.partie().decor()[p.position().getY()-1][p.position().getX()].couleur()==codes.rouge)
			//CaseRouge(N)
			{
				conditions.add(codes.caseRougeNord);
			}
			//Mur(N)
			if(p.partie().decor()[p.position().getY()-1][p.position().getX()].valeur()==codes.mur)
			{
				conditions.add(codes.murNord);
			}

		}
		
		if(p.position().getX()-1<=X-1)
		{
			//Personnage a l'est
			if (p.partie().occupe(p.position().getX()+1, p.position().getY())!=null){
				
				//Allie
				if(p.partie().occupe(p.position().getX()+1, p.position().getY()).equipe()==p.equipe()){
					conditions.add(codes.allieEst);
				}
				else //Ennemi 
				{
					conditions.add(codes.ennemiEst);
				}
			}
			//CaseBlanche(E)
			if(p.partie().decor()[p.position().getY()][p.position().getX()+1].couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheEst);
			}
			else if(p.partie().decor()[p.position().getY()][p.position().getX()+1].couleur()==codes.bleu)
			//CaseBleu(E)	
			{
				conditions.add(codes.caseBleuEst);
			}
			else if (p.partie().decor()[p.position().getY()][p.position().getX()+1].couleur()==codes.rouge)
			//CaseRouge(E)
			{
				conditions.add(codes.caseRougeEst);
			}
			//Mur(E)
			if(p.partie().decor()[p.position().getY()][p.position().getX()+1].valeur()==codes.mur)
			{
				conditions.add(codes.murEst);
			}
		}
		
		if(p.position().getY()+1<=Y-1)
		{
			//Personnage au sud
			if (p.partie().occupe(p.position().getX(), p.position().getY()+1)!=null){
				
				//Allie
				if(p.partie().occupe(p.position().getX(), p.position().getY()+1).equipe()==p.equipe()){
					conditions.add(codes.allieSud);
				}
				else //Ennemi 
				{
					conditions.add(codes.ennemiSud);
				}
			}
			//CaseBlanche(S)
			if(p.partie().decor()[p.position().getY()+1][p.position().getX()].couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheSud);
			}
			else if(p.partie().decor()[p.position().getY()+1][p.position().getX()].couleur()==codes.bleu)
			//CaseBleu(S)
			{
				conditions.add(codes.caseBleuSud);
			}
			else if(p.partie().decor()[p.position().getY()+1][p.position().getX()].couleur()==codes.rouge)
			//CaseRouge(S)
			{
				conditions.add(codes.caseRougeSud);
			}
			//Mur(S)
			if(p.partie().decor()[p.position().getY()+1][p.position().getX()].valeur()==codes.mur)
			{
				conditions.add(codes.murSud);
			}
		}
		
		if(p.position().getX()-1>=0)
		{
			//Personnage a l'ouest
			if (p.partie().occupe(p.position().getX()-1, p.position().getY())!=null){
			
				//Allie
				if(p.partie().occupe(p.position().getX()-1, p.position().getY()).equipe()==p.equipe()){
					conditions.add(codes.allieOuest);
				}	
				else //Ennemi 
				{
					conditions.add(codes.ennemiOuest);
				}
			}
			//CaseBlanche(O)
			if(p.partie().decor()[p.position().getY()][p.position().getX()-1].couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheOuest);
			}
			else if(p.partie().decor()[p.position().getY()][p.position().getX()-1].couleur()==codes.bleu)
			//CaseBleu(O)
			{
				conditions.add(codes.caseBleuOuest);
			}
			else if(p.partie().decor()[p.position().getY()][p.position().getX()-1].couleur()==codes.rouge)
			//CaseRouge(O)
			{
				conditions.add(codes.caseRougeOuest);
			}
			//Mur(O)
			if(p.partie().decor()[p.position().getY()][p.position().getX()-1].valeur()==codes.mur)
			{
				conditions.add(codes.murOuest);
			}

		}
		//CaseBlanche(cellule actuelle)
		if(p.partie().decor()[p.position().getY()][p.position().getX()].couleur()==0)
		{
			conditions.add(codes.caseBlancheCentre);
		}
		else if(p.partie().decor()[p.position().getY()][p.position().getX()].couleur()==1)
		//CaseBleu(Cellule actuelle)	
		{
			conditions.add(codes.caseBleuCentre);
		}
		else
		{
			conditions.add(codes.caseRougeCentre);
		}
		
		
		//quelqun au NW
		if(p_NO!=null)
		{
			//ennemi au NW
			if (p_NO.equipe()!=p.equipe())
			{
				conditions.add(codes.ennemiEloigneNord);
				conditions.add(codes.ennemiEloigneOuest);
				EnnemiN=true;
				EnnemiO=true;
			}
			
		}
		
		if(p_SE!=null)
		{
			if (p_SE.equipe()!=p.equipe())
			{
				conditions.add(codes.ennemiEloigneSud);
				conditions.add(codes.ennemiEloigneEst);
				EnnemiS=true;
				EnnemiE=true;
			}
		}

		if(p_NE!=null)
		{
			if (p_NE.equipe()!=p.equipe())
			{
				if (!EnnemiN){
					conditions.add(codes.ennemiEloigneNord);
					EnnemiN=true;}
				
				if (!EnnemiE){
					conditions.add(codes.ennemiEloigneEst);
					EnnemiE=true;}
			}
		}
		
		if(p_SO!=null)
		{
			if (p_SO.equipe()!=p.equipe())
			{
				if (!EnnemiS){
					conditions.add(codes.ennemiEloigneSud);
					EnnemiS=true;}
				
				if (!EnnemiO){
					conditions.add(codes.ennemiEloigneOuest);
					EnnemiO=true;}
			}
		}
		
		if(!EnnemiN && p_N!=null)
		{
			if(p_N.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneNord);}
		}
		
		if(!EnnemiE && p_E!=null)
		{
			if(p_E.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneEst);}
		}
		
		if(!EnnemiS && p_S!=null)
		{
			if(p_S.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneSud);}
		}
		
		if(!EnnemiO && p_O!=null)
		{
			if(p_O.equipe()==p.equipe()){
				conditions.add(codes.ennemiEloigneOuest);}
		}
		
		//----------------------------------------------------
		if(c_NO!=null)
		{
			//ennemi au NW
			if (c_NO.couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheEloigneeNord);
				conditions.add(codes.caseBlancheEloigneeOuest);
				BlancEl_N=true;
				BlancEl_O=true;
			}
			else if(c_NO.couleur()==codes.bleu)
			{
				conditions.add(codes.caseBleuEloigneeNord);
				conditions.add(codes.caseBleuEloigneeOuest);
				BleueEl_N=true;
				BleueEl_O=true;
			}
			else if (c_NO.couleur()==codes.rouge)
			{
				conditions.add(codes.caseRougeEloigneeNord);
				conditions.add(codes.caseRougeEloigneeOuest);
				RougeEl_N=true;
				RougeEl_O=true;
			}
		}

			//ennemi au NW
			if (c_SE.couleur()==codes.blanc)
			{
				conditions.add(codes.caseBlancheEloigneeSud);
				conditions.add(codes.caseBlancheEloigneeEst);
				BlancEl_S=true;
				BlancEl_E=true;
			}
			else if(c_SE.couleur()==codes.bleu)
			{
				conditions.add(codes.caseBleuEloigneeSud);
				conditions.add(codes.caseBleuEloigneeEst);
				BleueEl_S=true;
				BleueEl_E=true;
			}
			else if (c_SE.couleur()==codes.rouge)
			{
				conditions.add(codes.caseRougeEloigneeSud);
				conditions.add(codes.caseRougeEloigneeEst);
				RougeEl_S=true;
				RougeEl_E=true;
			}
		


			if (c_NE.couleur()==codes.blanc)
			{
				if (!BlancEl_N){
					conditions.add(codes.caseBlancheEloigneeNord);
					BlancEl_N=true;}
				
				if (!BlancEl_E){
					conditions.add(codes.caseBlancheEloigneeEst);
					BlancEl_E=true;}
			}
			else if(c_NE.couleur()==codes.bleu)
			{
				if (!BleueEl_N){
					conditions.add(codes.caseBleuEloigneeNord);
					BleueEl_N=true;}
				
				if (!BleueEl_E){
					conditions.add(codes.caseBleuEloigneeEst);
					BleueEl_E=true;}
			}
			else if (c_NE.couleur()==codes.rouge)
			{
				if (!RougeEl_N){
					conditions.add(codes.caseRougeEloigneeNord);
					RougeEl_N=true;}
				
				if (!BleueEl_E){
					conditions.add(codes.caseRougeEloigneeEst);
					RougeEl_E=true;}
			}
		

			if (c_SO.couleur()==codes.blanc)
			{
				if (!BlancEl_S){
					conditions.add(codes.caseBlancheEloigneeSud);
					BlancEl_S=true;}
				
				if (!BlancEl_O){
					conditions.add(codes.caseBlancheEloigneeOuest);
					BlancEl_O=true;}
			}
			else if(c_SO.couleur()==codes.bleu)
			{
				if (!BleueEl_S){
					conditions.add(codes.caseBleuEloigneeSud);
					BleueEl_S=true;}
				
				if (!BleueEl_O){
					conditions.add(codes.caseBleuEloigneeOuest);
					BleueEl_O=true;}
			}
			else
			{
				if (!RougeEl_S){
					conditions.add(codes.caseRougeEloigneeSud);
					RougeEl_S=true;}
				
				if (!BleueEl_O){
					conditions.add(codes.caseRougeEloigneeOuest);
					RougeEl_O=true;}
			}
		

			if(c_N.couleur()==codes.blanc && !BlancEl_N){
				conditions.add(codes.caseBlancheEloigneeNord);}
			else if (c_N.couleur()==codes.bleu && !BleueEl_N)
			{
				conditions.add(codes.caseBleuEloigneeNord);}
			else if(c_N.couleur()==codes.rouge && !RougeEl_N)
			{
				conditions.add(codes.caseRougeEloigneeNord);
			}

			if(c_E.couleur()==codes.blanc && !BlancEl_E){
				conditions.add(codes.caseBlancheEloigneeEst);}
			else if (c_E.couleur()==codes.bleu && !BleueEl_E)
			{
				conditions.add(codes.caseBleuEloigneeNord);}
			else if(c_E.couleur()==codes.rouge && !RougeEl_E)
			{
				conditions.add(codes.caseRougeEloigneeEst);
			}
		
	
			if(c_S.couleur()==codes.blanc && !BlancEl_N){
				conditions.add(codes.caseBlancheEloigneeSud);}
			else if (c_S.couleur()==codes.bleu && !BleueEl_S)
			{
				conditions.add(codes.caseBleuEloigneeSud);}
			else if(c_S.couleur()==codes.rouge && !RougeEl_S)
			{
				conditions.add(codes.caseRougeEloigneeSud);
			}
		
		

			if(c_O.couleur()==codes.blanc && !BlancEl_O){
				conditions.add(codes.caseBlancheEloigneeOuest);}
			else if (c_O.couleur()==codes.bleu && !BleueEl_O)
			{
				conditions.add(codes.caseBleuEloigneeOuest);}
			else if(c_O.couleur()==codes.rouge && !RougeEl_O)
			{
				conditions.add(codes.caseRougeEloigneeOuest);
			}
		
		
			return conditions;
		}
}
