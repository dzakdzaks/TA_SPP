package com.dzakdzaks.ta_spp.api;

import com.dzakdzaks.ta_spp.response.ResponseCRUDPayment;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPengumuman;
import com.dzakdzaks.ta_spp.response.ResponseCRUDSiswa;
import com.dzakdzaks.ta_spp.response.ResponseLogin;
import com.dzakdzaks.ta_spp.response.ResponsePayment;
import com.dzakdzaks.ta_spp.response.ResponsePengumuman;
import com.dzakdzaks.ta_spp.response.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("user_login.php")
    Call<ResponseLogin> requestlogin(
            @Field("nis_user") String nis,
            @Field("pass_user") String pass
    );

    @FormUrlEncoded
    @POST("user_get_by_nis.php")
    Call<ResponseLogin> getUserByNis(
            @Field("nis_user") String nis
    );

    @FormUrlEncoded
    @POST("user_get.php")
    Call<ResponseUser> getUserByKelas(
            @Field("kelas_user") String kelas
    );

    @FormUrlEncoded
    @POST("payment_get.php")
    Call<ResponsePayment> getPayment(
            @Field("nis_user") String nis
    );

    @FormUrlEncoded
    @POST("pengumuman_get.php")
    Call<ResponsePengumuman> getPengumuman(
            @Field("nis_user") String nis
    );

    @FormUrlEncoded
    @POST("user_delete.php")
    Call<ResponseCRUDSiswa> deleteSiswa(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("user_add.php")
    Call<ResponseCRUDSiswa> addSiswa(
            @Field("id_payment") String id,
            @Field("nis") String nis,
            @Field("no_urut") String no_urut,
            @Field("nama") String nama,
            @Field("kls") String kls
    );

    @FormUrlEncoded
    @POST("user_edit.php")
    Call<ResponseCRUDSiswa> editSiswa(
            @Field("id") String id,
            @Field("id_payment") String idPay,
            @Field("nis") String nis,
            @Field("no_urut") String no_urut,
            @Field("nama") String nama,
            @Field("kls") String kls,
            @Field("jk") String jk,
            @Field("ttl") String ttl,
            @Field("agama") String agama,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("sakit") String sakit,
            @Field("izin") String izin,
            @Field("alpha") String alpha,
            @Field("pay") String asd,
            @Field("valpay") String asda
    );

    @FormUrlEncoded
    @POST("pengumuman_add.php")
    Call<ResponseCRUDPengumuman> addPengumuman(
            @Field("title") String nis,
            @Field("isi") String no_urut
    );

    @FormUrlEncoded
    @POST("pengumuman_edit.php")
    Call<ResponseCRUDPengumuman> editPengumuman(
            @Field("id") String id,
            @Field("title") String nis,
            @Field("isi") String no_urut
    );

    @FormUrlEncoded
    @POST("pengumuman_delete.php")
    Call<ResponseCRUDPengumuman> deletePengumuman(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("pengumuman_add.php")
    Call<ResponseCRUDPayment> addPayment(
            @Field("nama") String nama,
            @Field("value") String value,
            @Field("cat") String cat
    );


    @FormUrlEncoded
    @POST("payment_delete.php")
    Call<ResponseCRUDPayment> deletePayment(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("payment_edit.php")
    Call<ResponseCRUDPayment> editPayment(
            @Field("id_payment") String id,
            @Field("nama_payment") String nis,
            @Field("value_payment") String no_urut,
            @Field("catatan_payment") String asd
    );



}
