# POKEDEX

# Guia ayuda construcción API REST

# Arquitectura REST

La arquitectura **REST** (Representational State Transfer) es un estilo de arquitectura para el diseño de servicios web que permite la comunicación entre sistemas de forma sencilla y escalable, utilizando el protocolo HTTP. Fue definida por **Roy Fielding** en el año 2000 en su tesis doctoral y es ampliamente utilizada para construir APIs (interfaces de programación de aplicaciones) que faciliten la interacción entre el cliente y el servidor.

## Principios clave de REST:

1. **Cliente-Servidor**:
   REST promueve una separación clara entre el cliente (quien solicita los recursos) y el servidor (quien los provee). Esto facilita la escalabilidad y permite desarrollar y evolucionar cada parte por separado.

2. **Stateless (Sin Estado)**:
   Cada solicitud del cliente al servidor debe contener toda la información necesaria para que el servidor la entienda y la procese, sin depender del estado de solicitudes anteriores. Esto significa que el servidor no guarda información sobre el estado del cliente entre solicitudes.

3. **Cacheable (Almacenable en Caché)**:
   REST permite que las respuestas del servidor puedan ser almacenadas en caché para mejorar la eficiencia y reducir la carga de red. Las respuestas deben ser etiquetadas como “cacheables” o “no cacheables” para que el cliente sepa cómo manejarlas.

4. **Interfaz Uniforme**:
   La arquitectura REST se basa en una interfaz estándar que facilita la comunicación entre sistemas. Generalmente, se usa HTTP como protocolo y se implementan métodos como:
    - `GET`: Obtener un recurso.
    - `POST`: Crear un nuevo recurso.
    - `PUT`: Actualizar un recurso existente.
    - `DELETE`: Eliminar un recurso.

5. **Recursos Identificados por URIs**:
   En REST, cada recurso (una entidad como un usuario, producto, etc.) es representado por una URI única. Por ejemplo, `/api/usuarios/1` podría representar al usuario con ID 1.

6. **Representaciones de Recursos**:
   Los recursos pueden representarse en distintos formatos como JSON, XML, o incluso HTML. El servidor decide qué formato entregar y el cliente puede solicitar un formato específico usando el encabezado `Accept`.

## Ventajas de una arquitectura REST:
- **Escalabilidad**: Permite escalar tanto el cliente como el servidor de forma independiente.
- **Flexibilidad y portabilidad**: Al usar HTTP y formatos de datos como JSON o XML, es fácilmente integrable con distintos lenguajes y plataformas.
- **Simplicidad**: REST es más fácil de implementar y entender, gracias al uso de métodos HTTP estándar y una estructura de URL clara para identificar recursos.

# Flujo de Ejecución en Spring Boot para una HTTP Request

Cuando llega una petición HTTP a una aplicación Spring Boot, esta sigue un flujo de ejecución para procesar la solicitud y enviar una respuesta al cliente. Vamos a ver un ejemplo de flujo de ejecución paso a paso para una solicitud `GET` en la URI `localhost:8080/pokemon/1`, donde `/pokemon/1` representa una solicitud para obtener los datos de un Pokémon con ID `1`.

## 1. El cliente envía la solicitud HTTP

El cliente envía una solicitud HTTP `GET` a la dirección `localhost:8080/pokemon/1`. Esta solicitud contiene:

- El método HTTP (`GET`).
- La URI (`/pokemon/1`).
- Encabezados HTTP, como `Accept`, para especificar el formato de respuesta deseado (por ejemplo, JSON).

Spring Boot escucha en el puerto 8080 de forma predeterminada y recibe la solicitud.

## 2. DispatcherServlet recibe la solicitud

En Spring Boot, todas las solicitudes entrantes son manejadas por el componente principal llamado **DispatcherServlet**. Este actúa como el controlador central de las solicitudes y distribuye cada solicitud a los componentes adecuados de la aplicación.

## 3. El DispatcherServlet busca un controlador adecuado

El **DispatcherServlet** busca en el contexto de Spring un **Controlador** que pueda manejar esta solicitud específica. En este caso, intentará encontrar un controlador que esté mapeado a la URI `/pokemon/{id}` y al método HTTP `GET`.

## 4. Spring llama al método del controlador

Una vez que encuentra el controlador adecuado, el **DispatcherServlet** llama al método que maneja la solicitud.

````java
@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable Long id) {
        Pokemon pokemon = pokemonService.getPokemonById(id);
        return ResponseEntity.ok(pokemon);
    }
}
````

## 5. El controlador llama a la capa de servicio
Para procesar la solicitud, el método del controlador suele delegar la lógica de negocio a una capa de servicio.

