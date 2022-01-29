package com.agsistemas.torneiofifast;


public class JogosFg {

    private String tituloPartida = "";
    private String keyGrupo = "";
    private String keyGrupoPartida;
    private String id = "";
    private String idTimeCasa = "";
    private String idTimeFora = "";
    private String statusTimeCasa = "";
    private String statusTimeFora = "";
    private String imagemTimeCasa = "32";
    private String imagemTimeFora = "32";
    private String imagemPlayerCasa = "0";
    private String imagemPlayerFora = "0";
    private String timeCnPartida = "";
    private String timeFnPartida = "";
    private String timeCasa = "vazio";
    private String timeFora = "vazio";
    private String golsC = "";
    private String golsF = "";
    private String golsPenaltC = "0";
    private String golsPenaltF = "0";

    public String getStatusTimeCasa() {
        return statusTimeCasa;
    }

    public void setStatusTimeCasa(String statusTimeCasa) {
        this.statusTimeCasa = statusTimeCasa;
    }

    public String getStatusTimeFora() {
        return statusTimeFora;
    }

    public void setStatusTimeFora(String statusTimeFora) {
        this.statusTimeFora = statusTimeFora;
    }

    public String getImagemPlayerCasa() {
        return imagemPlayerCasa;
    }

    public void setImagemPlayerCasa(String imagemPlayerCasa) {
        this.imagemPlayerCasa = imagemPlayerCasa;
    }

    public String getImagemPlayerFora() {
        return imagemPlayerFora;
    }

    public void setImagemPlayerFora(String imagemPlayerFora) {
        this.imagemPlayerFora = imagemPlayerFora;
    }

    public String getImagemTimeCasa() {
        return imagemTimeCasa;
    }

    public void setImagemTimeCasa(String imagemTimeCasa) {
        this.imagemTimeCasa = imagemTimeCasa;
    }

    public String getImagemTimeFora() {
        return imagemTimeFora;
    }

    public void setImagemTimeFora(String imagemTimeFora) {
        this.imagemTimeFora = imagemTimeFora;
    }

    public String getGolsPenaltC() {
        return golsPenaltC;
    }

    public void setGolsPenaltC(String golsPenaltC) {
        this.golsPenaltC = golsPenaltC;
    }

    public String getGolsPenaltF() {
        return golsPenaltF;
    }

    public void setGolsPenaltF(String golsPenaltF) {
        this.golsPenaltF = golsPenaltF;
    }

    public String getTituloPartida() {
        return tituloPartida;
    }

    public void setTituloPartida(String tituloPartida) {
        this.tituloPartida = tituloPartida;
    }

    public String getKeyGrupoPartida() {
        return keyGrupoPartida;
    }

    public void setKeyGrupoPartida(String keyGrupoPartida) {
        this.keyGrupoPartida = keyGrupoPartida;
    }

    public String getTimeCnPartida() {
        return timeCnPartida;
    }

    public void setTimeCnPartida(String timeCnPartida) {
        this.timeCnPartida = timeCnPartida;
    }

    public String getTimeFnPartida() {
        return timeFnPartida;
    }

    public void setTimeFnPartida(String timeFnPartida) {
        this.timeFnPartida = timeFnPartida;
    }

    public String getTimeFora() {
        return timeFora;
    }

    public void setTimeFora(String timeFora) {
        this.timeFora = timeFora;
    }

    public String getIdTimeCasa() {
        return idTimeCasa;
    }

    public void setIdTimeCasa(String idTimeCasa) {
        this.idTimeCasa = idTimeCasa;
    }

    public String getIdTimeFora() {
        return idTimeFora;
    }

    public void setIdTimeFora(String idTimeFora) {
        this.idTimeFora = idTimeFora;
    }

    public String getKeyGrupo() {
        return keyGrupo;
    }

    public void setKeyGrupo(String keyGrupo) {
        this.keyGrupo = keyGrupo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(String timeCasa) {
        this.timeCasa = timeCasa;
    }

    public String getGolsC() {
        return golsC;
    }

    public void setGolsC(String golsC) {
        this.golsC = golsC;
    }

    public String getGolsF() {
        return golsF;
    }

    public void setGolsF(String golsF) {
        this.golsF = golsF;
    }

    @Override
    public String toString() {
        return timeCasa + " " + golsC + " X " + golsF + " " + timeFora;
    }

}
