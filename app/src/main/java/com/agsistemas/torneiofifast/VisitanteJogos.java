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
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisitanteJogos extends Activity {

    List<JogosFg> jogosGrupoA = new ArrayList<JogosFg>();
    AdapterJogosFg adapterJGrupoA;

    List<JogosFg> jogosGrupoB = new ArrayList<JogosFg>();
    AdapterJogosFg adapterJGrupoB;

    int rankingGa[] = new int[4];
    // String time[] = new String[4];
    // String player[] = new String[4];
    int ptsGa[] = new int[4];
    int vitGa[] = new int[4];
    int empGa[] = new int[4];
    int derGa[] = new int[4];
    int gpGa[] = new int[4];
    int gcGa[] = new int[4];
    int sgGa[] = new int[4];

    int rankingGb[] = new int[4];
    // String time[] = new String[4];
    // String player[] = new String[4];
    int ptsGb[] = new int[4];
    int vitGb[] = new int[4];
    int empGb[] = new int[4];
    int derGb[] = new int[4];
    int gpGb[] = new int[4];
    int gcGb[] = new int[4];
    int sgGb[] = new int[4];

    int rankingPc[] = new int[8];
    // String time[] = new String[4];
    // String player[] = new String[4];
    int ptsPc[] = new int[8];
    int vitPc[] = new int[8];
    int empPc[] = new int[8];
    int derPc[] = new int[8];
    int gpPc[] = new int[8];
    int gcPc[] = new int[8];
    int sgPc[] = new int[8];

    ImageButton tabelaId, transf, historicoId, configId, sairId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listaJogosGrupoA, listaJogosGrupoB;
    JogosFg selecionarPartida;

    TextView titulo01, titulo02;

    TabelaClassificacaoFg cA[] = new TabelaClassificacaoFg[4];
    TabelaClassificacaoFg cB[] = new TabelaClassificacaoFg[4];
    TabelaClassificacaoFg pC[] = new TabelaClassificacaoFg[8];

    ImageView logo;
    TextView textoFinalizado;

    Config config;
    String formatoTorneio, dataProxT, statusTorneio, keyGrupoA, keyGrupoAPartida, keyGrupoB, keyGrupoBPartida ;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitante_jogos);

        titulo01 = findViewById(R.id.titulo01);
        titulo02 = findViewById(R.id.titulo02);

        logo = findViewById(R.id.logoId);
        textoFinalizado = findViewById(R.id.textoFinalizado);

        listaJogosGrupoA = findViewById(R.id.listaJogosGrupoA);
        listaJogosGrupoB = findViewById(R.id.listaJogosGrupoB);

        tabelaId = findViewById(R.id.tabelaId);
        transf = findViewById(R.id.transfId);
        historicoId = findViewById(R.id.historicoId);
        configId = findViewById(R.id.configId);

        tabelaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteJogos.this, VisitanteTabela.class);
                startActivity(intent);
            }
        });

        historicoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteJogos.this, VisitanteHistorico.class);
                startActivity(intent);
            }
        });

        inicializarFirebase();
        getConfigServidor();

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

                        logo.setVisibility(View.INVISIBLE);
                        textoFinalizado.setText("");

                        listarJogosGrupoA();
                        listarJogosGrupoB();

                        controleTabelaGrupoA();
                        controleTabelaGrupoB();

                        keyGrupoA = "JogosGrupoA";
                        keyGrupoAPartida = "ParticipantesGrupoA";

                        keyGrupoB = "JogosGrupoB";
                        keyGrupoBPartida = "ParticipantesGrupoB";

                        titulo01.setText("GRUPO A");
                        titulo02.setText("GRUPO B");
                    } else {

                        listarJogosGrupoA();
                        listarJogosGrupoB();

                        controleTabelaGrupoA();
                        controleTabelaGrupoB();

                        logo.setVisibility(View.VISIBLE);
                        listaJogosGrupoA.setVisibility(View.INVISIBLE);
                        listaJogosGrupoB.setVisibility(View.INVISIBLE);
                        textoFinalizado.setText("DATA PRÓXIMO TORNEIO:" + "\n" + dataProxT);

                        titulo01.setText("");
                        titulo01.setBackgroundColor(0x00FFFFFF);
                        titulo02.setText("");
                        titulo02.setBackgroundColor(0x00FFFFFF);

                        keyGrupoA = "JogosGrupoA";
                        keyGrupoAPartida = "ParticipantesGrupoA";

                        keyGrupoB = "JogosGrupoB";
                        keyGrupoBPartida = "ParticipantesGrupoB";

                        progressDialog.dismiss();

                    }


                } else if(formatoTorneio.equals("pontosCorridos")){


                    if(statusTorneio.equals("andamento")){

                        listarJogosPontosCorridosTurno1();
                        listarJogosPontosCorridosTurno2();

                        controleTabelaPontosCorridos();

                        keyGrupoA = "JogosPontosC";
                        keyGrupoAPartida = "ParticipantesPontosCorridos";

                        keyGrupoB = "JogosPontosC";
                        keyGrupoBPartida = "ParticipantesPontosCorridos";

                        titulo01.setText("1ª TURNO");
                        titulo02.setText("2ª TURNO");

                    } else {

                        listarJogosPontosCorridosTurno1();
                        listarJogosPontosCorridosTurno2();

                        controleTabelaPontosCorridos();

                        logo.setVisibility(View.VISIBLE);
                        listaJogosGrupoA.setVisibility(View.INVISIBLE);
                        listaJogosGrupoB.setVisibility(View.INVISIBLE);
                        textoFinalizado.setText("DATA PRÓXIMO TORNEIO:" + "\n" + dataProxT);

                        titulo01.setText("");
                        titulo01.setBackgroundColor(0x00FFFFFF);
                        titulo02.setText("");
                        titulo02.setBackgroundColor(0x00FFFFFF);

                        keyGrupoA = "JogosPontosC";
                        keyGrupoAPartida = "ParticipantesPontosCorridos";

                        keyGrupoB = "JogosPontosC";
                        keyGrupoBPartida = "ParticipantesPontosCorridos";

                        progressDialog.dismiss();

                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarJogosGrupoA(){

        progressDialog = new ProgressDialog(VisitanteJogos.this, R.style.MyAlertDialogStyle  );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child("JogosGrupoA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jogosGrupoA.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    JogosFg p = objSnapshot.getValue(JogosFg.class);

                    if( p.getStatusTimeCasa().equals("INATIVO") || p.getStatusTimeFora().equals("INATIVO") ){
                        //não fazer nada
                    } else{
                        jogosGrupoA.add(p);
                    }

                }

                adapterJGrupoA = new AdapterJogosFg (getApplicationContext(), jogosGrupoA);
                listaJogosGrupoA.setAdapter(adapterJGrupoA);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listarJogosGrupoB(){
        databaseReference.child("JogosGrupoB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jogosGrupoB.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){

                    JogosFg p = objSnapshot.getValue(JogosFg.class);

                    if( p.getStatusTimeCasa().equals("INATIVO") || p.getStatusTimeFora().equals("INATIVO") ){
                        //não fazer nada
                    } else{
                        jogosGrupoB.add(p);
                    }


                }

                adapterJGrupoB = new AdapterJogosFg (getApplicationContext(), jogosGrupoB);
                listaJogosGrupoB.setAdapter(adapterJGrupoB);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listarJogosPontosCorridosTurno1(){

        progressDialog = new ProgressDialog(VisitanteJogos.this, R.style.MyAlertDialogStyle  );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child("JogosPontosC").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jogosGrupoA.clear();
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    JogosFg p = objSnapshot.getValue(JogosFg.class);

                    if( p.getStatusTimeCasa().equals("INATIVO") || p.getStatusTimeFora().equals("INATIVO") ) {
                        //NÃO FAZER NADA
                    } else {
                        if(i < 28){
                            jogosGrupoA.add(p);
                        }
                    }
                    i++;
                    adapterJGrupoA = new AdapterJogosFg (getApplicationContext(), jogosGrupoA);
                    listaJogosGrupoA.setAdapter(adapterJGrupoA);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listarJogosPontosCorridosTurno2(){
        databaseReference.child("JogosPontosC").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jogosGrupoB.clear();
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    JogosFg p = objSnapshot.getValue(JogosFg.class);

                    if( p.getStatusTimeCasa().equals("INATIVO") || p.getStatusTimeFora().equals("INATIVO") ) {
                        //NÃO FAZER NADA
                    } else {
                        if(i > 27){
                            jogosGrupoB.add(p);
                        }
                    }
                    i++;
                    adapterJGrupoB = new AdapterJogosFg (getApplicationContext(), jogosGrupoB);
                    listaJogosGrupoB.setAdapter(adapterJGrupoB);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void controleTabelaGrupoA(){

        databaseReference.child("ParticipantesGrupoA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = 0, j = 8;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    cA[i] = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    if(cA[i].getStatus().equals("INATIVO")){
                        rankingGa[i] = 1000000000;
                        i++;
                        j--;
                    } else{
                        ptsGa[i] = vitGa[i]*3 + empGa[i]*1;
                        vitGa[i] = Integer.parseInt(cA[i].getJ1vit()) + Integer.parseInt(cA[i].getJ2vit()) + Integer.parseInt(cA[i].getJ3vit()) + Integer.parseInt(cA[i].getJ4vit()) + Integer.parseInt(cA[i].getJ5vit()) + Integer.parseInt(cA[i].getJ6vit());
                        empGa[i] = Integer.parseInt(cA[i].getJ1emp()) + Integer.parseInt(cA[i].getJ2emp()) + Integer.parseInt(cA[i].getJ3emp()) + Integer.parseInt(cA[i].getJ4emp()) + Integer.parseInt(cA[i].getJ5emp()) + Integer.parseInt(cA[i].getJ6emp());
                        derGa[i] = Integer.parseInt(cA[i].getJ1der()) + Integer.parseInt(cA[i].getJ2der()) + Integer.parseInt(cA[i].getJ3der()) + Integer.parseInt(cA[i].getJ4der()) + Integer.parseInt(cA[i].getJ5der()) + Integer.parseInt(cA[i].getJ6der());
                        gpGa[i] = Integer.parseInt(cA[i].getJ1gp()) + Integer.parseInt(cA[i].getJ2gp()) + Integer.parseInt(cA[i].getJ3gp()) + Integer.parseInt(cA[i].getJ4gp()) + Integer.parseInt(cA[i].getJ5gp()) + Integer.parseInt(cA[i].getJ6gp());
                        gcGa[i] = Integer.parseInt(cA[i].getJ1gc()) + Integer.parseInt(cA[i].getJ2gc()) + Integer.parseInt(cA[i].getJ3gc()) + Integer.parseInt(cA[i].getJ4gc()) + Integer.parseInt(cA[i].getJ5gc()) + Integer.parseInt(cA[i].getJ6gc());
                        sgGa[i] = gpGa[i] - gcGa[i];

                        rankingGa[i] = 999999 - Integer.parseInt(cA[i].getPontos())*10000 - Integer.parseInt(cA[i].getSg())*100 - Integer.parseInt(cA[i].getGp())*10 - j;

                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("pontos").setValue( String.valueOf(ptsGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("vit").setValue( String.valueOf(vitGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("emp").setValue( String.valueOf(empGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("der").setValue( String.valueOf(derGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("gp").setValue( String.valueOf(gpGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("gc").setValue( String.valueOf(gcGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("sg").setValue( String.valueOf(sgGa[i]) );
                        databaseReference.child("ParticipantesGrupoA").child(cA[i].getId()).child("ranking").setValue( String.valueOf(rankingGa[i]) );

                        i++;
                        j--;
                    }



                }

                Arrays.sort(rankingGa);

                //time 1
                if( cA[0].getRanking().equals(String.valueOf(rankingGa[0])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[0].getId()).child("posicaoTabela").setValue( "1" );

                } else if( cA[0].getRanking().equals(String.valueOf(rankingGa[1])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[0].getId()).child("posicaoTabela").setValue( "2" );

                } else if( cA[0].getRanking().equals(String.valueOf(rankingGa[2])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[0].getId()).child("posicaoTabela").setValue( "3" );

                } else if( cA[0].getRanking().equals(String.valueOf(rankingGa[3])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[0].getId()).child("posicaoTabela").setValue( "4" );
                }

                //time 2
                if( cA[1].getRanking().equals(String.valueOf(rankingGa[0])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[1].getId()).child("posicaoTabela").setValue( "1" );

                } else if( cA[1].getRanking().equals(String.valueOf(rankingGa[1])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[1].getId()).child("posicaoTabela").setValue( "2" );

                } else if( cA[1].getRanking().equals(String.valueOf(rankingGa[2])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[1].getId()).child("posicaoTabela").setValue( "3" );

                } else if( cA[1].getRanking().equals(String.valueOf(rankingGa[3])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[1].getId()).child("posicaoTabela").setValue( "4" );
                }

                //time 3
                if( cA[2].getRanking().equals(String.valueOf(rankingGa[0])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[2].getId()).child("posicaoTabela").setValue( "1" );

                } else if( cA[2].getRanking().equals(String.valueOf(rankingGa[1])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[2].getId()).child("posicaoTabela").setValue( "2" );

                } else if( cA[2].getRanking().equals(String.valueOf(rankingGa[2])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[2].getId()).child("posicaoTabela").setValue( "3" );

                } else if( cA[2].getRanking().equals(String.valueOf(rankingGa[3])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[2].getId()).child("posicaoTabela").setValue( "4" );
                }

                //time 4
                if( cA[3].getRanking().equals(String.valueOf(rankingGa[0])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[3].getId()).child("posicaoTabela").setValue( "1" );

                } else if( cA[3].getRanking().equals(String.valueOf(rankingGa[1])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[3].getId()).child("posicaoTabela").setValue( "2" );

                } else if( cA[3].getRanking().equals(String.valueOf(rankingGa[2])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[3].getId()).child("posicaoTabela").setValue( "3" );

                } else if( cA[3].getRanking().equals(String.valueOf(rankingGa[3])) ){
                    databaseReference.child("ParticipantesGrupoA").child(cA[3].getId()).child("posicaoTabela").setValue( "4" );
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void controleTabelaGrupoB(){

        databaseReference.child("ParticipantesGrupoB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = 0, j = 8;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    cB[i] = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    if (cB[i].getStatus().equals("INATIVO")) {
                        rankingGb[i] = 1000000000;
                        i++;
                        j--;
                    } else {

                        ptsGb[i] = vitGb[i]*3 + empGb[i]*1;
                        vitGb[i] = Integer.parseInt(cB[i].getJ1vit()) + Integer.parseInt(cB[i].getJ2vit()) + Integer.parseInt(cB[i].getJ3vit()) + Integer.parseInt(cB[i].getJ4vit()) + Integer.parseInt(cB[i].getJ5vit()) + Integer.parseInt(cB[i].getJ6vit());
                        empGb[i] = Integer.parseInt(cB[i].getJ1emp()) + Integer.parseInt(cB[i].getJ2emp()) + Integer.parseInt(cB[i].getJ3emp()) + Integer.parseInt(cB[i].getJ4emp()) + Integer.parseInt(cB[i].getJ5emp()) + Integer.parseInt(cB[i].getJ6emp());
                        derGb[i] = Integer.parseInt(cB[i].getJ1der()) + Integer.parseInt(cB[i].getJ2der()) + Integer.parseInt(cB[i].getJ3der()) + Integer.parseInt(cB[i].getJ4der()) + Integer.parseInt(cB[i].getJ5der()) + Integer.parseInt(cB[i].getJ6der());
                        gpGb[i] = Integer.parseInt(cB[i].getJ1gp()) + Integer.parseInt(cB[i].getJ2gp()) + Integer.parseInt(cB[i].getJ3gp()) + Integer.parseInt(cB[i].getJ4gp()) + Integer.parseInt(cB[i].getJ5gp()) + Integer.parseInt(cB[i].getJ6gp());
                        gcGb[i] = Integer.parseInt(cB[i].getJ1gc()) + Integer.parseInt(cB[i].getJ2gc()) + Integer.parseInt(cB[i].getJ3gc()) + Integer.parseInt(cB[i].getJ4gc()) + Integer.parseInt(cB[i].getJ5gc()) + Integer.parseInt(cB[i].getJ6gc());
                        sgGb[i] = gpGb[i] - gcGb[i];

                        rankingGb[i] = 999999 - Integer.parseInt(cB[i].getPontos()) * 10000 - Integer.parseInt(cB[i].getSg()) * 100 - Integer.parseInt(cB[i].getGp()) * 10 - j;

                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("pontos").setValue(String.valueOf(ptsGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("vit").setValue(String.valueOf(vitGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("emp").setValue(String.valueOf(empGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("der").setValue(String.valueOf(derGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("gp").setValue(String.valueOf(gpGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("gc").setValue(String.valueOf(gcGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("sg").setValue(String.valueOf(sgGb[i]));
                        databaseReference.child("ParticipantesGrupoB").child(cB[i].getId()).child("ranking").setValue(String.valueOf(rankingGb[i]));

                        i++;
                        j--;

                    }
                }

                Arrays.sort(rankingGb);

                if(dataSnapshot.getChildrenCount() < 2){
                    finish();
                    Intent intent = new Intent(VisitanteJogos.this, TabelaFg.class);
                    startActivity(intent);
                    Toast.makeText(VisitanteJogos.this, "Erro ao tentar abrir os jogos do torneio, é necessesario no minimo 6 competidores para iniciar o torneio.", Toast.LENGTH_SHORT).show();
                } else if(dataSnapshot.getChildrenCount() == 2){

                    //time 1
                    if( cB[0].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "4" );
                    }

                    //time 2
                    if( cB[1].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "4" );
                    }

                } else if(dataSnapshot.getChildrenCount() == 3){

                    //time 1
                    if( cB[0].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "4" );
                    }

                    //time 2
                    if( cB[1].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "4" );
                    }

                    //time 3
                    if (cB[2].getRanking().equals(String.valueOf(rankingGb[0]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("1");

                    } else if (cB[2].getRanking().equals(String.valueOf(rankingGb[1]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("2");

                    } else if (cB[2].getRanking().equals(String.valueOf(rankingGb[2]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("3");

                    } else if (cB[2].getRanking().equals(String.valueOf(rankingGb[3]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("4");
                    }

                } else if (dataSnapshot.getChildrenCount() == 4){

                    //time 1
                    if( cB[0].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[0].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[0].getId()).child("posicaoTabela").setValue( "4" );
                    }

                    //time 2
                    if( cB[1].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[1].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[1].getId()).child("posicaoTabela").setValue( "4" );
                    }

                    //time 3
                    if (cB[2].getRanking().equals(String.valueOf(rankingGb[0]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("1");

                    } else if (cB[2].getRanking().equals(String.valueOf(rankingGb[1]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("2");

                    } else if (cB[2].getRanking().equals(String.valueOf(rankingGb[2]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("3");

                    } else if (cB[2].getRanking().equals(String.valueOf(rankingGb[3]))) {
                        databaseReference.child("ParticipantesGrupoB").child(cB[2].getId()).child("posicaoTabela").setValue("4");
                    }


                    //time 4
                    if( cB[3].getRanking().equals(String.valueOf(rankingGb[0])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[3].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( cB[3].getRanking().equals(String.valueOf(rankingGb[1])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[3].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( cB[3].getRanking().equals(String.valueOf(rankingGb[2])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[3].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( cB[3].getRanking().equals(String.valueOf(rankingGb[3])) ){
                        databaseReference.child("ParticipantesGrupoB").child(cB[3].getId()).child("posicaoTabela").setValue( "4" );
                    }


                }


                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void controleTabelaPontosCorridos(){

        databaseReference.child("ParticipantesPontosCorridos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = 0, j = 8;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    pC[i] = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    if(pC[i].getStatus().equals("INATIVO")){
                        rankingPc[i] = 1000000000;
                        i++;
                        j--;
                    } else {


                        vitPc[i] = Integer.parseInt(pC[i].getJ1vit()) + Integer.parseInt(pC[i].getJ2vit()) + Integer.parseInt(pC[i].getJ3vit()) + Integer.parseInt(pC[i].getJ4vit()) +
                                Integer.parseInt(pC[i].getJ5vit()) + Integer.parseInt(pC[i].getJ6vit()) + Integer.parseInt(pC[i].getJ7vit()) + Integer.parseInt(pC[i].getJ8vit()) +
                                Integer.parseInt(pC[i].getJ9vit()) + Integer.parseInt(pC[i].getJ10vit()) + Integer.parseInt(pC[i].getJ11vit()) + Integer.parseInt(pC[i].getJ12vit()) +
                                Integer.parseInt(pC[i].getJ13vit()) + Integer.parseInt(pC[i].getJ14vit());

                        empPc[i] = Integer.parseInt(pC[i].getJ1emp()) + Integer.parseInt(pC[i].getJ2emp()) + Integer.parseInt(pC[i].getJ3emp()) + Integer.parseInt(pC[i].getJ4emp()) +
                                Integer.parseInt(pC[i].getJ5emp()) + Integer.parseInt(pC[i].getJ6emp()) + Integer.parseInt(pC[i].getJ7emp()) + Integer.parseInt(pC[i].getJ8emp())
                                + Integer.parseInt(pC[i].getJ9emp()) + Integer.parseInt(pC[i].getJ10emp()) + Integer.parseInt(pC[i].getJ11emp()) + Integer.parseInt(pC[i].getJ12emp())
                                + Integer.parseInt(pC[i].getJ13emp()) + Integer.parseInt(pC[i].getJ14emp());

                        derPc[i] = Integer.parseInt(pC[i].getJ1der()) + Integer.parseInt(pC[i].getJ2der()) + Integer.parseInt(pC[i].getJ3der()) + Integer.parseInt(pC[i].getJ4der()) +
                                Integer.parseInt(pC[i].getJ5der()) + Integer.parseInt(pC[i].getJ6der()) + Integer.parseInt(pC[i].getJ7der()) + Integer.parseInt(pC[i].getJ8der())
                                + Integer.parseInt(pC[i].getJ9der()) + Integer.parseInt(pC[i].getJ10der()) + Integer.parseInt(pC[i].getJ11der()) + Integer.parseInt(pC[i].getJ12der())
                                + Integer.parseInt(pC[i].getJ13der()) + Integer.parseInt(pC[i].getJ14der());

                        gpPc[i] = Integer.parseInt(pC[i].getJ1gp()) + Integer.parseInt(pC[i].getJ2gp()) + Integer.parseInt(pC[i].getJ3gp()) + Integer.parseInt(pC[i].getJ4gp()) +
                                Integer.parseInt(pC[i].getJ5gp()) + Integer.parseInt(pC[i].getJ6gp()) + Integer.parseInt(pC[i].getJ7gp()) + Integer.parseInt(pC[i].getJ8gp())
                                + Integer.parseInt(pC[i].getJ9gp()) + Integer.parseInt(pC[i].getJ10gp()) + Integer.parseInt(pC[i].getJ11gp()) + Integer.parseInt(pC[i].getJ12gp())
                                + Integer.parseInt(pC[i].getJ13gp()) + Integer.parseInt(pC[i].getJ14gp());

                        gcPc[i] = Integer.parseInt(pC[i].getJ1gc()) + Integer.parseInt(pC[i].getJ2gc()) + Integer.parseInt(pC[i].getJ3gc()) + Integer.parseInt(pC[i].getJ4gc()) +
                                Integer.parseInt(pC[i].getJ5gc()) + Integer.parseInt(pC[i].getJ6gc()) + Integer.parseInt(pC[i].getJ7gc()) + Integer.parseInt(pC[i].getJ8gc())
                                + Integer.parseInt(pC[i].getJ9gc()) + Integer.parseInt(pC[i].getJ10gc()) + Integer.parseInt(pC[i].getJ11gc()) + Integer.parseInt(pC[i].getJ12gc())
                                + Integer.parseInt(pC[i].getJ13gc()) + Integer.parseInt(pC[i].getJ14gc());

                        sgPc[i] = gpPc[i] - gcPc[i];

                        ptsPc[i] = vitPc[i]*3 + empPc[i]*1;


                        rankingPc[i] = 999999 - Integer.parseInt(pC[i].getPontos()) * 10000 - Integer.parseInt(pC[i].getSg()) * 100 - Integer.parseInt(pC[i].getGp()) * 10 - j;

                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("pontos").setValue(String.valueOf(ptsPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("vit").setValue(String.valueOf(vitPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("emp").setValue(String.valueOf(empPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("der").setValue(String.valueOf(derPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("gp").setValue(String.valueOf(gpPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("gc").setValue(String.valueOf(gcPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("sg").setValue(String.valueOf(sgPc[i]));
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[i].getId()).child("ranking").setValue(String.valueOf(rankingPc[i]));

                        i++;
                        j--;

                    }

                }

                Arrays.sort(rankingPc);

                if(dataSnapshot.getChildrenCount() < 5){
                    finish();
                    Intent intent = new Intent(VisitanteJogos.this, TabelaFg.class);
                    startActivity(intent);
                    Toast.makeText(VisitanteJogos.this, "Erro ao tentar abrir os jogos do torneio, é necessesario no minimo 6 competidores para iniciar o torneio.", Toast.LENGTH_SHORT).show();

                } else if(dataSnapshot.getChildrenCount() == 5){

                    //time 1
                    if( pC[0].getRanking().equals(String.valueOf(rankingPc[0])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[1])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[2])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[3])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "4" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[4])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "5" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[5])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "6" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[6])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "7" );

                    } else if( pC[0].getRanking().equals(String.valueOf(rankingPc[7])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue( "8" );

                    }

                    //time 2
                    if( pC[1].getRanking().equals(String.valueOf(rankingPc[0])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[1])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[2])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[3])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "4" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[4])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "5" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[5])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "6" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[6])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "7" );

                    } else if( pC[1].getRanking().equals(String.valueOf(rankingPc[7])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue( "8" );

                    }

                    //time 3
                    if( pC[2].getRanking().equals(String.valueOf(rankingPc[0])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[1])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[2])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[3])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "4" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[4])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "5" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[5])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "6" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[6])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "7" );

                    } else if( pC[2].getRanking().equals(String.valueOf(rankingPc[7])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue( "8" );

                    }

                    //time 4
                    if( pC[3].getRanking().equals(String.valueOf(rankingPc[0])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[1])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[2])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[3])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "4" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[4])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "5" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[5])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "6" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[6])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "7" );

                    } else if( pC[3].getRanking().equals(String.valueOf(rankingPc[7])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue( "8" );

                    }

                    //time 5
                    if( pC[4].getRanking().equals(String.valueOf(rankingPc[0])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "1" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[1])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "2" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[2])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "3" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[3])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "4" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[4])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "5" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[5])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "6" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[6])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "7" );

                    } else if( pC[4].getRanking().equals(String.valueOf(rankingPc[7])) ){
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue( "8" );

                    }

                } else if(dataSnapshot.getChildrenCount() == 6) {

                    //time 1
                    if (pC[0].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 2
                    if (pC[1].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 3
                    if (pC[2].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 4
                    if (pC[3].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 5
                    if (pC[4].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 6
                    if (pC[5].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("8");

                    }



                } else if(dataSnapshot.getChildrenCount() == 7) {

                    //time 1
                    if (pC[0].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 2
                    if (pC[1].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 3
                    if (pC[2].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 4
                    if (pC[3].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 5
                    if (pC[4].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 6
                    if (pC[5].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("8");

                    }


                    //time 7
                    if (pC[6].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("8");

                    }


                } else if(dataSnapshot.getChildrenCount() == 8) {

                    //time 1
                    if (pC[0].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[0].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[0].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 2
                    if (pC[1].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[1].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[1].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 3
                    if (pC[2].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[2].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[2].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 4
                    if (pC[3].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[3].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[3].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 5
                    if (pC[4].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[4].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[4].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 6
                    if (pC[5].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[5].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[5].getId()).child("posicaoTabela").setValue("8");

                    }


                    //time 7
                    if (pC[6].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[6].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[6].getId()).child("posicaoTabela").setValue("8");

                    }

                    //time 8
                    if (pC[7].getRanking().equals(String.valueOf(rankingPc[0]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("1");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[1]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("2");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[2]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("3");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[3]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("4");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[4]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("5");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[5]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("6");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[6]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("7");

                    } else if (pC[7].getRanking().equals(String.valueOf(rankingPc[7]))) {
                        databaseReference.child("ParticipantesPontosCorridos").child(pC[7].getId()).child("posicaoTabela").setValue("8");

                    }


                }



                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}