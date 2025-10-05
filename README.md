# Propósito

Esse projeto foi desenvolvido para estudo pessoal sobre Quarkus reativo e Mutiny.

Contém dois recursos (endpoints):
 - um que faz várias consultas a um único serviço externo;
 - e outro que consulta dois serviços externos diferentes;

As consultas são sempre do tipo não bloqueante e reativas.
Coloquei também alguns exemplos de como tratar o erro de cada chamada individual, mesmo trabalhando em lote.


## Como executar

Clone o projeto.

Execute o comando na raiz do projeto:
```
./mvnw quarkus:dev
```

Abra o navegador e acesse o Swagger em:

http://localhost:8080/q/swagger-ui/
