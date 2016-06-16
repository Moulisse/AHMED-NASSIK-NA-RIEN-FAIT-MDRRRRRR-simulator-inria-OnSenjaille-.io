
package moteur;

import java.util.ArrayList;
import java.util.List;


public class Partie {

	private Cellule decor[][];
	private Ordonnanceur ordonnanceur;
	private List<Personnage> personnages;//penser a distinguer la team des personnages
	
	public Partie() {
		this.decor = new Cellule[10][10];
		for(int i = 0; i < decor.length; i++){
			for (int j = 0; j < decor[i].length; j++) {
				decor[i][j]=new Cellule();
			}
		}
		this.ordonnanceur = new Ordonnanceur(this);
		this.personnages=new ArrayList<Personnage>();
	}
	
	public Partie(int nbLigne,int nbColonne) {
		this.decor = new Cellule[nbLigne][nbColonne];
		for(int i = 0; i < decor.length; i++){
			for (int j = 0; j < decor[i].length; j++) {
				decor[i][j]=new Cellule();
			}
		}
		this.ordonnanceur = new Ordonnanceur(this);
		this.personnages=new ArrayList<Personnage>();
	}
	
	public void tour(){
		ordonnanceur.tour();
	}
	
	public void ajouterPersonnage(Personnage p){
		personnages.add(p);
		
	}
	
	public void ajouterListe(List<Personnage> p){
		personnages.addAll(p);
		
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
				this.decor[posY+i][posX+j].setValeur(a[i][j]);
			}
		}
		return new Position(posX,posY);
	}
	
	//Place l'automate d'action et renvoie ses coordonnées
	public void placerActions(int[][] a, int x, int y){
		//TODO vérifier les positions disponibles
		int posX=x;
		int posY=y;
		int i,j;
		for(i=0;i<a.length;i++){
			for(j=0;j<a[i].length;j++){
				this.decor[posY+i][posX+j].setValeur(a[i][j]);
			}
		}
	}
	
	//Place l'automate d'action et renvoie ses coordonnées
	public Position placerActions(int[][] a, int indicePlacement){
		//TODO vérifier les positions disponibles
		int i=0,j = 0;
		
		int randomPlacement=(int)(Math.random()*19);
		for(i=0;i<a.length;i++){
			for(j=0;j<a[i].length;j++){
				System.out.print((6*indicePlacement)+i+" ");
				System.out.print((randomPlacement+j)+" ");
				System.out.println("i="+i+" j="+j+" "+a[i][j]);
				this.decor[(6*indicePlacement)+i][randomPlacement+j].setValeur(a[i][j]);
			}
		}
		return new Position(6*indicePlacement,(randomPlacement));
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
	
	public boolean blocke(int x,int y){
		return (this.decor[y][x].valeur()==65);
	}
	
	public void affichageText(){
		int i,j;
		
		for(j=0;j<this.decor()[0].length;j++){
			System.out.print("----");
		}
		System.out.print("-\n");
		for(i=0;i<this.decor().length;i++){
			System.out.print("|");
			for(j=0;j<this.decor()[i].length;j++){
				if(this.occupe(j, i)!=null){
					System.out.print(" * ");
				}else{
					System.out.print(" "+this.decor[i][j].valeur()+" ");
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
		System.out.print("\n");
	}
	
	
}
