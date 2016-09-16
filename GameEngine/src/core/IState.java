package core;



public interface IState {
	public void onEnter();
	public void onExit();
	public void update(IStatistics statistical);
	public void render();
}
