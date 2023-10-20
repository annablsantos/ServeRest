package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.dto.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import provider.UsuarioProvider;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private static UsuarioProvider usuarioProvider;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioProvider = new UsuarioProvider();
    }

    @Test
    @DisplayName("Deve deletar um usuário.")
    void testeDeletandoUsuario() {
        Usuario usuario = usuarioProvider.postUsuario();
        Response respostaPost = usuarioProvider.criarUsuario(usuario);

        assertEquals(201, respostaPost.getStatusCode());
        String idUsuario = respostaPost.jsonPath().getString("_id");

        Response delete = given().when().delete("/usuarios/" + idUsuario);

        assertEquals(200, delete.getStatusCode());
        assertTrue(delete.getBody().asString().contains("Registro excluído com sucesso"));
    }

    @Test
    @DisplayName("Deve tentar deletar um usuário inexistente")
    void testeDeletandoUsuarioInexistente() {
        String idUsuario = usuarioProvider.getIdUsuario();
        Response resposta =
                given()
                .when()
                .delete("/usuarios/" + idUsuario);

        assertEquals(200, resposta.getStatusCode());

        String mensagem = resposta.jsonPath().getString("message");
        assertEquals("Nenhum registro excluído", mensagem);
    }
}
