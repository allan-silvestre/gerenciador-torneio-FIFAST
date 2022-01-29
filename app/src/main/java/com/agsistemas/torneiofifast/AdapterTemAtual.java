package com.agsistemas.torneiofifast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterTemAtual extends BaseAdapter {

    private Context context;
    private List<Competidor> listTempA;

    public AdapterTemAtual(Context context, List<Competidor> listTemA) {
        this.context = context;
        this.listTempA = listTemA;
    }

    List<Integer> imgStatusP = new ArrayList<>();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.custom_listview_temporada_atual, null);

        ImageView imgPlayer = v.findViewById(R.id.imgPlayer);
        ImageView img = v.findViewById(R.id.escudoTime);
        ImageView imgOuro = v.findViewById(R.id.imgOuro);
        ImageView imgPrata = v.findViewById(R.id.imgPrata);
        ImageView imgBronze = v.findViewById(R.id.imgBronze);

        TextView time = v.findViewById(R.id.time);
        TextView classficacao = v.findViewById(R.id.classificacao);
        TextView player = v.findViewById(R.id.playerId);
        TextView titulos = v.findViewById(R.id.titulosId);
        TextView prata = v.findViewById(R.id.prataId);
        TextView bronze = v.findViewById(R.id.bronzeId);
        TextView pts = v.findViewById(R.id.ptsId);

        if(i > 0 && i % 2 != 0 ){

            v.setBackgroundColor(0x603A3939);

        } else {
            if(i != 0){
                v.setBackgroundColor(0x605C5A5A);
            }

        }

        if(i > 0 && i < 4){

            if(i == 1){
                classficacao.setBackgroundColor(0xFF349E39);
            } else{
                classficacao.setBackgroundColor(0xFF305A0B);
            }

        } else if(i > 3 && i < 7){
            classficacao.setBackgroundColor(0xFF00796B);
        } else{
            //fundo.setImageResource(R.drawable.aaa);
        }


        if(i == 0 ){
            //imgPlayer.setImageResource(R.drawable.logo);
            classficacao.setVisibility(View.INVISIBLE);
            time.setText("");
            classficacao.setText("");
            player.setText("");
            pts.setText("PTS");
            pts.setTextSize(10);
            //v.setBackgroundColor(Color.BLACK);

            imgOuro.setImageResource(R.drawable.ouro);
            imgPrata.setImageResource(R.drawable.prata);
            imgBronze.setImageResource(R.drawable.bronze);

        }

        if(i > 0){

/*
            imgOuro.setImageResource(R.drawable.ouro);
            imgPrata.setImageResource(R.drawable.prata);
            imgBronze.setImageResource(R.drawable.bronze);

 */
            time.setText(listTempA.get(i).getNome());
            classficacao.setText(String.valueOf(i));
           // fundo.setImageResource(R.drawable.aaa);
            Picasso.with(context).load(listTempA.get(i).getImgPerfil()).into(imgPlayer);

            imagensReduzidasEscudos();
            img.setImageResource( images[ Integer.parseInt( listTempA.get(i).getImagem() ) ] );


            titulos.setText(listTempA.get(i).getTempAtualOuro());
            prata.setText(listTempA.get(i).getTempAtualPrata());
            bronze.setText(listTempA.get(i).getTempAtualBronze());

            if( Integer.parseInt( listTempA.get(i).getTempAtualPts() ) < 10 && Integer.parseInt( listTempA.get(i).getTempAtualPts() ) > 0){
                pts.setText("" + listTempA.get(i).getTempAtualPts());
            } else if(Integer.parseInt( listTempA.get(i).getTempAtualPts() ) == 0){
                pts.setText("0");
            } else{
                pts.setText(listTempA.get(i).getTempAtualPts());
            }

        }

        
        v.setTag(listTempA.get(i).getId());

        return v;
    }

    private void imgStatus(){
        CadastroCompetidorActivity a = new CadastroCompetidorActivity();
        for( int i = 0; i < a.times.length; i++ ){

            imgStatusP.add( context.getResources().getIdentifier("statusp" + i , "drawable", context.getPackageName() ) );

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
