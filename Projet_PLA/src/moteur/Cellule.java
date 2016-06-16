package moteur;

public class Cellule {

	private int valeur;
	private int couleur;
	
	public Cellule(){
		valeur = 0;
		couleur = 0;
	}
	
	public Cellule(int i,int c){
		valeur = 1;
		couleur = c;
	}
	
	public Cellule(int i){
		valeur = 1;
	}
	
	public int valeur(){
		return valeur;
	}
	
	public void setValeur(int v){
		this.valeur=v;
	}
	
	public int couleur(){
		return couleur;
	}
	
	public void peindre(int c){
		couleur = c;
		if(valeur==codes.rouge){
			if(this.valeur>=codes.peindreB&&this.valeur<=codes.peindreB+4){
				this.valeur=this.valeur-codes.peindreB+codes.peindreR;
			}

			else if(valeur>=codes.caseBlancheEloigneeNord&&valeur<=codes.caseBlancheEloigneeOuest){
				this.valeur=this.valeur - codes.caseBlancheEloigneeNord + codes.caseRougeEloigneeNord;
			}
			
			else if(valeur>=codes.caseBlancheNord&&valeur<=codes.caseBlancheCentre){
				this.valeur=this.valeur - codes.caseBlancheNord + codes.caseRougeNord;
			}
			
			else if(valeur>=codes.caseBleuEloigneeNord&&valeur<=codes.caseBleuEloigneeOuest){
				this.valeur=this.valeur - codes.caseBleuEloigneeNord + codes.caseRougeEloigneeNord;
			}
			
			else if(valeur>=codes.caseBleuNord&&valeur<=codes.caseBleuOuest){
				this.valeur=this.valeur - codes.caseBleuNord + codes.caseRougeNord;
			}	
		
		}
		else if(valeur==codes.bleu){
			if(this.valeur>=codes.peindreR&&this.valeur<=codes.peindreR+4)
				this.valeur=this.valeur-codes.peindreR+codes.peindreB;
			
			else if(valeur>=codes.caseBlancheEloigneeNord&&valeur<=codes.caseBlancheEloigneeOuest){
				this.valeur=this.valeur - codes.caseBlancheEloigneeNord + codes.caseBleuEloigneeNord;
			}
			
			else if(valeur>=codes.caseBlancheNord&&valeur<=codes.caseBlancheCentre){
				this.valeur=this.valeur - codes.caseBlancheNord + codes.caseBleuNord;
			}
			
			else if(valeur>=codes.caseRougeEloigneeNord&&valeur<=codes.caseRougeEloigneeOuest){
				this.valeur=this.valeur - codes.caseRougeEloigneeNord + codes.caseBleuEloigneeNord;
			}
			
			else if(valeur>=codes.caseRougeNord&&valeur<=codes.caseRougeOuest){
				this.valeur=this.valeur - codes.caseRougeNord + codes.caseBleuNord;
			}	
		}
		
	}
	
}
