package model;

public class Transacao {
    private String data;
    private String hora;
    private String nomeOperacao;
    private String creditosGerados;
    private String creditoReceber;
    private String creditoCompensado;
    private String creditoAdiantado;
    private String saldoAnterior;
    private String saldoAtual;

    public Transacao(String data, String hora, String nomeOperacao, String creditosGerados, String creditoReceber, String creditoCompensado, String creditoAdiantado, String saldoAnterior, String saldoAtual) {
        this.data = data;
        this.hora = hora;
        this.nomeOperacao = nomeOperacao;
        this.creditosGerados = creditosGerados;
        this.creditoReceber = creditoReceber;
        this.creditoCompensado = creditoCompensado;
        this.creditoAdiantado = creditoAdiantado;
        this.saldoAnterior = saldoAnterior;
        this.saldoAtual = saldoAtual;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNomeOperacao() {
        return nomeOperacao;
    }

    public void setNomeOperacao(String nomeOperacao) {
        this.nomeOperacao = nomeOperacao;
    }

    public String getCreditosGerados() {
        return creditosGerados;
    }

    public void setCreditosGerados(String creditosGerados) {
        this.creditosGerados = creditosGerados;
    }

    public String getCreditoReceber() {
        return creditoReceber;
    }

    public void setCreditoReceber(String creditoReceber) {
        this.creditoReceber = creditoReceber;
    }

    public String getCreditoCompensado() {
        return creditoCompensado;
    }

    public void setCreditoCompensado(String creditoCompensado) {
        this.creditoCompensado = creditoCompensado;
    }

    public String getCreditoAdiantado() {
        return creditoAdiantado;
    }

    public void setCreditoAdiantado(String creditoAdiantado) {
        this.creditoAdiantado = creditoAdiantado;
    }

    public String getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(String saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(String saldoAtual) {
        this.saldoAtual = saldoAtual;
    }
}