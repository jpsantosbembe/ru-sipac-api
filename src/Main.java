import exception.ExcecaoErroDeConectividade;
import exception.ExcecaoUsuarioSemCadastroRU;
import exception.ExcecaoUsuarioSenhaInvalido;
import model.Credenciais;
import model.Transacao;
import util.Login;
import controller.ControladorUsuario;
import model.Usuario;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Credenciais credenciais = new Credenciais(
                args[0],
                args[1]
        );
        try {
            new Login().fazerLogin(credenciais);
        } catch (ExcecaoErroDeConectividade | ExcecaoUsuarioSenhaInvalido | ExcecaoUsuarioSemCadastroRU e) {
            throw new RuntimeException(e);
        }

        Usuario usuario = new ControladorUsuario().obterUsuario();

        System.out.println(usuario.getPerfil().getNomeCompleto());
        System.out.println(usuario.getPerfil().getMatricula());
        System.out.println(usuario.getPerfil().getTipoDeVinculo());
        System.out.println(usuario.getPerfil().getSituacaoDoVinculo());
        System.out.println(usuario.getPerfil().getURLFoto());
        System.out.println(usuario.getCarteira().getCodigo());
        System.out.println(usuario.getCarteira().getSaldo());
        System.out.println(usuario.getCarteira().getStrQRCode());
        System.out.println(usuario.getCredenciais().getUsuario());
        System.out.println(usuario.getCredenciais().getSenha());

        List<Transacao> todasTransacoes = usuario.getHistoricoTransacoes().getTransacoes();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-10s | %-42s | %-17s | %-20s | %-20s | %-15s | %-12s |%n",
                "DATA", "HORA", "OPERACAO", "CREDITOS GERADOS", "CREDITOS A RECEBER",
                "CREDITOS COMPENSADOS", "SALDO ANTERIOR", "SALDO ATUAL");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Transacao transacao : todasTransacoes) {
            System.out.printf("%-12s | %-10s | %-42s | %-17s | %-20s | %-20s | %-15s | %-12s |%n",
                    transacao.getData(), transacao.getHora(), transacao.getNomeOperacao(),
                    "        "+transacao.getCreditosGerados(), "          "+transacao.getCreditoReceber(),
                    "          "+transacao.getCreditoCompensado(), "        "+transacao.getSaldoAnterior(),
                    "      "+transacao.getSaldoAtual());
        }
    }
}