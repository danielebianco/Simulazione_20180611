package it.polito.tdp.ufo.model;

import java.time.Year;

public class AnnoCount {
	
	private Year anno;
	private int cnt;
	
	public AnnoCount(Year anno, int cnt) {
		super();
		this.anno = anno;
		this.cnt = cnt;
	}
	
	public Year getAnno() {
		return anno;
	}
	public void setAnno(Year anno) {
		this.anno = anno;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	@Override
	public String toString() {
		return anno.toString() + " (" + cnt + ")" ;
	}
	
	
	
}
