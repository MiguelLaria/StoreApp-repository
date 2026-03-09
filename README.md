# 🍷 A Tasca do Microserviço
> Labs 3 & 4 do Microservices + DevOps + Cloud Academy 2026.
> Product Service + Order Service containerizados com Docker Compose + PostgreSQL.
## ‍ Informações do Aluno
| Campo | Valor |
|-----------------|----------------------------------------|
| Nome | [SEU NOME COMPLETO] |
| Turma | Microservices Academy 2026 |
| Labs | Lab 3 & 4 — Dia 2 |
| Data de entrega | [DATA] |
| Email | [SEU EMAIL] |
## ️ Arquitectura
```
┌─────────────────────────────────────────────────────────┐
│ Docker Network │
│ │
│ ┌──────────────────┐ ┌──────────────────────────┐ │
│ │ product-service │◄───│ order-service │ │
│ │ :8080 │ │ :8081 │ │
│ │ Spring Boot │ │ Spring Boot │ │
│ └────────┬─────────┘ └────────────┬─────────────┘ │
│ │ │ │
│ ┌────────▼───────────────────────────▼─────────────┐ │
│ │ PostgreSQL :5432 │ │
│ │ db: tascadb │ │
│ └──────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────┘
```
| Serviço | Porta | Responsabilidade |
|------------------|-------|----------------------------------------------|
| product-service | 8080 | CRUD de produtos (ementa da Tasca) |
| order-service | 8081 | Gestão de encomendas (pedidos de mesa) |
| db (PostgreSQL) | 5432 | Persistência de produtos e encomendas |
## ⚡ Execução
### Pré-requisitos
- Docker Desktop instalado e em execução
- Java 17+ e Maven (para compilar os JARs antes do build Docker)
### Passo 1 — Compilar os microserviços
```bash
git clone git@github.com:SEU_USERNAME/tasca-do-microservico.git
cd tasca-do-microservico
# Compilar product-service
cd product-service
./mvnw clean package -DskipTests
cd ..
# Compilar order-service
cd order-service
./mvnw clean package -DskipTests
cd ..
```
### Passo 2 — Iniciar com Docker Compose
```bash
docker compose up --build
```
> ⏱️ Primeira execução: 2–4 minutos
> Aguardar até ver nos logs:
> `tasca-products | Started ProductServiceApplication`
> `tasca-orders | Started OrderServiceApplication`
### Verificar serviços
```bash
docker compose ps
# db, product-service e order-service devem estar Up
curl http://localhost:8080/api/products
curl http://localhost:8081/api/orders
```
## 🧪 Testar o Fluxo Completo
```bash
# 1. Criar um produto (prato da ementa)
curl -X POST http://localhost:8080/api/products \
-H 'Content-Type: application/json' \
-d '{"name":"Bacalhau com Natas","description":"O clássico","price":14.50,"quantity":50}'
# Resposta esperada: {"id":1, "name":"Bacalhau com Natas", ...}
# 2. Criar uma encomenda que referencia o produto
curl -X POST http://localhost:8081/api/orders \
-H 'Content-Type: application/json' \
-d '{"productId":1,"quantity":2,"customerName":"Ze das Couves"}'
# 3. Listar encomendas
curl http://localhost:8081/api/orders
## ️ Inspecionar a Base de Dados
```bash
# Aceder ao PostgreSQL directamente
docker exec -it tasca-db psql -U zecouves -d tascadb
# Dentro do psql:
SELECT * FROM product;
SELECT * FROM orders;
\q
```
## 🌐 Swagger UI
| Serviço | URL |
|------------------|-----------------------------------------------|
| product-service | http://localhost:8080/swagger-ui/index.html |
| order-service | http://localhost:8081/swagger-ui/index.html |
## 🛑 Parar e Limpar
```bash
docker compose down # para (dados preservados em volume)
docker compose down -v # reset total — apaga a base de dados
```
## 📁 Estrutura do Projecto
```
tasca-do-microservico/
├── product-service/
│ ├── src/main/java/.../
│ │ ├── controller/ProductController.java
│ │ ├── model/Product.java
│ │ └── repository/ProductRepository.java
│ ├── Dockerfile
│ └── pom.xml
├── order-service/
│ ├── src/main/java/.../
│ │ ├── controller/OrderController.java
│ │ ├── model/Order.java
│ │ └── service/OrderService.java
│ ├── Dockerfile
│ └── pom.xml
├── docker-compose.yml
├── .gitignore
└── README.md
```
## 🐛 Troubleshooting
**JAR file not found durante o docker build:**
```bash
# Não esqueceres de compilar antes do build!
cd product-service && ./mvnw clean package -DskipTests && cd ..
cd order-service && ./mvnw clean package -DskipTests && cd ..
docker compose up --build
```
**Connection refused à base de dados:**
```bash
# O Spring Boot arrancou antes do PostgreSQL. Reiniciar o serviço:
docker compose restart product-service
docker compose restart order-service
```
**Order Service não encontra o Product Service:**
```bash
# Confirmar variável de ambiente no docker-compose.yml:
# PRODUCT_SERVICE_URL: http://product-service:8080
# O hostname deve ser 'product-service' (nome do serviço), não 'localhost'
docker compose logs order-service
```
**Porta já em uso:**
```bash
docker compose down && docker compose up --build
# ou verificar: lsof -i :8080 (Mac/Linux)