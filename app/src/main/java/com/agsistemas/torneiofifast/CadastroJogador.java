package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.List;




public class CadastroJogador extends Activity {

    Spinner sClube, nJogador, sPosicao, sOverall;
    ImageView imagemJogador;
    Button salvar, cancelar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    Competidor clube;
    String idClub, club, pos, ove, jog, posicaoImagem, pImagemC;
    List<String> idC = new ArrayList<String>();
    List<String> imgC = new ArrayList<String>();
    List<String> c = new ArrayList<String>();
    List<String> o = new ArrayList<String>();
    String p[] = {"GL", "ZAG", "LD", "LE", "VOL", "MC", "MD", "ME", "MAD", "MAE", "PD", "PE", "MEI", "SA", "ATA"};

    String jogadores[] =
            {"","ALABA", "ALDERWEIRELD", "ALEX SANCHEZ", "ALEX SANDRO", "ALEX TELLES", "ALEXANDER ARNOLD", "ALISSON",
                    "ANDER HERRERA", "ANTONY", "AREOLA", "ARIAS", "AUBAMEYANG", "AZPILICUETA", "BAGGIO", "BAILY", "BALE", "BALLACK",
                    "BENZEMA", "BERGKAMP", "BERNARDESCHI", "BERNAT", "BEST", "BLANC", "BOATENG", "BROZOVIC", "BRUNO FERNANDES",
                    "BUTRAGUENO", "BUFFON", "BURKI", "CAMPBELL", "CANNAVARO", "CAPA", "CARLOS ALBERTO", "CARVAJAL", "CASEMIRO",
                    "CHRISTIAN ERIKSEN", "CORREA", "COURTOIS", "COUTINHO", "CR7", "CRUYFF", "CUADRADO", "DAVID LUIZ", "DAVID NERES",
                    "DAVID SILVA", "DAVIES", "DE BRUYNE", "DE GEA",  "DE JONG", "DE LIGT", "DE MARCOS", "DEL PIERO", "DEMBELE",
                    "DEPAY", "DESAILLY", "DI MARIA", "DIEGO COSTA", "DRAXLER", "DZEKO", "EDEN HAZARD", "EDERSON", "ESSIEN", "ETO'O",
                    "EUSEBIO", "FABINHO", "FALCÃO GARCIA", "FELIPE", "FELIPE ANDERSON", "FELLAINE", "FERDINAND", "FERNANDEZ",
                    "FERNANDINHO", "HIERRO", "FERNANDO TORRES", "LUIS FIGO", "FRED", "G. JESUS", "GARRINCHA", "GERRARD", "GIOVINCO",
                    "GNABRY", "GODIN", "GOMEZ", "GORETZKA", "GRIEZMANN", "GUARDIOLA", "GUERREIRO", "GUEYE", "GULLIT", "HAKIMI",
                    "HALAND", "HAMSIK", "HANDANOVIC", "HAVERTZ", "HAZARD", "HECTOR HERRERA", "HENDERSON", "HENRY", "HIGUAIN",
                    "HUMMELS", "IBRAHIMOVIC", "ICARD", "IMMOBILE", "INSIGNE", "OXLADE CHAMBERLAIN", "JAMES RODRIGUES", "JOÃO CANCELO",
                    "JOÃO FELIX", "JORGINHO", "JUAN MATA", "KAKA", "KANE", "KEITA", "KEPA", "KIMMICH", "KIMPEMBE", "KOKE", "KOLAROV",
                    "KOULIBALY", "KROSS", "LACAZETTE", "LAHM", "LAMPARD", "LENGLET", "LENO", "LEWANDOWSKI", "LINDELOF", "LO CELSO",
                    "LUCAS HERNANDEZ", "LUCAS MOURA", "LUCAS VAZQUEZ", "LUKAKU", "SHAW", "MAGUIRE", "MAHREZ", "MAKELELE", "MALDINI",
                    "MANÉ", "MANOLAS", "MARADONA", "MARQUINHOS", "MARTIAL", "MARTINEZ", "MATIC", "MATIP", "MATTHAUS", "MBAPPE",
                    "MENDY", "MERTENS", "MESSI", "MILNIR", "MIRANDA", "MKHITARYAN", "MODRIC", "MORATA", "MULLER", "NAINGGOLAN",
                    "NAVAS", "NDOMBELE", "NEDVED", "NESTA", "NETO", "NEYMAR", "OBLAK", "OTAMENDI", "PARTEY", "PAVARD", "PELÉ", "PEPE", "PERISIC",
                    "PIQUE", "PIRLO", "PISZCZEK", "PJANIC", "POGBA", "PUSKAS", "PUYOL", "RABIOT", "RAFINHA", "RAKITIC", "RAMSEY",
                    "RASHFORD", "RAUL", "REIS", "REUS", "RIJKAARD", "RIVALDO", "ROBERTO CARLOS", "ROBERTSON", "RODRI", "RODRYGO",
                    "ROMERO", "RONALDINHO", "RONALDO", "ROONEY", "RUGANI", "SALAH", "SANCHEZ", "SANE", "SARABIA", "SCHWEINSTEIGER",
                    "SEEDORF", "SERGIO RAMOS", "SERGIO ROBERTO", "SHAQIRI", "SHEVCHENKO", "SISSOKO", "SOCRATES", "STOICHKOV", "SUAREZ",
                    "SULE", "SZCZESNY", "TAGLIAFICO", "TER STERGEN", "THIAGO SILVA", "TOLISSO", "ULREICH", "UMTITI", "VAN DIJK",
                    "VARANE", "VARDY", "VERON", "VERRATI", "VIDAL", "VIEIRA", "VINI JR", "WALKER", "WERNER", "WIJNALDUM", "WITSEL",
                    "XAVI", "ZANETTI", "ZIDANE", "ZIYECH", "SON", "FIRMINO", "DYBALA", "KANTÉ", "RODRIGO", "LEMAR", "D. SANCHEZ",
                    "CAN", "CANTONA", "VAN NISTELROOY", "NEUER", "SANCHO", "MARCELO", "THIAGO", "SCHMEICHEL", "CAVANI", "SOMMER", "DIGNE",
                    "GIGGS", "DROGBA", "OVERMARS", "RIQUELME", "BECKHAM", "BUSQUETS", "ALBA", "BONUCCI", "SKRINIAR", "PEREIRA", "YASHIN",
                    "LLORIS", "ZOUMA", "NDIDI", "BERNARDO SILVA", "WILLIAN", "STERLING", "PORTU", "GRIMALDO", "PULISIC", "KOVAČIĆ",
                    "LLORENTE", "MILINKOVIĆ-SAVIĆ", "SAÚL", "CALLEJÓN", "MATUIDI", "MILITÃO", "ARTHUR", "SEMEDO", "DOUGLAS COSTA", "PÉPÉ",
                    "ASENSIO", "GIMÉNEZ", "INIESTA", "SABITZER", "ISCO", "AGUERO", "VIDIĆ", "BARESI", "VAN BASTEN", "MOORE", "DALGLISH",
                    "PIRÈS", "OWEN", "KLUIVERT", "HAGI", "TREZEGUET", "SCHOLES", "KLOSE", "RUSH", "RUI COSTA", "PETIT", "HERNÁNDEZ",
                    "CRESPO", "LEHMANN", "LINEKER", "VAN DER SAR", "BERCHICHE IZETA", "MARCOS ACUNÃ", "KIERA TRIPPIER", "RUBEM SANTOS",
                    "ASHLEY COLE", "RONALD KOEMAN", "CAFÚ", "LAPORTE", "VAN PERSIE", "CASILLAS", "CECH", "GINOLA", "ZAMBROTTA"};

