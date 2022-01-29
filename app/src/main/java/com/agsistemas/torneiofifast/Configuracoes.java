package com.agsistemas.torneiofifast;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

public class Configuracoes extends Activity {

    Button novoCompetidor, novoTorneio, addTemporada, cadastroJogador, gCompetidores, embaralhar,
            addHistorico, finalizarTorneio, dataProxT, editarPerfil;
    TextView titulo;

    private List<Competidor> listCompetidor = new ArrayList<Competidor>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Competidor p[] = new Competidor[8];
    TabelaClassificacaoFg q;
    Competidor pGrupos[] = new Competidor[8];

    String listaTimes[] = new String[10];
    String idTimes[] = new String[10];

    String iTime[] = new String[10];
    String iPlayer[] = new String[10];

    String statusTime[] = new String[10];

    final int[] images = new int[33];

    List<String> nAleatorio = new ArrayList<String>();

    Config config;

    ArrayList<Competidor> listUserLogado = new ArrayList<>();
    Competidor uL;
    String uLogadoId;

    String sf01_Time01, sf01_Time02, sf02_Time01, sf02_Time02, statusTorneio, formatoTorneio;

    ImageView usuarioLogadoImg, getUsuarioLogadoImgClube;
    TextView usuarioInfoNome, usuarioInfoNivel;
    ImageButton tabelaId, historicoId, transf, sair;

    TabelaClassificacaoFg a[] = new TabelaClassificacaoFg[8];
    TabelaClassificacaoFg b[] = new TabelaClassificacaoFg[4];

    String sort[] = new String[8];

    DateFormat diaa, mess, anoo;
    Date dia, mes, ano;

    ProgressDialog progressDialog;



    String permissoes []  = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.BATTERY_STATS,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            };

    public static final int PERMISSOES_REQUERIDAS = 1;

    PendingIntent pendingIntent;

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        //super.onBackPressed();  // optional depending on your needs
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        permissao();
        if(permissao()){
        }

        nAleatorio.add("0");
        nAleatorio.add("1");
        nAleatorio.add("6");
        nAleatorio.add("7");
        nAleatorio.add("4");
        nAleatorio.add("5");
        nAleatorio.add("2");
        nAleatorio.add("3");

        inicializarFirebase();
        getConfigServidor();
        getUsuarioLogado();

        //startService(new Intent(getBaseContext(), NotificationServices.class));
        //sendBroadcast(new Intent(getBaseContext(), BootCompletedIntentReceiver.class));
/*
        final String SOME_ACTION = Intent.ACTION_BOOT_COMPLETED;
        IntentFilter intentFilter = new IntentFilter(SOME_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(getBaseContext(), NotificationServices.class));
        }



        BootCompletedIntentReceiver bootCompletedIntentReceiver = new BootCompletedIntentReceiver();
        registerReceiver(bootCompletedIntentReceiver, intentFilter);

 */
