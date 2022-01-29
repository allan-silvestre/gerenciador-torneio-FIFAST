package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class VisitanteHistorico extends Activity {

    ArrayList<Competidor> listTempAtual = new ArrayList<>();
    AdapterTemAtual arrayTempAtual;

    ListView tAtual;

    ImageButton tabelaId, jogosId, transf, historicoId;
    Button historico;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    final int[] images = new int[33];

    Competidor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitante_historico);

        tabelaId = findViewById(R.id.tabelaId);
        jogosId = findViewById(R.id.jogosId);
        transf = findViewById(R.id.transfId);
        historicoId = findViewById(R.id.historicoId);
        historico = findViewById(R.id.historico);

        historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteHistorico.this, HistoricoTorneio.class);
                startActivity(intent);
            }
        });

        tabelaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteHistorico.this, VisitanteTabela.class);
                startActivity(intent);
            }
        });

        jogosId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitanteHistorico.this, VisitanteJogos.class);
                startActivity(intent);
            }
        });

        tAtual = findViewById(R.id.temAtualList);

        inicializarFirebase();
        imagensReduzidasEscudos();
        listarDados();


    }

    private void listarDados(){

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listTempAtual.clear();

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

                listTempAtual.add(cabecalho);

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Competidor p = objSnapshot.getValue(Competidor.class);

                    if(p.getStatus().equals("INATIVO")){

                    } else {
                        listTempAtual.add(p);
                    }




                }

                Collections.sort(listTempAtual);
                arrayTempAtual = new AdapterTemAtual (VisitanteHistorico.this, listTempAtual);
                tAtual.setAdapter(arrayTempAtual);

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

    private void imagensReduzidasEscudos(){
        images[0] = R.drawable.reduzidoarsenal;
        images[1] = R.drawable.reduzidoatalanta;
        images[2] = R.drawable.reduzidoathletic_bilbao;
        images[3] = R.drawable.reduzidoatletico_madrid;
        images[4] = R.drawable.reduzidobarcelona;
        images[5] = R.drawable.reduzidobayern;
        images[6] = R.drawable.reduzidobenfica;
        images[7] = R.drawable.reduzidoborussia_dortmund;
        images[8] = R.drawable.reduzidoborussia_monchengladbach;
        images[9] = R.drawable.reduzidochelsea;
        images[10] = R.drawable.reduzidoeintracht_frankfurt;
        images[11] = R.drawable.reduzidohoffenheim;
        images[12] = R.drawable.reduzidointernazionale;
        images[13] = R.drawable.reduzidojuventus;
        images[14] = R.drawable.reduzidolazio;
        images[15] = R.drawable.reduzidoleicester_city;
        images[16] = R.drawable.reduzidoliverpool;
        images[17] = R.drawable.reduzidomanchester_city;
        images[18] = R.drawable.reduzidomanchester_united;
        images[19] = R.drawable.reduzidomilan;
        images[20] = R.drawable.reduzidomonaco;
        images[21] = R.drawable.reduzidonapoli;
        images[22] = R.drawable.reduzidoporto;
        images[23] = R.drawable.reduzidopsg;
        images[24] = R.drawable.reduzidoreal_madrid;
        images[25] = R.drawable.reduzidoreal_sociedad;
        images[26] = R.drawable.reduzidoroma;
        images[27] = R.drawable.reduzidoschalke_04;
        images[28] = R.drawable.reduzidosevilla;
        images[29] = R.drawable.reduzidosporting;
        images[30] = R.drawable.reduzidotottenham;
        images[31] = R.drawable.reduzidovalencia;
        images[32] = R.drawable.reduzidovillarreall;

    }

}