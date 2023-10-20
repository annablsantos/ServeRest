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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private static UsuarioProvider usuarioProvider;
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioProvider = new UsuarioProvider();
    }
    @Test
    @DisplayName("Deve criar um novo usuário.")
    void testeCriandoUsuario() {
        Usuario usuario = usuarioProvider.postUsuario();
        Response resposta =
        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios");

        assertEquals(201, resposta.getStatusCode());
        usuarioProvider.setIdUsuario(resposta.jsonPath().getString("_id"));
    }
    @Test
    @DisplayName("Deve retornar erro caso um usuário possua um e-mail repetido.")
    void testeCriandoUsuarioComEmailRepetido() {
        Usuario usuario = usuarioProvider.getUsuario();
        Response resposta =
        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios");

        assertEquals(400, resposta.getStatusCode());
    }
}
