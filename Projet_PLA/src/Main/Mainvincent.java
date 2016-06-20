package Main;

import Parser.ParserXML;
import graphique.Grande;
//import javafx.application.Application;
import moteur.*;


public class Mainvincent {
	public static Partie p;
	public static void main(String[] args) {
		/*
		p = new Partie(50,50);
		int[][] t = new int[1][2];
		t[0][0]=1;
		t[0][1]=1;
		int[][] t2 = new int[1][2];
		t2[0][0]=codes.caseBlancheEloigneeSud;
		t2[0][1]=codes.avancer+2;
		
		Automate aut = new Automate(t, p.placerActions(t2), p);
		Peintre pei = new Peintre(0,aut,new Position(5,5));
		p.ajouterPersonnage(pei);
		Joueur rouge = new Joueur();
		rouge.ajoutPersonnage(pei);
		p.decor()[30][5].setValeur(codes.mur);
		*/
		ParserXML parser=new ParserXML();
		//verifier codes pour chaque type de personnage codes.attaquer à codes.attaquer + 3
		
		//Partie jeu;

		//0:Partie buildGame(X.xml,Y.xml,0)
		//1: spawn proche sur meme ligne
		//Partie buildGame(guerrierLigneR.xml,guerrierLigneB.xml,1)
		//2: un peintre qui repeint directement automate ennemi
		//Partie buildGame(guerrierLigneR.xml,guerrierLigneB.xml,2)
		//3: deux peintres qui essaye de peindre la meme case
		//Partie buildGame(peintreCaseR.xml,peindreCaseB.xml,3)
		//4: reagissent qu'à la balise
		//Partie buildGame(teamBaliseR.xml,teamBaliseB.xml,4)
		//5: game normale
		//Partie buildGame(teamNormaleR.xml,teamNormaleB.xml,5)
		
		int i=3;
		
		switch(i){
		case 0:p=parser.buildGame("joueur1.xml","joueur2.xml",0);
		p.ajoutMursMap();
		break;
		case 1:p=parser.buildGame("guerrierLigneR.xml","guerrierLigneB.xml",1);break;
		case 2:p=parser.buildGame("peintreRepeint1.xml","peintreRepeint2.xml",2);break;
		case 3:p=parser.buildGame("PeintrevsPeintre1.xml","PeintrevsPeintre2.xml",3);break;
		case 4:p=parser.buildGame("BaliseOnly1.xml","BaliseOnly2.xml",4);break;
		}
		//p=parser.buildGame("joueur1.xml","joueur2.xml",0);
		//p=parser.buildGame("guerrierLigneR.xml","guerrierLigneB.xml",1);

		
		
		//jeu.ajoutMurs(500);
		
		//p.ajoutMursMap();
		
		p.affichageText();
		
		try {
		    Thread.sleep(1000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		while(true){
			p.tour();
			p.affichageText();
			p.affichePersoPos();
			
			System.out.println(p.nbCasesPeinteR());
			System.out.println(p.nbCasesPeinteB());
			try {
			    Thread.sleep(1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		//Application.launch(Grande.class, args);

	}

}
