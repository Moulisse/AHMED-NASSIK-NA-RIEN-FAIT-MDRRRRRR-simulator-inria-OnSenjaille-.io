package moteur;

public class ActionFutur implements Comparable<ActionFutur>{
	
	private TypeAction type;
	private Personnage perso;
	private int codeAction;
	private Position cible;
	
	ActionFutur(Personnage p,int i){
		perso = p;
		cible = new Position();
		if(i>=codes.avancer&&i<=codes.avancer+3){
			type = TypeAction.MOUVEMENT;
			codeAction = i-codes.avancer;
			setCible(codeAction); //Code de l'action - premier code = direction cf.codes.odt
		}else if(codeAction>=codes.frapper&&codeAction<=codes.frapper+3){
			type = TypeAction.FRAPPE;
			codeAction = i-codes.frapper;
			setCible(codeAction); 
		}else if(codeAction>=codes.peindreR&&codeAction<=codes.peindreB+4){
			type = TypeAction.PEINT;
			codeAction = i-codes.peindreR;
			setCible((codeAction)%5); //%5 dans le cas codeAction>=54
		}else {
			type = TypeAction.RATE; //bug code inconnue.
		}
	}
	
	public Position cible(){
		return cible;
	}
	
	public Personnage perso(){
		return perso;
	}
	
	public void executer(){
		switch (type){
		case MOUVEMENT :
			if (!this.perso.partie().blocke(this.cible().getX(), this.cible().getY())){
				this.perso.avancer(codeAction);
			}else{
				this.perso.avancer(4);
			}
		case RATE :
			this.perso().rate();
		case FRAPPE :
			if(this.getClass().isInstance(Guerrier.class)){
				((Guerrier)this.perso).frapper(codeAction);
			}
		case PEINT:
			if(this.getClass().isInstance(Peintre.class)){
				if(codeAction<5){
					((Peintre)this.perso).peindre(codeAction, codes.bleu );
				}else{
					((Peintre)this.perso).peindre(codeAction-5, codes.rouge );
				}
			}
			break;
		default:
			break;
		}
	}
	
	public void setType(TypeAction t){
		type = t;
	}
	
	public TypeAction type(){
		return type;
	}

	public int codeAction(){
		return codeAction;
	}
	
	public boolean sameColor(ActionFutur a){
		int diff = this.codeAction - a.codeAction();
		return (this.type()==a.type()&&a.type()==TypeAction.PEINT)&&(diff<5&&diff>-5);
	}
	
	//TODO généraliser dans Position.Java
	public void setCible(int i){
		switch (i){
		case 0 :	//Nord
			cible.setX(perso.position().getX());
			cible.setY(perso.position().getY()-1);
		case 1 : 	//Est
			cible.setX(perso.position().getX()+1);
			cible.setY(perso.position().getY());
		case 2 : 	//Sud
			cible.setX(perso.position().getX());
			cible.setY(perso.position().getY()+1);
		case 3 : 	//Ouest
			cible.setX(perso.position().getX()-1);
			cible.setY(perso.position().getY());
		case 4 : 	//Sur Place
			cible.setX(perso.position().getX());
			cible.setY(perso.position().getY());
		}
	}
	public int compareTo(ActionFutur arg0) {
		return this.type().ordinal() - ((ActionFutur)arg0).type().ordinal();
	}
	
	
}
