package controller;

import model.Carteira;
import model.Credenciais;
import model.Perfil;
import model.Usuario;

public class ControladorUsuario {
    public Usuario criarNovoUsuario(String nomeCompleto, String tipoDeVinculo, String situacaoDoVinculo, String URLFoto,
                                    String codigoCarteira, int saldoCarteira, String strQRCodeCarteira,
                                    String nomeUsuario, String senhaUsuario) {
        Perfil perfil = new Perfil(nomeCompleto, tipoDeVinculo, situacaoDoVinculo, URLFoto);
        Carteira carteira = new Carteira(codigoCarteira, saldoCarteira, strQRCodeCarteira);
        Credenciais credenciais = new Credenciais(nomeUsuario, senhaUsuario);
        return Usuario.getInstance(perfil, carteira, credenciais);
    }
    public Usuario obterUsuario() {
        return Usuario.getInstance(null, null, null);
    }
    public String consultarNomeUsuario (Usuario usuario) {
        return usuario.getPerfil().getNomeCompleto ();
    }
    public String consultarTipoDeVinculo (Usuario usuario) {
        return usuario.getPerfil().getTipoDeVinculo();
    }
    public String consultarSituacaoDoVinculo (Usuario usuario) {
        return usuario.getPerfil().getSituacaoDoVinculo();
    }
    public String consultarURLFoto (Usuario usuario) {
        return usuario.getPerfil().getURLFoto ();
    }
    public String consultarCodigoCarteira (Usuario usuario) {
        return usuario.getCarteira().getCodigo();
    }
    public double consultarSaldoCarteira (Usuario usuario) {
        return usuario.getCarteira ().getSaldo ();
    }
    public String consultarStrQRCodeCarteira (Usuario usuario) {
        return usuario.getCarteira ().getStrQRCode ();
    }
    public void atualizarSaldoCarteira (Usuario usuario, int novoSaldo) {
        usuario.getCarteira ().setSaldo (novoSaldo);
    }
}
