# Consulta Crédito API

Este projeto contém a API para o sistema de Consulta de Crédito, que depende de Kafka e PostgreSQL. A aplicação web que consome esta API está no repositório https://github.com/guilherme-boaventura/consulta-credits-web, que deve ser clonado e buildado antes de executar o sistema completo.

## Pré-requisitos

- Docker
- Docker Compose
- Git

## Passo 1: Clonar e buildar a aplicação web (consulta-credit-web)

Antes de iniciar o backend, é necessário clonar o repositório da aplicação web e construir a imagem Docker.

```bash
git clone https://github.com/guilherme-boaventura/consulta-credits-web.git
cd consulta-credits-web
docker build -t consulta-credit-web .
```

## Passo 2: Clonar e buildar a API (consulta-credit-api)
No repositório onde deseja alocar este projeto, execute:

```bash
git clone https://github.com/guilherme-boaventura/consulta-credits-api.git
cd consulta-credits-api
docker build -t consulta-credit-api .
```

## Passo 3: Subir os serviços com Docker Compose

No diretório raiz desse projeto, execute:

```bash
docker-compose up
```

Isso irá subir os seguintes serviços:

Kafka (broker)

Kafka Init (criação do tópico)

Kafka UI (interface web para monitoramento do Kafka)

PostgreSQL (banco de dados)

DB Init (criação e população da tabela de crédito)

Consulta Crédito API (aplicação back-end)

Consulta Crédito Web (aplicação front-end)

## 🔗 Serviços Disponíveis

| Serviço      | URL de Acesso                                     |
|--------------|---------------------------------------------------|
| Web          | http://localhost:4200                             | 
| API          | http://localhost:8080                             |
| Kafka UI     | http://localhost:8081                             |
| PostgreSQL   | jdbc:postgresql://localhost:5432/consulta_credito |
| Kafka Broker | localhost:9092 (externo) / kafka:29092 (interno)  |

## 🛑 Parar o sistema
Para parar e remover os containers:

```bash
docker-compose down
```

## 👀 Observações

Este projeto configura um ambiente completo para desenvolvimento e testes da aplicação **Consulta Crédito**, incluindo:

- **Apache Kafka** para mensageria.
- **Kafka UI** para visualização e gerenciamento dos tópicos.
- **PostgreSQL** como banco de dados.
- **Aplicação Consulta Crédito** containerizada com Docker.

Certifique-se de que as portas 8080, 4200, 9092 e 8081 estejam livres em sua máquina.

A aplicação espera que o Kafka esteja acessível via `kafka:29092`, e esse é o endereço interno no `docker-compose`.
O Kafka está configurado com dois listeners:
  - `EXTERNAL` para acesso local (ex: `localhost:9092`)
  - `INTERNAL` para comunicação entre containers (ex: `kafka:29092`)

## ⚙️ Variáveis de Ambiente

### Kafka

- `KAFKA_BOOTSTRAP_SERVER=kafka:29092`  
  Usado pela aplicação para se conectar ao broker Kafka.

### Banco de Dados

- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/consulta_credito`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=postgres`

## Executando testadores

Com os serviços do docker-compose sendo executados, execute o seguinte comando para executar os testadores:

```bash
set KAFKA_BOOTSTRAP_SERVER=localhost:9092 && mvn test
```