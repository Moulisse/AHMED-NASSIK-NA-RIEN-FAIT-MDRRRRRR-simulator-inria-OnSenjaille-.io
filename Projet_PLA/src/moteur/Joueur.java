package moteur;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	private List<Personnage> personnages;
	private Balise balise;
	
	public Joueur()
	{
		personnages=new ArrayList<Personnage>();
		balise = new Balise();
	}
	
	public Balise balise(){
		return balise;
	}
	
	public Joueur(List<Personnage> p)
	{
		this.personnages=p;
		balise = new Balise();
		for(Personnage pers : this.personnages){
			pers.setEquipe(this);
		}
	}
	
	public void ajoutPersonnage(Personnage perso)
	{
		this.personnages.add(perso);
		perso.setEquipe(this); //changement équipe du perso ajouté
	}
	
	public List<Personnage> getPersonnages(){
		return this.personnages;
	}
	
	
}
