
package moteur;

import java.util.ArrayList;
import java.util.List;


public class Partie {

	private Cellule decor[][];
	private Ordonnanceur horloge;
	private List<Personnage> personnages;
	
	public Partie() {
		this.decor = new Cellule[10][10];
		for(int i = 0; i < decor.length; i++){
			for (int j = 0; j < decor[i].length; j++) {
				decor[i][j]=new Cellule();
			}
		}
		this.horloge = new Ordonnanceur(this);
		this.personnages=new ArrayList<Personnage>();
	}
	
	public void ajouterPersonnage(Personnage p){
		personnages.add(p);
		
	}
	
	public List<Personnage> personnages(){
		return this.personnages;
	}
	
	//Place l'automate d'action et renvoie ses coordonnées
	public Position placerActions(int[][] a){
		//TODO vérifier les positions disponibles
		int posX=0;
		int posY=0;
		int i,j;
		for(i=0;i<a.length;i++){
			for(j=0;j<a[i].length;j++){
				this.decor[posX+i][posY+j].setValeur(a[i][j]);
			}
		}
		return new Position(posX,posY);
	}
	
	public Cellule[][] decor(){
		return decor;
	}
	
	public Personnage occupe(int x, int y){
		for(Personnage p : personnages){
			if (p.position().getX()==x&&p.position().getY()==y){
				return p;
			}
		}
		return null;
	}
	
	public void affichageText(){
		int i,j;
		for(i=0;i<this.decor().length;i++){
			System.out.print("|");
			for(j=0;j<this.decor()[i].length;j++){
				if(this.occupe(j, i)!=null){
					System.out.print(" * ");
				}else{
					System.out.print("   ");
				}
				System.out.print("|");
			}
			System.out.print("\n");
			System.out.print("-");
			for(j=0;j<this.decor()[i].length;j++){
				System.out.print("----");
			}
			System.out.print("\n");
		}
	}
	
	
}
