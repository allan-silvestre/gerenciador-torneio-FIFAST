package com.agsistemas.torneiofifast;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterClubesCadastrados extends BaseAdapter {

    private Context context;
    private List<Competidor> listClubesC;

    public AdapterClubesCadastrados(Context context, List<Competidor> listClubesC) {
        this.context = context;
        this.listClubesC = listClubesC;
    }

    final int[] images = new int[33];

    @Override
    public int getCount() {
        return listClubesC.size();
    }

    @Override
    public Object getItem(int i) {
        return listClubesC.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_clubes_cadastrados, null);

        TextView club = v.findViewById(R.id.clubesC);
        ImageView img = v.findViewById(R.id.imgClube);

        imagensEscudos();
        img.setImageResource( images[ Integer.parseInt( listClubesC.get(i).getImagem() ) ] );

        club.setText(listClubesC.get(i).getTime());
        v.setTag(listClubesC.get(i).getId());

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

}
