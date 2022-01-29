package com.agsistemas.torneiofifast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Login extends Activity {

    EditText usuario, senha;
    Button entrar, entrarC;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Competidor> usuarioDadosBd = new ArrayList<>();
    String u;
    String s;
    String android_id;

    ProgressDialog progressDialog;

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        //super.onBackPressed();  // optional depending on your needs
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarFirebase();

        usuario = findViewById(R.id.nomeUsuario);
        senha = findViewById(R.id.senha);
        entrar = findViewById(R.id.entrar);
        entrarC = findViewById(R.id.entrarC);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verificacao();

            }
        });

        entrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertaConvidado();

            }
        });
        
    }

    @Override
    public void onRestart(){
        super.onRestart();
        // cria uma nova activity
        startActivity(new Intent(this, GerenciarClubes.class));
        finish(); // finaliza a activity aberta
    }

    private void verificacao(){



        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioDadosBd.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Competidor c = objSnapshot.getValue(Competidor.class);

                    usuarioDadosBd.add(c);

                }

                u = usuario.getText().toString();
                s = senha.getText().toString();


                for(int i = 0; i < usuarioDadosBd.size(); i++){

                    if( u.equals(usuarioDadosBd.get(i).getNome()) && s.equals(usuarioDadosBd.get(i).getSenha()) ){

                        if ( usuarioDadosBd.get(i).getStatus().equals("INATIVO") ){
                            usuario.setError("Usuário desativado!");
                            senha.setError("Usuário desativado!");

                        } else {

                            progressDialog = new ProgressDialog(Login.this, R.style.MyAlertDialogStyle );
                            //Configura o título da progress dialog
                            progressDialog.setTitle("FIFAST");
                            //configura a mensagem de que está sendo feito o carregamento
                            progressDialog.setMessage("Carregando...");
                            //configura se a progressDialog pode ser cancelada pelo usuário
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                            databaseReference.child("Competidores").child(usuarioDadosBd.get(i).getId()).child("macLogado").setValue(android_id);
                            Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                            intent.putExtra("idLogado", usuarioDadosBd.get(i).getId() );
                            startActivity(intent);
                            usuarioDadosBd.clear();
                            finish();
                        }


                    } else if ( u.equals("")){

                        usuario.setError("Campo obrigatorio!!");

                    } else if ( s.equals("")){

                        senha.setError("Campo obrigatorio!!");

                    } else if ( u != usuarioDadosBd.get(i).getNome() || s != usuarioDadosBd.get(i).getSenha() ){

                        usuario.setError("Usuario ou senha invalidos!");
                        senha.setError("Usuario ou senha invalidos!");


                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertaConvidado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( "Ao entrar como convidado você terá acesso apenas a visualização de alguns conteudos e não podera fazer alterações" );
        builder.setPositiveButton("Continuar como Convidado",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                Intent intent = new Intent(Login.this, VisitanteTabela.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("fechar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                //ação do botão "sim"

            }
        });
        builder.show();
    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

}