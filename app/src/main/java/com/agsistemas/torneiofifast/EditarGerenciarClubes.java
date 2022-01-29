package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditarGerenciarClubes extends Activity {

    ArrayList<Jogador> listGoleiros = new ArrayList<>();
    AdapterGerClubes arrayAdapterGoleiros;

    ArrayList<Jogador> listDefesa = new ArrayList<>();
    AdapterGerClubes arrayAdapterDefesa;

    ArrayList<Jogador> listMeio = new ArrayList<>();
    AdapterGerClubes arrayAdapterMeio;

    ArrayList<Jogador> listAtaque = new ArrayList<>();
    AdapterGerClubes arrayAdapterAtaque;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    ListView listGol, listDef, listMc, listAta;

    Jogador g, p, m, a;

    int qJGol, qJdef, qJmeio, qJata;

    String id, imagem, imgPerfil, time, nome, oD, oM, oA;

    ImageView escudoTime, imgPlayer;
    TextView nomeTime, mediaDef, mediaMeio, mediaAta, qJogadores;

    final int[] images = new int[33];
    List<Integer> imagemJogadores = new ArrayList<>();

    ArrayList<Competidor> listUserLogado = new ArrayList<>();
    Competidor uL;

    String uLogadoId;

    android.widget.Spinner selecionarFormacao, sGol, sLe, sLd, sZag1, sZag2, sZag3, sVol1, sVol2, sMc1, sMc2, sMd, sMe, sMei1, sMei2, sMei3, sPe, sPd, sAta1, sAta2;
    TextView formacaoSelecionada;
    ImageButton salvarFormacao;
    Competidor formacaoDados;

    ImageButton p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19;
    TextView o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11, o12, o13, o14, o15, o16, o17, o18, o19;
    ConstraintLayout gol1, zag1, zag2, zag3, le, ld, vol1, vol2, mc1, mc2, me, md, mei1, mei2, mei3, pe, pd, ata1, ata2;

    String posicaoimgGol, overalGol, idGol,
    posicaoimgLe, overalLe, idLe,
    posicaoimgLd, overalLd, idLd,
    posicaoZag1, overalZag1, idZag1,
    posicaoZag2, overalZag2, idZag2,
    posicaoZag3, overalZag3, idZag3,
    posicaoVol1, overalVol1, idVol1,
    posicaoVol2, overalVol2, idVol2,
    posicaoMc1, overalMc1, idMc1,
    posicaoMc2, overalMc2, idMc2,
    posicaoMd, overalMd, idmd,
    posicaoMe, overalMe, idMe,
    posicaoMei1, overalMei1, idMei1,
    posicaoMei2, overalMei2, idMei2,
    posicaoMei3, overalMei3, idMei3,
    posicaoAta1, overalAta1, idAta1,
    posicaoAta2, overalAta2, idAta2,
    posicaoPd, overalPd, idPd,
    posicaoPe, overalPe, idPe;

    List<String> goleiros = new ArrayList<String>();
    List<String> jogadoresLinha = new ArrayList<String>();
    List<String> oGol = new ArrayList<String>();
    List<String> idGoleiro = new ArrayList<String>();
    List<String> pImagemGol = new ArrayList<String>();
    List<String> idJogogadores = new ArrayList<String>();
    List<String> oJogadoresLinha = new ArrayList<String>();
    List<String> imgJogadoresLinha = new ArrayList<String>();

    String formacoes[] =
            {"", "4-4-2", "4-4-2(2)", "4-5-1", "4-5-1(2)", "4-1-2-1-2", "4-1-2-1-2(2)", "4-2-2-2", "4-2-3-1", "4-2-3-1(2)", "4-3-3", "3-1-4-2", "3-4-1-2", "3-4-2-1",  "3-4-3", "3-5-2", "5-2-1-2", "5-2-3", "5-3-2", "5-4-1" };

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_gerenciar_clubes);

        mediaPlayer = MediaPlayer.create(EditarGerenciarClubes.this, R.raw.efeito6);

        inicializarFirebase();
        listarJogadoresGol();
        listarJogadoresDef();
        listarJogadoresMc();
        listarJogadoresAta();
        imagensEscudos();
        imagensJogador();
        getUsuarioLogado();

        escudoTime = findViewById(R.id.escudoTime);
        imgPlayer = findViewById(R.id.imgPlayer);
        nomeTime = findViewById(R.id.nomeTime);
        mediaDef = findViewById(R.id.mediaDef);
        mediaMeio = findViewById(R.id.mediaMeio);
        mediaAta = findViewById(R.id.mediaAta);
        qJogadores = findViewById(R.id.qJogadores);
        selecionarFormacao = findViewById(R.id.selecionarFormacao);
        formacaoSelecionada = findViewById(R.id.formacaoSelecionada);
        salvarFormacao = findViewById(R.id.salvarFormacao);

        listGol = findViewById(R.id.listGol);
        listDef = findViewById(R.id.listDef);
        listMc = findViewById(R.id.listMc);
        listAta = findViewById(R.id.listAta);

        sGol = findViewById(R.id.selecionarGol);
        sLd = findViewById(R.id.selecionarLd);
        sLe = findViewById(R.id.selecionarLe);
        sZag1 = findViewById(R.id.selecionarZag1);
        sZag2 = findViewById(R.id.selecionarZag2);
        sZag3 = findViewById(R.id.selecionarZag3);
        sVol1 = findViewById(R.id.selecionarVol1);
        sVol2= findViewById(R.id.selecionarVol2);
        sMc1 = findViewById(R.id.selecionarMc1);
        sMc2 = findViewById(R.id.selecionarMc2);
        sMd = findViewById(R.id.selecionarMd);
        sMe = findViewById(R.id.selecionarMe);
        sMei1 = findViewById(R.id.selecionarMei1);
        sMei2 = findViewById(R.id.selecionarMei2);
        sMei3 = findViewById(R.id.selecionarMei3);
        sPd = findViewById(R.id.selecionarPd);
        sPe = findViewById(R.id.selecionarPe);
        sAta1 = findViewById(R.id.selecionarAta1);
        sAta2 = findViewById(R.id.selecionarAta2);

        gol1 = findViewById(R.id.gol1);
        zag1 = findViewById(R.id.zag1);
        zag2 = findViewById(R.id.zag2);
        zag3 = findViewById(R.id.zag3);
        le = findViewById(R.id.le);
        ld = findViewById(R.id.ld);
        vol1 = findViewById(R.id.vol1);
        vol2 = findViewById(R.id.vol2);
        mc1 = findViewById(R.id.mc1);
        mc2 = findViewById(R.id.mc2);
        me = findViewById(R.id.me);
        md = findViewById(R.id.md);
        mei1 = findViewById(R.id.mei1);
        mei2 = findViewById(R.id.mei2);
        mei3 = findViewById(R.id.mei3);
        pe = findViewById(R.id.pe);
        pd = findViewById(R.id.pd);
        ata1 = findViewById(R.id.ata1);
        ata2 = findViewById(R.id.ata2);

        o1 = findViewById(R.id.overalP1);
        o2 = findViewById(R.id.overalP2);
        o3 = findViewById(R.id.overalP3);
        o4 = findViewById(R.id.overalP4);
        o5 = findViewById(R.id.overalP5);
        o6 = findViewById(R.id.overalP6);
        o7 = findViewById(R.id.overalP7);
        o8 = findViewById(R.id.overalP8);
        o9 = findViewById(R.id.overalP9);
        o10 = findViewById(R.id.overalP10);
        o11 = findViewById(R.id.overalP11);
        o12 = findViewById(R.id.overalP12);
        o13 = findViewById(R.id.overalP13);
        o14 = findViewById(R.id.overalP14);
        o15 = findViewById(R.id.overalP15);
        o16 = findViewById(R.id.overalP16);
        o17 = findViewById(R.id.overalP17);
        o18 = findViewById(R.id.overalP18);
        o19 = findViewById(R.id.overalP19);

        p1 = findViewById(R.id.imgP1);
        p2 = findViewById(R.id.imgP2);
        p3 = findViewById(R.id.imgP3);
        p4 = findViewById(R.id.imgP4);
        p5 = findViewById(R.id.imgP5);
        p6 = findViewById(R.id.imgP6);
        p7 = findViewById(R.id.imgP7);
        p8 = findViewById(R.id.imgP8);
        p9 = findViewById(R.id.imgP9);
        p10 = findViewById(R.id.imgP10);
        p11 = findViewById(R.id.imgP11);
        p12 = findViewById(R.id.imgP12);
        p13 = findViewById(R.id.imgP13);
        p14 = findViewById(R.id.imgP14);
        p15 = findViewById(R.id.imgP15);
        p16= findViewById(R.id.imgP16);
        p17 = findViewById(R.id.imgP17);
        p18 = findViewById(R.id.imgP18);
        p19 = findViewById(R.id.imgP19);


        Bundle b = new Bundle();
        b = getIntent().getExtras();

        id = b.getString("id");
        imagem = b.getString("imagem");
        time = b.getString("time");
        nome = b.getString("nome");
        imgPerfil = b.getString("imgPerfil");
        oD = b.getString("oDEF");
        oM = b.getString("oMEI");
        oA = b.getString("oATA");

        uLogadoId = b.getString("idLogado");

        Picasso.with(EditarGerenciarClubes.this).load(imgPerfil).into(imgPlayer);
        escudoTime.setImageResource( images[ Integer.parseInt( imagem ) ] );
        nomeTime.setText(nome + " / " + time);
        nomeTime.setVisibility(View.INVISIBLE);

         selecionarFormacao.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
         selecionarFormacao.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, formacoes));
         selecionarFormacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (selecionarFormacao.getSelectedItem().equals("")) {

                            getFormacaoDados();

                        } else {
                            formacaoSelecionada.setText(selecionarFormacao.getSelectedItem().toString());
                        }

                        resetFormacao();
                        configFormacao();


         }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
         });




        salvarFormacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listUserLogado.get(0).getNivelAcesso().equals("ADMINISTRADOR")){

                    progressDialog = new ProgressDialog(EditarGerenciarClubes.this, R.style.MyAlertDialogStyle );
                    //Configura o título da progress dialog
                    progressDialog.setTitle("Aguarde");
                    //configura a mensagem de que está sendo feito o carregamento
                    progressDialog.setMessage("Carregando!!!");
                    //configura se a progressDialog pode ser cancelada pelo usuário
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    //formação
                    databaseReference.child("Competidores").child(id).child("formacaoPadrao").setValue(formacaoSelecionada.getText().toString());

                    //goleiro
                    databaseReference.child("Competidores").child(id).child("p1Img").setValue(posicaoimgGol);
                    databaseReference.child("Competidores").child(id).child("p1Over").setValue(o1.getText().toString());

                    //LD
                    databaseReference.child("Competidores").child(id).child("p6Img").setValue(posicaoimgLd);
                    databaseReference.child("Competidores").child(id).child("p6Over").setValue(o6.getText().toString());

                    //LE
                    databaseReference.child("Competidores").child(id).child("p2Img").setValue(posicaoimgLe);
                    databaseReference.child("Competidores").child(id).child("p2Over").setValue(o2.getText().toString());

                    //ZAG01
                    databaseReference.child("Competidores").child(id).child("p3Img").setValue(posicaoZag1);
                    databaseReference.child("Competidores").child(id).child("p3Over").setValue(o3.getText().toString());

                    //ZAG02
                    databaseReference.child("Competidores").child(id).child("p4Img").setValue(posicaoZag2);
                    databaseReference.child("Competidores").child(id).child("p4Over").setValue(o4.getText().toString());

                    //ZAG03
                    databaseReference.child("Competidores").child(id).child("p5Img").setValue(posicaoZag3);
                    databaseReference.child("Competidores").child(id).child("p5Over").setValue(o5.getText().toString());

                    //VOL01
                    databaseReference.child("Competidores").child(id).child("p9Img").setValue(posicaoVol1);
                    databaseReference.child("Competidores").child(id).child("p9Over").setValue(o9.getText().toString());

                    //VOL02
                    databaseReference.child("Competidores").child(id).child("p10Img").setValue(posicaoVol2);
                    databaseReference.child("Competidores").child(id).child("p10Over").setValue(o10.getText().toString());

                    //MC01
                    databaseReference.child("Competidores").child(id).child("p8Img").setValue(posicaoMc1);
                    databaseReference.child("Competidores").child(id).child("p8Over").setValue(o8.getText().toString());

                    //MC02
                    databaseReference.child("Competidores").child(id).child("p11Img").setValue(posicaoMc2);
                    databaseReference.child("Competidores").child(id).child("p11Over").setValue(o11.getText().toString());

                    //MD
                    databaseReference.child("Competidores").child(id).child("p12Img").setValue(posicaoMd);
                    databaseReference.child("Competidores").child(id).child("p12Over").setValue(o12.getText().toString());

                    //ME
                    databaseReference.child("Competidores").child(id).child("p7Img").setValue(posicaoMe);
                    databaseReference.child("Competidores").child(id).child("p7Over").setValue(o7.getText().toString());

                    //MEI01
                    databaseReference.child("Competidores").child(id).child("p13Img").setValue(posicaoMei1);
                    databaseReference.child("Competidores").child(id).child("p13Over").setValue(o13.getText().toString());

                    //MEI02
                    databaseReference.child("Competidores").child(id).child("p14Img").setValue(posicaoMei2);
                    databaseReference.child("Competidores").child(id).child("p14Over").setValue(o14.getText().toString());

                    //MEI03
                    databaseReference.child("Competidores").child(id).child("p15Img").setValue(posicaoMei3);
                    databaseReference.child("Competidores").child(id).child("p15Over").setValue(o15.getText().toString());

                    //PD
                    databaseReference.child("Competidores").child(id).child("p19Img").setValue(posicaoPd);
                    databaseReference.child("Competidores").child(id).child("p19Over").setValue(o19.getText().toString());

                    //PE
                    databaseReference.child("Competidores").child(id).child("p16Img").setValue(posicaoPe);
                    databaseReference.child("Competidores").child(id).child("p16Over").setValue(o16.getText().toString());

                    //ATA01
                    databaseReference.child("Competidores").child(id).child("p17Img").setValue(posicaoAta1);
                    databaseReference.child("Competidores").child(id).child("p17Over").setValue(o17.getText().toString());

                    //ATA02
                    databaseReference.child("Competidores").child(id).child("p18Img").setValue(posicaoAta2);
                    databaseReference.child("Competidores").child(id).child("p18Over").setValue(o18.getText().toString());

                    Toast.makeText(EditarGerenciarClubes.this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else {
                    if (listUserLogado.get(0).getId().equals(id)) {

                        progressDialog = new ProgressDialog(EditarGerenciarClubes.this, R.style.MyAlertDialogStyle );
                        //Configura o título da progress dialog
                        progressDialog.setTitle("Aguarde");
                        //configura a mensagem de que está sendo feito o carregamento
                        progressDialog.setMessage("Carregando!!!");
                        //configura se a progressDialog pode ser cancelada pelo usuário
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        //formação
                        databaseReference.child("Competidores").child(id).child("formacaoPadrao").setValue(formacaoSelecionada.getText().toString());

                        //goleiro
                        databaseReference.child("Competidores").child(id).child("p1Img").setValue(posicaoimgGol);
                        databaseReference.child("Competidores").child(id).child("p1Over").setValue(o1.getText().toString());

                        //LD
                        databaseReference.child("Competidores").child(id).child("p6Img").setValue(posicaoimgLd);
                        databaseReference.child("Competidores").child(id).child("p6Over").setValue(o6.getText().toString());

                        //LE
                        databaseReference.child("Competidores").child(id).child("p2Img").setValue(posicaoimgLe);
                        databaseReference.child("Competidores").child(id).child("p2Over").setValue(o2.getText().toString());

                        //ZAG01
                        databaseReference.child("Competidores").child(id).child("p3Img").setValue(posicaoZag1);
                        databaseReference.child("Competidores").child(id).child("p3Over").setValue(o3.getText().toString());

                        //ZAG02
                        databaseReference.child("Competidores").child(id).child("p4Img").setValue(posicaoZag2);
                        databaseReference.child("Competidores").child(id).child("p4Over").setValue(o4.getText().toString());

                        //ZAG03
                        databaseReference.child("Competidores").child(id).child("p5Img").setValue(posicaoZag3);
                        databaseReference.child("Competidores").child(id).child("p5Over").setValue(o5.getText().toString());

                        //VOL01
                        databaseReference.child("Competidores").child(id).child("p9Img").setValue(posicaoVol1);
                        databaseReference.child("Competidores").child(id).child("p9Over").setValue(o9.getText().toString());

                        //VOL02
                        databaseReference.child("Competidores").child(id).child("p10Img").setValue(posicaoVol2);
                        databaseReference.child("Competidores").child(id).child("p10Over").setValue(o10.getText().toString());

                        //MC01
                        databaseReference.child("Competidores").child(id).child("p8Img").setValue(posicaoMc1);
                        databaseReference.child("Competidores").child(id).child("p8Over").setValue(o8.getText().toString());

                        //MC02
                        databaseReference.child("Competidores").child(id).child("p11Img").setValue(posicaoMc2);
                        databaseReference.child("Competidores").child(id).child("p11Over").setValue(o11.getText().toString());

                        //MD
                        databaseReference.child("Competidores").child(id).child("p12Img").setValue(posicaoMd);
                        databaseReference.child("Competidores").child(id).child("p12Over").setValue(o12.getText().toString());

                        //ME
                        databaseReference.child("Competidores").child(id).child("p7Img").setValue(posicaoMe);
                        databaseReference.child("Competidores").child(id).child("p7Over").setValue(o7.getText().toString());

                        //MEI01
                        databaseReference.child("Competidores").child(id).child("p13Img").setValue(posicaoMei1);
                        databaseReference.child("Competidores").child(id).child("p13Over").setValue(o13.getText().toString());

                        //MEI02
                        databaseReference.child("Competidores").child(id).child("p14Img").setValue(posicaoMei2);
                        databaseReference.child("Competidores").child(id).child("p14Over").setValue(o14.getText().toString());

                        //MEI03
                        databaseReference.child("Competidores").child(id).child("p15Img").setValue(posicaoMei3);
                        databaseReference.child("Competidores").child(id).child("p15Over").setValue(o15.getText().toString());

                        //PD
                        databaseReference.child("Competidores").child(id).child("p19Img").setValue(posicaoPd);
                        databaseReference.child("Competidores").child(id).child("p19Over").setValue(o19.getText().toString());

                        //PE
                        databaseReference.child("Competidores").child(id).child("p16Img").setValue(posicaoPe);
                        databaseReference.child("Competidores").child(id).child("p16Over").setValue(o16.getText().toString());

                        //ATA01
                        databaseReference.child("Competidores").child(id).child("p17Img").setValue(posicaoAta1);
                        databaseReference.child("Competidores").child(id).child("p17Over").setValue(o17.getText().toString());

                        //ATA02
                        databaseReference.child("Competidores").child(id).child("p18Img").setValue(posicaoAta2);
                        databaseReference.child("Competidores").child(id).child("p18Over").setValue(o18.getText().toString());

                        Toast.makeText(EditarGerenciarClubes.this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {
                        alertaSemPermissao();
                    }
                }


            }


        });

            listGol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Jogador jogador = (Jogador) adapterView.getItemAtPosition(i);

                    Intent intent = new Intent(EditarGerenciarClubes.this, EditarJogadorClube.class);
                    intent.putExtra("idJogador", jogador.getIdJogador());
                    intent.putExtra("idClube", jogador.getIdClube());
                    intent.putExtra("imagem", jogador.getImagem());
                    intent.putExtra("imgClubeAssociado", jogador.getImgClubeAssociado());
                    intent.putExtra("nJogador", jogador.getnJogador());
                    intent.putExtra("cJogador", jogador.getcJogador());
                    intent.putExtra("pJogador", jogador.getpJogador());
                    intent.putExtra("oJogador", jogador.getoJogador());
                    intent.putExtra("reflexos", jogador.getnJogador());
                    intent.putExtra("menejo", jogador.getcJogador());
                    intent.putExtra("chute", jogador.getpJogador());
                    intent.putExtra("elasticidade", jogador.getoJogador());
                    intent.putExtra("posicionamento", jogador.getnJogador());

                    intent.putExtra("idLogado", uLogadoId );

                    startActivity(intent);

                }
            });

            listDef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Jogador jogador = (Jogador) adapterView.getItemAtPosition(i);

                    Intent intent = new Intent(EditarGerenciarClubes.this, EditarJogadorClube.class);
                    intent.putExtra("idJogador", jogador.getIdJogador());
                    intent.putExtra("idClube", jogador.getIdClube());
                    intent.putExtra("imagem", jogador.getImagem());
                    intent.putExtra("imgClubeAssociado", jogador.getImgClubeAssociado());
                    intent.putExtra("nJogador", jogador.getnJogador());
                    intent.putExtra("cJogador", jogador.getcJogador());
                    intent.putExtra("pJogador", jogador.getpJogador());
                    intent.putExtra("oJogador", jogador.getoJogador());

                    intent.putExtra("velocidade", jogador.getnJogador());
                    intent.putExtra("finalizacao", jogador.getcJogador());
                    intent.putExtra("passe", jogador.getpJogador());
                    intent.putExtra("drible", jogador.getoJogador());
                    intent.putExtra("defesa", jogador.getnJogador());
                    intent.putExtra("fisico", jogador.getcJogador());
                    intent.putExtra("urlJ", jogador.getpJogador());

                    intent.putExtra("idLogado", uLogadoId );

                    startActivity(intent);

                }
            });

            listMc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Jogador jogador = (Jogador) adapterView.getItemAtPosition(i);

                    Intent intent = new Intent(EditarGerenciarClubes.this, EditarJogadorClube.class);
                    intent.putExtra("idJogador", jogador.getIdJogador());
                    intent.putExtra("idClube", jogador.getIdClube());
                    intent.putExtra("imagem", jogador.getImagem());
                    intent.putExtra("imgClubeAssociado", jogador.getImgClubeAssociado());
                    intent.putExtra("nJogador", jogador.getnJogador());
                    intent.putExtra("cJogador", jogador.getcJogador());
                    intent.putExtra("pJogador", jogador.getpJogador());
                    intent.putExtra("oJogador", jogador.getoJogador());

                    intent.putExtra("velocidade", jogador.getnJogador());
                    intent.putExtra("finalizacao", jogador.getcJogador());
                    intent.putExtra("passe", jogador.getpJogador());
                    intent.putExtra("drible", jogador.getoJogador());
                    intent.putExtra("defesa", jogador.getnJogador());
                    intent.putExtra("fisico", jogador.getcJogador());
                    intent.putExtra("urlJ", jogador.getpJogador());

                    intent.putExtra("idLogado", uLogadoId );

                    startActivity(intent);

                }
            });

            listAta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Jogador jogador = (Jogador) adapterView.getItemAtPosition(i);

                    Intent intent = new Intent(EditarGerenciarClubes.this, EditarJogadorClube.class);
                    intent.putExtra("idJogador", jogador.getIdJogador());
                    intent.putExtra("idClube", jogador.getIdClube());
                    intent.putExtra("imagem", jogador.getImagem());
                    intent.putExtra("imgClubeAssociado", jogador.getImgClubeAssociado());
                    intent.putExtra("nJogador", jogador.getnJogador());
                    intent.putExtra("cJogador", jogador.getcJogador());
                    intent.putExtra("pJogador", jogador.getpJogador());
                    intent.putExtra("oJogador", jogador.getoJogador());

                    intent.putExtra("velocidade", jogador.getnJogador());
                    intent.putExtra("finalizacao", jogador.getcJogador());
                    intent.putExtra("passe", jogador.getpJogador());
                    intent.putExtra("drible", jogador.getoJogador());
                    intent.putExtra("defesa", jogador.getnJogador());
                    intent.putExtra("fisico", jogador.getcJogador());
                    intent.putExtra("urlJ", jogador.getpJogador());

                    intent.putExtra("idLogado", uLogadoId );

                    startActivity(intent);

                }
            });


    }

    private void getFormacaoDados() {
        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    formacaoDados = objSnapshot.getValue(Competidor.class);

                        if(formacaoDados.getId().equals(id)){

                            o1.setText(formacaoDados.getP1Over());
                            p1.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP1Img() ) ) );
                            posicaoimgGol = formacaoDados.getP1Img();

                            o2.setText(formacaoDados.getP2Over());
                            p2.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP2Img() ) ) );
                            posicaoimgLe = formacaoDados.getP2Img();

                            o3.setText(formacaoDados.getP3Over());
                            p3.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP3Img() ) ) );
                            posicaoZag1 = formacaoDados.getP3Img();

                            o4.setText(formacaoDados.getP4Over());
                            p4.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP4Img() ) ) );
                            posicaoZag2 = formacaoDados.getP4Img();

                            o5.setText(formacaoDados.getP5Over());
                            p5.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP5Img() ) ) );
                            posicaoZag3 = formacaoDados.getP5Img();

                            o6.setText(formacaoDados.getP6Over());
                            p6.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP6Img() ) ) );
                            posicaoimgLd = formacaoDados.getP6Img();

                            o7.setText(formacaoDados.getP7Over());
                            p7.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP7Img() ) ) );
                            posicaoMe = formacaoDados.getP7Img();

                            o8.setText(formacaoDados.getP8Over());
                            p8.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP8Img() ) ) );
                            posicaoMc1 = formacaoDados.getP8Img();

                            o9.setText(formacaoDados.getP9Over());
                            p9.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP9Img() ) ) );
                            posicaoVol1 = formacaoDados.getP9Img();

                            o10.setText(formacaoDados.getP10Over());
                            p10.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP10Img() ) ) );
                            posicaoVol2 = formacaoDados.getP10Img();

                            o11.setText(formacaoDados.getP11Over());
                            p11.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP11Img() ) ) );
                            posicaoMc2 = formacaoDados.getP11Img();

                            o12.setText(formacaoDados.getP12Over());
                            p12.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP12Img() ) ) );
                            posicaoMd = formacaoDados.getP12Img();

                            o13.setText(formacaoDados.getP13Over());
                            p13.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP13Img() ) ) );
                            posicaoMei1 = formacaoDados.getP13Img();

                            o14.setText(formacaoDados.getP14Over());
                            p14.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP14Img() ) ) );
                            posicaoMei2 = formacaoDados.getP14Img();

                            o15.setText(formacaoDados.getP15Over());
                            p15.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP15Img() ) ) );
                            posicaoMei3 = formacaoDados.getP15Img();

                            o16.setText(formacaoDados.getP16Over());
                            p16.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP16Img() ) ) );
                            posicaoPe = formacaoDados.getP16Img();

                            o17.setText(formacaoDados.getP17Over());
                            p17.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP17Img() ) ) );
                            posicaoAta1 = formacaoDados.getP17Img();

                            o18.setText(formacaoDados.getP18Over());
                            p18.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP18Img() ) ) );
                            posicaoAta2 = formacaoDados.getP18Img();

                            o19.setText(formacaoDados.getP19Over());
                            p19.setImageResource( imagemJogadores.get( Integer.parseInt( formacaoDados.getP19Img() ) ) );
                            posicaoPd = formacaoDados.getP19Img();

                            formacaoSelecionada.setText(formacaoDados.getFormacaoPadrao());

                            configFormacao();


                        }

                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void configFormacao(){

            if (formacaoSelecionada.getText().toString().equals("4-4-2")) {

                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                mc1.setVisibility(View.INVISIBLE);
                mc2.setVisibility(View.INVISIBLE);
                zag2.setVisibility(View.INVISIBLE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

            } else if (formacaoSelecionada.getText().toString().equals("4-4-2(2)")) {

                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                vol1.setVisibility(View.INVISIBLE);
                vol2.setVisibility(View.INVISIBLE);
                zag2.setVisibility(View.INVISIBLE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");
                p10.setImageResource(R.drawable.villarreall);
                o10.setText("VOL");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

            } else if (formacaoSelecionada.getText().toString().equals("4-5-1")) {

                zag2.setVisibility(View.INVISIBLE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                vol1.setVisibility(View.GONE);
                ata1.setVisibility(View.GONE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");
                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");

                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");

            } else if (formacaoSelecionada.getText().toString().equals("4-5-1(2)")) {

                zag2.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                vol1.setVisibility(View.GONE);
                ata1.setVisibility(View.GONE);


                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");
                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");


                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

            } else if (formacaoSelecionada.getText().toString().equals("4-1-2-1-2")) {


                vol1.setVisibility(View.GONE);
                zag2.setVisibility(View.INVISIBLE);
                mc1.setVisibility(View.INVISIBLE);
                mc2.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");


            } else if (formacaoSelecionada.getText().toString().equals("4-1-2-1-2(2)")) {

                vol1.setVisibility(View.GONE);
                zag2.setVisibility(View.INVISIBLE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");

            } else if (formacaoSelecionada.getText().toString().equals("4-2-2-2")) {

                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mc1.setVisibility(View.INVISIBLE);
                mc2.setVisibility(View.INVISIBLE);
                zag2.setVisibility(View.INVISIBLE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");


            } else if (formacaoSelecionada.getText().toString().equals("4-2-3-1")) {

                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                mc1.setVisibility(View.INVISIBLE);
                mc2.setVisibility(View.INVISIBLE);
                zag2.setVisibility(View.INVISIBLE);
                ata1.setVisibility(View.GONE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

            } else if (formacaoSelecionada.getText().toString().equals("4-2-3-1(2)")) {

                pd.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                mc1.setVisibility(View.GONE);
                mc2.setVisibility(View.GONE);
                zag2.setVisibility(View.INVISIBLE);
                ata1.setVisibility(View.GONE);


                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

            } else if (formacaoSelecionada.getText().toString().equals("4-3-3")) {

                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                mei1.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                zag2.setVisibility(View.INVISIBLE);
                vol1.setVisibility(View.GONE);
                ata1.setVisibility(View.GONE);

                p4.setImageResource(R.drawable.villarreall);
                o4.setText("ZAG");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

            } else if (formacaoSelecionada.getText().toString().equals("3-1-4-2")) {

                mei1.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                ld.setVisibility(View.GONE);
                le.setVisibility(View.GONE);
                vol1.setVisibility(View.GONE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

            } else if (formacaoSelecionada.getText().toString().equals("3-4-1-2")) {

                mei1.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                ld.setVisibility(View.GONE);
                le.setVisibility(View.GONE);
                vol1.setVisibility(View.GONE);
                vol2.setVisibility(View.GONE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");
                p10.setImageResource(R.drawable.villarreall);
                o10.setText("VOL");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");

                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

            } else if (formacaoSelecionada.getText().toString().equals("3-4-2-1")) {

                mei2.setVisibility(View.INVISIBLE);
                ld.setVisibility(View.GONE);
                le.setVisibility(View.GONE);
                mc1.setVisibility(View.GONE);
                mc2.setVisibility(View.GONE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);
                ata1.setVisibility(View.GONE);

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");


            } else if (formacaoSelecionada.getText().toString().equals("3-4-3")) {

                mei1.setVisibility(View.INVISIBLE);
                mei2.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                ata1.setVisibility(View.GONE);
                ld.setVisibility(View.GONE);
                le.setVisibility(View.GONE);
                mc1.setVisibility(View.GONE);
                mc2.setVisibility(View.GONE);

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");


            } else if (formacaoSelecionada.getText().toString().equals("3-5-2")) {

                mei1.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                ld.setVisibility(View.GONE);
                le.setVisibility(View.GONE);
                mc1.setVisibility(View.GONE);
                mc2.setVisibility(View.GONE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

            } else if (formacaoSelecionada.getText().toString().equals("5-2-1-2")) {

                mei1.setVisibility(View.INVISIBLE);
                mei3.setVisibility(View.INVISIBLE);
                mc1.setVisibility(View.GONE);
                mc2.setVisibility(View.GONE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);
                md.setVisibility(View.GONE);
                me.setVisibility(View.GONE);

                p2.setImageResource(R.drawable.villarreall);
                o2.setText("LE");
                p6.setImageResource(R.drawable.villarreall);
                o6.setText("LD");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");


            } else if (formacaoSelecionada.getText().toString().equals("5-2-3")) {

                vol1.setVisibility(View.GONE);
                vol2.setVisibility(View.GONE);
                mei1.setVisibility(View.GONE);
                mei2.setVisibility(View.GONE);
                mei3.setVisibility(View.INVISIBLE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                ata1.setVisibility(View.GONE);

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");
                p10.setImageResource(R.drawable.villarreall);
                o10.setText("VOL");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");


            } else if (formacaoSelecionada.getText().toString().equals("5-3-2")) {

                vol1.setVisibility(View.GONE);
                mei1.setVisibility(View.GONE);
                mei2.setVisibility(View.GONE);
                mei3.setVisibility(View.INVISIBLE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);

                p13.setImageResource(R.drawable.villarreall);
                o13.setText("MEI");
                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");
                p15.setImageResource(R.drawable.villarreall);
                o15.setText("MEI");

                p9.setImageResource(R.drawable.villarreall);
                o9.setText("VOL");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

            } else if (formacaoSelecionada.getText().toString().equals("5-4-1")) {

                mc1.setVisibility(View.GONE);
                mei2.setVisibility(View.INVISIBLE);
                mc2.setVisibility(View.GONE);
                md.setVisibility(View.INVISIBLE);
                me.setVisibility(View.INVISIBLE);
                pd.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);
                ata1.setVisibility(View.GONE);

                p19.setImageResource(R.drawable.villarreall);
                o19.setText("PD");
                p16.setImageResource(R.drawable.villarreall);
                o16.setText("PE");

                p17.setImageResource(R.drawable.villarreall);
                o17.setText("ATA");

                p7.setImageResource(R.drawable.villarreall);
                o7.setText("ME");
                p12.setImageResource(R.drawable.villarreall);
                o12.setText("MD");

                p14.setImageResource(R.drawable.villarreall);
                o14.setText("MEI");

                p8.setImageResource(R.drawable.villarreall);
                o8.setText("MC");
                p11.setImageResource(R.drawable.villarreall);
                o11.setText("MC");

            }
        }

    private void getUsuarioLogado() {

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    uL = objSnapshot.getValue(Competidor.class);

                    if(uL.getId().equals(uLogadoId)){

                        listUserLogado.add(uL);

                    }

                }

                if(listUserLogado.get(0).getNivelAcesso().equals("ADMINISTRADOR")){

                    //----------

                } else {
                    if (listUserLogado.get(0).getId().equals(id)) {

                        //---------

                    } else {

                        sGol.setVisibility(View.INVISIBLE);

                        sLd.setVisibility(View.INVISIBLE);
                        sLe.setVisibility(View.INVISIBLE);
                        sZag1.setVisibility(View.INVISIBLE);
                        sZag2.setVisibility(View.INVISIBLE);
                        sZag3.setVisibility(View.INVISIBLE);

                        sVol1.setVisibility(View.INVISIBLE);
                        sVol2.setVisibility(View.INVISIBLE);

                        sMd.setVisibility(View.INVISIBLE);
                        sMe.setVisibility(View.INVISIBLE);

                        sMc1.setVisibility(View.INVISIBLE);
                        sMc2.setVisibility(View.INVISIBLE);

                        sMei1.setVisibility(View.INVISIBLE);
                        sMei2.setVisibility(View.INVISIBLE);
                        sMei3.setVisibility(View.INVISIBLE);

                        sPd.setVisibility(View.INVISIBLE);
                        sPe.setVisibility(View.INVISIBLE);
                        sAta1.setVisibility(View.INVISIBLE);
                        sAta2.setVisibility(View.INVISIBLE);

                        salvarFormacao.setVisibility(View.INVISIBLE);
                        selecionarFormacao.setVisibility(View.INVISIBLE);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertaSemPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Transferência não autorizada");
        builder.setMessage("Somente o proprietário do time tem acesso a realizar alterações.");
        builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {


            }
        });
        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });
        builder.show();
    }

    private void resetFormacao(){
        gol1.setVisibility(View.VISIBLE);
        //o1.setText("0");
        //p1.setImageResource( R.drawable.reduzidovillarreall );

        le.setVisibility(View.VISIBLE);
        //o2.setText("0");
        //p2.setImageResource( R.drawable.reduzidovillarreall );

        zag1.setVisibility(View.VISIBLE);
        //o3.setText("0");
        //p3.setImageResource( R.drawable.reduzidovillarreall );

        zag2.setVisibility(View.VISIBLE);
        //o4.setText("0");
        //p4.setImageResource( R.drawable.reduzidovillarreall );

        zag3.setVisibility(View.VISIBLE);
        //o5.setText("0");
        //p5.setImageResource( R.drawable.reduzidovillarreall );

        ld.setVisibility(View.VISIBLE);
        //o6.setText("0");
        //p6.setImageResource( R.drawable.reduzidovillarreall );

        me.setVisibility(View.VISIBLE);
        //o7.setText("0");
        //p7.setImageResource( R.drawable.reduzidovillarreall );

        mc1.setVisibility(View.VISIBLE);
        //o8.setText("0");
        //p8.setImageResource( R.drawable.reduzidovillarreall );

        vol1.setVisibility(View.VISIBLE);
        //o9.setText("0");
        //p9.setImageResource( R.drawable.reduzidovillarreall );

        vol2.setVisibility(View.VISIBLE);
        //o10.setText("0");
        //p10.setImageResource( R.drawable.reduzidovillarreall );

        mc2.setVisibility(View.VISIBLE);
       // o11.setText("0");
        //p11.setImageResource( R.drawable.reduzidovillarreall );

        md.setVisibility(View.VISIBLE);
       // o12.setText("0");
       // p12.setImageResource( R.drawable.reduzidovillarreall );

        mei1.setVisibility(View.VISIBLE);
       // o13.setText("0");
       // p13.setImageResource( R.drawable.reduzidovillarreall );

        mei2.setVisibility(View.VISIBLE);
       // o14.setText("0");
        //p14.setImageResource( R.drawable.reduzidovillarreall );

        mei3.setVisibility(View.VISIBLE);
       // o15.setText("0");
       // p15.setImageResource( R.drawable.reduzidovillarreall );

        pe.setVisibility(View.VISIBLE);
        //o16.setText("0");
        //p16.setImageResource( R.drawable.reduzidovillarreall );

        ata1.setVisibility(View.VISIBLE);
        //o17.setText("0");
        //p17.setImageResource( R.drawable.reduzidovillarreall );

        ata2.setVisibility(View.VISIBLE);
       // o18.setText("0");
        //p18.setImageResource( R.drawable.reduzidovillarreall );

        pd.setVisibility(View.VISIBLE);
       // o19.setText("0");
       // p19.setImageResource( R.drawable.reduzidovillarreall );

    }

    private void imagensJogador(){
        CadastroJogador a = new CadastroJogador();
        for( int i = 0; i < a.jogadores.length+1; i++ ){

            imagemJogadores.add( this.getResources().getIdentifier("jogador" + i , "drawable", this.getPackageName() ) );

        }


    }

    private void listarJogadoresGol() {
        progressDialog = new ProgressDialog(EditarGerenciarClubes.this, R.style.MyAlertDialogStyle );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listGoleiros.clear();
                goleiros.add("");
                jogadoresLinha.add("");

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    g = objSnapshot.getValue(Jogador.class);

                    if(g.getIdClube().equals(id)){

                        if(!g.getpJogador().equals("GL")){
                            jogadoresLinha.add( g.getnJogador() );
                            idJogogadores.add( g.getIdJogador() );
                            oJogadoresLinha.add( g.getoJogador() );
                            imgJogadoresLinha.add( g.getImagem() );
                        }

                        if(g.getpJogador().equals("GL") ){
                            listGoleiros.add(g);
                            pImagemGol.add(g.getImagem());
                            idGoleiro.add( g.getIdJogador() );
                            oGol.add(g.getoJogador());
                            goleiros.add(g.getnJogador());

                        }

                    }

                    if(listGoleiros.size() > 0) {

                        qJGol = listGoleiros.size();
                    }


                }

                Collections.sort(listGoleiros);

                arrayAdapterGoleiros = new AdapterGerClubes (getApplicationContext(), listGoleiros);
                listGol.setAdapter(arrayAdapterGoleiros);

                //SELECIONAR GOLEIRO
                sGol.getBackground().setColorFilter(Color.parseColor("#FF000080"), PorterDuff.Mode.CLEAR);
                sGol.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, goleiros));
                sGol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        //mediaPlayer.start();

                        if(sGol.getSelectedItem().equals("")){

                            //getFormacaoDados();

                        } else {

                                for(int j = 0; j < goleiros.size(); j++){
                                    if(i == j){

                                        idGol = idGoleiro.get(j-1);

                                            if( idGol.equals(posicaoimgGol) ){
                                                alertaGoleiroRepetido();
                                                sGol.setSelection(0);

                                            } else  {

                                                p1.setImageResource( imagemJogadores.get( Integer.parseInt(pImagemGol.get(j-1)) ) );
                                                o1.setText("(GOL)" + sGol.getSelectedItem().toString());

                                                overalGol = o1.getText().toString();
                                                posicaoimgGol = pImagemGol.get(j-1);

                                                sGol.setSelection(0);

                                            }



                                    }
                                }

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR LATERAL DIREITO
                sLd.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sLd.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sLd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sLd.getSelectedItem().equals("")){

                            //--------------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){

                                    p6.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o6.setText("(LD)" + sLd.getSelectedItem().toString());

                                    overalLd = o6.getText().toString();
                                    posicaoimgLd = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR LATERAL ESQUERDO
                sLe.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sLe.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sLe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sLe.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p2.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o2.setText("(LE)" + sLe.getSelectedItem().toString());

                                    overalLe = o2.getText().toString();
                                    posicaoimgLe = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR ZAG01
                sZag1.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sZag1.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sZag1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sZag1.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p3.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o3.setText("(ZAG)" + sZag1.getSelectedItem().toString());

                                    overalZag1 = o3.getText().toString();
                                    posicaoZag1 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR ZAG02
                sZag2.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sZag2.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sZag2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sZag2.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p4.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o4.setText("(ZAG)" + sZag2.getSelectedItem().toString());

                                    overalZag2 = o4.getText().toString();
                                    posicaoZag2 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR ZAG03
                sZag3.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sZag3.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sZag3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sZag3.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p5.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o5.setText("(ZAG)" + sZag3.getSelectedItem().toString());

                                    overalZag3 = o5.getText().toString();
                                    posicaoZag3 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR VOL01
                sVol1.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sVol1.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sVol1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sVol1.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p9.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o9.setText("(VOL)" + sVol1.getSelectedItem().toString());

                                    overalVol1 = o9.getText().toString();
                                    posicaoVol1 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR VOL02
                sVol2.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sVol2.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sVol2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sVol2.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p10.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o10.setText("(VOL)" + sVol2.getSelectedItem().toString());

                                    overalVol2 = o10.getText().toString();
                                    posicaoVol2 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR MC01
                sMc1.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMc1.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMc1.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p8.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o8.setText("(MC)" + sMc1.getSelectedItem().toString());

                                    overalMc1 = o8.getText().toString();
                                    posicaoMc1 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR MC02
                sMc2.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMc2.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMc2.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p11.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o11.setText("(MC)" + sMc2.getSelectedItem().toString());

                                    overalMc2 = o11.getText().toString();
                                    posicaoMc2 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR MD
                sMd.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMd.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMd.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p12.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o12.setText("(MD)" + sMd.getSelectedItem().toString());

                                    overalMd = o12.getText().toString();
                                    posicaoMd = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR ME
                sMe.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMe.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMe.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p7.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o7.setText("(ME)" + sMe.getSelectedItem().toString());

                                    overalMe = o7.getText().toString();
                                    posicaoMe = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR MEI01
                sMei1.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMei1.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMei1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMei1.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p13.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o13.setText("(MEI)" + sMei1.getSelectedItem().toString());

                                    overalMei1 = o13.getText().toString();
                                    posicaoMei1 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR MEI02
                sMei2.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMei2.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMei2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMei2.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p14.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o14.setText("(MEI)" + sMei2.getSelectedItem().toString());

                                    overalMei2 = o14.getText().toString();
                                    posicaoMei2 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR MEI03
                sMei3.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sMei3.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sMei3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sMei3.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p15.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o15.setText("(MEI)" + sMei3.getSelectedItem().toString());

                                    overalMei3 = o15.getText().toString();
                                    posicaoMei3 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR PD
                sPd.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sPd.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sPd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sPd.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p19.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o19.setText("(PD)" + sPd.getSelectedItem().toString());

                                    overalPd = o19.getText().toString();
                                    posicaoPd = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR PE
                sPe.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sPe.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sPe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sPe.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p16.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o16.setText("(PE)" + sPe.getSelectedItem().toString());

                                    overalPe = o16.getText().toString();
                                    posicaoPe = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR ATA01
                sAta1.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sAta1.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sAta1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sAta1.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p17.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o17.setText("(ATA)" + sAta1.getSelectedItem().toString());

                                    overalAta1 = o17.getText().toString();
                                    posicaoAta1 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //SELECIONAR ATA02
                sAta2.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.CLEAR);
                sAta2.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadoresLinha));
                sAta2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        if(sAta2.getSelectedItem().equals("")){

                            //-----------

                        } else {

                            for(int j = 0; j < jogadoresLinha.size(); j++){
                                if(i == j){
                                    p18.setImageResource( imagemJogadores.get( Integer.parseInt( imgJogadoresLinha.get(j-1) ) ) );
                                    o18.setText("(ATA)" + sAta2.getSelectedItem().toString());

                                    overalAta2 = o18.getText().toString();
                                    posicaoAta2 = imgJogadoresLinha.get(j-1);

                                }
                            }

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarJogadoresDef() {

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listDefesa.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(Jogador.class);

                    if(p.getIdClube().equals(id)){
                        if( p.getpJogador().equals("ZAG") || p.getpJogador().equals("LD") || p.getpJogador().equals("LE") ){
                            listDefesa.add(p);

                        }

                    }

                    if(listDefesa.size() > 0) {

                        qJdef = listDefesa.size();
                    }


                }

                Collections.sort(listDefesa);

                NumberFormat nMc = NumberFormat.getInstance();
                nMc.setMaximumFractionDigits(2);
                float totalD = Integer.parseInt( listGoleiros.get(0).getoJogador()) + Integer.parseInt( listDefesa.get(0).getoJogador()) + Integer.parseInt( listDefesa.get(1).getoJogador()) + Integer.parseInt( listDefesa.get(2).getoJogador()) + Integer.parseInt( listDefesa.get(3).getoJogador());
                mediaDef.setText(nMc.format(totalD / 5) );

                arrayAdapterDefesa = new AdapterGerClubes (getApplicationContext(), listDefesa);
                listDef.setAdapter(arrayAdapterDefesa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarJogadoresMc() {

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listMeio.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    m = objSnapshot.getValue(Jogador.class);

                    if(m.getIdClube().equals(id)){
                        if(m.getpJogador().equals("VOL") || m.getpJogador().equals("MC") || m.getpJogador().equals("MD") || m.getpJogador().equals("ME") || m.getpJogador().equals("MEI") ){
                            listMeio.add(m);

                        }

                    }

                    if(listMeio.size() > 0)

                        qJmeio = listMeio.size();


                }



                Collections.sort(listMeio);

                NumberFormat nMc = NumberFormat.getInstance();
                nMc.setMaximumFractionDigits(2);
                float totalM = Integer.parseInt( listMeio.get(0).getoJogador()) + Integer.parseInt( listMeio.get(1).getoJogador()) + Integer.parseInt( listMeio.get(2).getoJogador()) + Integer.parseInt( listMeio.get(3).getoJogador());
                mediaMeio.setText(nMc.format(totalM / 4) );

                arrayAdapterMeio = new AdapterGerClubes (getApplicationContext(), listMeio);
                listMc.setAdapter(arrayAdapterMeio);

               // databaseReference.child("Competidores").child(id).child("oMEI").setValue(String.valueOf(nMc.format(totalM / 4)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarJogadoresAta() {

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listAtaque.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    a = objSnapshot.getValue(Jogador.class);

                    if(a.getIdClube().equals(id)){
                        if(a.getpJogador().equals("MAD") || a.getpJogador().equals("MAE") || a.getpJogador().equals("PD") || a.getpJogador().equals("PE") || a.getpJogador().equals("SA") || a.getpJogador().equals("ATA") ){
                            listAtaque.add(a);

                        }

                    }

                    if(listMeio.size() > 0)

                        qJata= listAtaque.size();

                        int a = qJGol + qJdef + qJmeio + qJata;
                        qJogadores.setText(String.valueOf(a));

                }

                Collections.sort(listAtaque);

                NumberFormat nMc = NumberFormat.getInstance();
                nMc.setMaximumFractionDigits(2);
                float totalA = Integer.parseInt( listAtaque.get(0).getoJogador()) + Integer.parseInt( listAtaque.get(1).getoJogador()) + Integer.parseInt( listAtaque.get(2).getoJogador());
                mediaAta.setText(nMc.format(totalA / 3) );

                arrayAdapterAtaque = new AdapterGerClubes (getApplicationContext(), listAtaque);
                listAta.setAdapter(arrayAdapterAtaque);

               // databaseReference.child("Competidores").child(id).child("oATA").setValue(String.valueOf(nMc.format(totalA / 3)));

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertaGoleiroRepetido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( sGol.getSelectedItem().toString() + " JÁ É O GOLEIRO TITULAR." );
        builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {


            }
        });
        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });
        builder.show();
    }

    private void alertaJogadorRepetido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( "Jogador selecionado já se encontra escalado" );
        builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {


            }
        });
        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });
        builder.show();
    }

    private void imagensEscudos(){
        images[0] = R.drawable.arsenal;
        images[1] = R.drawable.atalanta;
        images[2] = R.drawable.athletic_bilbao;
        images[3] = R.drawable.atletico_madrid;
        images[4] = R.drawable.barcelona;
        images[5] = R.drawable.bayern;
        images[6] = R.drawable.benfica;
        images[7] = R.drawable.borussia_dortmund;
        images[8] = R.drawable.borussia_monchengladbach;
        images[9] = R.drawable.chelsea;
        images[10] = R.drawable.eintracht_frankfurt;
        images[11] = R.drawable.hoffenheim;
        images[12] = R.drawable.internazionale;
        images[13] = R.drawable.juventus;
        images[14] = R.drawable.lazio;
        images[15] = R.drawable.leicester_city;
        images[16] = R.drawable.liverpool;
        images[17] = R.drawable.manchester_city;
        images[18] = R.drawable.manchester_united;
        images[19] = R.drawable.milan;
        images[20] = R.drawable.monaco;
        images[21] = R.drawable.napoli;
        images[22] = R.drawable.porto;
        images[23] = R.drawable.psg;
        images[24] = R.drawable.real_madrid;
        images[25] = R.drawable.real_sociedad;
        images[26] = R.drawable.roma;
        images[27] = R.drawable.schalke_04;
        images[28] = R.drawable.sevilla;
        images[29] = R.drawable.sporting;
        images[30] = R.drawable.tottenham;
        images[31] = R.drawable.valencia;
        images[32] = R.drawable.villarreal;

    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();

        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

}