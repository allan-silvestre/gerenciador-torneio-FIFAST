package com.agsistemas.torneiofifast;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterUtmTransf extends BaseAdapter {

    private Context context;
    private List<UltimasTransf> listT;

    public AdapterUtmTransf(Context context, List<UltimasTransf> listTransf) {
        this.context = context;
        this.listT = listTransf;
    }

    final int[] images = new int[33];
    List<Integer> imagemJogadores = new ArrayList<>();

    @Override
    public int getCount() {
        return listT.size();
    }

    @Override
    public Object getItem(int i) {
        return listT.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_ultimas_transf, null);

        ImageView imgJ = v.findViewById(R.id.imgJ);
        ImageView imgAntC = v.findViewById(R.id.imgAntC);
        ImageView imgNovoC = v.findViewById(R.id.imgNovoC);
        ImageView seta = v.findViewById(R.id.seta);

        TextView nJ = v.findViewById(R.id.nJ);
        TextView pJ = v.findViewById(R.id.pJ);
        TextView oJ = v.findViewById(R.id.oJ);
        TextView nomeAntC = v.findViewById(R.id.nomeAntC);
        TextView nomeNovoC = v.findViewById(R.id.nomeNovoC);
        TextView data = v.findViewById(R.id.data);

        imagensEscudos();
        imagensJogador();
        imgJ.setImageResource( imagemJogadores.get( Integer.parseInt( listT.get(i).getImgJogador() ) ) );
        seta.setImageResource(R.drawable.seta);
        imgAntC.setImageResource( images[Integer.parseInt( listT.get(i).getImgAntClube() )] );
        imgNovoC.setImageResource( images[Integer.parseInt( listT.get(i).getImgClube() )] );

        nJ.setText(listT.get(i).getNomeJogador());
        pJ.setText(listT.get(i).getPosJogador());
        oJ.setText(listT.get(i).getOverJogador());
        nomeAntC.setText(listT.get(i).getAntClube());
        nomeNovoC.setText(listT.get(i).getNovoClubeJogador());


        data.setText("DATA DA TRANSFERÊNCIA: " + listT.get(i).getDataHora());

        v.setTag(listT.get(i).getIdJogador());

        return v;
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

    private void imagensJogador(){
        CadastroJogador a = new CadastroJogador();
        for( int i = 0; i < a.jogadores.length; i++ ){

            imagemJogadores.add( context.getResources().getIdentifier("jogador" + i , "drawable", context.getPackageName() ) );

        }


    }


}

