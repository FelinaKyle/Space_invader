package space_invader;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.FontMetrics; codigo para test
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.image.*;

public class Game_Board extends JPanel implements Runnable, MouseListener{
	
	/**
	 * generado automaticamente para eliminar un low warning
	 */
	private static final long serialVersionUID = 1133140955136597235L;
	//variables
	boolean ingame = true;// indica si el programa de juego esta en marcha
    private Dimension d; // dimensiones del panel
    static int WIDTH = 500;// entero que representa el ancho
    static int HEIGHT =500;// entero que representa el alto
    int x= 0;////marcador
    BufferedImage img;// imagen
    String msn ;// mensaje 
    private Thread animator;//
    Player player;
   ArrayList<Alien> aliens= new ArrayList<Alien>();// array de aliens
    ArrayList<Shot> disparos =new ArrayList<Shot>();// array de disparos
    
    //constructor
    public Game_Board() {
    	
    	addKeyListener(new TAdapter());// quien recibe y actua tras evento de teclado
    	addMouseListener(this);// quien recibe y actua tras evvento de raton
    	setFocusable(true);// revisar api
    	d= new Dimension(WIDTH, HEIGHT);// inidcializamos la variable dimension
    	
    	//inicializamos Character
    	player = new Player(WIDTH/2, HEIGHT-60,5);//inicializamos personaje, pendiente de revisar la localizacion y velocidad	
    		
    	//inicializamos los aliens
    	int ax = 10;// x para la aparicion del alien, podemos hacerlo random
        int ay = 10;// lo mismo pero y
    	for (int i=0; i<10; i++) {
    		aliens.add(new Alien(ax,ay,2));// creamos aliens
    		ax += 40;// vamos cambiando la localizacion de los alien
    		if (i==4) { // cambiamo s de fila
    		ax = 10;
    		ay +=40;
    		}
    		
    	}
    	
    	setBackground(Color.black);// fijamos el fondo de pantalla de color negro
    	
    	/* try{ 
    	 * img = ImageIO.read(this.getClass().getResource("mount.jpg"); revisar el archivo que vaya a ir aqui
    	 * } catch (IOException e){
    	 * System.out.println("Imagen no localizada");
    	 * system.exit(1);
    	 * }
    	 */
    	if (animator == null || !ingame) { //
    		animator= new Thread(this);//
    		animator.start();//
    	}
    	setDoubleBuffered(true);//
    }
    
   public void paint(Graphics g) {
	   
	   super.paint(g);// llamamos a la funcion de la superclase que vamos a sobreescribir
	   
	   g.setColor(Color.black);//elegimos color
	   g.fillRect(0,0,d.width,d.height);//y figura
	   
	   //protagonista
	   g.setColor(Color.red);//elegimos color
	   g.fillRect(player.x, player.y,20,20);//y figura
	   if (player.moveRight== true) {// si nos ordenan mover a la derecha
		   if (player.x > WIDTH+10) {// comprobamos si estamos al borde
			   player.moveRight= false; //si es asi no nos movemos
		   }else {// si no esta al borde*/
			   player.x += player.speed; // nos movemos
		  }
	   }
	   if (player.moveLeft==true) {
		  
		   if (player.x < 10) {
			   player.moveLeft= false;
		   }else {
			   player.x -= player.speed;
		   }
	   }
	   moveAliens() ; // llamamos a la funcion de movimiento de los aliens
	   for (Alien alien: aliens) {// loop para la creacion de los graficos de los aliens
   		   g.setColor(Color.green);
		   g.fillRect(alien.x,alien.y,30,30); // rectangulos de 30x30
   		}
	   //disparos realizados
	   moveshots();// movemos los disparos
	  if (disparos.size()!= 0) {
		 g.setColor(Color.white);
		 for (Shot shot: disparos) {	   
		      if (shot.visible) {
		    	  g.fillRect(shot.x, shot.y, 2,2 );
		      }
		 }
	  }      
	   chechCollisions();// comprobamos si hay colisiones en el juego
	   
	   Font small = new Font("Helvetica", Font.BOLD, 14); // escogemos parametros de letra
	  // FontMetrics metr = this.getFontMetrics(small);// para obtener el tamaño ocupado de pantalla nor servira para cuadrar el mensaje en la pantalla
	  if (ingame) {
		  msn = ( "you have " + x + " points");
	  }else {
		  msn = "You win";
	  }
	   g.setColor(Color.white);//color de la letra
	   g.setFont(small);// indicamos la fuente para la letra
	   g.drawString(msn, 10, d.height-60); // escribimos el texto, revisar la localizacion
   
   
   if (ingame) {
	   //g.drawImage(img,0,0,200,200,null);
   }
	   Toolkit.getDefaultToolkit().sync();
	   g.dispose();
   }
   
   
   // funcion de movimiento de los aliens
   public void moveAliens() {
	   
	   for(Alien alien: aliens) {// recorremos el array de aliens
		   if (alien.moveLeft==true) {// si su movimiento es a la izquierda
		   alien.x -= alien.speed;// movemos pixeles a la izquierda
		   }
		   if (alien.moveRight==true) {// si su movimiento es a la derecha
			   alien.x += alien.speed;// movemos pixeles a la derecha
			   }
		   }
		   for (Alien alien: aliens) {
		   if (alien.x> WIDTH){// si esta al borde del tablero
			   for(Alien alien1: aliens) {
				 alien1.moveLeft= true;  // nos movemos hacia la izquierda por lo que
				 alien1.moveRight = false;// cambiamos la direccion
				 alien1.y +=5;// y desciende
			   }
			   
		   }
		   if (alien.x <0){ // si esta justo al borde izquierdo
			   for(Alien alien2: aliens) {
					 alien2.moveRight= true;  // nos movemos a la derecha
					 alien2.moveLeft= false; // cambiamos la direccion
					 alien2.y +=5;// y desciende
				   }
			   
		   }
		   }  
	   }
   
