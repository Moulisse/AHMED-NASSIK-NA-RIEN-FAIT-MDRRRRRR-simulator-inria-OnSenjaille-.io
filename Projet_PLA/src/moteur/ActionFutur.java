package moteur;

public class ActionFutur {
	
	private Personnage perso;
	private int codeAction;
	
	ActionFutur(Personnage p,int i){
		perso = p;
		codeAction = i;
	}
	
	public void executer(){
		perso.agir(codeAction);
	}
	
	
	
}
