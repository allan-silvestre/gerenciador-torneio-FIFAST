package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.agsistemas.torneiofifast.R.id.escudoTimeBronze;
import static com.agsistemas.torneiofifast.R.id.escudoTimeOuro;
import static com.agsistemas.torneiofifast.R.id.escudoTimePrata;

public class CadastroHistorico extends Activity {

    ImageView imgPlayerOuro, imgPlayerPrata, imgPlayerBronze, escudoOuro, escudoPrata, escudoBronze;

    String posicaoImgPlayerOuro, posicaoImgPlayerPrata, posicaoImgPlayerBronze, posicaoEscudoOuro, posicaoEscudoPrata, posicaoEscudoBronze;

    TextView nPlayerOuro, nTimeOuro, nPlayerPrata, nTimePrata, nPlayerBronze, nTimeBronze, dia, mes, ano, vFifaText;

    Spinner ouroId, prataId, bronzeId, vFifa;

    Button salvarId, cancelarId;

    ArrayList<Competidor> listClube = new ArrayList<>();
    Competidor p;

    List<String> listaPlayers = new ArrayList<String>();
    List<String> listaTimes = new ArrayList<String>();
    List<String> listaImgPlayers = new ArrayList<String>();
    List<String> listaEscudoTimes = new ArrayList<String>();

    List<Integer> imgPlayers = new ArrayList<>();
    final int[] images = new int[33];

    DateFormat diaa, mess, anoo;
    Date d, m, a;

    String listaVfifa[] =
            {"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "39", "40"};

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_historico);

        progressDialog = new ProgressDialog(CadastroHistorico.this, R.style.MyAlertDialogStyle );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        imgPlayerOuro = findViewById(R.id.imgPlayerOuro);
        imgPlayerPrata = findViewById(R.id.imgPlayerPrata);
        imgPlayerBronze = findViewById(R.id.imgPlayerBronze);

        escudoOuro = findViewById(escudoTimeOuro);
        escudoPrata = findViewById(escudoTimePrata);
        escudoBronze = findViewById(escudoTimeBronze);

        //data = findViewById(R.id.data);
        dia = findViewById(R.id.dia);
        mes = findViewById(R.id.mes);
        ano = findViewById(R.id.ano);
        vFifa = findViewById(R.id.vFifa);
        vFifaText = findViewById(R.id.vFifaText);
        nPlayerOuro = findViewById(R.id.nPlayerOuro);
        nTimeOuro = findViewById(R.id.nTimeOuro);
        nPlayerPrata = findViewById(R.id.nPlayerPrata);
        nTimePrata = findViewById(R.id.nTimePrata);
        nPlayerBronze = findViewById(R.id.nPlayerBronze);
        nTimeBronze = findViewById(R.id.nTimeBronze);

        ouroId = findViewById(R.id.PlayerOuroId);
        prataId = findViewById(R.id.prataId);
        bronzeId = findViewById(R.id.bronzeId);

        salvarId = findViewById(R.id.salvarId);
        cancelarId = findViewById(R.id.cancelarId);

