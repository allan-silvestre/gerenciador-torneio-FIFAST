package com.agsistemas.torneiofifast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterCartoes extends BaseAdapter {

    private Context context;
    private List<Cartoes> listCartoes;

    public AdapterCartoes(Context context, List<Cartoes> listCartoes) {
        this.context = context;
        this.listCartoes = listCartoes;
    }

    final int[] images = new int[33];

    List<Integer> imagemJogadores = new ArrayList<>();

    @Override
    public int getCount() {
        return listCartoes.size();
    }

    @Override
    public Object getItem(int i) {
        return listCartoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_cartoes, null);

        ImageView imgJogador = v.findViewById(R.id.imgJogador);
        ImageView img = v.findViewById(R.id.imageView);
        TextView jog = v.findViewById(R.id.jogador);
        TextView aId = v.findViewById(R.id.amarelo);
        TextView vId = v.findViewById(R.id.vermelho);

        imagensReduzidasEscudos();
        img.setImageResource( images[ Integer.parseInt( listCartoes.get(i).getImagemClube() ) ] );

        imagensJogador();
        imgJogador.setImageResource( imagemJogadores.get( Integer.parseInt( listCartoes.get(i).getImagemJogador() ) ) );

        jog.setText(listCartoes.get(i).getJogador());

        aId.setText(listCartoes.get(i).getcAmarelo());
        vId.setText(listCartoes.get(i).getcVermelho());

        if(aId.getText().toString().equals("0")){
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) aId.getLayoutParams();
            params.width = 0;
            params.height = 0;
            aId.setLayoutParams(params);
            aId.setVisibility(View.INVISIBLE);
        }

        if(vId.getText().toString().equals("0")){
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) vId.getLayoutParams();
            params.width = 0;
            params.height = 0;
            vId.setLayoutParams(params);
            vId.setVisibility(View.INVISIBLE);
        }

        v.setTag(listCartoes.get(i).getId());

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

    private void imagensJogador(){
        CadastroJogador a = new CadastroJogador();
        for( int i = 0; i < a.jogadores.length+1; i++ ){

            imagemJogadores.add( context.getResources().getIdentifier("jogador" + i , "drawable", context.getPackageName() ) );

        }


    }

}
