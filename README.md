# Acadify — Backend

API REST del proyecto Acadify, un sistema para la gestión de programas académicos, asignaturas, resultados de aprendizaje y syllabus, desarrollado como proyecto formativo del programa Análisis y Desarrollo de Software (SENA, ficha 3186612).

Este repositorio contiene el backend, construido con Spring Boot. Expone una API REST protegida con Firebase Auth, consumida por el frontend en Angular ([acadify-frontend](https://github.com/lcvalencia9310/acadify-frontend)).

## Autores

- Esneider Cicery Manjarres
- Luis Carlos Valencia Guerrero

Instructor técnico: Mauricio Vengoechea Jiménez

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL
- Firebase Admin SDK (verificación de tokens)
- Maven
- HikariCP (pool de conexiones)

## Despliegue

| Componente | Dónde |
|---|---|
| API (este repo) | [Render](https://render.com) (Docker, JAR standalone) |
| Base de datos | MySQL en [Clever Cloud](https://www.clever-cloud.com) |
| Frontend | [Vercel](https://vercel.com) — `acadify-frontend-one.vercel.app` |
| Autenticación | [Firebase Auth](https://firebase.google.com) |

## Modelo de datos

El backend expone cuatro entidades, relacionadas entre sí mediante llaves foráneas:

```
Programa → Asignatura → ResultadoAprendizaje
              │
              └──→ Syllabus
```

- **Programa** (`programa`): código, nombre, estado activo/inactivo.
- **Asignatura** (`asignatura`): código, nombre, créditos, intensidad horaria; relacionada con un Programa (`@ManyToOne`).
- **ResultadoAprendizaje** (`resultado_aprendizaje`): descripción; relacionada con una Asignatura (`@ManyToOne`).
- **Syllabus** (`syllabus`): descripción del plan de contenido, fecha de creación; relacionada con una Asignatura (`@OneToOne`).

Cada entidad sigue la misma arquitectura en capas: `Entity → Repository → Service → Controller`.

## Seguridad

- Todas las rutas bajo `/api/**` exigen el header `Authorization: Bearer <idToken>` con un ID token de Firebase válido. `FirebaseAuthFilter` lo verifica en cada petición y responde `401` (JSON) si falta, expiró o no es válido — ver [`src/main/java/com/example/acadify/config/FirebaseAuthFilter.java`](src/main/java/com/example/acadify/config/FirebaseAuthFilter.java).
- El CORS está centralizado en [`FilterConfig`](src/main/java/com/example/acadify/config/FilterConfig.java): solo el frontend en Vercel y `localhost:4200` (desarrollo) pueden llamar a la API.
- Ninguna credencial real vive en este repositorio — ver la sección de configuración de base de datos más abajo.

## Endpoints de la API

Todas las rutas parten de `http://127.0.0.1:8080` en local, o de la URL del servicio en Render en producción. Todas requieren el header `Authorization: Bearer <idToken>`.

### Programa — `/api/programas`

| Método | Endpoint | Acción |
|---|---|---|
| GET | `/api/programas` | Listar todos |
| GET | `/api/programas/{id}` | Consultar uno |
| POST | `/api/programas` | Crear |
| PUT | `/api/programas/{id}` | Actualizar |
| DELETE | `/api/programas/{id}` | Eliminar |

### Asignatura — `/api/asignaturas`

| Método | Endpoint | Acción |
|---|---|---|
| GET | `/api/asignaturas` | Listar todos |
| GET | `/api/asignaturas/{id}` | Consultar uno |
| POST | `/api/asignaturas` | Crear |
| PUT | `/api/asignaturas/{id}` | Actualizar |
| DELETE | `/api/asignaturas/{id}` | Eliminar |

### Resultado de Aprendizaje — `/api/resultados-aprendizaje`

| Método | Endpoint | Acción |
|---|---|---|
| GET | `/api/resultados-aprendizaje` | Listar todos |
| GET | `/api/resultados-aprendizaje/{id}` | Consultar uno |
| POST | `/api/resultados-aprendizaje` | Crear |
| PUT | `/api/resultados-aprendizaje/{id}` | Actualizar |
| DELETE | `/api/resultados-aprendizaje/{id}` | Eliminar |

### Syllabus — `/api/syllabus`

| Método | Endpoint | Acción |
|---|---|---|
| GET | `/api/syllabus` | Listar todos |
| GET | `/api/syllabus/{id}` | Consultar uno |
| POST | `/api/syllabus` | Crear |
| PUT | `/api/syllabus/{id}` | Actualizar |
| DELETE | `/api/syllabus/{id}` | Eliminar |

## Requisitos previos

- JDK 21
- Maven (o usar el wrapper incluido `./mvnw`)
- MySQL corriendo localmente (por ejemplo, vía XAMPP)
- Un IDE con soporte para Spring Boot (recomendado: IntelliJ IDEA)

## Configuración de la base de datos

Crea una base de datos vacía en MySQL, por ejemplo `acadify2`.

`src/main/resources/application.properties` no contiene credenciales: las lee de variables de entorno, con valores por defecto solo para desarrollo local.

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://127.0.0.1:3306/acadify2?useSSL=false&serverTimezone=UTC}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Para desarrollo local normalmente no necesitas definir nada (usa los valores por defecto de arriba, pensados para un MySQL local sin contraseña). Para apuntar a la base de datos en la nube (Clever Cloud) o para el despliegue en Render, define estas variables de entorno (nunca las escribas en el archivo ni las subas a git):

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `FIREBASE_CREDENTIALS_JSON` — el JSON completo de la cuenta de servicio de Firebase, como una sola línea.

> Gracias a `spring.jpa.hibernate.ddl-auto=update`, Hibernate crea automáticamente las tablas (con sus llaves foráneas) al arrancar la aplicación por primera vez — no es necesario ejecutar scripts SQL manualmente.

## Clonar e instalar

```bash
git clone https://github.com/lcvalencia9310/acadify_v1.2.git
cd acadify_v1.2
```

## Ejecutar el proyecto

Desde IntelliJ IDEA, abre el proyecto y ejecuta la clase principal `AcadifyApplication`.

O desde la terminal, usando Maven:

```bash
./mvnw spring-boot:run
```

El servidor queda disponible en `http://127.0.0.1:8080`.

## Probar la API

Como todos los endpoints exigen un token de Firebase, para probar con Postman obtén un ID token válido (por ejemplo, desde el frontend ya logueado, con `firebase.auth().currentUser.getIdToken()`) y agrégalo como header. Ejemplo de creación de un programa:

```
POST http://127.0.0.1:8080/api/programas
Content-Type: application/json
Authorization: Bearer <idToken>

{
  "codigoPrograma": "228118",
  "nombrePrograma": "Análisis y Desarrollo de Software",
  "activo": true
}
```

## Pruebas

```bash
./mvnw test -Dtest=FirebaseAuthFilterTest
```

Verifica, sin depender de base de datos ni de un proyecto real de Firebase, que `FirebaseAuthFilter` rechaza peticiones sin token o con token inválido, y deja pasar el preflight CORS (`OPTIONS`).

> El resto de la suite (`AcadifyApplicationTests`) levanta el contexto completo de Spring y necesita una base de datos y credenciales de Firebase accesibles; no corre en un entorno sin esos recursos.

## Estructura del proyecto

```
src/main/java/com/example/acadify/
├── config/         # Seguridad (FirebaseAuthFilter, CORS), inicialización de Firebase
├── model/          # Entidades JPA (Programa, Asignatura, ResultadoAprendizaje, Syllabus)
├── repository/     # Interfaces JpaRepository
├── service/        # Lógica de negocio
└── controller/     # Controladores REST (@RestController)
```

## Repositorios relacionados

- Frontend (Angular): [acadify-frontend](https://github.com/lcvalencia9310/acadify-frontend)
