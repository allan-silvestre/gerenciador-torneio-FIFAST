package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditarJogadorClube extends Activity {

    String idJ, idC, img, imgAntClubAssociado, imgCassociado, nJ, cJ, pJ, oJ, novoCj, novoIdClube;
    Competidor clube;

    android.widget.Spinner sC;
    List<String> getIdClube = new ArrayList<String>();
    List<String> c = new ArrayList<String>();
    List<String> imgAssociado = new ArrayList<String>();

    ArrayList<UltimasTransf> listT = new ArrayList<>();
    UltimasTransf p;

    ImageView imgJ;
    TextView idJogador, nomeJ, posicaoJ, overallJ, clubeJ, clubeAtual;
    Button transfJogador, cancelar;

    List<Integer> imagemJogadores = new ArrayList<>();

    ArrayList<Competidor> listUserLogado = new ArrayList<>();
    Competidor uL;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    String uLogadoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_jogador_clube);

        inicializarFirebase();
        imagensJogador();
        getClubes();
        getUsuarioLogado();
        ultimasTransf();

        imgJ = findViewById(R.id.imagemJogador);

        nomeJ = findViewById(R.id.nJogador);
        posicaoJ = findViewById(R.id.nPosicao);
        overallJ = findViewById(R.id.nOverall);
        clubeJ = findViewById(R.id.nClube);
        clubeAtual = findViewById(R.id.clubeAtual);
        idJogador = findViewById(R.id.id);

        transfJogador = findViewById(R.id.transfJogador);
        cancelar = findViewById(R.id.cancelar);

        sC = findViewById(R.id.selecionarClube);
        sC.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);

        sC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                novoCj = String.valueOf(sC.getSelectedItem());
                novoIdClube = getIdClube.get(i);
                clubeJ.setText(novoCj);
                imgCassociado = imgAssociado.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Bundle b = new Bundle();
        b = getIntent().getExtras();

        idJ = b.getString("idJogador");
        idC = b.getString("idClube");
        img = b.getString("imagem");
        imgAntClubAssociado = b.getString("imgClubeAssociado");
        nJ = b.getString("nJogador");
        cJ = b.getString("cJogador");
        pJ = b.getString("pJogador");
        oJ = b.getString("oJogador");

        uLogadoId = b.getString("idLogado");


        imgJ.setImageResource( imagemJogadores.get( Integer.parseInt( img ) ) );
        nomeJ.setText(nJ);
        posicaoJ.setText(pJ);
        overallJ.setText(oJ);
        clubeJ.setText(cJ);
        idJogador.setText("ID: " + idJ);
        clubeAtual.setText("CLUBE ATUAL\n" + cJ);

        transfJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listUserLogado.get(0).getNivelAcesso().equals("ADMINISTRADOR")){

                    progressDialog = new ProgressDialog(EditarJogadorClube.this, R.style.MyAlertDialogStyle );
                    //Configura o título da progress dialog
                    progressDialog.setTitle("Aguarde");
                    //configura a mensagem de que está sendo feito o carregamento
                    progressDialog.setMessage("Carregando!!!");
                    //configura se a progressDialog pode ser cancelada pelo usuário
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    Jogador jogador = new Jogador();

                    jogador.setIdJogador(idJ);
                    jogador.setIdClube(novoIdClube);
                    jogador.setImagem(img);
                    jogador.setImgClubeAssociado(imgCassociado);
                    jogador.setnJogador(nJ);
                    jogador.setpJogador(pJ);
                    jogador.setoJogador(oJ);
                    jogador.setcJogador(novoCj);

                    UltimasTransf ut = new UltimasTransf();


                    DateFormat idFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    Date id = new Date();

                    ut.setId(idFormat.format(id));
                    ut.setIdJogador(idJ);
                    ut.setNovaIdClube(novoIdClube);
                    ut.setNomeJogador(nJ);
                    ut.setImgJogador(img);
                    ut.setImgClube(imgCassociado);
                    ut.setImgAntClube(imgAntClubAssociado);
                    ut.setPosJogador(pJ);
                    ut.setOverJogador(oJ);
                    ut.setNovoClubeJogador(novoCj);
                    ut.setAntClube(cJ);
                    ut.setNumeroNotify("97");

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    ut.setDataHora(dateFormat.format(date));

                    databaseReference.child("JogadoresCadastrados").child(idJ).setValue(jogador);
                    databaseReference.child("UltimasTransf").child(ut.getId()).setValue(ut);

                    Toast.makeText(EditarJogadorClube.this, "Carregando, aguarde...!", Toast.LENGTH_SHORT).show();
                    finish();
                    Toast.makeText(EditarJogadorClube.this, "Transferência de jogador realizada com sucesso!", Toast.LENGTH_SHORT).show();

                } else {

                    if(listUserLogado.get(0).getId().equals(idC)){

                        progressDialog = new ProgressDialog(EditarJogadorClube.this, R.style.MyAlertDialogStyle );
                        //Configura o título da progress dialog
                        progressDialog.setTitle("Aguarde");
                        //configura a mensagem de que está sendo feito o carregamento
                        progressDialog.setMessage("Carregando!!!");
                        //configura se a progressDialog pode ser cancelada pelo usuário
                        progressDialog.setCancelable(false);
                        progressDialog.show();


                        Jogador jogador = new Jogador();

                        jogador.setIdJogador(idJ);
                        jogador.setIdClube(novoIdClube);
                        jogador.setImagem(img);
                        jogador.setImgClubeAssociado(imgCassociado);
                        jogador.setnJogador(nJ);
                        jogador.setpJogador(pJ);
                        jogador.setoJogador(oJ);
                        jogador.setcJogador(novoCj);

                        UltimasTransf ut = new UltimasTransf();


                        DateFormat idFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                        Date id = new Date();

                        ut.setId(idFormat.format(id));
                        ut.setIdJogador(idJ);
                        ut.setNovaIdClube(novoIdClube);
                        ut.setNomeJogador(nJ);
                        ut.setImgJogador(img);
                        ut.setImgClube(imgCassociado);
                        ut.setImgAntClube(imgAntClubAssociado);
                        ut.setPosJogador(pJ);
                        ut.setOverJogador(oJ);
                        ut.setNovoClubeJogador(novoCj);
                        ut.setAntClube(cJ);
                        ut.setNumeroNotify("97");

                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        ut.setDataHora(dateFormat.format(date));

                        databaseReference.child("JogadoresCadastrados").child(idJ).setValue(jogador);
                        databaseReference.child("UltimasTransf").child(ut.getId()).setValue(ut);

                        Toast.makeText(EditarJogadorClube.this, "Carregando, aguarde...!", Toast.LENGTH_SHORT).show();
                        finish();
                        Toast.makeText(EditarJogadorClube.this, "Transferência de jogador realizada com sucesso!", Toast.LENGTH_SHORT).show();

                    } else {

                        progressDialog = new ProgressDialog(EditarJogadorClube.this, R.style.MyAlertDialogStyle );
                        //Configura o título da progress dialog
                        progressDialog.setTitle("Aguarde");
                        //configura a mensagem de que está sendo feito o carregamento
                        progressDialog.setMessage("Carregando!!!");
                        //configura se a progressDialog pode ser cancelada pelo usuário
                        progressDialog.setCancelable(false);
                        progressDialog.show();


                        Jogador jogador = new Jogador();

                        jogador.setIdJogador(idJ);
                        jogador.setIdClube(novoIdClube);
                        jogador.setImagem(img);
                        jogador.setImgClubeAssociado(imgCassociado);
                        jogador.setnJogador(nJ);
                        jogador.setpJogador(pJ);
                        jogador.setoJogador(oJ);
                        jogador.setcJogador(novoCj);

                        UltimasTransf ut = new UltimasTransf();


                        DateFormat idFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                        Date id = new Date();

                        ut.setId(idFormat.format(id));
                        ut.setIdJogador(idJ);
                        ut.setNovaIdClube(novoIdClube);
                        ut.setNomeJogador(nJ);
                        ut.setImgJogador(img);
                        ut.setImgClube(imgCassociado);
                        ut.setImgAntClube(imgAntClubAssociado);
                        ut.setPosJogador(pJ);
                        ut.setOverJogador(oJ);
                        ut.setNovoClubeJogador(novoCj);
                        ut.setAntClube(cJ);
                        ut.setNumeroNotify("97");

                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        ut.setDataHora(dateFormat.format(date));

                        databaseReference.child("JogadoresCadastrados").child(idJ).setValue(jogador);
                        databaseReference.child("UltimasTransf").child(ut.getId()).setValue(ut);

                        Toast.makeText(EditarJogadorClube.this, "Carregando, aguarde...!", Toast.LENGTH_SHORT).show();
                        finish();
                        Toast.makeText(EditarJogadorClube.this, "Transferência de jogador realizada com sucesso!", Toast.LENGTH_SHORT).show();

                        /*
                        alertaSemPermissao();

                         */

                    }

                }



            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void imagensJogador(){
        CadastroJogador a = new CadastroJogador();

        for( int i = 0; i < a.jogadores.length; i++ ){

            imagemJogadores.add( getResources().getIdentifier("jogador" + i , "drawable", getPackageName() ) );

        }

    }

    private void getClubes() {

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    clube = objSnapshot.getValue(Competidor.class);

                    String controlador = clube.getTime() + "/" + clube.getNome();

                    if(controlador.equals(cJ)) {

                    } else{
                        getIdClube.add(clube.getId());
                        c.add(clube.getTime() + "/" + clube.getNome());
                        imgAssociado.add(clube.getImagem());
                    }


                }

                sC.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, c));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUsuarioLogado() {

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    uL = objSnapshot.getValue(Competidor.class);

                    if(uL.getId().equals(uLogadoId)){

                        listUserLogado.add(uL);

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertaSemPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Transferência não autorizada");
        builder.setMessage("Somente o proprietário do time tem acesso a realizar a transferência do jogador.");
        builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {


            }
        });
        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });
        builder.show();
    }

    public void ultimasTransf(){

        inicializarFirebase();

        databaseReference.child("UltimasTransf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listT.clear();
                final NotificationManagerCompat notificationManagerCompat;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(UltimasTransf.class);
                    listT.add(p);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return;

    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}