package com.dzakdzaks.ta_spp.response;

import com.google.gson.annotations.SerializedName;

public class PaymentItem{

	@SerializedName("id_payment")
	private String idPayment;

	@SerializedName("value_payment")
	private String valuePayment;

	@SerializedName("nama_payment")
	private String namaPayment;

	@SerializedName("created")
	private String created;

	@SerializedName("catatan_payment")
	private String catatanPayment;

	public void setIdPayment(String idPayment){
		this.idPayment = idPayment;
	}

	public String getIdPayment(){
		return idPayment;
	}

	public void setValuePayment(String valuePayment){
		this.valuePayment = valuePayment;
	}

	public String getValuePayment(){
		return valuePayment;
	}

	public void setNamaPayment(String namaPayment){
		this.namaPayment = namaPayment;
	}

	public String getNamaPayment(){
		return namaPayment;
	}

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setCatatanPayment(String catatanPayment){
		this.catatanPayment = catatanPayment;
	}

	public String getCatatanPayment(){
		return catatanPayment;
	}
}