## 6. La capa de servicio interactúa con el repositorio
La capa de servicio puede interactuar con la capa de acceso a datos (repositorio) para recuperar los datos.

````java
@Service
public class PokemonService {
    
    @Autowired
    private PokemonRepository pokemonRepository;
    
    public Pokemon getPokemonById(Long id) {
        return pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
    }
}
````

## 7. El repositorio accede a la base de datos
En Spring, los repositorios generalmente se implementan mediante interfaces que extienden `JpaRepository` u otras interfaces de persistencia de datos. Spring Data JPA proporciona automáticamente las implementaciones de las operaciones comunes para interactuar con la base de datos.

````java
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
````

## 8. La respuesta se construye en el controlador

El objeto obtenido desde el servicio es devuelto desde el servicio al controlador, quien lo envuelve en un `ResponseEntity` para enviar una respuesta adecuada.

## 9. DispatcherServlet envía la respuesta al cliente

El **DispatcherServlet** recibe la respuesta `ResponseEntity` del controlador, que incluye:

- El código de estado HTTP (por ejemplo, `200 OK`).
- Los encabezados HTTP relevantes.
- El cuerpo de la respuesta con el objeto en formato JSON.

Luego, **DispatcherServlet** envía esta respuesta al cliente.

## Resumen del flujo

1. **Cliente** envía una solicitud `GET /pokemon/1`.
2. **DispatcherServlet** recibe la solicitud.
3. **DispatcherServlet** busca el controlador adecuado.
4. **Controlador** procesa la solicitud y llama a la **capa de servicio**.
5. **Servicio** interactúa con el **repositorio** para acceder a la base de datos.
6. **Repositorio** obtiene los datos de la base de datos.
7. **Servicio** retorna los datos al **controlador**.
8. **Controlador** construye la respuesta.
9. **DispatcherServlet** envía la respuesta al **cliente**.

Este flujo de ejecución permite manejar solicitudes HTTP de manera eficiente y modular, lo que hace de Spring Boot una plataforma popular para desarrollar APIs REST.

# Anotaciones más Importantes en Spring Boot

En Spring Boot, existen varias anotaciones esenciales que facilitan la configuración y el manejo de la aplicación, permitiendo una organización y modularización clara de los componentes. A continuación se detallan algunas de las anotaciones más importantes y su propósito.

## 1. @RestController

- Combina las funcionalidades de `@Controller` y `@ResponseBody`.
- Indica que la clase es un controlador de Spring MVC, diseñado para manejar solicitudes web.
- Los métodos dentro de una clase anotada con `@RestController` devuelven el cuerpo de la respuesta directamente (generalmente en formato JSON o XML) sin necesidad de anotarlos con `@ResponseBody`.

## 2. @Controller

- Define una clase como controlador en el patrón MVC.
- Generalmente, se usa cuando la aplicación retorna vistas HTML, como en aplicaciones web tradicionales (no APIs REST).
- Se puede combinar con `@RequestMapping` para definir las rutas.

## 3. @RequestMapping

- Define la URL o el mapeo de ruta de un controlador o método.
- Se puede aplicar a nivel de clase y de método, para crear rutas específicas para distintas solicitudes.
- También soporta restricciones de método HTTP (`GET`, `POST`, etc.) y otros atributos como `params`, `headers`, y `consumes`.

## 4. @GetMapping, @PostMapping, @PutMapping, @DeleteMapping

- Variantes de `@RequestMapping` específicas para cada método HTTP (`GET`, `POST`, `PUT`, `DELETE`).
- Simplifican la configuración de rutas en el controlador al indicar el tipo de solicitud esperado, facilitando el código y la legibilidad.

## 5. @Service

- Marca una clase como un "Servicio", dentro de la capa de negocio o lógica de la aplicación.
- Se usa para indicar que la clase contiene lógica de negocio, facilitando la inyección de dependencias y el manejo de transacciones.

## 6. @Repository

- Marca una clase como "Repositorio", generalmente usada en la capa de acceso a datos.
- Se utiliza junto a Spring Data JPA, que permite implementar métodos de acceso a la base de datos sin necesidad de escribir consultas SQL manuales.
- Aporta una capa de abstracción entre la aplicación y el almacenamiento de datos.

## 7. @Autowired

- Permite la **inyección de dependencias** automática de Spring, encargándose de instanciar y suministrar las dependencias requeridas en los componentes.
- Se puede utilizar en constructores, métodos o campos.
- Facilita el desacoplamiento de los componentes de la aplicación y asegura que las dependencias se gestionen en un único lugar.

## 8. @Component

- Marca una clase como un "Componente de Spring" genérico.
- Indica que el objeto puede ser administrado por Spring y está disponible para ser inyectado en otras clases.
- `@Service`, `@Repository`, y `@Controller` son subtipos de `@Component`, con funciones específicas.

