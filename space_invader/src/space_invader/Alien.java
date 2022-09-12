package space_invader;

public class Alien extends Character{

	//variables
		boolean moveRight;
		boolean moveLeft;
		boolean isVisible;

		public Alien(int x, int y, int speed) {

			super(x, y, speed);// constructor de la super clase
			
			moveLeft= false;// inicializamos todas la variables
			moveRight = true;
			isVisible = true;
		}
}
