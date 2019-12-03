package com.uniminuto.restnumbers.model;

import java.util.ArrayList;

public class ResponseNumbers {
	private String ip;
	private ArrayList<Integer> nums;
	
	
	public ResponseNumbers(String ip, ArrayList<Integer> nums) {
		super();
		this.ip = ip;
		this.nums = nums;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public ArrayList<Integer> getNums() {
		return nums;
	}
	public void setNums(ArrayList<Integer> nums) {
		this.nums = nums;
	}
	
	
}
