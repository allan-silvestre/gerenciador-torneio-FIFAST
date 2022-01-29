package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.List;
import java.util.UUID;

public class AddCartoes extends Activity {

    TextView aId, vId, nJogador, tJogador;
    Button salvar, cancelar, maisA, menosA, maisV, menosV ;
    Spinner jogadores;

    Jogador jogador;

    ImageView imgJogador, escudoTime;

    String posicaoEscudo, posicaoimgJ;

    List<String> idCj = new ArrayList<String>();

    List<String> j = new ArrayList<String>();
    List<String> c = new ArrayList<String>();
    List<String> pImagemC = new ArrayList<String>();
    List<String> pImagemJ = new ArrayList<String>();
    List<String> orientacao = new ArrayList<String>();

    List<String> jCadastrados = new ArrayList<String>();

    final int[] images = new int[33];
    List<Integer> imagemJogadores = new ArrayList<>();

    int ama = 0;
    int ver = 0;

    String uLogadoId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    CadastroJogador a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cartoes);

        imagensEscudos();
        imagensJogador();
        inicializarFirebase();
        getJogJaCadastrados();
        getJogadores();

        jogadores = findViewById(R.id.nomeJogador);
        nJogador = findViewById(R.id.nJogador);
        tJogador = findViewById(R.id.tJogador);
        imgJogador = findViewById(R.id.imgJogador);
        escudoTime = findViewById(R.id.escudoTime);
        maisA = findViewById(R.id.maisA);
        menosA = findViewById(R.id.menosA);
        maisV = findViewById(R.id.maisV);
        menosV = findViewById(R.id.menosV);
        aId = findViewById(R.id.aId);
        vId = findViewById(R.id.vId);
        salvar = findViewById(R.id.salvarId);
        cancelar = findViewById(R.id.cancelarId);

        jogadores.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);

        aId.setText( String.valueOf(ama) );

        maisA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ama++;
                aId.setText( String.valueOf(ama) );
            }
        });

        menosA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ama--;
                aId.setText( String.valueOf(ama) );
            }
        });

        aId.setText( String.valueOf(ama) );

        maisV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ver++;
                vId.setText( String.valueOf(ver) );
            }
        });

        menosV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ver--;
                vId.setText( String.valueOf(ver) );
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        jogadores.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, j));

        jogadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                nJogador.setText( String.valueOf( jogadores.getSelectedItem() ) );

                for(int j = 0; j < a.jogadores.length; j++){
                    if(i == j){
                        imgJogador.setImageResource( imagemJogadores.get( Integer.parseInt(pImagemJ.get(j)) ) );
                        escudoTime.setImageResource( images[ Integer.parseInt(pImagemC.get(j)) ] );
                        tJogador.setText(c.get(j));

                        posicaoEscudo = pImagemC.get(j);
                        posicaoimgJ = pImagemJ.get(j);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validacao = "s";
                for(int i = 0; i < jCadastrados.size(); i++){

                    if( nJogador.getText().toString().equals( jCadastrados.get(i) ) ){
                        nJogador.setError("Jogador já cadastrado");
                        Toast.makeText(AddCartoes.this, "Já existe um jogador chamado " + nJogador.getText().toString() + " cadastrado na lista de jogadores com cartão", Toast.LENGTH_SHORT).show();
                        validacao = "n";
                        break;
                    }

                }

                if(validacao.equals("s")){

                    Cartoes novoCartao = new Cartoes();

                    novoCartao.setId(UUID.randomUUID().toString());
                    novoCartao.setJogador(nJogador.getText().toString());
                    novoCartao.setImagemClube(posicaoEscudo);
                    novoCartao.setImagemJogador(posicaoimgJ);
                    novoCartao.setTime(tJogador.getText().toString());
                    novoCartao.setcAmarelo(aId.getText().toString());
                    novoCartao.setcVermelho(vId.getText().toString());

                    databaseReference.child("Cartoes").child(novoCartao.getId()).setValue(novoCartao);
                    Toast.makeText(AddCartoes.this, "O jogador " + nJogador.getText().toString() + " do " + tJogador.getText().toString()+ ", foi adicionado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }

            }

        });

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        uLogadoId = b.getString("idLogado");


    }

    private void getJogadores() {

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    jogador = objSnapshot.getValue(Jogador.class);
/*
                    if(jogador.getIdClube().equals(idC.get(6))){
                        databaseReference.child("JogadoresCadastrados").child(jogador.getIdJogador()).child("imgClubeAssociado").setValue(pImagemC.get(6));
                    }
*/
                    idCj.add(jogador.getIdClube());
                    j.add(jogador.getnJogador());
                    pImagemJ.add(jogador.getImagem());
                    pImagemC.add(jogador.getImgClubeAssociado());
                    c.add(jogador.getcJogador());

                    orientacao.add( String.valueOf(i) );
                    i++;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getJogJaCadastrados() {

        databaseReference.child("Cartoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Artilheiro jogador = objSnapshot.getValue(Artilheiro.class);

                    jCadastrados.add(jogador.getJogador());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void imagensEscudos(){

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

    public void imagensJogador(){
        a = new CadastroJogador();

        for( int i = 0; i < a.jogadores.length; i++ ){

            imagemJogadores.add( getResources().getIdentifier("jogador" + i , "drawable", getPackageName() ) );

        }

    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();

        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

}