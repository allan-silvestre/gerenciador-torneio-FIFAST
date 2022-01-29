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

public class AdapterHistoricoTorneio extends BaseAdapter {

    private Context context;
    private List<Historico> listH;

    public AdapterHistoricoTorneio(Context context, List<Historico> listH) {
        this.context = context;
        this.listH = listH;
    }

    List<Integer> imgPlayers = new ArrayList<>();
    final int[] images = new int[33];

    @Override
    public int getCount() {
        return listH.size();
    }

    @Override
    public Object getItem(int i) {
        return listH.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_historico, null);

        TextView dataId = v.findViewById(R.id.dataId);

        TextView playerOuro = v.findViewById(R.id.playerOuro);
        TextView playerPrata = v.findViewById(R.id.playerPrata);
        TextView playerBronze = v.findViewById(R.id.playerBronze);

        ImageView imgOuro = v.findViewById(R.id.imgPlayerOuro);
        ImageView imgPrata = v.findViewById(R.id.imgPlayerPrata);
        ImageView imgBronze = v.findViewById(R.id.imgPlayerBronze);

        ImageView escudoOuro = v.findViewById(R.id.escudoOuro);
        ImageView escudoPrata = v.findViewById(R.id.escudoPrata);
        ImageView escudoBronze = v.findViewById(R.id.escudoBronze);

        // set texto
        dataId.setText("VENCEDORES DO TORNEIO REALIZADO NO DIA " + listH.get(i).getData());

        playerOuro.setText(listH.get(i).getPlayerOuro());
        playerOuro.setText(listH.get(i).getPlayerOuro());
        playerPrata.setText(listH.get(i).getPlayerPrata());
        playerBronze.setText(listH.get(i).getPlayerBronze());

        //set img
        imgPlayer();
        Picasso.with(context).load(listH.get(i).getImgPlayerOuro()).into(imgOuro);
        Picasso.with(context).load(listH.get(i).getImgPlayerPrata()).into(imgPrata);
        Picasso.with(context).load(listH.get(i).getImgPlayerBronze()).into(imgBronze);

        //imgOuro.setImageResource( imgPlayers.get( Integer.parseInt( listH.get(i).getImgPlayerOuro() ) ) );
        //imgPrata.setImageResource( imgPlayers.get( Integer.parseInt( listH.get(i).getImgPlayerPrata() ) ) );
        //imgBronze.setImageResource( imgPlayers.get( Integer.parseInt( listH.get(i).getImgPlayerBronze() ) ) );

        imagensEscudos();
        escudoOuro.setImageResource( images[ Integer.parseInt( listH.get(i).getImgOuro() ) ] );
        escudoPrata.setImageResource( images[ Integer.parseInt( listH.get(i).getImgPrata() ) ] );
        escudoBronze.setImageResource( images[ Integer.parseInt( listH.get(i).getImgBronze() ) ] );

        return v;
    }

    private void imgPlayer(){
        CadastroCompetidorActivity a = new CadastroCompetidorActivity();
        for( int i = 0; i < a.times.length; i++ ){

            imgPlayers.add( context.getResources().getIdentifier("participante" + i , "drawable", context.getPackageName() ) );

        }


    }

    public void imagensEscudos(){

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
}

