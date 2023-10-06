# ServeRest
Este é um projeto com o objetivo de realizar testes automatizados para a API do [ServeRest](https://serverest.dev/).

### Mapa de tecnologias e requisitos:
- Java Development Kit (JDK 11 ou superior)
- Gradle instalado
- RestAssured (4.5.1)
- SpringBoot (2.7.15)

### Como executar:
- Clone esse repositório localmente;
- Abra o projeto na sua IDE de preferência;
- Verifique se as dependências do arquivo foram corretamente baixadas;
- Execute os testes do projeto utilizando:
```./gradlew test``` ou executando a pasta "./test".


## Cenários de testes:

O projeto abrange os seguintes cenários de testes:

### GetUsuarioTest
- Verifica se a busca de usuários é executada corretamente.
- Validar o formato de um e-mail de acordo com um REGEX.
- Teste de busca de usuário por ID
- Cenário de erro: Busca um usuário que possui um ID inválido

### PostUsuarioTest
- Teste de criação de um novo usuário
- Teste de criação de usuário com E-mail repetido

### DeleteUsuarioTest
- Verificação de delete de um usuário
- Verificação do delete de um usuário que não existe

### PutUsuarioTest
- Verificação da alteração de um usuário
