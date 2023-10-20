package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.dto.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import provider.UsuarioProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.IsNot.not;

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
    void testeCriandoUsuarioComSucesso() {
        Usuario usuario = usuarioProvider.postUsuario();
        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .statusCode(201)
                .body("_id", not(emptyOrNullString()));
    }
    @Test
    @DisplayName("Deve retornar erro caso um usuário possua um e-mail repetido.")
    void testeCriandoUsuarioComEmailRepetido() {
        Usuario usuario = usuarioProvider.getUsuario();
        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .statusCode(400);
    }
}
