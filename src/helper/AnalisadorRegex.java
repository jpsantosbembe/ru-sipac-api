package helper;

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
}
