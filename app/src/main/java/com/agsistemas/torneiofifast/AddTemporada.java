package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTemporada extends Activity {

    ImageView escudoTime;
    TextView temporada, nomeTime;
    EditText player, ouro, prata, bronze;
    Button salvar, cancelar;

    String times[] = {"ARSENAL", "ATALANTA", "A. BILBAO", "A. MADRID", "BARCELONA", "BAYERN",
            "BENFICA", "DORTMUND", "B.M.E.", "CHELSEA", "E.FRANKFURT",
            "HOFFENHEIM", "INTER", "JUVENTOS", "LAZIO", "LEIC. CITY", "LIVERPOOL", "M. CITY",
            "M. UNITED", "MILAN", "MONACO", "NAPOLI", "PORTO", "PSG", "R. MADRID", "R. SOCIEDAD", "ROMA",
            "SCHALKE 04", "SEVILLA", "SPORTING", "TOTTENHAM", "VALENCIA", "INATIVO"};

    Spinner sTime;
    String sT, posicaoEscudo;

    final int[] images = new int[33];

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_temporada);

        inicializarFirebase();
        imagensEscudos();

        escudoTime = findViewById(R.id.escudoTime1);
        sTime = findViewById(R.id.sTime1);
        temporada = findViewById(R.id.temporada);
        nomeTime = findViewById(R.id.nomeTime1);
        player = findViewById(R.id.nomePlayer);
        ouro = findViewById(R.id.ouro1);
        prata = findViewById(R.id.prata1);
        bronze = findViewById(R.id.bronze1);
        salvar = findViewById(R.id.salvarId2);
        cancelar = findViewById(R.id.cancelarId2);

        sTime.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, times));

        sTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sT = String.valueOf(sTime.getSelectedItem());

                for(int j = 0; j < 33; j++){
                    if(i == j){

                        escudoTime.setImageResource(images[j]);
                        posicaoEscudo = String.valueOf(j);
                        nomeTime.setText(times[j]);

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

                Competidor temporadas = new Competidor();

                temporadas.setStatus(temporada.getText().toString());
                temporadas.setId(temporada.getText().toString() + player.getText().toString());
                temporadas.setNome(player.getText().toString());
                temporadas.setTime(nomeTime.getText().toString());
                temporadas.setImagem(posicaoEscudo);
                temporadas.setTempAtualOuro(ouro.getText().toString());
                temporadas.setTempAtualPrata(prata.getText().toString());
                temporadas.setTempAtualBronze(bronze.getText().toString());

                int pts = Integer.parseInt(ouro.getText().toString()) * 10 + Integer.parseInt(prata.getText().toString()) * 6 + Integer.parseInt(bronze.getText().toString()) * 3;
                temporadas.setTempAtualPts(String.valueOf(pts));

                databaseReference.child("historico").child("temporadas").child(temporadas.getId()).setValue(temporadas);
                Toast.makeText(AddTemporada.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                //finish();

               // temporada.setText("");
                player.setText("");
                nomeTime.setText("");
                ouro.setText("");
                prata.setText("");
                bronze.setText("");

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}