package moteur;

public class Balise {
	
	private Position pos;
	private boolean active;
	private int charges; 
	
	Balise (){
		pos= new Position(0,0);
		active = false;
		charges = 5;
	}
	
	public boolean active(){
		return active;
	}
	
	public Position position(){
		return pos;
	}
	
	public void placerBalise(Position p){
		if(charges>0){
			pos=p;
			active = true;
			charges -= 1;
		}
		
		
	}
	
	public void desactiverBalise(){
		active = false;
	}
	
	
}
