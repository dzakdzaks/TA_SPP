package com.dzakdzaks.ta_spp.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser{

	@SerializedName("msg")
	private String msg;

	@SerializedName("error")
	private String error;

	@SerializedName("user")
	private List<User> user;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	public void setUser(List<User> user){
		this.user = user;
	}

	public List<User> getUser(){
		return user;
	}
}