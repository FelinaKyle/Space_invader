package space_invader;

public class Player extends Character {
	//variables
	boolean moveRight;
	boolean moveLeft;

	public Player(int x, int y, int speed) {

		super(x, y, speed);// constructor de la super clase
		
		moveLeft= false;// inicializamos todas la variables
		moveRight = false;
	}

}
