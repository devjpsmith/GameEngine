import java.awt.event.*;

import core.IState;
import core.IStatistics;
import core.StateObject;
import sprites.PlayerSprite;
import sprites.Sprite;
import sprites.TiledMap;
import uiContainers.GameWindow;


public class LocalMapState extends StateObject implements IState {
	
	public final static String Name = "MainMenuState";
		
	private LocalMapState _state;				// the local reference to the MainMenuState
	
	// this will be our ticker to time the game to; it's incremented with every update
	private long _ticker = 0;	
	private PlayerSprite _playerSprite;						// the user's on-screen character	
	private TiledMap _map;
	private GameWindow _window;								// holds a reference to the GameWindow we're rendering
	
	/**
	 * ctor
	 * @param window - a reference to the window this state will be rendered in
	 */
	public LocalMapState(GameWindow window){
		super(window);
		_window = window;
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
		
		_window.addKeyListener(_playerSprite);
		
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
		_playerSprite.update();
	}

	@Override
	public void render() {		
		super.render();
		mWindow.getPanel().addDrawable(_map);
		mWindow.getPanel().addDrawable(_playerSprite);
		mWindow.repaint();
	}
	

}
