# TickTock ⏰ — *El tiempo corre, nosotros te avisamos.*

**TickTock** es una aplicación construida con **Spring Boot** que permite programar recordatorios y enviarlos por correo electrónico en momentos específicos.

## ✨ Características principales

- 🔔 Recordatorios personalizados por usuario.
- 📬 Envío automático de correos electrónicos con frases especiales.
- 🧩 Arquitectura modular orientada a funcionalidades (`user`, `birthday`, `shared`, `config`.).
- 🌐 API REST documentada con Swagger/OpenAPI.
- 🌱 Uso de variables de entorno con `dotenv-java`.
- 🧪 Testing con Spring Boot Starter Test (JUnit/MOCKITO en el futuro).
- 🖥️ Plantillas visuales con Thymeleaf para correos.

## 🔧 Diseño y patrones aplicados

Este proyecto sigue un enfoque didáctico y experimental para practicar distintas formas de implementación dentro del ecosistema Spring Boot. Los principales patrones y enfoques utilizados son:

- **Uso de Singleton** para la gestión eficiente de instancias.
- **Integración del patrón MVC** para separar la lógica de negocio, presentación y datos.
- **Uso de mappers personalizados con Lombok y el patrón Builder**, como enfoque principal para la conversión entre entidades y DTOs.
- **Integración opcional de MapStruct** como alternativa para mapeos automáticos, manteniendo la flexibilidad.
- **Validaciones declarativas** con `spring-boot-starter-validation` para asegurar la consistencia de los datos.
- **Paginación y DTOs** para devolver respuestas limpias y optimizadas en las API.
- **Manejo centralizado de errores** mediante un `GlobalExceptionHandler` para mejorar la gestión de excepciones en toda la aplicación.


## 🛠️ Tecnologías utilizadas

| Tecnología           | Descripción                                |
|----------------------|--------------------------------------------|
| Spring Boot 3.4.4    | Framework principal                        |
| Java 21              | Versión del JDK                           |
| Spring Data JPA      | Persistencia de datos                     |
| H2                   | Base de datos en memoria (dev/test)       |
| Swagger (springdoc)  | Documentación de la API                   |
| Lombok               | Reducción de boilerplate code             |
| MapStruct            | Alternativa de mapeo entre entidades y DTOs |
| Thymeleaf            | Plantillas HTML para emails               |
| Spring Boot Mail Sender  | Envío de correos electrónicos automáticos|
| dotenv-java          | Variables de entorno                      |


# Estructura del proyecto
```plaintext
com.TickTock.TickTock
│
├── birthday
│   ├── application
│   │   ├── dtos
│   │   │   ├── request
│   │   │   │   └── BirthdayRequest.java
│   │   │   └── response
│   │   │       └── BirthdayResponse.java
│   │   └── mappers
│   │       ├── BirthdayMapperMapStruct.java
│   │       └── BirthdayMapper.java
│   ├── domain
│   │   ├── entities
│   │   │   └── BirthdayEntity.java
│   │   ├── repositories
│   │   │   └── BirthdayRepository.java
│   │   └── services
│   │       ├── BirthdayPhraseProvider.java
│   │       └── BirthdayService.java
│   └── infrastructure
│       └── controllers
│           └── BirthdayController.java
│
├── config
│   └── SwaggerConfig.java
│
├── shared
│   ├── application
│   │   └── dtos
│   │       └── response
│   │           └── ErrorHandlerResponse.java
│   └── infrastructure
│       ├── exceptions
│       │   └── GlobalExceptionHandler.java
│       └── notifications
│           └── EmailService.java
│
├── user
│   ├── application
│   │   ├── dtos
│   │   │   ├── request
│   │   │   │   └── UserRequest.java
│   │   │   └── response
│   │   │       └── UserResponse.java
│   │   └── mappers
│   │       ├── UserMapperMapStruct.java
│   │       └── UserMapper.java
│   ├── domain
│   │   ├── entities
│   │   │   └── UserEntity.java
│   │   ├── repositories
│   │   │   └── UserRepository.java
│   │   └── services
│   │       └── UserService.java
│   └── infrastructure
│       └── controllers
│           └── UserController.java
│
├── TickTockApplication.java
│
└── resources
    ├──templates
    │    ├── BirthdayList.html
    │    └── BirthdayUser.html
    └── application.properties
```
