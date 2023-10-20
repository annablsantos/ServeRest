package api;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.example.dto.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;
import provider.UsuarioProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PutUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private static UsuarioProvider usuarioProvider;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioProvider = new UsuarioProvider();
    }
    @Test
    @DisplayName("Deve alterar um usuário com sucesso.")
    void testeAlterandoUsuario() {
        Usuario usuario = usuarioProvider.postUsuario();
        Response respostaPost = usuarioProvider.criarUsuario(usuario);

        String idDoUsuario = respostaPost.jsonPath().getString("_id");

        Usuario usuarioAlterado = usuarioProvider.putUsuario();

        given()
                .contentType(ContentType.JSON)
                .body(usuarioAlterado)
        .when()
                .put("/usuarios/" + idDoUsuario)
        .then()
                .statusCode(200)
                .body(containsString("Registro alterado com sucesso"));

        Response respostaGet =
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/usuarios/" + idDoUsuario);

        respostaGet
                .then()
                .statusCode(200)
                .body("nome", equalTo(usuarioAlterado.getNome()))
                .body("email", equalTo(usuarioAlterado.getEmail()))
                .body("password", equalTo(usuarioAlterado.getSenha()));
    }
}
