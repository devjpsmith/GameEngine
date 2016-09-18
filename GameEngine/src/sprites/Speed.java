package sprites;

public class Speed {

	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_UP = -1;
	public static final int DIRECTION_DOWN = 1;
	public static final int DIRECTION_NONE = 0;
	
	private float _xv;								// the x plane velocity
	private float _yv;								// the y plane velocity
	
	// by default, our directions will == NONE
	private int _xDirection;						// the current x plane direction
	private int _yDirection;						// the current y plane direction
	
	/**
	 * ctor
	 */
	public Speed(){
		_xDirection = DIRECTION_NONE;
		_yDirection = DIRECTION_NONE;
		_xv = 0f;
		_yv = 0f;
	}
	
	/**
	 * ctor
	 * instantiates a Speed object with the specified velocities
	 * @param xv - multiplier for direction
	 * @param yv - multiplier for direction
	 */
	public Speed(float xv, float yv){
		super();
		_xv = Math.abs(xv);
		_yv = Math.abs(yv);
	}
	
	/**
	 * gets the x velocity
	 * @return - x velocity
	 */
	public float getXv(){
		return _xv;
	}
	
	/**
	 * gets the y velocity
	 * @return - y velocity
	 */
	public float getYv(){
		return _yv;
	}
	
	/**
	 * sets the X velocity
	 * @param xv - multiplier for direction
	 */
	public void setXv(float xv){
		_xv = Math.abs(xv);
	}
	
	/**
	 * sets the Y velocity
	 * @param yv - multiplier for direction 
	 */
	public void setYv(float yv){
		_yv = yv;
	}
	
	/**
	 * gets the direction on the X plane
	 * @return - the Speed.xDirection
	 */
	public int getXDirection(){
		return _xDirection;
	}
	
	/**
	 * gets the direction on the Y plane
	 * @return - the Speed.yDirection
	 */
	public int getYDirection(){
		return _yDirection;
	}
	
	/**
	 * sets the direction on the x plane if the argument is valid
	 * @param d - Speed.DIRECTION_RIGHT or Speed.DIRECTION_LEFT
	 */
	public void setXDirection(int d){
		switch(d){
		case DIRECTION_LEFT:
		case DIRECTION_RIGHT: _xDirection = d; break;
		default:
			break;
		
		}
	}
	
	/**
	 * sets the direction on the y plane if the argument is valid
	 * @param d - Speed.DIRECTION_UP or Speed.DIRECTION_DOWN
	 */
	public void setYDirection(int d){
		switch(d){
		case DIRECTION_UP:
		case DIRECTION_DOWN: _yDirection = d; break;
		default:
			break;
		}
	}
	
	/**
	 * toggle the X direction
	 */
	public void toggleXDirection(){
		_xDirection *= -1;
	}
	
	/**
	 * toggle the Y direction
	 */
	public void toggleYDirection(){
		_yDirection *= -1;
	}
	
	
	
	
	
	
}
