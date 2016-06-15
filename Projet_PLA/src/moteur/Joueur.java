package moteur;

import java.util.List;

public class Joueur {
	private List<Personnage> personnages;
	
	public Joueur()
	{
		personnages=null;
	}
	
	public Joueur(List<Personnage> p)
	{
		this.personnages=p;
	}
	
	public void ajoutPersonnage(Personnage perso)
	{
		this.personnages.add(perso);
		perso.equipe=this;//changement équipe du perso ajouté
	}
	
	public List<Personnage> getPersonnages(){
		return this.personnages;
	}
	
	
}
