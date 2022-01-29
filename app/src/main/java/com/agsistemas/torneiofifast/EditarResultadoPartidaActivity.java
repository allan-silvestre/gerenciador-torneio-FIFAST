package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class EditarResultadoPartidaActivity extends Activity {

    ImageView escudoTimeC, escudoTimeF, imgPC, imgPF;
    TextView timeC, timeF, idPartidaBD;
    EditText editGolsTimeC, editGolsTimeF;
    String keyGrupo, keyGrupoPartida, idPartida, idTimeC, idTimeF, timeCnPartida, timeFnPartida, imgTC, imgTF, imgPlayerC, imgPlayerF;
    String uLogadoId;

    Button salvarResultado, zerarPartida, cancelar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    final int[] images = new int[33];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_resultado_partida);

        setTitle("Editar dados da partida");

        inicializarFirebase();
        imagensEscudos();

        escudoTimeC = findViewById(R.id.escudoTimeC);
        escudoTimeF = findViewById(R.id.escudoTimeF);
        imgPC = findViewById(R.id.imgPlayerC);
        imgPF = findViewById(R.id.imgPlayerF);
        timeC = findViewById(R.id.editTimeC);
        timeF = findViewById(R.id.editTimeF);
        editGolsTimeC = findViewById(R.id.editGolsTimeC);
        editGolsTimeF = findViewById(R.id.editGolsTimeF);
        idPartidaBD = findViewById(R.id.idPartida);

        salvarResultado = findViewById(R.id.salvarResultado);
        zerarPartida = findViewById(R.id.zerarPartida);
        cancelar = findViewById(R.id.cancelar);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        keyGrupo = b.getString("keyGrupo");
        keyGrupoPartida = b.getString("keyGrupoPartida");
        idPartida = b.getString("id");
        timeCnPartida = b.getString("timeCnPartida");
        timeFnPartida = b.getString("timeFnPartida");
        idTimeC = b.getString("idTimeCasa");
        idTimeF = b.getString("idTimeFora");
        imgTC = b.getString("imagemTimeCasa");
        imgTF = b.getString("imagemTimeFora");
        imgPlayerC= b.getString("imagemPlayerCasa");
        imgPlayerF = b.getString("imagemPlayerFora");

        uLogadoId = b.getString("idLogado");

        escudoTimeC.setImageResource(images[ Integer.parseInt( imgTC ) ]);
        escudoTimeF.setImageResource(images[ Integer.parseInt( imgTF ) ]);

        Picasso.with(EditarResultadoPartidaActivity.this).load(imgPlayerC).into(imgPC);
        Picasso.with(EditarResultadoPartidaActivity.this).load(imgPlayerF).into(imgPF);

        timeC.setText(b.getString("timeCasa"));
        timeF.setText(b.getString("timeFora"));
        editGolsTimeC.setText(b.getString("golsC"));
        editGolsTimeF.setText(b.getString("golsF"));

        idPartidaBD.setText("ID da Partida: " + idPartida);

        salvarResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idPartidaa = idPartida;
                String timeCnPartidaa = timeCnPartida;
                String timeFnPartidaa = timeFnPartida;
                String imagemTimeC = imgTC;
                String imagemTimeF = imgTF;
                String idTimeCC = idTimeC;
                String idTimeFF = idTimeF;
                String timeCC = timeC.getText().toString();
                String timeFF = timeF.getText().toString();
                String golsCC = editGolsTimeC.getText().toString();
                String golsFF = editGolsTimeF.getText().toString();

                if(golsCC.equals("") || golsFF.equals("")){
                    validacao();
                    Toast.makeText(EditarResultadoPartidaActivity.this, "Adicione o resultado da partida para continuar", Toast.LENGTH_SHORT).show();

                }else {

                    progressDialog = new ProgressDialog(EditarResultadoPartidaActivity.this, R.style.MyAlertDialogStyle );
                    //Configura o título da progress dialog
                    progressDialog.setTitle("Aguarde");
                    //configura a mensagem de que está sendo feito o carregamento
                    progressDialog.setMessage("Salvando alterações!!!");
                    //configura se a progressDialog pode ser cancelada pelo usuário
                    progressDialog.setCancelable(false);

                    progressDialog.show();

                    JogosFg p = new JogosFg();
                    p.setId(idPartidaa);
                    p.setImagemTimeCasa(imagemTimeC);
                    p.setImagemTimeFora(imagemTimeF);
                    p.setImagemPlayerCasa(imgPlayerC);
                    p.setImagemPlayerFora(imgPlayerF);
                    p.setTimeCnPartida(timeCnPartidaa);
                    p.setTimeFnPartida(timeFnPartidaa);
                    p.setIdTimeCasa(idTimeCC);
                    p.setIdTimeFora(idTimeFF);
                    p.setTimeCasa(timeCC);
                    p.setTimeFora(timeFF);
                    p.setGolsC(golsCC);
                    p.setGolsF(golsFF);

                    if (Integer.parseInt(golsCC) > Integer.parseInt(golsFF)) {
                        Toast.makeText(EditarResultadoPartidaActivity.this, "Parabéns, " + timeCC + " pelos 3 pontos conquistados!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditarResultadoPartidaActivity.this, timeFF + " mais sorte na próxima!", Toast.LENGTH_SHORT).show();

                                if (timeCnPartidaa.equals("1")){
                                databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1vit").setValue("1");
                                databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gp").setValue(golsCC);
                                databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gc").setValue(golsFF);

                            } else if (timeCnPartidaa.equals("2")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("3")) {

                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("4")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gc").setValue(golsFF);

                                }else if (timeCnPartidaa.equals("5")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("6")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("7")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gc").setValue(golsFF);
                                } else if (timeCnPartidaa.equals("8")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("9")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("10")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("11")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("12")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gc").setValue(golsFF);


                                } else if (timeCnPartidaa.equals("13")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gc").setValue(golsFF);

                                } else if (timeCnPartidaa.equals("14")) {
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14vit").setValue("1");
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gp").setValue(golsCC);
                                    databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gc").setValue(golsFF);

                                }


                        if (timeFnPartidaa.equals("1")){
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("2")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("3")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("4")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gc").setValue(golsCC);

                        }else if (timeFnPartidaa.equals("5")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("6")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("7")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gc").setValue(golsCC);


                        } else if (timeFnPartidaa.equals("8")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("9")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("10")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("11")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("12")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("13")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("14")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gc").setValue(golsCC);

                        }



                    } else if (Integer.parseInt(golsCC) < Integer.parseInt(golsFF)) {
                        Toast.makeText(EditarResultadoPartidaActivity.this, "Parabéns, " + timeFF + " pelos 3 pontos conquistados!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditarResultadoPartidaActivity.this, timeCC + " mais sorte na próxima!", Toast.LENGTH_SHORT).show();

                        if (timeCnPartidaa.equals("1")){
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gc").setValue(golsFF);


                        } else if (timeCnPartidaa.equals("2")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gc").setValue(golsFF);


                        } else if (timeCnPartidaa.equals("3")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gc").setValue(golsFF);


                        } else if (timeCnPartidaa.equals("4")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gc").setValue(golsFF);


                        }else if (timeCnPartidaa.equals("5")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gc").setValue(golsFF);


                        } else if (timeCnPartidaa.equals("6")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gc").setValue(golsFF);


                        } else if (timeCnPartidaa.equals("7")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gc").setValue(golsFF);


                        } else if (timeCnPartidaa.equals("8")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("9")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("10")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("11")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("12")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("13")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("14")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14der").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gc").setValue(golsFF);

                        }


                        if (timeFnPartidaa.equals("1")){
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("2")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("3")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("4")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gc").setValue(golsCC);


                        }else if (timeFnPartidaa.equals("5")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("6")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("7")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("8")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("9")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("10")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("11")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("12")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("13")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("14")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14vit").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gc").setValue(golsCC);

                        }



                    } else if (Integer.parseInt(golsCC) == Integer.parseInt(golsFF)) {
                        Toast.makeText(EditarResultadoPartidaActivity.this, "Partida empatada!", Toast.LENGTH_SHORT).show();

                        if (timeCnPartidaa.equals("1")){
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("2")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("3")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("4")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gc").setValue(golsFF);

                        }else if (timeCnPartidaa.equals("5")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("6")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("7")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("8")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("9")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("10")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("11")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("12")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("13")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gc").setValue(golsFF);

                        } else if (timeCnPartidaa.equals("14")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gp").setValue(golsCC);
                            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gc").setValue(golsFF);

                        }


                        if (timeFnPartidaa.equals("1")){
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("2")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("3")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("4")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gc").setValue(golsCC);

                        }else if (timeFnPartidaa.equals("5")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("6")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("7")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("8")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("9")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("10")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("11")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("12")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("13")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gc").setValue(golsCC);

                        } else if (timeFnPartidaa.equals("14")) {
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14emp").setValue("1");
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gp").setValue(golsFF);
                            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gc").setValue(golsCC);

                        }

                    }

                    databaseReference.child(keyGrupo).child(p.getId()).setValue(p);

                    if( keyGrupo.equals("JogosGrupoA") ){
                        databaseReference.child("Notificacoes").child("JogosGa").child("gA").setValue(p);
                    } else {
                        databaseReference.child("Notificacoes").child("JogosGb").child("gB").setValue(p);
                    }


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
                Intent intent = new Intent(EditarResultadoPartidaActivity.this, JogosFgActivity.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });


    }

    public void zerarPartida(){
        progressDialog = new ProgressDialog(EditarResultadoPartidaActivity.this, R.style.MyAlertDialogStyle );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Apagando dados da partida!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);

        progressDialog.show();

        String keyGrupoo = keyGrupo;
        String idPartidaa = idPartida;
        String timeCnPartidaa = timeCnPartida;
        String timeFnPartidaa = timeFnPartida;
        String imagemTimeC = imgTC;
        String imagemTimeF = imgTF;
        String idTimeCC = idTimeC;
        String idTimeFF = idTimeF;
        String timeCC = timeC.getText().toString();
        String timeFF = timeF.getText().toString();

        JogosFg p = new JogosFg();
        p.setKeyGrupo(keyGrupoo);
        p.setId(idPartidaa);
        p.setImagemTimeCasa(imagemTimeC);
        p.setImagemTimeFora(imagemTimeF);
        p.setImagemPlayerCasa(imgPlayerC);
        p.setImagemPlayerFora(imgPlayerF);
        p.setTimeCnPartida(timeCnPartidaa);
        p.setTimeFnPartida(timeFnPartidaa);
        p.setIdTimeCasa(idTimeCC);
        p.setIdTimeFora(idTimeFF);
        p.setTimeCasa(timeCC);
        p.setTimeFora(timeFF);
        p.setGolsC("");
        p.setGolsF("");

        if (timeCnPartidaa.equals("1")){
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j1gc").setValue("0");

        } else if (timeCnPartidaa.equals("2")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j2gc").setValue("0");

        } else if (timeCnPartidaa.equals("3")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j3gc").setValue("0");

        } else if (timeCnPartidaa.equals("4")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j4gc").setValue("0");

        }else if (timeCnPartidaa.equals("5")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j5gc").setValue("0");

        } else if (timeCnPartidaa.equals("6")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j6gc").setValue("0");

        } else if (timeCnPartidaa.equals("7")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j7gc").setValue("0");

        } else if (timeCnPartidaa.equals("8")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j8gc").setValue("0");

        } else if (timeCnPartidaa.equals("9")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j9gc").setValue("0");

        } else if (timeCnPartidaa.equals("10")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j10gc").setValue("0");

        } else if (timeCnPartidaa.equals("11")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j11gc").setValue("0");

        } else if (timeCnPartidaa.equals("12")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j12gc").setValue("0");

        } else if (timeCnPartidaa.equals("13")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j13gc").setValue("0");

        } else if (timeCnPartidaa.equals("14")) {
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeC).child("j14gc").setValue("0");

        }

        if (timeFnPartidaa.equals("1")){
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j1gc").setValue("0");

        } else if (timeFnPartidaa.equals("2")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j2gc").setValue("0");

        } else if (timeFnPartidaa.equals("3")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j3gc").setValue("0");

        } else if (timeFnPartidaa.equals("4")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j4gc").setValue("0");

        }else if (timeFnPartidaa.equals("5")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j5gc").setValue("0");

        } else if (timeFnPartidaa.equals("6")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j6gc").setValue("0");

        } else if (timeFnPartidaa.equals("7")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j7gc").setValue("0");

        } else if (timeFnPartidaa.equals("8")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j8gc").setValue("0");

        } else if (timeFnPartidaa.equals("9")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j9gc").setValue("0");

        } else if (timeFnPartidaa.equals("10")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j10gc").setValue("0");

        } else if (timeFnPartidaa.equals("11")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j11gc").setValue("0");

        } else if (timeFnPartidaa.equals("12")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j12gc").setValue("0");

        } else if (timeFnPartidaa.equals("13")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j13gc").setValue("0");

        } else if (timeFnPartidaa.equals("14")) {
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14vit").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14emp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14der").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gp").setValue("0");
            databaseReference.child(keyGrupoPartida).child(idTimeF).child("j14gc").setValue("0");

        }


        databaseReference.child(keyGrupoo).child(p.getId()).setValue(p);

        Toast.makeText(EditarResultadoPartidaActivity.this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();

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