   public void chechCollisions() {// revisar
	   if (ingame) {
	   if (aliens.size()==0) { // si no quedan aliens 
			  ingame = false;// salimos del juego
	     } else  if (disparos.size()>0) {// si hay disparos en juego
	    	 
	    	 	for(int i = 0 ; i< aliens.size(); i++) {// recorremos el array de aliens
	    	 		Alien alien = aliens.get(i);// auxiliar para almacenar la instancia que estamos examinando
	    	 		for (int j = 0 ; j< disparos.size(); j++) { //y el de disparos		
	    	 			
	    	 			Shot shot = disparos.get(j);// auxiliar para almacenar la instancia que estamos examinando
	    	 			if (shot.activo) {
	    	 				if (((shot.x> alien.x)&&shot.x<(alien.x+30)) && ((shot.y<(alien.y+30))&& shot.y>alien.y)){ //si el disparo toca un alien
	    	 					x ++;// aumenta el marcador
	    	 					shot.activo= false;//invalidamos el disparo, 
	    	 					shot.visible= false; // hacemos invisible el disparo
	    	 					aliens.remove(alien);   // eliminamos el alien
	    	 					}
		   
	    	 				}
	    	 			}	
	    	 		}
	    	 	}
	   
	   for(int i =0; i<aliens.size(); i++) {// recorremos el array de aliens
	    	Alien alien = aliens.get(i);// auxiliar para almacenar el alien que estamos analizando  		
		    if (alien.y+30>=player.y) {//si los alien llegan a donde esta el jugador
		    	 int cuenta = 10;// cuenta de diez para cierre
		    	 		for (int k = 0; k< cuenta; k++) {//comenzamos cuenta atrás
		    	 				msn = "YOU LOST " + k;// mensaje de juego perdido
		    	 					ingame = false;// salimos del juego
				 		}
		    	 		if (alien.y >= player.y) {// si los aliens llegan a donde el jugador
					
		    	 			System.exit(0);// cerramos consola
		    	 		}
		    	}	
		    }
   }}
   
   public  void moveshots(){

	   for(Shot shot:disparos) {
       if(shot.y<0){
           shot.goUp=false;
           }

       if(shot.goUp) {
           shot.y -= shot.speed;
	   	}
      
	   }
   }
  

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	//	int x = e.getX(); codigo para test
	//	int y = e.getY(); codigo para test
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		//long timeDiff, sleep;
		//long beforeTime = System.currentTimeMillis();
		int animationDelay =5;
		long time = System.currentTimeMillis();
		
		while (true) {
			// spriteManager.update();codigo para test
			repaint();
			try {
			time += animationDelay;
					Thread.sleep(Math.max(0, time-System.currentTimeMillis()));
			}catch (InterruptedException e) {
				System.out.println(e);
				
			}
		}
		
		
	}
	
	private class TAdapter extends KeyAdapter{
		
		public void keyReleased(KeyEvent e) {
			//System.out.println(e.getKeyCode()); codigo para test
			//msn = "Tecla pulsada: " + e.getKeyCode();codigo para test
			int key = e.getKeyCode();
			if (key == 39 || key ==37) {
			player.moveLeft = false;
			player.moveRight = false;
			}
		}
		public void keyPressed(KeyEvent e) {	
			int key = e.getKeyCode();
			if (key ==39) {
				player.moveRight = true;	
				
			}
			
			if (key ==37) {
				player.moveLeft = true;	
				
			}
			
			if (key == 38) {
				disparos.add(new Shot(player.x+10, player.y-10, 15));
				
			}
		}
	}
}
