package model;

public class Usuario {
    private static Usuario instance;
    private Perfil perfil;
    private Carteira carteira;
    private Credenciais credenciais;
    private Usuario(Perfil perfil, Carteira carteira, Credenciais credenciais) {
        this.perfil = perfil;
        this.carteira = carteira;
        this.credenciais = credenciais;
    }
    public static Usuario getInstance(Perfil perfil, Carteira carteira, Credenciais credenciais) {
        if (instance == null) {
            instance = new Usuario(perfil, carteira, credenciais);
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
}
