package com.gls.demo.entity;

public class Size {
	private double h;
	private double w;
	private String uom;
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Size(double h, double w, String uom) {
		super();
		this.h = h;
		this.w = w;
		this.uom = uom;
	}
}
