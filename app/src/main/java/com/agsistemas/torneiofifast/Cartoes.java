package com.agsistemas.torneiofifast;

public class Cartoes {

    String id;
    String jogador;
    String time;
    String imagemJogador;
    String imagemClube = "32";
    String cAmarelo;
    String cVermelho;

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

    public String getcAmarelo() {
        return cAmarelo;
    }

    public void setcAmarelo(String cAmarelo) {
        this.cAmarelo = cAmarelo;
    }

    public String getcVermelho() {
        return cVermelho;
    }

    public void setcVermelho(String cVermelho) {
        this.cVermelho = cVermelho;
    }
}
