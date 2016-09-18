package sprites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class represents the player's character
 * @author James
 *
 */
public class PlayerSprite extends Sprite implements KeyListener{

	
	private final static int WIDTH = 32;				// the width for the source sprite
	private final static int HEIGHT = 48;				// the height for the source sprite
	private final static int MOVE_TICKER_MAX = 10;		// determines when the move animation frame should be advanced
	private final static float VELOCITY = 1.5f;			// determine the sprite Speed.velocity				
	
	// keyboard controls
	private final static int KEYCODE_UP = 38;
	private final static int KEYCODE_DOWN = 40;
	private final static int KEYCODE_RIGHT = 39;
	private final static int KEYCODE_LEFT = 37;
	private boolean _keyUpPressed = false;		// determines if the Up key is depressed
	private boolean _keyDownPressed = false;	// determines if the Down key is depressed
	private boolean _keyRightPressed = false;	// determines if the Right key is depressed
	private boolean _keyLeftPressed = false;	// determines if the Left key is depressed
	
	
	private final static String _femaleFileSource = "ninja_f.png";	// the file with female char art
	private final static String _maleFileSource = "images/steampunk_m11.png";	// the file with male char art
	
	public final static int MALE = 0;					// specifies male image file to be used
	public final static int FEMALE = 1;					// specifies female image file to be used		
	
	private int _xBoundry;			// the maximum X coordinate, the player must remain below this value
	private int _yBoundry;			// the maximum Y coordinate, the player must remain below this value
	private int _xMin;				// the minimum X coordinate, the player must remain above this value
	private int _yMin;				// the minimum Y coordinate, the player must remain above this value
	private boolean _xBlockedRight;	// determines if the player can travel right
	private boolean _xBlockedLeft;	// determines if the player can travel left
	private boolean _yBlockedUp;	// determines if the player can travel up
	private boolean _yBlockedDown;	// determines if the player can travel down
	private long _moveTicker;		// ticks while the player is moving, to time movement animations
	private boolean _advanceFrameR; // true if the animation frame should be advanced right; if false - left
	
	
	
	
	/**
	 * ctor - parameterless ctor will automatically instantiate a female character
	 */
	public PlayerSprite(){
		this(FEMALE);
	}
	
	/**
	 * ctor
	 * @param gender - PlayerSprite.MALE OR PlayerSprite.FEMALE 
	 */
	public PlayerSprite(int gender) {
		super((gender == MALE ? _maleFileSource : _femaleFileSource)); // pass in the image source to the base ctor
		mWidth = WIDTH;
		mHeight = HEIGHT;
		// set an initial neutral stance, facing the user
		mGid = 1;
		mSourceTilesAcross = 4;
		// set the sprite movement speed
		mSpeed = new Speed();
	}
	
	/**
	 * ctor 
	 * @param gender - PlayerSprite.MALE OR PlayerSprite.FEMALE 
	 * @param xBoundry - the max X coordinate of the frame the sprite is to be rendered in
	 * @param yBoundry - the max Y coordinate of the frame the sprite is to be rendered in
	 */
	public PlayerSprite(int gender, int xBoundry, int yBoundry){
		//super((gender == MALE ? maleFileSource : femaleFileSource)); // pass in the image source to the base ctor
		this(gender);
		_xBoundry = xBoundry - mWidth;
		_yBoundry = yBoundry - mHeight;
	}
	
	public int getWidth(){
		return mWidth;
	}
	
	public int getHeight(){
		return mHeight;
	}
		
	@Override
	public void update(){
		super.update();
		
		float newX = 0f;
		float newY = 0f;
		
		newX = mX + ((mSpeed.getXDirection() * mSpeed.getXv()));
		newY = mY + ((mSpeed.getYDirection() * mSpeed.getYv()));
		
		// set each point, within the bounds of the frame	
		setX(Math.max(Math.min(newX, _xBoundry), 0));
		setY(Math.max(Math.min(newY, _yBoundry), 0));
		
		// update the image
		if(mSpeed.getXv() != 0 || mSpeed.getYv() != 0){
						
			_moveTicker++;
			if(_moveTicker == 1) {
				_moveTicker = 2; // from now on, while we move, our min ticker value will be 2
				if (mSpeed.getXDirection() != Speed.DIRECTION_NONE){
					mGid = (mSpeed.getXDirection() == Speed.DIRECTION_LEFT ? 5 : 9);
				}
				if (mSpeed.getYDirection() != Speed.DIRECTION_NONE){
					mGid = (mSpeed.getYDirection() == Speed.DIRECTION_DOWN ? 1 : 13);
				}
				mGid++; // start off on the right foot :)
			}
			if(_moveTicker >= MOVE_TICKER_MAX + 2){ // add 2 to the timer, since we'll start at 2
				// time to reset the ticker and advance our frame
				_moveTicker = 2; // start here to keep from stuttering
				if((mGid % mSourceTilesAcross) == 0) 
					mGid-= mSourceTilesAcross;
				
				mGid++;
			}
		}
		else
			_moveTicker = 0;
	}

	
	/**
	 * block the player from moving in the specified direction
	 * @param up - if true, the player cannot move up
	 * @param right - if true, the player cannot move right
	 * @param down - if true, the player cannot move down
	 * @param left - if true, the player cannot move left
	 */
	public void block(boolean up, boolean right, boolean down, boolean left){
		if(up) _yBlockedUp = true;
		if(right) _xBlockedRight = true;
		if(down) _yBlockedDown = true;
		if(left) _xBlockedLeft = true;
		
	}
	
