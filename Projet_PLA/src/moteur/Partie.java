
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
	/*
	public Partie(int nbLigne,int nbColonne) {
		this.decor = new Cellule[nbLigne][nbColonne];
		for(int i = 0; i < decor.length; i++){
			for (int j = 0; j < decor[i].length; j++) {
				decor[i][j]=new Cellule();
			}
		}
		this.ordonnanceur = new Ordonnanceur(this);
		this.personnages=new ArrayList<Personnage>();
	}*/		
	public Partie(int nbLigne,int nbColonne) {
		this.decor = new Cellule[nbLigne+2][nbColonne+2];
		for(int i = 0; i < decor.length; i++){
			for (int j = 0; j < decor[i].length; j++) {
				decor[i][j]=new Cellule();
			}
		}
		
		//creation murs
		decor[0][0].setValeur(codes.mur);

		for(int j=0;j < decor[0].length; j++) {
			decor[0][j].setValeur(codes.mur);;
			decor[decor.length-1][j].setValeur(codes.mur);
		}
		
		for(int j=1;j < decor.length-1; j++) {
			decor[j][0].setValeur(codes.mur);
			decor[j][decor[0].length-1].setValeur(codes.mur);
		}
		
		

		this.ordonnanceur = new Ordonnanceur(this);
		this.personnages=new ArrayList<Personnage>();
	}
	/*
	public Partie(int nbLigne,int nbColonne) {
		this.decor = new Cellule[nbLigne+2][nbColonne+2];
		
	
		
		
		for(int i = 0; i < decor.length; i++){
			for (int j = 0; j < decor[i].length; j++) {
				decor[i][j]=new Cellule();
			}
		}
		
		//creation murs
		
		for(int j=1;j < decor[0].length-1; j++) {
			decor[0][j].setValeur(9);;
			decor[decor.length][j].setValeur(9);
		}
		
		
		this.ordonnanceur = new Ordonnanceur(this);
		this.personnages=new ArrayList<Personnage>();
	}
	*/
	public void tour(){
		ordonnanceur.tour();
	}
	
	public void ajouterPersonnage(Personnage p){
		personnages.add(p);
		p.setPartie(this);
		
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
	/*
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
	}*/
	
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
				this.decor[(6*indicePlacement)+i+1][randomPlacement+j+1].setValeur(a[i][j]);
			}
		}
		return new Position(6*indicePlacement,(randomPlacement));
	}
	
	//Place l'automate d'action et renvoie ses coordonnées, place en random selon la taille de l'automate
	public Position placerActions(int[][] a, int indicePlacement, int tailleAutomate, int tailleMax){
		//TODO vérifier les positions disponibles
		int i=0,j = 0;
		
		int randomPlacement=(int)(Math.random()*(tailleMax-tailleAutomate));
		for(i=0;i<a.length;i++){
			for(j=0;j<a[i].length;j++){
				System.out.print((6*indicePlacement)+i+" ");
				System.out.print((randomPlacement+j)+" ");
				System.out.println("i="+i+" j="+j+" "+a[i][j]);
				this.decor[randomPlacement+i+1][(6*indicePlacement)+j+1].setValeur(a[i][j]);
			}
		}
		return new Position(6*indicePlacement+1,(randomPlacement)+1);
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
		return (this.decor[y][x].valeur()==codes.mur);
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
	
	//renvoie une position aléatoire de la classe, non occupée (pas de joueur ni de mur sur la cellule)
	//utilisée pour le placement des personnages
	public Position placementAleatoire(){
		Position pos=new Position(0,0);
		int coordX;
		int coordY;
		do{
		coordX=(int)(Math.random()*(this.decor[0].length));
		coordY=(int)(Math.random()*(this.decor.length));
				
		pos.setY(coordX);		
		pos.setX(coordY);
		}while((this.blocke(coordX,coordY)==true) || (this.occupe(coordX,coordY)!=null) );
		
		
		return pos;
		
	}
	
	public void ajoutMurRandom(){
		Position posRand=this.placementAleatoire();
		System.out.println("posRand : "+posRand.getX()+" " +posRand.getY() );
		this.decor[posRand.getX()][posRand.getY()].setValeur(codes.mur);
	}
	
	public void ajoutMurs(int nbMur){
		for(int i=0;i<nbMur;i++)
		{
			this.ajoutMurRandom();
		}
	}
	
	//ajoute un mur avec une proba 0.1
	public void ajoutMursMap(){
		int rand;
		for(int i=0;i<this.decor().length;i++){
			for(int j=0;j<this.decor()[i].length;j++){
				rand=((int)(Math.random()*100));
				if(rand<=10){this.decor[i][j].setValeur(codes.mur);
				}
			}
		}
		
		
	}
	
	public int hauteur(){
		return this.decor.length;
	}
	
	public int longueur(){
		return this.decor[0].length;
	}
	
	
}
