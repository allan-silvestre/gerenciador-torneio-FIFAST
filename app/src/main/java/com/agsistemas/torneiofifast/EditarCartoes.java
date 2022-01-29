package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditarCartoes extends Activity {

    TextView aId, vId, nJogador, tJogador;
    Button salvar, cancelar, excluir, maisA, menosA, maisV, menosV;
    ImageView imgJogador, escudoTime;

    String id, time, jogador, cAmarelo, cVermelho, imgC, imgJ;

    final int[] images = new int[33];
    List<Integer> imagemJogadores = new ArrayList<>();

    int ama;
    int ver;

    String uLogadoId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    CadastroJogador a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cartoes);

        imagensEscudos();
        imagensJogador();
        inicializarFirebase();

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
        excluir = findViewById(R.id.excluirId);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        id = b.getString("id");
        jogador = b.getString("jogador");
        time = b.getString("time");
        cAmarelo = b.getString("cAmarelo");
        cVermelho = b.getString("cVermelho");
        imgC = b.getString("imagemClube");
        imgJ = b.getString("imagemJogador");

        uLogadoId = b.getString("idLogado");

        imgJogador.setImageResource( imagemJogadores.get( Integer.parseInt( imgJ )));
        escudoTime.setImageResource( images[ Integer.parseInt( imgC )]);

        nJogador.setText(jogador);
        tJogador.setText(time);
        aId.setText( cAmarelo );
        vId.setText( cVermelho );

        ama = Integer.parseInt( aId.getText().toString() );
        ver = Integer.parseInt( vId.getText().toString() );

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

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Cartoes novoCartao = new Cartoes();

                    novoCartao.setId(id);
                    novoCartao.setJogador(nJogador.getText().toString());
                    novoCartao.setImagemClube(imgC);
                    novoCartao.setImagemJogador(imgJ);
                    novoCartao.setTime(tJogador.getText().toString());
                    novoCartao.setcAmarelo(aId.getText().toString());
                    novoCartao.setcVermelho(vId.getText().toString());

                    databaseReference.child("Cartoes").child(novoCartao.getId()).setValue(novoCartao);
                    Toast.makeText(EditarCartoes.this, "Alterações salvas com sucesso!", Toast.LENGTH_LONG).show();

                    finish();


            }

        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaEmbCompetidor();
            }
        });

    }

    private void alertaEmbCompetidor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( "Tem certeza que deseja excluir o jogador " + jogador + " do " + time + " da lista de jogadores com cartão?" );
        builder.setPositiveButton("Sim",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idd) {

                databaseReference.child("Cartoes").child(id).removeValue();
                Toast.makeText(EditarCartoes.this, "Exclusão realizada com sucesso!", Toast.LENGTH_LONG).show();
                finish();

            }
        });
        builder.setNegativeButton("Não",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idd) {
                //nada
            }
        });
        builder.show();
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