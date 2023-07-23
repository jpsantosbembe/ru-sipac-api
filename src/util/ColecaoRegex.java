package util;

public class ColecaoRegex {
    public static final String NOME_COMPLETO = "<th\\s*.*>Nome:</th>\\s\\W+<td\\s*.*>(\\s*.*)</td>";
    public static final String CODIGO = "<th\\s*.*>C&#243;digo:</th>\\s\\W+<td\\s*.*>(\\s*.*)</td>";
    public static final String SITUACAO = "<th\\s*.*>Situa&#231;&#227;o:</th>\\s\\W+<td\\s*.*>(\\s*.*)</td>";
    public static final String TIPO_DE_VINCULO = "<th\\s*.*>Tipo de V&#237;nculo:</th>\\s\\W+<td\\s*.*>(\\s*.*)</td>";
    public static final String SALDO = "<th\\s*.*>Total de Refei&#231;&#245;es:</th>\\s\\W+<td>\\s\\W+<span\\s*.*>(\\d+)</span>\\s\\W+</td>";
    public static final String STRING_QRCODE = "/sipac/QRCode\\?codigo=(.+)&tamanho=";
    public static final String FOTO_DE_PERFIL = "(http[s]?[\\w\\S]+\\.jpg)";
    public static final String USUARIO_SENHA_INVALIDO = "<b>\\s+(Usuário e\\/ou senha inválidos)\\s+<\\/b>";
    public static final String HISTORICO_TRANSACOES = "<tr class=\\\"\\w+\\\">\\s*<td><\\/td>\\s*<td style=\\\"text-align: center;\\\">\\s*(\\d{2}\\/\\d{2}\\/\\d{4})&nbsp;(\\d{2}:\\d{2})\\s*<\\/td>\\s*<td style=\\\"text-align: left;\\\" nowrap=\\\"nowrap\\\">\\s*((?:.+?)(?:&\\w+;)(?:.+))\\s+(?:\\(almo&ccedil;o\\))?\\s*<\\/td>\\s*<td style=\\\"text-align: right;\\\">\\s*<!-- 0 = TipoCompraCredito\\.COMPRA_GRU \\s*2 = TipoCompraCredito\\.Compra_PRESENCIAL-->\\s*(\\d+)\\s*<\\/td>\\s*(?:<!-- TipoCompraCredito\\.COMPENSACAO_GRU -->)?\\s*<td style=\\\"text-align: right;\\\">(\\d+)<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s+(\\d+)\\s+<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s*(\\d+)\\s*<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s*(\\d+)\\s*<\\/td>\\s*<td style=\\\"text-align: right; background-color: #ffff[ce][6c];\\\">\\s*(\\d+)\\s*<\\/td>\\s*<\\/tr>";

}
