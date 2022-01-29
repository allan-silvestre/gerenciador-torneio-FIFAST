package com.agsistemas.torneiofifast;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterJogosFg extends BaseAdapter {

    private Context context;
    private List<JogosFg> listJogos;

    public AdapterJogosFg(Context context, List<JogosFg> listJogos) {
        this.context = context;
        this.listJogos = listJogos;
    }

    final int[] images = new int[33];
    List<Integer> imgPlayers = new ArrayList<>();

    @Override
    public int getCount() {
        return listJogos.size();
    }

    @Override
    public Object getItem(int i) {
        return listJogos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_jogos_fg, null);

        ImageView escudoTimeC = v.findViewById(R.id.escudoTimeC);
        ImageView escudoTimeF = v.findViewById(R.id.escudoTimeF);
        ImageView imgPlayerC = v.findViewById(R.id.imgPlayerC);
        ImageView imgPlayerF = v.findViewById(R.id.imgPlayerF);

        TextView timeC = v.findViewById(R.id.timeC);
        TextView golsC = v.findViewById(R.id.golsC);
        TextView golsF = v.findViewById(R.id.golsF);
        TextView timeF = v.findViewById(R.id.timeF);

        if(i > 0 && i % 2 != 0 ){

            v.setBackgroundColor(0x603A3939);

        } else {
                v.setBackgroundColor(0x605C5A5A);

        }

        if(listJogos.get(i).getGolsC().equals("")){

        } else{
            if( Integer.parseInt(listJogos.get(i).getGolsC()) + Integer.parseInt(listJogos.get(i).getGolsPenaltC()) < Integer.parseInt(listJogos.get(i).getGolsF()) + Integer.parseInt(listJogos.get(i).getGolsPenaltF()) ){
                timeC.setBackgroundResource(R.drawable.aaa);
                timeF.setBackgroundResource(R.drawable.aaaaaaaaa);

            } else if( Integer.parseInt(listJogos.get(i).getGolsC()) + Integer.parseInt(listJogos.get(i).getGolsPenaltC()) > Integer.parseInt(listJogos.get(i).getGolsF()) + Integer.parseInt(listJogos.get(i).getGolsPenaltF()) ) {
                timeC.setBackgroundResource(R.drawable.aaaaaaaaa);
                timeF.setBackgroundResource(R.drawable.aaa);

            } else if( Integer.parseInt(listJogos.get(i).getGolsC()) + Integer.parseInt(listJogos.get(i).getGolsPenaltC()) == Integer.parseInt(listJogos.get(i).getGolsF()) + Integer.parseInt(listJogos.get(i).getGolsPenaltF()) ) {
                timeC.setBackgroundResource(R.drawable.aaaaa);
                timeF.setBackgroundResource(R.drawable.aaaaa);

            }
        }

        Picasso.with(context).load(listJogos.get(i).getImagemPlayerCasa()).into(imgPlayerC);
        Picasso.with(context).load(listJogos.get(i).getImagemPlayerFora()).into(imgPlayerF);

        imagensReduzidasEscudos();
        escudoTimeC.setImageResource( images[ Integer.parseInt( listJogos.get(i).getImagemTimeCasa() ) ] );
        escudoTimeF.setImageResource( images[ Integer.parseInt( listJogos.get(i).getImagemTimeFora() ) ] );

        timeC.setText(listJogos.get(i).getTimeCasa());
        golsC.setText(listJogos.get(i).getGolsC());
        golsF.setText(listJogos.get(i).getGolsF());
        timeF.setText(listJogos.get(i).getTimeFora());

        v.setTag(listJogos.get(i).getId());

        return v;
    }

    private void imgPlayer(){
        //CadastroCompetidorActivity a = new CadastroCompetidorActivity();
        for( int i = 0; i < 9; i++ ){

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

}