        vFifa.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, listaVfifa));
        vFifa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                vFifaText.setText( "FIFA" + String.valueOf( vFifa.getSelectedItem() ) );

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ouroId.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, listaPlayers));
        ouroId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                nPlayerOuro.setText( String.valueOf( ouroId.getSelectedItem() ) );

                for(int j = 0; j < 8; j++){
                    if(i == j){
                        posicaoImgPlayerOuro = listaImgPlayers.get(j);
                        Picasso.with(CadastroHistorico.this).load(listaImgPlayers.get(j)).into(imgPlayerOuro);
                        //imgPlayerOuro.setImageResource( imgPlayers.get( Integer.parseInt( listaImgPlayers.get(j) ) ) );
                        escudoOuro.setImageResource( images[ Integer.parseInt(listaEscudoTimes.get(j)) ] );
                        posicaoEscudoOuro = listaEscudoTimes.get(j);
                        nTimeOuro.setText(listaTimes.get(j));

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prataId.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, listaPlayers));
        prataId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                nPlayerPrata.setText( String.valueOf( prataId.getSelectedItem() ) );

                for(int j = 0; j < 8; j++){
                    if(i == j){
                        posicaoImgPlayerPrata = listaImgPlayers.get(j);
                        Picasso.with(CadastroHistorico.this).load(listaImgPlayers.get(j)).into(imgPlayerPrata);
                        //imgPlayerPrata.setImageResource( imgPlayers.get( Integer.parseInt( listaImgPlayers.get(j) ) ) );
                        escudoPrata.setImageResource( images[ Integer.parseInt(listaEscudoTimes.get(j)) ] );
                        posicaoEscudoPrata = listaEscudoTimes.get(j);
                        nTimePrata.setText(listaTimes.get(j));

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bronzeId.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, listaPlayers));
        bronzeId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                nPlayerBronze.setText( String.valueOf( bronzeId.getSelectedItem() ) );

                for(int j = 0; j < 8; j++){
                    if(i == j){
                        posicaoImgPlayerBronze = listaImgPlayers.get(j);
                        Picasso.with(CadastroHistorico.this).load(listaImgPlayers.get(j)).into(imgPlayerBronze);
                        //imgPlayerBronze.setImageResource( imgPlayers.get( Integer.parseInt( listaImgPlayers.get(j) ) ) );
                        escudoBronze.setImageResource( images[ Integer.parseInt(listaEscudoTimes.get(j)) ] );
                        posicaoEscudoBronze = listaEscudoTimes.get(j);
                        nTimeBronze.setText(listaTimes.get(j));

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vFifa.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        ouroId.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        prataId.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        bronzeId.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);

        diaa = new SimpleDateFormat("dd");
        d = new Date();

        mess = new SimpleDateFormat("MM");
        m = new Date();

        anoo = new SimpleDateFormat("yyyy");
        a = new Date();

        //data.setText( diaa.format(d) + "/" + mess.format(m) + "/" + anoo.format(a) );

        dia.setText(diaa.format(d));
        mes.setText(mess.format(m));
        ano.setText(anoo.format(a));

        salvarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Historico h = new Historico();

                h.setId(ano.getText().toString() + "_" + mes.getText().toString() + "_" + dia.getText().toString());
                h.setData(dia.getText().toString() + "-" + mes.getText().toString() + "-" + ano.getText().toString());
                h.setVersaoFifa(vFifaText.getText().toString());

                h.setPlayerOuro(nPlayerOuro.getText().toString());
                h.setTimeOuro(nTimeOuro.getText().toString());
                h.setImgPlayerOuro(posicaoImgPlayerOuro);
                h.setImgOuro(posicaoEscudoOuro);

                h.setPlayerPrata(nPlayerPrata.getText().toString());
                h.setTimePrata(nTimePrata.getText().toString());
                h.setImgPlayerPrata(posicaoImgPlayerPrata);
                h.setImgPrata(posicaoEscudoPrata);

                h.setPlayerBronze(nPlayerBronze.getText().toString());
                h.setTimeBronze(nTimeBronze.getText().toString());
                h.setImgPlayerBronze(posicaoImgPlayerBronze);
                h.setImgBronze(posicaoEscudoBronze);

                databaseReference.child("historico").child("historicoTorneio").child(h.getId()).setValue(h);
                Toast.makeText(CadastroHistorico.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        cancelarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgPlayer();
        imagensEscudos();
        inicializarFirebase();
        listarClubes();

    }

    private void listarClubes(){

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listClube.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(Competidor.class);
                    listClube.add(p);

                    listaPlayers.add(p.getNome());
                    listaTimes.add(p.getTime());
                    listaImgPlayers.add(p.getImgPerfil());
                    listaEscudoTimes.add(p.getImagem());



                }

                Collections.sort(listClube);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void imgPlayer(){
        //CadastroCompetidorActivity a = new CadastroCompetidorActivity();
        for( int i = 0; i < 9; i++ ){

            imgPlayers.add( getResources().getIdentifier("participante" + i , "drawable", getPackageName() ) );

        }


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
        images[32] = R.drawable.villarreall;
    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}