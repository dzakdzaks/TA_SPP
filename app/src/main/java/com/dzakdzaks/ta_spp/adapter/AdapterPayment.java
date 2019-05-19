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

import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.fragment.PembayaranFragment;
import com.dzakdzaks.ta_spp.fragment.PengumumanFragment;
import com.dzakdzaks.ta_spp.response.PaymentItem;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPayment;
import com.dzakdzaks.ta_spp.response.ResponseCRUDPengumuman;
import com.dzakdzaks.ta_spp.session.UserSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ViewAbsensiHolder> {


    private List<PaymentItem> users;
    private Context context;
    UserSession session;

    EditText inputTitle, inputVal, inputCat;
    String strId, strTitle, strVal, strCat;

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
    public void onBindViewHolder(@NonNull ViewAbsensiHolder holder, final int i) {
        if (session.getSpRole().equals("Siswa")) {
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        }
        holder.tvTiket.setText(users.get(i).getNamaPayment());
        holder.tvValue.setText("Rp." + users.get(i).getValuePayment());
        holder.tvCat.setText("*" + users.get(i).getCatatanPayment());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete");
                alert.setIcon(R.mipmap.ic_launcher_round);
                alert.setMessage("Anda yakin ingin menghapus pembayaran " + users.get(i).getNamaPayment() +"?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiInterface apiInterface = ApiClient.getInstance();
                        Call<ResponseCRUDPayment> call = apiInterface.deletePayment(users.get(i).getIdPayment());

                        call.enqueue(new Callback<ResponseCRUDPayment>() {
                            @Override
                            public void onResponse(Call<ResponseCRUDPayment> call, Response<ResponseCRUDPayment> response) {
                                if (response.isSuccessful()){
                                    PembayaranFragment pembayaranFragment = new PembayaranFragment();
                                    FragmentTransaction ft =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment, pembayaranFragment);
                                    ft.commit();
                                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseCRUDPayment> call, Throwable t) {
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
                Toast.makeText(context, users.get(i).getIdPayment(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.form_add_pay, null);
                alert.setView(dialogView);
                alert.setCancelable(true);
                alert.setTitle("Edit Pembayaran " + users.get(i).getNamaPayment());
                alert.setIcon(R.drawable.ic_message_black_24dp);

                inputTitle = dialogView.findViewById(R.id.input_title);
                inputVal= dialogView.findViewById(R.id.input_val);
                inputCat= dialogView.findViewById(R.id.input_cat);

                inputTitle.setText(users.get(i).getNamaPayment());
                inputVal.setText(users.get(i).getValuePayment());
                inputCat.setText(users.get(i).getCatatanPayment());

                strId = users.get(i).getIdPayment();

                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        strTitle = inputTitle.getText().toString();
                        strVal = inputVal.getText().toString();
                        strCat = inputCat.getText().toString();

                        ApiInterface apiInterface = ApiClient.getInstance();
                        Call<ResponseCRUDPayment> call = apiInterface.editPayment(strId, strTitle ,strVal, strCat);
                        call.enqueue(new Callback<ResponseCRUDPayment>() {
                            @Override
                            public void onResponse(Call<ResponseCRUDPayment> call, Response<ResponseCRUDPayment> response) {
                                String msg = response.body().getMsg();
                                if (response.isSuccessful()) {
                                    PembayaranFragment pembayaranFragment = new PembayaranFragment();
                                    FragmentTransaction ft =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment, pembayaranFragment);
                                    ft.commit();
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseCRUDPayment> call, Throwable t) {
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
        TextView tvTiket, tvValue, tvCat, tvStatus1, tvStatus2, tvStatus3;
        ImageView delete, edit;
        CardView cardView;

        public ViewAbsensiHolder(View v) {
            super(v);
            tvTiket = v.findViewById(R.id.tvTiket);
            tvValue = v.findViewById(R.id.tvValue);
            delete = v.findViewById(R.id.delete);
            edit = v.findViewById(R.id.edit);
            tvCat = v.findViewById(R.id.tvCat);
//            tvStatus1 = v.findViewById(R.id.tvStatus1);
//            tvStatus2 = v.findViewById(R.id.tvStatus2);
//            tvStatus3 = v.findViewById(R.id.tvStatus3);
            cardView = v.findViewById(R.id.cardTiket);
        }
    }
}
