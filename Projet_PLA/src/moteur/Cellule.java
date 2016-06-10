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
	}
	
}
