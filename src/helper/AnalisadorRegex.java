package helper;

import model.HistoricoTransacoes;
import model.Transacao;
import util.ColecaoRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisadorRegex {
    public static String localizarOcorrencia(String regex, String texto) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(texto);
        if (matcher.find()){
            return matcher.group(1);
        }
        return null;
    }
    public static HistoricoTransacoes localizarTransacoes(String texto) {
        HistoricoTransacoes historico = new HistoricoTransacoes();
        final String regex = ColecaoRegex.HISTORICO_TRANSACOES;
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(texto);

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
        return historico;
    }
}
