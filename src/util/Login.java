package util;

import controller.ControladorUsuario;
import exception.ExcecaoErroDeConectividade;
import exception.ExcecaoUsuarioSenhaInvalido;
import helper.AnalisadorRegex;
import model.Credenciais;
import model.HistoricoTransacoes;
import model.Transacao;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login{
    public void fazerLogin(Credenciais credenciais) throws ExcecaoUsuarioSenhaInvalido, ExcecaoErroDeConectividade {

        String cookie = obterCookie();
        autenticarUsuario(credenciais, cookie);
        String strResponseBody1 = obterPaginaSaldoCartaoRU(cookie);
        String strResponseBody2 = obterPaginaPerfilUsuario(cookie);
        System.out.println(strResponseBody1);

        HistoricoTransacoes historico = new HistoricoTransacoes();

        final String regex = "<tr class=\\\"\\w+\\\">\\s*<td><\\/td>\\s*<td style=\\\"text-align: center;\\\">\\s*(\\d{2}\\/\\d{2}\\/\\d{4})&nbsp;(\\d{2}:\\d{2})\\s*<\\/td>\\s*<td style=\\\"text-align: left;\\\" nowrap=\\\"nowrap\\\">\\s*((?:.+?)(?:&\\w+;)(?:.+))\\s+(?:\\(almo&ccedil;o\\))?\\s*<\\/td>\\s*<td style=\\\"text-align: right;\\\">\\s*<!-- 0 = TipoCompraCredito\\.COMPRA_GRU \\s*2 = TipoCompraCredito\\.Compra_PRESENCIAL-->\\s*(\\d+)\\s*<\\/td>\\s*(?:<!-- TipoCompraCredito\\.COMPENSACAO_GRU -->)?\\s*<td style=\\\"text-align: right;\\\">(\\d+)<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s+(\\d+)\\s+<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s*(\\d+)\\s*<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s*(\\d+)\\s*<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s*(\\d+)\\s*<\\/td>\\s*<\\/tr>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(strResponseBody1);

        while (matcher.find()) {
            Transacao transacao = new Transacao(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4),
                    matcher.group(5),
                    matcher.group(6),
                    matcher.group(7),
                    matcher.group(8),
                    matcher.group(9));
            historico.adicionarTransacao(transacao);
        }

        List<Transacao> todasTransacoes = historico.getTransacoes();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-10s | %-42s | %-17s | %-20s | %-20s | %-15s | %-12s%n",
                "DATA", "HORA", "OPERACAO", "CREDITOS GERADOS", "CREDITOS A RECEBER",
                "CREDITOS COMPENSADOS", "SALDO ANTERIOR", "SALDO ATUAL");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Transacao transacao : todasTransacoes) {
            System.out.printf("%-12s | %-10s | %-42s | %-17s | %-20s | %-20s | %-15s | %-12s%n",
                    transacao.getData(), transacao.getHora(), transacao.getNomeOperacao(),
                    "        "+transacao.getCreditosGerados(), "          "+transacao.getCreditoReceber(),
                    "          "+transacao.getCreditoCompensado(), "        "+transacao.getSaldoAnterior(),
                    "        "+transacao.getSaldoAtual());
        }

        String nomeCompleto;
        nomeCompleto = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.NOME_COMPLETO, strResponseBody1);
        String codigo;
        codigo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.CODIGO, strResponseBody1);
        String situacaoDoVinculo;
        situacaoDoVinculo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.SITUACAO, strResponseBody1);
        String tipoDeVinculo;
        tipoDeVinculo = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.TIPO_DE_VINCULO, strResponseBody1);
        int saldo;
        saldo = Integer.parseInt(Objects.requireNonNull(AnalisadorRegex.localizarOcorrencia(ColecaoRegex.SALDO, strResponseBody1)));
        String strQRCode;
        strQRCode = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.STRING_QRCODE, strResponseBody1);
        String URLPerfil;
        URLPerfil = AnalisadorRegex.localizarOcorrencia(ColecaoRegex.FOTO_DE_PERFIL, strResponseBody2);

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
    private String obterCookie() throws ExcecaoErroDeConectividade{
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            try (Response response = servicoHttp.fazerRequisicaoHttpGET(Endpoints.PAGINA_INICIAL_SIPAC)) {
                int index = Objects.requireNonNull(response.headers().get("Set-Cookie")).indexOf(";");
                return Objects.requireNonNull(response.headers().get("Set-Cookie")).substring(0, index);
            }
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
    private void autenticarUsuario(Credenciais credenciais, String cookie) throws ExcecaoErroDeConectividade, ExcecaoUsuarioSenhaInvalido {
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            String respostaSipac = servicoHttp.fazerRequisicaoHttpPOST(
                    Endpoints.LOGON_SIPAC + cookie.toLowerCase(),
                    "width=1920&height=1080&login=" + credenciais.getUsuario() + "&senha=" + credenciais.getSenha(),
                    cookie);
            if (AnalisadorRegex.localizarOcorrencia(ColecaoRegex.USUARIO_SENHA_INVALIDO, respostaSipac) != null) {
                throw new ExcecaoUsuarioSenhaInvalido();
            }
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
    private String obterPaginaSaldoCartaoRU(String cookie) throws ExcecaoErroDeConectividade {
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            return servicoHttp.fazerRequisicaoHttpPOST(Endpoints.SALDO_RU_SIPAC,
                    "formmenuadm=formmenuadm&jscook_action=formmenuadm_menuaaluno_menu%3AA%5D%23%7BsaldoCartao.iniciar%7D&javax.faces.ViewState=j_id1",
                    cookie);
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
    private String obterPaginaPerfilUsuario(String cookie) throws ExcecaoErroDeConectividade {
        try {
            ServicoHttp servicoHttp = new ServicoHttp();
            return servicoHttp.fazerRequisicaoHttpGET(Endpoints.PORTAL_DO_ALUNO_SIPAC, cookie);
        } catch (IOException e) {
            throw new ExcecaoErroDeConectividade();
        }

    }
}
