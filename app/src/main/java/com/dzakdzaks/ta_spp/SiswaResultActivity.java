package com.dzakdzaks.ta_spp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.ResponseCRUDSiswa;
import com.dzakdzaks.ta_spp.response.ResponseLogin;
import com.dzakdzaks.ta_spp.response.User;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiswaResultActivity extends AppCompatActivity {


    @BindView(R.id.tvNisUser)
    TextView tvNisUser;
    @BindView(R.id.tvNameUser)
    TextView tvNameUser;
    @BindView(R.id.txt_error)
    TextView tvError;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layout_ticket)
    LinearLayout linearLayoutTicket;
    @BindView(R.id.tvNoUrut)
    TextView tvNoUrut;
    @BindView(R.id.tvTtl)
    TextView tvTtl;
    @BindView(R.id.tvKelas)
    TextView tvKelas;
    @BindView(R.id.tvJk)
    TextView tvJk;
    @BindView(R.id.tvAgama)
    TextView tvAgama;
    @BindView(R.id.tvAlamat)
    TextView tvAlamat;
    @BindView(R.id.tvNotelp)
    TextView tvNotelp;
    @BindView(R.id.tvSakit)
    TextView tvSakit;
    @BindView(R.id.tvIzin)
    TextView tvIzin;
    @BindView(R.id.tvAlpha)
    TextView tvAlpha;
    @BindView(R.id.btn_tunggakan)
    Button btnTunggakan;

    UserSession session;

    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.delete)
    ImageView delete;

    GlobalVariable globalVariable;

    String id, idPayment, nis, noUrut, namaSiswa, pass, ttl, kelas, jk, agama, pay, valpay, catpay;
    String alamat, noTelp, sakit, izin, alpha, role, pay1, valPay1, catPay1;

    EditText inputNis, inputNama, inputNoUrut, inputTTL, inputAgama, inputAlamat, inputNoTelpon, inputSakit, inputIzin, inputAlpha, inputNamaPayment, inputValPayment;
    Spinner spinnerKelas, spinnerJK;

    ListView listPay, listValPay;
    TextView totalPayment;
    ArrayList<String> myListPay;
    ArrayList<String> myListValPay;
    ArrayList<Integer> myListResultPay;
    int sum;

    String strInputNis, strInputNama, strInputNoUrut, strInputPay, strInputTTL, strInputAgama, strInputAlamat, strInputNoTelpon, strInputSakit, strInputIzin, strInputAlpha, strInputNamaPayment, strInputValPayment;

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
        setContentView(R.layout.activity_siswa_result);
        ButterKnife.bind(this);

        globalVariable = new GlobalVariable();
        session = new UserSession(this);
        role = session.getSpRole();

        setView();


    }

    private void setView() {

        String barcode = getIntent().getStringExtra("nis");

        // close the activity in case of empty barcode
        if (TextUtils.isEmpty(barcode)) {
            Toast.makeText(getApplicationContext(), "QRCODE is empty!", Toast.LENGTH_LONG).show();
            finish();
        }

        // search the barcode
        searchBarcode(barcode);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEditSiswa();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SiswaResultActivity.this);
                alert.setTitle("Delete");
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin ingin menghapus Siswa " + namaSiswa + "?");
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

    private void searchBarcode(String barcode) {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseLogin> call = apiInterface.getUserByNis(barcode);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    User siswa = response.body().getUser();
                    if (siswa != null) {
                        nis = siswa.getNisUser();
                        id = siswa.getIdUser();
                        idPayment = siswa.getIdPayment();
                        namaSiswa = siswa.getNamaUser();
                        noUrut = siswa.getNoUrutUser();
                        ttl = siswa.getTtlUser();
                        kelas = siswa.getKelasUser();
                        jk = siswa.getJkUser();
                        agama = siswa.getAgamaUser();
                        alamat = siswa.getAlamatUser();
                        noTelp = siswa.getNoTelponUser();
                        sakit = siswa.getSakitUser();
                        izin = siswa.getIzinUser();
                        alpha = siswa.getAlphaUser();
                        pay1 = siswa.getPayment1();
                        valPay1 = siswa.getValuePayment1();
                        catPay1 = siswa.getCatPayment1();
                        pay = siswa.getNamaPayment();
                        valpay = siswa.getValuePayment();
                        catpay = siswa.getCatatanPayment();
                        tvNisUser.setText(nis);
                        tvNameUser.setText(namaSiswa);
                        tvNoUrut.setText(noUrut);
                        tvTtl.setText(ttl);
                        tvKelas.setText(kelas);
                        tvJk.setText(jk);
                        tvAgama.setText(agama);
                        tvAlamat.setText(alamat);
                        tvNotelp.setText(noTelp);
                        tvSakit.setText(sakit + " kali");
                        tvIzin.setText(izin + " kali");
                        tvAlpha.setText(alpha + " kali");
                    } else {
                        showNoTicket();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                showNoTicket();
            }
        });
    }

    private void dialogEditSiswa() {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_edit_siswa, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Edit Siswa " + namaSiswa);
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
        inputNamaPayment = dialogView.findViewById(R.id.input_nama_tunggakan);
        inputValPayment = dialogView.findViewById(R.id.input_value_tunggakan);

        inputNis.setText(nis);
        inputNama.setText(namaSiswa);
        inputNoUrut.setText(noUrut);
        inputTTL.setText(ttl);
        inputAgama.setText(agama);
        inputAlamat.setText(alamat);
        inputNoTelpon.setText(noTelp);
        inputSakit.setText(sakit);
        inputIzin.setText(izin);
        inputAlpha.setText(alpha);
        inputNamaPayment.setText(pay1);
        inputValPayment.setText(valPay1);


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
        strInputNamaPayment = inputNamaPayment.getText().toString();
        strInputValPayment = inputValPayment.getText().toString();

        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseCRUDSiswa> call = apiInterface.editSiswa(id, strInputPay, strInputNis, strInputNoUrut, strInputNama, itemKelas, itemJks, strInputTTL, strInputAgama, strInputAlamat, strInputNoTelpon, strInputSakit, strInputIzin, strInputAlpha, strInputNamaPayment, strInputValPayment);
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
                    globalVariable.hideProgressBar(progressBar, SiswaResultActivity.this);
                    Toast.makeText(SiswaResultActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    globalVariable.hideProgressBar(progressBar, SiswaResultActivity.this);
                    Toast.makeText(SiswaResultActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCRUDSiswa> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, SiswaResultActivity.this);
                Toast.makeText(SiswaResultActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_tunggak, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tunggakan Siswa " + namaSiswa);
        alert.setIcon(R.drawable.ic_person_black_24dp);

        listPay = dialogView.findViewById(R.id.rvTunggak);
        listValPay = dialogView.findViewById(R.id.rvTunggak1);
        totalPayment = dialogView.findViewById(R.id.payment_total_value);

        myListPay = new ArrayList<String>(Arrays.asList(pay1.split(",")));
        myListPay.add(pay);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                myListPay){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
        listPay.setAdapter(arrayAdapter);

        myListValPay = new ArrayList<String>(Arrays.asList(valPay1.split(",")));
        myListValPay.add(valpay);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                myListValPay){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
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

    private void showNoTicket() {
        tvError.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        linearLayoutTicket.setVisibility(View.GONE);
    }
}
