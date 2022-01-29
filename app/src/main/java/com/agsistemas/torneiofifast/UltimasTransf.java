package com.agsistemas.torneiofifast;

public class UltimasTransf implements Comparable<UltimasTransf>{

    String id;
    String idJogador;
    String novaIdClube;
    String imgJogador;
    String imgClube;
    String nomeJogador;
    String posJogador;
    String overJogador;
    String novoClubeJogador;
    String dataHora;
    String numeroNotify;

    String antClube;
    String imgAntClube;

    public String getNumeroNotify() {
        return numeroNotify;
    }

    public void setNumeroNotify(String numeroNotify) {
        this.numeroNotify = numeroNotify;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getImgAntClube() {
        return imgAntClube;
    }

    public void setImgAntClube(String imgAntClube) {
        this.imgAntClube = imgAntClube;
    }

    public String getId() {
        return id;
    }

    public String getAntClube() {
        return antClube;
    }

    public void setAntClube(String antClube) {
        this.antClube = antClube;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(String idJogador) {
        this.idJogador = idJogador;
    }

    public String getNovaIdClube() {
        return novaIdClube;
    }

    public void setNovaIdClube(String novaIdClube) {
        this.novaIdClube = novaIdClube;
    }

    public String getImgJogador() {
        return imgJogador;
    }

    public void setImgJogador(String imgJogador) {
        this.imgJogador = imgJogador;
    }

    public String getImgClube() {
        return imgClube;
    }

    public void setImgClube(String imgClube) {
        this.imgClube = imgClube;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public String getPosJogador() {
        return posJogador;
    }

    public void setPosJogador(String posJogador) {
        this.posJogador = posJogador;
    }

    public String getOverJogador() {
        return overJogador;
    }

    public void setOverJogador(String overJogador) {
        this.overJogador = overJogador;
    }

    public String getNovoClubeJogador() {
        return novoClubeJogador;
    }

    public void setNovoClubeJogador(String novoClubeJogador) {
        this.novoClubeJogador = novoClubeJogador;
    }

    @Override
    public int compareTo(UltimasTransf o) {
        if(Integer.parseInt(this.dataHora) < Integer.parseInt(o.getDataHora())){
            return 1;
        } else if(Integer.parseInt(this.dataHora) > Integer.parseInt(o.getDataHora())){
            return -1;
        } else{
            return 0;
        }

    }
}
