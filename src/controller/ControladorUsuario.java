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
        return new Usuario(perfil, carteira, credenciais);
    }
}
