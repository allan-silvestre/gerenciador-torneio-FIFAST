package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class EditarArtilheiro extends Activity {
    ImageView escudoTime, imgJogador;
    TextView nJog, gols, tJog;
    Button salvar, cancelar, excluir, mais, menos;

    String id, time, jogador, gJog, imgC, imgJ;

    ProgressDialog progressDialog;

    List<String> c = new ArrayList<String>();
    List<String> pImagemC = new ArrayList<String>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    final int[] images = new int[33];

    int gol;

    List<Integer> imagemJogadores = new ArrayList<>();

    String uLogadoId;

    CadastroJogador a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_artilheiro);

        inicializarFirebase();
        imagensEscudos();
        imagensJogador();

        nJog = findViewById(R.id.nJogador);
        imgJogador = findViewById(R.id.imgJogador);
        escudoTime = findViewById(R.id.escudoTime);
        tJog = findViewById(R.id.tJogador);
        mais = findViewById(R.id.mais);
        menos = findViewById(R.id.menos);
        gols = findViewById(R.id.golsId);
        salvar = findViewById(R.id.salvarId);
        cancelar = findViewById(R.id.cancelarId);
        excluir = findViewById(R.id.excluirId);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        id = b.getString("id");
        jogador = b.getString("jogador");
        time = b.getString("time");
        gJog = b.getString("gols");
        imgC = b.getString("imagemClube");
        imgJ = b.getString("imagemJogador");

        uLogadoId = b.getString("idLogado");


        nJog.setText( jogador );
        tJog.setText( time );
        gols.setText( gJog );

        imgJogador.setImageResource( imagemJogadores.get( Integer.parseInt( imgJ )));
        escudoTime.setImageResource( images[ Integer.parseInt( imgC )]);

        gol = Integer.parseInt( gols.getText().toString() );

        mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gol++;
                gols.setText( String.valueOf(gol) );
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gol--;
                gols.setText( String.valueOf(gol) );
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Artilheiro editarArtilheiro = new Artilheiro();

                    editarArtilheiro.setId(id);
                    editarArtilheiro.setJogador(jogador);
                    editarArtilheiro.setTime(time);
                    editarArtilheiro.setImagemClube(imgC);
                    editarArtilheiro.setImagemJogador(imgJ);
                    editarArtilheiro.setGols(gols.getText().toString());

                    databaseReference.child("Artilharia").child(editarArtilheiro.getId()).setValue(editarArtilheiro);
                    Toast.makeText(EditarArtilheiro.this, "Alterações salvas com sucesso!", Toast.LENGTH_LONG).show();

                    finish();


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        builder.setMessage( "Tem certeza que deseja excluir o jogador " + jogador + " do " + time + " da artilharia?" );
        builder.setPositiveButton("Sim",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idd) {

                databaseReference.child("Artilharia").child(id).removeValue();
                Toast.makeText(EditarArtilheiro.this, "Exclusão realizada com sucesso!", Toast.LENGTH_LONG).show();
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