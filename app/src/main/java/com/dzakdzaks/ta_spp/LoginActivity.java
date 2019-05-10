package com.dzakdzaks.ta_spp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.ResponseLogin;
import com.dzakdzaks.ta_spp.response.User;
import com.dzakdzaks.ta_spp.session.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.admin)
    Button admin;
    @BindView(R.id.btn_login)
    Button btnLogin;

    UserSession session;
    GlobalVariable globalVariable;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setView();

    }

    private void setView() {
        session = new UserSession(this);
        globalVariable = new GlobalVariable();
        if (session.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login();
            }
        });
    }

    private void login() {
        String nis = inputUsername.getText().toString();
        String pass = inputPassword.getText().toString();
        globalVariable.showProgressBar(progressBar, this);
        if (nis.isEmpty()) {
            globalVariable.hideProgressBar(progressBar, this);
            inputUsername.setError("Masih Kosong");
            inputUsername.requestFocus();
        } else if (pass.isEmpty()) {
            globalVariable.hideProgressBar(progressBar, this);
            inputPassword.setError("Masih Kosong");
            inputPassword.requestFocus();
        } else {
            ApiInterface apiInterface = ApiClient.getInstance();
            Call<ResponseLogin> call = apiInterface.requestlogin(nis, pass);
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    String error = response.body().getError();
                    String msg = response.body().getMsg();
                    if (response.isSuccessful()) {
                        globalVariable.hideProgressBar(progressBar, LoginActivity.this);
                        if (error.equals("false")) {
                            User user = response.body().getUser();
                            String id = user.getIdUser();
                            String nis = user.getNisUser();
                            String noUrut = user.getNoUrutUser();
                            String nama = user.getNamaUser();
                            String pass = user.getPassUser();
                            String ttl = user.getTtlUser();
                            String kelas = user.getKelasUser();
                            String jk = user.getJkUser();
                            String agama = user.getAgamaUser();
                            String alamat = user.getAlamatUser();
                            String noTelp = user.getNoTelponUser();
                            String sakit = user.getSakitUser();
                            String izin = user.getIzinUser();
                            String alpha = user.getAlphaUser();
                            String role = user.getRoleUser();

                            System.out.println("nama users : " + nama);

                            session.saveSPString(UserSession.SP_ID, id);
                            session.saveSPString(UserSession.SP_NIS, nis);
                            session.saveSPString(UserSession.SP_NO_URUT, noUrut);
                            session.saveSPString(UserSession.SP_NAME, nama);
                            session.saveSPString(UserSession.SP_PASSWORD, pass);
                            session.saveSPString(UserSession.SP_TTL, ttl);
                            session.saveSPString(UserSession.SP_KELAS, kelas);
                            session.saveSPString(UserSession.SP_JK, jk);
                            session.saveSPString(UserSession.SP_AGAMA, agama);
                            session.saveSPString(UserSession.SP_ALAMAT, alamat);
                            session.saveSPString(UserSession.SP_NO, noTelp);
                            session.saveSPString(UserSession.SP_SAKIT, sakit);
                            session.saveSPString(UserSession.SP_IZIN, izin);
                            session.saveSPString(UserSession.SP_ALPHA, alpha);
                            session.saveSPString(UserSession.SP_ROLE, role);
                            session.saveSPBoolean(UserSession.SP_SUDAH_LOGIN, true);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                        } else {
                            globalVariable.hideProgressBar(progressBar, LoginActivity.this);
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        globalVariable.hideProgressBar(progressBar, LoginActivity.this);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    globalVariable.hideProgressBar(progressBar, LoginActivity.this);
                    Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
