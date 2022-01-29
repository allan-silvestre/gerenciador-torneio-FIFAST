package com.agsistemas.torneiofifast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterListPlayers extends BaseAdapter {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Jogador> listGoleiros = new ArrayList<>();
    ArrayList<Jogador> listDefesa = new ArrayList<>();
    ArrayList<Jogador> listMeio = new ArrayList<>();
    ArrayList<Jogador> listAtaque = new ArrayList<>();
    Jogador a;

    private Context context;
    private List<Competidor> listTempA;

    public AdapterListPlayers(Context context, List<Competidor> listTemA) {
        this.context = context;
        this.listTempA = listTemA;
    }

    List<Integer> imgPlayers = new ArrayList<>();
    final int[] images = new int[33];

    @Override
    public int getCount() {
        return listTempA.size();
    }

    @Override
    public Object getItem(int i) {
        return listTempA.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_listplayers, null);

        inicializarFirebase();

        ImageView imgPlayer = v.findViewById(R.id.imgPlayer);
        ImageView imgEscudo = v.findViewById(R.id.imgEscudo);

        TextView nPlayer = v.findViewById(R.id.nPlayer);
        final TextView def = v.findViewById(R.id.oDef);
        final TextView mei = v.findViewById(R.id.oMEI);
        final TextView ata = v.findViewById(R.id.oATA);

        Picasso.with(context).load(listTempA.get(i).getImgPerfil()).into(imgPlayer);

        imagensReduzidasEscudos();
        imgEscudo.setImageResource( images[ Integer.parseInt( listTempA.get(i).getImagem() ) ] );

        nPlayer.setText(listTempA.get(i).getNome());

        databaseReference.child("JogadoresCadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listGoleiros.clear();
                listDefesa.clear();
                listMeio.clear();
                listAtaque.clear();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    a = objSnapshot.getValue(Jogador.class);

                    if (a.getIdClube().equals(listTempA.get(i).getId())) {
                        if (a.getpJogador().equals("MAD") || a.getpJogador().equals("MAE") || a.getpJogador().equals("PD") || a.getpJogador().equals("PE") || a.getpJogador().equals("SA") || a.getpJogador().equals("ATA")) {
                            listAtaque.add(a);

                        }

                        if(a.getpJogador().equals("VOL") || a.getpJogador().equals("MC") || a.getpJogador().equals("MD") || a.getpJogador().equals("ME") || a.getpJogador().equals("MEI") ){
                            listMeio.add(a);

                        }

                        if( a.getpJogador().equals("ZAG") || a.getpJogador().equals("LD") || a.getpJogador().equals("LE") ){
                            listDefesa.add(a);

                        }

                        if(a.getpJogador().equals("GL") ){
                            listGoleiros.add(a);

                        }

                    }

                }

                Collections.sort(listGoleiros);
                Collections.sort(listDefesa);
                Collections.sort(listMeio);
                Collections.sort(listAtaque);


                NumberFormat nMc = NumberFormat.getInstance();
                nMc.setMaximumFractionDigits(2);

                float totalD = Integer.parseInt( listGoleiros.get(0).getoJogador()) + Integer.parseInt( listDefesa.get(0).getoJogador()) + Integer.parseInt( listDefesa.get(1).getoJogador()) + Integer.parseInt( listDefesa.get(2).getoJogador()) + Integer.parseInt( listDefesa.get(3).getoJogador());
                def.setText("DEF: " + nMc.format(totalD / 5) );

                float totalM = Integer.parseInt( listMeio.get(0).getoJogador()) + Integer.parseInt( listMeio.get(1).getoJogador()) + Integer.parseInt( listMeio.get(2).getoJogador()) + Integer.parseInt( listMeio.get(3).getoJogador());
                mei.setText("MEI: " + nMc.format(totalM / 4));

                float totalA = Integer.parseInt( listAtaque.get(0).getoJogador()) + Integer.parseInt( listAtaque.get(1).getoJogador()) + Integer.parseInt( listAtaque.get(2).getoJogador());
                ata.setText("ATA: " + nMc.format(totalA / 3));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        v.setTag(listTempA.get(i).getId());

        return v;
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

    public void inicializarFirebase(){
        FirebaseApp.initializeApp(context);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
