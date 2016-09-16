package core;

import java.util.*;


public class StateStack {
	Map<String, IState> _states = new HashMap<String, IState>();
	Stack<IState> _stack = new Stack<IState>();
	
	public void update(IStatistics statsObject){
		IState state = _stack.peek();
		state.update(statsObject);
	}
	
	public void render(){
		IState state = _stack.peek();
		state.render();
	}
	
	public void push(String name){
		IState state = _states.get(name);
		_stack.push(state);
		state.onEnter();
	}
	
	public IState pop(){
		if(!_stack.empty()){
			_stack.peek().onExit();
			return _stack.pop();
		}
		return null;
	}
	
	public void add(String name, IState state){
		if(!_states.containsKey(name))
		_states.put(name, state);
	}
	
	public void remove(String name){
		if(_states.containsKey(name))
			_states.remove(name);
	}
}
