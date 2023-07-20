package controller;

import model.Usuario;
import okhttp3.Response;
import util.HttpService;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorLogin {
    public void fazerLogin(Usuario usuario) throws IOException {
        HttpService httpService = new HttpService();
        Response response = httpService.fazerRequisicaoHttpGET("https://sipac.ufopa.edu.br/sipac/");
        int index = response.headers().get("Set-Cookie").indexOf(";");
        String cookie = response.headers().get("Set-Cookie").substring(0,index);
        //System.out.println(cookie);

        httpService.fazerRequisicaoHttpPOST("https://sipac.ufopa.edu.br/sipac/logon.do;" + cookie.toLowerCase(),
                "width=1920&height=1080&login=" + usuario.getCredenciais().getUsuario() + "&senha=" + usuario.getCredenciais().getSenha(),
                cookie);

        String strResponseBody1 = httpService.fazerRequisicaoHttpPOST("https://sipac.ufopa.edu.br/sipac/portal_aluno/index.jsf",
                "formmenuadm=formmenuadm&jscook_action=formmenuadm_menuaaluno_menu%3AA%5D%23%7BsaldoCartao.iniciar%7D&javax.faces.ViewState=j_id1",
                cookie);

        String strResponseBody2 = httpService.fazerRequisicaoHttpGET("https://sipac.ufopa.edu.br/sipac/portal_aluno/index.jsf", cookie);
        //System.out.println(strResponseBody2);

        System.out.println("--------------------------------------------------------------------------------------------");
        //System.out.println(strResponseBody);
        String regex = "<td\\s*.*>(\\s*.*)<\\/td>";
        final String string = strResponseBody1;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(string);

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
        matcher = pattern.matcher(string);

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                //System.out.println("Group " + i + ": " + matcher.group(i));
                usuario.getCarteira().setStrQRCode(matcher.group(i));
            }
        }

        regex = "<span\\s*.*>(\\d+)<\\/span>";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(string);

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
}
