import uiContainers.GameWindow;
import core.IState;
import core.IStatistics;


public class MyTestState implements IState {
	
	private GameWindow _testFrame;

	public final static String Name = "MyTestState";
	
	public MyTestState(GameWindow testFrame){
		_testFrame = testFrame;
	}
	
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s: onEnter", Name));
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s: onExit", Name));		
	}

	@Override
	public void update(IStatistics statsObject) {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s: update", Name));
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s: render", Name));		
	}

}
