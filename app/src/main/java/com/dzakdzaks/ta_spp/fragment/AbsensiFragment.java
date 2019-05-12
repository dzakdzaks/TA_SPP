package com.dzakdzaks.ta_spp.fragment;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.MainActivity;
import com.dzakdzaks.ta_spp.Permission.PermissionsActivity;
import com.dzakdzaks.ta_spp.Permission.PermissionsChecker;
import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.adapter.AdapterAbsensi;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.global.EmptyRecyclerView;
import com.dzakdzaks.ta_spp.global.FileUtils;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.ResponseCRUDSiswa;
import com.dzakdzaks.ta_spp.response.ResponseUser;
import com.dzakdzaks.ta_spp.response.User;
import com.dzakdzaks.ta_spp.session.UserSession;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dzakdzaks.ta_spp.Permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.dzakdzaks.ta_spp.Permission.PermissionsChecker.REQUIRED_PERMISSION;
import static com.dzakdzaks.ta_spp.global.LogUtils.LOGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AbsensiFragment extends Fragment {

    @BindView(R.id.print7A)
    ImageView print7A;
    @BindView(R.id.print7B)
    ImageView print7B;
    @BindView(R.id.print8A)
    ImageView print8A;
    @BindView(R.id.print8B)
    ImageView print8B;
    @BindView(R.id.print9A)
    ImageView print9A;
    @BindView(R.id.print9B)
    ImageView print9B;
    @BindView(R.id.tv7AExpand)
    TextView tv7AExpand;
    @BindView(R.id.drop7A)
    ImageView drop7A;
    @BindView(R.id.relative7A)
    RelativeLayout relative7A;
    @BindView(R.id.recycler_view_7A)
    EmptyRecyclerView recyclerView7A;
    @BindView(R.id.tv7BExpand)
    TextView tv7BExpand;
    @BindView(R.id.drop7B)
    ImageView drop7B;
    @BindView(R.id.relative7B)
    RelativeLayout relative7B;
    @BindView(R.id.recycler_view_7B)
    EmptyRecyclerView recyclerView7B;
    @BindView(R.id.tv8AExpand)
    TextView tv8AExpand;
    @BindView(R.id.drop8A)
    ImageView drop8A;
    @BindView(R.id.relative8A)
    RelativeLayout relative8A;
    @BindView(R.id.recycler_view_8A)
    EmptyRecyclerView recyclerView8A;
    @BindView(R.id.tv8BExpand)
    TextView tv8BExpand;
    @BindView(R.id.drop8B)
    ImageView drop8B;
    @BindView(R.id.relative8B)
    RelativeLayout relative8B;
    @BindView(R.id.recycler_view_8B)
    EmptyRecyclerView recyclerView8B;
    @BindView(R.id.tv9AExpand)
    TextView tv9AExpand;
    @BindView(R.id.drop9A)
    ImageView drop9A;
    @BindView(R.id.relative9A)
    RelativeLayout relative9A;
    @BindView(R.id.recycler_view_9A)
    EmptyRecyclerView recyclerView9A;
    @BindView(R.id.tv9BExpand)
    TextView tv9BExpand;
    @BindView(R.id.drop9B)
    ImageView drop9B;
    @BindView(R.id.relative9B)
    RelativeLayout relative9B;
    @BindView(R.id.recycler_view_9B)
    EmptyRecyclerView recyclerView9B;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.addButton)
    RelativeLayout add;


    EditText inputNis, inputNama, inputNoUrut;
    Spinner spinnerKelas;

    String strInputNis, strInputNama, strInputNoUrut, strInputPay;


    Unbinder unbinder;

    UserSession session;
    GlobalVariable globalVariable;

    final int fallDown = R.anim.layout_animation_fall_down;
    final int fallUp = R.anim.layout_animation_fall_up;


    String itemKelas;
    String kelas7A = "VII A";
    String kelas7B = "VII B";
    String kelas8A = "VIII A";
    String kelas8B = "VIII B";
    String kelas9A = "IX A";
    String kelas9B = "IX B";

    String strDate;

    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    Context context;
    int total = 0;
    Font mainheadone = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f,
            Font.BOLD);
    Font mainhead = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f,
            Font.BOLD);
    Font subhead = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f,
            Font.NORMAL);
    Font listvalues = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f,
            Font.NORMAL);

    ArrayList<HashMap<String, String>> listKelas7A;
    ArrayList<HashMap<String, String>> listKelas7B;
    ArrayList<HashMap<String, String>> listKelas8A;
    ArrayList<HashMap<String, String>> listKelas8B;
    ArrayList<HashMap<String, String>> listKelas9A;
    ArrayList<HashMap<String, String>> listKelas9B;



    View.OnClickListener listener;

    public AbsensiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_absensi, container, false);
        unbinder = ButterKnife.bind(this, view);
        session = new UserSession(getActivity());
        globalVariable = new GlobalVariable();
        context = getContext();
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
            add.setVisibility(View.GONE);
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddSiswa();
            }
        });

        recyclerView7A.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView7A.setHasFixedSize(true);
        get7A();

        recyclerView7B.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView7B.setHasFixedSize(true);
        get7B();

        recyclerView8A.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView8A.setHasFixedSize(true);
        get8A();

        recyclerView8B.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView8B.setHasFixedSize(true);
        get8B();

        recyclerView9A.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView9A.setHasFixedSize(true);
        get9A();

        recyclerView9B.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView9B.setHasFixedSize(true);
        get9B();

        drop7A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick7A();
            }
        });

        drop7B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick7B();
            }
        });

        drop8A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick8A();
            }
        });

        drop8B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick8B();
            }
        });

        drop9A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick9A();
            }
        });

        drop9B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick9B();
            }
        });

        print7A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission7A();

            }
        });

        print7B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission7B();

            }
        });

        print8A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission8A();

            }
        });

        print8B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission8B();

            }
        });

        print9A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission9A();

            }
        });

        print9B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission9B();

            }
        });



    }

    private void requestStoragePermission7A() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            try {
                                createPdf7A();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestStoragePermission7B() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            try {
                                createPdf7B();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestStoragePermission8A() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            try {
                                createPdf8A();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestStoragePermission8B() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            try {
                                createPdf8B();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestStoragePermission9A() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            try {
                                createPdf9A();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestStoragePermission9B() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            try {
                                createPdf9B();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void dialogAddSiswa() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.form_add_siswa, null);
        alert.setView(dialogView);
        alert.setCancelable(true);
        alert.setTitle("Tambah Siswa");
        alert.setIcon(R.drawable.ic_person_black_24dp);

        inputNis = dialogView.findViewById(R.id.input_nis);
        inputNama = dialogView.findViewById(R.id.input_nama);
        inputNoUrut = dialogView.findViewById(R.id.input_no_urut);
        spinnerKelas = dialogView.findViewById(R.id.spinner_kelas);


        List<String> kelas = new ArrayList<String>();
        kelas.add(kelas7A);
        kelas.add(kelas7B);
        kelas.add(kelas8A);
        kelas.add(kelas8B);
        kelas.add(kelas9A);
        kelas.add(kelas9B);

        ArrayAdapter<String> spinnerKelasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, kelas);
        spinnerKelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKelas.setAdapter(spinnerKelasAdapter);
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


        alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                strInputNis = inputNis.getText().toString();
                strInputNama = inputNama.getText().toString();
                strInputNoUrut = inputNoUrut.getText().toString();

                if (strInputNis.isEmpty()) {
                    inputNis.setError("belum diisi");
                    inputNis.requestFocus();
                } else if (strInputNama.isEmpty()) {
                    inputNama.setError("belum diisi");
                    inputNama.requestFocus();
                } else if (strInputNoUrut.isEmpty()) {
                    inputNoUrut.setError("belum diisi");
                    inputNoUrut.requestFocus();
                } else {
                    postAddSiswa();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    private void postAddSiswa() {
        strInputNis = inputNis.getText().toString();
        strInputNama = inputNama.getText().toString();
        strInputNoUrut = inputNoUrut.getText().toString();

        if (strInputNis.isEmpty()) {
            inputNis.setError("belum diisi");
            inputNis.requestFocus();
        } else if (strInputNama.isEmpty()) {
            inputNama.setError("belum diisi");
            inputNama.requestFocus();
        } else if (strInputNoUrut.isEmpty()) {
            inputNoUrut.setError("belum diisi");
            inputNoUrut.requestFocus();
        } else {
            ApiInterface apiInterface = ApiClient.getInstance();
            Call<ResponseCRUDSiswa> call = apiInterface.addSiswa(strInputPay, strInputNis, strInputNoUrut, strInputNama, itemKelas);
            call.enqueue(new Callback<ResponseCRUDSiswa>() {
                @Override
                public void onResponse(Call<ResponseCRUDSiswa> call, Response<ResponseCRUDSiswa> response) {
                    String msg = response.body().getMsg();
                    if (response.isSuccessful()) {
                        AbsensiFragment absensiFragment = new AbsensiFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, absensiFragment);
                        ft.commit();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCRUDSiswa> call, Throwable t) {
                    Toast.makeText(getContext(), "failed add siswa", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void get7A() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseUser> call = apiInterface.getUserByKelas(kelas7A);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<User> user = response.body().getUser();
                    AdapterAbsensi adapterTiket = new AdapterAbsensi(user, getActivity());
                    recyclerView7A.setAdapter(adapterTiket);

                    listKelas7A = new ArrayList<>();
                    for (int i = 0; i < user.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("no", user.get(i).getNoUrutUser());
                        map.put("nis", user.get(i).getNisUser());
                        map.put("nama", user.get(i).getNamaUser());
                        map.put("jk", user.get(i).getJkUser());
                        map.put("agama", user.get(i).getAgamaUser());
                        map.put("ttl", user.get(i).getTtlUser());
                        map.put("alamat", user.get(i).getAlamatUser());
                        map.put("no_telp", user.get(i).getNoTelponUser());
                        map.put("sakit", user.get(i).getSakitUser());
                        map.put("izin", user.get(i).getIzinUser());
                        map.put("alpha", user.get(i).getAlphaUser());
                        listKelas7A.add(map);
                    }
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get7B() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseUser> call = apiInterface.getUserByKelas(kelas7B);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<User> user = response.body().getUser();
                    AdapterAbsensi adapterTiket = new AdapterAbsensi(user, getActivity());
                    recyclerView7B.setAdapter(adapterTiket);

                    listKelas7B = new ArrayList<>();
                    for (int i = 0; i < user.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("no", user.get(i).getNoUrutUser());
                        map.put("nis", user.get(i).getNisUser());
                        map.put("nama", user.get(i).getNamaUser());
                        map.put("jk", user.get(i).getJkUser());
                        map.put("agama", user.get(i).getAgamaUser());
                        map.put("ttl", user.get(i).getTtlUser());
                        map.put("alamat", user.get(i).getAlamatUser());
                        map.put("no_telp", user.get(i).getNoTelponUser());
                        map.put("sakit", user.get(i).getSakitUser());
                        map.put("izin", user.get(i).getIzinUser());
                        map.put("alpha", user.get(i).getAlphaUser());
                        listKelas7B.add(map);
                    }
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get8A() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseUser> call = apiInterface.getUserByKelas(kelas8A);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<User> user = response.body().getUser();
                    AdapterAbsensi adapterTiket = new AdapterAbsensi(user, getActivity());
                    recyclerView8A.setAdapter(adapterTiket);

                    listKelas8A = new ArrayList<>();
                    for (int i = 0; i < user.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("no", user.get(i).getNoUrutUser());
                        map.put("nis", user.get(i).getNisUser());
                        map.put("nama", user.get(i).getNamaUser());
                        map.put("jk", user.get(i).getJkUser());
                        map.put("agama", user.get(i).getAgamaUser());
                        map.put("ttl", user.get(i).getTtlUser());
                        map.put("alamat", user.get(i).getAlamatUser());
                        map.put("no_telp", user.get(i).getNoTelponUser());
                        map.put("sakit", user.get(i).getSakitUser());
                        map.put("izin", user.get(i).getIzinUser());
                        map.put("alpha", user.get(i).getAlphaUser());
                        listKelas8A.add(map);
                    }
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get8B() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseUser> call = apiInterface.getUserByKelas(kelas8B);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<User> user = response.body().getUser();
                    AdapterAbsensi adapterTiket = new AdapterAbsensi(user, getActivity());
                    recyclerView8B.setAdapter(adapterTiket);

                    listKelas8B = new ArrayList<>();
                    for (int i = 0; i < user.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("no", user.get(i).getNoUrutUser());
                        map.put("nis", user.get(i).getNisUser());
                        map.put("nama", user.get(i).getNamaUser());
                        map.put("jk", user.get(i).getJkUser());
                        map.put("agama", user.get(i).getAgamaUser());
                        map.put("ttl", user.get(i).getTtlUser());
                        map.put("alamat", user.get(i).getAlamatUser());
                        map.put("no_telp", user.get(i).getNoTelponUser());
                        map.put("sakit", user.get(i).getSakitUser());
                        map.put("izin", user.get(i).getIzinUser());
                        map.put("alpha", user.get(i).getAlphaUser());
                        listKelas8B.add(map);
                    }
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get9A() {
        globalVariable.showProgressBar(progressBar, getActivity());
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseUser> call = apiInterface.getUserByKelas(kelas9A);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<User> user = response.body().getUser();
                    AdapterAbsensi adapterTiket = new AdapterAbsensi(user, getActivity());
                    recyclerView9A.setAdapter(adapterTiket);

                    listKelas9A = new ArrayList<>();
                    for (int i = 0; i < user.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("no", user.get(i).getNoUrutUser());
                        map.put("nis", user.get(i).getNisUser());
                        map.put("nama", user.get(i).getNamaUser());
                        map.put("jk", user.get(i).getJkUser());
                        map.put("agama", user.get(i).getAgamaUser());
                        map.put("ttl", user.get(i).getTtlUser());
                        map.put("alamat", user.get(i).getAlamatUser());
                        map.put("no_telp", user.get(i).getNoTelponUser());
                        map.put("sakit", user.get(i).getSakitUser());
                        map.put("izin", user.get(i).getIzinUser());
                        map.put("alpha", user.get(i).getAlphaUser());
                        listKelas9A.add(map);
                    }
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get9B() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseUser> call = apiInterface.getUserByKelas(kelas9B);
        globalVariable.showProgressBar(progressBar, getActivity());
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    String msg = response.body().getMsg();
                    List<User> user = response.body().getUser();
                    AdapterAbsensi adapterTiket = new AdapterAbsensi(user, getActivity());
                    recyclerView9B.setAdapter(adapterTiket);

                    listKelas9B = new ArrayList<>();
                    for (int i = 0; i < user.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("no", user.get(i).getNoUrutUser());
                        map.put("nis", user.get(i).getNisUser());
                        map.put("nama", user.get(i).getNamaUser());
                        map.put("jk", user.get(i).getJkUser());
                        map.put("agama", user.get(i).getAgamaUser());
                        map.put("ttl", user.get(i).getTtlUser());
                        map.put("alamat", user.get(i).getAlamatUser());
                        map.put("no_telp", user.get(i).getNoTelponUser());
                        map.put("sakit", user.get(i).getSakitUser());
                        map.put("izin", user.get(i).getIzinUser());
                        map.put("alpha", user.get(i).getAlphaUser());
                        listKelas9B.add(map);
                    }
//                    if (adapterTiket.getItemCount() == 0) {
//                        emptyView.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyView.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onClick7A() {
        if (recyclerView7A.getVisibility() == View.GONE) {
            recyclerView7A.setVisibility(View.VISIBLE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallDown);
            recyclerView7A.setLayoutAnimation(animation);
            drop7A.animate().rotation(180).start();
        } else {
            recyclerView7A.setVisibility(View.GONE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallUp);
            recyclerView7A.setLayoutAnimation(animation);
            drop7A.animate().rotation(360).start();
        }
    }

    private void onClick7B() {
        if (recyclerView7B.getVisibility() == View.GONE) {
            recyclerView7B.setVisibility(View.VISIBLE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallDown);
            recyclerView7B.setLayoutAnimation(animation);
            drop7B.animate().rotation(180).start();
        } else {
            recyclerView7B.setVisibility(View.GONE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallUp);
            recyclerView7B.setLayoutAnimation(animation);
            drop7B.animate().rotation(360).start();
        }
    }

    private void onClick8A() {
        if (recyclerView8A.getVisibility() == View.GONE) {
            recyclerView8A.setVisibility(View.VISIBLE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallDown);
            recyclerView8A.setLayoutAnimation(animation);
            drop8A.animate().rotation(180).start();
        } else {
            recyclerView8A.setVisibility(View.GONE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallUp);
            recyclerView8A.setLayoutAnimation(animation);
            drop8A.animate().rotation(360).start();
        }
    }

    private void onClick8B() {
        if (recyclerView8B.getVisibility() == View.GONE) {
            recyclerView8B.setVisibility(View.VISIBLE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallDown);
            recyclerView8B.setLayoutAnimation(animation);
            drop8B.animate().rotation(180).start();
        } else {
            recyclerView8B.setVisibility(View.GONE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallUp);
            recyclerView8B.setLayoutAnimation(animation);
            drop8B.animate().rotation(360).start();
        }
    }

    private void onClick9A() {
        if (recyclerView9A.getVisibility() == View.GONE) {
            recyclerView9A.setVisibility(View.VISIBLE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallDown);
            recyclerView9A.setLayoutAnimation(animation);
            drop9A.animate().rotation(180).start();
        } else {
            recyclerView9A.setVisibility(View.GONE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallUp);
            recyclerView9A.setLayoutAnimation(animation);
            drop9A.animate().rotation(360).start();
        }
    }

    private void onClick9B() {
        if (recyclerView9B.getVisibility() == View.GONE) {
            recyclerView9B.setVisibility(View.VISIBLE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallDown);
            recyclerView9B.setLayoutAnimation(animation);
            drop9B.animate().rotation(180).start();
        } else {
            recyclerView9B.setVisibility(View.GONE);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), fallUp);
            recyclerView9B.setLayoutAnimation(animation);
            drop9B.animate().rotation(360).start();
        }
    }


    private void createPdf7A() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Absensi", "Created a new directory for PDF");
        }
        pdfFile = new File(docsFolder.getAbsolutePath(), "Kelas " + kelas7A + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A2.rotate());
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();


        try {

            Font mOrderDetailsTitleFont = new Font(mainheadone);
            Chunk mOrderDetailsTitleChunk = new Chunk("YAYASAN PENDIDIKAN DELAPAN DELAPAN(YP'88)\n" +
                    "SEKOLAH MENENGAH PERTAMA (SMP) HUTAMA\n" +
                    "TAHUN PELAJARAN 2018 / 2019", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(mainheadone);
            Chunk mNamaUmurChunksas = new Chunk("ABSENSI BULAN          :", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("KELAS         :   " + kelas7A, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            document.add(new Paragraph("\n"));


            PdfPTable headtab = new PdfPTable(43);
            headtab.setWidthPercentage(100);
            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell headcell;

            headcell = new PdfPCell(new Paragraph("TANGGAL", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(40);
            headtab.addCell(headcell);
            document.add(headtab);

            headcell = new PdfPCell(new Paragraph("JUMLAH", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(3);
            headcell.setPadding(5);
            headtab.addCell(headcell);
            document.add(headtab);

            PdfPTable patab = new PdfPTable(43);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(1);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(5);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(3);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                pacell = new PdfPCell(new Paragraph(String.valueOf(total), mainhead));
                pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                pacell.setBorder(0);
                pacell.setColspan(1);
                headcell.setPadding(5);
                pacell.setBorderWidthTop(1);
                pacell.setBorderWidthLeft(1);
                pacell.setBorderWidthBottom(1);
                patab.addCell(pacell);
            }


            pacell = new PdfPCell(new Paragraph("S", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("I", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("A", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);


            document.add(patab);


            PdfPTable contab = new PdfPTable(43);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 0; listKelas7A.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                HashMap map = listKelas7A.get(i);
                String no = map.get("no").toString();
                String nis = map.get("nis").toString();
                String nama = map.get("nama").toString();
                String jk = map.get("jk").toString();
                String agama = map.get("agama").toString();
                String ttl = map.get("ttl").toString();
                String alamat = map.get("alamat").toString();
                String no_telp = map.get("no_telp").toString();
                String sakit = map.get("sakit").toString();
                String izin = map.get("izin").toString();
                String alpha = map.get("alpha").toString();

//                int intAmt = Integer.parseInt(amt);
//                total += intAmt;

                concell = new PdfPCell(new Paragraph("" + no, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(3);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                for (int is = 0; is < 31; is++) {
                    if (is == 0) {
                        total = 0;
                    }
                    concell = new PdfPCell(new Paragraph("", listvalues));
                    concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                    concell.setBorder(0);
                    concell.setColspan(1);
                    headcell.setPadding(5);
                    concell.setBorderWidthRight(1);
                    concell.setBorderWidthBottom(1);
                    contab.addCell(concell);
                }
                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

            }
            document.add(contab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsasW = new Font(mainhead);
            Chunk mNamaUmurChunksasW = new Chunk("*CATATAN:", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasW = new Paragraph(mNamaUmurChunksasW);
            document.add(mNamaUmurParagraphsasW);

            Chunk mNamaUmurChunksasWq = new Chunk("Jumlah Absen X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWq = new Paragraph(mNamaUmurChunksasWq);
            document.add(mNamaUmurParagraphsasWq);

            Chunk mNamaUmurChunksasWw = new Chunk("Jumlah Siswa X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWw = new Paragraph(mNamaUmurChunksasWw);
            document.add(mNamaUmurParagraphsasWw);

            Chunk mNamaUmurChunksase = new Chunk("Mengetahui                                                                              Bekasi,          2019", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsase = new Paragraph(mNamaUmurChunksase);
            mNamaUmurParagraphsase.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsase);

            //15tab
            Chunk mNamaUmurChunksasa = new Chunk("Kepala SMP HUTAMA                                                                             Wali Kelas", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            mNamaUmurParagraphsasa.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            //15tab
            Chunk mNamaUmurChunksasaw = new Chunk("MARDIN KARO KARO S PD                                                         -----------------------", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            mNamaUmurParagraphsasaw.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasaw);

            //15tab
            Chunk mNamaUmurChunksasawe = new Chunk("NIP. 19610806 198503 1 013                                                                                            ", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasawe = new Paragraph(mNamaUmurChunksasawe);
            mNamaUmurParagraphsasawe.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasawe);

//            PdfPTable headtab = new PdfPTable(1);
//            headtab.setWidthPercentage(100);
//            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            PdfPCell headcell;
//
//            headcell = new PdfPCell(new Paragraph("ABSENSI KELAS VII-A", mainheadone));
//            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            headcell.setBorderWidthLeft(1);
//            headcell.setBorderWidthRight(1);
//            headcell.setBorderWidthTop(1);
//            headcell.setBorderWidthBottom(1);
//            headcell.setPadding(5);
//            headtab.addCell(headcell);
//            document.add(headtab);
//
//            PdfPTable patab = new PdfPTable(27);
//            patab.setWidthPercentage(100);
//            PdfPCell pacell;
//
//            pacell = new PdfPCell(new Paragraph("NO", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//
//            pacell = new PdfPCell(new Paragraph("JK", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("AGAMA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(2);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("TTL", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(4);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("ALAMAT", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NO TELEPON", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);

//            pacell = new PdfPCell(new Paragraph("S", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("I", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("A", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthRight(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            document.add(patab);

//            /*Set the item details*/
//            PdfPTable contab = new PdfPTable(27);
//            contab.setWidthPercentage(100);
//            PdfPCell concell;
//
//            for (int i = 0; listKelas7A.size() > i; i++) {
//                if (i == 0) {
//                    total = 0;
//                }
//                HashMap map = listKelas7A.get(i);
//                String no = map.get("no").toString();
//                String nis = map.get("nis").toString();
//                String nama = map.get("nama").toString();
//                String jk = map.get("jk").toString();
//                String agama = map.get("agama").toString();
//                String ttl = map.get("ttl").toString();
//                String alamat = map.get("alamat").toString();
//                String no_telp = map.get("no_telp").toString();
//                String sakit = map.get("sakit").toString();
//                String izin = map.get("izin").toString();
//                String alpha = map.get("alpha").toString();
//
////                int intAmt = Integer.parseInt(amt);
////                total += intAmt;
//
//                concell = new PdfPCell(new Paragraph("" + no, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setPadding(5);
//                concell.setBorder(0);
//                concell.setColspan(1);
//                concell.setBorderWidthLeft(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + jk, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + agama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(2);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + ttl, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(4);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alamat, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + no_telp, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + sakit, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + izin, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alpha, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//            }
//            document.add(contab);

            document.close();

            Toast.makeText(context, "Absensi Kelas " + kelas7A + " Created... :) \n" +
                    "Created in /storage/emulate/0/ta_spp/Absensi", Toast.LENGTH_SHORT).show();

        } catch (
                DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }

    }


    private void createPdf7B()  throws FileNotFoundException,DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Absensi", "Created a new directory for PDF");
        }
        pdfFile = new File(docsFolder.getAbsolutePath(), "Kelas " + kelas7B + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A2.rotate());
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();

        try {

            Font mOrderDetailsTitleFont = new Font(mainheadone);
            Chunk mOrderDetailsTitleChunk = new Chunk("YAYASAN PENDIDIKAN DELAPAN DELAPAN(YP'88)\n" +
                    "SEKOLAH MENENGAH PERTAMA (SMP) HUTAMA\n" +
                    "TAHUN PELAJARAN 2018 / 2019", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(mainheadone);
            Chunk mNamaUmurChunksas = new Chunk("ABSENSI BULAN          :", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("KELAS         :   " + kelas7B, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            document.add(new Paragraph("\n"));


            PdfPTable headtab = new PdfPTable(43);
            headtab.setWidthPercentage(100);
            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell headcell;

            headcell = new PdfPCell(new Paragraph("TANGGAL", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(40);
            headtab.addCell(headcell);
            document.add(headtab);

            headcell = new PdfPCell(new Paragraph("JUMLAH", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(3);
            headcell.setPadding(5);
            headtab.addCell(headcell);
            document.add(headtab);

            PdfPTable patab = new PdfPTable(43);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(1);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(5);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(3);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                pacell = new PdfPCell(new Paragraph(String.valueOf(total), mainhead));
                pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                pacell.setBorder(0);
                pacell.setColspan(1);
                headcell.setPadding(5);
                pacell.setBorderWidthTop(1);
                pacell.setBorderWidthLeft(1);
                pacell.setBorderWidthBottom(1);
                patab.addCell(pacell);
            }


            pacell = new PdfPCell(new Paragraph("S", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("I", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("A", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);


            document.add(patab);


            PdfPTable contab = new PdfPTable(43);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 0; listKelas7B.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                HashMap map = listKelas7B.get(i);
                String no = map.get("no").toString();
                String nis = map.get("nis").toString();
                String nama = map.get("nama").toString();
                String jk = map.get("jk").toString();
                String agama = map.get("agama").toString();
                String ttl = map.get("ttl").toString();
                String alamat = map.get("alamat").toString();
                String no_telp = map.get("no_telp").toString();
                String sakit = map.get("sakit").toString();
                String izin = map.get("izin").toString();
                String alpha = map.get("alpha").toString();

//                int intAmt = Integer.parseInt(amt);
//                total += intAmt;

                concell = new PdfPCell(new Paragraph("" + no, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(3);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                for (int is = 0; is < 31; is++) {
                    if (is == 0) {
                        total = 0;
                    }
                    concell = new PdfPCell(new Paragraph("", listvalues));
                    concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                    concell.setBorder(0);
                    concell.setColspan(1);
                    headcell.setPadding(5);
                    concell.setBorderWidthRight(1);
                    concell.setBorderWidthBottom(1);
                    contab.addCell(concell);
                }
                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

            }
            document.add(contab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsasW = new Font(mainhead);
            Chunk mNamaUmurChunksasW = new Chunk("*CATATAN:", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasW = new Paragraph(mNamaUmurChunksasW);
            document.add(mNamaUmurParagraphsasW);

            Chunk mNamaUmurChunksasWq = new Chunk("Jumlah Absen X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWq = new Paragraph(mNamaUmurChunksasWq);
            document.add(mNamaUmurParagraphsasWq);

            Chunk mNamaUmurChunksasWw = new Chunk("Jumlah Siswa X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWw = new Paragraph(mNamaUmurChunksasWw);
            document.add(mNamaUmurParagraphsasWw);

            Chunk mNamaUmurChunksase = new Chunk("Mengetahui                                                                              Bekasi,          2019", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsase = new Paragraph(mNamaUmurChunksase);
            mNamaUmurParagraphsase.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsase);

            //15tab
            Chunk mNamaUmurChunksasa = new Chunk("Kepala SMP HUTAMA                                                                             Wali Kelas", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            mNamaUmurParagraphsasa.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            //15tab
            Chunk mNamaUmurChunksasaw = new Chunk("MARDIN KARO KARO S PD                                                         -----------------------", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            mNamaUmurParagraphsasaw.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasaw);

            //15tab
            Chunk mNamaUmurChunksasawe = new Chunk("NIP. 19610806 198503 1 013                                                                                            ", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasawe = new Paragraph(mNamaUmurChunksasawe);
            mNamaUmurParagraphsasawe.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasawe);

//            PdfPTable headtab = new PdfPTable(1);
//            headtab.setWidthPercentage(100);
//            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            PdfPCell headcell;
//
//            headcell = new PdfPCell(new Paragraph("ABSENSI KELAS VII-A", mainheadone));
//            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            headcell.setBorderWidthLeft(1);
//            headcell.setBorderWidthRight(1);
//            headcell.setBorderWidthTop(1);
//            headcell.setBorderWidthBottom(1);
//            headcell.setPadding(5);
//            headtab.addCell(headcell);
//            document.add(headtab);
//
//            PdfPTable patab = new PdfPTable(27);
//            patab.setWidthPercentage(100);
//            PdfPCell pacell;
//
//            pacell = new PdfPCell(new Paragraph("NO", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//
//            pacell = new PdfPCell(new Paragraph("JK", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("AGAMA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(2);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("TTL", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(4);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("ALAMAT", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NO TELEPON", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);

//            pacell = new PdfPCell(new Paragraph("S", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("I", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("A", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthRight(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            document.add(patab);

//            /*Set the item details*/
//            PdfPTable contab = new PdfPTable(27);
//            contab.setWidthPercentage(100);
//            PdfPCell concell;
//
//            for (int i = 0; listKelas7A.size() > i; i++) {
//                if (i == 0) {
//                    total = 0;
//                }
//                HashMap map = listKelas7A.get(i);
//                String no = map.get("no").toString();
//                String nis = map.get("nis").toString();
//                String nama = map.get("nama").toString();
//                String jk = map.get("jk").toString();
//                String agama = map.get("agama").toString();
//                String ttl = map.get("ttl").toString();
//                String alamat = map.get("alamat").toString();
//                String no_telp = map.get("no_telp").toString();
//                String sakit = map.get("sakit").toString();
//                String izin = map.get("izin").toString();
//                String alpha = map.get("alpha").toString();
//
////                int intAmt = Integer.parseInt(amt);
////                total += intAmt;
//
//                concell = new PdfPCell(new Paragraph("" + no, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setPadding(5);
//                concell.setBorder(0);
//                concell.setColspan(1);
//                concell.setBorderWidthLeft(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + jk, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + agama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(2);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + ttl, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(4);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alamat, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + no_telp, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + sakit, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + izin, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alpha, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//            }
//            document.add(contab);

            document.close();

            Toast.makeText(context, "Absensi Kelas " + kelas7B + " Created... :) \n" +
                    "Created in /storage/emulate/0/ta_spp/Absensi", Toast.LENGTH_SHORT).show();

        } catch (
                DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
    }


    private void createPdf8A()throws FileNotFoundException,DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Absensi", "Created a new directory for PDF");
        }
        pdfFile = new File(docsFolder.getAbsolutePath(), "Kelas " + kelas8A + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A2.rotate());
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();
        try {

            Font mOrderDetailsTitleFont = new Font(mainheadone);
            Chunk mOrderDetailsTitleChunk = new Chunk("YAYASAN PENDIDIKAN DELAPAN DELAPAN(YP'88)\n" +
                    "SEKOLAH MENENGAH PERTAMA (SMP) HUTAMA\n" +
                    "TAHUN PELAJARAN 2018 / 2019", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(mainheadone);
            Chunk mNamaUmurChunksas = new Chunk("ABSENSI BULAN          :", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("KELAS         :   " + kelas8A, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            document.add(new Paragraph("\n"));


            PdfPTable headtab = new PdfPTable(43);
            headtab.setWidthPercentage(100);
            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell headcell;

            headcell = new PdfPCell(new Paragraph("TANGGAL", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(40);
            headtab.addCell(headcell);
            document.add(headtab);

            headcell = new PdfPCell(new Paragraph("JUMLAH", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(3);
            headcell.setPadding(5);
            headtab.addCell(headcell);
            document.add(headtab);

            PdfPTable patab = new PdfPTable(43);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(1);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(5);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(3);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                pacell = new PdfPCell(new Paragraph(String.valueOf(total), mainhead));
                pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                pacell.setBorder(0);
                pacell.setColspan(1);
                headcell.setPadding(5);
                pacell.setBorderWidthTop(1);
                pacell.setBorderWidthLeft(1);
                pacell.setBorderWidthBottom(1);
                patab.addCell(pacell);
            }


            pacell = new PdfPCell(new Paragraph("S", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("I", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("A", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);


            document.add(patab);


            PdfPTable contab = new PdfPTable(43);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 0; listKelas8A.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                HashMap map = listKelas8A.get(i);
                String no = map.get("no").toString();
                String nis = map.get("nis").toString();
                String nama = map.get("nama").toString();
                String jk = map.get("jk").toString();
                String agama = map.get("agama").toString();
                String ttl = map.get("ttl").toString();
                String alamat = map.get("alamat").toString();
                String no_telp = map.get("no_telp").toString();
                String sakit = map.get("sakit").toString();
                String izin = map.get("izin").toString();
                String alpha = map.get("alpha").toString();

//                int intAmt = Integer.parseInt(amt);
//                total += intAmt;

                concell = new PdfPCell(new Paragraph("" + no, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(3);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                for (int is = 0; is < 31; is++) {
                    if (is == 0) {
                        total = 0;
                    }
                    concell = new PdfPCell(new Paragraph("", listvalues));
                    concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                    concell.setBorder(0);
                    concell.setColspan(1);
                    headcell.setPadding(5);
                    concell.setBorderWidthRight(1);
                    concell.setBorderWidthBottom(1);
                    contab.addCell(concell);
                }
                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

            }
            document.add(contab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsasW = new Font(mainhead);
            Chunk mNamaUmurChunksasW = new Chunk("*CATATAN:", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasW = new Paragraph(mNamaUmurChunksasW);
            document.add(mNamaUmurParagraphsasW);

            Chunk mNamaUmurChunksasWq = new Chunk("Jumlah Absen X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWq = new Paragraph(mNamaUmurChunksasWq);
            document.add(mNamaUmurParagraphsasWq);

            Chunk mNamaUmurChunksasWw = new Chunk("Jumlah Siswa X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWw = new Paragraph(mNamaUmurChunksasWw);
            document.add(mNamaUmurParagraphsasWw);

            Chunk mNamaUmurChunksase = new Chunk("Mengetahui                                                                              Bekasi,          2019", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsase = new Paragraph(mNamaUmurChunksase);
            mNamaUmurParagraphsase.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsase);

            //15tab
            Chunk mNamaUmurChunksasa = new Chunk("Kepala SMP HUTAMA                                                                             Wali Kelas", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            mNamaUmurParagraphsasa.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            //15tab
            Chunk mNamaUmurChunksasaw = new Chunk("MARDIN KARO KARO S PD                                                         -----------------------", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            mNamaUmurParagraphsasaw.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasaw);

            //15tab
            Chunk mNamaUmurChunksasawe = new Chunk("NIP. 19610806 198503 1 013                                                                                            ", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasawe = new Paragraph(mNamaUmurChunksasawe);
            mNamaUmurParagraphsasawe.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasawe);

//            PdfPTable headtab = new PdfPTable(1);
//            headtab.setWidthPercentage(100);
//            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            PdfPCell headcell;
//
//            headcell = new PdfPCell(new Paragraph("ABSENSI KELAS VII-A", mainheadone));
//            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            headcell.setBorderWidthLeft(1);
//            headcell.setBorderWidthRight(1);
//            headcell.setBorderWidthTop(1);
//            headcell.setBorderWidthBottom(1);
//            headcell.setPadding(5);
//            headtab.addCell(headcell);
//            document.add(headtab);
//
//            PdfPTable patab = new PdfPTable(27);
//            patab.setWidthPercentage(100);
//            PdfPCell pacell;
//
//            pacell = new PdfPCell(new Paragraph("NO", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//
//            pacell = new PdfPCell(new Paragraph("JK", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("AGAMA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(2);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("TTL", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(4);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("ALAMAT", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NO TELEPON", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);

//            pacell = new PdfPCell(new Paragraph("S", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("I", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("A", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthRight(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            document.add(patab);

//            /*Set the item details*/
//            PdfPTable contab = new PdfPTable(27);
//            contab.setWidthPercentage(100);
//            PdfPCell concell;
//
//            for (int i = 0; listKelas7A.size() > i; i++) {
//                if (i == 0) {
//                    total = 0;
//                }
//                HashMap map = listKelas7A.get(i);
//                String no = map.get("no").toString();
//                String nis = map.get("nis").toString();
//                String nama = map.get("nama").toString();
//                String jk = map.get("jk").toString();
//                String agama = map.get("agama").toString();
//                String ttl = map.get("ttl").toString();
//                String alamat = map.get("alamat").toString();
//                String no_telp = map.get("no_telp").toString();
//                String sakit = map.get("sakit").toString();
//                String izin = map.get("izin").toString();
//                String alpha = map.get("alpha").toString();
//
////                int intAmt = Integer.parseInt(amt);
////                total += intAmt;
//
//                concell = new PdfPCell(new Paragraph("" + no, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setPadding(5);
//                concell.setBorder(0);
//                concell.setColspan(1);
//                concell.setBorderWidthLeft(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + jk, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + agama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(2);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + ttl, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(4);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alamat, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + no_telp, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + sakit, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + izin, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alpha, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//            }
//            document.add(contab);

            document.close();

            Toast.makeText(context, "Absensi Kelas " + kelas8A + " Created... :) \n" +
                    "Created in /storage/emulate/0/ta_spp/Absensi", Toast.LENGTH_SHORT).show();

        } catch (
                DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
    }

    private void createPdf8B() throws FileNotFoundException,DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Absensi", "Created a new directory for PDF");
        }
        pdfFile = new File(docsFolder.getAbsolutePath(), "Kelas " + kelas8B + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A2.rotate());
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();
        try {

            Font mOrderDetailsTitleFont = new Font(mainheadone);
            Chunk mOrderDetailsTitleChunk = new Chunk("YAYASAN PENDIDIKAN DELAPAN DELAPAN(YP'88)\n" +
                    "SEKOLAH MENENGAH PERTAMA (SMP) HUTAMA\n" +
                    "TAHUN PELAJARAN 2018 / 2019", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(mainheadone);
            Chunk mNamaUmurChunksas = new Chunk("ABSENSI BULAN          :", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("KELAS         :   " + kelas8B, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            document.add(new Paragraph("\n"));


            PdfPTable headtab = new PdfPTable(43);
            headtab.setWidthPercentage(100);
            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell headcell;

            headcell = new PdfPCell(new Paragraph("TANGGAL", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(40);
            headtab.addCell(headcell);
            document.add(headtab);

            headcell = new PdfPCell(new Paragraph("JUMLAH", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(3);
            headcell.setPadding(5);
            headtab.addCell(headcell);
            document.add(headtab);

            PdfPTable patab = new PdfPTable(43);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(1);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(5);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(3);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                pacell = new PdfPCell(new Paragraph(String.valueOf(total), mainhead));
                pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                pacell.setBorder(0);
                pacell.setColspan(1);
                headcell.setPadding(5);
                pacell.setBorderWidthTop(1);
                pacell.setBorderWidthLeft(1);
                pacell.setBorderWidthBottom(1);
                patab.addCell(pacell);
            }


            pacell = new PdfPCell(new Paragraph("S", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("I", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("A", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);


            document.add(patab);


            PdfPTable contab = new PdfPTable(43);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 0; listKelas8B.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                HashMap map = listKelas8B.get(i);
                String no = map.get("no").toString();
                String nis = map.get("nis").toString();
                String nama = map.get("nama").toString();
                String jk = map.get("jk").toString();
                String agama = map.get("agama").toString();
                String ttl = map.get("ttl").toString();
                String alamat = map.get("alamat").toString();
                String no_telp = map.get("no_telp").toString();
                String sakit = map.get("sakit").toString();
                String izin = map.get("izin").toString();
                String alpha = map.get("alpha").toString();

//                int intAmt = Integer.parseInt(amt);
//                total += intAmt;

                concell = new PdfPCell(new Paragraph("" + no, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(3);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                for (int is = 0; is < 31; is++) {
                    if (is == 0) {
                        total = 0;
                    }
                    concell = new PdfPCell(new Paragraph("", listvalues));
                    concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                    concell.setBorder(0);
                    concell.setColspan(1);
                    headcell.setPadding(5);
                    concell.setBorderWidthRight(1);
                    concell.setBorderWidthBottom(1);
                    contab.addCell(concell);
                }
                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

            }
            document.add(contab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsasW = new Font(mainhead);
            Chunk mNamaUmurChunksasW = new Chunk("*CATATAN:", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasW = new Paragraph(mNamaUmurChunksasW);
            document.add(mNamaUmurParagraphsasW);

            Chunk mNamaUmurChunksasWq = new Chunk("Jumlah Absen X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWq = new Paragraph(mNamaUmurChunksasWq);
            document.add(mNamaUmurParagraphsasWq);

            Chunk mNamaUmurChunksasWw = new Chunk("Jumlah Siswa X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWw = new Paragraph(mNamaUmurChunksasWw);
            document.add(mNamaUmurParagraphsasWw);

            Chunk mNamaUmurChunksase = new Chunk("Mengetahui                                                                              Bekasi,          2019", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsase = new Paragraph(mNamaUmurChunksase);
            mNamaUmurParagraphsase.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsase);

            //15tab
            Chunk mNamaUmurChunksasa = new Chunk("Kepala SMP HUTAMA                                                                             Wali Kelas", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            mNamaUmurParagraphsasa.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            //15tab
            Chunk mNamaUmurChunksasaw = new Chunk("MARDIN KARO KARO S PD                                                         -----------------------", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            mNamaUmurParagraphsasaw.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasaw);

            //15tab
            Chunk mNamaUmurChunksasawe = new Chunk("NIP. 19610806 198503 1 013                                                                                            ", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasawe = new Paragraph(mNamaUmurChunksasawe);
            mNamaUmurParagraphsasawe.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasawe);

//            PdfPTable headtab = new PdfPTable(1);
//            headtab.setWidthPercentage(100);
//            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            PdfPCell headcell;
//
//            headcell = new PdfPCell(new Paragraph("ABSENSI KELAS VII-A", mainheadone));
//            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            headcell.setBorderWidthLeft(1);
//            headcell.setBorderWidthRight(1);
//            headcell.setBorderWidthTop(1);
//            headcell.setBorderWidthBottom(1);
//            headcell.setPadding(5);
//            headtab.addCell(headcell);
//            document.add(headtab);
//
//            PdfPTable patab = new PdfPTable(27);
//            patab.setWidthPercentage(100);
//            PdfPCell pacell;
//
//            pacell = new PdfPCell(new Paragraph("NO", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//
//            pacell = new PdfPCell(new Paragraph("JK", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("AGAMA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(2);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("TTL", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(4);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("ALAMAT", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NO TELEPON", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);

//            pacell = new PdfPCell(new Paragraph("S", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("I", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("A", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthRight(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            document.add(patab);

//            /*Set the item details*/
//            PdfPTable contab = new PdfPTable(27);
//            contab.setWidthPercentage(100);
//            PdfPCell concell;
//
//            for (int i = 0; listKelas7A.size() > i; i++) {
//                if (i == 0) {
//                    total = 0;
//                }
//                HashMap map = listKelas7A.get(i);
//                String no = map.get("no").toString();
//                String nis = map.get("nis").toString();
//                String nama = map.get("nama").toString();
//                String jk = map.get("jk").toString();
//                String agama = map.get("agama").toString();
//                String ttl = map.get("ttl").toString();
//                String alamat = map.get("alamat").toString();
//                String no_telp = map.get("no_telp").toString();
//                String sakit = map.get("sakit").toString();
//                String izin = map.get("izin").toString();
//                String alpha = map.get("alpha").toString();
//
////                int intAmt = Integer.parseInt(amt);
////                total += intAmt;
//
//                concell = new PdfPCell(new Paragraph("" + no, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setPadding(5);
//                concell.setBorder(0);
//                concell.setColspan(1);
//                concell.setBorderWidthLeft(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + jk, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + agama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(2);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + ttl, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(4);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alamat, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + no_telp, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + sakit, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + izin, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alpha, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//            }
//            document.add(contab);

            document.close();

            Toast.makeText(context, "Absensi Kelas " + kelas8B + " Created... :) \n" +
                    "Created in /storage/emulate/0/ta_spp/Absensi", Toast.LENGTH_SHORT).show();

        } catch (
                DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
    }


    private void createPdf9A() throws FileNotFoundException,DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Absensi", "Created a new directory for PDF");
        }
        pdfFile = new File(docsFolder.getAbsolutePath(), "Kelas " + kelas9A + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A2.rotate());
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();
        try {

            Font mOrderDetailsTitleFont = new Font(mainheadone);
            Chunk mOrderDetailsTitleChunk = new Chunk("YAYASAN PENDIDIKAN DELAPAN DELAPAN(YP'88)\n" +
                    "SEKOLAH MENENGAH PERTAMA (SMP) HUTAMA\n" +
                    "TAHUN PELAJARAN 2018 / 2019", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(mainheadone);
            Chunk mNamaUmurChunksas = new Chunk("ABSENSI BULAN          :", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("KELAS         :   " + kelas9A, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            document.add(new Paragraph("\n"));


            PdfPTable headtab = new PdfPTable(43);
            headtab.setWidthPercentage(100);
            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell headcell;

            headcell = new PdfPCell(new Paragraph("TANGGAL", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(40);
            headtab.addCell(headcell);
            document.add(headtab);

            headcell = new PdfPCell(new Paragraph("JUMLAH", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(3);
            headcell.setPadding(5);
            headtab.addCell(headcell);
            document.add(headtab);

            PdfPTable patab = new PdfPTable(43);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(1);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(5);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(3);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                pacell = new PdfPCell(new Paragraph(String.valueOf(total), mainhead));
                pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                pacell.setBorder(0);
                pacell.setColspan(1);
                headcell.setPadding(5);
                pacell.setBorderWidthTop(1);
                pacell.setBorderWidthLeft(1);
                pacell.setBorderWidthBottom(1);
                patab.addCell(pacell);
            }


            pacell = new PdfPCell(new Paragraph("S", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("I", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("A", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);


            document.add(patab);


            PdfPTable contab = new PdfPTable(43);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 0; listKelas9A.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                HashMap map = listKelas9A.get(i);
                String no = map.get("no").toString();
                String nis = map.get("nis").toString();
                String nama = map.get("nama").toString();
                String jk = map.get("jk").toString();
                String agama = map.get("agama").toString();
                String ttl = map.get("ttl").toString();
                String alamat = map.get("alamat").toString();
                String no_telp = map.get("no_telp").toString();
                String sakit = map.get("sakit").toString();
                String izin = map.get("izin").toString();
                String alpha = map.get("alpha").toString();

//                int intAmt = Integer.parseInt(amt);
//                total += intAmt;

                concell = new PdfPCell(new Paragraph("" + no, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(3);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                for (int is = 0; is < 31; is++) {
                    if (is == 0) {
                        total = 0;
                    }
                    concell = new PdfPCell(new Paragraph("", listvalues));
                    concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                    concell.setBorder(0);
                    concell.setColspan(1);
                    headcell.setPadding(5);
                    concell.setBorderWidthRight(1);
                    concell.setBorderWidthBottom(1);
                    contab.addCell(concell);
                }
                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

            }
            document.add(contab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsasW = new Font(mainhead);
            Chunk mNamaUmurChunksasW = new Chunk("*CATATAN:", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasW = new Paragraph(mNamaUmurChunksasW);
            document.add(mNamaUmurParagraphsasW);

            Chunk mNamaUmurChunksasWq = new Chunk("Jumlah Absen X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWq = new Paragraph(mNamaUmurChunksasWq);
            document.add(mNamaUmurParagraphsasWq);

            Chunk mNamaUmurChunksasWw = new Chunk("Jumlah Siswa X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWw = new Paragraph(mNamaUmurChunksasWw);
            document.add(mNamaUmurParagraphsasWw);

            Chunk mNamaUmurChunksase = new Chunk("Mengetahui                                                                              Bekasi,          2019", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsase = new Paragraph(mNamaUmurChunksase);
            mNamaUmurParagraphsase.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsase);

            //15tab
            Chunk mNamaUmurChunksasa = new Chunk("Kepala SMP HUTAMA                                                                             Wali Kelas", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            mNamaUmurParagraphsasa.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            //15tab
            Chunk mNamaUmurChunksasaw = new Chunk("MARDIN KARO KARO S PD                                                         -----------------------", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            mNamaUmurParagraphsasaw.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasaw);

            //15tab
            Chunk mNamaUmurChunksasawe = new Chunk("NIP. 19610806 198503 1 013                                                                                            ", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasawe = new Paragraph(mNamaUmurChunksasawe);
            mNamaUmurParagraphsasawe.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasawe);

//            PdfPTable headtab = new PdfPTable(1);
//            headtab.setWidthPercentage(100);
//            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            PdfPCell headcell;
//
//            headcell = new PdfPCell(new Paragraph("ABSENSI KELAS VII-A", mainheadone));
//            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            headcell.setBorderWidthLeft(1);
//            headcell.setBorderWidthRight(1);
//            headcell.setBorderWidthTop(1);
//            headcell.setBorderWidthBottom(1);
//            headcell.setPadding(5);
//            headtab.addCell(headcell);
//            document.add(headtab);
//
//            PdfPTable patab = new PdfPTable(27);
//            patab.setWidthPercentage(100);
//            PdfPCell pacell;
//
//            pacell = new PdfPCell(new Paragraph("NO", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//
//            pacell = new PdfPCell(new Paragraph("JK", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("AGAMA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(2);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("TTL", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(4);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("ALAMAT", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NO TELEPON", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);

//            pacell = new PdfPCell(new Paragraph("S", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("I", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("A", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthRight(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            document.add(patab);

//            /*Set the item details*/
//            PdfPTable contab = new PdfPTable(27);
//            contab.setWidthPercentage(100);
//            PdfPCell concell;
//
//            for (int i = 0; listKelas7A.size() > i; i++) {
//                if (i == 0) {
//                    total = 0;
//                }
//                HashMap map = listKelas7A.get(i);
//                String no = map.get("no").toString();
//                String nis = map.get("nis").toString();
//                String nama = map.get("nama").toString();
//                String jk = map.get("jk").toString();
//                String agama = map.get("agama").toString();
//                String ttl = map.get("ttl").toString();
//                String alamat = map.get("alamat").toString();
//                String no_telp = map.get("no_telp").toString();
//                String sakit = map.get("sakit").toString();
//                String izin = map.get("izin").toString();
//                String alpha = map.get("alpha").toString();
//
////                int intAmt = Integer.parseInt(amt);
////                total += intAmt;
//
//                concell = new PdfPCell(new Paragraph("" + no, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setPadding(5);
//                concell.setBorder(0);
//                concell.setColspan(1);
//                concell.setBorderWidthLeft(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + jk, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + agama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(2);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + ttl, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(4);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alamat, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + no_telp, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + sakit, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + izin, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alpha, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//            }
//            document.add(contab);

            document.close();

            Toast.makeText(context, "Absensi Kelas " + kelas9A + " Created... :) \n" +
                    "Created in /storage/emulate/0/ta_spp/Absensi", Toast.LENGTH_SHORT).show();

        } catch (
                DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
    }


    private void createPdf9B() throws FileNotFoundException,DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Absensi", "Created a new directory for PDF");
        }
        pdfFile = new File(docsFolder.getAbsolutePath(), "Kelas " + kelas9B + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A2.rotate());
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();
        try {

            Font mOrderDetailsTitleFont = new Font(mainheadone);
            Chunk mOrderDetailsTitleChunk = new Chunk("YAYASAN PENDIDIKAN DELAPAN DELAPAN(YP'88)\n" +
                    "SEKOLAH MENENGAH PERTAMA (SMP) HUTAMA\n" +
                    "TAHUN PELAJARAN 2018 / 2019", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(mainheadone);
            Chunk mNamaUmurChunksas = new Chunk("ABSENSI BULAN          :", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("KELAS         :   " + kelas9B, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            document.add(new Paragraph("\n"));


            PdfPTable headtab = new PdfPTable(43);
            headtab.setWidthPercentage(100);
            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell headcell;

            headcell = new PdfPCell(new Paragraph("TANGGAL", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(40);
            headtab.addCell(headcell);
            document.add(headtab);

            headcell = new PdfPCell(new Paragraph("JUMLAH", mainheadone));
            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            headcell.setBorderWidthLeft(1);
            headcell.setBorderWidthRight(1);
            headcell.setBorderWidthTop(1);
            headcell.setBorderWidthBottom(1);
            headcell.setColspan(3);
            headcell.setPadding(5);
            headtab.addCell(headcell);
            document.add(headtab);

            PdfPTable patab = new PdfPTable(43);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(1);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(5);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setColspan(3);
            headcell.setPadding(5);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                pacell = new PdfPCell(new Paragraph(String.valueOf(total), mainhead));
                pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                pacell.setBorder(0);
                pacell.setColspan(1);
                headcell.setPadding(5);
                pacell.setBorderWidthTop(1);
                pacell.setBorderWidthLeft(1);
                pacell.setBorderWidthBottom(1);
                patab.addCell(pacell);
            }


            pacell = new PdfPCell(new Paragraph("S", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("I", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("A", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthBottom(1);
            pacell.setColspan(1);
            headcell.setPadding(5);
            patab.addCell(pacell);


            document.add(patab);


            PdfPTable contab = new PdfPTable(43);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 0; listKelas9B.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                HashMap map = listKelas9B.get(i);
                String no = map.get("no").toString();
                String nis = map.get("nis").toString();
                String nama = map.get("nama").toString();
                String jk = map.get("jk").toString();
                String agama = map.get("agama").toString();
                String ttl = map.get("ttl").toString();
                String alamat = map.get("alamat").toString();
                String no_telp = map.get("no_telp").toString();
                String sakit = map.get("sakit").toString();
                String izin = map.get("izin").toString();
                String alpha = map.get("alpha").toString();

//                int intAmt = Integer.parseInt(amt);
//                total += intAmt;

                concell = new PdfPCell(new Paragraph("" + no, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(3);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);


                for (int is = 0; is < 31; is++) {
                    if (is == 0) {
                        total = 0;
                    }
                    concell = new PdfPCell(new Paragraph("", listvalues));
                    concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                    concell.setBorder(0);
                    concell.setColspan(1);
                    headcell.setPadding(5);
                    concell.setBorderWidthRight(1);
                    concell.setBorderWidthBottom(1);
                    contab.addCell(concell);
                }
                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("", listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setBorder(0);
                concell.setColspan(1);
                headcell.setPadding(5);
                concell.setBorderWidthRight(1);
                concell.setBorderWidthBottom(1);
                contab.addCell(concell);

            }
            document.add(contab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsasW = new Font(mainhead);
            Chunk mNamaUmurChunksasW = new Chunk("*CATATAN:", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasW = new Paragraph(mNamaUmurChunksasW);
            document.add(mNamaUmurParagraphsasW);

            Chunk mNamaUmurChunksasWq = new Chunk("Jumlah Absen X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWq = new Paragraph(mNamaUmurChunksasWq);
            document.add(mNamaUmurParagraphsasWq);

            Chunk mNamaUmurChunksasWw = new Chunk("Jumlah Siswa X 100 = Presentase", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasWw = new Paragraph(mNamaUmurChunksasWw);
            document.add(mNamaUmurParagraphsasWw);

            Chunk mNamaUmurChunksase = new Chunk("Mengetahui                                                                              Bekasi,          2019", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsase = new Paragraph(mNamaUmurChunksase);
            mNamaUmurParagraphsase.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsase);

            //15tab
            Chunk mNamaUmurChunksasa = new Chunk("Kepala SMP HUTAMA                                                                             Wali Kelas", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            mNamaUmurParagraphsasa.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            //15tab
            Chunk mNamaUmurChunksasaw = new Chunk("MARDIN KARO KARO S PD                                                         -----------------------", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            mNamaUmurParagraphsasaw.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasaw);

            //15tab
            Chunk mNamaUmurChunksasawe = new Chunk("NIP. 19610806 198503 1 013                                                                                            ", mNamaUmurFontsasW);
            Paragraph mNamaUmurParagraphsasawe = new Paragraph(mNamaUmurChunksasawe);
            mNamaUmurParagraphsasawe.setAlignment(Element.ALIGN_RIGHT);
            document.add(mNamaUmurParagraphsasawe);

//            PdfPTable headtab = new PdfPTable(1);
//            headtab.setWidthPercentage(100);
//            headtab.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            PdfPCell headcell;
//
//            headcell = new PdfPCell(new Paragraph("ABSENSI KELAS VII-A", mainheadone));
//            headcell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            headcell.setBorderWidthLeft(1);
//            headcell.setBorderWidthRight(1);
//            headcell.setBorderWidthTop(1);
//            headcell.setBorderWidthBottom(1);
//            headcell.setPadding(5);
//            headtab.addCell(headcell);
//            document.add(headtab);
//
//            PdfPTable patab = new PdfPTable(27);
//            patab.setWidthPercentage(100);
//            PdfPCell pacell;
//
//            pacell = new PdfPCell(new Paragraph("NO", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NAMA SISWA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NIS", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//
//            pacell = new PdfPCell(new Paragraph("JK", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("AGAMA", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(2);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("TTL", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            pacell.setColspan(4);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("ALAMAT", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(5);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("NO TELEPON", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(3);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);

//            pacell = new PdfPCell(new Paragraph("S", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("I", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            pacell = new PdfPCell(new Paragraph("A", mainhead));
//            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//            pacell.setBorder(0);
//            pacell.setPadding(5);
//            pacell.setColspan(1);
//            pacell.setBorderWidthLeft(1);
//            pacell.setBorderWidthRight(1);
//            pacell.setBorderWidthBottom(1);
//            patab.addCell(pacell);
//
//            document.add(patab);

//            /*Set the item details*/
//            PdfPTable contab = new PdfPTable(27);
//            contab.setWidthPercentage(100);
//            PdfPCell concell;
//
//            for (int i = 0; listKelas7A.size() > i; i++) {
//                if (i == 0) {
//                    total = 0;
//                }
//                HashMap map = listKelas7A.get(i);
//                String no = map.get("no").toString();
//                String nis = map.get("nis").toString();
//                String nama = map.get("nama").toString();
//                String jk = map.get("jk").toString();
//                String agama = map.get("agama").toString();
//                String ttl = map.get("ttl").toString();
//                String alamat = map.get("alamat").toString();
//                String no_telp = map.get("no_telp").toString();
//                String sakit = map.get("sakit").toString();
//                String izin = map.get("izin").toString();
//                String alpha = map.get("alpha").toString();
//
////                int intAmt = Integer.parseInt(amt);
////                total += intAmt;
//
//                concell = new PdfPCell(new Paragraph("" + no, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setPadding(5);
//                concell.setBorder(0);
//                concell.setColspan(1);
//                concell.setBorderWidthLeft(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nis, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + nama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + jk, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + agama, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(2);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + ttl, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(4);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alamat, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(5);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + no_telp, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(3);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + sakit, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + izin, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//                concell = new PdfPCell(new Paragraph("" + alpha, listvalues));
//                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
//                concell.setBorder(0);
//                concell.setPadding(5);
//                concell.setColspan(1);
//                concell.setBorderWidthRight(1);
//                concell.setBorderWidthBottom(1);
//                contab.addCell(concell);
//
//            }
//            document.add(contab);

            document.close();

            Toast.makeText(context, "Absensi Kelas " + kelas9B + " Created... :) \n" +
                    "Created in /storage/emulate/0/ta_spp/Absensi", Toast.LENGTH_SHORT).show();


        } catch (
                DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        }
    }

    private void previewPdf() {

        PackageManager packageManager = getContext().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                startActivity(intent);
            }

        } else {
            Toast.makeText(getContext(), "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }


    /// ini untuk string random
//    protected String getSaltString() {
//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//        while (salt.length() < 18) { // length of the random string.
//            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//            salt.append(SALTCHARS.charAt(index));
//        }
//        String saltStr = salt.toString();
//        return saltStr;
//    }

    public void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        strDate = mdformat.format(calendar.getTime());
    }

}
