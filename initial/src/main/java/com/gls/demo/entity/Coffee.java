package com.gls.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
	private String id;
    private String name;
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
}