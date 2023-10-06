package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.model.Usuario;
import org.junit.jupiter.api.TestInstance;
import stub.UsuarioStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetUsuarioTest {
    private static final String BASE_URL = "https://serverest.dev";
    private UsuarioStub usuarioStub;
    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
        usuarioStub = new UsuarioStub();
    }
    @Test
    @DisplayName("Deve verificar se o get dos usuários busca-os corretamente.")
    void testeBuscandoUsuarios() {
        Response resposta =
                given()
                .when()
                .get("usuarios");

        assertEquals(200, resposta.getStatusCode());
        String nomeDoUsuario = resposta.jsonPath().getString("usuarios[0].nome");
        String emailDoUsuario = resposta.jsonPath().getString("usuarios[0].email");
        boolean administrador = resposta.jsonPath().getBoolean("usuarios[0].administrador");
        String senhaDoUsuario = resposta.jsonPath().getString("usuarios[0].password");

        assertFalse(nomeDoUsuario.isEmpty());
        assertFalse(emailDoUsuario.isEmpty());
        assertTrue(administrador);
        assertFalse(senhaDoUsuario.isEmpty());
    }
    @Test
    @DisplayName("Deve validar um e-mail.")
    void testeValidandoEmail(){
        String emailTeste = "annab@email.com";
        String regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";

        assertTrue(emailTeste.matches(regex));
    }
    @Test
    @DisplayName("Deve buscar um usuário pelo ID.")
    void testeBuscandoUsuarioPeloId() {
        Usuario usuario = usuarioStub.postUsuario();

        Response respostaGet =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(usuario)
                .when()
                        .post("/usuarios");

        String idUsuario = respostaGet.jsonPath().getString("_id");

        Response resposta =
                given()
                        .when()
                        .get("/usuarios/" + idUsuario);

        assertEquals(200, resposta.getStatusCode());

        String nome = resposta.jsonPath().getString("nome");
        String email = resposta.jsonPath().getString("email");
        String senha = resposta.jsonPath().getString("password");
        String id = resposta.jsonPath().getString("_id");

        assertEquals(usuario.getNome(), nome);
        assertEquals(usuario.getEmail(), email);
        assertEquals(usuario.getSenha(), senha);
        assertEquals(idUsuario, id);
    }
    @Test
    @DisplayName("Deve retornar um erro caso o ID do usuário seja inválido.")
    void testeVerificandoIDInvalido() {
        Response resposta =
                given()
                .when()
                .get("usuarios/idquenaoexiste");

        assertEquals(400, resposta.getStatusCode());
    }
}

