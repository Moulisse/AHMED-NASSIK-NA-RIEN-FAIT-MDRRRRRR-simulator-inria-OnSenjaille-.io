package Parser;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import moteur.*;

import org.jdom2.filter.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ParserXML {
	//static org.jdom2.Document document;
	//static Element racine;// pas laisser ici

/*	public void test(String[] args) {
		// On crée une instaXxRomUalD720N0Sc0P3xXnce de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// ajouter scanf nom fichier
			document = sxb.build(new File("modele.xml"));
		} catch (Exception e) {
		}
		// On initialise un nouvel élément racine avec l'élément racine du
		// document.
		racine = document.getRootElement();// racine : <automates>
		// Méthode définie plus loin
		afficheALL();
		Partie partieOfficial = buildGame("modeleJ1", "modeleJ2");
		partieOfficial.affichageText();
	}*/

	
	 

	public ParserXML(){
		
	}
	
	
	
	
	
	
	
	void afficheALL(String nomFichier) {
		org.jdom2.Document document = null;
		Element racine;
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// ajouter scanf nom fichier
			document = sxb.build(new File(nomFichier));
		} catch (Exception e) {
		}
		
		racine = document.getRootElement();
		
		System.out.println("Nombre d'automates : " + racine.getChild("nb_aut").getText());
		List listAutomates = racine.getChildren("au");
		Iterator it_automates = listAutomates.iterator();// iterateur automates
		Element automate_courant;
		while (it_automates.hasNext()) // parcours automates
		{
			automate_courant = (Element) it_automates.next();
			System.out.println("Type du personnage : " + automate_courant.getAttributeValue("personnage"));
			Element action = automate_courant.getChild("action");
			System.out.println("Nombre de lignes automate actions : " + action.getAttributeValue("nb_l")
					+ "\nNombre de colonnes automate action : " + action.getAttributeValue("nb_c"));
			List listLignesAction = action.getChildren("l");
			Iterator it_lignes = listLignesAction.iterator(); // iterateur
																// lignes
																// automate
																// action
			Element ligne_courante;
			System.out.println("Automate des actions :");
			while (it_lignes.hasNext()) {
				ligne_courante = (Element) it_lignes.next();
				List listColonnesActions = ligne_courante.getChildren("c");
				Iterator it_colonnes = listColonnesActions.iterator();// iterateur
																		// colonnes
				Element colonne_courante;
				while (it_colonnes.hasNext()) {
					colonne_courante = (Element) it_colonnes.next();
					System.out.print(colonne_courante.getText());
				}
				System.out.println("");
			}

			Element transition = automate_courant.getChild("transition");
			System.out.println("Nombre de lignes automate transition : " + transition.getAttributeValue("nb_l")
					+ "\nNombre de colonnes transition action : " + transition.getAttributeValue("nb_c"));
			List listLignesTransi = transition.getChildren("l");
			it_lignes = listLignesTransi.iterator();
			System.out.println("Automate des Transitions :");
			while (it_lignes.hasNext()) {
				ligne_courante = (Element) it_lignes.next();
				List listColonnesTransitions = ligne_courante.getChildren("c");
				Iterator it_colonnes = listColonnesTransitions.iterator();
				Element colonne_courante;
				while (it_colonnes.hasNext()) {
					colonne_courante = (Element) it_colonnes.next();
					System.out.print(colonne_courante.getText());
				}
				System.out.println("");

			}

		}

	}

	static int nbAuto(String nomFichier)// renvoie le nombre d'automates
	{
		org.jdom2.Document document = null;
		Element racine;
		// On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			document = sxb.build(new File(nomFichier));
		} catch (Exception e) {
		}
		// On initialise un nouvel élément racine avec l'élément racine du
		// document.
		racine = document.getRootElement();// <automates>
		int nbAuto;
		nbAuto = Integer.parseInt(racine.getChild("nb_aut").getValue());
		return nbAuto;

	}

	public Personnage createPersonnage(Element element, Partie partie, int indicePlacement, int tailleMap,int numeroTest) {
		int i = 0, j = 0;

		System.out.println("1Type du personnage : " + element.getAttributeValue("personnage"));

		Element action = element.getChild("action");
		System.out.println("Nombre de lignes automate actions : " + action.getAttributeValue("nb_l")
				+ "\nNombre de colonnes automate action : " + action.getAttributeValue("nb_c"));

		if(Integer.parseInt(action.getAttributeValue("nb_c"))>5){
			System.err.print("UN AUTOMATE POSSEDE TROP D'ETAT : MAXIMUM = 5");
			System.exit(0);}
		
		int actions[][] = new int[Integer.parseInt(action.getAttributeValue("nb_l"))][Integer.parseInt(action.getAttributeValue("nb_c"))];

		List listLignesAction = action.getChildren("l");
		Iterator it_lignes = listLignesAction.iterator(); // iterateur lignes
															// automate action
		Element ligne_courante;
		System.out.println("Automate des actions :");
		while (it_lignes.hasNext()) {
			ligne_courante = (Element) it_lignes.next();
			List listColonnesActions = ligne_courante.getChildren("c");
			Iterator it_colonnes = listColonnesActions.iterator();// iterateur
																	// colonnes
			Element colonne_courante;
			while (it_colonnes.hasNext()) {
				colonne_courante = (Element) it_colonnes.next();

				System.out.println(colonne_courante.getText());
				actions[i][j] = Integer.parseInt(colonne_courante.getText());
				System.out.println("actions "+i+" "+j+" "+actions[i][j]);
				j++;
			}

			i++;
			j = 0;

			System.out.println("");
		}
		Position automatePosition = partie.placerActions(actions, indicePlacement,Integer.parseInt(action.getAttributeValue("nb_l")),tailleMap);
		System.out.println("automate position : X = "+automatePosition.getX()+" Y = "+automatePosition.getY());
		System.out.println("placement max : "+(tailleMap-Integer.parseInt(action.getAttributeValue("nb_c"))));
		System.out.println("Positionnement en "+i);
		Element transition = element.getChild("transition");

		// TRANSITIONS
		int transi[][] = new int[Integer.parseInt(transition.getAttributeValue("nb_l"))][Integer.parseInt(transition.getAttributeValue("nb_c"))];
		i = 0;
		j = 0;

		System.out.println("Nombre de lignes automate transition : " + transition.getAttributeValue("nb_l")
				+ "\nNombre de colonnes transition action : " + transition.getAttributeValue("nb_c"));
		List listLignesTransi = transition.getChildren("l");
		it_lignes = listLignesTransi.iterator();
		System.out.println("Automate des Transitions :");
		while (it_lignes.hasNext()) {
			ligne_courante = (Element) it_lignes.next();
			List listColonnesTransitions = ligne_courante.getChildren("c");
			Iterator it_colonnes = listColonnesTransitions.iterator();
			Element colonne_courante;
			while (it_colonnes.hasNext()) {
				colonne_courante = (Element) it_colonnes.next();
				transi[i][j] = Integer.parseInt(colonne_courante.getText());
				System.out.print("transitions "+"j="+j+" i="+i+" "+colonne_courante.getText()+" ");
				j++;
			}

			j = 0;
			i++;
			System.out.println("");

		}
		Automate auto;
		// Position newPos=new Position(i*6,j);
		auto = new Automate(transi, automatePosition, partie);

		Personnage persoCourant = null;

		//System.out.println("X: "+automatePosition.getX()+" Y : "+automatePosition.getY());
	//	persoCourant = new Guerrier(0, auto, automatePosition);
		//persoCourant = new Guerrier(0, auto, new Position(automatePosition.getX(),automatePosition.getY()));
	//System.out.println("X: "+persoCourant.position().getX()+" Y : "+persoCourant.position().getY());
		/*if (element.getAttributeValue("personnage") == "guerrier") {
			persoCourant = new Guerrier(0, auto, automatePosition);
		} else {
			if (element.getAttributeValue("personnage") == "peintre") {
				persoCourant = new Peintre(0, auto, automatePosition);
			}
		}*/
		System.out.println("X: "+automatePosition.getX()+" Y : "+automatePosition.getY());
			Position newPos = new Position();
		switch (numeroTest){
		case 0 :	//Nord
			
			newPos=partie.placementAleatoire();
			break;
		case 1 : 	//Est
			
			if(partie.occupe(3,20)!=null)
			{
				newPos.setX(15);
				newPos.setY(20);
			}
			else
			{
				newPos.setX(3);
				newPos.setY(20);
			}
			
			
			break;
		case 2 : 
			break;
		}
		

	//	persoCourant = new Guerrier(0, auto, automatePosition);
		System.out.println("Le perso est de tyyype : "+element.getAttributeValue("personnage"));
		
		if((element.getAttributeValue("personnage")).equals("guerrier")){	
			//persoCourant = new Guerrier(0, auto, new Position(automatePosition.getX(),automatePosition.getY()));}

			persoCourant=new Guerrier(0, auto, newPos);
		}
		else{
			persoCourant=new Peintre(0, auto, newPos);
			
		}
			
		
		System.out.println("POSITION PERSONNAGE X: "+persoCourant.position().getX()+" Y : "+persoCourant.position().getY());

		//persoCourant = new Guerrier(0, auto, new Position(1,2));
		if(partie.occupe(persoCourant.position().getX()+1,persoCourant.position().getY())!=null){System.out.println("occupe");}
		else{System.out.println("non occuoe");}
		if(persoCourant==null){System.out.println("YOOKROZEKROEZKROZKEROZKEROZKR");}
		persoCourant.setPartie(partie);
		return persoCourant;

	}

	
	public Joueur createPlayer(Partie partie, String nomFichier, int posInitiale,int tailleMap,int numeroTest) {
		// Joueur joueur=new Joueur();
		// Personnage persoCourant=null;
		
		
		org.jdom2.Document document = null;
		Element racine;
		SAXBuilder sxb = new SAXBuilder();
		//try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			try {
				document = sxb.build(new File(nomFichier));
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//	} catch (Exception e) 
		
		
		racine = document.getRootElement();

		int i = 0;
		List<Element> listAutomates = racine.getChildren("au");
		Iterator<Element> it_automates = listAutomates.iterator();// iterateur automates
		List<Personnage> personnageListe = new ArrayList<Personnage>();
		Element automate_courant;
		while (it_automates.hasNext()) // parcours automates
		{
			automate_courant = (Element) it_automates.next();
			System.out.println("Type du personnage : " + automate_courant.getAttributeValue("personnage"));
			
			
			personnageListe.add(createPersonnage(automate_courant, partie, posInitiale + i,tailleMap,numeroTest));
			
			System.out.println(i+ " ooooo");
			i++;

		}

		return new Joueur(personnageListe);

	}
		
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


	public Partie buildGame(String fichierJoueur1, String fichierJoueur2,int numeroTest) {
		int nbTotalAutomates = nbAuto(fichierJoueur1) + nbAuto(fichierJoueur2);
		if(nbAuto(fichierJoueur1)!=(nbAuto(fichierJoueur2))){System.err.println("LES DEUX JOUEURS N'ONT PAS LE MEME NOMBRE DE PERSONNAGE"); 
		System.exit(0);
		}
		// hauteur max : 40
		// longueur max : 5

		// taille map : chaque perso peut prendre la taille max
		int longueurAutoMax=50;
		
		
		System.out.println("nbauto1 : "+nbAuto(fichierJoueur1)+"nbauto2 "+nbAuto(fichierJoueur2));
		Partie partieInitiale = new Partie(longueurAutoMax,6 * nbTotalAutomates);// peut
																		// etre
																		// modif
																		// 40
																		// trop
																		// faible//g
																		// mis
																		// 60 et
																		// ça
																		// spawn
																		// a un
																		// endroit
																		// random
																		// sur
																		// la
																		// hauteur

	//	System.out.println("taille map : "+partieInitiale.decor().length);
		Joueur joueur1 = createPlayer(partieInitiale, fichierJoueur1, 0,longueurAutoMax,numeroTest);
		//les deux joueurs doivent avoir le meme nombre d'automate
		Joueur joueur2 = createPlayer(partieInitiale, fichierJoueur2, nbAuto(fichierJoueur1),longueurAutoMax,numeroTest);

		// initialisation JOUEUR 1
		
		partieInitiale.setJoueur1(joueur1);
		partieInitiale.setJoueur2(joueur2);
		partieInitiale.ajouterListe(joueur1.getPersonnages());
		System.out.println("Il ya : "+ joueur1.getPersonnages().size()+" pour le joueur 1");
		partieInitiale.ajouterListe(joueur2.getPersonnages());
		System.out.println("Il ya : "+ joueur2.getPersonnages().size()+" pour le joueur 2");

		return partieInitiale;
	}

	/*
	 * static void instancierJoueur(joueur joueur,String nomFichier) { //On crée
	 * une instance de SAXBuilder SAXBuilder sxb = new SAXBuilder(); Element
	 * racine;//care y a deja un racine debut class try { //On crée un nouveau
	 * document JDOM avec en argument le fichier XML //Le parsing est terminé ;)
	 * document = sxb.build(new File(nomFichier)); } catch(Exception e){} //UN
	 * JOUEUR A UNE LISTE DE PERSONNAGES personnage persoCourant;//personnage
	 * courant que l'on va ajouter a la liste des persos du joueur List
	 * listAutomates = racine.getChildren("au"); Iterator it_automates =
	 * listAutomates.iterator();//iterateur automates Element automate_courant;
	 * automate_courant=(Element) it_automates.next();
	 * //System.out.println("ee");
	 * persoCourant.type=automate_courant.getAttributeValue("personnage");
	 * Element action=automate_courant.getChild("action"); int nbl,nbc;
	 * nbl=Integer.parseInt(action.getAttributeValue("nb_l"));
	 * nbc=Integer.parseInt(action.getAttributeValue("nb_c")); //int tg[][];
	 * //int[nbl][nbc] actions;//tableau actions System.out.println(
	 * "Nombre de lignes automate actions : "+action.getAttributeValue("nb_l")+
	 * "\nNombre de colonnes automate action : "
	 * +action.getAttributeValue("nb_c")); persoCourant.actions List
	 * listLignesAction=action.getChildren("l"); Iterator
	 * it_lignes=listLignesAction.iterator(); Element ligne_courante;
	 * System.out.println("Automate des actions :"); while(it_lignes.hasNext())
	 * { ligne_courante=(Element)it_lignes.next(); List
	 * listColonnesActions=ligne_courante.getChildren("c"); Iterator
	 * it_colonnes=listColonnesActions.iterator(); Element colonne_courante;
	 * while(it_colonnes.hasNext()) { colonne_courante=(Element)
	 * it_colonnes.next(); System.out.print(colonne_courante.getText()); }
	 * System.out.println(""); }
	 * 
	 * Element transition=automate_courant.getChild("transition");
	 * System.out.println("Nombre de lignes automate transition : "
	 * +transition.getAttributeValue("nb_l")+
	 * "\nNombre de colonnes transition action : "
	 * +transition.getAttributeValue("nb_c")); List
	 * listLignesTransi=transition.getChildren("l");
	 * it_lignes=listLignesTransi.iterator(); System.out.println(
	 * "Automate des Transitions :"); while(it_lignes.hasNext()) {
	 * ligne_courante=(Element)it_lignes.next(); List
	 * listColonnesTransitions=ligne_courante.getChildren("c"); Iterator
	 * it_colonnes=listColonnesTransitions.iterator(); Element colonne_courante;
	 * while(it_colonnes.hasNext()) { colonne_courante=(Element)
	 * it_colonnes.next(); System.out.print(colonne_courante.getText()); }
	 * System.out.println("");
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * ParserXML
	 * 
	 */
}
