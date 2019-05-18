package com.dzakdzaks.ta_spp.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseUser{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("user")
	private List<User> user;

	public void setResult(int result){
		this.result = result;
	}

	public int getResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setUser(List<User> user){
		this.user = user;
	}

	public List<User> getUser(){
		return user;
	}
}