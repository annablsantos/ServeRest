package stub;

import org.example.model.Usuario;

public class UsuarioStub {
    private String idUsuario;
    public Usuario postUsuario(){
        Usuario joao = new Usuario(
                "João",
                "joao@email.com",
                "Senha123@",
                "true");

        return joao;
    }
    public Usuario putUsuario(){
        Usuario joana = new Usuario(
                "Joana",
                "joana@email.com",
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
