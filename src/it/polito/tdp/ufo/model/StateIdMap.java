package it.polito.tdp.ufo.model;

import java.util.HashMap;

public class StateIdMap extends HashMap<String,State>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public State get(State state) {
		State old = super.get(state.getState());
		
		if (old != null) {
			return old;
		}
		super.put(state.getState(), state);
		
		return state;
	}
	
}
