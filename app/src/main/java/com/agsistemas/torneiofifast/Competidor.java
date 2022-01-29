package com.agsistemas.torneiofifast;

public class Competidor implements Comparable<Competidor>{

    private String id = "0";
    private String macLogado = "0";
    private String nivelAcesso = "PADRÃO";
    private String imgPerfil = "https://firebasestorage.googleapis.com/v0/b/torneiofifadb.appspot.com/o/participante8.png?alt=media&token=a9f7bf79-e160-49b7-9edd-e2785267c98f";
    private String imagem = "32";
    private String nome = "";
    private String sobrenome = "";
    private String time = "";
    private String senha = "";
    private String ordem = "0";
    private String status = "ATIVO";
    private String TempAtualOuro = "0";
    private String TempAtualPrata = "0";
    private String TempAtualBronze = "0";
    private String TempAtualPts = "0";
    private String statusP = "3";
    private String GeralOuro = "";
    private String GeralPrata = "";
    private String GeralBronze = "";
    private String formacaoPadrao = "4-4-2";

    private String p1Img = "0";
    private String p1Over = "0";
    private String p1Id = "0";

    private String p2Img = "0";
    private String p2Over = "0";
    private String p2Id = "0";

    private String p3Img = "0";
    private String p3Over = "0";
    private String p3Id = "0";

    private String p4Img = "0";
    private String p4Over = "0";
    private String p4Id = "0";

    private String p5Img = "0";
    private String p5Over = "0";
    private String p5Id = "0";

    private String p6Img = "0";
    private String p6Over = "0";
    private String p6Id = "0";

    private String p7Img = "0";
    private String p7Over = "0";
    private String p7Id = "0";

    private String p8Img = "0";
    private String p8Over = "0";
    private String p8Id = "0";

    private String p9Img = "0";
    private String p9Over = "0";
    private String p9Id = "0";

    private String p10Img = "0";
    private String p10Over = "0";
    private String p10Id = "0";

    private String p11Img = "0";
    private String p11Over = "0";
    private String p11Id = "0";

    private String p12Img = "0";
    private String p12Over = "0";
    private String p12Id = "0";

    private String p13Img = "0";
    private String p13Over = "0";
    private String p13Id = "0";

    private String p14Img = "0";
    private String p14Over = "0";
    private String p14Id = "0";

    private String p15Img = "0";
    private String p15Over = "0";
    private String p15Id = "0";

    private String p16Img = "0";
    private String p16Over = "0";
    private String p16Id = "0";

    private String p17Img = "0";
    private String p17Over = "0";
    private String p17Id = "0";

    private String p18Img = "0";
    private String p18Over = "0";
    private String p18Id = "0";

    private String p19Img = "0";
    private String p19Over = "0";
    private String p19Id = "0";


    public String getMacLogado() {
        return macLogado;
    }

    public void setMacLogado(String macLogado) {
        this.macLogado = macLogado;
    }

    public String getP1Id() {
        return p1Id;
    }

    public void setP1Id(String p1Id) {
        this.p1Id = p1Id;
    }

    public String getP2Id() {
        return p2Id;
    }

    public void setP2Id(String p2Id) {
        this.p2Id = p2Id;
    }

    public String getP3Id() {
        return p3Id;
    }

    public void setP3Id(String p3Id) {
        this.p3Id = p3Id;
    }

    public String getP4Id() {
        return p4Id;
    }

    public void setP4Id(String p4Id) {
        this.p4Id = p4Id;
    }

    public String getP5Id() {
        return p5Id;
    }

    public void setP5Id(String p5Id) {
        this.p5Id = p5Id;
    }

    public String getP6Id() {
        return p6Id;
    }

    public void setP6Id(String p6Id) {
        this.p6Id = p6Id;
    }

    public String getP7Id() {
        return p7Id;
    }

    public void setP7Id(String p7Id) {
        this.p7Id = p7Id;
    }

    public String getP8Id() {
        return p8Id;
    }

    public void setP8Id(String p8Id) {
        this.p8Id = p8Id;
    }

    public String getP9Id() {
        return p9Id;
    }

    public void setP9Id(String p9Id) {
        this.p9Id = p9Id;
    }

    public String getP10Id() {
        return p10Id;
    }

    public void setP10Id(String p10Id) {
        this.p10Id = p10Id;
    }

