package com.dzakdzaks.ta_spp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.fragment.AbsensiFragment;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.ResponseCRUDSiswa;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSiswaActivity extends AppCompatActivity {

    @BindView(R.id.txt_error)
    TextView txtError;
    @BindView(R.id.tvNisUser)
    TextView tvNisUser;
    @BindView(R.id.tvNameUser)
    TextView tvNameUser;
    @BindView(R.id.tvNoUrut)
    TextView tvNoUrut;
    @BindView(R.id.tvAgama)
    TextView tvAgama;
    @BindView(R.id.tvKelas)
    TextView tvKelas;
    @BindView(R.id.tvJk)
    TextView tvJk;
    @BindView(R.id.tvNotelp)
    TextView tvNotelp;
    @BindView(R.id.tvAlamat)
    TextView tvAlamat;
    @BindView(R.id.tvTtl)
    TextView tvTtl;
    @BindView(R.id.tvSakit)
    TextView tvSakit;
    @BindView(R.id.tvIzin)
    TextView tvIzin;
    @BindView(R.id.tvAlpha)
    TextView tvAlpha;
    @BindView(R.id.layout_ticket)
    LinearLayout layoutTicket;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.btn_tunggakan)
    Button btnTunggakan;

    UserSession session;
    GlobalVariable globalVariable;

    String id, idPayment, nis, noUrut, nama, pass, ttl, kelas, jk, agama, pay, valpay, catpay;
    String alamat, noTelp, sakit, izin, alpha, role, pay1, valPay1, catPay1;

    EditText inputNis, inputNama, inputNoUrut, inputTTL, inputAgama, inputAlamat, inputNoTelpon, inputSakit, inputIzin, inputAlpha;
    Spinner spinnerKelas, spinnerJK;

    ListView listPay, listValPay;
    TextView totalPayment;
    ArrayList<String> myListPay;
    ArrayList<String> myListValPay;
    ArrayList<Integer> myListResultPay;
    int sum;

    String strInputNis, strInputNama, strInputNoUrut, strInputPay, strInputTTL, strInputAgama, strInputAlamat, strInputNoTelpon, strInputSakit, strInputIzin, strInputAlpha;

    String itemKelas, itemJks;
    String kelas7A = "VII A";
    String kelas7B = "VII B";
    String kelas8A = "VIII A";
    String kelas8B = "VIII B";
    String kelas9A = "IX A";
    String kelas9B = "IX B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_siswa);
        ButterKnife.bind(this);

        session = new UserSession(this);
        globalVariable = new GlobalVariable();

        setView();

    }

    private void setView() {

        if (session.getSpRole().equals("Siswa")) {
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            btnTunggakan.setVisibility(View.GONE);
        }


        id = getIntent().getStringExtra("id");
        idPayment = getIntent().getStringExtra("id_payment");
        nis = getIntent().getStringExtra("nis");
        noUrut = getIntent().getStringExtra("no_urut");
        nama = getIntent().getStringExtra("nama");
        pass = getIntent().getStringExtra("pass");
        ttl = getIntent().getStringExtra("ttl");
        kelas = getIntent().getStringExtra("kelas");
        jk = getIntent().getStringExtra("jk");
        agama = getIntent().getStringExtra("agama");
        alamat = getIntent().getStringExtra("alamat");
        noTelp = getIntent().getStringExtra("no_telpon");
        sakit = getIntent().getStringExtra("sakit");
        izin = getIntent().getStringExtra("izin");
        alpha = getIntent().getStringExtra("alpha");
        role = getIntent().getStringExtra("role");
        pay1 = getIntent().getStringExtra("pay1");
        valPay1 = getIntent().getStringExtra("valpay1");
        catPay1 = getIntent().getStringExtra("catvalpay1");
        pay = getIntent().getStringExtra("payment");
        valpay = getIntent().getStringExtra("value_payment");
        catpay = getIntent().getStringExtra("catatan_payment");

        if (session.getSpNis().equals(nis)) {
            btnTunggakan.setVisibility(View.VISIBLE);
        }

        tvNisUser.setText(nis);
        tvNameUser.setText(nama);
        tvNoUrut.setText(noUrut);
        tvKelas.setText(kelas);
        tvTtl.setText(ttl);
        tvJk.setText(jk);
        tvAgama.setText(agama);
        tvAlamat.setText(alamat);
        tvNotelp.setText(noTelp);
        tvSakit.setText(sakit + " kali");
        tvIzin.setText(izin + " kali");
        tvAlpha.setText(alpha + " kali");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEditSiswa();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailSiswaActivity.this);
                alert.setTitle("Delete");
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin ingin menghapus Siswa " + nama + "?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSiswa();
                    }
                });
                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });

        btnTunggakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void dialogEditSiswa() {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_edit_siswa, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Edit Siswa " + nama);
        alert.setIcon(R.drawable.ic_person_black_24dp);

        inputNis = dialogView.findViewById(R.id.input_nis);
        inputNama = dialogView.findViewById(R.id.input_nama);
        inputNoUrut = dialogView.findViewById(R.id.input_no_urut);
        spinnerKelas = dialogView.findViewById(R.id.spinner_kelas);
        spinnerJK = dialogView.findViewById(R.id.spinner_jk);
        inputTTL = dialogView.findViewById(R.id.input_ttl);
        inputAgama = dialogView.findViewById(R.id.input_agama);
        inputAlamat = dialogView.findViewById(R.id.input_alamat);
        inputNoTelpon = dialogView.findViewById(R.id.input_no_telp);
        inputSakit = dialogView.findViewById(R.id.input_sakit);
        inputIzin = dialogView.findViewById(R.id.input_izin);
        inputAlpha = dialogView.findViewById(R.id.input_alpha);

        inputNis.setText(nis);
        inputNama.setText(nama);
        inputNoUrut.setText(noUrut);
        inputTTL.setText(ttl);
        inputAgama.setText(agama);
        inputAlamat.setText(alamat);
        inputNoTelpon.setText(noTelp);
        inputSakit.setText(sakit);
        inputIzin.setText(izin);
        inputAlpha.setText(alpha);

        List<String> kelass = new ArrayList<String>();
        kelass.add(kelas7A);
        kelass.add(kelas7B);
        kelass.add(kelas8A);
        kelass.add(kelas8B);
        kelass.add(kelas9A);
        kelass.add(kelas9B);

        ArrayAdapter<String> spinnerKelasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, kelass);
        spinnerKelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKelas.setAdapter(spinnerKelasAdapter);
        if (kelas != null) {
            int spinnerPosition = spinnerKelasAdapter.getPosition(kelas);
            spinnerKelas.setSelection(spinnerPosition);
        }
        spinnerKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemKelas = parent.getItemAtPosition(position).toString();
                if (itemKelas.equals(kelas7A)) {
                    strInputPay = "4";
                } else if (itemKelas.equals(kelas7B)) {
                    strInputPay = "4";
                } else if (itemKelas.equals(kelas8A)) {
                    strInputPay = "5";
                } else if (itemKelas.equals(kelas8B)) {
                    strInputPay = "5";
                } else if (itemKelas.equals(kelas9A)) {
                    strInputPay = "6";
                } else if (itemKelas.equals(kelas9B)) {
                    strInputPay = "6";
                }
                Toast.makeText(parent.getContext(), "Selected: " + strInputPay, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> jks = new ArrayList<String>();
        jks.add("L");
        jks.add("P");

        ArrayAdapter<String> spinnerjkAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, jks);
        spinnerjkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJK.setAdapter(spinnerjkAdapter);
        if (jk != null) {
            int spinnerPosition = spinnerjkAdapter.getPosition(jk);
            spinnerJK.setSelection(spinnerPosition);
        }
        spinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemJks = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                postEditSiswa();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }

    private void postEditSiswa() {
        strInputNis = inputNis.getText().toString();
        strInputNama = inputNama.getText().toString();
        strInputNoUrut = inputNoUrut.getText().toString();
        strInputTTL = inputTTL.getText().toString();
        strInputAgama = inputAgama.getText().toString();
        strInputAlamat = inputAlamat.getText().toString();
        strInputNoTelpon = inputNoTelpon.getText().toString();
        strInputSakit = inputSakit.getText().toString();
        strInputIzin = inputIzin.getText().toString();
        strInputAlpha = inputAlpha.getText().toString();

        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseCRUDSiswa> call = apiInterface.editSiswa(id, strInputPay, strInputNis, strInputNoUrut, strInputNama, itemKelas, itemJks, strInputTTL, strInputAgama, strInputAlamat, strInputNoTelpon, strInputSakit, strInputIzin, strInputAlpha);
        call.enqueue(new Callback<ResponseCRUDSiswa>() {
            @Override
            public void onResponse(Call<ResponseCRUDSiswa> call, Response<ResponseCRUDSiswa> response) {
                String msg = response.body().getMsg();
                if (response.isSuccessful()) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCRUDSiswa> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed edit siswa", Toast.LENGTH_SHORT).show();
                Log.d("kampret lah", t.toString());
            }
        });
    }

    private void deleteSiswa() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseCRUDSiswa> call = apiInterface.deleteSiswa(id);
        globalVariable.showProgressBar(progressBar, this);
        call.enqueue(new Callback<ResponseCRUDSiswa>() {
            @Override
            public void onResponse(Call<ResponseCRUDSiswa> call, Response<ResponseCRUDSiswa> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, DetailSiswaActivity.this);
                    Toast.makeText(DetailSiswaActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    globalVariable.hideProgressBar(progressBar, DetailSiswaActivity.this);
                    Toast.makeText(DetailSiswaActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCRUDSiswa> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, DetailSiswaActivity.this);
                Toast.makeText(DetailSiswaActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_tunggak, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tunggakan Siswa " + nama);
        alert.setIcon(R.drawable.ic_person_black_24dp);

        listPay = dialogView.findViewById(R.id.rvTunggak);
        listValPay = dialogView.findViewById(R.id.rvTunggak1);
        totalPayment = dialogView.findViewById(R.id.payment_total_value);

        myListPay = new ArrayList<String>(Arrays.asList(pay1.split(",")));
        myListPay.add(pay);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                myListPay);
        listPay.setAdapter(arrayAdapter);

        myListValPay = new ArrayList<String>(Arrays.asList(valPay1.split(",")));
        myListValPay.add(valpay);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                myListValPay);
        listValPay.setAdapter(arrayAdapter1);

        // convert arraylist string ke arraylist integer
        myListResultPay = globalVariable.getIntegerArray(myListValPay);
        //menjumlah dari value payment
        sum = 0;
        for (int i = 0; i < myListResultPay.size(); i++) {
            sum += myListResultPay.get(i);
            totalPayment.setText("Rp." + sum);
        }

        alert.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.show();
    }
}
