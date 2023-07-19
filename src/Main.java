import model.Carteira;
import model.Credenciais;
import model.Perfil;
import model.Usuario;

public class Main {
    public static void main(String[] args) {
        Perfil perfil = new Perfil("Fulano Silvano Da Selvageria", "Discente", "Ativo", "uri://example.com/pic.png");
        Carteira carteira = new Carteira("0000", 10,"ead3dsisbdshb456ujbdhjqjq776");
        Credenciais credenciais = new Credenciais("teste", "teste123");
        Usuario usuario = new Usuario(perfil, carteira, credenciais);
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