    public String getP11Id() {
        return p11Id;
    }

    public void setP11Id(String p11Id) {
        this.p11Id = p11Id;
    }

    public String getP12Id() {
        return p12Id;
    }

    public void setP12Id(String p12Id) {
        this.p12Id = p12Id;
    }

    public String getP13Id() {
        return p13Id;
    }

    public void setP13Id(String p13Id) {
        this.p13Id = p13Id;
    }

    public String getP14Id() {
        return p14Id;
    }

    public void setP14Id(String p14Id) {
        this.p14Id = p14Id;
    }

    public String getP15Id() {
        return p15Id;
    }

    public void setP15Id(String p15Id) {
        this.p15Id = p15Id;
    }

    public String getP16Id() {
        return p16Id;
    }

    public void setP16Id(String p16Id) {
        this.p16Id = p16Id;
    }

    public String getP17Id() {
        return p17Id;
    }

    public void setP17Id(String p17Id) {
        this.p17Id = p17Id;
    }

    public String getP18Id() {
        return p18Id;
    }

    public void setP18Id(String p18Id) {
        this.p18Id = p18Id;
    }

    public String getP19Id() {
        return p19Id;
    }

    public void setP19Id(String p19Id) {
        this.p19Id = p19Id;
    }

    public String getP1Img() {
        return p1Img;
    }

    public void setP1Img(String p1Img) {
        this.p1Img = p1Img;
    }

    public String getP1Over() {
        return p1Over;
    }

    public void setP1Over(String p1Over) {
        this.p1Over = p1Over;
    }

    public String getP2Img() {
        return p2Img;
    }

    public void setP2Img(String p2Img) {
        this.p2Img = p2Img;
    }

    public String getP2Over() {
        return p2Over;
    }

    public void setP2Over(String p2Over) {
        this.p2Over = p2Over;
    }


    public String getP3Img() {
        return p3Img;
    }

    public void setP3Img(String p3Img) {
        this.p3Img = p3Img;
    }

    public String getP3Over() {
        return p3Over;
    }

    public void setP3Over(String p3Over) {
        this.p3Over = p3Over;
    }


    public String getP4Img() {
        return p4Img;
    }

    public void setP4Img(String p4Img) {
        this.p4Img = p4Img;
    }

    public String getP4Over() {
        return p4Over;
    }

    public void setP4Over(String p4Over) {
        this.p4Over = p4Over;
    }


    public String getP5Img() {
        return p5Img;
    }

    public void setP5Img(String p5Img) {
        this.p5Img = p5Img;
    }

    public String getP5Over() {
        return p5Over;
    }

    public void setP5Over(String p5Over) {
        this.p5Over = p5Over;
    }


    public String getP6Img() {
        return p6Img;
    }

    public void setP6Img(String p6Img) {
        this.p6Img = p6Img;
    }

    public String getP6Over() {
        return p6Over;
    }

    public void setP6Over(String p6Over) {
        this.p6Over = p6Over;
    }


    public String getP7Img() {
        return p7Img;
    }

    public void setP7Img(String p7Img) {
        this.p7Img = p7Img;
    }

    public String getP7Over() {
        return p7Over;
    }

    public void setP7Over(String p7Over) {
        this.p7Over = p7Over;
    }


    public String getP8Img() {
        return p8Img;
    }

    public void setP8Img(String p8Img) {
        this.p8Img = p8Img;
    }

    public String getP8Over() {
        return p8Over;
    }

    public void setP8Over(String p8Over) {
        this.p8Over = p8Over;
    }

    public String getP9Img() {
        return p9Img;
    }

    public void setP9Img(String p9Img) {
        this.p9Img = p9Img;
    }

    public String getP9Over() {
        return p9Over;
    }

    public void setP9Over(String p9Over) {
        this.p9Over = p9Over;
    }

    public String getP10Img() {
        return p10Img;
    }

    public void setP10Img(String p10Img) {
        this.p10Img = p10Img;
    }

    public String getP10Over() {
        return p10Over;
    }

    public void setP10Over(String p10Over) {
        this.p10Over = p10Over;
    }

    public String getP11Img() {
        return p11Img;
    }

    public void setP11Img(String p11Img) {
        this.p11Img = p11Img;
    }

    public String getP11Over() {
        return p11Over;
    }

