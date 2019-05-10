package com.dzakdzaks.ta_spp.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePayment{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("payment")
	private List<PaymentItem> payment;

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

	public void setPayment(List<PaymentItem> payment){
		this.payment = payment;
	}

	public List<PaymentItem> getPayment(){
		return payment;
	}
}