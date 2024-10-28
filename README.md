# App Medic

App Medic es una aplicación diseñada para gestionar consultas médicas. Este proyecto está construido con Java y utiliza Spring Boot.

## Estructura del Proyecto


```markdown
# App Medic

App Medic es una aplicación diseñada para gestionar consultas médicas. Este proyecto está construido con Java y utiliza Spring Boot.

## Estructura del Proyecto
.mvn/
    wrapper/
        maven-wrapper.properties
lombok.config
mvnw
mvnw.cmd
pom.xml
src/
    main/
        java/
            com/
                course/
                    app_medic/
                        AppMedicApplication.java
                        configs/
                        controllers/
                        dtos/
                        exception/
                        models/
                        repositories/
                        security/
                        ...
    resources/
        application.properties
        banner.txt
        messages_en.properties
        messages_fr.properties
        messages.properties
        reports/
            consults.jasper
```

## Requisitos Previos

- Java 11 o superior
- Maven 3.6.3 o superior

## Instalación

1. Clona el repositorio:
    ```sh
    git clone <URL_DEL_REPOSITORIO>
    ```
2. Navega al directorio del proyecto:
    ```sh
    cd app_medic
    ```
3. Compila el proyecto:
    ```sh
    ./mvnw clean install
    ```

## Configuración

La configuración de la aplicación se encuentra en el archivo [`application.properties`](src/main/resources/application.properties).

## Ejemplos de Uso

### Consultas

#### Buscar Consultas por DNI o Nombre Completo

```java
@Autowired
private IConsultRepo consultRepo;

public List<Consult> buscarConsultas(String dni, String fullname) {
    return consultRepo.search(dni, fullname);
}
```

#### Buscar Consultas por Rango de Fechas

```java
@Autowired
private IConsultRepo consultRepo;

public List<Consult> buscarConsultasPorFechas(LocalDateTime date1, LocalDateTime date2) {
    return consultRepo.searchByDates(date1, date2);
}
```

### Menús

#### Obtener Menús por Nombre de Usuario

```java
@Autowired
private IMenuService menuService;

public List<Menu> obtenerMenusPorUsuario(String username) {
    return menuService.getMenusByUsername(username);
}
```

### Manejo de Excepciones

```java
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().stream().map(
                e -> e.getField().concat(":").concat(e.getDefaultMessage().concat(" "))).collect(Collectors.joining());

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg,
                request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
```

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request.
