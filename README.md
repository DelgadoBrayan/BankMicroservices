# 🏦 Banking Microservices – Spring WebFlux

Sistema bancario reactivo construido con **Java + WebFlux + Docker**, siguiendo **Arquitectura Hexagonal** y principios de **Clean Code** en dos microservicios independientes.

---

## 🏛️ Architecture Overview

El sistema está dividido en dos microservicios completamente independientes, cada uno con su propia base de datos:

```
┌─────────────────────────────────────────────────────┐
│                   API Consumers                     │
└────────────────────┬────────────────────────────────┘
                     │
        ┌────────────┴────────────┐
        │                         │
        ▼                         ▼
┌───────────────┐         ┌───────────────┐
│cliente-service│◄────────│cuenta-service │
│  port: 8081   │ WebClient│  port: 8082  │
└───────┬───────┘         └───────┬───────┘
        │                         │
        ▼                         ▼
┌───────────────┐         ┌───────────────┐
│ banco_clientes│         │ banco_cuentas │
│  (PostgreSQL) │         │  (PostgreSQL) │
└───────────────┘         └───────────────┘
```

Cada microservicio sigue **Arquitectura Hexagonal (Ports & Adapters)**:

```
Domain          ← Lógica de negocio pura, sin dependencias de frameworks
Application     ← Orquestación de casos de uso, DTOs, Mappers
Infrastructure  ← Controladores REST, adaptadores R2DBC, WebClient
```

---

## ✅ Buenas Prácticas Aplicadas

- **Stack 100% reactivo** — Cada capa es no bloqueante de extremo a extremo usando `Mono` y `Flux`, desde los controladores REST hasta la base de datos con R2DBC.

- **Lógica de negocio en el dominio** — Las reglas de negocio viven donde corresponden. El modelo `Account` contiene la lógica de cálculo de saldo mediante `applyMovement()`, evitando que esa responsabilidad se disperse en los casos de uso.

- **Enums tipados con errores personalizados** — Conceptos como tipo de cuenta y tipo de movimiento son enums, no strings, evitando datos inválidos. Si se envía un valor inexistente, se retorna un error claro y personalizado.

- **Paginación en todos los endpoints GET** — Todos los listados retornan un `PageResponse<T>` con metadatos de paginación, evitando cargas de datos sin límite.

- **Comunicación asíncrona entre servicios** — `cuenta-service` valida la existencia del cliente en `cliente-service` vía WebClient reactivo antes de crear o actualizar una cuenta.

- **Prevención del problema N+1** — Los datos de clientes se obtienen con una única consulta JOIN con proyección personalizada, evitando consultas adicionales por cada registro.

- **Índices optimizados** — Se aplicaron índices estratégicos en todas las columnas de búsqueda frecuente, incluyendo índices compuestos para las consultas del reporte.

- **Manejo centralizado de errores** — Todas las excepciones son gestionadas globalmente mediante `@RestControllerAdvice`, retornando siempre una respuesta estructurada y consistente:
```json
{
  "timestamp": "2026-03-01T10:00:00",
  "status": 400,
  "error": "Insufficient Balance",
  "message": "Insufficient balance. Current balance: 100.00"
}
```

- **Migraciones con Flyway** — El esquema de base de datos está versionado con Flyway, garantizando consistencia en todos los entornos.

- **Dockerización completa** — Dockerfiles multi-stage con Eclipse Temurin 17 y un `docker-compose.yml` que orquesta los 4 servicios con healthchecks y red compartida.

---

## 🚀 Clonar y Correr el Proyecto

### Prerrequisitos

| Herramienta    | Versión |
|----------------|---------|
| Java JDK       | 17+     |
| Maven          | 3.8+    |
| Docker         | 24+     |
| Docker Compose | 2.0+    |

---

### Opción A – Docker (Recomendado)

**1. Clonar el repositorio:**
```bash
git clone https://github.com/DelgadoBrayan/BankMicroservices.git
cd proyecto-bancario
```

**2. Construir e iniciar todos los servicios:**
```bash
docker-compose up --build
```

**3. Verificar que los 4 contenedores estén corriendo:**
```bash
docker-compose ps
```

```
NAME                STATUS
postgres-cliente    running (healthy)
postgres-cuenta     running (healthy)
cliente-service     running (healthy)
cuenta-service      running
```

**4. Los servicios estarán disponibles en:**
- `cliente-service` → http://localhost:8081
- `cuenta-service`  → http://localhost:8082

**5. Detener los servicios:**
```bash
# Solo detener
docker-compose down

# Detener y eliminar datos (reset completo)
docker-compose down -v
```
## 🧪 Correr los Tests

```bash
# Tests unitarios – cliente-service
cd cliente-service
mvn test -Dtest=ClientUseCaseImplTest

# Tests de integración – cuenta-service
cd cuenta-service
mvn test -Dtest=AccountMovementIntegrationTest

# Todos los tests
mvn test
```
