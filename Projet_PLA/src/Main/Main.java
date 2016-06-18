package Main;

import graphique.Grande;
import javafx.application.Application;
import moteur.*;


public class Main {
	public static Partie p;
	public static void main(String[] args) {
		
		p = new Partie(300,300);
		int[][] t = new int[1][2];
		t[0][0]=1;
		t[0][1]=1;
		int[][] t2 = new int[1][2];
		t2[0][0]=codes.caseBlancheEloigneeSud;
		t2[0][1]=codes.avancer+2;
		Automate aut = new Automate(t, p.placerActions(t2), p);
		Guerrier gue = new Guerrier(0,aut,new Position(5,2));
		p.ajouterPersonnage(gue);
		Joueur rouge = new Joueur();
		rouge.ajoutPersonnage(gue);
		p.affichageText();
		/*try {
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
		}*/
		
		Application.launch(Grande.class, args);

	}

}