/*
        Intent in = new Intent(getBaseContext(), BootCompletedIntentReceiver.class);
        PendingIntent p = PendingIntent.getBroadcast(getBaseContext(), 0, in, 0);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 3);

        AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(getBaseContext().ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(),
                5000,
                p);
*/

        titulo = findViewById(R.id.titulo);
        usuarioInfoNome = findViewById(R.id.usuarioInfo);
        usuarioInfoNivel = findViewById(R.id.nivelAcesso);
        usuarioLogadoImg = findViewById(R.id.usuarioImg);
        getUsuarioLogadoImgClube = findViewById(R.id.imgClube);
        editarPerfil = findViewById(R.id.editarPerfil);

        cadastroJogador = findViewById(R.id.cadJogador);
        gCompetidores = findViewById(R.id.gCompetidores);
        embaralhar = findViewById(R.id.embaralharC);
        novoTorneio = findViewById(R.id.novoTorneio);
        novoCompetidor = findViewById(R.id.novoCompetidor);
        addHistorico = findViewById(R.id.addHistorico);
        addTemporada = findViewById(R.id.gTemporadas);
        finalizarTorneio = findViewById(R.id.finalizarTorneio);
        dataProxT = findViewById(R.id.dataProximoTorneio);

        tabelaId = findViewById(R.id.tabelaId);
        transf = findViewById(R.id.transfId);
        historicoId = findViewById(R.id.historicoId);
        sair = findViewById(R.id.sairId);

        Bundle b = new Bundle();
        b = getIntent().getExtras();

        uLogadoId = b.getString("idLogado");


        dataProxT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText dataProxTorneio = new EditText(Configuracoes.this);

                diaa = new SimpleDateFormat("dd");
                dia = new Date();

                mess = new SimpleDateFormat("MM");
                mes = new Date();

                anoo = new SimpleDateFormat("yyyy");
                ano = new Date();

                dataProxTorneio.setGravity(Gravity.CENTER);
                dataProxTorneio.setText( diaa.format(dia) + " / " +  mess.format(mes) + " / " + anoo.format(ano));

                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracoes.this);
                builder.setTitle("DEFINA A DATA DO PRÓXIMO TORNEIO");
                //builder.setMessage( "Tem certeza que dejesa continuar?" );
                builder.setIcon(R.drawable.ic_launcher);
                builder.setView(dataProxTorneio);
                builder.setPositiveButton("SALVAR",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        databaseReference.child("Configuracoes").child("fTorneio").child("dataProxT").setValue(dataProxTorneio.getText().toString());
                        Toast.makeText( Configuracoes.this, "Data do próximo torneio alterada para" + "\n" + dataProxTorneio.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                });
                builder.show();


            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(Configuracoes.this, R.style.MyAlertDialogStyle );
                //Configura o título da progress dialog
                progressDialog.setTitle("FIFAST");
                //configura a mensagem de que está sendo feito o carregamento
                progressDialog.setMessage("Saindo...");
                //configura se a progressDialog pode ser cancelada pelo usuário
                progressDialog.setCancelable(false);
                progressDialog.show();

                databaseReference.child("Competidores").child(uLogadoId).child("macLogado").setValue("0");
                Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                startActivity(intent);
                //teste();
                finish();

            }
        });

        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Configuracoes.this, EditarPerfil.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);

            }
        });

        embaralhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                embaralharCompetidores();

                Toast.makeText( Configuracoes.this, "Sorteio realizado!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracoes.this);
                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("SORTEIO");
                builder.setMessage("Realizar novo sorteio?");
                builder.setPositiveButton("NÂO",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        //Nenhuma ação

                    }
                });
                builder.setNegativeButton("SIM",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracoes.this);
                        builder.setTitle("RESULTADO DO SORTEIO");
                        builder.setIcon(R.drawable.ic_launcher);
                        builder.setMessage(
                                        sort[0] + "\n" +
                                        sort[1] + "\n" +
                                        sort[2] + "\n" +
                                        sort[3] + "\n" + "\n" +
                                        sort[4] + "\n" +
                                        sort[5] + "\n" +
                                        sort[6] + "\n" +
                                        sort[7]
                        );
                        builder.setPositiveButton("FECHAR",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                //Nenhuma ação

                            }
                        });
                        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                            }
                        });
                        builder.show();

                    }
                });
                builder.show();




            }
        });


        finalizarTorneio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracoes.this);
                builder.setTitle("Alterar status do torneio");
                builder.setMessage( "Tem certeza que dejesa continuar?" );
                builder.setIcon(R.drawable.ic_launcher);
                builder.setPositiveButton("NÂO",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        //Nenhuma ação

                    }
                });
                builder.setNegativeButton("SIM",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        if( statusTorneio.equals("andamento") ){
                            statusTorneio = "finalizado";
                            finalizarTorneio.setText("PAUSADO");
                        } else {
                            statusTorneio = "andamento";
                            finalizarTorneio.setText("TORNEIO EM ANDAMENTO");
                        }
                        databaseReference.child("Configuracoes").child("fTorneio").child("statusTorneio").setValue(statusTorneio);

                        Toast.makeText( Configuracoes.this, "Status do torneio alterado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });


        gCompetidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configuracoes.this, Competidores.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });


        cadastroJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configuracoes.this, CadastroJogador.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        tabelaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(Configuracoes.this, TabelaFg.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        addTemporada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(Configuracoes.this, AddTemporada.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        transf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
                Intent intent = new Intent(Configuracoes.this, GerenciarClubes.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        historicoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(Configuracoes.this, TemporadaAtual.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });


        novoCompetidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configuracoes.this, CadastroCompetidorActivity.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);
            }
        });

        novoTorneio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaEmbCompetidor();

            }
        });

        addHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configuracoes.this, CadastroHistorico.class);
                intent.putExtra("idLogado", uLogadoId );
                startActivity(intent);

            }
        });


    }

    private void teste(){
        databaseReference.child("Competidores").child(uLogadoId).child("macLogado").setValue("0");
    }

    private void embaralharCompetidores(){

        Collections.shuffle(nAleatorio);

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p[i] = objSnapshot.getValue(Competidor.class);
                    listCompetidor.add(p[i]);
                    p[i].setOrdem(nAleatorio.get(i));

                    // get nomes para exibir apos o sorteio
                    if(p[i].getOrdem().equals("0")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[0] = "";
                        } else{
                            sort[0] = p[i].getNome();
                        }

                    } else if(p[i].getOrdem().equals("1")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[1] = "";
                        } else{
                            sort[1] = p[i].getNome();
                        }

                    } else if(p[i].getOrdem().equals("2")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[2] = "";
                        } else{
                            sort[2] = p[i].getNome();
                        }

                    } else if(p[i].getOrdem().equals("3")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[3] = "";
                        } else{
                            sort[3] = p[i].getNome();
                        }

                    } else if(p[i].getOrdem().equals("4")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[4] = "";
                        } else{
                            sort[4] = p[i].getNome();

                        }

                    } else if(p[i].getOrdem().equals("5")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[5] = "";
                        } else{
                            sort[5] = p[i].getNome();
                        }

                    } else if(p[i].getOrdem().equals("6")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[6] = "";
                        } else{
                            sort[6] = p[i].getNome();
                        }

                    } else if(p[i].getOrdem().equals("7")){
                        if(p[i].getNome().equals("INATIVO")){
                            sort[7] = "";
                        } else{
                            sort[7] = p[i].getNome();
                        }

                    }

                    i++;
                }

                finalizarTorneio.setText(nAleatorio.get(0) + nAleatorio.get(1) + nAleatorio.get(2) + nAleatorio.get(3) + nAleatorio.get(4) + nAleatorio.get(5) + nAleatorio.get(6) + nAleatorio.get(7) );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void listarDadosEgerarParticipantesGaGb(){
        progressDialog = new ProgressDialog(Configuracoes.this );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Gerando grupos e jogos!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);

        progressDialog.show();

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listCompetidor.clear();
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    pGrupos[i] = objSnapshot.getValue(Competidor.class);
                    q = objSnapshot.getValue(TabelaClassificacaoFg.class);
                    listCompetidor.add(pGrupos[i]);

                    i++;

                    progressDialog.dismiss();

                    }

                //Collections.shuffle(nAleatorio);

                pGrupos[0].setOrdem(nAleatorio.get(0));
                pGrupos[1].setOrdem(nAleatorio.get(1));
                pGrupos[2].setOrdem(nAleatorio.get(2));
                pGrupos[3].setOrdem(nAleatorio.get(3));

                pGrupos[4].setOrdem(nAleatorio.get(4));
                pGrupos[5].setOrdem(nAleatorio.get(5));
                pGrupos[6].setOrdem(nAleatorio.get(6));
                pGrupos[7].setOrdem(nAleatorio.get(7));


                for( int a = 0; a < 8; a++){

                    if( Integer.parseInt( listCompetidor.get( a ).getOrdem() )  < 4 ){
                        // GRUPO A
                        q.setId(pGrupos[a].getOrdem() + pGrupos[a].getId());
                        q.setStatus(pGrupos[a].getStatus());
                        q.setTime(pGrupos[a].getTime());
                        q.setImagem(pGrupos[a].getImagem());
                        q.setImgPerfil(pGrupos[a].getImgPerfil());
                        q.setPlayer(pGrupos[a].getNome());
                        q.setPontos(q.getPontos());
                        q.setVit(q.getVit());
                        q.setEmp(q.getEmp());
                        q.setDer(q.getDer());
                        q.setGp(q.getGp());
                        q.setGc(q.getGc());
                        q.setSg(q.getSg());

                        databaseReference.child("ParticipantesGrupoA").child(q.getId()).setValue(q);

                    } else {
                        q.setId(pGrupos[a].getOrdem() + pGrupos[a].getId());
                        q.setStatus(pGrupos[a].getStatus());
                        q.setTime(pGrupos[a].getTime());
                        q.setImagem(pGrupos[a].getImagem());
                        q.setImgPerfil(pGrupos[a].getImgPerfil());
                        q.setPlayer(p[a].getNome());
                        q.setPontos(q.getPontos());
                        q.setVit(q.getVit());
                        q.setEmp(q.getEmp());
                        q.setDer(q.getDer());
                        q.setGp(q.getGp());
                        q.setGc(q.getGc());
                        q.setSg(q.getSg());

                        databaseReference.child("ParticipantesGrupoB").child(q.getId()).setValue(q);
                    }

                }


                progressDialog.show();


                //semifinal 01*******************************************************************
                JogosFg semiFinal01 = new JogosFg();

                semiFinal01.setTituloPartida("SEMIFINAIS");
                semiFinal01.setId("aaaSemiFinal_01");
                semiFinal01.setTimeCnPartida("1");
                semiFinal01.setTimeFnPartida("1");

                semiFinal01.setTimeCasa(sf01_Time01);
                semiFinal01.setTimeFora(sf01_Time02);

                databaseReference.child("Finais").child(semiFinal01.getId()).setValue(semiFinal01);

                //semifinal 02
                JogosFg semiFinal02 = new JogosFg();

                semiFinal02.setTituloPartida("");
                semiFinal02.setId("bbbSemiFinal_02");
                semiFinal02.setTimeCnPartida("1");
                semiFinal02.setTimeFnPartida("1");

                semiFinal02.setTimeCasa(sf02_Time01);
                semiFinal02.setTimeFora(sf02_Time02);

                databaseReference.child("Finais").child(semiFinal02.getId()).setValue(semiFinal02);

                //disputa 3
                JogosFg disp03 = new JogosFg();

                disp03.setTituloPartida("3ª LUGAR");
                disp03.setId("cccDisputa_03");
                disp03.setTimeCnPartida("2");
                disp03.setTimeFnPartida("2");

                databaseReference.child("Finais").child(disp03.getId()).setValue(disp03);

                //final
                JogosFg finalJ = new JogosFg();

                finalJ.setTituloPartida("FINAL");
                finalJ.setId("dddFinal");
                finalJ.setTimeCnPartida("2");
                finalJ.setTimeFnPartida("2");

                databaseReference.child("Finais").child(finalJ.getId()).setValue(finalJ);

                progressDialog.dismiss();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gerarJogosGrupoA(){
        databaseReference.child("ParticipantesGrupoA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    TabelaClassificacaoFg gA = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    idTimes[i] = gA.getId();
                    listaTimes[i] = gA.getPlayer();
                    iTime[i] = gA.getImagem();
                    iPlayer[i] = gA.getImgPerfil();
                    statusTime[i] = gA.getStatus();

                    i++;

                }


                //Grupo A Turno 1
                //rodada 1
                //jogo 1

                JogosFg grupoA_T1R1J1 = new JogosFg();

                grupoA_T1R1J1.setId("grupoA_T1R1J1");
                grupoA_T1R1J1.setTimeCnPartida("1");
                grupoA_T1R1J1.setTimeFnPartida("1");

                grupoA_T1R1J1.setStatusTimeCasa(statusTime[0]);
                grupoA_T1R1J1.setStatusTimeFora(statusTime[3]);

                grupoA_T1R1J1.setIdTimeCasa(idTimes[0]);
                grupoA_T1R1J1.setIdTimeFora(idTimes[3]);

                grupoA_T1R1J1.setImagemTimeCasa(iTime[0]);
                grupoA_T1R1J1.setImagemTimeFora(iTime[3]);

                grupoA_T1R1J1.setImagemPlayerCasa(iPlayer[0]);
                grupoA_T1R1J1.setImagemPlayerFora(iPlayer[3]);

                grupoA_T1R1J1.setTimeCasa(listaTimes[0]);
                grupoA_T1R1J1.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosGrupoA").child(grupoA_T1R1J1.getId()).setValue(grupoA_T1R1J1);

                //GrupoA
                //rodada 1
                //jogo 2
                JogosFg grupoA_T1R1J2 = new JogosFg();

                grupoA_T1R1J2.setId("grupoA_T1R1J2");
                grupoA_T1R1J2.setTimeCnPartida("1");
                grupoA_T1R1J2.setTimeFnPartida("1");

                grupoA_T1R1J2.setStatusTimeCasa(statusTime[1]);
                grupoA_T1R1J2.setStatusTimeFora(statusTime[2]);

                grupoA_T1R1J2.setIdTimeCasa(idTimes[1]);
                grupoA_T1R1J2.setIdTimeFora(idTimes[2]);

                grupoA_T1R1J2.setImagemTimeCasa(iTime[1]);
                grupoA_T1R1J2.setImagemTimeFora(iTime[2]);

                grupoA_T1R1J2.setImagemPlayerCasa(iPlayer[1]);
                grupoA_T1R1J2.setImagemPlayerFora(iPlayer[2]);

                grupoA_T1R1J2.setTimeCasa(listaTimes[1]);
                grupoA_T1R1J2.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosGrupoA").child(grupoA_T1R1J2.getId()).setValue(grupoA_T1R1J2);


                //GrupoA
                //rodada 2
                //jogo 1
                JogosFg grupoA_T1R2J1 = new JogosFg();

                grupoA_T1R2J1.setId("grupoA_T1R2J1");
                grupoA_T1R2J1.setTimeCnPartida("2");
                grupoA_T1R2J1.setTimeFnPartida("2");

                grupoA_T1R2J1.setStatusTimeCasa(statusTime[0]);
                grupoA_T1R2J1.setStatusTimeFora(statusTime[2]);

                grupoA_T1R2J1.setIdTimeCasa(idTimes[0]);
                grupoA_T1R2J1.setIdTimeFora(idTimes[2]);

                grupoA_T1R2J1.setImagemTimeCasa(iTime[0]);
                grupoA_T1R2J1.setImagemTimeFora(iTime[2]);

                grupoA_T1R2J1.setImagemPlayerCasa(iPlayer[0]);
                grupoA_T1R2J1.setImagemPlayerFora(iPlayer[2]);

                grupoA_T1R2J1.setTimeCasa(listaTimes[0]);
                grupoA_T1R2J1.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosGrupoA").child(grupoA_T1R2J1.getId()).setValue(grupoA_T1R2J1);

                //GrupoA
                //rodada 2
                //jogo 2
                JogosFg grupoA_T1R2J2 = new JogosFg();

                grupoA_T1R2J2.setId("grupoA_T1R2J2");
                grupoA_T1R2J2.setTimeCnPartida("2");
                grupoA_T1R2J2.setTimeFnPartida("2");

                grupoA_T1R2J2.setStatusTimeCasa(statusTime[3]);
                grupoA_T1R2J2.setStatusTimeFora(statusTime[1]);

                grupoA_T1R2J2.setIdTimeCasa(idTimes[3]);
                grupoA_T1R2J2.setIdTimeFora(idTimes[1]);

                grupoA_T1R2J2.setImagemTimeCasa(iTime[3]);
                grupoA_T1R2J2.setImagemTimeFora(iTime[1]);

                grupoA_T1R2J2.setImagemPlayerCasa(iPlayer[3]);
                grupoA_T1R2J2.setImagemPlayerFora(iPlayer[1]);

                grupoA_T1R2J2.setTimeCasa(listaTimes[3]);
                grupoA_T1R2J2.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosGrupoA").child(grupoA_T1R2J2.getId()).setValue(grupoA_T1R2J2);

                //GrupoA
                //rodada 3
                //jogo 1
                JogosFg grupoA_T1R3J1 = new JogosFg();

                grupoA_T1R3J1.setId("grupoA_T1R3J1");
                grupoA_T1R3J1.setTimeCnPartida("3");
                grupoA_T1R3J1.setTimeFnPartida("3");

                grupoA_T1R3J1.setStatusTimeCasa(statusTime[0]);
                grupoA_T1R3J1.setStatusTimeFora(statusTime[1]);

                grupoA_T1R3J1.setIdTimeCasa(idTimes[0]);
                grupoA_T1R3J1.setIdTimeFora(idTimes[1]);

                grupoA_T1R3J1.setImagemTimeCasa(iTime[0]);
                grupoA_T1R3J1.setImagemTimeFora(iTime[1]);

                grupoA_T1R3J1.setImagemPlayerCasa(iPlayer[0]);
                grupoA_T1R3J1.setImagemPlayerFora(iPlayer[1]);

                grupoA_T1R3J1.setTimeCasa(listaTimes[0]);
                grupoA_T1R3J1.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosGrupoA").child(grupoA_T1R3J1.getId()).setValue(grupoA_T1R3J1);

                //GrupoA
                //rodada 3
                //jogo 2
                JogosFg grupoA_T1R3J2 = new JogosFg();

                grupoA_T1R3J2.setId("grupoA_T1R3J2");
                grupoA_T1R3J2.setTimeCnPartida("3");
                grupoA_T1R3J2.setTimeFnPartida("3");

                grupoA_T1R3J2.setStatusTimeCasa(statusTime[2]);
                grupoA_T1R3J2.setStatusTimeFora(statusTime[3]);

                grupoA_T1R3J2.setIdTimeCasa(idTimes[2]);
                grupoA_T1R3J2.setIdTimeFora(idTimes[3]);

                grupoA_T1R3J2.setImagemTimeCasa(iTime[2]);
                grupoA_T1R3J2.setImagemTimeFora(iTime[3]);

                grupoA_T1R3J2.setImagemPlayerCasa(iPlayer[2]);
                grupoA_T1R3J2.setImagemPlayerFora(iPlayer[3]);

                grupoA_T1R3J2.setTimeCasa(listaTimes[2]);
                grupoA_T1R3J2.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosGrupoA").child(grupoA_T1R3J2.getId()).setValue(grupoA_T1R3J2);

                //Fim Grupo A Turno 1

                //Grupo A Turno 2
                //rodada 1
                //jogo 1
                JogosFg grupoA_T2R1J1 = new JogosFg();

                grupoA_T2R1J1.setId("grupoA_T2R1J1");
                grupoA_T2R1J1.setTimeCnPartida("4");
                grupoA_T2R1J1.setTimeFnPartida("4");

                grupoA_T2R1J1.setStatusTimeCasa(statusTime[3]);
                grupoA_T2R1J1.setStatusTimeFora(statusTime[0]);

                grupoA_T2R1J1.setIdTimeCasa(idTimes[3]);
                grupoA_T2R1J1.setIdTimeFora(idTimes[0]);

                grupoA_T2R1J1.setImagemTimeCasa(iTime[3]);
                grupoA_T2R1J1.setImagemTimeFora(iTime[0]);

                grupoA_T2R1J1.setImagemPlayerCasa(iPlayer[3]);
                grupoA_T2R1J1.setImagemPlayerFora(iPlayer[0]);

                grupoA_T2R1J1.setTimeCasa(listaTimes[3]);
                grupoA_T2R1J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosGrupoA").child(grupoA_T2R1J1.getId()).setValue(grupoA_T2R1J1);


                //GrupoA
                //rodada 1
                //jogo 2
                JogosFg grupoA_T2R1J2 = new JogosFg();

                grupoA_T2R1J2.setId("grupoA_T2R1J2");
                grupoA_T2R1J2.setTimeCnPartida("4");
                grupoA_T2R1J2.setTimeFnPartida("4");

                grupoA_T2R1J2.setStatusTimeCasa(statusTime[2]);
                grupoA_T2R1J2.setStatusTimeFora(statusTime[1]);

                grupoA_T2R1J2.setIdTimeCasa(idTimes[2]);
                grupoA_T2R1J2.setIdTimeFora(idTimes[1]);

                grupoA_T2R1J2.setImagemTimeCasa(iTime[2]);
                grupoA_T2R1J2.setImagemTimeFora(iTime[1]);

                grupoA_T2R1J2.setImagemPlayerCasa(iPlayer[2]);
                grupoA_T2R1J2.setImagemPlayerFora(iPlayer[1]);

                grupoA_T2R1J2.setTimeCasa(listaTimes[2]);
                grupoA_T2R1J2.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosGrupoA").child(grupoA_T2R1J2.getId()).setValue(grupoA_T2R1J2);


                //GrupoA
                //rodada 2
                //jogo 1
                JogosFg grupoA_T2R2J1 = new JogosFg();

                grupoA_T2R2J1.setId("grupoA_T2R2J1");
                grupoA_T2R2J1.setTimeCnPartida("5");
                grupoA_T2R2J1.setTimeFnPartida("5");

                grupoA_T2R2J1.setStatusTimeCasa(statusTime[2]);
                grupoA_T2R2J1.setStatusTimeFora(statusTime[0]);

                grupoA_T2R2J1.setIdTimeCasa(idTimes[2]);
                grupoA_T2R2J1.setIdTimeFora(idTimes[0]);

                grupoA_T2R2J1.setImagemTimeCasa(iTime[2]);
                grupoA_T2R2J1.setImagemTimeFora(iTime[0]);

                grupoA_T2R2J1.setImagemPlayerCasa(iPlayer[2]);
                grupoA_T2R2J1.setImagemPlayerFora(iPlayer[0]);

                grupoA_T2R2J1.setTimeCasa(listaTimes[2]);
                grupoA_T2R2J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosGrupoA").child(grupoA_T2R2J1.getId()).setValue(grupoA_T2R2J1);

                //GrupoA
                //rodada 2
                //jogo 2
                JogosFg grupoA_T2R2J2 = new JogosFg();

                grupoA_T2R2J2.setId("grupoA_T2R2J2");
                grupoA_T2R2J2.setTimeCnPartida("5");
                grupoA_T2R2J2.setTimeFnPartida("5");

                grupoA_T2R2J2.setStatusTimeCasa(statusTime[1]);
                grupoA_T2R2J2.setStatusTimeFora(statusTime[3]);

                grupoA_T2R2J2.setIdTimeCasa(idTimes[1]);
                grupoA_T2R2J2.setIdTimeFora(idTimes[3]);

                grupoA_T2R2J2.setImagemTimeCasa(iTime[1]);
                grupoA_T2R2J2.setImagemTimeFora(iTime[3]);

                grupoA_T2R2J2.setImagemPlayerCasa(iPlayer[1]);
                grupoA_T2R2J2.setImagemPlayerFora(iPlayer[3]);

                grupoA_T2R2J2.setTimeCasa(listaTimes[1]);
                grupoA_T2R2J2.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosGrupoA").child(grupoA_T2R2J2.getId()).setValue(grupoA_T2R2J2);

                //GrupoA
                //rodada 3
                //jogo 1
                JogosFg grupoA_T2R3J1 = new JogosFg();

                grupoA_T2R3J1.setId("grupoA_T2R3J1");
                grupoA_T2R3J1.setTimeCnPartida("6");
                grupoA_T2R3J1.setTimeFnPartida("6");

                grupoA_T2R3J1.setStatusTimeCasa(statusTime[1]);
                grupoA_T2R3J1.setStatusTimeFora(statusTime[0]);

                grupoA_T2R3J1.setIdTimeCasa(idTimes[1]);
                grupoA_T2R3J1.setIdTimeFora(idTimes[0]);

                grupoA_T2R3J1.setImagemTimeCasa(iTime[1]);
                grupoA_T2R3J1.setImagemTimeFora(iTime[0]);

                grupoA_T2R3J1.setImagemPlayerCasa(iPlayer[1]);
                grupoA_T2R3J1.setImagemPlayerFora(iPlayer[0]);

                grupoA_T2R3J1.setTimeCasa(listaTimes[1]);
                grupoA_T2R3J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosGrupoA").child(grupoA_T2R3J1.getId()).setValue(grupoA_T2R3J1);

                //GrupoA
                //rodada 3
                //jogo 2
                JogosFg grupoA_T2R3J2 = new JogosFg();

                grupoA_T2R3J2.setId("grupoA_T2R3J2");
                grupoA_T2R3J2.setTimeCnPartida("6");
                grupoA_T2R3J2.setTimeFnPartida("6");

                grupoA_T2R3J2.setStatusTimeCasa(statusTime[3]);
                grupoA_T2R3J2.setStatusTimeFora(statusTime[2]);

                grupoA_T2R3J2.setIdTimeCasa(idTimes[3]);
                grupoA_T2R3J2.setIdTimeFora(idTimes[2]);

                grupoA_T2R3J2.setImagemTimeCasa(iTime[3]);
                grupoA_T2R3J2.setImagemTimeFora(iTime[2]);

                grupoA_T2R3J2.setImagemPlayerCasa(iPlayer[3]);
                grupoA_T2R3J2.setImagemPlayerFora(iPlayer[2]);

                grupoA_T2R3J2.setTimeCasa(listaTimes[3]);
                grupoA_T2R3J2.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosGrupoA").child(grupoA_T2R3J2.getId()).setValue(grupoA_T2R3J2);

                //fim Grupo A turno 2

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gerarJogosGrupoB(){
        databaseReference.child("ParticipantesGrupoB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 4;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    TabelaClassificacaoFg gB = objSnapshot.getValue(TabelaClassificacaoFg.class);

                    idTimes[i] = gB.getId();
                    listaTimes[i] = gB.getPlayer();
                    iTime[i] = gB.getImagem();
                    iPlayer[i] = gB.getImgPerfil();
                    statusTime[i] = gB.getStatus();

                    i++;

                }



                //Grupo B Turno 1
                //rodada 1
                //jogo 1

                JogosFg grupoB_T1R1J1 = new JogosFg();

                grupoB_T1R1J1.setId("grupoB_T1R1J1");
                grupoB_T1R1J1.setTimeCnPartida("1");
                grupoB_T1R1J1.setTimeFnPartida("1");

                grupoB_T1R1J1.setStatusTimeCasa(statusTime[4]);
                grupoB_T1R1J1.setStatusTimeFora(statusTime[7]);

                grupoB_T1R1J1.setIdTimeCasa(idTimes[4]);
                grupoB_T1R1J1.setIdTimeFora(idTimes[7]);

                grupoB_T1R1J1.setImagemTimeCasa(iTime[4]);
                grupoB_T1R1J1.setImagemTimeFora(iTime[7]);

                grupoB_T1R1J1.setImagemPlayerCasa(iPlayer[4]);
                grupoB_T1R1J1.setImagemPlayerFora(iPlayer[7]);

                grupoB_T1R1J1.setTimeCasa(listaTimes[4]);
                grupoB_T1R1J1.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosGrupoB").child(grupoB_T1R1J1.getId()).setValue(grupoB_T1R1J1);

                //GrupoB
                //rodada 1
                //jogo 2
                JogosFg grupoB_T1R1J2 = new JogosFg();

                grupoB_T1R1J2.setId("grupoB_T1R1J2");
                grupoB_T1R1J2.setTimeCnPartida("1");
                grupoB_T1R1J2.setTimeFnPartida("1");

                grupoB_T1R1J2.setStatusTimeCasa(statusTime[5]);
                grupoB_T1R1J2.setStatusTimeFora(statusTime[6]);

                grupoB_T1R1J2.setIdTimeCasa(idTimes[5]);
                grupoB_T1R1J2.setIdTimeFora(idTimes[6]);

                grupoB_T1R1J2.setImagemTimeCasa(iTime[5]);
                grupoB_T1R1J2.setImagemTimeFora(iTime[6]);

                grupoB_T1R1J2.setImagemPlayerCasa(iPlayer[5]);
                grupoB_T1R1J2.setImagemPlayerFora(iPlayer[6]);

                grupoB_T1R1J2.setTimeCasa(listaTimes[5]);
                grupoB_T1R1J2.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosGrupoB").child(grupoB_T1R1J2.getId()).setValue(grupoB_T1R1J2);


                //GrupoB
                //rodada 2
                //jogo 1
                JogosFg grupoB_T1R2J1 = new JogosFg();

                grupoB_T1R2J1.setId("grupoB_T1R2J1");
                grupoB_T1R2J1.setTimeCnPartida("2");
                grupoB_T1R2J1.setTimeFnPartida("2");

                grupoB_T1R2J1.setStatusTimeCasa(statusTime[4]);
                grupoB_T1R2J1.setStatusTimeFora(statusTime[6]);

                grupoB_T1R2J1.setIdTimeCasa(idTimes[4]);
                grupoB_T1R2J1.setIdTimeFora(idTimes[6]);

                grupoB_T1R2J1.setImagemTimeCasa(iTime[4]);
                grupoB_T1R2J1.setImagemTimeFora(iTime[6]);

                grupoB_T1R2J1.setImagemPlayerCasa(iPlayer[4]);
                grupoB_T1R2J1.setImagemPlayerFora(iPlayer[6]);

                grupoB_T1R2J1.setTimeCasa(listaTimes[4]);
                grupoB_T1R2J1.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosGrupoB").child(grupoB_T1R2J1.getId()).setValue(grupoB_T1R2J1);

                //GrupoB
                //rodada 2
                //jogo 2
                JogosFg grupoB_T1R2J2 = new JogosFg();

                grupoB_T1R2J2.setId("grupoB_T1R2J2");
                grupoB_T1R2J2.setTimeCnPartida("2");
                grupoB_T1R2J2.setTimeFnPartida("2");

                grupoB_T1R2J2.setStatusTimeCasa(statusTime[7]);
                grupoB_T1R2J2.setStatusTimeFora(statusTime[5]);

                grupoB_T1R2J2.setIdTimeCasa(idTimes[7]);
                grupoB_T1R2J2.setIdTimeFora(idTimes[5]);

                grupoB_T1R2J2.setImagemTimeCasa(iTime[7]);
                grupoB_T1R2J2.setImagemTimeFora(iTime[5]);

                grupoB_T1R2J2.setImagemPlayerCasa(iPlayer[7]);
                grupoB_T1R2J2.setImagemPlayerFora(iPlayer[5]);

                grupoB_T1R2J2.setTimeCasa(listaTimes[7]);
                grupoB_T1R2J2.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosGrupoB").child(grupoB_T1R2J2.getId()).setValue(grupoB_T1R2J2);

                //GrupoB
                //rodada 3
                //jogo 1
                JogosFg grupoB_T1R3J1 = new JogosFg();

                grupoB_T1R3J1.setId("grupoB_T1R3J1");
                grupoB_T1R3J1.setTimeCnPartida("3");
                grupoB_T1R3J1.setTimeFnPartida("3");

                grupoB_T1R3J1.setStatusTimeCasa(statusTime[4]);
                grupoB_T1R3J1.setStatusTimeFora(statusTime[5]);

                grupoB_T1R3J1.setIdTimeCasa(idTimes[4]);
                grupoB_T1R3J1.setIdTimeFora(idTimes[5]);

                grupoB_T1R3J1.setImagemTimeCasa(iTime[4]);
                grupoB_T1R3J1.setImagemTimeFora(iTime[5]);

                grupoB_T1R3J1.setImagemPlayerCasa(iPlayer[4]);
                grupoB_T1R3J1.setImagemPlayerFora(iPlayer[5]);

                grupoB_T1R3J1.setTimeCasa(listaTimes[4]);
                grupoB_T1R3J1.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosGrupoB").child(grupoB_T1R3J1.getId()).setValue(grupoB_T1R3J1);

                //GrupoB
                //rodada 2
                //jogo 2
                JogosFg grupoB_T1R3J2 = new JogosFg();

                grupoB_T1R3J2.setId("grupoB_T1R3J2");
                grupoB_T1R3J2.setTimeCnPartida("3");
                grupoB_T1R3J2.setTimeFnPartida("3");

                grupoB_T1R3J2.setStatusTimeCasa(statusTime[6]);
                grupoB_T1R3J2.setStatusTimeFora(statusTime[7]);

                grupoB_T1R3J2.setIdTimeCasa(idTimes[6]);
                grupoB_T1R3J2.setIdTimeFora(idTimes[7]);

                grupoB_T1R3J2.setImagemTimeCasa(iTime[6]);
                grupoB_T1R3J2.setImagemTimeFora(iTime[7]);

                grupoB_T1R3J2.setImagemPlayerCasa(iPlayer[6]);
                grupoB_T1R3J2.setImagemPlayerFora(iPlayer[7]);

                grupoB_T1R3J2.setTimeCasa(listaTimes[6]);
                grupoB_T1R3J2.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosGrupoB").child(grupoB_T1R3J2.getId()).setValue(grupoB_T1R3J2);

                //Fim Grupo B Turno 1

                // Grupo B Turno 2
                //rodada 1
                //jogo 1
                JogosFg grupoB_T2R1J1 = new JogosFg();

                grupoB_T2R1J1.setId("grupoB_T2R1J1");
                grupoB_T2R1J1.setTimeCnPartida("4");
                grupoB_T2R1J1.setTimeFnPartida("4");

                grupoB_T2R1J1.setStatusTimeCasa(statusTime[7]);
                grupoB_T2R1J1.setStatusTimeFora(statusTime[4]);

                grupoB_T2R1J1.setIdTimeCasa(idTimes[7]);
                grupoB_T2R1J1.setIdTimeFora(idTimes[4]);

                grupoB_T2R1J1.setImagemTimeCasa(iTime[7]);
                grupoB_T2R1J1.setImagemTimeFora(iTime[4]);

                grupoB_T2R1J1.setImagemPlayerCasa(iPlayer[7]);
                grupoB_T2R1J1.setImagemPlayerFora(iPlayer[4]);

                grupoB_T2R1J1.setTimeCasa(listaTimes[7]);
                grupoB_T2R1J1.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosGrupoB").child(grupoB_T2R1J1.getId()).setValue(grupoB_T2R1J1);


                //GrupoB
                //rodada 1
                //jogo 2
                JogosFg grupoB_T2R1J2 = new JogosFg();

                grupoB_T2R1J2.setId("grupoB_T2R1J2");
                grupoB_T2R1J2.setTimeCnPartida("4");
                grupoB_T2R1J2.setTimeFnPartida("4");

                grupoB_T2R1J2.setStatusTimeCasa(statusTime[6]);
                grupoB_T2R1J2.setStatusTimeFora(statusTime[5]);

                grupoB_T2R1J2.setIdTimeCasa(idTimes[6]);
                grupoB_T2R1J2.setIdTimeFora(idTimes[5]);

                grupoB_T2R1J2.setImagemTimeCasa(iTime[6]);
                grupoB_T2R1J2.setImagemTimeFora(iTime[5]);

                grupoB_T2R1J2.setImagemPlayerCasa(iPlayer[6]);
                grupoB_T2R1J2.setImagemPlayerFora(iPlayer[5]);

                grupoB_T2R1J2.setTimeCasa(listaTimes[6]);
                grupoB_T2R1J2.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosGrupoB").child(grupoB_T2R1J2.getId()).setValue(grupoB_T2R1J2);


                //GrupoB
                //rodada 2
                //jogo 1
                JogosFg grupoB_T2R2J1 = new JogosFg();

                grupoB_T2R2J1.setId("grupoB_T2R2J1");
                grupoB_T2R2J1.setTimeCnPartida("5");
                grupoB_T2R2J1.setTimeFnPartida("5");

                grupoB_T2R2J1.setStatusTimeCasa(statusTime[6]);
                grupoB_T2R2J1.setStatusTimeFora(statusTime[4]);

                grupoB_T2R2J1.setIdTimeCasa(idTimes[6]);
                grupoB_T2R2J1.setIdTimeFora(idTimes[4]);

                grupoB_T2R2J1.setImagemTimeCasa(iTime[6]);
                grupoB_T2R2J1.setImagemTimeFora(iTime[4]);

                grupoB_T2R2J1.setImagemPlayerCasa(iPlayer[6]);
                grupoB_T2R2J1.setImagemPlayerFora(iPlayer[4]);

                grupoB_T2R2J1.setTimeCasa(listaTimes[6]);
                grupoB_T2R2J1.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosGrupoB").child(grupoB_T2R2J1.getId()).setValue(grupoB_T2R2J1);

                //GrupoB
                //rodada 2
                //jogo 2
                JogosFg grupoB_T2R2J2 = new JogosFg();

                grupoB_T2R2J2.setId("grupoB_T2R2J2");
                grupoB_T2R2J2.setTimeCnPartida("5");
                grupoB_T2R2J2.setTimeFnPartida("5");

                grupoB_T2R2J2.setStatusTimeCasa(statusTime[5]);
                grupoB_T2R2J2.setStatusTimeFora(statusTime[7]);

                grupoB_T2R2J2.setIdTimeCasa(idTimes[5]);
                grupoB_T2R2J2.setIdTimeFora(idTimes[7]);

                grupoB_T2R2J2.setImagemTimeCasa(iTime[5]);
                grupoB_T2R2J2.setImagemTimeFora(iTime[7]);

                grupoB_T2R2J2.setImagemPlayerCasa(iPlayer[5]);
                grupoB_T2R2J2.setImagemPlayerFora(iPlayer[7]);

                grupoB_T2R2J2.setTimeCasa(listaTimes[5]);
                grupoB_T2R2J2.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosGrupoB").child(grupoB_T2R2J2.getId()).setValue(grupoB_T2R2J2);

                //GrupoA
                //rodada 3
                //jogo 1
                JogosFg grupoB_T2R3J1 = new JogosFg();

                grupoB_T2R3J1.setId("grupoB_T2R3J1");
                grupoB_T2R3J1.setTimeCnPartida("6");
                grupoB_T2R3J1.setTimeFnPartida("6");

                grupoB_T2R3J1.setStatusTimeCasa(statusTime[5]);
                grupoB_T2R3J1.setStatusTimeFora(statusTime[4]);

                grupoB_T2R3J1.setIdTimeCasa(idTimes[5]);
                grupoB_T2R3J1.setIdTimeFora(idTimes[4]);

                grupoB_T2R3J1.setImagemTimeCasa(iTime[5]);
                grupoB_T2R3J1.setImagemTimeFora(iTime[4]);

                grupoB_T2R3J1.setImagemPlayerCasa(iPlayer[5]);
                grupoB_T2R3J1.setImagemPlayerFora(iPlayer[4]);

                grupoB_T2R3J1.setTimeCasa(listaTimes[5]);
                grupoB_T2R3J1.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosGrupoB").child(grupoB_T2R3J1.getId()).setValue(grupoB_T2R3J1);

                //GrupoA
                //rodada 2
                //jogo 2
                JogosFg grupoB_T2R3J2 = new JogosFg();

                grupoB_T2R3J2.setId("grupoB_T2R3J2");
                grupoB_T2R3J2.setTimeCnPartida("6");
                grupoB_T2R3J2.setTimeFnPartida("6");

                grupoB_T2R2J2.setStatusTimeCasa(statusTime[7]);
                grupoB_T2R2J2.setStatusTimeFora(statusTime[6]);

                grupoB_T2R3J2.setIdTimeCasa(idTimes[7]);
                grupoB_T2R3J2.setIdTimeFora(idTimes[6]);

                grupoB_T2R3J2.setImagemTimeCasa(iTime[7]);
                grupoB_T2R3J2.setImagemTimeFora(iTime[6]);

                grupoB_T2R3J2.setImagemPlayerCasa(iPlayer[7]);
                grupoB_T2R3J2.setImagemPlayerFora(iPlayer[6]);

                grupoB_T2R3J2.setTimeCasa(listaTimes[7]);
                grupoB_T2R3J2.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosGrupoB").child(grupoB_T2R3J2.getId()).setValue(grupoB_T2R3J2);
                //fim Grupo B turno 2


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GerarDadosPc(){
        progressDialog = new ProgressDialog(Configuracoes.this );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Gerando jogos!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);

        progressDialog.show();

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listCompetidor.clear();
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p[i] = objSnapshot.getValue(Competidor.class);
                    q = objSnapshot.getValue(TabelaClassificacaoFg.class);
                    listCompetidor.add(p[i]);

                    i++;

                    progressDialog.dismiss();

                }

                p[0].setOrdem(nAleatorio.get(0));
                p[1].setOrdem(nAleatorio.get(1));
                p[2].setOrdem(nAleatorio.get(2));
                p[3].setOrdem(nAleatorio.get(3));

                p[4].setOrdem(nAleatorio.get(4));
                p[5].setOrdem(nAleatorio.get(5));
                p[6].setOrdem(nAleatorio.get(6));
                p[7].setOrdem(nAleatorio.get(7));

                for( int a = 0; a < 8; a++){

                    if(a < 8){
                        // GRUPO A

                        q.setId(p[a].getOrdem() + p[a].getId());
                        q.setStatus(p[a].getStatus());
                        q.setTime(p[a].getTime());
                        q.setImagem(p[a].getImagem());
                        q.setImgPerfil(p[a].getImgPerfil());
                        q.setPlayer(p[a].getNome());
                        q.setPontos(q.getPontos());
                        q.setVit(q.getVit());
                        q.setEmp(q.getEmp());
                        q.setDer(q.getDer());
                        q.setGp(q.getGp());
                        q.setGc(q.getGc());
                        q.setSg(q.getSg());

                        databaseReference.child("ParticipantesPontosCorridos").child(q.getId()).setValue(q);
                        }
                    }

                GerarJogosPc();



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GerarJogosPc(){
        progressDialog = new ProgressDialog(Configuracoes.this );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Gerando jogos!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);

        progressDialog.show();

        databaseReference.child("ParticipantesPontosCorridos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listCompetidor.clear();
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p[i] = objSnapshot.getValue(Competidor.class);
                    q = objSnapshot.getValue(TabelaClassificacaoFg.class);
                    listCompetidor.add(p[i]);

                    idTimes[i] = q.getId();
                    listaTimes[i] = q.getPlayer();
                    iTime[i] = q.getImagem();
                    iPlayer[i] = q.getImgPerfil();
                    statusTime[i] = q.getStatus();

                    i++;

                    }


                // jogos pontos corridos 1 turno
                // rodada 1
                //*******************************************************************************
                JogosFg pC_T1R1J1 = new JogosFg();

                pC_T1R1J1.setId("pC_T1R1J1");
                pC_T1R1J1.setTimeCnPartida("1");
                pC_T1R1J1.setTimeFnPartida("1");

                pC_T1R1J1.setStatusTimeCasa(statusTime[0]);
                pC_T1R1J1.setStatusTimeFora(statusTime[1]);

                pC_T1R1J1.setIdTimeCasa(idTimes[0]);
                pC_T1R1J1.setIdTimeFora(idTimes[1]);

                pC_T1R1J1.setImagemTimeCasa(iTime[0]);
                pC_T1R1J1.setImagemTimeFora(iTime[1]);

                pC_T1R1J1.setImagemPlayerCasa(iPlayer[0]);
                pC_T1R1J1.setImagemPlayerFora(iPlayer[1]);

                pC_T1R1J1.setTimeCasa(listaTimes[0]);
                pC_T1R1J1.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T1R1J1.getId()).setValue(pC_T1R1J1);


                //*******************************************************************************
                JogosFg pC_T1R1J2 = new JogosFg();

                pC_T1R1J2.setId("pC_T1R1J2");
                pC_T1R1J2.setTimeCnPartida("1");
                pC_T1R1J2.setTimeFnPartida("1");

                pC_T1R1J2.setStatusTimeCasa(statusTime[2]);
                pC_T1R1J2.setStatusTimeFora(statusTime[7]);

                pC_T1R1J2.setIdTimeCasa(idTimes[2]);
                pC_T1R1J2.setIdTimeFora(idTimes[7]);

                pC_T1R1J2.setImagemTimeCasa(iTime[2]);
                pC_T1R1J2.setImagemTimeFora(iTime[7]);

                pC_T1R1J2.setImagemPlayerCasa(iPlayer[2]);
                pC_T1R1J2.setImagemPlayerFora(iPlayer[7]);

                pC_T1R1J2.setTimeCasa(listaTimes[2]);
                pC_T1R1J2.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T1R1J2.getId()).setValue(pC_T1R1J2);

                //*******************************************************************************
                JogosFg pC_T1R1J3 = new JogosFg();

                pC_T1R1J3.setId("pC_T1R1J3");
                pC_T1R1J3.setTimeCnPartida("1");
                pC_T1R1J3.setTimeFnPartida("1");

                pC_T1R1J3.setStatusTimeCasa(statusTime[3]);
                pC_T1R1J3.setStatusTimeFora(statusTime[6]);

                pC_T1R1J3.setIdTimeCasa(idTimes[3]);
                pC_T1R1J3.setIdTimeFora(idTimes[6]);

                pC_T1R1J3.setImagemTimeCasa(iTime[3]);
                pC_T1R1J3.setImagemTimeFora(iTime[6]);

                pC_T1R1J3.setImagemPlayerCasa(iPlayer[3]);
                pC_T1R1J3.setImagemPlayerFora(iPlayer[6]);

                pC_T1R1J3.setTimeCasa(listaTimes[3]);
                pC_T1R1J3.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T1R1J3.getId()).setValue(pC_T1R1J3);

                //*******************************************************************************
                JogosFg pC_T1R1J4 = new JogosFg();

                pC_T1R1J4.setId("pC_T1R1J4");
                pC_T1R1J4.setTimeCnPartida("1");
                pC_T1R1J4.setTimeFnPartida("1");

                pC_T1R1J4.setStatusTimeCasa(statusTime[5]);
                pC_T1R1J4.setStatusTimeFora(statusTime[4]);

                pC_T1R1J4.setIdTimeCasa(idTimes[5]);
                pC_T1R1J4.setIdTimeFora(idTimes[4]);

                pC_T1R1J4.setImagemTimeCasa(iTime[5]);
                pC_T1R1J4.setImagemTimeFora(iTime[4]);

                pC_T1R1J4.setImagemPlayerCasa(iPlayer[5]);
                pC_T1R1J4.setImagemPlayerFora(iPlayer[4]);

                pC_T1R1J4.setTimeCasa(listaTimes[5]);
                pC_T1R1J4.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T1R1J4.getId()).setValue(pC_T1R1J4);


                // rodada 2
                //*******************************************************************************
                JogosFg pC_T1R2J1 = new JogosFg();

                pC_T1R2J1.setId("pC_T1R2J1");
                pC_T1R2J1.setTimeCnPartida("2");
                pC_T1R2J1.setTimeFnPartida("2");

                pC_T1R2J1.setStatusTimeCasa(statusTime[1]);
                pC_T1R2J1.setStatusTimeFora(statusTime[5]);

                pC_T1R2J1.setIdTimeCasa(idTimes[1]);
                pC_T1R2J1.setIdTimeFora(idTimes[5]);

                pC_T1R2J1.setImagemTimeCasa(iTime[1]);
                pC_T1R2J1.setImagemTimeFora(iTime[5]);

                pC_T1R2J1.setImagemPlayerCasa(iPlayer[1]);
                pC_T1R2J1.setImagemPlayerFora(iPlayer[5]);

                pC_T1R2J1.setTimeCasa(listaTimes[1]);
                pC_T1R2J1.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T1R2J1.getId()).setValue(pC_T1R2J1);


                //*******************************************************************************
                JogosFg pC_T1R2J2 = new JogosFg();

                pC_T1R2J2.setId("pC_T1R2J2");
                pC_T1R2J2.setTimeCnPartida("2");
                pC_T1R2J2.setTimeFnPartida("2");

                pC_T1R2J2.setStatusTimeCasa(statusTime[4]);
                pC_T1R2J2.setStatusTimeFora(statusTime[3]);

                pC_T1R2J2.setIdTimeCasa(idTimes[4]);
                pC_T1R2J2.setIdTimeFora(idTimes[3]);

                pC_T1R2J2.setImagemTimeCasa(iTime[4]);
                pC_T1R2J2.setImagemTimeFora(iTime[3]);

                pC_T1R2J2.setImagemPlayerCasa(iPlayer[4]);
                pC_T1R2J2.setImagemPlayerFora(iPlayer[3]);

                pC_T1R2J2.setTimeCasa(listaTimes[4]);
                pC_T1R2J2.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T1R2J2.getId()).setValue(pC_T1R2J2);

                //*******************************************************************************
                JogosFg pC_T1R2J3 = new JogosFg();

                pC_T1R2J3.setId("pC_T1R2J3");
                pC_T1R2J3.setTimeCnPartida("2");
                pC_T1R2J3.setTimeFnPartida("2");

                pC_T1R2J3.setStatusTimeCasa(statusTime[6]);
                pC_T1R2J3.setStatusTimeFora(statusTime[2]);

                pC_T1R2J3.setIdTimeCasa(idTimes[6]);
                pC_T1R2J3.setIdTimeFora(idTimes[2]);

                pC_T1R2J3.setImagemTimeCasa(iTime[6]);
                pC_T1R2J3.setImagemTimeFora(iTime[2]);

                pC_T1R2J3.setImagemPlayerCasa(iPlayer[6]);
                pC_T1R2J3.setImagemPlayerFora(iPlayer[2]);

                pC_T1R2J3.setTimeCasa(listaTimes[6]);
                pC_T1R2J3.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T1R2J3.getId()).setValue(pC_T1R2J3);

                //*******************************************************************************
                JogosFg pC_T1R2J4 = new JogosFg();

                pC_T1R2J4.setId("pC_T1R2J4");
                pC_T1R2J4.setTimeCnPartida("2");
                pC_T1R2J4.setTimeFnPartida("2");

                pC_T1R2J4.setStatusTimeCasa(statusTime[7]);
                pC_T1R2J4.setStatusTimeFora(statusTime[0]);

                pC_T1R2J4.setIdTimeCasa(idTimes[7]);
                pC_T1R2J4.setIdTimeFora(idTimes[0]);

                pC_T1R2J4.setImagemTimeCasa(iTime[7]);
                pC_T1R2J4.setImagemTimeFora(iTime[0]);

                pC_T1R2J4.setImagemPlayerCasa(iPlayer[7]);
                pC_T1R2J4.setImagemPlayerFora(iPlayer[0]);

                pC_T1R2J4.setTimeCasa(listaTimes[7]);
                pC_T1R2J4.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T1R2J4.getId()).setValue(pC_T1R2J4);

                // rodada 3
                //*******************************************************************************
                JogosFg pC_T1R3J1 = new JogosFg();

                pC_T1R3J1.setId("pC_T1R3J1");
                pC_T1R3J1.setTimeCnPartida("3");
                pC_T1R3J1.setTimeFnPartida("3");

                pC_T1R3J1.setStatusTimeCasa(statusTime[2]);
                pC_T1R3J1.setStatusTimeFora(statusTime[4]);

                pC_T1R3J1.setIdTimeCasa(idTimes[2]);
                pC_T1R3J1.setIdTimeFora(idTimes[4]);

                pC_T1R3J1.setImagemTimeCasa(iTime[2]);
                pC_T1R3J1.setImagemTimeFora(iTime[4]);

                pC_T1R3J1.setImagemPlayerCasa(iPlayer[2]);
                pC_T1R3J1.setImagemPlayerFora(iPlayer[4]);

                pC_T1R3J1.setTimeCasa(listaTimes[2]);
                pC_T1R3J1.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T1R3J1.getId()).setValue(pC_T1R3J1);


                //*******************************************************************************
                JogosFg pC_T1R3J2 = new JogosFg();

                pC_T1R3J2.setId("pC_T1R3J2");
                pC_T1R3J2.setTimeCnPartida("3");
                pC_T1R3J2.setTimeFnPartida("3");

                pC_T1R3J2.setStatusTimeCasa(statusTime[3]);
                pC_T1R3J2.setStatusTimeFora(statusTime[1]);

                pC_T1R3J2.setIdTimeCasa(idTimes[3]);
                pC_T1R3J2.setIdTimeFora(idTimes[1]);

                pC_T1R3J2.setImagemTimeCasa(iTime[3]);
                pC_T1R3J2.setImagemTimeFora(iTime[1]);

                pC_T1R3J2.setImagemPlayerCasa(iPlayer[3]);
                pC_T1R3J2.setImagemPlayerFora(iPlayer[1]);

                pC_T1R3J2.setTimeCasa(listaTimes[3]);
                pC_T1R3J2.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T1R3J2.getId()).setValue(pC_T1R3J2);

                //*******************************************************************************
                JogosFg pC_T1R3J3 = new JogosFg();

                pC_T1R3J3.setId("pC_T1R3J3");
                pC_T1R3J3.setTimeCnPartida("3");
                pC_T1R3J3.setTimeFnPartida("3");

                pC_T1R3J3.setStatusTimeCasa(statusTime[5]);
                pC_T1R3J3.setStatusTimeFora(statusTime[0]);

                pC_T1R3J3.setIdTimeCasa(idTimes[5]);
                pC_T1R3J3.setIdTimeFora(idTimes[0]);

                pC_T1R3J3.setImagemTimeCasa(iTime[5]);
                pC_T1R3J3.setImagemTimeFora(iTime[0]);

                pC_T1R3J3.setImagemPlayerCasa(iPlayer[5]);
                pC_T1R3J3.setImagemPlayerFora(iPlayer[0]);

                pC_T1R3J3.setTimeCasa(listaTimes[5]);
                pC_T1R3J3.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T1R3J3.getId()).setValue(pC_T1R3J3);

                //*******************************************************************************
                JogosFg pC_T1R3J4 = new JogosFg();

                pC_T1R3J4.setId("pC_T1R3J4");
                pC_T1R3J4.setTimeCnPartida("3");
                pC_T1R3J4.setTimeFnPartida("3");

                pC_T1R3J4.setStatusTimeCasa(statusTime[6]);
                pC_T1R3J4.setStatusTimeFora(statusTime[7]);

                pC_T1R3J4.setIdTimeCasa(idTimes[6]);
                pC_T1R3J4.setIdTimeFora(idTimes[7]);

                pC_T1R3J4.setImagemTimeCasa(iTime[6]);
                pC_T1R3J4.setImagemTimeFora(iTime[7]);

                pC_T1R3J4.setImagemPlayerCasa(iPlayer[6]);
                pC_T1R3J4.setImagemPlayerFora(iPlayer[7]);

                pC_T1R3J4.setTimeCasa(listaTimes[6]);
                pC_T1R3J4.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T1R3J4.getId()).setValue(pC_T1R3J4);


                // rodada 4
                //*******************************************************************************
                JogosFg pC_T1R4J1 = new JogosFg();

                pC_T1R4J1.setId("pC_T1R4J1");
                pC_T1R4J1.setTimeCnPartida("4");
                pC_T1R4J1.setTimeFnPartida("4");

                pC_T1R4J1.setStatusTimeCasa(statusTime[0]);
                pC_T1R4J1.setStatusTimeFora(statusTime[3]);

                pC_T1R4J1.setIdTimeCasa(idTimes[0]);
                pC_T1R4J1.setIdTimeFora(idTimes[3]);

                pC_T1R4J1.setImagemTimeCasa(iTime[0]);
                pC_T1R4J1.setImagemTimeFora(iTime[3]);

                pC_T1R4J1.setImagemPlayerCasa(iPlayer[0]);
                pC_T1R4J1.setImagemPlayerFora(iPlayer[3]);

                pC_T1R4J1.setTimeCasa(listaTimes[0]);
                pC_T1R4J1.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T1R4J1.getId()).setValue(pC_T1R4J1);


                //*******************************************************************************
                JogosFg pC_T1R4J2 = new JogosFg();

                pC_T1R4J2.setId("pC_T1R4J2");
                pC_T1R4J2.setTimeCnPartida("4");
                pC_T1R4J2.setTimeFnPartida("4");

                pC_T1R4J2.setStatusTimeCasa(statusTime[1]);
                pC_T1R4J2.setStatusTimeFora(statusTime[2]);

                pC_T1R4J2.setIdTimeCasa(idTimes[1]);
                pC_T1R4J2.setIdTimeFora(idTimes[2]);

                pC_T1R4J2.setImagemTimeCasa(iTime[1]);
                pC_T1R4J2.setImagemTimeFora(iTime[2]);

                pC_T1R4J2.setImagemPlayerCasa(iPlayer[1]);
                pC_T1R4J2.setImagemPlayerFora(iPlayer[2]);

                pC_T1R4J2.setTimeCasa(listaTimes[1]);
                pC_T1R4J2.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T1R4J2.getId()).setValue(pC_T1R4J2);

                //*******************************************************************************
                JogosFg pC_T1R4J3 = new JogosFg();

                pC_T1R4J3.setId("pC_T1R4J3");
                pC_T1R4J3.setTimeCnPartida("4");
                pC_T1R4J3.setTimeFnPartida("4");

                pC_T1R4J3.setStatusTimeCasa(statusTime[4]);
                pC_T1R4J3.setStatusTimeFora(statusTime[6]);

                pC_T1R4J3.setIdTimeCasa(idTimes[4]);
                pC_T1R4J3.setIdTimeFora(idTimes[6]);

                pC_T1R4J3.setImagemTimeCasa(iTime[4]);
                pC_T1R4J3.setImagemTimeFora(iTime[6]);

                pC_T1R4J3.setImagemPlayerCasa(iPlayer[4]);
                pC_T1R4J3.setImagemPlayerFora(iPlayer[6]);

                pC_T1R4J3.setTimeCasa(listaTimes[4]);
                pC_T1R4J3.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T1R4J3.getId()).setValue(pC_T1R4J3);

                //*******************************************************************************
                JogosFg pC_T1R4J4 = new JogosFg();

                pC_T1R4J4.setId("pC_T1R4J4");
                pC_T1R4J4.setTimeCnPartida("4");
                pC_T1R4J4.setTimeFnPartida("4");

                pC_T1R4J4.setStatusTimeCasa(statusTime[7]);
                pC_T1R4J4.setStatusTimeFora(statusTime[5]);

                pC_T1R4J4.setIdTimeCasa(idTimes[7]);
                pC_T1R4J4.setIdTimeFora(idTimes[5]);

                pC_T1R4J4.setImagemTimeCasa(iTime[7]);
                pC_T1R4J4.setImagemTimeFora(iTime[5]);

                pC_T1R4J4.setImagemPlayerCasa(iPlayer[7]);
                pC_T1R4J4.setImagemPlayerFora(iPlayer[5]);

                pC_T1R4J4.setTimeCasa(listaTimes[7]);
                pC_T1R4J4.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T1R4J4.getId()).setValue(pC_T1R4J4);


                // rodada 5
                //*******************************************************************************
                JogosFg pC_T1R5J1 = new JogosFg();

                pC_T1R5J1.setId("pC_T1R5J1");
                pC_T1R5J1.setTimeCnPartida("5");
                pC_T1R5J1.setTimeFnPartida("5");

                pC_T1R5J1.setStatusTimeCasa(statusTime[2]);
                pC_T1R5J1.setStatusTimeFora(statusTime[0]);

                pC_T1R5J1.setIdTimeCasa(idTimes[2]);
                pC_T1R5J1.setIdTimeFora(idTimes[0]);

                pC_T1R5J1.setImagemTimeCasa(iTime[2]);
                pC_T1R5J1.setImagemTimeFora(iTime[0]);

                pC_T1R5J1.setImagemPlayerCasa(iPlayer[2]);
                pC_T1R5J1.setImagemPlayerFora(iPlayer[0]);

                pC_T1R5J1.setTimeCasa(listaTimes[2]);
                pC_T1R5J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T1R5J1.getId()).setValue(pC_T1R5J1);


                //*******************************************************************************
                JogosFg pC_T1R5J2 = new JogosFg();

                pC_T1R5J2.setId("pC_T1R5J2");
                pC_T1R5J2.setTimeCnPartida("5");
                pC_T1R5J2.setTimeFnPartida("5");

                pC_T1R5J2.setStatusTimeCasa(statusTime[3]);
                pC_T1R5J2.setStatusTimeFora(statusTime[5]);

                pC_T1R5J2.setIdTimeCasa(idTimes[3]);
                pC_T1R5J2.setIdTimeFora(idTimes[5]);

                pC_T1R5J2.setImagemTimeCasa(iTime[3]);
                pC_T1R5J2.setImagemTimeFora(iTime[5]);

                pC_T1R5J2.setImagemPlayerCasa(iPlayer[3]);
                pC_T1R5J2.setImagemPlayerFora(iPlayer[5]);

                pC_T1R5J2.setTimeCasa(listaTimes[3]);
                pC_T1R5J2.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T1R5J2.getId()).setValue(pC_T1R5J2);

                //*******************************************************************************
                JogosFg pC_T1R5J3 = new JogosFg();

                pC_T1R5J3.setId("pC_T1R5J3");
                pC_T1R5J3.setTimeCnPartida("5");
                pC_T1R5J3.setTimeFnPartida("5");

                pC_T1R5J3.setStatusTimeCasa(statusTime[4]);
                pC_T1R5J3.setStatusTimeFora(statusTime[7]);

                pC_T1R5J3.setIdTimeCasa(idTimes[4]);
                pC_T1R5J3.setIdTimeFora(idTimes[7]);

                pC_T1R5J3.setImagemTimeCasa(iTime[4]);
                pC_T1R5J3.setImagemTimeFora(iTime[7]);

                pC_T1R5J3.setImagemPlayerCasa(iPlayer[4]);
                pC_T1R5J3.setImagemPlayerFora(iPlayer[7]);

                pC_T1R5J3.setTimeCasa(listaTimes[4]);
                pC_T1R5J3.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T1R5J3.getId()).setValue(pC_T1R5J3);

                //*******************************************************************************
                JogosFg pC_T1R5J4 = new JogosFg();

                pC_T1R5J4.setId("pC_T1R5J4");
                pC_T1R5J4.setTimeCnPartida("5");
                pC_T1R5J4.setTimeFnPartida("5");

                pC_T1R5J4.setStatusTimeCasa(statusTime[6]);
                pC_T1R5J4.setStatusTimeFora(statusTime[1]);

                pC_T1R5J4.setIdTimeCasa(idTimes[6]);
                pC_T1R5J4.setIdTimeFora(idTimes[1]);

                pC_T1R5J4.setImagemTimeCasa(iTime[6]);
                pC_T1R5J4.setImagemTimeFora(iTime[1]);

                pC_T1R5J4.setImagemPlayerCasa(iPlayer[6]);
                pC_T1R5J4.setImagemPlayerFora(iPlayer[1]);

                pC_T1R5J4.setTimeCasa(listaTimes[6]);
                pC_T1R5J4.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T1R5J4.getId()).setValue(pC_T1R5J4);


                // rodada 6
                //*******************************************************************************
                JogosFg pC_T1R6J1 = new JogosFg();

                pC_T1R6J1.setId("pC_T1R6J1");
                pC_T1R6J1.setTimeCnPartida("6");
                pC_T1R6J1.setTimeFnPartida("6");

                pC_T1R6J1.setStatusTimeCasa(statusTime[0]);
                pC_T1R6J1.setStatusTimeFora(statusTime[6]);

                pC_T1R6J1.setIdTimeCasa(idTimes[0]);
                pC_T1R6J1.setIdTimeFora(idTimes[6]);

                pC_T1R6J1.setImagemTimeCasa(iTime[0]);
                pC_T1R6J1.setImagemTimeFora(iTime[6]);

                pC_T1R6J1.setImagemPlayerCasa(iPlayer[0]);
                pC_T1R6J1.setImagemPlayerFora(iPlayer[6]);

                pC_T1R6J1.setTimeCasa(listaTimes[0]);
                pC_T1R6J1.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T1R6J1.getId()).setValue(pC_T1R6J1);


                //*******************************************************************************
                JogosFg pC_T1R6J2 = new JogosFg();

                pC_T1R6J2.setId("pC_T1R6J2");
                pC_T1R6J2.setTimeCnPartida("6");
                pC_T1R6J2.setTimeFnPartida("6");

                pC_T1R6J2.setStatusTimeCasa(statusTime[1]);
                pC_T1R6J2.setStatusTimeFora(statusTime[4]);

                pC_T1R6J2.setIdTimeCasa(idTimes[1]);
                pC_T1R6J2.setIdTimeFora(idTimes[4]);

                pC_T1R6J2.setImagemTimeCasa(iTime[1]);
                pC_T1R6J2.setImagemTimeFora(iTime[4]);

                pC_T1R6J2.setImagemPlayerCasa(iPlayer[1]);
                pC_T1R6J2.setImagemPlayerFora(iPlayer[4]);

                pC_T1R6J2.setTimeCasa(listaTimes[1]);
                pC_T1R6J2.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T1R6J2.getId()).setValue(pC_T1R6J2);

                //*******************************************************************************
                JogosFg pC_T1R6J3 = new JogosFg();

                pC_T1R6J3.setId("pC_T1R6J3");
                pC_T1R6J3.setTimeCnPartida("6");
                pC_T1R6J3.setTimeFnPartida("6");

                pC_T1R6J3.setStatusTimeCasa(statusTime[3]);
                pC_T1R6J3.setStatusTimeFora(statusTime[7]);

                pC_T1R6J3.setIdTimeCasa(idTimes[3]);
                pC_T1R6J3.setIdTimeFora(idTimes[7]);

                pC_T1R6J3.setImagemTimeCasa(iTime[3]);
                pC_T1R6J3.setImagemTimeFora(iTime[7]);

                pC_T1R6J3.setImagemPlayerCasa(iPlayer[3]);
                pC_T1R6J3.setImagemPlayerFora(iPlayer[7]);

                pC_T1R6J3.setTimeCasa(listaTimes[3]);
                pC_T1R6J3.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T1R6J3.getId()).setValue(pC_T1R6J3);

                //*******************************************************************************
                JogosFg pC_T1R6J4 = new JogosFg();

                pC_T1R6J4.setId("pC_T1R6J4");
                pC_T1R6J4.setTimeCnPartida("6");
                pC_T1R6J4.setTimeFnPartida("6");

                pC_T1R6J4.setStatusTimeCasa(statusTime[5]);
                pC_T1R6J4.setStatusTimeFora(statusTime[2]);

                pC_T1R6J4.setIdTimeCasa(idTimes[5]);
                pC_T1R6J4.setIdTimeFora(idTimes[2]);

                pC_T1R6J4.setImagemTimeCasa(iTime[5]);
                pC_T1R6J4.setImagemTimeFora(iTime[2]);

                pC_T1R6J4.setImagemPlayerCasa(iPlayer[5]);
                pC_T1R6J4.setImagemPlayerFora(iPlayer[2]);

                pC_T1R6J4.setTimeCasa(listaTimes[5]);
                pC_T1R6J4.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T1R6J4.getId()).setValue(pC_T1R6J4);


                // rodada 7
                //*******************************************************************************
                JogosFg pC_T1R7J1 = new JogosFg();

                pC_T1R7J1.setId("pC_T1R7J1");
                pC_T1R7J1.setTimeCnPartida("7");
                pC_T1R7J1.setTimeFnPartida("7");

                pC_T1R7J1.setStatusTimeCasa(statusTime[2]);
                pC_T1R7J1.setStatusTimeFora(statusTime[3]);

                pC_T1R7J1.setIdTimeCasa(idTimes[2]);
                pC_T1R7J1.setIdTimeFora(idTimes[3]);

                pC_T1R7J1.setImagemTimeCasa(iTime[2]);
                pC_T1R7J1.setImagemTimeFora(iTime[3]);

                pC_T1R7J1.setImagemPlayerCasa(iPlayer[2]);
                pC_T1R7J1.setImagemPlayerFora(iPlayer[3]);

                pC_T1R7J1.setTimeCasa(listaTimes[2]);
                pC_T1R7J1.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T1R7J1.getId()).setValue(pC_T1R7J1);


                //*******************************************************************************
                JogosFg pC_T1R7J2 = new JogosFg();

                pC_T1R7J2.setId("pC_T1R7J2");
                pC_T1R7J2.setTimeCnPartida("7");
                pC_T1R7J2.setTimeFnPartida("7");

                pC_T1R7J2.setStatusTimeCasa(statusTime[4]);
                pC_T1R7J2.setStatusTimeFora(statusTime[0]);

                pC_T1R7J2.setIdTimeCasa(idTimes[4]);
                pC_T1R7J2.setIdTimeFora(idTimes[0]);

                pC_T1R7J2.setImagemTimeCasa(iTime[4]);
                pC_T1R7J2.setImagemTimeFora(iTime[0]);

                pC_T1R7J2.setImagemPlayerCasa(iPlayer[4]);
                pC_T1R7J2.setImagemPlayerFora(iPlayer[0]);

                pC_T1R7J2.setTimeCasa(listaTimes[4]);
                pC_T1R7J2.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T1R7J2.getId()).setValue(pC_T1R7J2);

                //*******************************************************************************
                JogosFg pC_T1R7J3 = new JogosFg();

                pC_T1R7J3.setId("pC_T1R7J3");
                pC_T1R7J3.setTimeCnPartida("7");
                pC_T1R7J3.setTimeFnPartida("7");

                pC_T1R7J3.setStatusTimeCasa(statusTime[6]);
                pC_T1R7J3.setStatusTimeFora(statusTime[5]);

                pC_T1R7J3.setIdTimeCasa(idTimes[6]);
                pC_T1R7J3.setIdTimeFora(idTimes[5]);

                pC_T1R7J3.setImagemTimeCasa(iTime[6]);
                pC_T1R7J3.setImagemTimeFora(iTime[5]);

                pC_T1R7J3.setImagemPlayerCasa(iPlayer[6]);
                pC_T1R7J3.setImagemPlayerFora(iPlayer[5]);

                pC_T1R7J3.setTimeCasa(listaTimes[6]);
                pC_T1R7J3.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T1R7J3.getId()).setValue(pC_T1R7J3);

                //*******************************************************************************
                JogosFg pC_T1R7J4 = new JogosFg();

                pC_T1R7J4.setId("pC_T1R7J4");
                pC_T1R7J4.setTimeCnPartida("7");
                pC_T1R7J4.setTimeFnPartida("7");

                pC_T1R7J4.setStatusTimeCasa(statusTime[7]);
                pC_T1R7J4.setStatusTimeFora(statusTime[1]);

                pC_T1R7J4.setIdTimeCasa(idTimes[7]);
                pC_T1R7J4.setIdTimeFora(idTimes[1]);

                pC_T1R7J4.setImagemTimeCasa(iTime[7]);
                pC_T1R7J4.setImagemTimeFora(iTime[1]);

                pC_T1R7J4.setImagemPlayerCasa(iPlayer[7]);
                pC_T1R7J4.setImagemPlayerFora(iPlayer[1]);

                pC_T1R7J4.setTimeCasa(listaTimes[7]);
                pC_T1R7J4.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T1R7J4.getId()).setValue(pC_T1R7J4);


                // jogos pontos corridos 2 turno
                // rodada 1
                //*******************************************************************************
                JogosFg pC_T2R1J1 = new JogosFg();

                pC_T2R1J1.setId("pC_T2R1J1");
                pC_T2R1J1.setTimeCnPartida("8");
                pC_T2R1J1.setTimeFnPartida("8");

                pC_T2R1J1.setStatusTimeCasa(statusTime[1]);
                pC_T2R1J1.setStatusTimeFora(statusTime[0]);

                pC_T2R1J1.setIdTimeCasa(idTimes[1]);
                pC_T2R1J1.setIdTimeFora(idTimes[0]);

                pC_T2R1J1.setImagemTimeCasa(iTime[1]);
                pC_T2R1J1.setImagemTimeFora(iTime[0]);

                pC_T2R1J1.setImagemPlayerCasa(iPlayer[1]);
                pC_T2R1J1.setImagemPlayerFora(iPlayer[0]);

                pC_T2R1J1.setTimeCasa(listaTimes[1]);
                pC_T2R1J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T2R1J1.getId()).setValue(pC_T2R1J1);


                //*******************************************************************************
                JogosFg pC_T2R1J2 = new JogosFg();

                pC_T2R1J2.setId("pC_T2R1J2");
                pC_T2R1J2.setTimeCnPartida("8");
                pC_T2R1J2.setTimeFnPartida("8");

                pC_T2R1J2.setStatusTimeCasa(statusTime[7]);
                pC_T2R1J2.setStatusTimeFora(statusTime[2]);

                pC_T2R1J2.setIdTimeCasa(idTimes[7]);
                pC_T2R1J2.setIdTimeFora(idTimes[2]);

                pC_T2R1J2.setImagemTimeCasa(iTime[7]);
                pC_T2R1J2.setImagemTimeFora(iTime[2]);

                pC_T2R1J2.setImagemPlayerCasa(iPlayer[7]);
                pC_T2R1J2.setImagemPlayerFora(iPlayer[2]);

                pC_T2R1J2.setTimeCasa(listaTimes[7]);
                pC_T2R1J2.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T2R1J2.getId()).setValue(pC_T2R1J2);

                //*******************************************************************************
                JogosFg pC_T2R1J3 = new JogosFg();

                pC_T2R1J3.setId("pC_T2R1J3");
                pC_T2R1J3.setTimeCnPartida("8");
                pC_T2R1J3.setTimeFnPartida("8");

                pC_T2R1J3.setStatusTimeCasa(statusTime[6]);
                pC_T2R1J3.setStatusTimeFora(statusTime[3]);

                pC_T2R1J3.setIdTimeCasa(idTimes[6]);
                pC_T2R1J3.setIdTimeFora(idTimes[3]);

                pC_T2R1J3.setImagemTimeCasa(iTime[6]);
                pC_T2R1J3.setImagemTimeFora(iTime[3]);

                pC_T2R1J3.setImagemPlayerCasa(iPlayer[6]);
                pC_T2R1J3.setImagemPlayerFora(iPlayer[3]);

                pC_T2R1J3.setTimeCasa(listaTimes[6]);
                pC_T2R1J3.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T2R1J3.getId()).setValue(pC_T2R1J3);

                //*******************************************************************************
                JogosFg pC_T2R1J4 = new JogosFg();

                pC_T2R1J4.setId("pC_T2R1J4");
                pC_T2R1J4.setTimeCnPartida("8");
                pC_T2R1J4.setTimeFnPartida("8");

                pC_T2R1J4.setStatusTimeCasa(statusTime[4]);
                pC_T2R1J4.setStatusTimeFora(statusTime[5]);

                pC_T2R1J4.setIdTimeCasa(idTimes[4]);
                pC_T2R1J4.setIdTimeFora(idTimes[5]);

                pC_T2R1J4.setImagemTimeCasa(iTime[4]);
                pC_T2R1J4.setImagemTimeFora(iTime[5]);

                pC_T2R1J4.setImagemPlayerCasa(iPlayer[4]);
                pC_T2R1J4.setImagemPlayerFora(iPlayer[5]);

                pC_T2R1J4.setTimeCasa(listaTimes[4]);
                pC_T2R1J4.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T2R1J4.getId()).setValue(pC_T2R1J4);


                // rodada 2
                //*******************************************************************************
                JogosFg pC_T2R2J1 = new JogosFg();

                pC_T2R2J1.setId("pC_T2R2J1");
                pC_T2R2J1.setTimeCnPartida("9");
                pC_T2R2J1.setTimeFnPartida("9");

                pC_T2R2J1.setStatusTimeCasa(statusTime[5]);
                pC_T2R2J1.setStatusTimeFora(statusTime[1]);

                pC_T2R2J1.setIdTimeCasa(idTimes[5]);
                pC_T2R2J1.setIdTimeFora(idTimes[1]);

                pC_T2R2J1.setImagemTimeCasa(iTime[5]);
                pC_T2R2J1.setImagemTimeFora(iTime[1]);

                pC_T2R2J1.setImagemPlayerCasa(iPlayer[5]);
                pC_T2R2J1.setImagemPlayerFora(iPlayer[1]);

                pC_T2R2J1.setTimeCasa(listaTimes[5]);
                pC_T2R2J1.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T2R2J1.getId()).setValue(pC_T2R2J1);


                //*******************************************************************************
                JogosFg pC_T2R2J2 = new JogosFg();

                pC_T2R2J2.setId("pC_T2R2J2");
                pC_T2R2J2.setTimeCnPartida("9");
                pC_T2R2J2.setTimeFnPartida("9");

                pC_T2R2J2.setStatusTimeCasa(statusTime[3]);
                pC_T2R2J2.setStatusTimeFora(statusTime[4]);

                pC_T2R2J2.setIdTimeCasa(idTimes[3]);
                pC_T2R2J2.setIdTimeFora(idTimes[4]);

                pC_T2R2J2.setImagemTimeCasa(iTime[3]);
                pC_T2R2J2.setImagemTimeFora(iTime[4]);

                pC_T2R2J2.setImagemPlayerCasa(iPlayer[3]);
                pC_T2R2J2.setImagemPlayerFora(iPlayer[4]);

                pC_T2R2J2.setTimeCasa(listaTimes[3]);
                pC_T2R2J2.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T2R2J2.getId()).setValue(pC_T2R2J2);

                //*******************************************************************************
                JogosFg pC_T2R2J3 = new JogosFg();

                pC_T2R2J3.setId("pC_T2R2J3");
                pC_T2R2J3.setTimeCnPartida("9");
                pC_T2R2J3.setTimeFnPartida("9");

                pC_T2R2J3.setStatusTimeCasa(statusTime[2]);
                pC_T2R2J3.setStatusTimeFora(statusTime[6]);

                pC_T2R2J3.setIdTimeCasa(idTimes[2]);
                pC_T2R2J3.setIdTimeFora(idTimes[6]);

                pC_T2R2J3.setImagemTimeCasa(iTime[2]);
                pC_T2R2J3.setImagemTimeFora(iTime[6]);

                pC_T2R2J3.setImagemPlayerCasa(iPlayer[2]);
                pC_T2R2J3.setImagemPlayerFora(iPlayer[6]);

                pC_T2R2J3.setTimeCasa(listaTimes[2]);
                pC_T2R2J3.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T2R2J3.getId()).setValue(pC_T2R2J3);

                //*******************************************************************************
                JogosFg pC_T2R2J4 = new JogosFg();

                pC_T2R2J4.setId("pC_T2R2J4");
                pC_T2R2J4.setTimeCnPartida("9");
                pC_T2R2J4.setTimeFnPartida("9");

                pC_T2R2J4.setStatusTimeCasa(statusTime[0]);
                pC_T2R2J4.setStatusTimeFora(statusTime[7]);

                pC_T2R2J4.setIdTimeCasa(idTimes[0]);
                pC_T2R2J4.setIdTimeFora(idTimes[7]);

                pC_T2R2J4.setImagemTimeCasa(iTime[0]);
                pC_T2R2J4.setImagemTimeFora(iTime[7]);

                pC_T2R2J4.setImagemPlayerCasa(iPlayer[0]);
                pC_T2R2J4.setImagemPlayerFora(iPlayer[7]);

                pC_T2R2J4.setTimeCasa(listaTimes[0]);
                pC_T2R2J4.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T2R2J4.getId()).setValue(pC_T2R2J4);

                // rodada 3
                //*******************************************************************************
                JogosFg pC_T2R3J1 = new JogosFg();

                pC_T2R3J1.setId("pC_T2R3J1");
                pC_T2R3J1.setTimeCnPartida("10");
                pC_T2R3J1.setTimeFnPartida("10");

                pC_T2R3J1.setStatusTimeCasa(statusTime[4]);
                pC_T2R3J1.setStatusTimeFora(statusTime[2]);

                pC_T2R3J1.setIdTimeCasa(idTimes[4]);
                pC_T2R3J1.setIdTimeFora(idTimes[2]);

                pC_T2R3J1.setImagemTimeCasa(iTime[4]);
                pC_T2R3J1.setImagemTimeFora(iTime[2]);

                pC_T2R3J1.setImagemPlayerCasa(iPlayer[4]);
                pC_T2R3J1.setImagemPlayerFora(iPlayer[2]);

                pC_T2R3J1.setTimeCasa(listaTimes[4]);
                pC_T2R3J1.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T2R3J1.getId()).setValue(pC_T2R3J1);


                //*******************************************************************************
                JogosFg pC_T2R3J2 = new JogosFg();

                pC_T2R3J2.setId("pC_T2R3J2");
                pC_T2R3J2.setTimeCnPartida("10");
                pC_T2R3J2.setTimeFnPartida("10");

                pC_T2R3J2.setStatusTimeCasa(statusTime[1]);
                pC_T2R3J2.setStatusTimeFora(statusTime[3]);

                pC_T2R3J2.setIdTimeCasa(idTimes[1]);
                pC_T2R3J2.setIdTimeFora(idTimes[3]);

                pC_T2R3J2.setImagemTimeCasa(iTime[1]);
                pC_T2R3J2.setImagemTimeFora(iTime[3]);

                pC_T2R3J2.setImagemPlayerCasa(iPlayer[1]);
                pC_T2R3J2.setImagemPlayerFora(iPlayer[3]);

                pC_T2R3J2.setTimeCasa(listaTimes[1]);
                pC_T2R3J2.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T2R3J2.getId()).setValue(pC_T2R3J2);

                //*******************************************************************************
                JogosFg pC_T2R3J3 = new JogosFg();

                pC_T2R3J3.setId("pC_T2R3J3");
                pC_T2R3J3.setTimeCnPartida("10");
                pC_T2R3J3.setTimeFnPartida("10");

                pC_T2R3J3.setStatusTimeCasa(statusTime[0]);
                pC_T2R3J3.setStatusTimeFora(statusTime[5]);

                pC_T2R3J3.setIdTimeCasa(idTimes[0]);
                pC_T2R3J3.setIdTimeFora(idTimes[5]);

                pC_T2R3J3.setImagemTimeCasa(iTime[0]);
                pC_T2R3J3.setImagemTimeFora(iTime[5]);

                pC_T2R3J3.setImagemPlayerCasa(iPlayer[0]);
                pC_T2R3J3.setImagemPlayerFora(iPlayer[5]);

                pC_T2R3J3.setTimeCasa(listaTimes[0]);
                pC_T2R3J3.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T2R3J3.getId()).setValue(pC_T2R3J3);

                //*******************************************************************************
                JogosFg pC_T2R3J4 = new JogosFg();

                pC_T2R3J4.setId("pC_T2R3J4");
                pC_T2R3J4.setTimeCnPartida("10");
                pC_T2R3J4.setTimeFnPartida("10");

                pC_T2R3J4.setStatusTimeCasa(statusTime[7]);
                pC_T2R3J4.setStatusTimeFora(statusTime[6]);

                pC_T2R3J4.setIdTimeCasa(idTimes[7]);
                pC_T2R3J4.setIdTimeFora(idTimes[6]);

                pC_T2R3J4.setImagemTimeCasa(iTime[7]);
                pC_T2R3J4.setImagemTimeFora(iTime[6]);

                pC_T2R3J4.setImagemPlayerCasa(iPlayer[7]);
                pC_T2R3J4.setImagemPlayerFora(iPlayer[6]);

                pC_T2R3J4.setTimeCasa(listaTimes[7]);
                pC_T2R3J4.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T2R3J4.getId()).setValue(pC_T2R3J4);


                // rodada 4
                //*******************************************************************************
                JogosFg pC_T2R4J1 = new JogosFg();

                pC_T2R4J1.setId("pC_T2R4J1");
                pC_T2R4J1.setTimeCnPartida("11");
                pC_T2R4J1.setTimeFnPartida("11");

                pC_T2R4J1.setStatusTimeCasa(statusTime[3]);
                pC_T2R4J1.setStatusTimeFora(statusTime[0]);

                pC_T2R4J1.setIdTimeCasa(idTimes[3]);
                pC_T2R4J1.setIdTimeFora(idTimes[0]);

                pC_T2R4J1.setImagemTimeCasa(iTime[3]);
                pC_T2R4J1.setImagemTimeFora(iTime[0]);

                pC_T2R4J1.setImagemPlayerCasa(iPlayer[3]);
                pC_T2R4J1.setImagemPlayerFora(iPlayer[0]);

                pC_T2R4J1.setTimeCasa(listaTimes[3]);
                pC_T2R4J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T2R4J1.getId()).setValue(pC_T2R4J1);


                //*******************************************************************************
                JogosFg pC_T2R4J2 = new JogosFg();

                pC_T2R4J2.setId("pC_T2R4J2");
                pC_T2R4J2.setTimeCnPartida("11");
                pC_T2R4J2.setTimeFnPartida("11");

                pC_T2R4J2.setStatusTimeCasa(statusTime[2]);
                pC_T2R4J2.setStatusTimeFora(statusTime[1]);

                pC_T2R4J2.setIdTimeCasa(idTimes[2]);
                pC_T2R4J2.setIdTimeFora(idTimes[1]);

                pC_T2R4J2.setImagemTimeCasa(iTime[2]);
                pC_T2R4J2.setImagemTimeFora(iTime[1]);

                pC_T2R4J2.setImagemPlayerCasa(iPlayer[2]);
                pC_T2R4J2.setImagemPlayerFora(iPlayer[1]);

                pC_T2R4J2.setTimeCasa(listaTimes[2]);
                pC_T2R4J2.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T2R4J2.getId()).setValue(pC_T2R4J2);

                //*******************************************************************************
                JogosFg pC_T2R4J3 = new JogosFg();

                pC_T2R4J3.setId("pC_T2R4J3");
                pC_T2R4J3.setTimeCnPartida("11");
                pC_T2R4J3.setTimeFnPartida("11");

                pC_T2R4J3.setStatusTimeCasa(statusTime[6]);
                pC_T2R4J3.setStatusTimeFora(statusTime[4]);

                pC_T2R4J3.setIdTimeCasa(idTimes[6]);
                pC_T1R4J3.setIdTimeFora(idTimes[4]);

                pC_T2R4J3.setImagemTimeCasa(iTime[6]);
                pC_T2R4J3.setImagemTimeFora(iTime[4]);

                pC_T2R4J3.setImagemPlayerCasa(iPlayer[6]);
                pC_T2R4J3.setImagemPlayerFora(iPlayer[4]);

                pC_T2R4J3.setTimeCasa(listaTimes[6]);
                pC_T2R4J3.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T2R4J3.getId()).setValue(pC_T2R4J3);

                //*******************************************************************************
                JogosFg pC_T2R4J4 = new JogosFg();

                pC_T2R4J4.setId("pC_T2R4J4");
                pC_T2R4J4.setTimeCnPartida("11");
                pC_T2R4J4.setTimeFnPartida("11");

                pC_T2R4J4.setStatusTimeCasa(statusTime[5]);
                pC_T2R4J4.setStatusTimeFora(statusTime[7]);

                pC_T2R4J4.setIdTimeCasa(idTimes[5]);
                pC_T2R4J4.setIdTimeFora(idTimes[7]);

                pC_T2R4J4.setImagemTimeCasa(iTime[5]);
                pC_T2R4J4.setImagemTimeFora(iTime[7]);

                pC_T2R4J4.setImagemPlayerCasa(iPlayer[5]);
                pC_T2R4J4.setImagemPlayerFora(iPlayer[7]);

                pC_T2R4J4.setTimeCasa(listaTimes[5]);
                pC_T2R4J4.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T2R4J4.getId()).setValue(pC_T2R4J4);


                // rodada 5
                //*******************************************************************************
                JogosFg pC_T2R5J1 = new JogosFg();

                pC_T2R5J1.setId("pC_T2R5J1");
                pC_T2R5J1.setTimeCnPartida("12");
                pC_T2R5J1.setTimeFnPartida("12");

                pC_T2R5J1.setStatusTimeCasa(statusTime[0]);
                pC_T2R5J1.setStatusTimeFora(statusTime[2]);

                pC_T2R5J1.setIdTimeCasa(idTimes[0]);
                pC_T2R5J1.setIdTimeFora(idTimes[2]);

                pC_T2R5J1.setImagemTimeCasa(iTime[0]);
                pC_T2R5J1.setImagemTimeFora(iTime[2]);

                pC_T2R5J1.setImagemPlayerCasa(iPlayer[0]);
                pC_T2R5J1.setImagemPlayerFora(iPlayer[2]);

                pC_T2R5J1.setTimeCasa(listaTimes[0]);
                pC_T2R5J1.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T2R5J1.getId()).setValue(pC_T2R5J1);


                //*******************************************************************************
                JogosFg pC_T2R5J2 = new JogosFg();

                pC_T2R5J2.setId("pC_T2R5J2");
                pC_T2R5J2.setTimeCnPartida("12");
                pC_T2R5J2.setTimeFnPartida("12");

                pC_T2R5J2.setStatusTimeCasa(statusTime[5]);
                pC_T2R5J2.setStatusTimeFora(statusTime[3]);

                pC_T2R5J2.setIdTimeCasa(idTimes[5]);
                pC_T2R5J2.setIdTimeFora(idTimes[3]);

                pC_T2R5J2.setImagemTimeCasa(iTime[5]);
                pC_T2R5J2.setImagemTimeFora(iTime[3]);

                pC_T2R5J2.setImagemPlayerCasa(iPlayer[5]);
                pC_T2R5J2.setImagemPlayerFora(iPlayer[3]);

                pC_T2R5J2.setTimeCasa(listaTimes[5]);
                pC_T2R5J2.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T2R5J2.getId()).setValue(pC_T2R5J2);

                //*******************************************************************************
                JogosFg pC_T2R5J3 = new JogosFg();

                pC_T2R5J3.setId("pC_T2R5J3");
                pC_T2R5J3.setTimeCnPartida("12");
                pC_T2R5J3.setTimeFnPartida("12");

                pC_T2R5J3.setStatusTimeCasa(statusTime[7]);
                pC_T2R5J3.setStatusTimeFora(statusTime[4]);

                pC_T2R5J3.setIdTimeCasa(idTimes[7]);
                pC_T2R5J3.setIdTimeFora(idTimes[4]);

                pC_T2R5J3.setImagemTimeCasa(iTime[7]);
                pC_T2R5J3.setImagemTimeFora(iTime[4]);

                pC_T2R5J3.setImagemPlayerCasa(iPlayer[7]);
                pC_T2R5J3.setImagemPlayerFora(iPlayer[4]);

                pC_T2R5J3.setTimeCasa(listaTimes[7]);
                pC_T2R5J3.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T2R5J3.getId()).setValue(pC_T2R5J3);

                //*******************************************************************************
                JogosFg pC_T2R5J4 = new JogosFg();

                pC_T2R5J4.setId("pC_T2R5J4");
                pC_T2R5J4.setTimeCnPartida("12");
                pC_T2R5J4.setTimeFnPartida("12");

                pC_T2R5J4.setStatusTimeCasa(statusTime[1]);
                pC_T2R5J4.setStatusTimeFora(statusTime[6]);

                pC_T2R5J4.setIdTimeCasa(idTimes[1]);
                pC_T2R5J4.setIdTimeFora(idTimes[6]);

                pC_T2R5J4.setImagemTimeCasa(iTime[1]);
                pC_T2R5J4.setImagemTimeFora(iTime[6]);

                pC_T2R5J4.setImagemPlayerCasa(iPlayer[1]);
                pC_T2R5J4.setImagemPlayerFora(iPlayer[6]);

                pC_T2R5J4.setTimeCasa(listaTimes[1]);
                pC_T2R5J4.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T2R5J4.getId()).setValue(pC_T2R5J4);


                // rodada 6
                //*******************************************************************************
                JogosFg pC_T2R6J1 = new JogosFg();

                pC_T2R6J1.setId("pC_T2R6J1");
                pC_T2R6J1.setTimeCnPartida("13");
                pC_T2R6J1.setTimeFnPartida("13");

                pC_T2R6J1.setStatusTimeCasa(statusTime[6]);
                pC_T2R6J1.setStatusTimeFora(statusTime[0]);

                pC_T2R6J1.setIdTimeCasa(idTimes[6]);
                pC_T2R6J1.setIdTimeFora(idTimes[0]);

                pC_T2R6J1.setImagemTimeCasa(iTime[6]);
                pC_T2R6J1.setImagemTimeFora(iTime[0]);

                pC_T2R6J1.setImagemPlayerCasa(iPlayer[6]);
                pC_T2R6J1.setImagemPlayerFora(iPlayer[0]);

                pC_T2R6J1.setTimeCasa(listaTimes[6]);
                pC_T2R6J1.setTimeFora(listaTimes[0]);

                databaseReference.child("JogosPontosC").child(pC_T2R6J1.getId()).setValue(pC_T2R6J1);


                //*******************************************************************************
                JogosFg pC_T2R6J2 = new JogosFg();

                pC_T2R6J2.setId("pC_T2R6J2");
                pC_T2R6J2.setTimeCnPartida("13");
                pC_T2R6J2.setTimeFnPartida("13");

                pC_T2R6J2.setStatusTimeCasa(statusTime[4]);
                pC_T2R6J2.setStatusTimeFora(statusTime[1]);

                pC_T2R6J2.setIdTimeCasa(idTimes[4]);
                pC_T2R6J2.setIdTimeFora(idTimes[1]);

                pC_T2R6J2.setImagemTimeCasa(iTime[4]);
                pC_T2R6J2.setImagemTimeFora(iTime[1]);

                pC_T2R6J2.setImagemPlayerCasa(iPlayer[4]);
                pC_T2R6J2.setImagemPlayerFora(iPlayer[1]);

                pC_T2R6J2.setTimeCasa(listaTimes[4]);
                pC_T2R6J2.setTimeFora(listaTimes[1]);

                databaseReference.child("JogosPontosC").child(pC_T2R6J2.getId()).setValue(pC_T2R6J2);

                //*******************************************************************************
                JogosFg pC_T2R6J3 = new JogosFg();

                pC_T2R6J3.setId("pC_T2R6J3");
                pC_T2R6J3.setTimeCnPartida("13");
                pC_T2R6J3.setTimeFnPartida("13");

                pC_T2R6J3.setStatusTimeCasa(statusTime[7]);
                pC_T2R6J3.setStatusTimeFora(statusTime[3]);

                pC_T2R6J3.setIdTimeCasa(idTimes[7]);
                pC_T2R6J3.setIdTimeFora(idTimes[3]);

                pC_T2R6J3.setImagemTimeCasa(iTime[7]);
                pC_T2R6J3.setImagemTimeFora(iTime[3]);

                pC_T2R6J3.setImagemPlayerCasa(iPlayer[7]);
                pC_T2R6J3.setImagemPlayerFora(iPlayer[3]);

                pC_T2R6J3.setTimeCasa(listaTimes[7]);
                pC_T2R6J3.setTimeFora(listaTimes[3]);

                databaseReference.child("JogosPontosC").child(pC_T2R6J3.getId()).setValue(pC_T2R6J3);

                //*******************************************************************************
                JogosFg pC_T2R6J4 = new JogosFg();

                pC_T2R6J4.setId("pC_T2R6J4");
                pC_T2R6J4.setTimeCnPartida("13");
                pC_T2R6J4.setTimeFnPartida("13");

                pC_T2R6J4.setStatusTimeCasa(statusTime[2]);
                pC_T2R6J4.setStatusTimeFora(statusTime[5]);

                pC_T2R6J4.setIdTimeCasa(idTimes[2]);
                pC_T2R6J4.setIdTimeFora(idTimes[5]);

                pC_T2R6J4.setImagemTimeCasa(iTime[2]);
                pC_T2R6J4.setImagemTimeFora(iTime[5]);
                pC_T2R6J4.setImagemPlayerCasa(iPlayer[2]);
                pC_T2R6J4.setImagemPlayerFora(iPlayer[5]);

                pC_T2R6J4.setTimeCasa(listaTimes[2]);
                pC_T2R6J4.setTimeFora(listaTimes[5]);

                databaseReference.child("JogosPontosC").child(pC_T2R6J4.getId()).setValue(pC_T2R6J4);


                // rodada 7
                //*******************************************************************************
                JogosFg pC_T2R7J1 = new JogosFg();

                pC_T2R7J1.setId("pC_T2R7J1");
                pC_T2R7J1.setTimeCnPartida("14");
                pC_T2R7J1.setTimeFnPartida("14");

                pC_T2R7J1.setStatusTimeCasa(statusTime[3]);
                pC_T2R7J1.setStatusTimeFora(statusTime[2]);

                pC_T2R7J1.setIdTimeCasa(idTimes[3]);
                pC_T2R7J1.setIdTimeFora(idTimes[2]);

                pC_T2R7J1.setImagemTimeCasa(iTime[3]);
                pC_T2R7J1.setImagemTimeFora(iTime[2]);

                pC_T2R7J1.setImagemPlayerCasa(iPlayer[3]);
                pC_T2R7J1.setImagemPlayerFora(iPlayer[2]);

                pC_T2R7J1.setTimeCasa(listaTimes[3]);
                pC_T2R7J1.setTimeFora(listaTimes[2]);

                databaseReference.child("JogosPontosC").child(pC_T2R7J1.getId()).setValue(pC_T2R7J1);


                //*******************************************************************************
                JogosFg pC_T2R7J2 = new JogosFg();

                pC_T2R7J2.setId("pC_T2R7J2");
                pC_T2R7J2.setTimeCnPartida("14");
                pC_T2R7J2.setTimeFnPartida("14");

                pC_T2R7J2.setStatusTimeCasa(statusTime[0]);
                pC_T2R7J2.setStatusTimeFora(statusTime[4]);

                pC_T2R7J2.setIdTimeCasa(idTimes[0]);
                pC_T2R7J2.setIdTimeFora(idTimes[4]);

                pC_T2R7J2.setImagemTimeCasa(iTime[0]);
                pC_T2R7J2.setImagemTimeFora(iTime[4]);

                pC_T2R7J2.setImagemPlayerCasa(iPlayer[0]);
                pC_T2R7J2.setImagemPlayerFora(iPlayer[4]);

                pC_T2R7J2.setTimeCasa(listaTimes[0]);
                pC_T2R7J2.setTimeFora(listaTimes[4]);

                databaseReference.child("JogosPontosC").child(pC_T2R7J2.getId()).setValue(pC_T2R7J2);

                //*******************************************************************************
                JogosFg pC_T2R7J3 = new JogosFg();

                pC_T2R7J3.setId("pC_T2R7J3");
                pC_T2R7J3.setTimeCnPartida("14");
                pC_T2R7J3.setTimeFnPartida("14");

                pC_T2R7J3.setStatusTimeCasa(statusTime[5]);
                pC_T2R7J3.setStatusTimeFora(statusTime[6]);

                pC_T2R7J3.setIdTimeCasa(idTimes[5]);
                pC_T2R7J3.setIdTimeFora(idTimes[6]);

                pC_T2R7J3.setImagemTimeCasa(iTime[5]);
                pC_T2R7J3.setImagemTimeFora(iTime[6]);

                pC_T2R7J3.setImagemPlayerCasa(iPlayer[5]);
                pC_T2R7J3.setImagemPlayerFora(iPlayer[6]);

                pC_T2R7J3.setTimeCasa(listaTimes[5]);
                pC_T2R7J3.setTimeFora(listaTimes[6]);

                databaseReference.child("JogosPontosC").child(pC_T2R7J3.getId()).setValue(pC_T2R7J3);

                //*******************************************************************************
                JogosFg pC_T2R7J4 = new JogosFg();

                pC_T2R7J4.setId("pC_T2R7J4");
                pC_T2R7J4.setTimeCnPartida("14");
                pC_T2R7J4.setTimeFnPartida("14");

                pC_T2R7J4.setStatusTimeCasa(statusTime[1]);
                pC_T2R7J4.setStatusTimeFora(statusTime[7]);

                pC_T2R7J4.setIdTimeCasa(idTimes[1]);
                pC_T2R7J4.setIdTimeFora(idTimes[7]);

                pC_T2R7J4.setImagemTimeCasa(iTime[1]);
                pC_T2R7J4.setImagemTimeFora(iTime[7]);

                pC_T2R7J4.setImagemPlayerCasa(iPlayer[1]);
                pC_T2R7J4.setImagemPlayerFora(iPlayer[7]);

                pC_T2R7J4.setTimeCasa(listaTimes[1]);
                pC_T2R7J4.setTimeFora(listaTimes[7]);

                databaseReference.child("JogosPontosC").child(pC_T2R7J4.getId()).setValue(pC_T2R7J4);




                //semifinal 01*******************************************************************
                JogosFg semiFinal01 = new JogosFg();

                semiFinal01.setTituloPartida("SEMIFINAIS");
                semiFinal01.setId("aaaSemiFinal_01");
                semiFinal01.setTimeCnPartida("1");
                semiFinal01.setTimeFnPartida("1");

                semiFinal01.setTimeCasa(sf01_Time01);
                semiFinal01.setTimeFora(sf01_Time02);

                databaseReference.child("Finais").child(semiFinal01.getId()).setValue(semiFinal01);

                //semifinal 02
                JogosFg semiFinal02 = new JogosFg();

                semiFinal02.setTituloPartida("");
                semiFinal02.setId("bbbSemiFinal_02");
                semiFinal02.setTimeCnPartida("1");
                semiFinal02.setTimeFnPartida("1");

                semiFinal02.setTimeCasa(sf02_Time01);
                semiFinal02.setTimeFora(sf02_Time02);

                databaseReference.child("Finais").child(semiFinal02.getId()).setValue(semiFinal02);

                //disputa 3
                JogosFg disp03 = new JogosFg();

                disp03.setTituloPartida("3ª LUGAR");
                disp03.setId("cccDisputa_03");
                disp03.setTimeCnPartida("2");
                disp03.setTimeFnPartida("2");

                databaseReference.child("Finais").child(disp03.getId()).setValue(disp03);

                //final
                JogosFg finalJ = new JogosFg();

                finalJ.setTituloPartida("FINAL");
                finalJ.setId("dddFinal");
                finalJ.setTimeCnPartida("2");
                finalJ.setTimeFnPartida("2");

                databaseReference.child("Finais").child(finalJ.getId()).setValue(finalJ);

                progressDialog.dismiss();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertaEmbCompetidor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( "selecione o formato do torneio?" );
        builder.setPositiveButton("Pontos corridos",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                //embaralharCompetidores();

                databaseReference.child("JogosGrupoA").removeValue();
                databaseReference.child("JogosGrupoB").removeValue();
                databaseReference.child("JogosPontosC").removeValue();
                databaseReference.child("ParticipantesPontosCorridos").removeValue();
                databaseReference.child("ParticipantesGrupoA").removeValue();
                databaseReference.child("ParticipantesGrupoB").removeValue();
                databaseReference.child("Artilharia").removeValue();
                databaseReference.child("Cartoes").removeValue();
                alertaIniciarEgerarJogosTorneio();


                databaseReference.child("Configuracoes").child("fTorneio").child("formatoTorneio").setValue("pontosCorridos");

            }
        });
        builder.setNegativeButton("Grupos",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                //embaralharCompetidores();

                databaseReference.child("JogosGrupoA").removeValue();
                databaseReference.child("JogosGrupoB").removeValue();
                databaseReference.child("JogosPontosC").removeValue();
                databaseReference.child("ParticipantesPontosCorridos").removeValue();
                databaseReference.child("ParticipantesGrupoA").removeValue();
                databaseReference.child("ParticipantesGrupoB").removeValue();
                databaseReference.child("Artilharia").removeValue();
                databaseReference.child("Cartoes").removeValue();
                alertaIniciarEgerarJogosTorneio();

                //ação do botão "sim"
               // Toast.makeText( Configuracoes.this, "Aguarde...", Toast.LENGTH_SHORT).show();

                databaseReference.child("Configuracoes").child("fTorneio").child("formatoTorneio").setValue("grupos");
            }
        });
        builder.show();
    }

    private void alertaIniciarEgerarJogosTorneio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Quase pronto!");
        builder.setMessage( "pressione continuar para gerar a tabela de jogos!" );
        builder.setPositiveButton("CONTINUAR",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                if(formatoTorneio.equals("grupos")){

                    listarDadosEgerarParticipantesGaGb();
                    gerarJogosGrupoA();
                    gerarJogosGrupoB();
                    Toast.makeText( Configuracoes.this, "Configurações finalizadas, novo torneio em andamento!", Toast.LENGTH_SHORT).show();
                    databaseReference.child("Configuracoes").child("fTorneio").child("statusTorneio").setValue("andamento");

                    Intent intent = new Intent(Configuracoes.this, JogosFgActivity.class);
                    intent.putExtra("idLogado", uLogadoId );
                    startActivity(intent);

                } else if(formatoTorneio.equals("pontosCorridos")){

                    GerarDadosPc();
                    databaseReference.child("Configuracoes").child("fTorneio").child("statusTorneio").setValue("andamento");
                    Toast.makeText( Configuracoes.this, "Configurações finalizadas, novo torneio em andamento!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Configuracoes.this, JogosFgActivity.class);
                    intent.putExtra("idLogado", uLogadoId );
                    startActivity(intent);
                }


            }
        });

        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                //ação do botão "não"
                databaseReference.child("JogosGrupoA").removeValue();
                databaseReference.child("JogosGrupoB").removeValue();
                databaseReference.child("PartPontosCorridos").removeValue();
                databaseReference.child("ParticipantesGrupoA").removeValue();
                databaseReference.child("ParticipantesGrupoB").removeValue();

            }
        });

        builder.show();
    }

    private void getConfigServidor(){
        databaseReference.child("Configuracoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    config = objSnapshot.getValue(Config.class);

                    formatoTorneio = config.getFormatoTorneio();
                    statusTorneio = config.getStatusTorneio();
                }

                if( statusTorneio.equals("andamento") ){
                    finalizarTorneio.setText("STATUS DO TORNEIO: (EM ANDAMENTO)");
                    finalizarTorneio.setBackgroundColor(0xFF2F4919);

                } else {
                    finalizarTorneio.setText("STATUS DO TORNEIO: (FINALIZADO)");
                    finalizarTorneio.setBackgroundColor(0xFFAC0B0B);
                }


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

    public boolean permissao (){
        List<String> permissoesRequeridas = new ArrayList<>();

        for(String permissao : permissoes){
            if(ContextCompat.checkSelfPermission(this, permissao) != PackageManager.PERMISSION_GRANTED){

                permissoesRequeridas.add(permissao);

            }
        }

        if(!permissoesRequeridas.isEmpty()){
            ActivityCompat.requestPermissions(this, permissoesRequeridas.toArray(new String[permissoesRequeridas.size()]),
                    PERMISSOES_REQUERIDAS);

            return false;

        }

        return true;


    }

    private void imagensReduzidasEscudos(){
        images[0] = R.drawable.reduzidoarsenal;
        images[1] = R.drawable.reduzidoatalanta;
        images[2] = R.drawable.reduzidoathletic_bilbao;
        images[3] = R.drawable.reduzidoatletico_madrid;
        images[4] = R.drawable.reduzidobarcelona;
        images[5] = R.drawable.reduzidobayern;
        images[6] = R.drawable.reduzidobenfica;
        images[7] = R.drawable.reduzidoborussia_dortmund;
        images[8] = R.drawable.reduzidoborussia_monchengladbach;
        images[9] = R.drawable.reduzidochelsea;
        images[10] = R.drawable.reduzidoeintracht_frankfurt;
        images[11] = R.drawable.reduzidohoffenheim;
        images[12] = R.drawable.reduzidointernazionale;
        images[13] = R.drawable.reduzidojuventus;
        images[14] = R.drawable.reduzidolazio;
        images[15] = R.drawable.reduzidoleicester_city;
        images[16] = R.drawable.reduzidoliverpool;
        images[17] = R.drawable.reduzidomanchester_city;
        images[18] = R.drawable.reduzidomanchester_united;
        images[19] = R.drawable.reduzidomilan;
        images[20] = R.drawable.reduzidomonaco;
        images[21] = R.drawable.reduzidonapoli;
        images[22] = R.drawable.reduzidoporto;
        images[23] = R.drawable.reduzidopsg;
        images[24] = R.drawable.reduzidoreal_madrid;
        images[25] = R.drawable.reduzidoreal_sociedad;
        images[26] = R.drawable.reduzidoroma;
        images[27] = R.drawable.reduzidoschalke_04;
        images[28] = R.drawable.reduzidosevilla;
        images[29] = R.drawable.reduzidosporting;
        images[30] = R.drawable.reduzidotottenham;
        images[31] = R.drawable.reduzidovalencia;
        images[32] = R.drawable.reduzidovillarreall;

    }

    private void getUsuarioLogado() {

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    uL = objSnapshot.getValue(Competidor.class);

                    if(uL.getId().equals(uLogadoId)){

                        listUserLogado.add(uL);

                        if(listUserLogado.get(0).getNivelAcesso().equals("ADMINISTRADOR")){

                            usuarioInfoNome.setText(listUserLogado.get(0).getNome());
                            usuarioInfoNivel.setText(uL.getNivelAcesso());
                            Picasso.with(getApplicationContext()).load(uL.getImgPerfil()).into(usuarioLogadoImg);
                            imagensReduzidasEscudos();
                            getUsuarioLogadoImgClube.setImageResource( images[ Integer.parseInt( uL.getImagem() ) ] );
                            getUsuarioLogadoImgClube.setVisibility(View.GONE);

                        } else {

                            usuarioInfoNome.setText(uL.getNome());
                            usuarioInfoNivel.setText("ACESSO " + uL.getNivelAcesso());
                            Picasso.with(getApplicationContext()).load(uL.getImgPerfil()).into(usuarioLogadoImg);
                            imagensReduzidasEscudos();
                            getUsuarioLogadoImgClube.setImageResource( images[ Integer.parseInt( uL.getImagem() ) ] );

                            LinearLayout l = findViewById(R.id.linearLayout17);
                            l.setVisibility(View.GONE);

                            getUsuarioLogadoImgClube.setVisibility(View.GONE);
                            embaralhar.setVisibility(View.GONE);
                            dataProxT.setVisibility(View.GONE);
                            titulo.setVisibility(View.GONE);
                            addHistorico.setVisibility(View.GONE);
                            novoCompetidor.setVisibility(View.GONE);
                            cadastroJogador.setVisibility(View.GONE);
                            gCompetidores.setVisibility(View.GONE);
                            addTemporada.setVisibility(View.GONE);
                            novoTorneio.setVisibility(View.GONE);

                            addHistorico.setVisibility(View.GONE);
                            novoCompetidor.setVisibility(View.GONE);
                            cadastroJogador.setVisibility(View.GONE);
                            gCompetidores.setVisibility(View.GONE);
                            addTemporada.setVisibility(View.GONE);
                            finalizarTorneio.setVisibility(View.GONE);
                        }

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}