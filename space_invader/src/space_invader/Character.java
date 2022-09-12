package space_invader;

public class Character {
	
	//variables
	int x;//posicion en x
	int y;// posicion en y
	int speed;//pixeles que se avanza
	
	//constructor
	
	public Character() {}
	public Character(int x, int y, int speed) {
		
		this.x= x;//asignamos valores
		this.y= y;
		this.speed = speed;
	}

}
