package com.dzakdzaks.ta_spp.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dzakdzaks.ta_spp.LoginActivity;

public class UserSession {
    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";

    public static final String SP_ID = "spId";
    public static final String SP_NIS = "spNis";
    public static final String SP_NO_URUT = "spNoUrut";
    public static final String SP_NAME = "spName";
    public static final String SP_PASSWORD = "spPassword";
    public static final String SP_TTL = "spTTL";
    public static final String SP_KELAS = "spKelas";
    public static final String SP_JK = "spJK";
    public static final String SP_AGAMA = "spAgama";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_NO = "spNo";
    public static final String SP_SAKIT = "spSakit";
    public static final String SP_IZIN = "spIzin";
    public static final String SP_ALPHA = "spAlpha";
    public static final String SP_ROLE = "spRole";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    int PRIVATE_MODE = 0;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    Context context;

    public UserSession(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, PRIVATE_MODE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void logout() {
        spEditor.clear();
        spEditor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpId() {
        return sp.getString(SP_ID, "");
    }

    public String getSpName() {
        return sp.getString(SP_NAME, "");
    }

    public String getSpPassword() {
        return sp.getString(SP_PASSWORD, "");
    }

    public String getSpAlamat() {
        return sp.getString(SP_ALAMAT, "");
    }

    public String getSpTtl() {
        return sp.getString(SP_TTL, "");
    }

    public String getSpNo() {
        return sp.getString(SP_NO, "");
    }

    public String getSpRole() {
        return sp.getString(SP_ROLE, "");
    }

    public String getSpNis() {
        return sp.getString(SP_NIS, "");
    }

    public String getSpNoUrut() {
        return sp.getString(SP_NO_URUT, "");    }

    public String getSpKelas() {
        return sp.getString(SP_KELAS, "");
    }

    public String getSpJk() {
        return sp.getString(SP_JK, "");
    }

    public String getSpAgama() {
        return sp.getString(SP_AGAMA, "");
    }

    public String getSpSakit() {
        return sp.getString(SP_SAKIT, "");
    }

    public String getSpIzin() {
        return sp.getString(SP_IZIN, "");
    }

    public  String getSpAlpha() {
        return sp.getString(SP_ALPHA, "");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }


}
