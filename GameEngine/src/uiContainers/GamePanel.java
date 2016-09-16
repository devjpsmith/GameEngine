package uiContainers;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import core.IDrawable;

public class GamePanel extends JPanel{

	private ArrayList<IDrawable> _drawables;
	
	public GamePanel(){
		_drawables = new ArrayList<IDrawable>();
	}
	
	public void addDrawable(IDrawable d){
		synchronized (_drawables){
			_drawables.add(d);
		}		
	}

	public void removeDrawable(IDrawable d){
		synchronized (_drawables){
			if(_drawables.contains(d))
			_drawables.remove(d);
		}
	}
	
	public void clearDrawables(){
		synchronized (_drawables){
			_drawables = new ArrayList<IDrawable>();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		synchronized (_drawables){	
			if(_drawables != null){
				for(Iterator<IDrawable> i = _drawables.iterator(); i.hasNext();){
					i.next().draw(g);
				}
			}
		}
	}
	
}

