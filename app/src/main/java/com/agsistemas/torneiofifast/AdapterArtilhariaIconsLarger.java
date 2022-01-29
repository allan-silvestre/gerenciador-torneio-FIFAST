package com.agsistemas.torneiofifast;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterArtilhariaIconsLarger extends BaseAdapter {

    private Context context;
    private List<Artilheiro> listArtilheiro;
    private List<Artilheiro> listArt;

    public AdapterArtilhariaIconsLarger(Context context, List<Artilheiro> listArtilheiro) {
        this.context = context;
        this.listArtilheiro = listArtilheiro;
    }

    final int[] images = new int[33];

    List<Integer> imagemJogadores = new ArrayList<>();

    @Override
    public int getCount() {
        return listArtilheiro.size();
    }

    @Override
    public Object getItem(int i) {
        return listArtilheiro.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_artilharia, null);

        TextView pos = v.findViewById(R.id.pos);
        ImageView imgJogador = v.findViewById(R.id.imgJogador);
        ImageView img = v.findViewById(R.id.imageView);
        ImageView vId = v.findViewById(R.id.viewId);
        TextView jog = v.findViewById(R.id.jogador);
        TextView gols = v.findViewById(R.id.gols);

        if(i == 0){

            ViewGroup.LayoutParams imgJ = (ViewGroup.LayoutParams) imgJogador.getLayoutParams();
            imgJ.width = 300;
            imgJ.height = 400;
            imgJogador.setLayoutParams(imgJ);

            ViewGroup.LayoutParams imgT = (ViewGroup.LayoutParams) img.getLayoutParams();
            imgT.width = 150;
            imgT.height = 150;
            img.setLayoutParams(imgT);

            gols.setTextSize(30);
            jog.setTextSize(15);
            vId.setImageResource(R.drawable.trofeuartilheiro);

            jog.setText( listArtilheiro.get(i).getJogador());

        } else if( i > 0){

            ViewGroup.LayoutParams imgJ = (ViewGroup.LayoutParams) imgJogador.getLayoutParams();
            imgJ.width = 200;
            imgJ.height = 200;
            imgJogador.setLayoutParams(imgJ);

            ViewGroup.LayoutParams imgT = (ViewGroup.LayoutParams) img.getLayoutParams();
            imgT.width = 100;
            imgT.height = 100;
            img.setLayoutParams(imgT);

            ViewGroup.LayoutParams vIdd = (ViewGroup.LayoutParams) vId.getLayoutParams();
            vIdd.width = 0;
            vIdd.height = 0;
            vId.setLayoutParams(vIdd);

            gols.setTextSize(30);
            jog.setTextSize(15);

            jog.setText(listArtilheiro.get(i).getJogador());

        }



        imagensEscudos();
        img.setImageResource( images[ Integer.parseInt( listArtilheiro.get(i).getImagemClube() ) ] );

        imagensJogador();
        imgJogador.setImageResource( imagemJogadores.get( Integer.parseInt( listArtilheiro.get(i).getImagemJogador() ) ) );

        //pos.setText(String.valueOf(i+1 + "ª"));

        gols.setText(listArtilheiro.get(i).getGols());

        v.setTag(listArtilheiro.get(i).getId());

        return v;
    }

    /*
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
*/

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

    private void imagensJogador(){
        CadastroJogador a = new CadastroJogador();
        for( int i = 0; i < a.jogadores.length+1; i++ ){

            imagemJogadores.add( context.getResources().getIdentifier("jogador" + i , "drawable", context.getPackageName() ) );

        }


    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Artilheiro> results = new ArrayList<Artilheiro>();
                if (listArt == null)
                    listArt = listArtilheiro;
                if (constraint != null) {
                    if (listArt != null && listArt.size() > 0) {
                        for (final Artilheiro g : listArt) {
                            if (g.getJogador().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listArtilheiro = (ArrayList<Artilheiro>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
