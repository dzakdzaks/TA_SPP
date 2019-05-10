package com.dzakdzaks.ta_spp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.adapter.AdapterAbsensi;
import com.dzakdzaks.ta_spp.adapter.AdapterPayment;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.global.EmptyRecyclerView;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.PaymentItem;
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
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linearPusat)
    LinearLayout linearPusat;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.card)
    CardView card;

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
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
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

}
