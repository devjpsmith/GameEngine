package uiContainers;


import java.awt.Graphics;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
	public final static int FRAME_WIDTH = 1000;
	public final static int FRAME_HEIGHT = 600;
	
	private GamePanel _p;
	
	public void render(){
		_p.repaint();
	}
	
	public GamePanel getPanel(){
		return _p;
	}

	public GameWindow(){
	      super("Test Frame");
	      prepareGUI();	      
   }
	
	public int getMaxRight(int offset){
		return getWidth()- offset - (getInsets().left + getInsets().right);
	}
	
	public int getMaxBottom(int offset){
		return getHeight()- offset - (getInsets().top + getInsets().bottom);
	}

   private void prepareGUI(){
	   setSize(FRAME_WIDTH,FRAME_HEIGHT);
	   setResizable(false);
	   setLocationRelativeTo(null);
	   setDefaultCloseOperation(EXIT_ON_CLOSE);
	   _p = new GamePanel();
	   
	   
	   setContentPane(_p);
	   setVisible(true);
   }  
   

}
