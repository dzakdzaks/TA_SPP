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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.adapter.AdapterPengumuman;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.global.EmptyRecyclerView;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.PengumumanItem;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPengumuman;
import com.dzakdzaks.ta_spp.response.ResponseCRUDSiswa;
import com.dzakdzaks.ta_spp.response.ResponsePengumuman;
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
public class PengumumanFragment extends Fragment {


    @BindView(R.id.board)
    Button board;
    @BindView(R.id.rvAncmnt)
    EmptyRecyclerView rvAncmnt;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.addButton)
    RelativeLayout addButton;
    Unbinder unbinder;

    UserSession session;
    GlobalVariable globalVariable;


    EditText inputTitle, inputIsi;
    String title, isi;

    public PengumumanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengumuman, container, false);
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

    private void setView() {

        if (session.getSpRole().equals("Siswa")) {
            addButton.setVisibility(View.GONE);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddAncmnt();
            }
        });

        rvAncmnt.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvAncmnt.setHasFixedSize(true);
        getAncmnt();
    }

    private void getAncmnt() {
        String nis = session.getSpNis();
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponsePengumuman> call = apiInterface.getPengumuman(nis);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponsePengumuman>() {
            @Override
            public void onResponse(Call<ResponsePengumuman> call, Response<ResponsePengumuman> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<PengumumanItem> pay = response.body().getPengumuman();
                    AdapterPengumuman adapterTiket = new AdapterPengumuman(pay, getActivity());
                    rvAncmnt.setAdapter(adapterTiket);
                    if (adapterTiket.getItemCount() == 0) {
                        board.setText("Tidak ada Pengumuman");
                    } else {
                        board.setText("Pengumuman");
                    }
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengumuman> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogAddAncmnt() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_pengumuman, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Pengumuman");
        alert.setIcon(R.drawable.ic_message_black_24dp);

        inputTitle = dialogView.findViewById(R.id.input_title);
        inputIsi = dialogView.findViewById(R.id.input_isi);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addAncmnt();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    private void addAncmnt() {

        title = inputTitle.getText().toString();
        isi = inputIsi.getText().toString();

        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseCRUDPengumuman> call = apiInterface.addPengumuman(title, isi);
        call.enqueue(new Callback<ResponseCRUDPengumuman>() {
            @Override
            public void onResponse(Call<ResponseCRUDPengumuman> call, Response<ResponseCRUDPengumuman> response) {
                String msg = response.body().getMsg();
                if (response.isSuccessful()) {
                    PengumumanFragment absensiFragment = new PengumumanFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, absensiFragment);
                    ft.commit();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCRUDPengumuman> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
