package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class VisitanteTabela extends Activity {

    ArrayList<TabelaClassificacaoFg> listClassifGrupoA = new ArrayList<>();
    AdapterTabelaFg arrayAdapterClassifGrupoA;

    ArrayList<TabelaClassificacaoFg> listClassifGrupoB = new ArrayList<>();
    AdapterTabelaFg arrayAdapterClassifGrupoB;

    ArrayList<JogosFg> listFinaisJ = new ArrayList<>();
    AdapterJogosF arrayAdapterFinaisJ;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView logo;
    TextView tituloTabela, mtmt, textoFinalizado;
    ListView classifGrupoA, classifGrupoB, jogosFinais;
    ImageButton jogosId, historicoId;

    Config config;
    String formatoTorneio, statusTorneio, dataProxT;

    ProgressDialog progressDialog;

    TabelaClassificacaoFg a[] = new TabelaClassificacaoFg[8];
    TabelaClassificacaoFg b[] = new TabelaClassificacaoFg[4];
    JogosFg jF[] = new JogosFg[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitante_tabela);

        progressDialog = new ProgressDialog(VisitanteTabela.this, R.style.MyAlertDialogStyle );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        logo = findViewById(R.id.logoId);
        textoFinalizado = findViewById(R.id.textoFinalizado);
        tituloTabela = findViewById(R.id.titulo01);

        classifGrupoA = findViewById(R.id.classifGrupoA);
        classifGrupoB = findViewById(R.id.classifGrupoB);
        jogosFinais = findViewById(R.id.jogosFinais);

        jogosId = findViewById(R.id.jogosId);
        historicoId = findViewById(R.id.historicoId);


        jogosId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteTabela.this, VisitanteJogos.class);
                startActivity(intent);
            }
        });

        historicoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteTabela.this, VisitanteHistorico.class);
                startActivity(intent);
            }
        });



        inicializarFirebase();
        getConfigServidor();
    }

    private void listarDadosGrupoA() {

        databaseReference.child("ParticipantesGrupoA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listClassifGrupoA.clear();

                TabelaClassificacaoFg cabecalho = new TabelaClassificacaoFg();

                cabecalho.setTime("GRUPO A");
                //cabecalho.setPlayer("PLAYER");
                cabecalho.setPontos("PTS");
                cabecalho.setVit("VIT");
                cabecalho.setEmp("EMP");
                cabecalho.setDer("DER");
                cabecalho.setGp("GP");
                cabecalho.setGc("GC");
                cabecalho.setSg("SG");
                listClassifGrupoA.add(cabecalho);

                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    a[i] = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    if(a[i].getStatus().equals("INATIVO")){
                        //não fazer nada
                    } else{
                        listClassifGrupoA.add(a[i]);
                    }

                    if(a[i].getPosicaoTabela().equals("1")){
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("timeCasa").setValue(a[i].getPlayer());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemTimeCasa").setValue(a[i].getImagem());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemPlayerCasa").setValue(a[i].getImgPerfil());
                    }

                    if(a[i].getPosicaoTabela().equals("2")){
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("timeFora").setValue(a[i].getPlayer());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemTimeFora").setValue(a[i].getImagem());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemPlayerFora").setValue(a[i].getImgPerfil());
                    }

                    i++;
                }


                Collections.sort(listClassifGrupoA);
                arrayAdapterClassifGrupoA = new AdapterTabelaFg (getApplicationContext(), listClassifGrupoA);
                classifGrupoA.setAdapter(arrayAdapterClassifGrupoA);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarDadosGrupoB() {
        databaseReference.child("ParticipantesGrupoB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listClassifGrupoB.clear();

                TabelaClassificacaoFg cabecalho = new TabelaClassificacaoFg();

                cabecalho.setTime("GRUPO B");
                //cabecalho.setPlayer("PLAYER");
                cabecalho.setPontos("PTS");
                cabecalho.setVit("VIT");
                cabecalho.setEmp("EMP");
                cabecalho.setDer("DER");
                cabecalho.setGp("GP");
                cabecalho.setGc("GC");
                cabecalho.setSg("SG");
                listClassifGrupoB.add(cabecalho);

                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    b[i] = objSnapshot.getValue(TabelaClassificacaoFg.class);


                    if(b[i].getStatus().equals("INATIVO")){
                        //não fazer nada
                    } else{
                        listClassifGrupoB.add(b[i]);
                    }

                    if(b[i].getPosicaoTabela().equals("1")){
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("timeCasa").setValue(b[i].getPlayer());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemTimeCasa").setValue(b[i].getImagem());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemPlayerCasa").setValue(b[i].getImgPerfil());
                    }

                    if(b[i].getPosicaoTabela().equals("2")){
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("timeFora").setValue(b[i].getPlayer());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemTimeFora").setValue(b[i].getImagem());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemPlayerFora").setValue(b[i].getImgPerfil());
                    }

                    i++;

                }


                Collections.sort(listClassifGrupoB);
                arrayAdapterClassifGrupoB = new AdapterTabelaFg (getApplicationContext(), listClassifGrupoB);
                classifGrupoB.setAdapter(arrayAdapterClassifGrupoB);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarDadosPontosC() {
        databaseReference.child("ParticipantesPontosCorridos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listClassifGrupoA.clear();

                TabelaClassificacaoFg cabecalho = new TabelaClassificacaoFg();

                cabecalho.setTime("");
                // cabecalho.setPlayer("PLAYER");
                cabecalho.setPontos("PTS");
                cabecalho.setVit("VIT");
                cabecalho.setEmp("EMP");
                cabecalho.setDer("DER");
                cabecalho.setGp("GP");
                cabecalho.setGc("GC");
                cabecalho.setSg("SG");
                listClassifGrupoA.add(cabecalho);

                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    a[i] = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    if(a[i].getStatus().equals("INATIVO")){
                        //não fazer nada
                    } else{
                        listClassifGrupoA.add(a[i]);
                    }


                    if(a[i].getPosicaoTabela().equals("1")){
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("timeCasa").setValue(a[i].getPlayer());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemTimeCasa").setValue(a[i].getImagem());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemPlayerCasa").setValue(a[i].getImgPerfil());
                    }

                    if(a[i].getPosicaoTabela().equals("2")){
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("timeCasa").setValue(a[i].getPlayer());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemTimeCasa").setValue(a[i].getImagem());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemPlayerCasa").setValue(a[i].getImgPerfil());
                    }

                    if(a[i].getPosicaoTabela().equals("3")){
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("timeFora").setValue(a[i].getPlayer());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemTimeFora").setValue(a[i].getImagem());
                        databaseReference.child("Finais").child("bbbSemiFinal_02").child("imagemPlayerFora").setValue(a[i].getImgPerfil());
                    }

                    if(a[i].getPosicaoTabela().equals("4")){
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("timeFora").setValue(a[i].getPlayer());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemTimeFora").setValue(a[i].getImagem());
                        databaseReference.child("Finais").child("aaaSemiFinal_01").child("imagemPlayerFora").setValue(a[i].getImgPerfil());
                    }

                    i++;
                }


                Collections.sort(listClassifGrupoA);
                arrayAdapterClassifGrupoA = new AdapterTabelaFg (getApplicationContext(), listClassifGrupoA);
                classifGrupoA.setAdapter(arrayAdapterClassifGrupoA);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getConfigServidor(){
        databaseReference.child("Configuracoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    config = objSnapshot.getValue(Config.class);

                    formatoTorneio = config.getFormatoTorneio();
                    statusTorneio = config.getStatusTorneio();
                    dataProxT = config.getDataProxT();
                }

                if(formatoTorneio.equals("grupos")){

                    if(statusTorneio.equals("andamento")){

                        listarDadosGrupoA();
                        listarDadosGrupoB();
                        listarJogosF();

                        logo.setVisibility(View.INVISIBLE);
                        textoFinalizado.setText("");
                    } else {

                        logo.setVisibility(View.VISIBLE);
                        textoFinalizado.setText("PRÓXIMO TORNEIO PREVISTO PARA:" + "\n" + dataProxT);

                        tituloTabela.setText("");
                        tituloTabela.setBackgroundColor(0x00FFFFFF);
                        mtmt.setText("");
                        mtmt.setBackgroundColor(0x00FFFFFF);

                        progressDialog.dismiss();
                    }

                } else if(formatoTorneio.equals("pontosCorridos")){

                    if(statusTorneio.equals("andamento")){

                        listarDadosPontosC();
                        listarJogosF();

                        logo.setVisibility(View.INVISIBLE);
                        textoFinalizado.setText("");
                    } else {

                        logo.setVisibility(View.VISIBLE);
                        textoFinalizado.setText("DATA PRÓXIMO TORNEIO" + "\n" + dataProxT);

                        tituloTabela.setText("");
                        tituloTabela.setBackgroundColor(0x00FFFFFF);
                        mtmt.setText("");
                        mtmt.setBackgroundColor(0x00FFFFFF);

                        progressDialog.dismiss();
                    }


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarJogosF(){
        databaseReference.child("Finais").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listFinaisJ.clear();
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    jF[i] = objSnapshot.getValue(JogosFg.class);
                    i++;
                }

                arrayAdapterFinaisJ = new AdapterJogosF(getApplicationContext(), listFinaisJ);
                jogosFinais.setAdapter(arrayAdapterFinaisJ);


                listFinaisJ.add(jF[0]);
                listFinaisJ.add(jF[1]);


                if( jF[0].getGolsC().equals("") && jF[0].getGolsF().equals("") && jF[1].getGolsC().equals("") && jF[1].getGolsF().equals("")){
                    //Não listar
                } else{
                    listFinaisJ.add(jF[2]);
                    listFinaisJ.add(jF[3]);
                }

                // definir quem passa semifinal 01
                if( String.valueOf(jF[0].getGolsC()).equals("") && String.valueOf(jF[0].getGolsF()).equals("") ) {

                    databaseReference.child("Finais").child(jF[2].getId()).child("timeCasa").setValue("");
                    databaseReference.child("Finais").child(jF[3].getId()).child("timeCasa").setValue("");

                    databaseReference.child("Finais").child(jF[2].getId()).child("imagemTimeCasa").setValue("32");
                    databaseReference.child("Finais").child(jF[3].getId()).child("imagemTimeCasa").setValue("32");

                    databaseReference.child("Finais").child(jF[2].getId()).child("imagemPlayerCasa").setValue("8");
                    databaseReference.child("Finais").child(jF[3].getId()).child("imagemPlayerCasa").setValue("8");

                } else {

                    if (Integer.parseInt(jF[0].getGolsC()) + Integer.parseInt(jF[0].getGolsPenaltC()) > Integer.parseInt(jF[0].getGolsF()) + Integer.parseInt(jF[0].getGolsPenaltF())) {
                        databaseReference.child("Finais").child(jF[2].getId()).child("timeCasa").setValue(jF[0].getTimeFora());
                        databaseReference.child("Finais").child(jF[3].getId()).child("timeCasa").setValue(jF[0].getTimeCasa());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemTimeCasa").setValue(jF[0].getImagemTimeFora());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemTimeCasa").setValue(jF[0].getImagemTimeCasa());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemPlayerCasa").setValue(jF[0].getImagemPlayerFora());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemPlayerCasa").setValue(jF[0].getImagemPlayerCasa());

                    } else {
                        databaseReference.child("Finais").child(jF[2].getId()).child("timeCasa").setValue(jF[0].getTimeCasa());
                        databaseReference.child("Finais").child(jF[3].getId()).child("timeCasa").setValue(jF[0].getTimeFora());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemTimeCasa").setValue(jF[0].getImagemTimeCasa());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemTimeCasa").setValue(jF[0].getImagemTimeFora());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemPlayerCasa").setValue(jF[0].getImagemPlayerCasa());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemPlayerCasa").setValue(jF[0].getImagemPlayerFora());
                    }
                }

                // definir quem passa semifinal 02
                if( String.valueOf(jF[1].getGolsC()).equals("") && String.valueOf(jF[1].getGolsF()).equals("") ) {

                    databaseReference.child("Finais").child(jF[2].getId()).child("timeFora").setValue("");
                    databaseReference.child("Finais").child(jF[3].getId()).child("timeFora").setValue("");

                    databaseReference.child("Finais").child(jF[2].getId()).child("imagemTimeFora").setValue("32");
                    databaseReference.child("Finais").child(jF[3].getId()).child("imagemTimeFora").setValue("32");

                    databaseReference.child("Finais").child(jF[2].getId()).child("imagemPlayerFora").setValue("8");
                    databaseReference.child("Finais").child(jF[3].getId()).child("imagemPlayerFora").setValue("8");


                } else {

                    if (Integer.parseInt(jF[1].getGolsC()) + Integer.parseInt(jF[1].getGolsPenaltC()) > Integer.parseInt(jF[1].getGolsF()) + Integer.parseInt(jF[1].getGolsPenaltF())) {
                        databaseReference.child("Finais").child(jF[2].getId()).child("timeFora").setValue(jF[1].getTimeFora());
                        databaseReference.child("Finais").child(jF[3].getId()).child("timeFora").setValue(jF[1].getTimeCasa());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemTimeFora").setValue(jF[1].getImagemTimeFora());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemTimeFora").setValue(jF[1].getImagemTimeCasa());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemPlayerFora").setValue(jF[1].getImagemPlayerFora());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemPlayerFora").setValue(jF[1].getImagemPlayerCasa());

                    } else {
                        databaseReference.child("Finais").child(jF[2].getId()).child("timeFora").setValue(jF[1].getTimeCasa());
                        databaseReference.child("Finais").child(jF[3].getId()).child("timeFora").setValue(jF[1].getTimeFora());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemTimeFora").setValue(jF[1].getImagemTimeCasa());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemTimeFora").setValue(jF[1].getImagemTimeFora());

                        databaseReference.child("Finais").child(jF[2].getId()).child("imagemPlayerFora").setValue(jF[1].getImagemPlayerCasa());
                        databaseReference.child("Finais").child(jF[3].getId()).child("imagemPlayerFora").setValue(jF[1].getImagemPlayerFora());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();

        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}