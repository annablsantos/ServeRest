package stub;

import org.example.dto.Usuario;

import java.util.UUID;

public class UsuarioStub {
    private String idUsuario;
    String gerarEmail = gerarEmailUnico();

    private String gerarEmailUnico(){
        UUID uuid = UUID.randomUUID();
        return "annab608_" + uuid + "@email.com";
    }
    public Usuario postUsuario(){
        Usuario joao = new Usuario(
                "João",
                gerarEmail,
                "Senha123@",
                "true");

        return joao;
    }
    public Usuario putUsuario(){
        Usuario joana = new Usuario(
                "Joana",
                gerarEmail,
                "SenhaDaJoana123@",
                "true");

        return joana;
    }
    public Usuario getUsuario(){
        Usuario maria = new Usuario(
                "Maria",
                "maria@email.com",
                "SenhaDaMaria123@",
                "true");

        return maria;
    }
    public String setIdUsuario(String idUsuario){
        return this.idUsuario = idUsuario;
    }
    public String getIdUsuario(){
        return idUsuario;
    }
}