    public void setP11Over(String p11Over) {
        this.p11Over = p11Over;
    }

    public String getP12Img() {
        return p12Img;
    }

    public void setP12Img(String p12Img) {
        this.p12Img = p12Img;
    }

    public String getP12Over() {
        return p12Over;
    }

    public void setP12Over(String p12Over) {
        this.p12Over = p12Over;
    }

    public String getP13Img() {
        return p13Img;
    }

    public void setP13Img(String p13Img) {
        this.p13Img = p13Img;
    }

    public String getP13Over() {
        return p13Over;
    }

    public void setP13Over(String p13Over) {
        this.p13Over = p13Over;
    }

    public String getP14Img() {
        return p14Img;
    }

    public void setP14Img(String p14Img) {
        this.p14Img = p14Img;
    }

    public String getP14Over() {
        return p14Over;
    }

    public void setP14Over(String p14Over) {
        this.p14Over = p14Over;
    }

    public String getP15Img() {
        return p15Img;
    }

    public void setP15Img(String p15Img) {
        this.p15Img = p15Img;
    }

    public String getP15Over() {
        return p15Over;
    }

    public void setP15Over(String p15Over) {
        this.p15Over = p15Over;
    }

    public String getP16Img() {
        return p16Img;
    }

    public void setP16Img(String p16Img) {
        this.p16Img = p16Img;
    }

    public String getP16Over() {
        return p16Over;
    }

    public void setP16Over(String p16Over) {
        this.p16Over = p16Over;
    }

    public String getP17Img() {
        return p17Img;
    }

    public void setP17Img(String p17Img) {
        this.p17Img = p17Img;
    }

    public String getP17Over() {
        return p17Over;
    }

    public void setP17Over(String p17Over) {
        this.p17Over = p17Over;
    }

    public String getP18Img() {
        return p18Img;
    }

    public void setP18Img(String p18Img) {
        this.p18Img = p18Img;
    }

    public String getP18Over() {
        return p18Over;
    }

    public void setP18Over(String p18Over) {
        this.p18Over = p18Over;
    }

    public String getP19Img() {
        return p19Img;
    }

    public void setP19Img(String p19Img) {
        this.p19Img = p19Img;
    }

    public String getP19Over() {
        return p19Over;
    }

    public void setP19Over(String p19Over) {
        this.p19Over = p19Over;
    }

    public void setFormacaoPadrao(String formacaoPadrao) {
        this.formacaoPadrao = formacaoPadrao;
    }

    public String getFormacaoPadrao() {
        return formacaoPadrao;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getGeralOuro() {
        return GeralOuro;
    }

    public void setGeralOuro(String geralOuro) {
        GeralOuro = geralOuro;
    }

    public String getGeralPrata() {
        return GeralPrata;
    }

    public void setGeralPrata(String geralPrata) {
        GeralPrata = geralPrata;
    }

    public String getGeralBronze() {
        return GeralBronze;
    }

    public void setGeralBronze(String geralBronze) {
        GeralBronze = geralBronze;
    }

    public String getStatusP() {
        return statusP;
    }

    public void setStatusP(String statusP) {
        this.statusP = statusP;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public String getTempAtualOuro() {
        return TempAtualOuro;
    }

    public void setTempAtualOuro(String tempAtualOuro) {
        TempAtualOuro = tempAtualOuro;
    }

    public String getTempAtualPrata() {
        return TempAtualPrata;
    }

    public void setTempAtualPrata(String tempAtualPrata) {
        TempAtualPrata = tempAtualPrata;
    }

    public String getTempAtualBronze() {
        return TempAtualBronze;
    }

    public void setTempAtualBronze(String tempAtualBronze) {
        TempAtualBronze = tempAtualBronze;
    }

    public String getTempAtualPts() {
        return TempAtualPts;
    }

    public void setTempAtualPts(String tempAtualPts) {
        TempAtualPts = tempAtualPts;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome + " / " + time + " Ord: " + ordem;
    }


    @Override
    public int compareTo(Competidor o) {
        if(Integer.parseInt(this.TempAtualPts) < Integer.parseInt(o.getTempAtualPts())){
            return 1;
        } else if(Integer.parseInt(this.TempAtualPts) > Integer.parseInt(o.getTempAtualPts())){
            return -1;
        } else{
            return 0;
        }

    }

}