## 9. @Configuration

- Indica que una clase define una o varias configuraciones de Spring (similar a los archivos XML de configuración en Spring tradicional).
- Dentro de una clase `@Configuration`, se pueden definir `@Bean` para instanciar y configurar objetos específicos que estarán disponibles en el contexto de Spring.

## 10. @Bean

- Define un método que crea y devuelve un objeto administrado por Spring (un "Bean").
- Los métodos anotados con `@Bean` dentro de una clase `@Configuration` permiten personalizar y configurar objetos específicos en el contexto de Spring.

## 11. @Qualifier

- Se usa junto con `@Autowired` para diferenciar entre múltiples implementaciones de una misma interfaz.
- Es útil en casos donde hay varios beans del mismo tipo en el contexto de Spring, y se requiere especificar cuál debe inyectarse en un componente.

## 12. @Value

- Inyecta valores en variables desde el archivo de configuración (por ejemplo, `application.properties` o `application.yml`).
- Se utiliza para parametrizar configuraciones como URLs, credenciales, u otros valores dinámicos en la aplicación.

## 13. @Transactional

- Marca un método o clase para indicar que sus operaciones se deben ejecutar en una transacción.
- Permite que Spring gestione automáticamente las transacciones, asegurando que las operaciones de la base de datos se completen o se reviertan en caso de error.

## 14. @SpringBootApplication

- Es una anotación que simplifica la configuración de una aplicación Spring Boot al combinar tres anotaciones importantes:

    - `@Configuration`: Para definir la configuración de Spring.
    - `@EnableAutoConfiguration`: Para permitir la configuración automática de Spring Boot.
    - `@ComponentScan`: Para escanear el paquete principal en busca de componentes y registrarlos en el contexto.

- Se coloca en la clase principal de la aplicación para indicar el punto de entrada.

Estas anotaciones ayudan a definir la arquitectura de una aplicación Spring Boot, permitiendo gestionar controladores, servicios, repositorios, configuraciones y más de manera modular y eficiente.

# Inyección de Dependencias (Dependency Injection, DI) e Inversión de Control (Inversion of Control, IoC)

### Inyección de Dependencias (Dependency Injection, DI)

La inyección de dependencias es un patrón de diseño en el que un objeto recibe sus dependencias desde una fuente externa en lugar de crearlas internamente. La "dependencia" es cualquier otro objeto que el objeto actual necesita para funcionar. Este patrón busca desacoplar las clases, promoviendo la reutilización y la modularidad del código. Así, en lugar de instanciar directamente sus dependencias, las clases se construyen para aceptar objetos desde fuera, ya sea por inyección a través de constructores, métodos o campos.

**Ejemplo de Inyección de Dependencias en Spring**

En Spring, el contenedor de inversión de control (IOC) administra y proporciona los objetos que necesita cada componente. Esto significa que las dependencias se pueden declarar en la clase, y Spring se encarga de suministrarlas en tiempo de ejecución, lo que simplifica el código y permite que los objetos sean reutilizables y fáciles de probar.

### Inversión de Control (Inversion of Control, IoC)

La inversión de control es un principio de diseño que cambia la responsabilidad de manejar el flujo de la aplicación desde el código de negocio hacia un framework o contenedor. En lugar de que el objeto cree y controle sus dependencias, el control se invierte: el framework (como Spring) gestiona la creación, configuración y ciclo de vida de los objetos.

**Relación entre Inyección de Dependencias y la Inversión de Control**

La inyección de dependencias es una implementación concreta del principio de inversión de control. IoC establece el concepto de que el flujo de control debe ser gestionado externamente, y la inyección de dependencias lo lleva a cabo, ya que permite que el contenedor se haga cargo de proveer los objetos necesarios.

### Inyección de Dependencias e Inversión de Control en Spring Boot

Spring Boot hace que la inversión de control y la inyección de dependencias sean fáciles de implementar al configurar automáticamente los componentes en el contenedor de Spring, lo que permite a los desarrolladores centrarse en la lógica de negocio sin preocuparse por la creación y gestión de dependencias. Spring Boot utiliza la anotación `@Autowired` para facilitar la inyección de dependencias, y `@Component`, `@Service`, `@Repository`, y otras anotaciones para declarar los diferentes componentes que el contenedor IoC gestionará.

En resumen, en una aplicación Spring Boot:

- **IoC** permite que Spring Boot controle y gestione el ciclo de vida de los componentes.
- **DI** es la técnica que usa Spring para suministrar automáticamente los objetos necesarios a los componentes, promoviendo un código más modular, testeable y fácil de mantener.
