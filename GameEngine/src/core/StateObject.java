package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import sprites.TiledMap;
import uiContainers.GamePanel;
import uiContainers.GameWindow;


public abstract class StateObject implements IState {
	private final static boolean DEBUG_MODE = true;
	
	private double _fps = 0;
	private long _skippedFrames = 0;
	private int _inputX = 0;
	private int _inputY = 0;
	
	protected GamePanel mPanel;
	protected GameWindow mWindow;	
	
	public void setInputX(int X){
		_inputX = X;
	}
	
	public void setInputY(int Y){
		_inputY = Y;
	}
	
	public final static String Name = "MainMenuState";
	
	public StateObject(GameWindow window){
		mWindow = window;
		mPanel = window.getPanel();
	}

	@Override
	public void onEnter() {
		
	}

	@Override
	public void onExit() {
		
	}

	@Override
	public void update(IStatistics statObject) {
		_fps = statObject.getFps();
		_skippedFrames = statObject.getTotalFramesSkipped();
	}

	@Override
	public void render() {
		// start each render with a clean slate
		mPanel.clearDrawables();
		mPanel.addDrawable(new IDrawable(){

			@Override
			public void draw(Graphics g) {
				// fill the background black
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, GameWindow.FRAME_WIDTH, GameWindow.FRAME_HEIGHT);				
			}
			
		});
		if(DEBUG_MODE){
			// add the FPS counter to the top left
			mPanel.addDrawable( new IDrawable(){

				@Override
				public void draw(Graphics g) {
					g.setFont(new Font("Ariel", 1, 10));
					g.setColor(Color.WHITE);
					g.drawString(String.format("FPS: %1$.2f", _fps) , 10, 20);
				}
					
			});
			// add the skipped frames counter right under the FPS 
			mPanel.addDrawable( new IDrawable(){

				@Override
				public void draw(Graphics g) {
					g.setFont(new Font("Ariel", 1, 10));
					g.setColor(Color.WHITE);
					g.drawString(String.format("Skipped Frames: %s", _skippedFrames) , 10, 30);
				}
					
			});
		}
		
	
		
	}
}
