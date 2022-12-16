# Proyecto Base Implementando Clean Architecture

## Set up

### Prerequisitos

- Git
- Docker y docker-compose instalado
- Postman -> (En la carpeta principal esta el postman collection.json) 

### Ejecutar proyecto 

- Git clone `https://github.com/nymer1ax/tenpo.git`
- Situarse en la carpeta clonada 
- Ejecutar `docker-compose build`
- Ejecutar `docker-compose up`


## Descripcion

Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:
Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10, entonces (5 + 5) + 10% = 11). Se deben tener en cuenta las siguientes consideraciones:

El servicio externo puede ser un mock, tiene que devolver el % sumado.

Dado que ese % varía poco, se debe de garantizar que esa información expire cada 30 min.

Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.

Si el servicio falla, se puede reintentar hasta 3 veces.

Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado, y en caso de falla, no debe impactar el llamado al servicio principal.

La api soporta recibir como máximo 3 rpm (request / minuto), en caso de superar ese umbral, debe retornar un error con el código http y mensaje adecuado.

El historial y la información de los usuarios se debe almacenar en una database PostgreSQL.

Incluir errores http. Mensajes y descripciones para la serie 4XX.

## Explicación Proyecto

Se construyeron dos microservicios usando Spring Web

### Microservicio 2

- Genera un valor de un porcentage cada 30 minutos
- Usa una base de datos en memoria H2 para cachear el porcentage generado aleatoriamente.

### Microservicio 1:
- Consume el Microservicio 2
- Genera un Historico
- Suma dos numeros
- Endpoint que muestra consumo de MS2

## Tecnologias Relevantes Usadas en el Proyecto

- Docker-compose: Esta configurado para correr el proyecto bajo gradle bootRun y iniciar ambos microservicios.

### Microservicio 1: Esta expuesto en el puerto 8080

- Base de datos Postres -> Usa la creada de la imagen de docker
OkHttp -> Rest Consumer del Microservicio 2

- Spring AOP -> A traves de la programacion orientada aspectos, guardo los valores del historico cada vez que se llama un endpoint o una excepcion.
y se usa para almacenar el ultimo numero porcentage del microservicio 2.

- Bancolombia Scaffold (Gradle) -> Bancolombia Scatfold es un plugin de bancolombia que permite generar proyectos bajo la filosofia de Clean Architecture
Es un plugin opensource que funciona a través de comandos gradle, separa la aplicación en capas y permite agilizar el desarrollo.
Con este plugin fueron generados ambos Microservicios. 
Bucket4j -> Se usa para controlar el Rate Limit de la API


### Microservicio 1: Esta expuesto en el puerto 8090

- Base de datos en memoria -> H2

- Bancolombia Scaffold (Gradle) -> Bancolombia Scatfold es un plugin de bancolombia que permite generar proyectos bajo la filosofia de Clean Architecture
Es un plugin opensource que funciona a través de comandos gradle, separa la aplicación en capas y permite agilizar el desarrollo.
Con este plugin fueron generados ambos Microservicios. 


## Endpoints


## API ENDPOINTS

# Microservicio 1

#### GET Percentage

Este endpoint consume la respuesta del enpoint del MS2 :8090/api/percentage
y muestra el resultado de el ultimo porcentage generado por el servicio 


- Endpoint: **GET http://localhost:8080/api/percentage**
- Response de ejemplo:
```
{
    "endpoint": "/api/percentage",
    "descripcionRespuesta": "OK",
    "codigoResultado": "200",
    "fecha": "2022-12-16T00:43:43.470935",
    "result": 0.6119385143519809
}
```

### Excepciones Respuestas GET Percentage

Si no tiene conexión con el MS2, intenta conectarse 3 veces. (Tarda 4.61s)
Sino obtiene respuesta devuelve la siguiente excepcion

```
{
    "endpoint": "ConnectException",
    "descripcionRespuesta": "No se pudo obtener el valor del procentaje. Ha ocurrido un error en la conexión.",
    "codigoResultado": "SERVICE_UNAVAILABLE",
    "fecha": "2022-12-16T00:57:31.834246",
    "result": []
}
```



