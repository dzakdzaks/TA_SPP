package com.dzakdzaks.ta_spp.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.adapter.AdapterAbsensi;
import com.dzakdzaks.ta_spp.adapter.AdapterPayment;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.global.EmptyRecyclerView;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.PaymentItem;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPayment;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPengumuman;
import com.dzakdzaks.ta_spp.response.ResponsePayment;
import com.dzakdzaks.ta_spp.response.ResponseUser;
import com.dzakdzaks.ta_spp.response.User;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PembayaranFragment extends Fragment {


    Unbinder unbinder;

    UserSession session;
    GlobalVariable globalVariable;
    @BindView(R.id.board)
    Button board;
    @BindView(R.id.rvPayment)
    EmptyRecyclerView rvPayment;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.addButton)
    RelativeLayout addButton;

    EditText inputTitle, inputVal, inputCat;
    String title, val, cat;
    public PembayaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pembayaran, container, false);
        unbinder = ButterKnife.bind(this, view);
        session = new UserSession(getActivity());
        globalVariable = new GlobalVariable();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setView(){
        if (session.getSpRole().equals("Siswa")) {
            addButton.setVisibility(View.GONE);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddPay();
            }
        });
        rvPayment.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvPayment.setHasFixedSize(true);
        getPayment();
    }

    private void getPayment() {
        String nis = session.getSpNis();
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponsePayment> call = apiInterface.getPayment(nis);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponsePayment>() {
            @Override
            public void onResponse(Call<ResponsePayment> call, Response<ResponsePayment> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    rvPayment.setVisibility(View.VISIBLE);
                    String msg = response.body().getMsg();
                    List<PaymentItem> pay = response.body().getPayment();
                    AdapterPayment adapterTiket = new AdapterPayment(pay, getActivity());
                    rvPayment.setAdapter(adapterTiket);
                    if (adapterTiket.getItemCount() == 0) {
                        board.setText("Tidak ada Pembayaran");
                    } else {
                        board.setText("Pembayaran");
                    }
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePayment> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogAddPay() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_pay, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Pembayaran");
        alert.setIcon(R.drawable.ic_message_black_24dp);

        inputTitle = dialogView.findViewById(R.id.input_title);
        inputVal = dialogView.findViewById(R.id.input_val);
        inputCat = dialogView.findViewById(R.id.input_cat);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addPay();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    private void addPay() {
        title = inputTitle.getText().toString();
        val = inputVal.getText().toString();
        cat = inputCat.getText().toString();
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseCRUDPayment> call = apiInterface.addPayment(title, val, cat);
        call.enqueue(new Callback<ResponseCRUDPayment>() {
            @Override
            public void onResponse(Call<ResponseCRUDPayment> call, Response<ResponseCRUDPayment> response) {
                String msg = response.body().getMsg();
                if (response.isSuccessful()) {
                    PembayaranFragment pembayaranFragment = new PembayaranFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, pembayaranFragment);
                    ft.commit();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCRUDPayment> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
