package space_invader;

import javax.swing.JFrame;

public class Start extends JFrame
{
  /**
	 *generada automaticamente para eliminar low warning
	 */
	private static final long serialVersionUID = -6738047313303922287L;


public Start() {
	 // completamos la pantalla y dam,os parametros
	  // añadimos panel
	  add(new Game_Board());
	  // añadimos titulo
	  setTitle("Space Invaders");
	  // operacion de cierre
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  // damos tamaño a la pantalla
	  setSize(560,500);
	  	  //donde aparecera al iniciarse el programa
	  setLocationRelativeTo(null);
	  // impedimos la modificacion de tamaño
	  setResizable(false);
	  // Hacemos visible
	  setVisible(true);
	  
  }
	
  
  public static void main(String[] arg) {
	  new Start();
	  
  }
	
}
