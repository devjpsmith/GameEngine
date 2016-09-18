import java.awt.event.*;

import core.IState;
import core.IStatistics;
import core.StateObject;
import sprites.PlayerSprite;
import sprites.Sprite;
import sprites.TiledMap;
import uiContainers.GameWindow;


public class LocalMapState extends StateObject implements IState, KeyListener {
	
	public final static String Name = "MainMenuState";
	
	private final static int KEYCODE_UP = 38;
	private final static int KEYCODE_DOWN = 40;
	private final static int KEYCODE_RIGHT = 39;
	private final static int KEYCODE_LEFT = 37;
	
	private LocalMapState _state;				// the local reference to the MainMenuState
	private boolean _keyUpPressed = false;		// determines if the Up key is depressed
	private boolean _keyDownPressed = false;	// determines if the Down key is depressed
	private boolean _keyRightPressed = false;	// determines if the Right key is depressed
	private boolean _keyLeftPressed = false;	// determines if the Left key is depressed
	
	// this will be our ticker to time the game to; it's incremented with every update
	private long _ticker = 0;	
	private PlayerSprite _playerSprite;						// the user's on-screen character
	private int _inputDirection = Sprite.DIRECTION_DOWN; 	// the default player direction, facing toward the user
	private boolean _receivingMoveInput = false;			// determines if the user is holding a move key down	
	private TiledMap _map;
	
	/**
	 * ctor
	 * @param window - a reference to the window this state will be rendered in
	 */
	public LocalMapState(GameWindow window){
		super(window);
		window.addKeyListener(this);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		// TODO: create a fade in effect
		// create a new player character
		_playerSprite = new PlayerSprite(PlayerSprite.MALE, mWindow.getMaxRight(0), mWindow.getMaxBottom(0));
		// set the player in the center of the window
		_playerSprite.setX((mWindow.getMaxRight(0) / 2) - _playerSprite.getWidth() / 2);
		//_playerSprite.setY((mWindow.getMaxBottom(0) / 2) - _playerSprite.getHeight() / 2);
		_playerSprite.setY(0f);
		_map = new TiledMap();
	}

	@Override
	public void onExit() {
		super.onExit();
		// TODO create a fade-out effect
	}

	@Override
	public void update(IStatistics statsObject) {
		// update our ticker before anything else
		_ticker++;
		super.update(statsObject);
		// update our character
		_playerSprite.setDirection(_inputDirection);
		if(_receivingMoveInput){
			// we need to update the player pos
			_playerSprite.move();
			// get player space
			
			// check each corner for what gid it's in on the tile map
			
		}
		else{
			_playerSprite.stopMoving();
		}
	}

	@Override
	public void render() {		
		super.render();
		mWindow.getPanel().addDrawable(_map);
		mWindow.getPanel().addDrawable(_playerSprite);
		mWindow.repaint();
	}
	
	// sets the player-character direction
	public void setPlayerDirection(int d){
		_inputDirection = d;
		// this is called when a key is pressed, so set the char moving
		_receivingMoveInput = true;
	}
	
	// tells the state that a key-up event has occurred
	public void onNoMovementInput(){
		_receivingMoveInput = false;
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
		if(!_keyUpPressed && !_keyDownPressed && !_keyRightPressed && !_keyLeftPressed) onNoMovementInput();
		// if all keys are depressed, notify the state to stop
		else if(_keyUpPressed && _keyDownPressed && _keyRightPressed && _keyLeftPressed) onNoMovementInput();
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
			
			if(up && (right || left)){
				// traveling up diagonally
				setPlayerDirection(right ? Sprite.DIRECTION_UP_AND_RIGHT : Sprite.DIRECTION_UP_AND_LEFT);
			}
			else if(down && (right || left)){
				// traveling down diagonally
				setPlayerDirection(right ? Sprite.DIRECTION_DOWN_AND_RIGHT : Sprite.DIRECTION_DOWN_AND_LEFT);
			}
			else if(up)
				setPlayerDirection(Sprite.DIRECTION_UP);
			else if(down)
				setPlayerDirection(Sprite.DIRECTION_DOWN);
			else if(left)
				setPlayerDirection(Sprite.DIRECTION_LEFT);
			else if(right)
				setPlayerDirection(Sprite.DIRECTION_RIGHT);
			else
				onNoMovementInput();
		}
	}
	

}
