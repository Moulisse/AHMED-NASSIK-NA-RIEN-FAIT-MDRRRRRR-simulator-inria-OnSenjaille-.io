package moteur;

public class Balise {
	
	private Position pos;
	private boolean active;
	
	Balise (){
		pos= new Position(0,0);
		active = false;
	}
	
	public boolean active(){
		return active;
	}
	
	public Position position(){
		return pos;
	}
	
	public void placerBalise(Position p){
		pos=p;
		active = true;
	}
	
	public void desactiverBalise(){
		active = false;
	}
	
	
}
