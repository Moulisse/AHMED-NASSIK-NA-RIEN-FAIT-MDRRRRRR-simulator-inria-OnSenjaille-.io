package moteur;

import java.util.List;

public class Joueur {
	private List<Personnage> personnages;
	
	public Joueur()
	{
		personnages=null;
	}
	
	public void ajoutPersonnage(Personnage perso)
	{
		this.personnages.add(perso);
	}
	
	
	
}
