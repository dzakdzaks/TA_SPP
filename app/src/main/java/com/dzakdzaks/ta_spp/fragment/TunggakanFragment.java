package com.dzakdzaks.ta_spp.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dzakdzaks.ta_spp.MainActivity;
import com.dzakdzaks.ta_spp.Permission.PermissionsActivity;
import com.dzakdzaks.ta_spp.Permission.PermissionsChecker;
import com.dzakdzaks.ta_spp.R;
import com.dzakdzaks.ta_spp.api.ApiClient;
import com.dzakdzaks.ta_spp.api.ApiInterface;
import com.dzakdzaks.ta_spp.global.FileUtils;
import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.dzakdzaks.ta_spp.response.ResponseLogin;
import com.dzakdzaks.ta_spp.response.User;
import com.dzakdzaks.ta_spp.session.UserSession;
import com.google.android.gms.common.internal.GmsLogger;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
import java.util.Arrays;
import java.util.Calendar;
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
public class TunggakanFragment extends Fragment {

    @BindView(R.id.tvSiswaa)
    TextView tvSiswa;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    UserSession session;
    GlobalVariable globalVariable;
    @BindView(R.id.linearPusat)
    LinearLayout linearPusat;
    @BindView(R.id.board)
    Button board;
    @BindView(R.id.payment_total_value)
    TextView paymentTotalValue;
    @BindView(R.id.linearTotal)
    RelativeLayout linearTotal;
    @BindView(R.id.btn_cetak_tgk)
    Button btnCetak;
    @BindView(R.id.rvTunggak)
    ListView rvTunggak;
    @BindView(R.id.rvTunggak1)
    ListView rvTunggak1;
    @BindView(R.id.card)
    CardView card;

    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;


