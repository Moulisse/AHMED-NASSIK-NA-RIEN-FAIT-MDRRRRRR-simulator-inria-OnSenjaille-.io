package Parser;

import moteur.Partie;

public class mainXMLtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParserXML parser=new ParserXML();
		
		
		Partie jeu;
		jeu=parser.buildGame("modeleJ1.xml","modeleJ2.xml");
		
		
	}

}
