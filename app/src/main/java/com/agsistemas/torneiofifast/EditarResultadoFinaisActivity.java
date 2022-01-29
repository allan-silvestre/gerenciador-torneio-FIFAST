package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditarResultadoFinaisActivity extends Activity {

    TextView timeC, timeF;
    EditText editGolsTimeC, editGolsTimeF, editGolsPenaltTimeC, editGolsPenaltTimeF;
    String keyGrupo, tituloPartida, idPartida, idTimeC, idTimeF, timeCnPartida, timeFnPartida, imgTC, imgTF, imgPC, imgPF;
    ImageView escudoTimeC, escudoTimeF, imgPlayerC, imgPlayerF;

    Button salvarResultado, zerarPartida, cancelar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Integer> imgPlayers = new ArrayList<>();
    final int[] images = new int[33];

    String uLogadoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_resultado_finais);

        imagensEscudos();
        imgPlayer();

        escudoTimeC = findViewById(R.id.escudoTimeC);
        escudoTimeF = findViewById(R.id.escudoTimeF);
        imgPlayerC = findViewById(R.id.imgPlayerC);
        imgPlayerF = findViewById(R.id.imgPlayerF);
        timeC = findViewById(R.id.editTimeC);
        timeF = findViewById(R.id.editTimeF);
        editGolsTimeC = findViewById(R.id.editGolsTimeC);
        editGolsPenaltTimeC = findViewById(R.id.editGolsPenaltTimeC);
        editGolsTimeF = findViewById(R.id.editGolsTimeF);
        editGolsPenaltTimeF = findViewById(R.id.editGolsPenaltTimeF);

        salvarResultado = findViewById(R.id.salvarResultado);
        zerarPartida = findViewById(R.id.zerarPartida);
        cancelar = findViewById(R.id.cancelar);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        tituloPartida = b.getString("tituloPartida");
        idPartida = b.getString("id");
        timeCnPartida = b.getString("timeCnPartida");
        timeFnPartida = b.getString("timeFnPartida");
        imgTC = b.getString("imagemTimeCasa");
        imgTF = b.getString("imagemTimeFora");
        imgPC = b.getString("imagemPlayerCasa");
        imgPF = b.getString("imagemPlayerFora");
        idTimeC = b.getString("idTimeCasa");
        idTimeF = b.getString("idTimeFora");
        timeC.setText(b.getString("timeCasa"));
        timeF.setText(b.getString("timeFora"));
        editGolsTimeC.setText(b.getString("golsC"));
        editGolsTimeF.setText(b.getString("golsF"));
        editGolsPenaltTimeC.setText(b.getString("golsPenaltC"));
        editGolsPenaltTimeF.setText(b.getString("golsPenaltF"));

        uLogadoId = b.getString("idLogado");

        escudoTimeC.setImageResource( images[ Integer.parseInt(imgTC) ] );
        escudoTimeF.setImageResource( images[ Integer.parseInt(imgTF) ] );

        Picasso.with(EditarResultadoFinaisActivity.this).load(imgPC).into(imgPlayerC);
        Picasso.with(EditarResultadoFinaisActivity.this).load(imgPF).into(imgPlayerF);


        salvarResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(EditarResultadoFinaisActivity.this, R.style.MyAlertDialogStyle );
                //Configura o título da progress dialog
                progressDialog.setTitle("Aguarde");
                //configura a mensagem de que está sendo feito o carregamento
                progressDialog.setMessage("Salvando alterações!!!");
                //configura se a progressDialog pode ser cancelada pelo usuário
                progressDialog.setCancelable(false);

                progressDialog.show();

                String tituloPartidaa = tituloPartida;
                String idPartidaa = idPartida;
                String timeCnPartidaa = timeCnPartida;
                String timeFnPartidaa = timeFnPartida;
                String idTimeCC = idTimeC;
                String idTimeFF = idTimeF;
                String timeCC = timeC.getText().toString();
                String timeFF = timeF.getText().toString();
                String golsCC = editGolsTimeC.getText().toString();
                String golsFF = editGolsTimeF.getText().toString();
                String golsPenaltCC = editGolsPenaltTimeC.getText().toString();
                String golsPenaltFF = editGolsPenaltTimeF.getText().toString();

                if(golsCC.equals("") || golsFF.equals("")){
                    validacao();
                    Toast.makeText(EditarResultadoFinaisActivity.this, "Adicione o resultado da partida para continuar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    JogosFg p = new JogosFg();
                    p.setTituloPartida(tituloPartidaa);
                    p.setId(idPartidaa);
                    p.setTimeCnPartida(timeCnPartidaa);
                    p.setTimeFnPartida(timeFnPartidaa);
                    p.setImagemTimeCasa(imgTC);
                    p.setImagemTimeFora(imgTF);
                    p.setImagemPlayerCasa(imgPC);
                    p.setImagemPlayerFora(imgPF);
                    p.setIdTimeCasa(idTimeCC);
                    p.setIdTimeFora(idTimeFF);
                    p.setTimeCasa(timeCC);
                    p.setTimeFora(timeFF);
                    p.setGolsC(golsCC);
                    p.setGolsF(golsFF);

                    if(golsPenaltCC.equals("") && golsPenaltFF.equals("")){
                        editGolsPenaltTimeC.setText("0");
                        editGolsPenaltTimeF.setText("0");
                        p.setGolsPenaltC(editGolsPenaltTimeC.getText().toString());
                        p.setGolsPenaltF(editGolsPenaltTimeF.getText().toString());
                    } else{
                        p.setGolsPenaltC(golsPenaltCC);
                        p.setGolsPenaltF(golsPenaltFF);
                    }

                    if ( Integer.parseInt(golsCC) + Integer.parseInt(golsPenaltCC) > Integer.parseInt(golsFF) + Integer.parseInt(golsPenaltFF) ) {
                        Toast.makeText(EditarResultadoFinaisActivity.this, "Parabéns, " + timeCC + " Você esta classificado para a final, boa sorte!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditarResultadoFinaisActivity.this, timeFF + " mais sorte na próxima!", Toast.LENGTH_SHORT).show();


                    } else if (Integer.parseInt(golsCC) < Integer.parseInt(golsFF)) {
                        Toast.makeText(EditarResultadoFinaisActivity.this, "Parabéns, " + timeFF + " pelos 3 pontos conquistados!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditarResultadoFinaisActivity.this, timeCC + " mais sorte na próxima!", Toast.LENGTH_SHORT).show();



                    } else if (Integer.parseInt(golsCC) == Integer.parseInt(golsFF)) {
                        Toast.makeText(EditarResultadoFinaisActivity.this, "Partida empatada!", Toast.LENGTH_SHORT).show();



                    }

                    databaseReference.child("Finais").child(p.getId()).setValue(p);
                    databaseReference.child("Notificacoes").child("JogosF").child("f").setValue(p);

                    progressDialog.dismiss();
                    finish();

                }

            }
        });

        zerarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zerarPartida();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        inicializarFirebase();

    }

    public void zerarPartida(){
        progressDialog = new ProgressDialog(EditarResultadoFinaisActivity.this );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Apagando dados da partida!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);

        progressDialog.show();

        String tituloPartidaa = tituloPartida;
        String keyGrupoo = keyGrupo;
        String idPartidaa = idPartida;
        String timeCnPartidaa = timeCnPartida;
        String timeFnPartidaa = timeFnPartida;
        String idTimeCC = idTimeC;
        String idTimeFF = idTimeF;
        String timeCC = timeC.getText().toString();
        String timeFF = timeF.getText().toString();
        String golsPenaltCC = editGolsPenaltTimeC.getText().toString();
        String golsPenaltFF = editGolsPenaltTimeF.getText().toString();

        JogosFg p = new JogosFg();

        p.setTituloPartida(tituloPartidaa);
        p.setKeyGrupo(keyGrupoo);
        p.setId(idPartidaa);
        p.setTimeCnPartida(timeCnPartidaa);
        p.setTimeFnPartida(timeFnPartidaa);
        p.setImagemTimeCasa(imgTC);
        p.setImagemTimeFora(imgTF);
        p.setImagemPlayerCasa(imgPC);
        p.setImagemPlayerFora(imgPF);
        p.setIdTimeCasa(idTimeCC);
        p.setIdTimeFora(idTimeFF);
        p.setTimeCasa(timeCC);
        p.setTimeFora(timeFF);
        p.setGolsC("");
        p.setGolsF("");
        p.setGolsPenaltC("0");
        p.setGolsPenaltF("0");

        databaseReference.child("Finais").child(p.getId()).setValue(p);

        Toast.makeText(EditarResultadoFinaisActivity.this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();

        progressDialog.dismiss();
        finish();

    }

    private void validacao() {
        String golsCC = editGolsTimeC.getText().toString();
        String golsFF = editGolsTimeF.getText().toString();

        if (golsCC.equals("")) {
            editGolsTimeC.setError("Campo obrigatório");
        } else if (golsFF.equals("")) {
            editGolsTimeF.setError("Campo obrigatório");
        }
    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void imgPlayer(){
        CadastroCompetidorActivity a = new CadastroCompetidorActivity();
        for( int i = 0; i < a.times.length; i++ ){

            imgPlayers.add( getResources().getIdentifier("participante" + i , "drawable",getPackageName() ) );

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

}