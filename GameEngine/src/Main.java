import uiContainers.GameWindow;
import core.StateStack;


public class Main {

	public static void main(String[] args) {

		// this is the test console for our Game Engine
		GameWindow testFrame = new GameWindow();
		
		// Create the StateStack and add the States we will need
		StateStack stateStack = new StateStack();
		
		// create our states
		MyTestState testState = new MyTestState(testFrame);
		LocalMapState mainMenuState = new LocalMapState(testFrame);
		
		stateStack.add(LocalMapState.Name, mainMenuState);
		stateStack.add(MyTestState.Name, testState);
				
		stateStack.push(LocalMapState.Name);
		
		GraphicsThread gt = new GraphicsThread(stateStack);
		gt.setRunning(true);
		gt.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//stateStack.push(MyTestState.Name);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//stateStack.pop();
	}

}
