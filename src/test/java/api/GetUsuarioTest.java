package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.dto.Usuario;
import provider.UsuarioProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private static UsuarioProvider usuarioProvider;
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioProvider = new UsuarioProvider();
    }
    @Test
    @DisplayName("Deve listar os usuários com sucesso.")
    void testeBuscandoUsuariosComSucesso() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/usuarios")
        .then()
                .statusCode(200)
                .body("usuarios[0].nome", not(empty()))
                .body("usuarios[0].email", not(empty()))
                .body("usuarios[0].administrador", equalTo("true"))
                .body("usuarios[0].password", not(empty()));
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo ID.")
    void testeBuscandoUsuarioPeloId() {
        Usuario usuario = usuarioProvider.postUsuario();
        Response respostaGet = usuarioProvider.criarUsuario(usuario);

        String idUsuario = respostaGet.jsonPath().getString("_id");

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/usuarios/" + idUsuario)
        .then()
                .statusCode(200)
                .body("nome", equalTo(usuario.getNome()))
                .body("email", equalTo(usuario.getEmail()))
                .body("password", equalTo(usuario.getSenha()))
                .body("_id", equalTo(idUsuario));
    }

    @Test
    @DisplayName("Deve retornar um erro caso o ID do usuário não exista.")
    void testeVerificandoIdInexistente() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/usuarios/22222222222222222222222222222222222222")
        .then()
                .statusCode(400);
    }
}

