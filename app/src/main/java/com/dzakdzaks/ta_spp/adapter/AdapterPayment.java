package com.dzakdzaks.ta_spp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.response.PaymentItem;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.List;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ViewAbsensiHolder> {


    private List<PaymentItem> users;
    private Context context;
    UserSession session;

    public AdapterPayment(List<PaymentItem> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAbsensiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_payment, viewGroup, false);
        session = new UserSession(context);
        return new ViewAbsensiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAbsensiHolder holder, int i) {
        holder.tvTiket.setText(users.get(i).getNamaPayment());
        holder.tvValue.setText("Rp." + users.get(i).getValuePayment());
        holder.tvCat.setText("*" + users.get(i).getCatatanPayment());
    }

    @Override
    public int getItemCount() {
        if (users == null) {
            return 0;
        } else {
            return users.size();
        }
    }

    public static class ViewAbsensiHolder extends RecyclerView.ViewHolder {
        TextView tvTiket, tvValue, tvCat, tvStatus1, tvStatus2, tvStatus3;
        CardView cardView;

        public ViewAbsensiHolder(View v) {
            super(v);
            tvTiket = v.findViewById(R.id.tvTiket);
            tvValue = v.findViewById(R.id.tvValue);
            tvCat = v.findViewById(R.id.tvCat);
//            tvStatus1 = v.findViewById(R.id.tvStatus1);
//            tvStatus2 = v.findViewById(R.id.tvStatus2);
//            tvStatus3 = v.findViewById(R.id.tvStatus3);
            cardView = v.findViewById(R.id.cardTiket);
        }
    }
}