#### Get Historical
Este enpoint devuelve un historico de los llamados a todos los endpoints (incluyendose el mismo) con sus resultados.
Este enpoint se encuentra pagina para eso se hacen uso:
de los Query Params
?page=numero de pagina a mostrar (valor por defecto: 0)
?size= numero de elementos a mostrar (valor por defecto: 10)

- Endpoint: **GET http://localhost:8080/api/historical?page=0&size=10**
- Tambien puedes usar -> Endpoint: **GET http://localhost:8080/api/historical**
```
{
    "endpoint": "/api/historical",
    "descripcionRespuesta": "OK",
    "codigoResultado": "200",
    "fecha": "2022-12-16T00:51:00.138968",
    "result": {
        "content": [
            {
                "endpoint": "/api/percentage",
                "message": "OK",
                "statusCode": "200",
                "result": "0.6119385143519809",
                "createdAt": 1671151423474
            },
            {
                "endpoint": "/api/historical",
                "message": "OK",
                "statusCode": "200",
                "result": "OK",
                "createdAt": 1671151642149
            },
            {
                "endpoint": "TooManyRequestsException",
                "message": "Has superado los 3 request per minute. Intentalo más tarde",
                "statusCode": "TOO_MANY_REQUEST",
                "result": "[]",
                "createdAt": 1671151653548
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "pageNumber": 0,
            "pageSize": 10,
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "totalPages": 1,
        "totalElements": 3,
        "last": true,
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "first": true,
        "size": 10,
        "number": 0,
        "numberOfElements": 3,
        "empty": false
    }
}
```

### Excepciones Respuestas GET Historical

Si no existen datos que mostrar en el historico

```
{
    "endpoint": "NoContentException",
    "descripcionRespuesta": "Error: No existe información historica la cual mostrar.",
    "codigoResultado": "NO_CONTENT",
    "fecha": "2022-12-16T01:01:39.046774",
    "result": []
}
```

#### Get Sum two numbers
Este endpoins recibe dos numeros y los suma con el valor de porcentaje consumido del MS2.

de los Query Params
?num1= numero 1
?num2= numero 2

- Endpoint: **GET http://localhost:8080/api/sum?num1=5&num2=5**
```
{
    "endpoint": "/api/sum",
    "descripcionRespuesta": "OK",
    "codigoResultado": "200",
    "fecha": "2022-12-16T00:54:19.167713",
    "result": 10.0968060379223
}
```

### Excepciones Respuestas GET TWO SUMS

Este servicio invoca el microservicio 2, para obtener el valor del porcentaje a sumar. En caso de estar el microservicio dos apagado, busca el ultimo porcentaje obtenido del microservicio dos y lo suma. En caso de no encontrar ningun valor devuelve el siguiente error:

```
{
    "endpoint": "ConnectionException",
    "descripcionRespuesta": "La conexión con el servicio ha fallado: No se ha encontrado el valor del pct",
    "codigoResultado": "SERVICE_UNAVAILABLE",
    "fecha": "2022-12-16T01:07:28.340456",
    "result": []
}
```

### Excepciones Más de 3 peticiones

Esta excepcion se invoca cuando has superado el maximo de peticiones aceptada en la api por minuto: 3.
}
```
{
    "endpoint": "TooManyRequestsException",
    "descripcionRespuesta": "Has superado los 3 request per minute. Intentalo más tarde",
    "codigoResultado": "TOO_MANY_REQUEST",
    "fecha": "2022-12-16T01:11:52.902541",
    "result": []
}
}
```


# Microservicio 2

#### Get Sum two numbers
Este devuelve un porcentaje aleatorio cada 30 minutos. 

- Endpoint: **GET http://localhost:8090/api/percentage**
```
{
    "codigoResultado": "200",
    "descripcionRespuesta": "OK",
    "fecha": "2022-12-16T00:54:56.761666",
    "result": 0.7265920638157155
}
```





## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**
