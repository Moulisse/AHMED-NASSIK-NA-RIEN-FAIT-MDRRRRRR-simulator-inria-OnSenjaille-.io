package Main;

import Parser.ParserXML;
import graphique.Grande;
//import javafx.application.Application;
import moteur.*;


public class Main {
	public static Partie p;
	public static void main(String[] args) {
		
		/*p = new Partie(50,50);
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
		rouge.ajoutPersonnage(pei);*/

		ParserXML parser=new ParserXML();
		//verifier codes pour chaque type de personnage codes.attaquer à codes.attaquer + 3
		
		//Partie jeu;
		p=parser.buildGame("test1.xml","test1.xml");
		//jeu.ajoutMurs(500);
		
		p.ajoutMursMap();
		
		p.affichageText();
		try {
		    Thread.sleep(1000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		while(true){
			p.tour();
			p.affichageText();
			try {
			    Thread.sleep(1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		//Application.launch(Grande.class, args);

	}

}
