# api_test
## Requisitos

Openjdk 21.0.2 <br>
Docker

### Instruções de execução
Abra o docker desktop caso o utilize para trabalhar com docker e em seguida
rode o arquivo ApitestApplication, de preferencia utilizando uma IDE, recomendo o Intellij Community.

### Instrução de execução dos testes
Execute os arquivos de testes localizados na src/test referente ao teste que será executado,
se estiver utilizando Intellij Community, clique com botão direito do mouse sobre o diretorio src/test/java e selecione a opção
Run Alltests.

#### Para testar rotas de usuario que exigem login pelo swagger, realize cadastro, login e insira o token no swagger authorization

### Documentação da api
Para acessar a documentação da api vá em localhost:8080/swagger-ui.html

### Documentação javadoc
Para acessar abra o index.hmtl localizado no diretorio javadoc

### Considerações
O crud de usuario não está completo pois foi feito excluisivamento com a finalidade de demonstrar autenticação e autorização utilizando spring security.<br>
É possivel cadastrar o tipo de usuario pelo cadastro comum pois se trata apenas de um teste.<br>
A SecretKey que gera o JWT esta no codigo apenas por facilicitação de testes, o correto seria utilizar uma variavel local ou outra forma mais segura.<br>
