package moteur;

public class Main {
	
	
	public static void main(String[] args) {
				
		Partie p = new Partie();
		int[][] t = new int[1][2];
		t[0][0]=1;
		t[0][1]=1;
		int[][] t2 = new int[1][2];
		t2[0][0]=11;
		t2[0][1]=1;
		Automate aut = new Automate(t, p.placerActions(t2), p);
		p.ajouterPersonnage(new Guerrier(0,aut,new Position(3,3)));
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
	}
}
