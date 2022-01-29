package com.agsistemas.torneiofifast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterArtilharia extends BaseAdapter {

    private Context context;
    private List<Artilheiro> listArtilheiro;
    private List<Artilheiro> listArt;

    public AdapterArtilharia(Context context, List<Artilheiro> listArtilheiro) {
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
        TextView jog = v.findViewById(R.id.jogador);
        TextView gols = v.findViewById(R.id.gols);


        imagensReduzidasEscudos();
        img.setImageResource( images[ Integer.parseInt( listArtilheiro.get(i).getImagemClube() ) ] );

        imagensJogador();
        imgJogador.setImageResource( imagemJogadores.get( Integer.parseInt( listArtilheiro.get(i).getImagemJogador() ) ) );

        pos.setText(String.valueOf(i+1 + "ª"));

        jog.setText(listArtilheiro.get(i).getJogador());
        gols.setText(listArtilheiro.get(i).getGols());

        v.setTag(listArtilheiro.get(i).getId());

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
        images[32] = R.drawable.reduzidovillarreal;

    }

    private void imagensJogador(){
        CadastroJogador a = new CadastroJogador();
        for( int i = 0; i < a.jogadores.length+1; i++ ){

            imagemJogadores.add( context.getResources().getIdentifier("rjogador" + i , "drawable", context.getPackageName() ) );

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