    List<Integer> imagemJogadores = new ArrayList<>();
    TextView tim, nom, po, ov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);
        inicializarFirebase();
        imagensJogador();

        tim = findViewById(R.id.nClube);
        nom = findViewById(R.id.nJogador);
        po = findViewById(R.id.nPosicao);
        ov = findViewById(R.id.nOverall);

        sClube = findViewById(R.id.selecionarClube);
        imagemJogador = findViewById(R.id.imagemJogador);
        nJogador = findViewById(R.id.nomeJogador);
        sPosicao = findViewById(R.id.selecionarPosicao);
        sOverall = findViewById(R.id.selecionarOverall);
        salvar = findViewById(R.id.salvarJogador);
        cancelar = findViewById(R.id.cancelar);

        for(int i = 75; i < 100;i++){

            o.add(String.valueOf(i));

        }


        nJogador.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, jogadores));
        sPosicao.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, p));
        sOverall.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, o));
        getClubes();

        sClube.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                club = String.valueOf(sClube.getSelectedItem());
                idClub = idC.get(i);
                tim.setText(club);
                pImagemC = imgC.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nJogador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               jog = String.valueOf(nJogador.getSelectedItem());

                for(int j = 0; j < jogadores.length; j++){
                    if(i == j){
                        imagemJogador.setImageResource(imagemJogadores.get(j));
                        posicaoImagem = String.valueOf(j);
                        nom.setText(jog);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sPosicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                pos = String.valueOf(sPosicao.getSelectedItem());
                po.setText(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sOverall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ove = String.valueOf(sOverall.getSelectedItem());
                ov.setText(ove);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Jogador novoJ = new Jogador();

                    novoJ.setIdClube(idClub);
                    novoJ.setIdJogador(posicaoImagem);
                    novoJ.setnJogador(jog);
                    novoJ.setImagem(posicaoImagem);
                    novoJ.setImgClubeAssociado(pImagemC);
                    novoJ.setcJogador(club);
                    novoJ.setpJogador(pos);
                    novoJ.setoJogador(ove);

                    databaseReference.child("JogadoresCadastrados").child(novoJ.getIdJogador()).setValue(novoJ);


                    finish();
                    Toast.makeText(CadastroJogador.this, novoJ.getnJogador() + " FOI ADICIONADO COM SUCESSO AO " + novoJ.getcJogador(), Toast.LENGTH_SHORT).show();




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
        for( int i = 0; i < jogadores.length; i++ ){

            imagemJogadores.add( getResources().getIdentifier("jogador" + i , "drawable", getPackageName() ) );

        }

    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void getClubes() {
        progressDialog = new ProgressDialog(CadastroJogador.this );
        //Configura o título da progress dialog
        progressDialog.setTitle("Aguarde");
        //configura a mensagem de que está sendo feito o carregamento
        progressDialog.setMessage("Carregando!!!");
        //configura se a progressDialog pode ser cancelada pelo usuário
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    clube = objSnapshot.getValue(Competidor.class);

                    idC.add(clube.getId());
                    imgC.add(clube.getImagem());
                    c.add(clube.getTime() + "/" + clube.getNome());

                }

                sClube.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, c));
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}