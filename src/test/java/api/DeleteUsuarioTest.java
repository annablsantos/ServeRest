package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.dto.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import provider.UsuarioProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private static UsuarioProvider usuarioProvider;
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioProvider = new UsuarioProvider();
    }

    @Test
    @DisplayName("Deve deletar um usuário som sucesso.")
    void testeDeletandoUsuarioComSucesso() {
        Usuario usuario = usuarioProvider.postUsuario();
        Response respostaPost = usuarioProvider.criarUsuario(usuario);

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .delete("/usuarios/" + respostaPost.jsonPath().getString("_id"))
        .then()
                .statusCode(200)
                .body("message", containsString("Registro excluído com sucesso"));
    }
    @Test
    @DisplayName("Deve retornar uma mensagem ao deletar um usuário inexistente")
    void testeDeletandoUsuarioInexistente() {
        Usuario usuario = usuarioProvider.postUsuario();
        Response respostaPost = usuarioProvider.criarUsuario(usuario);

        String idUsuario = respostaPost.jsonPath().getString("_id");

        usuario.setIdUsuario(idUsuario);

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .delete("/usuarios/222222222222222" + usuario.getIdUsuario())
        .then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"));
    }
}
