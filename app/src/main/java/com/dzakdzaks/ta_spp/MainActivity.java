package com.dzakdzaks.ta_spp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.fragment.AbsensiFragment;
import com.dzakdzaks.ta_spp.fragment.PembayaranFragment;
import com.dzakdzaks.ta_spp.fragment.PengumumanFragment;
import com.dzakdzaks.ta_spp.fragment.ScanBarcodeFragment;
import com.dzakdzaks.ta_spp.fragment.TunggakanFragment;
import com.dzakdzaks.ta_spp.session.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view2)
    View view;
    @BindView(R.id.tvPengumuman)
    TextView tvPengumuman;
    @BindView(R.id.tvAbsensi)
    TextView tvAbsensi;
    @BindView(R.id.tvPembayaran)
    TextView tvPembayaran;
    @BindView(R.id.tvTunggakan)
    TextView tvTunggakan;
    @BindView(R.id.tvScanBarcode)
    TextView tvScanBarcode;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        session = new UserSession(this);

        if (session.getSpRole().equals("Siswa")) {
            tvScanBarcode.setVisibility(View.GONE);
        }

        PengumumanFragment pengumumanFragment = new PengumumanFragment();
        getFragment(pengumumanFragment);

    }

    @OnClick({R.id.tvPengumuman, R.id.tvAbsensi, R.id.tvPembayaran, R.id.tvTunggakan, R.id.tvScanBarcode, R.id.tvLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvPengumuman:
                tvPengumuman.setBackgroundResource(R.drawable.backtextdarker);
                tvAbsensi.setBackgroundResource(R.drawable.backtext);
                tvPembayaran.setBackgroundResource(R.drawable.backtext);
                tvTunggakan.setBackgroundResource(R.drawable.backtext);
                tvScanBarcode.setBackgroundResource(R.drawable.backtext);
                tvLogout.setBackgroundResource(R.drawable.backtext);
                PengumumanFragment pengumumanFragment = new PengumumanFragment();
                getFragment(pengumumanFragment);
                break;
            case R.id.tvAbsensi:
                tvPengumuman.setBackgroundResource(R.drawable.backtext);
                tvAbsensi.setBackgroundResource(R.drawable.backtextdarker);
                tvPembayaran.setBackgroundResource(R.drawable.backtext);
                tvTunggakan.setBackgroundResource(R.drawable.backtext);
                tvScanBarcode.setBackgroundResource(R.drawable.backtext);
                tvLogout.setBackgroundResource(R.drawable.backtext);
                AbsensiFragment absensiFragment = new AbsensiFragment();
                getFragment(absensiFragment);
                break;
            case R.id.tvPembayaran:
                tvPengumuman.setBackgroundResource(R.drawable.backtext);
                tvAbsensi.setBackgroundResource(R.drawable.backtext);
                tvPembayaran.setBackgroundResource(R.drawable.backtextdarker);
                tvTunggakan.setBackgroundResource(R.drawable.backtext);
                tvScanBarcode.setBackgroundResource(R.drawable.backtext);
                tvLogout.setBackgroundResource(R.drawable.backtext);
                PembayaranFragment pembayaranFragment = new PembayaranFragment();
                getFragment(pembayaranFragment);
                break;
            case R.id.tvTunggakan:
                tvPengumuman.setBackgroundResource(R.drawable.backtext);
                tvAbsensi.setBackgroundResource(R.drawable.backtext);
                tvPembayaran.setBackgroundResource(R.drawable.backtext);
                tvTunggakan.setBackgroundResource(R.drawable.backtextdarker);
                tvScanBarcode.setBackgroundResource(R.drawable.backtext);
                tvLogout.setBackgroundResource(R.drawable.backtext);
                TunggakanFragment tunggakanFragment = new TunggakanFragment();
                getFragment(tunggakanFragment);
                break;
            case R.id.tvScanBarcode:
                tvPengumuman.setBackgroundResource(R.drawable.backtext);
                tvAbsensi.setBackgroundResource(R.drawable.backtext);
                tvPembayaran.setBackgroundResource(R.drawable.backtext);
                tvTunggakan.setBackgroundResource(R.drawable.backtext);
                tvScanBarcode.setBackgroundResource(R.drawable.backtextdarker);
                tvLogout.setBackgroundResource(R.drawable.backtext);
                ScanBarcodeFragment scanBarcodeFragment = new ScanBarcodeFragment();
                getFragment(scanBarcodeFragment);
                break;
            case R.id.tvLogout:
                tvPengumuman.setBackgroundResource(R.drawable.backtext);
                tvAbsensi.setBackgroundResource(R.drawable.backtext);
                tvPembayaran.setBackgroundResource(R.drawable.backtext);
                tvTunggakan.setBackgroundResource(R.drawable.backtext);
                tvScanBarcode.setBackgroundResource(R.drawable.backtext);
                tvLogout.setBackgroundResource(R.drawable.backtextdarker);
                logout();
                break;
        }
    }

    private void getFragment(Fragment fragment){
        // Memulai transaksi
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // mengganti isi container dengan fragment baru
        ft.replace(R.id.fragment, fragment);
        // atau ft.add(R.id.your_placeholder, new FooFragment());
        // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
        // buat back
        // ft.addToBackStack(null);
        ft.commit();
    }

    private void logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Logout");
        alert.setIcon(R.mipmap.ic_launcher_round);
        alert.setMessage("Anda yakin ingin keluar?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserSession sesi = new UserSession(MainActivity.this);
                sesi.logout(); //logout
                finish();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
