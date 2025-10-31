# API Car with FIPE integration (Hexagonal Architecture)

![coverag](https://img.shields.io/badge/coverage-100%25-darkgreen)

Version DDD and Clean Architecture on [api-car-manager-with-fipe](https://github.com/skatesham/api-car-manager-with-fipe)

API register vehicle indexed by brand and model with external client app price search.
![alt text][8]

---

## 🧱 Hexagonal Architecture (Ports & Adapters)

### 📘 Concept

Hexagonal (or **Ports and Adapters**) architecture isolates the **core business logic** from external systems.
It makes the application **independent of frameworks, databases, or delivery mechanisms**.

---

### 🧩 Diagram (ASCII in Markdown)

```
               +-----------------------------+
               |        Incoming Adapters     |
               |   (Controllers, CLI, APIs)   |
               +-------------▲---------------+
                             │
                             │ Port (Input)
                             │
               +-------------┴---------------+
               |        Application Core     |
               |   (Use Cases / Domain Logic) |
               +-------------▲---------------+
                             │
                             │ Port (Output)
                             │
               +-------------┴---------------+
               |       Outgoing Adapters      |
               | (DB, REST API, Message Bus)  |
               +-----------------------------+
```

---

### ⚙️ Layers Explained

| Layer                         | Description                                                    | Examples                               |
| ----------------------------- | -------------------------------------------------------------- | -------------------------------------- |
| **Core (Domain + Use Cases)** | Contains pure business logic. No external dependencies.        | Domain models, services                |
| **Incoming Adapter**          | Translates external input into commands for the core.          | REST controller, CLI, GraphQL          |
| **Outgoing Adapter**          | Implements interfaces to external systems defined by the core. | Repository, HTTP client, Message queue |

---

### 🔁 Flow Example

1. **Incoming adapter (API Controller)** receives a request.
2. It calls a **use case** defined in the **core** (via an input port).
3. The use case uses an **output port** (interface) to interact with a **repository adapter**.
4. The **repository adapter** talks to the database.
5. The **core** remains isolated — easily testable, replaceable, and reusable.

---

### 🌟 Benefits

✅ **Testability:** Core can be tested without DB or frameworks.
✅ **Replaceable Adapters:** Switch from REST → gRPC, or SQL → NoSQL easily.
✅ **Maintainability:** Business logic stays clean and stable.
✅ **Independence:** Frameworks and tools are secondary — not dictating your design.

---

## Operation Functional Flow (part by part)

1. Receive request for create Vehicle by POST Rest entrypoint
2. Validate database schema constraints, as: UK, Marca and Modelo
3. Produce Event for schedule creation
4. Consume Event and convert to Object
5. Request External Client [Demo FIPE][6] getting the vehicle FIPE price
6. When has External client Integration Error: send to DLQ
7. Convert vehicle Price value to number
8. Save vehicle on database

## Stack

__API__:

- Java 11: Language Programming version 11
- Spring Boot: Java Injection Framework
- Spring Web: Embedded Web Server (Apache Tomcat)
- Spring JPA: ORM Hibernate
- OpenFeign: Web Client
- RabbitMQ: Async Queue Messaging
- Actuator: Health check
- Flyway: Manage Database Migration Versioning
- Netflix Ribbon: Client Side Load Balancer (Manage OpenFeign Client)
- Jackson ObjectMapper: Library for mapper objects java.
- PostgreSQL: Database SQL
- PostgreSQL Driver: Configure connection with PostgreSQL Database
- Lombok: Java Code Style Improvements

__Tests__:

- Wiremock: Mock Web client Server
- H2: Light weight Embedded SQL Database
- RestAssured: Testing BDD REST API
- H2 Driver: Configure connection with H2 Database

## Running App

### Requirements

- JDK Java 11
- Docker / Docker Compose

```shell
./gradlew build --parallel --x test
docker build --tag=car-api:latest .
docker-compose up
```

### Open App

- [Health Check][4]
- [Swagger][1]
- [Database Adminer][2]
- [RabbitMQ Manager][3]

__Example Vehicle Request__:  
Endpoint: [POST /api/v1/veiculos][5]

Payload:

```json
{
  "placa": "NEW-4321",
  "marcaId": 21,
  "modeloId": 473,
  "precoAnuncio": 1000000,
  "ano": 2011
}
```

__Database Credentials__:

- [Database Adminer][2]

```shell
System: PosgreSQL
Server: db
Username: 123456789
Password: 123456789
Database: postgres
```

__RabbitMQ Credentials__:

- [RabbitMQ Manager][3]

```shell
Username: guest
Password: guest
```

## Schema

![alt text][7]

## Vehicle Creation Flow
![alt text][9]

## Credits:

Sham Vinicius Fiorin  
By Dryve

[1]: http://localhost:8080/swagger-ui/index.html

[2]: http://localhost:9000

[3]: http://localhost:15672

[4]: http://localhost:8080/actuator/health

[5]: http://localhost:8080/swagger-ui/index.html#/Veiculo/createVeiculo

[6]: https://github.com/giovanigenerali/fipe-json

[7]: https://github.com/skatesham/api-car-manager-with-fipe/blob/main/imgs/schema.png?raw=true

[8]: https://github.com/skatesham/api-car-manager-with-fipe/blob/main/imgs/openapi.png?raw=true

[9]: https://github.com/skatesham/api-car-manager-with-fipe/blob/main/imgs/flow-diagram.drawio.png?raw=true
