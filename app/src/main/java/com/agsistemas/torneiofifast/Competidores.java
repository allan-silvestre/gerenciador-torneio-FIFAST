package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Competidores extends Activity {

    private List<Competidor> listCompetidor = new ArrayList<Competidor>();
    ArrayAdapter<Competidor> arrayAdapterCompetidor;

    ListView listaCompetidores;
    Competidor selecionarCompetidor;

    String uLogadoId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competidores);

        inicializarFirebase();
        listarCompetidores();

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        uLogadoId = b.getString("idLogado");



        listaCompetidores = findViewById(R.id.listaCompetidores);

        listaCompetidores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selecionarCompetidor = (Competidor) adapterView.getItemAtPosition(i);

                Intent intent=new Intent(Competidores.this, EditarCompetidorActivity.class);
                intent.putExtra("id", selecionarCompetidor.getId());
                intent.putExtra("nome", selecionarCompetidor.getNome());
                intent.putExtra("sobrenome", selecionarCompetidor.getSobrenome());
                intent.putExtra("senha", selecionarCompetidor.getSenha());
                intent.putExtra("time", selecionarCompetidor.getTime());
                intent.putExtra("imagem", selecionarCompetidor.getImagem());
                intent.putExtra("imgPerfil", selecionarCompetidor.getImgPerfil());
                intent.putExtra("senha", selecionarCompetidor.getSenha());
                intent.putExtra("ordem", selecionarCompetidor.getOrdem());
                intent.putExtra("TempAtualOuro", selecionarCompetidor.getTempAtualOuro());
                intent.putExtra("TempAtualPrata", selecionarCompetidor.getTempAtualPrata());
                intent.putExtra("TempAtualBronze", selecionarCompetidor.getTempAtualBronze());
                intent.putExtra("TempAtualPts", selecionarCompetidor.getTempAtualPts());
                intent.putExtra("statusP", selecionarCompetidor.getStatusP());
                intent.putExtra("nivelAcesso", selecionarCompetidor.getNivelAcesso());
                intent.putExtra("status", selecionarCompetidor.getStatus());

                intent.putExtra("idLogado", uLogadoId );

                startActivity(intent);

            }
        });



    }

    private void listarCompetidores(){

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listCompetidor.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Competidor p = objSnapshot.getValue(Competidor.class);
                    listCompetidor.add(p);

                }

                arrayAdapterCompetidor = new ArrayAdapter<Competidor>(Competidores.this, android.R.layout.simple_list_item_1, listCompetidor);
                listaCompetidores.setAdapter(arrayAdapterCompetidor);

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