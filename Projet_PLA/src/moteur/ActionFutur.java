package moteur;

public class ActionFutur implements Comparable{
	
	private TypeAction type;
	private Personnage perso;
	private int codeAction;
	private Position cible;
	
	ActionFutur(Personnage p,int i){
		perso = p;
		if(i>=41&&i<=44){
			type = TypeAction.MOUVEMENT;
			codeAction = i-41;
			setCible(codeAction); //Code de l'action - premier code = direction cf.codes.odt
		}else if(codeAction>=45&&codeAction<=48){
			type = TypeAction.FRAPPE;
			codeAction = i-45;
			setCible(codeAction); 
		}else if(codeAction>=49&&codeAction<=58){
			type = TypeAction.PEINT;
			codeAction = i-49;
			setCible((codeAction)%5); //%5 dans le cas codeAction>=54
		}
	}
	
	public Position cible(){
		return cible;
	}
	
	public Personnage perso(){
		return perso;
	}
	
	public void executer(){
		/*if (this.type==TypeAction.MOUVEMENT){
			
		}*/
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
	private void setCible(int i){
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

	public int compareTo(Object arg0) {
		if(arg0.getClass()!=this.getClass())
			try {
				throw(new Exception());
			} catch (Exception e) {
				System.out.print("Comparaison sur typeAction impossible\n");
				e.printStackTrace();
				return 0;
			}
		else{
			return this.type().ordinal() - ((ActionFutur)arg0).type().ordinal();
		}

	}
	
	
}