    Font mainheadone = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f,
            Font.BOLD);
    Font mainhead = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f,
            Font.BOLD);
    Font subhead = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f,
            Font.NORMAL);
    Font listvalues = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f,
            Font.NORMAL);

    ArrayList<String> myList;
    ArrayList<String> myList1;
    ArrayList<Integer> resultList;

    int sum;

    String nama, nisSiswa, alamatSiswa, kelasSiswa;
    int total = 0;


    public TunggakanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tunggakan, container, false);
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
        tvSiswa.setText(session.getSpName() + " Kelas " + session.getSpKelas());
        getPayment();

        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission();
            }
        });
    }


    private void getPayment() {
        String nis = session.getSpNis();
        Log.d("nis", nis);
        globalVariable.showProgressBar(progressBar, getActivity());
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseLogin> call = apiInterface.getUserByNis(nis);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                String msg = response.body().getMsg();
                String error = response.body().getError();
                if (response.isSuccessful()) {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    if (error.equals("false")) {
                        User user = response.body().getUser();
                        Log.d("kampuy", user.getNamaUser());

                        nama = user.getNamaUser();
                        nisSiswa = user.getNisUser();
                        alamatSiswa = user.getAlamatUser();
                        kelasSiswa = user.getKelasUser();
                        String pay1 = user.getPayment1();
                        String valPay1 = user.getValuePayment1();
                        Log.d("kampret", pay1);


                        //ngubah string ke arraylist string dengan menghilangkan koma
                        myList = new ArrayList<String>(Arrays.asList(pay1.split(",")));
                        myList.add(user.getNamaPayment());
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                myList);
                        rvTunggak.setAdapter(arrayAdapter);

                        myList1 = new ArrayList<String>(Arrays.asList(valPay1.split(",")));
                        myList1.add(user.getValuePayment());
                        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                myList1);
                        rvTunggak1.setAdapter(arrayAdapter1);


                        // convert arraylist string ke arraylist integer
                        resultList = globalVariable.getIntegerArray(myList1);
                        //menjumlah dari value payment
                        sum = 0;
                        for (int i = 0; i < resultList.size(); i++) {
                            sum += resultList.get(i);
                            paymentTotalValue.setText("Rp." + sum);
                        }

//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    } else {
                        globalVariable.hideProgressBar(progressBar, getActivity());
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    globalVariable.hideProgressBar(progressBar, getActivity());
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                globalVariable.hideProgressBar(progressBar, getActivity());
//                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void requestStoragePermission() {
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
                                createPdf();
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


    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/TA_SPP/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("Tunggakkan", "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(), "Tunggakan Siswa " + nama + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(10, 10, 12, 12);
        document.addCreationDate();
        document.addAuthor("Rahma");
        document.addCreator("Dzakdzaks");
        PdfWriter.getInstance(document, output);
        document.open();

        /***
         * Variables for further use....
         */
        BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
        float mHeadingFontSize = 14.0f;
        float mValueFontSize = 16.0f;

        /**
         * How to USE FONT....
         */
        BaseFont urName = null;
        try {
            urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // LINE SEPARATOR
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

        try {

            Font mOrderDetailsTitleFont = new Font(urName, 24.0f, Font.BOLD, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk = new Chunk("Surat Tunggakan", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            document.add(new Paragraph("\n"));

            document.add(new Chunk(lineSeparator));

            document.add(new Paragraph("\n"));

            Font mNamaUmurFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaUmurChunk = new Chunk("Assalamualaikum Wr.Wb.\n" +
                    "Segala puji bagi ALLAH SWT yang telah memperlihatkan rahmat taufiq dan hidayah-Nya kepada kita. Sholawat serta salam agar tetap terlimpahkan kepada baginda Rasulullah SAW, beserta keluarga, sahabat dan pengikutnya. AAMIIN.", mNamaUmurFont);
            Paragraph mNamaUmurParagraph = new Paragraph(mNamaUmurChunk);
            document.add(mNamaUmurParagraph);

            Font mNamaUmurFonts = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaUmurChunks = new Chunk("Sehubungan dengan berakhirnya tahun pelajaran  2019/2020 di Sekolah Menengah Pertama Hutama, maka kami selaku Bendahara sekolah memberitahukan bahwa siswa yang berikut:", mNamaUmurFonts);
            Paragraph mNamaUmurParagraphs = new Paragraph(mNamaUmurChunks);
            document.add(mNamaUmurParagraphs);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontsas = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaUmurChunksas = new Chunk("NIS                    :  " + nisSiswa, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsas = new Paragraph(mNamaUmurChunksas);
            document.add(mNamaUmurParagraphsas);

            Chunk mNamaUmurChunksass = new Chunk("NAMA              :  " + nama, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsass = new Paragraph(mNamaUmurChunksass);
            document.add(mNamaUmurParagraphsass);

            Chunk mNamaUmurChunksassa = new Chunk("KELAS              :  " + kelasSiswa, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsassa = new Paragraph(mNamaUmurChunksassa);
            document.add(mNamaUmurParagraphsassa);

            Chunk mNamaUmurChunksasa = new Chunk("ALAMAT          :  " + alamatSiswa, mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsasa = new Paragraph(mNamaUmurChunksasa);
            document.add(mNamaUmurParagraphsasa);

            document.add(new Paragraph("\n"));

            Chunk mNamaUmurChunksasaw = new Chunk("Mempunyai tanggungan sebagai berikut:", mNamaUmurFontsas);
            Paragraph mNamaUmurParagraphsasaw = new Paragraph(mNamaUmurChunksasaw);
            document.add(mNamaUmurParagraphsasaw);


            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable patab = new PdfPTable(12);
            patab.setWidthPercentage(100);
            PdfPCell pacell;

            pacell = new PdfPCell(new Paragraph("NO", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setPadding(5);
            pacell.setColspan(2);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthBottom(1);
            pacell.setBorderWidthTop(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("Nama Tunggakan", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setPadding(5);
            pacell.setColspan(5);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            pacell = new PdfPCell(new Paragraph("Jumlah Pembayaran", mainhead));
            pacell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
            pacell.setBorder(0);
            pacell.setPadding(5);
            pacell.setColspan(5);
            pacell.setBorderWidthLeft(1);
            pacell.setBorderWidthRight(1);
            pacell.setBorderWidthTop(1);
            pacell.setBorderWidthBottom(1);
            patab.addCell(pacell);

            document.add(patab);

            PdfPTable contab = new PdfPTable(12);
            contab.setWidthPercentage(100);
            PdfPCell concell;

            for (int i = 1; myList.size() > i && myList1.size() > i; i++) {
                if (i == 0) {
                    total = 0;
                }
                total++;
                concell = new PdfPCell(new Paragraph(String.valueOf(total), listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setPadding(5);
                concell.setBorder(0);
                concell.setColspan(2);
                concell.setBorderWidthLeft(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph(myList.get(i), listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setPadding(5);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthLeft(1);
                contab.addCell(concell);

                concell = new PdfPCell(new Paragraph("Rp." + myList1.get(i), listvalues));
                concell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                concell.setPadding(5);
                concell.setBorder(0);
                concell.setColspan(5);
                concell.setBorderWidthLeft(1);
                concell.setBorderWidthRight(1);
                contab.addCell(concell);

            }

            document.add(contab);

            PdfPTable tottab = new PdfPTable(12);
            tottab.setWidthPercentage(100);
            PdfPCell totacell;

            totacell = new PdfPCell(new Paragraph("Total Amount : ", mainhead));
            totacell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
            totacell.setBorder(0);
            totacell.setPadding(5);
            totacell.setColspan(10);
            totacell.setBorderWidthLeft(1);
            totacell.setBorderWidthTop(1);
            totacell.setBorderWidthBottom(1);
            tottab.addCell(totacell);

            totacell = new PdfPCell(new Paragraph("Rp." + sum, mainhead));
            totacell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
            totacell.setBorder(0);
            totacell.setPadding(5);
            totacell.setColspan(2);
            totacell.setBorderWidthRight(1);
            totacell.setBorderWidthTop(1);
            totacell.setBorderWidthBottom(1);
            tottab.addCell(totacell);
            document.add(tottab);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFonta = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaUmurChunka = new Chunk("Demikian surat pemberitahuan dari kami atas perhatian dan partisipasinya kami sampaikan terima kasih. Teriring doa JAZAKUMULLAH KHAIRAN KATSIRAN.\n" +
                    "Wassalamualaikum wr.wb.  ", mNamaUmurFonta);
            Paragraph mNamaUmurParagrapha = new Paragraph(mNamaUmurChunka);
            document.add(mNamaUmurParagrapha);

            document.add(new Paragraph("\n"));

            Font mNamaUmurFontaa = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mNamaUmurChunkaa = new Chunk("Mengetahui:", mNamaUmurFontaa);
            Paragraph mNamaUmurParagraphaa = new Paragraph(mNamaUmurChunkaa);
            mNamaUmurParagraphaa.setAlignment(Element.ALIGN_CENTER);
            document.add(mNamaUmurParagraphaa);

            Chunk mttdChunk = new Chunk("Bendahara", mNamaUmurFontaa);
            Paragraph mttdParagraph = new Paragraph(mttdChunk);
            mttdParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(mttdParagraph);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            Chunk maChunk = new Chunk("Pak Bendahara yang Tampan", mNamaUmurFontaa);
            Paragraph maParagraph = new Paragraph(maChunk);
            maParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(maParagraph);

            Chunk maeChunk = new Chunk("___________________________", mNamaUmurFontaa);
            Paragraph maeParagraph = new Paragraph(maeChunk);
            maeParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(maeParagraph);


//
//        Font mNamaUmurFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamaUmurChunk = new Chunk("Umur : " + nama + "                                                   " + "Pekerjaan: " + nama, mNamaUmurFont);
//        Paragraph mNamaUmurParagraph = new Paragraph(mNamaUmurChunk);
//        document.add(mNamaUmurParagraph);
//
//        Font mNamaAlamatFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamaAlamatChunk = new Chunk("Alamat : " + nama, mNamaAlamatFont);
//        Paragraph mNamaAlamatParagraph = new Paragraph(mNamaAlamatChunk);
//        document.add(mNamaAlamatParagraph);
//
////            document.add(new Paragraph("\n"));
////            document.add(new Paragraph("\n"));
//
//        Font mNamaAnakFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamaAnakChunk = new Chunk("Jumlah Anak : " + nama, mNamaAnakFont);
//        Paragraph mNamaAnakParagraph = new Paragraph(mNamaAnakChunk);
//        document.add(mNamaAnakParagraph);
//
//        Font mNamaStatusFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamaStatusChunk = new Chunk("Status Pasien KB : " + nama, mNamaStatusFont);
//        Paragraph mNamaStatusParagraph = new Paragraph(mNamaStatusChunk);
//        document.add(mNamaStatusParagraph);
//
//        Font mNamahamilFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamahamilChunk = new Chunk("Hamil : " + nama + "                                                " + "Tekanan Darah: " + nama, mNamahamilFont);
//        Paragraph mNamahamilParagraph = new Paragraph(mNamahamilChunk);
//        document.add(mNamahamilParagraph);
//
//        Font mNamabbFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamabbChunk = new Chunk("Berat Badan : " + nama + "kg" + "                                        " + "Haid Terakhir: " + nama, mNamabbFont);
//        Paragraph mNamabbParagraph = new Paragraph(mNamabbChunk);
//        document.add(mNamabbParagraph);
//
//
//        // Adding Line Breakable Space....
////            document.add(new Paragraph("\n"));
//        // Adding Horizontal Line...
//        document.add(new Chunk(lineSeparator));
//        // Adding Line Breakable Space....
//        document.add(new Paragraph(""));
//
//        Font mNamaKeadaanFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
//        Chunk mNamaKeadaanChunk = new Chunk("Keadaan Peserta KB saat ini : \n" +
//                "   a. Sakit Kuning : " + nama + "\n" +
//                "   b. Pendarahan Pervaginaan : " + nama + "\n" +
//                "   c. Tumor : " + nama + "\n" +
//                "   d. HIV/AIDS : " + nama, mNamaKeadaanFont);
//        Paragraph mNamaKeadaanParagraph = new Paragraph(mNamaKeadaanChunk);
//        document.add(mNamaKeadaanParagraph);
//
////            document.add(new Paragraph("\n"));
//
//        Chunk mNamaPosisiChunk = new Chunk("Posisi Rahim : " + nama, mNamaKeadaanFont);
//        Paragraph mNamaPosisiParagraph = new Paragraph(mNamaPosisiChunk);
//        document.add(mNamaPosisiParagraph);
//
//        Chunk mNamaDiabetesChunk = new Chunk("Diabetes : " + nama, mNamaKeadaanFont);
//        Paragraph mNamaDiabetesParagraph = new Paragraph(mNamaDiabetesChunk);
//        document.add(mNamaDiabetesParagraph);
//
//        Chunk mNamaPembDarahChunk = new Chunk("Pembekuan Darah : " + nama, mNamaKeadaanFont);
//        Paragraph mNamaPembDarahParagraph = new Paragraph(mNamaPembDarahChunk);
//        document.add(mNamaPembDarahParagraph);
//
//        // Adding Horizontal Line...
//        document.add(new Chunk(lineSeparator));
//        // Adding Line Breakable Space....
//        document.add(new Paragraph(""));
//
//        Chunk mNamaygChunk = new Chunk("Kontrasepsi yang diberikan : " + nama, mNamaKeadaanFont);
//        Paragraph mNamaygParagraph = new Paragraph(mNamaygChunk);
//        document.add(mNamaygParagraph);
//
//        Chunk mNamatglLayaniChunk = new Chunk("Tanggal dilayani : " + nama, mNamaKeadaanFont);
//        Paragraph mNamatglLayaniParagraph = new Paragraph(mNamatglLayaniChunk);
//        document.add(mNamatglLayaniParagraph);
//
//        Chunk mNamatglKembaliChunk = new Chunk("Tanggal dipesan kembali : " + nama, mNamaKeadaanFont);
//        Paragraph mNamatglKembaliParagraph = new Paragraph(mNamatglKembaliChunk);
//        document.add(mNamatglKembaliParagraph);
//
//        // Adding Horizontal Line...
//        document.add(new Chunk(lineSeparator));
//        // Adding Line Breakable Space....
//        document.add(new Paragraph(""));
//
//        Chunk mttdChunk = new Chunk("Penanggung Jawab Pelayanan KB", mNamaKeadaanFont);
//        Paragraph mttdParagraph = new Paragraph(mttdChunk);
//        mttdParagraph.setAlignment(Element.ALIGN_RIGHT);
//        document.add(mttdParagraph);
//
//        Chunk mwChunk = new Chunk("Dokter/Bidan/Perawat Kesehatan", mNamaKeadaanFont);
//        Paragraph mwParagraph = new Paragraph(mwChunk);
//        mwParagraph.setAlignment(Element.ALIGN_RIGHT);
//        document.add(mwParagraph);
//
//        document.add(new Paragraph("\n"));
//        document.add(new Paragraph("\n"));
//
//        Chunk maChunk = new Chunk(nama, mNamaKeadaanFont);
//        Paragraph maParagraph = new Paragraph(maChunk);
//        maParagraph.setAlignment(Element.ALIGN_RIGHT);
//        document.add(maParagraph);
//
//        Chunk maeChunk = new Chunk("___________________________", mNamaKeadaanFont);
//        Paragraph maeParagraph = new Paragraph(maeChunk);
//        maeParagraph.setAlignment(Element.ALIGN_RIGHT);
//        document.add(maeParagraph);
//
//        Chunk marChunk = new Chunk("NIP. " + nama, mNamaKeadaanFont);
//        Paragraph marParagraph = new Paragraph(marChunk);
//        marParagraph.setAlignment(Element.ALIGN_RIGHT);
//        document.add(marParagraph);

            document.close();
            Toast.makeText(getContext(), "Tunggakan Siswa " + nama + " Created... :) \n" +
                    "Created in /storage/emulate/0/TA_SPP/", Toast.LENGTH_SHORT).show();
        } catch (DocumentException e) {
            e.printStackTrace();
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

}