package it.polito.tdp.ufo.model;

public class StateResult {
	
	private State stato1;
	private State stato2;
	private int cnt;
		
	public StateResult(State stato1, State stato2, int cnt) {
		super();
		this.stato1 = stato1;
		this.stato2 = stato2;
		this.cnt = cnt;
	}
	public State getStato1() {
		return stato1;
	}
	public void setStato1(State stato1) {
		this.stato1 = stato1;
	}
	public State getStato2() {
		return stato2;
	}
	public void setStato2(State stato2) {
		this.stato2 = stato2;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
		
}
