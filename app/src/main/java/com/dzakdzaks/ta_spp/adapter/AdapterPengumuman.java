package com.dzakdzaks.ta_spp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.DetailSiswaActivity;
import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.fragment.PengumumanFragment;
import com.dzakdzaks.ta_spp.response.PaymentItem;
import com.dzakdzaks.ta_spp.response.PengumumanItem;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPengumuman;
import com.dzakdzaks.ta_spp.response.ResponseCRUDSiswa;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPengumuman extends RecyclerView.Adapter<AdapterPengumuman.ViewAbsensiHolder> {


    private List<PengumumanItem> users;
    private Context context;
    UserSession session;

    EditText inputTitle, inputIsi;
    String strId, strTitle, strIsi;

    public AdapterPengumuman(List<PengumumanItem> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAbsensiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_ancnmt, viewGroup, false);
        session = new UserSession(context);
        return new ViewAbsensiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAbsensiHolder holder, final int i) {
        if (session.getSpRole().equals("Siswa")) {
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        }
        holder.tvTiket.setText(users.get(i).getTitle());
        holder.tvValue.setText(users.get(i).getIsi());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete");
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin ingin menghapus pengumuman " + users.get(i).getTitle() +"?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiInterface apiInterface = ApiClient.getInstance();
                        Call<ResponseCRUDPengumuman> call = apiInterface.deletePengumuman(users.get(i).getId());

                        call.enqueue(new Callback<ResponseCRUDPengumuman>() {
                            @Override
                            public void onResponse(Call<ResponseCRUDPengumuman> call, Response<ResponseCRUDPengumuman> response) {
                                if (response.isSuccessful()){
                                    PengumumanFragment absensiFragment = new PengumumanFragment();
                                    FragmentTransaction ft =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment, absensiFragment);
                                    ft.commit();
                                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseCRUDPengumuman> call, Throwable t) {
                                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, users.get(i).getId(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.form_add_pengumuman, null);
                alert.setView(dialogView);
                alert.setCancelable(true);
                alert.setTitle("Edit Pengumuman " + users.get(i).getTitle());
                alert.setIcon(R.drawable.ic_message_black_24dp);

                inputTitle = dialogView.findViewById(R.id.input_title);
                inputIsi = dialogView.findViewById(R.id.input_isi);

                inputTitle.setText(users.get(i).getTitle());
                inputIsi.setText(users.get(i).getIsi());

                strId = users.get(i).getId();

                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        strTitle = inputTitle.getText().toString();
                        strIsi = inputIsi.getText().toString();

                        ApiInterface apiInterface = ApiClient.getInstance();
                        Call<ResponseCRUDPengumuman> call = apiInterface.editPengumuman(strId,strTitle, strIsi);
                        call.enqueue(new Callback<ResponseCRUDPengumuman>() {
                            @Override
                            public void onResponse(Call<ResponseCRUDPengumuman> call, Response<ResponseCRUDPengumuman> response) {
                                String msg = response.body().getMsg();
                                if (response.isSuccessful()) {
                                    PengumumanFragment absensiFragment = new PengumumanFragment();
                                    FragmentTransaction ft =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment, absensiFragment);
                                    ft.commit();
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseCRUDPengumuman> call, Throwable t) {
                                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
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
        TextView tvTiket, tvValue, tvHarga, tvStatus1, tvStatus2, tvStatus3;
        ImageView edit, delete;
        CardView cardView;

        public ViewAbsensiHolder(View v) {
            super(v);
            tvTiket = v.findViewById(R.id.tvTiket);
            tvValue = v.findViewById(R.id.tvValue);
            edit = v.findViewById(R.id.edit);
            delete = v.findViewById(R.id.delete);
//            tvHarga = v.findViewById(R.id.tvHarga);
//            tvStatus1 = v.findViewById(R.id.tvStatus1);
//            tvStatus2 = v.findViewById(R.id.tvStatus2);
//            tvStatus3 = v.findViewById(R.id.tvStatus3);
            cardView = v.findViewById(R.id.cardTiket);
        }
    }

}