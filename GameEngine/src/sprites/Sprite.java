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
	protected boolean mIsMoving; 
	protected Speed mSpeed;
	
	private ArrayList<BufferedImage> _sourceImageList = new ArrayList<BufferedImage>();	
	protected float mX;
	protected float mY;
	
	public int getWidth(){
		return mWidth;
	}
	
	public int getHeight(){
		return mHeight;
	}
	
	public float getX(){
		return mX;
	}
	
	public float getY(){
		return mY;
	}
	
	public void setX(float x){
		mX = x;
	}
	
	public void setY(float y){
		mY = y;
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
		int x = (int) Math.round(mX);
		int y = (int) Math.round(mY);
		int sourceX = getSourceX();
		int sourceY = getSourceY();
		// iterate through the list of source Images and render each
		for(Iterator<BufferedImage> i = _sourceImageList.iterator(); i.hasNext();){
			g.drawImage(i.next(), x, y, x + mWidth, y + mHeight, sourceX, sourceY, sourceX + mWidth, sourceY + mHeight, null);
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
}
