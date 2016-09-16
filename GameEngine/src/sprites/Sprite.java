package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import core.IDrawable;

public class Sprite implements IDrawable
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
	protected String mImageSource;
	protected BufferedImage mSource;
	protected int mWidth;
	protected int mHeight;
	protected int mSourceX;
	protected int mSourceY;
	
	private float _x;
	private float _y;
	
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
	
	public Sprite(String sourceFileName){
		mImageSource = sourceFileName;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(mImageSource);
			mSource = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(mSource != null){
			int x = (int) Math.round(_x);
			int y = (int) Math.round(_y);
			g.drawImage(mSource, x, y, x + mWidth, y + mHeight, mSourceX, mSourceY, mSourceX + mWidth, mSourceY + mHeight, null);
		}	
	}
	
	
}
