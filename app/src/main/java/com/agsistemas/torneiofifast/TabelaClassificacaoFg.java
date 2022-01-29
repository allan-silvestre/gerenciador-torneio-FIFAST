package com.agsistemas.torneiofifast;

public class TabelaClassificacaoFg implements Comparable<TabelaClassificacaoFg>{

    private String id = "0";
    private String status = "";
    private String imgPerfil = "0";
    private String imagem = "32";
    private String ranking = "0";
    private String time = "";
    private String player = "";
    private String pontos = "0";
    private String vit = "0";
    private String emp = "0";
    private String der = "0";
    private String gp = "0";
    private String gc = "0";
    private String sg = "0";
    private String posicaoTabela = "0";


    private String j1vit = "0";
    private String j1emp = "0";
    private String j1der = "0";
    private String j1gp = "0";
    private String j1gc = "0";



    private String j2vit = "0";
    private String j2emp = "0";
    private String j2der = "0";
    private String j2gp = "0";
    private String j2gc = "0";



    private String j3vit = "0";
    private String j3emp = "0";
    private String j3der = "0";
    private String j3gp = "0";
    private String j3gc = "0";



    private String j4vit = "0";
    private String j4emp = "0";
    private String j4der = "0";
    private String j4gp = "0";
    private String j4gc = "0";



    private String j5vit = "0";
    private String j5emp = "0";
    private String j5der = "0";
    private String j5gp = "0";
    private String j5gc = "0";



    private String j6vit = "0";
    private String j6emp = "0";
    private String j6der = "0";
    private String j6gp = "0";
    private String j6gc = "0";



    private String j7vit = "0";
    private String j7emp = "0";
    private String j7der = "0";
    private String j7gp = "0";
    private String j7gc = "0";


    private String j8vit = "0";
    private String j8emp = "0";
    private String j8der = "0";
    private String j8gp = "0";
    private String j8gc = "0";



    private String j9vit = "0";
    private String j9emp = "0";
    private String j9der = "0";
    private String j9gp = "0";
    private String j9gc = "0";



    private String j10vit = "0";
    private String j10emp = "0";
    private String j10der = "0";
    private String j10gp = "0";
    private String j10gc = "0";



    private String j11vit = "0";
    private String j11emp = "0";
    private String j11der = "0";
    private String j11gp = "0";
    private String j11gc = "0";



    private String j12vit = "0";
    private String j12emp = "0";
    private String j12der = "0";
    private String j12gp = "0";
    private String j12gc = "0";



    private String j13vit = "0";
    private String j13emp = "0";
    private String j13der = "0";
    private String j13gp = "0";
    private String j13gc = "0";



    private String j14vit = "0";
    private String j14emp = "0";
    private String j14der = "0";
    private String j14gp = "0";
    private String j14gc = "0";



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //usado só na hora de salvar o historico
    private String data;

