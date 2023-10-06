package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import stub.UsuarioStub;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private UsuarioStub usuarioStub;

    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioStub = new UsuarioStub();
    }

    @Test
    @DisplayName("Deve deletar um usuário.")
    void testeDeletandoUsuario() {
        Usuario usuario = usuarioStub.postUsuario();
        Response respostaPost =
        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios");

        assertEquals(201, respostaPost.getStatusCode());
        String idUsuario = respostaPost.jsonPath().getString("_id");

        Response delete =
                given()
                .when()
                .delete("/usuarios/" + idUsuario);

        assertEquals(200, delete.getStatusCode());
        assertTrue(delete.getBody().asString().contains("Registro excluído com sucesso"));
    }

    @Test
    @DisplayName("Deve tentar deletar um usuário inexistente")
    void testeDeletandoUsuarioInexistente() {
        String idUsuario = usuarioStub.getIdUsuario();
        Response resposta =
                given()
                .when()
                .delete("/usuarios/" + idUsuario);

        assertEquals(200, resposta.getStatusCode());

        String mensagem = resposta.jsonPath().getString("message");
        assertEquals("Nenhum registro excluído", mensagem);
    }
}
