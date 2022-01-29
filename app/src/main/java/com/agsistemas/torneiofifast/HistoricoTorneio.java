package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HistoricoTorneio extends Activity {

    ArrayList<Competidor> listTemp = new ArrayList<>();
    AdapterTemAtual arrayTemp;

    ArrayList<Historico> listHvF = new ArrayList<Historico>();
    AdapterHistoricoTorneio arrayAdapterHvF;

    ListView listaHistorico, listaVencedores;

    ImageView escudoOuro, escudoPrata, escudoBronze;

    ImageButton aVf, vVf;

    TextView tituloTabela, playerOuro, playerPrata, playerBronze, vF;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Competidor p;
    Historico h;

    DateFormat ano;
    Date a;
    int numeroVf;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_torneio);

        progressDialog = new ProgressDialog(HistoricoTorneio.this, R.style.MyAlertDialogStyle );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        vF = findViewById(R.id.vFifa);
        aVf = findViewById(R.id.avancarVf);
        vVf = findViewById(R.id.voltarVf);
        tituloTabela = findViewById(R.id.tituloTabela);

        escudoOuro = findViewById(R.id.escudoOuro);
        escudoPrata = findViewById(R.id.escudoPrata);
        escudoBronze = findViewById(R.id.escudoBronze);

        playerOuro = findViewById(R.id.playerOuro);

        playerPrata = findViewById(R.id.playerPrata);

        playerBronze = findViewById(R.id.playerBronze);

        listaHistorico = findViewById(R.id.listaHistoricoTorneio);
        listaVencedores = findViewById(R.id.listahistoricoVencedores);

        ano = new SimpleDateFormat("yy");
        a = new Date();
        numeroVf = Integer.parseInt(String.valueOf(ano.format(a)));
        vF.setText("FIFA" + numeroVf);
        tituloTabela.setText("TABELA DA TEMPORADA 20" + numeroVf);

        aVf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                numeroVf++;
                vF.setText("FIFA" + numeroVf);
                tituloTabela.setText("TABELA DA TEMPORADA 20" + numeroVf);
                listarDadosH();
                listarDadosT();

                progressDialog.dismiss();
            }
        });

        vVf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                numeroVf--;
                vF.setText("FIFA" + numeroVf);
                tituloTabela.setText("TABELA DA TEMPORADA 20" + numeroVf);
                listarDadosH();
                listarDadosT();
                progressDialog.dismiss();
            }
        });

        inicializarFirebase();

        listarDadosH();
        listarDadosT();
    }

    private void listarDadosT() {
        databaseReference.child("historico").child("temporadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listTemp.clear();

                Competidor cabecalho = new Competidor();

                cabecalho.setNome("aasd");
                cabecalho.setTime("aaaa");
                cabecalho.setStatusP("1");
                cabecalho.setImgPerfil("https://firebasestorage.googleapis.com/v0/b/torneiofifadb.appspot.com/o/participante8.png?alt=media&token=a9f7bf79-e160-49b7-9edd-e2785267c98f");
                cabecalho.setImagem("15");
                cabecalho.setTempAtualOuro("0");
                cabecalho.setTempAtualPrata("0");
                cabecalho.setTempAtualBronze("0");
                cabecalho.setTempAtualPts("1000");

                listTemp.add(cabecalho);

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(Competidor.class);

                    if( p.getStatus().equals(vF.getText().toString()) ){

                        listTemp.add(p);

                    }


                }

                Collections.sort(listTemp);
                arrayTemp = new AdapterTemAtual (getApplicationContext(), listTemp);
                listaVencedores.setAdapter(arrayTemp);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarDadosH() {
        databaseReference.child("historico").child("historicoTorneio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listHvF.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    h = objSnapshot.getValue(Historico.class);
                    if(h.getVersaoFifa().equals(vF.getText().toString())){
                        listHvF.add(h);
                    }


                }

                arrayAdapterHvF = new AdapterHistoricoTorneio (getApplicationContext(), listHvF);
                listaHistorico.setAdapter(arrayAdapterHvF);
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

        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }
}