    public String getData() {
        return data;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getPosicaoTabela() {
        return posicaoTabela;
    }

    public void setPosicaoTabela(String posicaoTabela) {
        this.posicaoTabela = posicaoTabela;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }




    public String getJ1vit() {
        return j1vit;
    }

    public void setJ1vit(String j1vit) {
        this.j1vit = j1vit;
    }

    public String getJ1emp() {
        return j1emp;
    }

    public void setJ1emp(String j1emp) {
        this.j1emp = j1emp;
    }

    public String getJ1der() {
        return j1der;
    }

    public void setJ1der(String j1der) {
        this.j1der = j1der;
    }

    public String getJ1gp() {
        return j1gp;
    }

    public void setJ1gp(String j1gp) {
        this.j1gp = j1gp;
    }

    public String getJ1gc() {
        return j1gc;
    }

    public void setJ1gc(String j1gc) {
        this.j1gc = j1gc;
    }

    public String getJ2vit() {
        return j2vit;
    }

    public void setJ2vit(String j2vit) {
        this.j2vit = j2vit;
    }

    public String getJ2emp() {
        return j2emp;
    }

    public void setJ2emp(String j2emp) {
        this.j2emp = j2emp;
    }

    public String getJ2der() {
        return j2der;
    }

    public void setJ2der(String j2der) {
        this.j2der = j2der;
    }

    public String getJ2gp() {
        return j2gp;
    }

    public void setJ2gp(String j2gp) {
        this.j2gp = j2gp;
    }

    public String getJ2gc() {
        return j2gc;
    }

    public void setJ2gc(String j2gc) {
        this.j2gc = j2gc;
    }

    public String getJ3vit() {
        return j3vit;
    }

    public void setJ3vit(String j3vit) {
        this.j3vit = j3vit;
    }

    public String getJ3emp() {
        return j3emp;
    }

    public void setJ3emp(String j3emp) {
        this.j3emp = j3emp;
    }

    public String getJ3der() {
        return j3der;
    }

    public void setJ3der(String j3der) {
        this.j3der = j3der;
    }

    public String getJ3gp() {
        return j3gp;
    }

    public void setJ3gp(String j3gp) {
        this.j3gp = j3gp;
    }

    public String getJ3gc() {
        return j3gc;
    }

    public void setJ3gc(String j3gc) {
        this.j3gc = j3gc;
    }

    public String getJ4vit() {
        return j4vit;
    }

    public void setJ4vit(String j4vit) {
        this.j4vit = j4vit;
    }

    public String getJ4emp() {
        return j4emp;
    }

    public void setJ4emp(String j4emp) {
        this.j4emp = j4emp;
    }

    public String getJ4der() {
        return j4der;
    }

    public void setJ4der(String j4der) {
        this.j4der = j4der;
    }

    public String getJ4gp() {
        return j4gp;
    }

    public void setJ4gp(String j4gp) {
        this.j4gp = j4gp;
    }

    public String getJ4gc() {
        return j4gc;
    }

    public void setJ4gc(String j4gc) {
        this.j4gc = j4gc;
    }

    public String getJ5vit() {
        return j5vit;
    }

    public void setJ5vit(String j5vit) {
        this.j5vit = j5vit;
    }

    public String getJ5emp() {
        return j5emp;
    }

    public void setJ5emp(String j5emp) {
        this.j5emp = j5emp;
    }

    public String getJ5der() {
        return j5der;
    }

    public void setJ5der(String j5der) {
        this.j5der = j5der;
    }

    public String getJ5gp() {
        return j5gp;
    }

    public void setJ5gp(String j5gp) {
        this.j5gp = j5gp;
    }

    public String getJ5gc() {
        return j5gc;
    }

    public void setJ5gc(String j5gc) {
        this.j5gc = j5gc;
    }

    public String getJ6vit() {
        return j6vit;
    }

    public void setJ6vit(String j6vit) {
        this.j6vit = j6vit;
    }

    public String getJ6emp() {
        return j6emp;
    }

    public void setJ6emp(String j6emp) {
        this.j6emp = j6emp;
    }

    public String getJ6der() {
        return j6der;
    }

    public void setJ6der(String j6der) {
        this.j6der = j6der;
    }

    public String getJ6gp() {
        return j6gp;
    }

    public void setJ6gp(String j6gp) {
        this.j6gp = j6gp;
    }

    public String getJ6gc() {
        return j6gc;
    }

    public void setJ6gc(String j6gc) {
        this.j6gc = j6gc;
    }

    public String getJ7vit() {
        return j7vit;
    }

    public void setJ7vit(String j7vit) {
        this.j7vit = j7vit;
    }

    public String getJ7emp() {
        return j7emp;
    }

    public void setJ7emp(String j7emp) {
        this.j7emp = j7emp;
    }

    public String getJ7der() {
        return j7der;
    }

    public void setJ7der(String j7der) {
        this.j7der = j7der;
    }

    public String getJ7gp() {
        return j7gp;
    }

    public void setJ7gp(String j7gp) {
        this.j7gp = j7gp;
    }

    public String getJ7gc() {
        return j7gc;
    }

    public void setJ7gc(String j7gc) {
        this.j7gc = j7gc;
    }

    public String getJ8vit() {
        return j8vit;
    }

    public void setJ8vit(String j8vit) {
        this.j8vit = j8vit;
    }

    public String getJ8emp() {
        return j8emp;
    }

    public void setJ8emp(String j8emp) {
        this.j8emp = j8emp;
    }

    public String getJ8der() {
        return j8der;
    }

    public void setJ8der(String j8der) {
        this.j8der = j8der;
    }

    public String getJ8gp() {
        return j8gp;
    }

    public void setJ8gp(String j8gp) {
        this.j8gp = j8gp;
    }

    public String getJ8gc() {
        return j8gc;
    }

    public void setJ8gc(String j8gc) {
        this.j8gc = j8gc;
    }

    public String getJ9vit() {
        return j9vit;
    }

    public void setJ9vit(String j9vit) {
        this.j9vit = j9vit;
    }

    public String getJ9emp() {
        return j9emp;
    }

    public void setJ9emp(String j9emp) {
        this.j9emp = j9emp;
    }

    public String getJ9der() {
        return j9der;
    }

    public void setJ9der(String j9der) {
        this.j9der = j9der;
    }

    public String getJ9gp() {
        return j9gp;
    }

    public void setJ9gp(String j9gp) {
        this.j9gp = j9gp;
    }

    public String getJ9gc() {
        return j9gc;
    }

    public void setJ9gc(String j9gc) {
        this.j9gc = j9gc;
    }

    public String getJ10vit() {
        return j10vit;
    }

    public void setJ10vit(String j10vit) {
        this.j10vit = j10vit;
    }

    public String getJ10emp() {
        return j10emp;
    }

    public void setJ10emp(String j10emp) {
        this.j10emp = j10emp;
    }

    public String getJ10der() {
        return j10der;
    }

    public void setJ10der(String j10der) {
        this.j10der = j10der;
    }

    public String getJ10gp() {
        return j10gp;
    }

    public void setJ10gp(String j10gp) {
        this.j10gp = j10gp;
    }

    public String getJ10gc() {
        return j10gc;
    }

    public void setJ10gc(String j10gc) {
        this.j10gc = j10gc;
    }

    public String getJ11vit() {
        return j11vit;
    }

    public void setJ11vit(String j11vit) {
        this.j11vit = j11vit;
    }

    public String getJ11emp() {
        return j11emp;
    }

    public void setJ11emp(String j11emp) {
        this.j11emp = j11emp;
    }

    public String getJ11der() {
        return j11der;
    }

    public void setJ11der(String j11der) {
        this.j11der = j11der;
    }

    public String getJ11gp() {
        return j11gp;
    }

    public void setJ11gp(String j11gp) {
        this.j11gp = j11gp;
    }

    public String getJ11gc() {
        return j11gc;
    }

    public void setJ11gc(String j11gc) {
        this.j11gc = j11gc;
    }

    public String getJ12vit() {
        return j12vit;
    }

    public void setJ12vit(String j12vit) {
        this.j12vit = j12vit;
    }

    public String getJ12emp() {
        return j12emp;
    }

    public void setJ12emp(String j12emp) {
        this.j12emp = j12emp;
    }

    public String getJ12der() {
        return j12der;
    }

    public void setJ12der(String j12der) {
        this.j12der = j12der;
    }

    public String getJ12gp() {
        return j12gp;
    }

    public void setJ12gp(String j12gp) {
        this.j12gp = j12gp;
    }

    public String getJ12gc() {
        return j12gc;
    }

    public void setJ12gc(String j12gc) {
        this.j12gc = j12gc;
    }

    public String getJ13vit() {
        return j13vit;
    }

    public void setJ13vit(String j13vit) {
        this.j13vit = j13vit;
    }

    public String getJ13emp() {
        return j13emp;
    }

    public void setJ13emp(String j13emp) {
        this.j13emp = j13emp;
    }

    public String getJ13der() {
        return j13der;
    }

    public void setJ13der(String j13der) {
        this.j13der = j13der;
    }

    public String getJ13gp() {
        return j13gp;
    }

    public void setJ13gp(String j13gp) {
        this.j13gp = j13gp;
    }

    public String getJ13gc() {
        return j13gc;
    }

    public void setJ13gc(String j13gc) {
        this.j13gc = j13gc;
    }

    public String getJ14vit() {
        return j14vit;
    }

    public void setJ14vit(String j14vit) {
        this.j14vit = j14vit;
    }

    public String getJ14emp() {
        return j14emp;
    }

    public void setJ14emp(String j14emp) {
        this.j14emp = j14emp;
    }

    public String getJ14der() {
        return j14der;
    }

    public void setJ14der(String j14der) {
        this.j14der = j14der;
    }

    public String getJ14gp() {
        return j14gp;
    }

    public void setJ14gp(String j14gp) {
        this.j14gp = j14gp;
    }

    public String getJ14gc() {
        return j14gc;
    }

    public void setJ14gc(String j14gc) {
        this.j14gc = j14gc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public String getVit() {
        return vit;
    }

    public void setVit(String vit) {
        this.vit = vit;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getDer() {
        return der;
    }

    public void setDer(String der) {
        this.der = der;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    @Override
    public int compareTo(TabelaClassificacaoFg o) {
        if(Integer.parseInt(this.ranking) < Integer.parseInt(o.getRanking())){
            return -1;
        } else if(Integer.parseInt(this.ranking) > Integer.parseInt(o.getRanking())){
            return 1;
        } else{
            return 0;
        }

    }
}
