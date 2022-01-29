package com.agsistemas.torneiofifast;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterTabelaFg extends BaseAdapter {


    private Context context;
    private List<TabelaClassificacaoFg> tabelaFg;

    public AdapterTabelaFg(Context context, List<TabelaClassificacaoFg> tabelaFg) {
        this.context = context;
        this.tabelaFg = tabelaFg;
    }

    final int[] images = new int[33];
    List<Integer> imgPlayers = new ArrayList<>();
    List<Integer> mAta = new ArrayList<>();
    List<Integer> mDef = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public int getCount() {
        return tabelaFg.size();
    }

    @Override
    public Object getItem(int i) {
        return tabelaFg.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_tabela_fg, null);

        inicializarFirebase();

        TextView pos = v.findViewById(R.id.pos);
        ImageView escudoTime = v.findViewById(R.id.escudoTime);
        ImageView imgPlayer = v.findViewById(R.id.imagemPlayer);
        ImageView estatitisca = v.findViewById(R.id.estatisticaId);
        ImageView estatitisca2 = v.findViewById(R.id.estatistica2);
        TextView time = v.findViewById(R.id.timeId);
        TextView player = v.findViewById(R.id.playerId);
        TextView pts = v.findViewById(R.id.ptsId);

        TextView vit = v.findViewById(R.id.vitId);
        TextView emp = v.findViewById(R.id.empId);
        TextView der = v.findViewById(R.id.derId);

        TextView gp = v.findViewById(R.id.gpId);
        TextView gc = v.findViewById(R.id.gcId);
        TextView sg = v.findViewById(R.id.sgId);

        if(i > 0 && i % 2 != 0 ){

            v.setBackgroundColor(0x603A3939);

        } else {
            if(i != 0){
                v.setBackgroundColor(0x605C5A5A);
            }

        }


        if(tabelaFg.size() < 6){

            if(i < 3){
                pos.setBackgroundColor(0xFF349E39);
            } else if(i > 2){
                //pos.setBackgroundColor(0xFF00796B);
            }

        } else if(tabelaFg.size() > 5){
            if(i<5){
               // fundo.setImageResource(R.drawable.aaaaaa);
                pos.setBackgroundColor(0xFF349E39);
            }else if(i>4 && i<7){
               // fundo.setImageResource(R.drawable.aaaaa);
                pos.setBackgroundColor(0xFF00796B);
            }

        }

        if(i == 0 ){

            pos.setVisibility(View.INVISIBLE);
           // v.setBackgroundColor(Color.BLACK);
            escudoTime.setVisibility(View.INVISIBLE);
            imgPlayer.setVisibility(View.INVISIBLE);

            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) escudoTime.getLayoutParams();
           // params.width = 0;
            params.height = 0;
            escudoTime.setLayoutParams(params);
            imgPlayer.setLayoutParams(params);

            estatitisca.setVisibility(View.INVISIBLE);

            time.setTextSize(14);
            time.setText(tabelaFg.get(i).getTime());
            player.setVisibility(View.GONE);
            player.setTextSize(10);
            pts.setTextSize(10);
            vit.setTextSize(10);
            emp.setTextSize(10);
            der.setTextSize(10);
            gp.setTextSize(10);
            gc.setTextSize(10);
            sg.setTextSize(10);
        }

        if(i > 0) {

            imagensReduzidasEscudos();
            imgPlayer();

            escudoTime.setImageResource(images[Integer.parseInt(tabelaFg.get(i).getImagem())]);
            //imgPlayer.setImageResource( imgPlayers.get( Integer.parseInt(tabelaFg.get(i).getImgPerfil()) ) );
            Picasso.with(context).load(tabelaFg.get(i).getImgPerfil()).into(imgPlayer);

            pos.setText(String.valueOf(i));
            time.setText(tabelaFg.get(i).getTime());
            player.setText(tabelaFg.get(i).getPlayer());
            pts.setText(tabelaFg.get(i).getPontos());

            vit.setText(tabelaFg.get(i).getVit());
            emp.setText(tabelaFg.get(i).getEmp());
            der.setText(tabelaFg.get(i).getDer());

            gp.setText(tabelaFg.get(i).getGp());
            gc.setText(tabelaFg.get(i).getGc());
            sg.setText(tabelaFg.get(i).getSg());


            v.setTag(tabelaFg.get(i).getId());

            if (i > 0) {

                //definir pior ataque
                mAta.add(Integer.parseInt(tabelaFg.get(i).getGp()));
                if (Integer.parseInt(tabelaFg.get(i).getGp()) == Collections.min(mAta)) {
                    estatitisca.setImageResource(R.drawable.ata2);
                }

                //definir pior defesa
                mDef.add(Integer.parseInt(tabelaFg.get(i).getGc()));
                if (Integer.parseInt(tabelaFg.get(i).getGc()) == Collections.max(mDef)) {
                    estatitisca2.setImageResource(R.drawable.def2);
                }

                //definir melhor ataque
                mAta.add(Integer.parseInt(tabelaFg.get(i).getGp()));
                if (Integer.parseInt(tabelaFg.get(i).getGp()) == Collections.max(mAta)) {
                    estatitisca.setImageResource(R.drawable.ata1);
                }

                //definir melhor defesa
                mDef.add(Integer.parseInt(tabelaFg.get(i).getGc()));
                if (Integer.parseInt(tabelaFg.get(i).getGc()) == Collections.min(mDef)) {
                    estatitisca2.setImageResource(R.drawable.def1);
                }

            }

        }


        return v;
    }


    private void imgPlayer(){
        CadastroCompetidorActivity a = new CadastroCompetidorActivity();
        for( int i = 0; i < a.times.length; i++ ){

            imgPlayers.add( context.getResources().getIdentifier("participante" + i , "drawable", context.getPackageName() ) );

        }


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