	/**
	 * set the boundries in which the sprite is able to move
	 * @param xBoundry
	 * @param yBoundry
	 */
	public void setBoundries(int xMin, int yMin, int xBoundry, int yBoundry){
		_xMin = xMin;
		_yMin = yMin;
		_xBoundry = xBoundry - mWidth;
		_yBoundry = yBoundry - mHeight;
	}
	
	/**
	 * method to reset the sprite after moving
	 */
	public void stopMoving(){
		mIsMoving = false;
		_moveTicker = 0;
		 // we're stopped, so set the stance to neutral
		if(mGid <=4) mGid = 1;
		else if(mGid >=5 && mGid <=8) mGid = 5;
		else if(mGid >=9 && mGid <=12) mGid = 9;
		else mGid = 13;
		
	}
	
	
	public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()){
			case KEYCODE_UP: {
				_keyUpPressed = true;
			}; break;
			case KEYCODE_DOWN:  {
				_keyDownPressed = true;
			};  break;
			case KEYCODE_RIGHT:  {
				_keyRightPressed = true;
			};  break;
			case KEYCODE_LEFT:  {
				_keyLeftPressed = true;
			};  break;
				default:
			}
			determineDirection();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()){
		case KEYCODE_UP: {
			_keyUpPressed = false;
		}; break;
		case KEYCODE_DOWN:  {
			_keyDownPressed = false;
		};  break;
		case KEYCODE_RIGHT:  {
			_keyRightPressed = false;
		};  break;
		case KEYCODE_LEFT:  {
			_keyLeftPressed = false;
		};  break;
			default:
		}
		determineDirection();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void determineDirection(){
		boolean up = false;
		boolean down = false;
		boolean right = false;
		boolean left = false;

					
		// if no keys are depressed, notify the state the user has stopped sending input to move
		// 	or if all keys are depressed, notify the state to stop
		if((!_keyUpPressed && !_keyDownPressed && !_keyRightPressed && !_keyLeftPressed)
			|| (_keyUpPressed && _keyDownPressed && _keyRightPressed && _keyLeftPressed)){
			mSpeed.setXDirection(Speed.DIRECTION_NONE);
			mSpeed.setYDirection(Speed.DIRECTION_NONE);
			mSpeed.setXv(0);
			mSpeed.setYv(0);
		}		
		else {
			if(_keyUpPressed && !_keyDownPressed){
				// up input
				up = true;
			}
			else if(_keyDownPressed && !_keyUpPressed){
				// down input
				down = true;
			}
			if(_keyRightPressed && !_keyLeftPressed){
				// right input
				right = true;
			}
			else if(_keyLeftPressed && !_keyRightPressed){
				// left input
				left = true;
			}
			
			
			if(up){
				mSpeed.setYDirection(Speed.DIRECTION_UP);
				mSpeed.setYv(VELOCITY);
			}				
			else if(down){
				mSpeed.setYDirection(Speed.DIRECTION_DOWN);
				mSpeed.setYv(VELOCITY);
			}				
			else{
				mSpeed.setYDirection(Speed.DIRECTION_NONE);
				mSpeed.setYv(0);
			}				
			
			if(left){
				mSpeed.setXDirection(Speed.DIRECTION_LEFT);
				mSpeed.setXv(VELOCITY);
			}				
			else if(right){
				mSpeed.setXDirection(Speed.DIRECTION_RIGHT);
				mSpeed.setXv(VELOCITY);
			}				
			else {
				mSpeed.setXDirection(Speed.DIRECTION_NONE);
				mSpeed.setXv(0);
			}				
		}
	}
	
}
