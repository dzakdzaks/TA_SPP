package com.dzakdzaks.ta_spp.response;

import com.google.gson.annotations.SerializedName;

public class PengumumanItem{

	@SerializedName("created")
	private String created;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("isi")
	private String isi;

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}
}