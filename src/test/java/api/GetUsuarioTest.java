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
import static org.hamcrest.MatcherAssert.assertThat;
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
    @DisplayName("Deve verificar se o get dos usuários busca-os corretamente")
    void testeBuscandoOsUsuarios() {
        Response resposta =
                given()
                .when()
                .get("usuarios");

        assertEquals(200, resposta.getStatusCode());
        String nomeDoUsuario = resposta.jsonPath().getString("usuarios[0].nome");
        String emailDoUsuario = resposta.jsonPath().getString("usuarios[0].email");
        String senhaDoUsuario = resposta.jsonPath().getString("usuarios[0].password");

        assertFalse(nomeDoUsuario.isEmpty());
        assertFalse(emailDoUsuario.isEmpty());
        assertFalse(senhaDoUsuario.isEmpty());
    }

    @Test
    @DisplayName("Deve validar um e-mail")
    void testeValidandoEmail(){
        String emailTeste = "annab@email.com";
        String regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";

        assertTrue(emailTeste.matches(regex));
    }

    @Test
    @DisplayName("Deve dar erro caso o ID do usuário seja inválido")
    void testeVerificandoIDInvalido() {
        Response resposta =
                given()
                .when()
                .get("usuarios/123456789");

        assertEquals(400, resposta.getStatusCode());
    }
}

