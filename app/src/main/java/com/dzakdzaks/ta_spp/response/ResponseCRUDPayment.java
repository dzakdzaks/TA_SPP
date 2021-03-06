package com.dzakdzaks.ta_spp.response;

import com.google.gson.annotations.SerializedName;

public class ResponseCRUDPayment{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

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
}