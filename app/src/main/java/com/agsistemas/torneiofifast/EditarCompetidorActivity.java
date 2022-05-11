package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class EditarCompetidorActivity extends Activity {
    ImageView escudoTime, imgStatusP;
    ImageButton mais, menos;
    EditText nomeC, sobrenomeC, timeC, senhaC, ordem, ouro, prata, bronze, statusP, linkImgPerfil;
    TextView pts, textNivelAcesso, textStatus;
    String idC, time, img, nivelAcesso, status;
    Button salvarC, cancelarC;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String times[] = {"ARSENAL", "ATALANTA", "A. BILBAO", "A. MADRID", "BARCELONA", "BAYERN",
            "BENFICA", "DORTMUND", "B.M.E.", "CHELSEA", "E.FRANKFURT",
            "HOFFENHEIM", "INTER", "JUVENTOS", "LAZIO", "LEIC. CITY", "LIVERPOOL", "M. CITY",
            "M. UNITED", "MILAN", "MONACO", "NAPOLI", "PORTO", "PSG", "R. MADRID", "R. SOCIEDAD", "ROMA",
            "SCHALKE 04", "SEVILLA", "SPORTING", "TOTTENHAM", "VALENCIA", "INATIVO"};

    String statusSelect[] = {"ATIVO", "INATIVO"};

    String nivelAcessoSelect[] = {"PADRÃO", "ADMINISTRADOR"};

    Spinner sTime, nAcesso, statusUser;
    String sT, posicaoEscudo;
    String uLogadoId;
    String pImg[] = new String[25];
    String pOver[] = new String[25];
    String pId[] = new String[25];

    final int[] images = new int[33];
    final int[] imgS = new int[4];

    int imgSp;

    ArrayList<Jogador> listGerC = new ArrayList<>();
    Jogador p;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_competidor);

        inicializarFirebase();
        imagensStatus();
        imagensEscudos();

        nomeC = findViewById(R.id.nomeCedit);
        sobrenomeC = findViewById(R.id.sobrenomeCedit);
        escudoTime = findViewById(R.id.escudoTime);
        senhaC = findViewById(R.id.senhaCedit);
        salvarC = findViewById(R.id.salvarCedit);
        cancelarC = findViewById(R.id.cancelarCedit);
        textNivelAcesso = findViewById(R.id.nivelAcesso);
        textStatus = findViewById(R.id.statusId);

        nAcesso = findViewById(R.id.spinnerNivelAcesso);
        statusUser = findViewById(R.id.spinnerStatus);
        sTime = findViewById(R.id.sTime);
        ordem = findViewById(R.id.ordem);
        statusP = findViewById(R.id.statusP);
        imgStatusP = findViewById(R.id.imgStatus);
        mais = findViewById(R.id.mais);
        menos = findViewById(R.id.menos);
        linkImgPerfil = findViewById(R.id.linkPerfil);

        ouro = findViewById(R.id.ouro);
        prata = findViewById(R.id.prata);
        bronze = findViewById(R.id.bronze);
        pts = findViewById(R.id.pts);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        idC = b.getString("id");
        nivelAcesso = b.getString("nivelAcesso");
        status = b.getString("status");
        nomeC.setText(b.getString("nome"));
        sobrenomeC.setText(b.getString("sobrenome"));
        img = b.getString("imagem");
        time = b.getString("time");
        senhaC.setText(b.getString("senha"));
        ordem.setText(b.getString("ordem"));
        final String imgPerfil = b.getString("imgPerfil");

        uLogadoId = b.getString("idLogado");

        ouro.setText(b.getString("TempAtualOuro"));
        prata.setText(b.getString("TempAtualPrata"));
        bronze.setText(b.getString("TempAtualBronze"));
        pts.setText(b.getString("TempAtualPts"));
        statusP.setText(b.getString("statusP"));
        linkImgPerfil.setText(b.getString("imgPerfil"));

        imgStatusP.setImageResource(imgS[ Integer.parseInt( statusP.getText().toString()) ]);

        imgSp = Integer.parseInt(statusP.getText().toString());

        mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    imgSp++;
                    statusP.setText( String.valueOf(imgSp) );
                    imgStatusP.setImageResource(imgS[ imgSp ]);

            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    imgSp--;
                    statusP.setText( String.valueOf(imgSp) );
                    imgStatusP.setImageResource(imgS[ imgSp ]);

            }
        });

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
        sTime.setSelection(Integer.parseInt(img));

        nAcesso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                textNivelAcesso.setText(String.valueOf(nAcesso.getSelectedItem()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        nAcesso.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, nivelAcessoSelect));

        statusUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                textStatus.setText(String.valueOf(statusUser.getSelectedItem()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        statusUser.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, statusSelect));

        /*
        salvarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(EditarCompetidorActivity.this, R.style.MyAlertDialogStyle );
                //Configura o título da progress dialog
                progressDialog.setTitle("Aguarde");
                //configura a mensagem de que está sendo feito o carregamento
                progressDialog.setMessage("Carregando!!!");
                //configura se a progressDialog pode ser cancelada pelo usuário
                progressDialog.setCancelable(false);
                progressDialog.show();

                String idCC = idC;
                String nomeCC = nomeC.getText().toString();
                String sobrenomeCC = sobrenomeC.getText().toString();
                String senhaCC = senhaC.getText().toString();
                String ordemC = ordem.getText().toString();

                if( nomeCC.equals("") || sobrenomeCC.equals("") || senhaCC.equals("") ){
                    validacao();
                    Toast.makeText(EditarCompetidorActivity.this, "Preencha todos os campos para continuar", Toast.LENGTH_SHORT).show();
                }else {
                    Competidor p = new Competidor();
                    p.setId(idCC);
                    p.setStatus(textStatus.getText().toString());
                    p.setNivelAcesso(textNivelAcesso.getText().toString());
                    p.setNome(nomeCC);
                    p.setSobrenome(sobrenomeCC);
                    p.setImagem(posicaoEscudo);
                    p.setImgPerfil(imgPerfil);
                    p.setTime(sT);
                    p.setSenha(senhaCC);
                    p.setOrdem(ordemC);
                    p.setTempAtualOuro(ouro.getText().toString());
                    p.setTempAtualPrata(prata.getText().toString());
                    p.setTempAtualBronze(bronze.getText().toString());
                    p.setStatusP(statusP.getText().toString());

                    p.setP1Img(pImg[0]);
                    p.setP1Over(pOver[0]);
                    p.setP1Over(pOver[0]);

                    p.setP2Img(pImg[1]);
                    p.setP2Over(pOver[1]);

                    p.setP3Img(pImg[2]);
                    p.setP3Over(pOver[2]);

                    p.setP4Img(pImg[3]);
                    p.setP4Over(pOver[3]);

                    p.setP5Img(pImg[4]);
                    p.setP5Over(pOver[4]);

                    p.setP6Img(pImg[5]);
                    p.setP6Over(pOver[5]);

                    p.setP7Img(pImg[6]);
                    p.setP7Over(pOver[6]);

                    p.setP8Img(pImg[7]);
                    p.setP8Over(pOver[7]);

                    p.setP9Img(pImg[8]);
                    p.setP9Over(pOver[8]);

                    p.setP10Img(pImg[9]);
                    p.setP10Over(pOver[9]);

                    p.setP11Img(pImg[10]);
                    p.setP11Over(pOver[10]);

                    p.setP12Img(pImg[11]);
                    p.setP12Over(pOver[11]);

                    p.setP13Img(pImg[12]);
                    p.setP13Over(pOver[12]);

                    p.setP14Img(pImg[13]);
                    p.setP14Over(pOver[13]);

                    p.setP15Img(pImg[14]);
                    p.setP15Over(pOver[14]);

                    p.setP16Img(pImg[15]);
                    p.setP16Over(pOver[15]);

                    p.setP17Img(pImg[16]);
                    p.setP17Over(pOver[16]);

                    p.setP18Img(pImg[17]);
                    p.setP18Over(pOver[17]);

                    p.setP19Img(pImg[18]);
                    p.setP19Over(pOver[18]);



                if( p.getStatus().equals("INATIVO") ){
                    p.setTempAtualPts("-100");
                } else if( p.getStatus().equals("ATIVO") ){
                    int ptss = Integer.parseInt( ouro.getText().toString() ) * 10 + Integer.parseInt( prata.getText().toString() ) * 6 + Integer.parseInt( bronze.getText().toString() ) * 3;
                    p.setTempAtualPts(String.valueOf(ptss));
                }

                    databaseReference.child("Competidores").child(p.getId()).setValue(p);
                    alterarClubeJogadores();

                    Toast.makeText(EditarCompetidorActivity.this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();

                }

            }
        });

         */

        salvarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(EditarCompetidorActivity.this, R.style.MyAlertDialogStyle );
                //Configura o título da progress dialog
                progressDialog.setTitle("Aguarde");
                //configura a mensagem de que está sendo feito o carregamento
                progressDialog.setMessage("Carregando!!!");
                //configura se a progressDialog pode ser cancelada pelo usuário
                progressDialog.setCancelable(false);
                progressDialog.show();

                String nomeCC = nomeC.getText().toString();
                String sobrenomeCC = sobrenomeC.getText().toString();
                String senhaCC = senhaC.getText().toString();
                String ordemC = ordem.getText().toString();

                if( nomeCC.equals("") || sobrenomeCC.equals("") || senhaCC.equals("") ){
                    validacao();
                    Toast.makeText(EditarCompetidorActivity.this, "Preencha todos os campos para continuar", Toast.LENGTH_SHORT).show();
                }else {

                    databaseReference.child("Competidores").child(idC).child("nome").setValue(nomeC.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("sobrenome").setValue(sobrenomeC.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("time").setValue(sT);
                    databaseReference.child("Competidores").child(idC).child("imagem").setValue(posicaoEscudo);
                    databaseReference.child("Competidores").child(idC).child("senha").setValue(senhaC.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("ordem").setValue(ordemC);
                    databaseReference.child("Competidores").child(idC).child("tempAtualOuro").setValue(ouro.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("tempAtualPrata").setValue(prata.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("tempAtualBronze").setValue(bronze.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("statusP").setValue(statusP.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("status").setValue(textStatus.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("nivelAcesso").setValue(textNivelAcesso.getText().toString());
                    databaseReference.child("Competidores").child(idC).child("imgPerfil").setValue(linkImgPerfil.getText().toString());

                    int ptss = Integer.parseInt( ouro.getText().toString() ) * 10 + Integer.parseInt( prata.getText().toString() ) * 6 + Integer.parseInt( bronze.getText().toString() ) * 3;
                    databaseReference.child("Competidores").child(idC).child("tempAtualPts").setValue(String.valueOf(ptss));

                    alterarClubeJogadores();

                    Toast.makeText(EditarCompetidorActivity.this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }

            }
        });

        cancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void alterarClubeJogadores() {

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(Jogador.class);

                    if(p.getIdClube().equals(idC)){
                        listGerC.add(p);
                        databaseReference.child("JogadoresCadastrados").child(p.getIdJogador()).child("cJogador").setValue(sT + "/" + nomeC.getText().toString());
                        databaseReference.child("JogadoresCadastrados").child(p.getIdJogador()).child("imgClubeAssociado").setValue(posicaoEscudo);
                    }

                }

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

    private void validacao(){
        String nomeCC = nomeC.getText().toString();
        String sobrenomeCC = sobrenomeC.getText().toString();
        String timeCC = timeC.getText().toString();
        String senhaCC = senhaC.getText().toString();

        if(nomeCC.equals("")){
            nomeC.setError("Campo obrigatório");
        }else if(sobrenomeCC.equals("")){
            sobrenomeC.setError("Campo obrigatório");
        }else if(timeCC.equals("")){
            timeC.setError("Campo obrigatório");
        }else if(senhaCC.equals("")){
            senhaC.setError("Campo obrigatório");
        }

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

    private void imagensStatus(){

        imgS[0] = R.drawable.statusp0;
        imgS[1] = R.drawable.statusp1;
        imgS[2] = R.drawable.statusp2;
        imgS[3] = R.drawable.statusp3;
    }

}