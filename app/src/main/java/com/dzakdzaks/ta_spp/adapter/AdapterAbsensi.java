package com.dzakdzaks.ta_spp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.DetailSiswaActivity;
import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.response.User;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.List;

public class AdapterAbsensi extends RecyclerView.Adapter<AdapterAbsensi.ViewAbsensiHolder> {


    private List<User> users;
    private Context context;
    UserSession session;

    public AdapterAbsensi(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAbsensiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_siswa, viewGroup, false);
        session = new UserSession(context);
        return new ViewAbsensiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAbsensiHolder holder, final int position) {
        holder.tvTiket.setText(users.get(position).getNamaUser());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailSiswaActivity.class);
                i.putExtra("id", users.get(position).getIdUser());
                i.putExtra("nis", users.get(position).getNisUser());
                i.putExtra("no_urut", users.get(position).getNoUrutUser());
                i.putExtra("nama", users.get(position).getNamaUser());
                i.putExtra("pass", users.get(position).getPassUser());
                i.putExtra("ttl", users.get(position).getTtlUser());
                i.putExtra("kelas", users.get(position).getKelasUser());
                i.putExtra("jk", users.get(position).getJkUser());
                i.putExtra("agama", users.get(position).getAgamaUser());
                i.putExtra("alamat", users.get(position).getAlamatUser());
                i.putExtra("no_telpon", users.get(position).getNoTelponUser());
                i.putExtra("sakit", users.get(position).getSakitUser());
                i.putExtra("izin", users.get(position).getIzinUser());
                i.putExtra("alpha", users.get(position).getAlphaUser());
                i.putExtra("role", users.get(position).getRoleUser());
                i.putExtra("pay1", users.get(position).getPayment1());
//                i.putExtra("pay2", users.get(position).getPayment2());
//                i.putExtra("pay3", users.get(position).getPayment3());
                i.putExtra("valpay1", users.get(position).getValuePayment1());
//                i.putExtra("valpay2", users.get(position).getValuePayment2());
//                i.putExtra("valpay3", users.get(position).getValuePayment3());
                Toast.makeText(context, users.get(position).getNisUser() + " clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });
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
        TextView tvTiket, tvKeberangkatan, tvHarga, tvStatus1, tvStatus2, tvStatus3;
        CardView cardView;

        public ViewAbsensiHolder(View v) {
            super(v);
            tvTiket = v.findViewById(R.id.tvTiket);
//            tvKeberangkatan = v.findViewById(R.id.tvKeberangkatan);
//            tvHarga = v.findViewById(R.id.tvHarga);
//            tvStatus1 = v.findViewById(R.id.tvStatus1);
//            tvStatus2 = v.findViewById(R.id.tvStatus2);
//            tvStatus3 = v.findViewById(R.id.tvStatus3);
            cardView = v.findViewById(R.id.cardTiket);
        }
    }
}
