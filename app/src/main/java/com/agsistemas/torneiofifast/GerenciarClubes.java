package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class GerenciarClubes extends Activity {

    ArrayList<UltimasTransf> listT = new ArrayList<>();
    AdapterUtmTransf arrayAdapterListT;

    ArrayList<Competidor> listP = new ArrayList<>();
    AdapterListPlayers arrayAdapterListP;
    Competidor p;

    ImageButton tabelaId, historicoId, configId;

    ListView utmTransf, listPlayers;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String uLogadoId;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_clubes);

        //sendBroadcast(new Intent(getBaseContext(), BootCompletedIntentReceiver.class));

        progressDialog = new ProgressDialog(GerenciarClubes.this, R.style.MyAlertDialogStyle );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        inicializarFirebase();
        listarClubes();
        listarUtmTransf();

        utmTransf = findViewById(R.id.listUltimasT);
        listPlayers = findViewById(R.id.listPlayers);

        tabelaId = findViewById(R.id.tabelaId);
        historicoId = findViewById(R.id.historicoId);
        configId = findViewById(R.id.configId);

        tabelaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GerenciarClubes.this, TabelaFg.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        historicoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GerenciarClubes.this, TemporadaAtual.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        configId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GerenciarClubes.this, Configuracoes.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        listPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Competidor jogador = (Competidor) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(GerenciarClubes.this, EditarGerenciarClubes.class);
                intent.putExtra("id", jogador.getId());
                intent.putExtra("imagem", jogador.getImagem());
                intent.putExtra("time", jogador.getTime());
                intent.putExtra("nome", jogador.getNome());
                intent.putExtra("imgPerfil", jogador.getImgPerfil());

                intent.putExtra("idLogado", uLogadoId );

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        uLogadoId = b.getString("idLogado");


    }

    private void listarUtmTransf(){

        databaseReference.child("UltimasTransf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listT.clear();
                int a = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    UltimasTransf p = objSnapshot.getValue(UltimasTransf.class);

                    listT.add(p);

                }

                Collections.reverse(listT);

                for(int i = 0; i < dataSnapshot.getChildrenCount(); i++){

                    if(i > 14){
                        databaseReference.child("UltimasTransf").child(listT.get(i).getId()).removeValue();
                    }

                }

                arrayAdapterListT = new AdapterUtmTransf(getApplicationContext(), listT);
                utmTransf.setAdapter(arrayAdapterListT);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listarClubes(){

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listP.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(Competidor.class);

                    if(p.getNome().equals("INATIVO")){

                    } else {
                        listP.add(p);
                    }

                }

                Collections.sort(listP);
                arrayAdapterListP = new AdapterListPlayers (getApplicationContext(), listP);
                listPlayers.setAdapter(arrayAdapterListP);


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