package util;

import model.Usuario;
import okhttp3.Response;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    public void fazerLogin(Usuario usuario) throws IOException {
        String cookie = obterCookie();
        autenticarUsuario(usuario, cookie);
        String strResponseBody1 = obterPaginaSaldoCartaoRU(cookie);
        String strResponseBody2 = obterPaginaPerfilUsuario(cookie);

        System.out.println("--------------------------------------------------------------------------------------------");
        //System.out.println(strResponseBody);
        String regex = "<td\\s*.*>(\\s*.*)</td>";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(strResponseBody1);

        int count = 0;
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                //System.out.println("Group " + i + ": " + matcher.group(i) + " --> "+ count);
                switch (count) {
                    case 0: usuario.getPerfil().setNomeCompleto(matcher.group(i));
                    case 1: usuario.getCarteira().setCodigo(matcher.group(i));
                    case 2: usuario.getPerfil().setSituacaoDoVinculo(matcher.group(i));
                    case 3: usuario.getPerfil().setTipoDeVinculo(matcher.group(i));

                }
            }
            count++;
        }

        regex = "/sipac/QRCode\\?codigo=(.+)&tamanho=";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(strResponseBody1);

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                //System.out.println("Group " + i + ": " + matcher.group(i));
                usuario.getCarteira().setStrQRCode(matcher.group(i));
            }
        }

        regex = "<span\\s*.*>(\\d+)</span>";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(strResponseBody1);

        count = 0;
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                //System.out.println("Group " + i + ": " + matcher.group(i));
                if (count == 0) {
                    usuario.getCarteira().setSaldo(Integer.parseInt(matcher.group(i)));
                }
            }
            count++;
        }

        regex = "http[s]?[\\w\\S]+\\.jpg";
        pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(strResponseBody2);

        while (matcher.find()) {
            usuario.getPerfil().setURLFoto(matcher.group(0));
        }
    }
    private String obterCookie() throws IOException {
        HttpService httpService = new HttpService();
        Response response = httpService.fazerRequisicaoHttpGET(Endpoints.HOMEPAGE_SIPAC);
        int index = Objects.requireNonNull(response.headers().get("Set-Cookie")).indexOf(";");
        return Objects.requireNonNull(response.headers().get("Set-Cookie")).substring(0,index);
    }
    private void autenticarUsuario(Usuario usuario, String cookie) throws IOException {
        HttpService httpService = new HttpService();
        httpService.fazerRequisicaoHttpPOST(
                Endpoints.LOGON_SIPAC + cookie.toLowerCase(),
                "width=1920&height=1080&login=" + usuario.getCredenciais().getUsuario() + "&senha=" + usuario.getCredenciais().getSenha(),
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
