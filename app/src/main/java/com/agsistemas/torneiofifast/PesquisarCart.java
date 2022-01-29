package com.agsistemas.torneiofifast;

import android.app.Activity;
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

public class PesquisarCart extends Activity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Cartoes> listCartoes = new ArrayList<>();
    AdapterCartoes arrayAdapterCartoes;
    Cartoes cart;
    ListView cartoes;
    ImageButton addCartoes;
    Cartoes selecionarCartoes;

    String uLogadoId;

    ImageButton tabelaId, transf, historicoId, configId, sairId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_cart2);

        cartoes = findViewById(R.id.cartoes);
        addCartoes = findViewById(R.id.addCartoes);

        tabelaId = findViewById(R.id.tabelaId);
        transf = findViewById(R.id.transfId);
        historicoId = findViewById(R.id.historicoId);
        configId = findViewById(R.id.configId);

        tabelaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesquisarCart.this, TabelaFg.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
                finish();
            }
        });

        transf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesquisarCart.this, GerenciarClubes.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
                finish();
            }
        });

        historicoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesquisarCart.this, PesquisarCart.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
                finish();
            }
        });

        configId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesquisarCart.this, Configuracoes.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
                finish();
            }
        });

        addCartoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesquisarCart.this, AddCartoes.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        cartoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selecionarCartoes = (Cartoes) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(PesquisarCart.this, EditarCartoes.class);
                intent.putExtra("id", selecionarCartoes.getId());
                intent.putExtra("jogador", selecionarCartoes.getJogador());
                intent.putExtra("time", selecionarCartoes.getTime());
                intent.putExtra("cAmarelo", selecionarCartoes.getcAmarelo());
                intent.putExtra("cVermelho", selecionarCartoes.getcVermelho());
                intent.putExtra("imagemClube", selecionarCartoes.getImagemClube());
                intent.putExtra("imagemJogador", selecionarCartoes.getImagemJogador());

                intent.putExtra("idLogado", uLogadoId );

                startActivity(intent);

            }
        });

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        uLogadoId = b.getString("idLogado");

        inicializarFirebase();
        listarDadosCartoes();

    }

    private void listarDadosCartoes() {

        databaseReference.child("Cartoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listCartoes.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    cart = objSnapshot.getValue(Cartoes.class);
                    listCartoes.add(cart);

                }

                // Collections.sort(listCartoes);
                arrayAdapterCartoes = new AdapterCartoes (getApplicationContext(), listCartoes);
                cartoes.setAdapter(arrayAdapterCartoes);


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