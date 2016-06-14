package Parser;
import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import moteur.*;

import org.jdom2.filter.*;
import java.util.List;
import java.util.Iterator;

public class parserXML
{
static org.jdom2.Document document;
static Element racine;//pas laisser ici
public static void main(String[] args)
{
//On crée une instaXxRomUalD720N0Sc0P3xXnce de SAXBuilder
SAXBuilder sxb = new SAXBuilder();
try
{
//On crée un nouveau document JDOM avec en argument le fichier XML
//ajouter scanf nom fichier
document = sxb.build(new File("modele.xml"));
}
catch(Exception e){}
//On initialise un nouvel élément racine avec l'élément racine du document.
racine = document.getRootElement();//racine : <automates>
//Méthode définie plus loin
afficheALL();
}
static void afficheALL()
{

	System.out.println("Nombre d'automates : "+racine.getChild("nb_aut").getText());
	List listAutomates = racine.getChildren("au");
	Iterator it_automates = listAutomates.iterator();//iterateur automates
	Element automate_courant;
	while(it_automates.hasNext())					//parcours automates
	{
	automate_courant=(Element) it_automates.next();
	System.out.println("Type du personnage : "+automate_courant.getAttributeValue("personnage"));
		Element action=automate_courant.getChild("action");
		System.out.println("Nombre de lignes automate actions : "+action.getAttributeValue("nb_l")+"\nNombre de colonnes automate action : "+action.getAttributeValue("nb_c"));
		List listLignesAction=action.getChildren("l");
		Iterator it_lignes=listLignesAction.iterator();	//iterateur lignes automate action
		Element ligne_courante;							
		System.out.println("Automate des actions :");
		while(it_lignes.hasNext())
		{
			ligne_courante=(Element)it_lignes.next();
			List listColonnesActions=ligne_courante.getChildren("c");
			Iterator it_colonnes=listColonnesActions.iterator();//iterateur colonnes
			Element colonne_courante;
			while(it_colonnes.hasNext())
			{
				colonne_courante=(Element) it_colonnes.next();
				System.out.print(colonne_courante.getText());
			}
				System.out.println("");
		}
		
		Element transition=automate_courant.getChild("transition");
		System.out.println("Nombre de lignes automate transition : "+transition.getAttributeValue("nb_l")+"\nNombre de colonnes transition action : "+transition.getAttributeValue("nb_c"));
		List listLignesTransi=transition.getChildren("l");
		it_lignes=listLignesTransi.iterator();
		System.out.println("Automate des Transitions :");
		while(it_lignes.hasNext())
		{
			ligne_courante=(Element)it_lignes.next();
			List listColonnesTransitions=ligne_courante.getChildren("c");
			Iterator it_colonnes=listColonnesTransitions.iterator();
			Element colonne_courante;
			while(it_colonnes.hasNext())
			{
				colonne_courante=(Element) it_colonnes.next();
				System.out.print(colonne_courante.getText());
			}
				System.out.println("");
		
		
	}
	
	
	
	
	}
	
	
	


}

static int nbAuto(String nomFichier)//renvoie le nombre d'automates
{
	Element racine;
	//On crée une instance de SAXBuilder
	SAXBuilder sxb = new SAXBuilder();
	try
	{
	//On crée un nouveau document JDOM avec en argument le fichier XML
	//Le parsing est terminé ;)
	document = sxb.build(new File(nomFichier));
	}
	catch(Exception e){}
	//On initialise un nouvel élément racine avec l'élément racine du document.
	racine = document.getRootElement();//<automates>	
	int nbAuto;
	nbAuto=Integer.parseInt(racine.getChild("nb_aut").getValue());	
	return nbAuto;
	
}



public Personnage createPersonnage(Element element, Partie partie)
{
	int i=0,j=0;
	
	
	System.out.println("Type du personnage : "+element.getAttributeValue("personnage"));	
	
	
	if(element.getAttributeValue("personnage")=="guerrier"){
		Guerrier persoCourant;
		
	}
	else{
			if(element.getAttributeValue("personnage")=="peintre")
			{
				Peintre persoCourant;
			}
	}
	
	
	
	
	Element action=element.getChild("action");
	System.out.println("Nombre de lignes automate actions : "+action.getAttributeValue("nb_l")+"\nNombre de colonnes automate action : "+action.getAttributeValue("nb_c"));
	
	int actions[][]=new int[Integer.parseInt(action.getAttributeValue("nb_l"))][Integer.parseInt(action.getAttributeValue("nb_c"))];
	
	
	List listLignesAction=action.getChildren("l");
	Iterator it_lignes=listLignesAction.iterator();	//iterateur lignes automate action
	Element ligne_courante;							
	System.out.println("Automate des actions :");
	while(it_lignes.hasNext())
	{
		ligne_courante=(Element)it_lignes.next();
		List listColonnesActions=ligne_courante.getChildren("c");
		Iterator it_colonnes=listColonnesActions.iterator();//iterateur colonnes
		Element colonne_courante;
		while(it_colonnes.hasNext())
		{
			colonne_courante=(Element) it_colonnes.next();
			
			System.out.print(colonne_courante.getText());
			partie
			actions[i][j];
		}
		
		i++;
		j=0;
			System.out.println("");
	}
	i<position.getY()+ hauteur de l'automate d'action (nombre de conditions). 
	Element transition=element.getChild("transition");
	System.out.println("Nombre de lignes automate transition : "+transition.getAttributeValue("nb_l")+"\nNombre de colonnes transition action : "+transition.getAttributeValue("nb_c"));
	List listLignesTransi=transition.getChildren("l");
	it_lignes=listLignesTransi.iterator();
	System.out.println("Automate des Transitions :");
	while(it_lignes.hasNext())
	{
		ligne_courante=(Element)it_lignes.next();
		List listColonnesTransitions=ligne_courante.getChildren("c");
		Iterator it_colonnes=listColonnesTransitions.iterator();
		Element colonne_courante;
		while(it_colonnes.hasNext())
		{
			colonne_courante=(Element) it_colonnes.next();
			System.out.print(colonne_courante.getText());
		}
			System.out.println("");
	
	
}
	
	
	return null;
	
	
}


public Joueur createPlayer(String nomFichier)
{
	Joueur joueur=new Joueur();
	//Personnage persoCourant=null;
	
	List listAutomates = racine.getChildren("au");
	Iterator it_automates = listAutomates.iterator();//iterateur automates
	Element automate_courant;
	while(it_automates.hasNext())					//parcours automates
	{
	automate_courant=(Element) it_automates.next();
	System.out.println("Type du personnage : "+automate_courant.getAttributeValue("personnage"));
	
	
	
	
	
	
	
	
	
	
	
	

	
	
		Element action=automate_courant.getChild("action");
		System.out.println("Nombre de lignes automate actions : "+action.getAttributeValue("nb_l")+"\nNombre de colonnes automate action : "+action.getAttributeValue("nb_c"));
		
		List listLignesAction=action.getChildren("l");
		Iterator it_lignes=listLignesAction.iterator();	//iterateur lignes automate action
		Element ligne_courante;							
		System.out.println("Automate des actions :");
		while(it_lignes.hasNext())
		{
			ligne_courante=(Element)it_lignes.next();
			List listColonnesActions=ligne_courante.getChildren("c");
			Iterator it_colonnes=listColonnesActions.iterator();//iterateur colonnes
			Element colonne_courante;
			while(it_colonnes.hasNext())
			{
				colonne_courante=(Element) it_colonnes.next();
				System.out.print(colonne_courante.getText());
			}
				System.out.println("");
		}10
		
		Element transition=automate_courant.getChild("transition");
		System.out.println("Nombre de lignes automate transition : "+transition.getAttributeValue("nb_l")+"\nNombre de colonnes transition action : "+transition.getAttributeValue("nb_c"));
		List listLignesTransi=transition.getChildren("l");
		it_lignes=listLignesTransi.iterator();
		System.out.println("Automate des Transitions :");
		while(it_lignes.hasNext())
		{
			ligne_courante=(Element)it_lignes.next();
			List listColonnesTransitions=ligne_courante.getChildren("c");
			Iterator it_colonnes=listColonnesTransitions.iterator();
			Element colonne_courante;
			while(it_colonnes.hasNext())
			{
				colonne_courante=(Element) it_colonnes.next();
				System.out.print(colonne_courante.getText());
			}
				System.out.println("");
		
		
	}
	
	
	
	
	}
	
	
	
	
	
	if(automate_courant.getAttributeValue("personnage")=="guerrier"){
		Guerrier persoCourant;
		
	}
	else{
			if(automate_courant.getAttributeValue("personnage")=="peintre")
			{
				Peintre persoCourant;
			}
	}
	
	
	
	
	
	
	
	
	
	return null;
	
}


public Partie buildGame(String fichierJoueur1, String fichierJoueur2)
{
	int tailleBoard=nbAuto(fichierJoueur1)+nbAuto(fichierJoueur2);
	//hauteur max : 40
	//longueur max : 5
	
	//taille map : chaque perso peut prendre la taille max
	
	
	Partie partieInitiale = new Partie(5*tailleBoard,40*tailleBoard);
	Joueur joueur1=new Joueur();
	Joueur joueur2=new Joueur();
	
	
	
	//initialisation JOUEUR 1
	

	
	
	
	
	
	
	
	return partieInitiale;
}




/*
static void instancierJoueur(joueur joueur,String nomFichier)
{
	//On crée une instance de SAXBuilder
	SAXBuilder sxb = new SAXBuilder();
	Element racine;//care y a deja un racine debut class
	try
	{
	//On crée un nouveau document JDOM avec en argument le fichier XML
	//Le parsing est terminé ;)
	document = sxb.build(new File(nomFichier));
	}
	catch(Exception e){}
	//UN JOUEUR A UNE LISTE DE PERSONNAGES
	personnage persoCourant;//personnage courant que l'on va ajouter a la liste des persos du joueur
	List listAutomates = racine.getChildren("au");
	Iterator it_automates = listAutomates.iterator();//iterateur automates
	Element automate_courant;
	automate_courant=(Element) it_automates.next();
	//System.out.println("ee");	
	persoCourant.type=automate_courant.getAttributeValue("personnage");
		Element action=automate_courant.getChild("action");
		int nbl,nbc;
		nbl=Integer.parseInt(action.getAttributeValue("nb_l"));
		nbc=Integer.parseInt(action.getAttributeValue("nb_c"));
		//int tg[][];
		//int[nbl][nbc] actions;//tableau actions
		System.out.println("Nombre de lignes automate actions : "+action.getAttributeValue("nb_l")+"\nNombre de colonnes automate action : "+action.getAttributeValue("nb_c"));
		persoCourant.actions
		List listLignesAction=action.getChildren("l");
		Iterator it_lignes=listLignesAction.iterator();
		Element ligne_courante;
		System.out.println("Automate des actions :");
		while(it_lignes.hasNext())
		{
			ligne_courante=(Element)it_lignes.next();
			List listColonnesActions=ligne_courante.getChildren("c");
			Iterator it_colonnes=listColonnesActions.iterator();
			Element colonne_courante;
			while(it_colonnes.hasNext())
			{
				colonne_courante=(Element) it_colonnes.next();
				System.out.print(colonne_courante.getText());
			}
				System.out.println("");
		}
		
		Element transition=automate_courant.getChild("transition");
		System.out.println("Nombre de lignes automate transition : "+transition.getAttributeValue("nb_l")+"\nNombre de colonnes transition action : "+transition.getAttributeValue("nb_c"));
		List listLignesTransi=transition.getChildren("l");
		it_lignes=listLignesTransi.iterator();
		System.out.println("Automate des Transitions :");
		while(it_lignes.hasNext())
		{
			ligne_courante=(Element)it_lignes.next();
			List listColonnesTransitions=ligne_courante.getChildren("c");
			Iterator it_colonnes=listColonnesTransitions.iterator();
			Element colonne_courante;
			while(it_colonnes.hasNext())
			{
				colonne_courante=(Element) it_colonnes.next();
				System.out.print(colonne_courante.getText());
			}
				System.out.println("");
		
		
	}
	
	
	
	
	
	
	
	
	
}





*/
}

