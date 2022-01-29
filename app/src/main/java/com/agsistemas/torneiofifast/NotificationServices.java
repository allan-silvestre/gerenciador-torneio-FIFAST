package com.agsistemas.torneiofifast;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationServices extends Service {

    UltimasTransf p;

    JogosFg gA, gB, f;

    List<JogosFg> jogosGrupoB = new ArrayList<JogosFg>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public NotificationServices() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        notificacaoTransferencia();
        //notificacoesJogosGrupoA();
        //notificacoesJogosGrupoB();
        //notificacoesJogosPontosCorridosTurno1();
        //notificacoesFinais();
        //monitoramento();
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       Log.e("TAG", "onStartCommand");
        notificacaoTransferencia();
       // notificacoesJogosGrupoA();
        //notificacoesJogosGrupoB();
        //notificacoesJogosPontosCorridosTurno1();
        //notificacoesFinais();
        //monitoramento();

       return START_STICKY;

    }

    private void monitoramento() {

        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.custom_notification);
        RemoteViews expandedView = new RemoteViews(getPackageName(),
                R.layout.custom_notification_expanded);

        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "FIFAST NEWS - EM EXECUÇÃO");
        expandedView.setImageViewResource(R.id.image_view_expanded, R.drawable.ic_launcher);
        Intent clickIntent = new Intent(this, BootCompletedIntentReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServices.this, getString(R.string.APP_ID))
                .setSmallIcon(R.drawable.reduzidovillarreall)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setCustomContentView(collapsedView)
                //.setCustomBigContentView(expandedView)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("jogador" + Integer.parseInt(p.getImgJogador()) , "drawable", getPackageName() )))
                //.setContentTitle("Transferência")
                .setGroup("aaa")
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                //.setContentText(p.getNomeJogador() + " AGORA É DO: " + p.getNovoClubeJogador())
                //.setStyle(new NotificationCompat.BigTextStyle().bigText( p.getNomeJogador() + " - " + p.getPosJogador() + " - " + p.getOverJogador() + "\n" + "SAIU DO: " + p.getAntClube() + "\n" + "ENTROU PARA: " + p.getNovoClubeJogador()))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                ///.setContentIntent(pendingIntent)
                //.setAutoCancel(true)
                .setChannelId( getString(R.string.APP_ID));

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(NotificationServices.this);
        startForeground(954284714, builder.build());



        /*
        // APP EM EXECUÇÃO
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationServices.this, getString(R.string.APP_ID));
        Notification notification = notificationBuilder.setOngoing(true)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setSmallIcon(R.drawable.reduzidovillarreall)
                .setGroup("aaa")
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                //.setCategory(Notification.CATEGORY_SERVICE)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(NotificationServices.this);
        //notificationManagerCompat.notify(900, notification);
        startForeground(999, notification);

         */


        //notificacoesJogosGrupoA();
        //notificacoesJogosGrupoB();
        //notificacoesFinais();

    }

    public void notificacaoTransferencia(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("UltimasTransf").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    p = objSnapshot.getValue(UltimasTransf.class);

                }

                Intent intent = new Intent(NotificationServices.this, GerenciarClubes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationServices.this,0, intent, 0);

                final NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServices.this, getString(R.string.NEWS_CHANNEL_ID))
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("jogador" + Integer.parseInt(p.getImgJogador()) , "drawable", getPackageName() )))
                        .setContentTitle("Transferência")
                        .setGroup("bbb")
                        .setContentText(p.getNomeJogador() + " AGORA É DO: " + p.getNovoClubeJogador())
                        .setStyle(new NotificationCompat.BigTextStyle().bigText( p.getNomeJogador() + " - " + p.getPosJogador() + " - " + p.getOverJogador() + "\n" + "SAIU DO: " + p.getAntClube() + "\n" + "ENTROU PARA: " + p.getNovoClubeJogador()))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setChannelId( getString(R.string.NEWS_CHANNEL_ID));

                final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(NotificationServices.this);




                ChildEventListener mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildAdded()");

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildChanged()");
                        notificationManagerCompat.notify( Integer.parseInt( p.getImgJogador() ), builder.build());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("TAG", "ChildEventListener: onChildRemoved()");

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildMoved()");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "ChildEventListener: onCancelled()");
                    }
                };
                databaseReference.child("UltimasTransf").limitToLast(1).addChildEventListener(mChildEventListener);


                UltimasTransf ut = new UltimasTransf();

                ut.setId(p.getId());
                ut.setIdJogador(p.getIdJogador());
                ut.setDataHora(p.getDataHora());
                ut.setNovaIdClube(p.getNovaIdClube());
                ut.setNomeJogador(p.getNomeJogador());
                ut.setImgJogador(p.getImgJogador());
                ut.setImgClube(p.getImgClube());
                ut.setImgAntClube(p.getImgAntClube());
                ut.setPosJogador(p.getPosJogador());
                ut.setOverJogador(p.getOverJogador());
                ut.setNovoClubeJogador(p.getNovoClubeJogador());
                ut.setAntClube(p.getAntClube());
                ut.setNumeroNotify("99");

                databaseReference.child("UltimasTransf").child(ut.getId()).setValue(ut);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void notificacoesJogosGrupoA(){

        databaseReference.child("Notificacoes").child("JogosGa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String conteudo = "";
                int imagem = 8;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                     gA = objSnapshot.getValue(JogosFg.class);
                }

                if( Integer.parseInt( gA.getGolsC() ) > Integer.parseInt( gA.getGolsF() ) ){
                    imagem = Integer.parseInt(gA.getImagemPlayerCasa());
                    conteudo = gA.getTimeCasa() + " venceu " + gA.getTimeFora() + " por " + gA.getGolsC() + " a " + gA.getGolsF();

                } else if( Integer.parseInt( gA.getGolsC() ) < Integer.parseInt( gA.getGolsF() ) ){
                    imagem = Integer.parseInt(gA.getImagemPlayerFora());
                    conteudo = gA.getTimeFora() + " venceu " + gA.getTimeCasa() + " por " + gA.getGolsF() + " a " + gA.getGolsC();

                } else if( Integer.parseInt( gA.getGolsC() ) == Integer.parseInt( gA.getGolsF() ) ){
                    imagem = Integer.parseInt(gA.getImagemPlayerCasa());
                    conteudo = gA.getTimeCasa() + " empatou em  " + gA.getGolsC() + " a " + gA.getGolsF() + " com " + gA.getTimeFora() ;

                }

                Intent intent = new Intent(NotificationServices.this, JogosFgActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationServices.this,0, intent, 0);

                final NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServices.this, getString(R.string.JOGOS_ID))
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("participante" + imagem , "drawable", getPackageName() )))
                        .setContentTitle("Grupo A - Partida finalizada")
                        .setGroup("gA")
                        .setContentText(conteudo)
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText( ""))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setChannelId( getString(R.string.JOGOS_ID));

                final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(NotificationServices.this);

                ChildEventListener mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildAdded()");

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildChanged()");
                        notificationManagerCompat.notify( 999, builder.build());

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("TAG", "ChildEventListener: onChildRemoved()");

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildMoved()");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "ChildEventListener: onCancelled()");
                    }
                };

                databaseReference.child("Notificacoes").child("JogosGa").child("gA").limitToLast(1).addChildEventListener(mChildEventListener);

                JogosFg p = new JogosFg();
                p.setId(gA.getId());
                p.setImagemTimeCasa(gA.getImagemTimeCasa());
                p.setImagemTimeFora(gA.getImagemTimeFora());
                p.setImagemPlayerCasa(gA.getImagemPlayerCasa());
                p.setImagemPlayerFora(gA.getImagemPlayerFora());
                p.setTimeCnPartida(gA.getTimeCnPartida());
                p.setTimeFnPartida(gA.getTimeFnPartida());
                p.setIdTimeCasa(gA.getIdTimeCasa());
                p.setIdTimeFora(gA.getIdTimeFora());
                p.setTimeCasa(gA.getTimeCasa());
                p.setTimeFora(gA.getTimeFora());
                p.setGolsC(gA.getGolsC());
                p.setGolsF(gA.getGolsF());
                p.setTituloPartida("notificacao");

                databaseReference.child("Notificacoes").child("JogosGa").child("gA").setValue(p);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void notificacoesJogosGrupoB(){

        databaseReference.child("Notificacoes").child("JogosGb").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String conteudo = "";
                int imagem = 8;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    gB = objSnapshot.getValue(JogosFg.class);
                }

                if( Integer.parseInt( gB.getGolsC() ) > Integer.parseInt( gB.getGolsF() ) ){
                    imagem = Integer.parseInt(gB.getImagemPlayerCasa());
                    conteudo = gB.getTimeCasa() + " venceu " + gB.getTimeFora() + " por " + gB.getGolsC() + " a " + gB.getGolsF();

                } else if( Integer.parseInt( gB.getGolsC() ) < Integer.parseInt( gB.getGolsF() ) ){
                    imagem = Integer.parseInt(gB.getImagemPlayerFora());
                    conteudo = gB.getTimeFora() + " venceu " + gB.getTimeCasa() + " por " + gB.getGolsF() + " a " + gB.getGolsC();

                } else if( Integer.parseInt( gB.getGolsC() ) == Integer.parseInt( gB.getGolsF() ) ){
                    imagem = Integer.parseInt(gB.getImagemPlayerCasa());
                    conteudo = gB.getTimeCasa() + " empatou em  " + gB.getGolsC() + " a " + gB.getGolsF() + " com " + gB.getTimeFora() ;

                }

                Intent intent = new Intent(NotificationServices.this, JogosFgActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationServices.this,0, intent, 0);

                final NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServices.this, getString(R.string.JOGOS_ID))
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("participante" + imagem , "drawable", getPackageName() )))
                        .setContentTitle("Grupo B - Partida finalizada")
                        .setGroup("gB")
                        .setContentText(conteudo)
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText( ""))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setChannelId( getString(R.string.JOGOS_ID));

                final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(NotificationServices.this);
                final int idN = Integer.parseInt(gB.getGolsC()) + Integer.parseInt(gB.getGolsF()) + Integer.parseInt(gB.getImagemPlayerCasa()) + Integer.parseInt(gB.getImagemTimeFora());

                ChildEventListener mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildAdded()");

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildChanged()");
                        notificationManagerCompat.notify( 998, builder.build());

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("TAG", "ChildEventListener: onChildRemoved()");

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildMoved()");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "ChildEventListener: onCancelled()");
                    }
                };

                databaseReference.child("Notificacoes").child("JogosGb").child("gB").limitToLast(1).addChildEventListener(mChildEventListener);

                JogosFg p = new JogosFg();
                p.setId(gB.getId());
                p.setImagemTimeCasa(gB.getImagemTimeCasa());
                p.setImagemTimeFora(gB.getImagemTimeFora());
                p.setImagemPlayerCasa(gB.getImagemPlayerCasa());
                p.setImagemPlayerFora(gB.getImagemPlayerFora());
                p.setTimeCnPartida(gB.getTimeCnPartida());
                p.setTimeFnPartida(gB.getTimeFnPartida());
                p.setIdTimeCasa(gB.getIdTimeCasa());
                p.setIdTimeFora(gB.getIdTimeFora());
                p.setTimeCasa(gB.getTimeCasa());
                p.setTimeFora(gB.getTimeFora());
                p.setGolsC(gB.getGolsC());
                p.setGolsF(gB.getGolsF());
                p.setTituloPartida("notificacao");

                databaseReference.child("Notificacoes").child("JogosGb").child("gB").setValue(p);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void notificacoesFinais(){

        databaseReference.child("Notificacoes").child("JogosF").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String titulo = "";
                String conteudo = "";
                int imagem = 8;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    f = objSnapshot.getValue(JogosFg.class);
                }

                if(f.getTituloPartida().equals("S. FINAL")){
                    titulo = "Semi final - partida finalizada";

                } else if(f.getTituloPartida().equals("S. FINAL")){
                    titulo = "Semi final - partida finalizada";

                } else if(f.getTituloPartida().equals("3ª LUGAR")){
                    titulo = "Disputa de 3ª lugar - partida finalizada";

                } else if(f.getTituloPartida().equals("FINAL")){
                    titulo = "Final - partida finalizada";

                }

                if( Integer.parseInt( f.getGolsC() ) + Integer.parseInt( f.getGolsPenaltC() ) > Integer.parseInt( f.getGolsF() ) + Integer.parseInt( f.getGolsPenaltF() ) ){
                    imagem = Integer.parseInt(f.getImagemPlayerCasa());
                    conteudo = f.getTimeCasa() + " venceu " + f.getTimeFora() + " por " + f.getGolsC() + " a " + f.getGolsF();

                } else if( Integer.parseInt( f.getGolsC() ) + Integer.parseInt( f.getGolsPenaltC() ) < Integer.parseInt( f.getGolsF() ) + Integer.parseInt( f.getGolsPenaltF() ) ){
                    imagem = Integer.parseInt(f.getImagemPlayerFora());
                    conteudo = f.getTimeFora() + " venceu " + f.getTimeCasa() + " por " + f.getGolsF() + " a " + f.getGolsC();

                } else if( Integer.parseInt( f.getGolsC() ) + Integer.parseInt( f.getGolsPenaltC() ) < Integer.parseInt( f.getGolsF() ) + Integer.parseInt( f.getGolsPenaltF() ) ){
                    imagem = Integer.parseInt(f.getImagemPlayerCasa());
                    conteudo = " Partida entre " + f.getTimeCasa() + " e " + f.getTimeFora() + "Será decidida nos panaltis";

                }

                Intent intent = new Intent(NotificationServices.this, TabelaFg.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationServices.this,0, intent, 0);

                final NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServices.this, getString(R.string.JOGOS_ID))
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("participante" + imagem , "drawable", getPackageName() )))
                        .setContentTitle(titulo)
                        .setGroup("f")
                        .setContentText(conteudo)
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText( ""))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setChannelId( getString(R.string.JOGOS_ID));

                final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(NotificationServices.this);

                final int idN = Integer.parseInt(f.getGolsC()) + Integer.parseInt(f.getGolsF()) + Integer.parseInt(f.getImagemPlayerCasa()) + Integer.parseInt(f.getImagemTimeFora());

                ChildEventListener mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildAdded()");

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildChanged()");
                        notificationManagerCompat.notify( 997, builder.build());

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("TAG", "ChildEventListener: onChildRemoved()");

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("TAG", "ChildEventListener: onChildMoved()");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "ChildEventListener: onCancelled()");
                    }
                };

                databaseReference.child("Notificacoes").child("JogosF").child("f").limitToLast(1).addChildEventListener(mChildEventListener);

                JogosFg p = new JogosFg();
                p.setId(f.getId());
                p.setImagemTimeCasa(f.getImagemTimeCasa());
                p.setImagemTimeFora(f.getImagemTimeFora());
                p.setImagemPlayerCasa(f.getImagemPlayerCasa());
                p.setImagemPlayerFora(f.getImagemPlayerFora());
                p.setTimeCnPartida(f.getTimeCnPartida());
                p.setTimeFnPartida(f.getTimeFnPartida());
                p.setIdTimeCasa(f.getIdTimeCasa());
                p.setIdTimeFora(f.getIdTimeFora());
                p.setTimeCasa(f.getTimeCasa());
                p.setTimeFora(f.getTimeFora());
                p.setGolsC(f.getGolsC());
                p.setGolsF(f.getGolsF());
                p.setTituloPartida("notificar");

                databaseReference.child("Notificacoes").child("JogosF").child("f").setValue(p);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void notificacoesJogosPontosCorridosTurno1(){


        databaseReference.child("JogosPontosC").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    JogosFg p = objSnapshot.getValue(JogosFg.class);

                    if(p.getTimeCasa().equals("INATIVO\nINATIVO") || p.getTimeFora().equals("INATIVO\nINATIVO")) {
                        //NÃO FAZER NADA
                    } else {
                        if(i < 28){
                        }
                    }
                    i++;

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onDestroy() {
        onDestroy();
    }

}
