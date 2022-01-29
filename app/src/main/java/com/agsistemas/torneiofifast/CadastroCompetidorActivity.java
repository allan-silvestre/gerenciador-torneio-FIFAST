package com.agsistemas.torneiofifast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CadastroCompetidorActivity extends AppCompatActivity {

    EditText nomeC, sobrenomeC, senhaC;
    Button salvarC, cancelar;
    ImageView escudoTime;

    String times[] = {"ARSENAL", "ATALANTA", "A. BILBAO", "A. MADRID", "BARCELONA", "BAYERN",
            "BENFICA", "DORTMUND", "B.M.E.", "CHELSEA", "E.FRANKFURT",
            "HOFFENHEIM", "INTER", "JUVENTOS", "LAZIO", "LEIC. CITY", "LIVERPOOL", "M. CITY",
            "M. UNITED", "MILAN", "MONACO", "NAPOLI", "PORTO", "PSG", "R. MADRID", "R. SOCIEDAD", "ROMA",
            "SCHALKE 04", "SEVILLA", "SPORTING", "TOTTENHAM", "VALENCIA", "INATIVO"};
    Spinner sTime;
    String sT, posicaoEscudo;

    final int[] images = new int[33];

    private List<Competidor> listCompetidor = new ArrayList<Competidor>();
    ArrayAdapter<Competidor> arrayAdapterCompetidor;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_competidor);
        setTitle("Cadastro de novo competidor");

        nomeC = findViewById(R.id.nomeC);
        sobrenomeC = findViewById(R.id.sobrenomeC);
        escudoTime = findViewById(R.id.escudoTime);
        sTime = findViewById(R.id.sTime);
        senhaC = findViewById(R.id.senhaC);
        salvarC = findViewById(R.id.salvarC);

        inicializarFirebase();
        imagensEscudos();

        sTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sT = String.valueOf(sTime.getSelectedItem());

                for(int j = 0; j < 33; j++){
                    if(i == j){

                        escudoTime.setImageResource(images[j]);
                        posicaoEscudo = String.valueOf(j);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sTime.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, times));

        salvarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeCC = nomeC.getText().toString();
                String sobrenomeCC = sobrenomeC.getText().toString();
                String timeCC = sT;
                String senhaCC = senhaC.getText().toString();

                if( nomeCC.equals("") || sobrenomeCC.equals("") || timeCC.equals("") || senhaCC.equals("") ){
                    validacao();
                    Toast.makeText(CadastroCompetidorActivity.this, "Preencha todos os campos para continuar", Toast.LENGTH_SHORT).show();
                }else {
                    Random random = new Random();

                    Competidor p = new Competidor();
                    p.setId(UUID.randomUUID().toString());
                    p.setOrdem(String.valueOf(random.nextInt(99)));
                    p.setImagem(posicaoEscudo);
                    p.setNome(nomeCC);
                    p.setSobrenome(sobrenomeCC);
                    p.setTime(timeCC);
                    p.setSenha(senhaCC);
                    p.setTempAtualOuro("0");
                    p.setTempAtualPrata("0");
                    p.setTempAtualBronze("0");
                    p.setTempAtualPts("0");

                    databaseReference.child("Competidores").child(p.getId()).setValue(p);

                    Toast.makeText(CadastroCompetidorActivity.this, "Novo competidor cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    finish();

                }

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

    private void validacao(){
        String nomeCC = nomeC.getText().toString();
        String sobrenomeCC = sobrenomeC.getText().toString();
        String senhaCC = senhaC.getText().toString();

        if(nomeCC.equals("")){
            nomeC.setError("Campo obrigatório");
        }else if(sobrenomeCC.equals("")){
            sobrenomeC.setError("Campo obrigatório");
        }else if(senhaCC.equals("")){
            senhaC.setError("Campo obrigatório");
        }

    }

}