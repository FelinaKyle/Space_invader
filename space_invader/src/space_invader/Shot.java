package space_invader;



public class Shot extends Character
{

       boolean goUp;
       boolean visible;
       boolean activo;

    public Shot(int x, int y, int w)
    {
        super(x, y, w);
        goUp = true;
        visible = true;
        activo= true;
    }

    

    
   }

