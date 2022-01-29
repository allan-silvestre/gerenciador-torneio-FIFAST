package com.agsistemas.torneiofifast;

public class Config {

    String formatoTorneio;
    String statusTorneio;
    String dataProxT;
    String versaoApp = "0";

    public String getVersaoApp() {
        return versaoApp;
    }

    public void setVersaoApp(String versaoApp) {
        this.versaoApp = versaoApp;
    }

    public String getDataProxT() {
        return dataProxT;
    }

    public void setDataProxT(String dataProxT) {
        this.dataProxT = dataProxT;
    }

    public String getFormatoTorneio() {
        return formatoTorneio;
    }

    public void setFormatoTorneio(String formatoTorneio) {
        this.formatoTorneio = formatoTorneio;
    }

    public String getStatusTorneio() {
        return statusTorneio;
    }

    public void setStatusTorneio(String statusTorneio) {
        this.statusTorneio = statusTorneio;
    }

    @Override
    public String toString() {
        return formatoTorneio;
    }
}
