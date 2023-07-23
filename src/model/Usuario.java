package model;

public class Usuario {
    private static Usuario instance;
    private Perfil perfil;
    private Carteira carteira;
    private Credenciais credenciais;
    private HistoricoTransacoes historicoTransacoes;
    private Usuario(Perfil perfil, Carteira carteira, Credenciais credenciais, HistoricoTransacoes historicoTransacoes) {
        this.perfil = perfil;
        this.carteira = carteira;
        this.credenciais = credenciais;
        this.historicoTransacoes = historicoTransacoes;
    }
    public static Usuario getInstance(Perfil perfil, Carteira carteira, Credenciais credenciais, HistoricoTransacoes historicoTransacoes) {
        if (instance == null) {
            instance = new Usuario(perfil, carteira, credenciais, historicoTransacoes);
        }
        return instance;
    }
    public Perfil getPerfil() {
        return perfil;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    public Carteira getCarteira() {
        return carteira;
    }
    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }
    public Credenciais getCredenciais() {
        return credenciais;
    }
    public void setCredenciais(Credenciais credenciais) {
        this.credenciais = credenciais;
    }

    public HistoricoTransacoes getHistoricoTransacoes() {
        return historicoTransacoes;
    }

    public void setHistoricoTransacoes(HistoricoTransacoes historicoTransacoes) {
        this.historicoTransacoes = historicoTransacoes;
    }
}
