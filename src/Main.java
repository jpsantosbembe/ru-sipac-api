import model.Credenciais;
import util.Login;
import controller.ControladorUsuario;
import model.Usuario;

public class Main {
    public static void main(String[] args) {
        Credenciais credenciais = new Credenciais(
                args[0],
                args[1]
        );
        try {
            new Login().fazerLogin(credenciais);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Usuario usuario = new ControladorUsuario().obterUsuario();

        System.out.println(usuario.getPerfil().getNomeCompleto());
        System.out.println(usuario.getPerfil().getTipoDeVinculo());
        System.out.println(usuario.getPerfil().getSituacaoDoVinculo());
        System.out.println(usuario.getPerfil().getURLFoto());
        System.out.println(usuario.getCarteira().getCodigo());
        System.out.println(usuario.getCarteira().getSaldo());
        System.out.println(usuario.getCarteira().getStrQRCode());
        System.out.println(usuario.getCredenciais().getUsuario());
        System.out.println(usuario.getCredenciais().getSenha());
    }
}