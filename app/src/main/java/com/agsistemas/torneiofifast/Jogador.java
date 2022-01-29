package com.agsistemas.torneiofifast;

public class Jogador implements Comparable<Jogador>{

    private String idJogador;
    private String idClube;
    private String imagem;
    private String imgClubeAssociado;
    private String nJogador;
    private String cJogador;
    private String pJogador;
    private String oJogador;
    private String velocidade = "";
    private String finalizacao = "";
    private String passe = "";
    private String drible = "";
    private String defesa = "";
    private String fisico = "";
    private String urlJ = "";

    private String reflexo = "";
    private String elasticidade = "";
    private String manejo = "";
    private String chute = "";
    private String posicionamento = "";

    public String getReflexo() {
        return reflexo;
    }

    public void setReflexo(String reflexo) {
        this.reflexo = reflexo;
    }

    public String getElasticidade() {
        return elasticidade;
    }

    public void setElasticidade(String elasticidade) {
        this.elasticidade = elasticidade;
    }

    public String getManejo() {
        return manejo;
    }

    public void setManejo(String manejo) {
        this.manejo = manejo;
    }

    public String getChute() {
        return chute;
    }

    public void setChute(String chute) {
        this.chute = chute;
    }

    public String getPosicionamento() {
        return posicionamento;
    }

    public void setPosicionamento(String posicionamento) {
        this.posicionamento = posicionamento;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
    }

    public String getFinalizacao() {
        return finalizacao;
    }

    public void setFinalizacao(String finalizacao) {
        this.finalizacao = finalizacao;
    }

    public String getPasse() {
        return passe;
    }

    public void setPasse(String passe) {
        this.passe = passe;
    }

    public String getDrible() {
        return drible;
    }

    public void setDrible(String drible) {
        this.drible = drible;
    }

    public String getDefesa() {
        return defesa;
    }

    public void setDefesa(String defesa) {
        this.defesa = defesa;
    }

    public String getFisico() {
        return fisico;
    }

    public void setFisico(String fisico) {
        this.fisico = fisico;
    }

    public String getUrlJ() {
        return urlJ;
    }

    public void setUrlJ(String urlJ) {
        this.urlJ = urlJ;
    }

    public String getImgClubeAssociado() {
        return imgClubeAssociado;
    }

    public void setImgClubeAssociado(String imgClubeAssociado) {
        this.imgClubeAssociado = imgClubeAssociado;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getIdClube() {
        return idClube;
    }

    public void setIdClube(String idClube) {
        this.idClube = idClube;
    }

    public String getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(String idJogador) {
        this.idJogador = idJogador;
    }

    public String getnJogador() {
        return nJogador;
    }

    public void setnJogador(String nJogador) {
        this.nJogador = nJogador;
    }

    public String getcJogador() {
        return cJogador;
    }

    public void setcJogador(String cJogador) {
        this.cJogador = cJogador;
    }

    public String getpJogador() {
        return pJogador;
    }

    public void setpJogador(String pJogador) {
        this.pJogador = pJogador;
    }

    public String getoJogador() {
        return oJogador;
    }

    public void setoJogador(String oJogador) {
        this.oJogador = oJogador;
    }


    @Override
    public int compareTo(Jogador o) {
        if(Integer.parseInt(this.oJogador) < Integer.parseInt(o.getoJogador())){
            return 1;
        } else if(Integer.parseInt(this.oJogador) > Integer.parseInt(o.getoJogador())){
            return -1;
        } else{
            return 0;
        }

    }
}
