package sprites;
/**
 * This class represents the player's character
 * @author James
 *
 */
public class PlayerSprite extends Sprite{

	
	private final static int WIDTH = 32;				// the width for the source sprite
	private final static int HEIGHT = 48;				// the height for the source sprite
	private final static int YORIENTATION_BACK = 144;		// source Y coord for back view
	private final static int YORIENTATION_RIGHT = 96;	// source Y coord for right facing view
	private final static int YORIENTATION_FRONT = 0;	// source Y coord for front view
	private final static int YORIENTATION_LEFT = 48;	// source Y coord for left view
	private final static int XORIENTATION_LEFT = 32;		// source X coord for left foot
	private final static int XORIENTATION_MID = 64;		// source X coord for neutral position
	private final static int XORIENTATION_RIGHT = 96;	// source X coord for right foot
	private final static int MOVE_TICKER_MAX = 10;		// determines when the move animation frame should be advanced
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
		mSourceX = XORIENTATION_MID;
		mSourceY = YORIENTATION_FRONT;
		// set the sprite movement speed
		mSpeed = 1.5f;
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
	public void setDirection(int d){
		// call the base method
		super.setDirection(d);
		// for this sprite, we want to change the orientation as well as direction
		switch(d){
			case Sprite.DIRECTION_UP_AND_RIGHT:
			case Sprite.DIRECTION_UP_AND_LEFT:
			case Sprite.DIRECTION_UP: mSourceY = YORIENTATION_BACK; break;		// show back view
			case Sprite.DIRECTION_DOWN_AND_RIGHT:
			case Sprite.DIRECTION_DOWN_AND_LEFT:
			case Sprite.DIRECTION_DOWN: mSourceY = YORIENTATION_FRONT; break;	// show front view
			case Sprite.DIRECTION_LEFT: mSourceY = YORIENTATION_LEFT; break;		// show left view
			case Sprite.DIRECTION_RIGHT: mSourceY = YORIENTATION_RIGHT; break;	// show right view	
		}
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
	 * method called when the player is moving.
	 * The PlayerSprite will use it's current direction and speed to determine how to move
	 */
	public void move(){

		mIsMoving = true;
		float newX = getX();
		float newY = getY();
		// switch on the direction
		// check to make sure nothing is blocking the way
		// 	if we're able to move, unblock the opposite direction
		switch(mDirection){
			case Sprite.DIRECTION_RIGHT: {
				if(!_xBlockedRight){
					newX += (1 * mSpeed);
					_xBlockedLeft = false;
				}			
			}; break;
			case Sprite.DIRECTION_LEFT: {
				if(!_xBlockedLeft){
					newX -= (1 * mSpeed); 
					_xBlockedRight = false;
				}
			}; break;
			case Sprite.DIRECTION_DOWN: {
				if(!_yBlockedDown){
					newY += (1 * mSpeed);
					_yBlockedUp = false;
				}
			}; break;
			case Sprite.DIRECTION_UP: {
				if(!_yBlockedUp){
					newY -= (1 * mSpeed);
					_yBlockedDown = false;
				}				
			}; break;
			case Sprite.DIRECTION_UP_AND_LEFT: {
				if(!_yBlockedUp){
					newY -= (1 * mSpeed);
					_yBlockedDown = false;
				}				
				if(!_xBlockedLeft){
					newX -= (1 * mSpeed); 
					_xBlockedRight = false;
				}
			}; break;
			case Sprite.DIRECTION_UP_AND_RIGHT: {
				if(!_yBlockedUp){
					newY -= (1 * mSpeed);
					_yBlockedDown = false;
				}	
				if(!_xBlockedRight){
					newX += (1 * mSpeed);
					_xBlockedLeft = false;
				}	
			}; break;
			case Sprite.DIRECTION_DOWN_AND_LEFT: {
				if(!_yBlockedDown){
					newY += (1 * mSpeed);
					_yBlockedUp = false;
				}	
				if(!_xBlockedLeft){
					newX -= (1 * mSpeed); 
					_xBlockedRight = false;
				}
			}; break;
			case Sprite.DIRECTION_DOWN_AND_RIGHT: {
				if(!_yBlockedDown){
					newY += (1 * mSpeed);
					_yBlockedUp = false;
				}		
				if(!_xBlockedRight){
					newX += (1 * mSpeed);
					_xBlockedLeft = false;
				}	
			}; break;
		}
		// set each point, within the bounds of the frame			
		setX(Math.max(Math.min(newX, _xBoundry), 0));
		setY(Math.max(Math.min(newY, _yBoundry), 0));
		_moveTicker++;
		if(_moveTicker == 1) {
			_moveTicker = 2; // from now on, while we move, our min ticker value will be 2
			mSourceX = XORIENTATION_RIGHT; // start off on the right foot :)
		}
		if(_moveTicker >= MOVE_TICKER_MAX + 2){ // add 2 to the timer, since we'll start at 2
			// time to reset the ticker and advance our frame
			_moveTicker = 2; // start here to keep from stuttering
			if(_advanceFrameR){
				// advance source image frame to the right
				if(mSourceX == XORIENTATION_LEFT){
					mSourceX = XORIENTATION_MID;
				}
				else {
					mSourceX = XORIENTATION_RIGHT;
					// time to turn around 
					_advanceFrameR = false;
				}
			}
			else{
				// advance source image frame to the left
				if(mSourceX == XORIENTATION_RIGHT){
					mSourceX = XORIENTATION_MID;
				}
				else {
					mSourceX = XORIENTATION_LEFT;
					// time to turn around
					_advanceFrameR = true;
				}
			}
		}
	}
	
	/**
	 * method to reset the sprite after moving
	 */
	public void stopMoving(){
		mIsMoving = false;
		_moveTicker = 0;
		mSourceX = XORIENTATION_MID; // we're stopped, so set the stance to neutral
	}
}
