package util;

import controller.ControladorUsuario;
import helper.AnalisadorRegex;
import model.Credenciais;
import okhttp3.Response;
import java.io.IOException;
import java.util.Objects;

public class Login{
    public void fazerLogin(Credenciais credenciais) throws Exception {
        String cookie = obterCookie();
        String paginaAuth = autenticarUsuario(credenciais, cookie);
        String strResponseBody1 = obterPaginaSaldoCartaoRU(cookie);
        String strResponseBody2 = obterPaginaPerfilUsuario(cookie);

        if (AnalisadorRegex.localizarOcorrencia(ColecaoRegex.USUARIO_SENHA_INVALIDO, paginaAuth) != null) {
            throw new Exception("Usuario e/ou senha invalidos");
        }

        String nomeCompleto;
        try {
            nomeCompleto = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.NOME_COMPLETO, strResponseBody1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String codigo;
        try {
            codigo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.CODIGO, strResponseBody1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String situacaoDoVinculo;
        try {
            situacaoDoVinculo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.SITUACAO, strResponseBody1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String tipoDeVinculo;
        try {
            tipoDeVinculo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.TIPO_DE_VINCULO, strResponseBody1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int saldo;
        try {
            saldo = Integer.parseInt(Objects.requireNonNull(AnalisadorRegex.localizarOcorrencia(ColecaoRegex.SALDO, strResponseBody1)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String strQRCode;
        try {
            strQRCode = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.STRING_QRCODE, strResponseBody1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String URLPerfil;
        try {
            URLPerfil = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.FOTO_DE_PERFIL, strResponseBody2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        new ControladorUsuario().criarNovoUsuario(
                nomeCompleto,
                tipoDeVinculo,
                situacaoDoVinculo,
                URLPerfil,
                codigo,
                saldo,
                strQRCode,
                credenciais.getUsuario(),
                credenciais.getSenha()
        );
    }
    private String obterCookie() throws IOException {
        HttpService httpService = new HttpService();
        Response response = httpService.fazerRequisicaoHttpGET(Endpoints.PAGINA_INICIAL_SIPAC);
        int index = Objects.requireNonNull(response.headers().get("Set-Cookie")).indexOf(";");
        return Objects.requireNonNull(response.headers().get("Set-Cookie")).substring(0,index);
    }
    private String autenticarUsuario(Credenciais credenciais, String cookie) throws IOException {
        HttpService httpService = new HttpService();
        return httpService.fazerRequisicaoHttpPOST(
                Endpoints.LOGON_SIPAC + cookie.toLowerCase(),
                "width=1920&height=1080&login=" + credenciais.getUsuario() + "&senha=" + credenciais.getSenha(),
                cookie);
    }
    private String obterPaginaSaldoCartaoRU(String cookie) throws IOException {
        HttpService httpService = new HttpService();
        return httpService.fazerRequisicaoHttpPOST(Endpoints.SALDO_RU_SIPAC,
                "formmenuadm=formmenuadm&jscook_action=formmenuadm_menuaaluno_menu%3AA%5D%23%7BsaldoCartao.iniciar%7D&javax.faces.ViewState=j_id1",
                cookie);
    }
    private String obterPaginaPerfilUsuario(String cookie) throws IOException {
        HttpService httpService = new HttpService();
        return httpService.fazerRequisicaoHttpGET(Endpoints.PORTAL_DO_ALUNO_SIPAC, cookie);
    }
}
