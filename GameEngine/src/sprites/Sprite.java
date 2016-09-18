package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import core.IDrawable;

public class Sprite extends Drawable implements IDrawable
{	
	public final static int DIRECTION_RIGHT = 0;
	public final static int DIRECTION_UP_AND_RIGHT = 45;
	public final static int DIRECTION_UP = 90;
	public final static int DIRECTION_UP_AND_LEFT = 135;
	public final static int DIRECTION_LEFT = 180;
	public final static int DIRECTION_DOWN_AND_LEFT = 225;
	public final static int DIRECTION_DOWN = 270;
	public final static int DIRECTION_DOWN_AND_RIGHT = 315;
	
	protected boolean mIsMoving; 
	protected float mSpeed;
	protected int mDirection;	
	
	private ArrayList<BufferedImage> _sourceImageList = new ArrayList<BufferedImage>();	
	private float _x;
	private float _y;
	
	public int getWidth(){
		return mWidth;
	}
	
	public int getHeight(){
		return mHeight();
	}
	
	public float getX(){
		return _x;
	}
	
	public float getY(){
		return _y;
	}
	
	public void setX(float x){
		_x = x;
	}
	
	public void setY(float y){
		_y = y;
	}
	
	public void setDirection(int d){
		mDirection = d;
	}
	
	public void addSourceImage(String sourcePath){
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(sourcePath);
			_sourceImageList.add(ImageIO.read(input));
		}catch(IOException e){
			
		}
	}
	
	public void clearSourceImages(){
		_sourceImageList = new ArrayList<BufferedImage>();
	}
	
	public Sprite(String sourceFileName){
		
		if(sourceFileName != null && !sourceFileName.isEmpty()){
			addSourceImage(sourceFileName);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		int x = (int) Math.round(_x);
		int y = (int) Math.round(_y);
		int sourceX = getSourceX();
		int sourceY = getSourceY();
		// iterate through the list of source Images and render each
		for(Iterator<BufferedImage> i = _sourceImageList.iterator(); i.hasNext();){
			g.drawImage(i.next(), x, y, x + mWidth, y + mHeight, sourceX, sourceY, sourceX + mWidth, sourceY + mHeight, null);
		}
		
	}
	
	
}
