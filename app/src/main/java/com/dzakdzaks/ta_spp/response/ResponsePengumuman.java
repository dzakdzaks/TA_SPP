package com.dzakdzaks.ta_spp.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePengumuman{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("pengumuman")
	private List<PengumumanItem> pengumuman;

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

	public void setPengumuman(List<PengumumanItem> pengumuman){
		this.pengumuman = pengumuman;
	}

	public List<PengumumanItem> getPengumuman(){
		return pengumuman;
	}
}