package Parser;

import moteur.Partie;

public class mainXMLtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParserXML parser=new ParserXML();
		//verifier codes pour chaque type de personnage codes.attaquer à codes.attaquer + 3
		
		//Partie jeu;
		Partie jeu=parser.buildGame("test1.xml","test1.xml");
		//jeu.ajoutMurs(500);
		
		jeu.ajoutMursMap();
		
		
	//	jeu.ajoutMurRandom();

		jeu.affichageText();
		
		jeu.tour();
		jeu.affichageText();
		jeu.tour();
		jeu.affichageText();
		jeu.tour();
		jeu.affichageText();
		//jeu.tour();
	//	jeu.affichageText();
	}

}
