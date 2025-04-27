# TickTock
"TickTock [El tiempo corre, nosotros te avisamos.] es una aplicación construida con Spring Boot que permite programar recordatorios y enviarlos por correo electrónico en momentos específicos."
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