package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;

public class EditarPerfil extends Activity {

    ImageView imgPerfil, imgClube;
    TextView nome;
    EditText senha, confirmSenha;
    Button salvar, cancelar;

    String uLogadoId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Competidor> listUserLogado = new ArrayList<>();
    Competidor uL;

    final int[] images = new int[33];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        imgPerfil = findViewById(R.id.imgPerfil);
        imgClube = findViewById(R.id.escudoTime);

        nome = findViewById(R.id.nomeId);

        senha = findViewById(R.id.senhaCedit);
        confirmSenha = findViewById(R.id.confirmSenha);

        salvar = findViewById(R.id.salvarCedit);
        cancelar = findViewById(R.id.cancelarCedit);


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = senha.getText().toString();

                if(!senha.getText().toString().equals(confirmSenha.getText().toString())){
                    confirmSenha.setError("As senhas não conferem!");

                } else{

                    if(senha.length() < 8){
                        senha.setError("A senha deve conter no mínimo 8 caracteres!");
                    }else {
                        Toast.makeText(getApplicationContext(), "Senha Alterada com sucesso.", Toast.LENGTH_LONG).show();
                        databaseReference.child("Competidores").child(uLogadoId).child("senha").setValue(senha.getText().toString());
                        finish();
                    }

                }



            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Bundle b = new Bundle();
        b = getIntent().getExtras();

        uLogadoId = b.getString("idLogado");

        inicializarFirebase();
        getUsuarioLogado();

    }

    private void getUsuarioLogado() {

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    uL = objSnapshot.getValue(Competidor.class);

                    if(uL.getId().equals(uLogadoId)){

                        listUserLogado.add(uL);

                        nome.setText(uL.getNome());
                        senha.setText(uL.getSenha());

                        Picasso.with(EditarPerfil.this).load( uL.getImgPerfil() ).into(imgPerfil);
                        imagensEscudos();
                        imgClube.setImageResource( images[ Integer.parseInt( uL.getImagem() ) ] );

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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