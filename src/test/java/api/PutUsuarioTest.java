package api;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.example.dto.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;
import stub.UsuarioStub;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PutUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private UsuarioStub usuarioStub;

    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioStub = new UsuarioStub();
    }

    @Test
    @DisplayName("Deve verificar se um usuário pode ser alterado.")
    void testeAlterandoUsuario(){
        Usuario usuario = usuarioStub.postUsuario();
        Response respostaPost =
        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios");

        String idDoUsuario = respostaPost.jsonPath().getString("_id");
        Usuario usuarioAlterado = usuarioStub.putUsuario();

        Response resposta =
        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(usuarioAlterado)
        .when()
                .put("/usuarios/" + idDoUsuario);

        assertEquals(200, resposta.getStatusCode());
        assertTrue(resposta.getBody().asString().contains("Registro alterado com sucesso"));

        Response respostaGet =
        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
        .when()
                .get("/usuarios/" + idDoUsuario);

        assertEquals(200, respostaGet.getStatusCode());
        assertEquals(usuarioAlterado.getNome(), respostaGet.jsonPath().getString("nome"));
        assertEquals(usuarioAlterado.getEmail(), respostaGet.jsonPath().getString("email"));
        assertEquals(usuarioAlterado.getSenha(), respostaGet.jsonPath().getString("password"));
    }
}
