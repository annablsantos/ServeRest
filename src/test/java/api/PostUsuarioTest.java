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
public class PostUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private UsuarioStub usuarioStub;
    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioStub = new UsuarioStub();
    }
    @Test
    @DisplayName("Deve criar um novo usuário")
    void testCadastrarNovoUsuario() {
        Usuario novoUsuario = usuarioStub.postUsuario();

        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(novoUsuario)
                .when()
                .post("/usuarios");

        assertEquals(201, response.getStatusCode());

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Cadastro realizado com sucesso"));

        usuarioStub.setIdUsuario(response.jsonPath().getString("_id"));
        System.out.println(usuarioStub.getIdUsuario());
    }
}
