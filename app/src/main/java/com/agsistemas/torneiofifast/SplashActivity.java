package com.agsistemas.torneiofifast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SplashActivity extends Activity {

    ConstraintLayout layout;

    final int[] images = new int[5];

    private final String TAG = "SplashActivity";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String android_id;

    TextView versaoAppId;
    Config config;
    String versaoApp;

    Competidor c[] = new Competidor[8];

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mediaPlayer = MediaPlayer.create(SplashActivity.this, R.raw.efeito2);

        inicializarFirebase();

        android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        versaoAppId = findViewById(R.id.versaoAppId);

        versaoAppId.setText("7.2.4");

        if( isOnline() ) {
            getConfigServidor();
        } else if( !isOnline() ){
            alertaConexao();
        }

        createNotificationChannel();
        //startService(new Intent(getBaseContext(), NotificationServices.class));
        sendBroadcast(new Intent(getBaseContext(), BootCompletedIntentReceiver.class));

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(getBaseContext(), NotificationServices.class));
        }

 */

        imagensBackgrounds();

        Random random = new Random();
        int n = random.nextInt(3);

        layout = findViewById(R.id.layout);
        layout.setBackgroundResource( images[n]);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.NEWS_CHANNEL_ID),getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT );
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.JOGOS_ID),getString(R.string.JOGOS), NotificationManager.IMPORTANCE_DEFAULT );
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notification = new NotificationChannel(getString(R.string.APP_ID),getString(R.string.APP), NotificationManager.IMPORTANCE_MIN );
            notification.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notification.setShowBadge(true);
            notification.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            notification.setSound(null, null);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notification);
        }
    }

    private void imagensBackgrounds(){
        images[0] = R.drawable.back1;
        images[1] = R.drawable.back10;
        images[2] = R.drawable.back8;
        images[3] = R.drawable.back2;

    }

    private void verificacaoUserLogado(){

        databaseReference.child("Competidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                     c[i] = objSnapshot.getValue(Competidor.class);

                    if (android_id.equals(c[i].getMacLogado())) {
                        Intent intent = new Intent(SplashActivity.this, Configuracoes.class);
                        intent.putExtra("idLogado", c[i].getId());
                        startActivity(intent);
                        finish();
                    }

                    i++;

                }

                    if (!android_id.equals(c[0].getMacLogado()) && !android_id.equals(c[1].getMacLogado()) && !android_id.equals(c[2].getMacLogado()) &&
                        !android_id.equals(c[3].getMacLogado()) && !android_id.equals(c[4].getMacLogado()) && !android_id.equals(c[5].getMacLogado()) &&
                        !android_id.equals(c[6].getMacLogado()) && !android_id.equals(c[7].getMacLogado())) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(getBaseContext(), Login.class));
                            finish();

                        }
                    }, 3000);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getConfigServidor(){
        databaseReference.child("Configuracoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    config = objSnapshot.getValue(Config.class);

                    //formatoTorneio = config.getFormatoTorneio();
                    //statusTorneio = config.getStatusTorneio();
                    //dataProxT = config.getDataProxT();
                    versaoApp = config.getVersaoApp();
                }

                if(!versaoApp.equals(versaoAppId.getText().toString())){

                    alertaVersaoApp();

                } else {
                    verificacaoUserLogado();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertaVersaoApp() {
        mediaPlayer.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( "Você não esta ultilizando a versão mais recente do aplicativo" );
        builder.setPositiveButton("Atualizar agora",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                String url = "https://drive.google.com/file/d/1wQx-PycAfXWcozCEXhpjyQnnvneS_slJ/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });
        builder.show();
    }

    private void alertaConexao() {
        mediaPlayer.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Torneio FIFAST");
        builder.setMessage( "Falha na conexão com o banco de dados, verifique sua conexão com a internet e tente novamente" );
        builder.setPositiveButton("Recarregar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                finishAffinity();
                Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });
        builder.show();
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }


}