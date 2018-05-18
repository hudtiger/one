package com.gls.demo.entity;

import org.springframework.data.annotation.Id;

public class Inventory {
	@Id
	private String id;
	private String item;
	private Integer qty;
	private String status;
	private Size size;
	private String[] tags;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public Inventory(String id, String item, Integer qty, String status, Size size, String[] tags) {
		super();
		this.id = id;
		this.item = item;
		this.qty = qty;
		this.status = status;
		this.size = size;
		this.tags = tags;
	}
	
//	{"id":"5afbe1b6f9162daff83f15a0","item":"journal","qty":25,"size":{"h":14.0,"uom":"cm","w":21.0},"status":"A","tags":["blank","red"]}
//	{"id":"5afbe1b6f9162daff83f15a1","item":"notebook","qty":50,"size":{"h":8.5,"uom":"in","w":11.0},"status":"A","tags":["red","blank"]}
//	{"id":"5afbe1b6f9162daff83f15a4","item":"postcard","qty":45,"size":{"h":10.0,"uom":"cm","w":15.25},"status":"A","tags":["blue"]}
}
