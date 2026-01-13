# Hotel Tulum Resort API

API REST hecha con Spring Boot 

## Qué he usado

- Java 21
- Spring Boot 3.4.1
- Spring Security con Basic Auth
- Spring Data JPA
- Base de datos H2 (en memoria)
- Gradle
- JUnit 5 para los tests

## Estructura del proyecto

He seguido arquitectura hexagonal separando en tres capas:

- domain: Las entidades (Hotel, Address) y la interfaz del repositorio
- application: Los casos de uso (crear, listar, buscar, actualizar, eliminar).
- infrastructure: Los adaptadores ,controlador REST, repositorio JPA, seguridad.

## Endpoints disponibles

| POST | /hotels | Crea un hotel | USER |
| GET | /hotels | Lista todos los hoteles | USER |
| GET | /hotels/search?city=Tulum | Busca hoteles por ciudad | USER |
| PATCH | /hotels/{id}/address | Actualiza la dirección | USER |
| DELETE | /hotels/{id} | Elimina un hotel | Solo ADMIN |

## Usuarios para probar

| user | user123 | USER |
| admin | admin123 | ADMIN |

Hay tests unitarios con MockMvc y tests de integración con RestTestClient.

## Ejemplo para crear un hotel

curl -X POST http://localhost:8080/hotels \
  -u user:user123 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hotel Tulum Resort",
    "stars": 5,
    "address": {
      "street": "Calle 1",
      "city": "Tulum",
      "country": "México",
      "postalCode": "77760"
    }
  }'

## Notas

- Las validaciones devuelven errores 400 con los campos que fallan
- Si no estás autenticado devuelve 401
- Si intentas borrar sin ser admin devuelve 403
- Si el hotel no existe devuelve 404