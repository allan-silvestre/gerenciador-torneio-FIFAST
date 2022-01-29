package com.agsistemas.torneiofifast;

public class Artilheiro implements Comparable<Artilheiro>{

    String id;
    String jogador;
    String time;
    String imagemJogador;
    String imagemClube = "32";
    String gols;

    public String getImagemJogador() {
        return imagemJogador;
    }

    public void setImagemJogador(String imagemJogador) {
        this.imagemJogador = imagemJogador;
    }

    public String getImagemClube() {
        return imagemClube;
    }

    public void setImagemClube(String imagemClube) {
        this.imagemClube = imagemClube;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGols() {
        return gols;
    }

    public void setGols(String gols) {
        this.gols = gols;
    }

    @Override
    public int compareTo(Artilheiro o) {
        if(Integer.parseInt(this.gols) < Integer.parseInt(o.getGols())){
            return 1;
        } else if(Integer.parseInt(this.gols) > Integer.parseInt(o.getGols())){
            return -1;
        } else{
            return 0;
        }

    }
}
