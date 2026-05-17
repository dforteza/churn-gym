# GUÍA DE TESTING - CHULETA COMPLETA
## JUnit 5 + Mockito + MockMvc

---

# ÍNDICE

1. [Tipos de Tests](#1-tipos-de-tests)
2. [Anotaciones](#2-anotaciones)
3. [Assertions (JUnit)](#3-assertions)
4. [Mockito - Configuración](#4-mockito)
5. [Mockito - Verificaciones](#5-verificaciones)
6. [MockMvc - Tests de Controllers](#6-mockmvc)
7. [JsonPath - Verificar JSON](#7-jsonpath)
8. [Estructura de Tests](#8-estructura)
9. [Patrones Comunes](#9-patrones)

---

# 1. TIPOS DE TESTS

## 1.1 Test Unitario vs Test de Integración

| Aspecto | Test Unitario | Test de Integración |
|---------|---------------|---------------------|
| **Objetivo** | Probar lógica de negocio aislada | Probar endpoint HTTP completo |
| **Capa** | Service, Repository | Controller |
| **Dependencias** | Mockeadas (@Mock) | Mockeadas parcialmente |
| **Anotación** | @ExtendWith(MockitoExtension.class) | @WebMvcTest |
| **Contexto Spring** | NO carga contexto | SÍ carga contexto parcial |
| **Velocidad** | ⚡ Rápido | 🐢 Más lento |
| **Ejemplo** | EventServiceTest | EventControllerTest |

---

# 2. ANOTACIONES

## 2.1 Anotaciones de Clase

### Test Unitario

| Anotación | Propósito | Ejemplo |
|-----------|-----------|---------|
| `@ExtendWith(MockitoExtension.class)` | Habilita Mockito en JUnit 5 | `@ExtendWith(MockitoExtension.class)` |

### Test de Integración (Controller)

| Anotación | Propósito | Parámetros Importantes |
|-----------|-----------|------------------------|
| `@WebMvcTest` | Test de controller con MockMvc | `controllers = EventController.class` |
| | | `excludeAutoConfiguration = {SecurityAutoConfiguration.class}` |
| | | `excludeFilters = @ComponentScan.Filter(...)` |

**Ejemplo completo:**
```java
@WebMvcTest(
    controllers = EventController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, 
        classes = {JwtAuthenticationFilter.class, JwtGenerator.class}
    )
)
```

---

## 2.2 Anotaciones de Atributos

### Test Unitario

| Anotación | Propósito | Uso |
|-----------|-----------|-----|
| `@Mock` | Crear mock de dependencia | `@Mock private EventRepository eventRepository;` |
| `@InjectMocks` | Inyectar mocks en clase a probar | `@InjectMocks private EventService eventService;` |

### Test de Integración

| Anotación | Propósito | Uso |
|-----------|-----------|-----|
| `@Autowired` | Inyectar bean de Spring | `@Autowired private MockMvc mockMvc;` |
| `@TestConfiguration` | Configuración específica de test | Clase interna con `@Bean` |
| `@Primary` | Bean prioritario en contexto | `@Bean @Primary EventService eventService()` |

---

## 2.3 Anotaciones de Métodos

| Anotación | Propósito | Cuándo usar |
|-----------|-----------|-------------|
| `@Test` | Marcar método como test | Todos los métodos de test |
| `@DisplayName("...")` | Nombre descriptivo del test | Siempre (buena práctica) |
| `@BeforeEach` | Ejecutar antes de cada test | Inicializar datos comunes |
| `@AfterEach` | Ejecutar después de cada test | Limpiar recursos |
| `@BeforeAll` | Ejecutar UNA vez antes de todos | Configuración pesada (static) |
| `@AfterAll` | Ejecutar UNA vez después de todos | Limpieza final (static) |

---

## 2.4 Anotaciones de Seguridad

| Anotación | Propósito | Ejemplo |
|-----------|-----------|---------|
| `@WithMockUser` | Simular usuario autenticado | `@WithMockUser(username = "admin", roles = "ADMIN")` |

**Parámetros:**
- `username`: Nombre de usuario simulado
- `roles`: Array de roles (sin prefijo ROLE_)
- `authorities`: Array de permisos (con prefijo ROLE_)

---

# 3. ASSERTIONS (JUnit)

## 3.1 Assertions Básicos

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `assertEquals(esperado, actual)` | Verificar igualdad | `assertEquals(1L, event.getId())` |
| `assertNotEquals(esperado, actual)` | Verificar diferencia | `assertNotEquals(0L, event.getId())` |
| `assertNull(objeto)` | Verificar que es null | `assertNull(event.getCategory())` |
| `assertNotNull(objeto)` | Verificar que NO es null | `assertNotNull(foundEvent)` |
| `assertTrue(condicion)` | Verificar que es true | `assertTrue(event.getSpeakers().isEmpty())` |
| `assertFalse(condicion)` | Verificar que es false | `assertFalse(event.getSpeakers().contains(speaker))` |

---

## 3.2 Assertions de Colecciones

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `assertEquals(size, collection.size())` | Verificar tamaño | `assertEquals(2, event.getSpeakers().size())` |
| `assertTrue(collection.contains(item))` | Verificar contenido | `assertTrue(speakers.contains(speaker1))` |
| `assertTrue(collection.isEmpty())` | Verificar vacío | `assertTrue(event.getSpeakers().isEmpty())` |

---

## 3.3 Assertions de Excepciones

| Método | Propósito | Uso |
|--------|-----------|-----|
| `assertThrows(Exception.class, () -> {...})` | Capturar y verificar excepción | Ver ejemplo abajo |

**Ejemplo completo:**
```java
ResourceNotFoundException thrown = assertThrows(
    ResourceNotFoundException.class, 
    () -> eventService.findById(99L)
);

assertEquals("Evento no encontrado con id: 99", thrown.getMessage());
```

---

# 4. MOCKITO - CONFIGURACIÓN

## 4.1 Métodos when() - Definir Comportamiento

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `when(mock.metodo()).thenReturn(valor)` | Retornar valor específico | `when(repo.findById(1L)).thenReturn(Optional.of(event))` |
| `when(mock.metodo()).thenThrow(excepcion)` | Lanzar excepción | `when(repo.findById(99L)).thenThrow(new ResourceNotFoundException(...))` |
| `when(mock.metodo()).thenAnswer(invocation -> {...})` | Comportamiento personalizado | Ver ejemplo abajo |
| `doNothing().when(mock).metodo()` | Método void sin acción | `doNothing().when(service).deleteById(1L)` |
| `doThrow(excepcion).when(mock).metodo()` | Método void lanza excepción | `doThrow(new Exception()).when(service).delete(1L)` |

**Ejemplo thenAnswer:**
```java
when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
    Event savedEvent = invocation.getArgument(0);
    savedEvent.setId(1L);
    return savedEvent;
});
```

---

## 4.2 Matchers - Argumentos Flexibles

| Matcher | Propósito | Ejemplo |
|---------|-----------|---------|
| `any()` | Cualquier objeto | `any(Event.class)` |
| `anyLong()` | Cualquier Long | `anyLong()` |
| `anyString()` | Cualquier String | `anyString()` |
| `eq(valor)` | Valor exacto (combinar con any) | `verify(service, times(1)).update(eq(1L), any())` |
| `isNull()` | Argumento null | `isNull()` |
| `isNotNull()` | Argumento no null | `isNotNull()` |

**Regla importante:** Si usas un matcher, TODOS los argumentos deben usar matchers.

```java
// CORRECTO
verify(service, times(1)).update(eq(1L), any(EventRequestDto.class));

// INCORRECTO (mezcla valor exacto con matcher)
verify(service, times(1)).update(1L, any(EventRequestDto.class));
```

---

# 5. VERIFICACIONES (Mockito)

## 5.1 Métodos verify()

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `verify(mock).metodo()` | Verificar que se llamó 1 vez | `verify(repo).findById(1L)` |
| `verify(mock, times(n)).metodo()` | Verificar n llamadas | `verify(repo, times(2)).save(any())` |
| `verify(mock, never()).metodo()` | Verificar que NUNCA se llamó | `verify(repo, never()).deleteById(anyLong())` |
| `verify(mock, atLeastOnce()).metodo()` | Al menos 1 vez | `verify(mapper, atLeastOnce()).toDto(any())` |
| `verify(mock, atLeast(n)).metodo()` | Al menos n veces | `verify(repo, atLeast(2)).save(any())` |
| `verify(mock, atMost(n)).metodo()` | Como máximo n veces | `verify(repo, atMost(5)).findAll()` |

---

## 5.2 Verificadores de Interacción

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `verifyNoInteractions(mock)` | Sin interacciones | `verifyNoInteractions(repo)` |
| `verifyNoMoreInteractions(mock)` | No más interacciones | `verifyNoMoreInteractions(service)` |
| `reset(mock)` | Resetear mock | `reset(service, mapper)` |

---

# 6. MOCKMVC - TESTS DE CONTROLLERS

## 6.1 Inyección y Configuración

```java
@Autowired
private MockMvc mockMvc;

@Autowired
private ObjectMapper objectMapper;  // Para convertir objetos a JSON
```
MockMvc sirve para probar tus endpoints HTTP (controllers) sin levantar el servidor.
Es decir:

- No hay Tomcat
- No hay llamadas HTTP reales
- No hay navegador
- PERO Spring maneja la request como si fuera real

---

## 6.2 Métodos HTTP

| Método | Propósito | Import |
|--------|-----------|--------|
| `get(url)` | Request GET | `import static o.s.t.w.s.r.MockMvcRequestBuilders.get;` |
| `post(url)` | Request POST | `import static o.s.t.w.s.r.MockMvcRequestBuilders.post;` |
| `put(url)` | Request PUT | `import static o.s.t.w.s.r.MockMvcRequestBuilders.put;` |
| `delete(url)` | Request DELETE | `import static o.s.t.w.s.r.MockMvcRequestBuilders.delete;` |
| `patch(url)` | Request PATCH | `import static o.s.t.w.s.r.MockMvcRequestBuilders.patch;` |

**Import completo:**
```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
```

---

## 6.3 Estructura de Request

```java
mockMvc.perform(
    get("/api/v1/events/{id}", 1L)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto))
        .param("page", "0")
        .param("size", "10")
)
```

### Métodos de Request

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `accept(MediaType)` | Tipo aceptado en respuesta | `.accept(MediaType.APPLICATION_JSON)` |
| `contentType(MediaType)` | Tipo de contenido enviado | `.contentType(MediaType.APPLICATION_JSON)` |
| `content(String)` | Body del request (JSON) | `.content(objectMapper.writeValueAsString(dto))` |
| `param(key, value)` | Query parameter | `.param("page", "0")` |
| `header(key, value)` | Header HTTP | `.header("Authorization", "Bearer token")` |

---

## 6.4 Assertions de Response

### Status HTTP

| Método | HTTP Code | Ejemplo |
|--------|-----------|---------|
| `status().isOk()` | 200 | `.andExpect(status().isOk())` |
| `status().isCreated()` | 201 | `.andExpect(status().isCreated())` |
| `status().isNoContent()` | 204 | `.andExpect(status().isNoContent())` |
| `status().isBadRequest()` | 400 | `.andExpect(status().isBadRequest())` |
| `status().isNotFound()` | 404 | `.andExpect(status().isNotFound())` |
| `status().isUnauthorized()` | 401 | `.andExpect(status().isUnauthorized())` |
| `status().isForbidden()` | 403 | `.andExpect(status().isForbidden())` |

### Content

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `content().contentType(MediaType)` | Verificar tipo de contenido | `.andExpect(content().contentType(MediaType.APPLICATION_JSON))` |
| `content().string(texto)` | Verificar texto exacto | `.andExpect(content().string("Success"))` |

---

# 7. JSONPATH - VERIFICAR JSON

## 7.1 Sintaxis Básica

| JsonPath | Propósito | Ejemplo |
|----------|-----------|---------|
| `$.campo` | Campo raíz | `$.id` → `{"id": 1}` |
| `$.objeto.campo` | Campo anidado | `$.category.name` → `{"category": {"name": "Tech"}}` |
| `$.array[0]` | Primer elemento de array | `$.speakers[0].name` |
| `$.array.length()` | Longitud de array | `$.speakers.length()` → retorna 2 |
| `$[?(@.campo == 'valor')]` | Filtrar array | `$.speakers[?(@.name == 'Juan')]` |

---

## 7.2 Métodos jsonPath()

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| `jsonPath("$.campo").value(valor)` | Verificar valor exacto | `jsonPath("$.id").value(1)` |
| `jsonPath("$.campo").exists()` | Verificar que existe | `jsonPath("$.category").exists()` |
| `jsonPath("$.campo").doesNotExist()` | Verificar que NO existe | `jsonPath("$.speakers[5]").doesNotExist()` |
| `jsonPath("$.campo").isEmpty()` | Verificar vacío | `jsonPath("$.speakers").isEmpty()` |
| `jsonPath("$.campo").isNotEmpty()` | Verificar NO vacío | `jsonPath("$.speakers").isNotEmpty()` |
| `jsonPath("$.campo").isArray()` | Verificar que es array | `jsonPath("$.speakers").isArray()` |

---

## 7.3 Ejemplos Completos

### Verificar Campo Simple
```java
.andExpect(jsonPath("$.id").value(1))
.andExpect(jsonPath("$.name").value("Conferencia Tech"))
.andExpect(jsonPath("$.location").value("Online"))
```

### Verificar Objeto Anidado
```java
.andExpect(jsonPath("$.category.id").value(10))
.andExpect(jsonPath("$.category.name").value("Tecnología"))
```

### Verificar Array
```java
.andExpect(jsonPath("$.speakers.length()").value(2))
.andExpect(jsonPath("$.speakers[0].name").value("Juan Pérez"))
.andExpect(jsonPath("$.speakers[1].email").value("maria@example.com"))
```

### Verificar Array con Filtro (sin asumir orden)
```java
.andExpect(jsonPath("$.speakers[?(@.name == 'Juan Pérez')].name").value("Juan Pérez"))
.andExpect(jsonPath("$.speakers[?(@.name == 'Juan Pérez')].email").value("juan@example.com"))
```

### Verificar Paginación
```java
.andExpect(jsonPath("$.content[0]").exists())
.andExpect(jsonPath("$.content[1]").exists())
.andExpect(jsonPath("$.content[2]").doesNotExist())
.andExpect(jsonPath("$.totalElements").value(2))
.andExpect(jsonPath("$.totalPages").value(1))
.andExpect(jsonPath("$.pageable.pageNumber").value(0))
.andExpect(jsonPath("$.pageable.pageSize").value(10))
```

---

# 8. ESTRUCTURA DE TESTS

## 8.1 Patrón AAA (Arrange-Act-Assert)

```java
@Test
@DisplayName("Descripción clara del test")
void nombreDescriptivo() {
    // ARRANGE (Preparación)
    when(mock.metodo()).thenReturn(valor);
    
    // ACT (Ejecución)
    Result result = service.metodo();
    
    // ASSERT (Verificación)
    assertNotNull(result);
    assertEquals(expected, result);
    verify(mock, times(1)).metodo();
}
```

---

## 8.2 Nomenclatura de Tests

### Patrón: `should[Acción]When[Condición]`

| Ejemplo | Descripción |
|---------|-------------|
| `shouldReturnEventWhenIdExists` | Retornar evento cuando ID existe |
| `shouldThrowExceptionWhenIdNotFound` | Lanzar excepción cuando ID no existe |
| `shouldSaveEventSuccessfully` | Guardar evento exitosamente |
| `shouldReturnPagedEvents` | Retornar eventos paginados |

---

## 8.3 @BeforeEach - Inicialización Común

```java
@BeforeEach
void setUp() {
    // Inicializar objetos comunes a todos los tests
    category = new Category(1L, "Tech", "Tecnología");
    
    event = new Event();
    event.setId(1L);
    event.setName("Spring Conf");
    event.setCategory(category);
    
    eventDto = new EventResponseDto();
    eventDto.setId(1L);
    eventDto.setName("Spring Conf");
}
```

**Para @WebMvcTest con mocks inyectados:**
```java
@BeforeEach
void setUp(@Autowired EventService eventServiceMock, @Autowired EventMapper eventMapperMock) {
    this.eventService = eventServiceMock;
    this.eventMapper = eventMapperMock;
    
    reset(eventService, eventMapper);  // Limpiar interacciones previas
    
    // Inicializar datos...
}
```

---

# 9. PATRONES COMUNES

## 9.1 Test Unitario - Service (findById)

```java
@Test
@DisplayName("Debe retornar un Evento cuando el ID existe")
void shouldReturnEventWhenIdExists() {
    // Arrange
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    
    // Act
    Event foundEvent = eventService.findById(1L);
    
    // Assert
    assertNotNull(foundEvent);
    assertEquals(1L, foundEvent.getId());
    verify(eventRepository, times(1)).findById(1L);
}
```

---

## 9.2 Test Unitario - Excepciones

```java
@Test
@DisplayName("Debe lanzar ResourceNotFoundException cuando el ID no existe")
void shouldThrowExceptionWhenIdNotFound() {
    // Arrange
    when(eventRepository.findById(99L)).thenReturn(Optional.empty());
    
    // Act & Assert
    ResourceNotFoundException thrown = assertThrows(
        ResourceNotFoundException.class,
        () -> eventService.findById(99L)
    );
    
    assertEquals("Evento no encontrado con id: 99", thrown.getMessage());
    verify(eventRepository, times(1)).findById(99L);
}
```

---

## 9.3 Test Unitario - Save con thenAnswer

```java
@Test
@DisplayName("Debe guardar un Evento exitosamente")
void shouldSaveEventSuccessfully() {
    // Arrange
    Event eventWithoutId = new Event();
    eventWithoutId.setName("Spring Conf");
    
    when(eventMapper.toEntity(any(EventRequestDto.class))).thenReturn(eventWithoutId);
    when(categoryService.findById(1L)).thenReturn(category);
    
    when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
        Event savedEvent = invocation.getArgument(0);
        savedEvent.setId(1L);  // Simular generación de ID
        return savedEvent;
    });
    
    // Act
    Event savedEvent = eventService.save(eventRequestDto);
    
    // Assert
    assertNotNull(savedEvent);
    assertEquals(1L, savedEvent.getId());
    verify(eventRepository, times(1)).save(any(Event.class));
}
```

---

## 9.4 Test Integración - GET by ID

```java
@Test
@DisplayName("GET /api/v1/events/{id} - Debe retornar un evento por ID")
@WithMockUser(username = "user", roles = "USER")
void shouldReturnEventById() throws Exception {
    // Arrange
    when(eventService.findById(1L)).thenReturn(eventEntity);
    when(eventMapper.toResponseDto(eventEntity)).thenReturn(eventResponseDto);
    
    // Act & Assert
    mockMvc.perform(get("/api/v1/events/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON))
        
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Conferencia Tech"));
    
    verify(eventService, times(1)).findById(1L);
    verify(eventMapper, times(1)).toResponseDto(eventEntity);
}
```

---

## 9.5 Test Integración - GET con Paginación

```java
@Test
@DisplayName("GET /api/v1/events - Debe retornar eventos paginados")
@WithMockUser(username = "user", roles = "USER")
void shouldReturnPagedEvents() throws Exception {
    // Arrange
    List<EventResponseDto> events = List.of(eventDto1, eventDto2);
    Pageable pageable = PageRequest.of(0, 10);
    Page<EventResponseDto> page = new PageImpl<>(events, pageable, 2);
    
    when(eventService.findAll(eq("Spring"), any(Pageable.class))).thenReturn(page);
    
    // Act & Assert
    mockMvc.perform(get("/api/v1/events")
            .param("page", "0")
            .param("size", "10")
            .param("name", "Spring")
            .accept(MediaType.APPLICATION_JSON))
        
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(2))
        .andExpect(jsonPath("$.totalElements").value(2))
        .andExpect(jsonPath("$.totalPages").value(1));
    
    verify(eventService, times(1)).findAll(eq("Spring"), any(Pageable.class));
}
```

---

## 9.6 Test Integración - POST

```java
@Test
@DisplayName("POST /api/v1/events - Debe crear un evento")
@WithMockUser(username = "admin", roles = "ADMIN")
void shouldCreateEvent() throws Exception {
    // Arrange
    EventRequestDto requestDto = new EventRequestDto();
    requestDto.setName("Nuevo Evento");
    
    when(eventService.save(any(EventRequestDto.class))).thenReturn(savedEvent);
    when(eventMapper.toResponseDto(savedEvent)).thenReturn(responseDto);
    
    // Act & Assert
    mockMvc.perform(post("/api/v1/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.name").value("Nuevo Evento"));
    
    verify(eventService, times(1)).save(any(EventRequestDto.class));
}
```

---

## 9.7 Test Integración - PUT

```java
@Test
@DisplayName("PUT /api/v1/events/{id} - Debe actualizar un evento")
@WithMockUser(username = "admin", roles = "ADMIN")
void shouldUpdateEvent() throws Exception {
    // Arrange
    EventRequestDto updateDto = new EventRequestDto();
    updateDto.setName("Evento Actualizado");
    
    when(eventService.update(eq(1L), any(EventRequestDto.class))).thenReturn(updatedEvent);
    when(eventMapper.toResponseDto(updatedEvent)).thenReturn(updatedDto);
    
    // Act & Assert
    mockMvc.perform(put("/api/v1/events/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)))
        
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Evento Actualizado"));
    
    verify(eventService, times(1)).update(eq(1L), any(EventRequestDto.class));
}
```

---

## 9.8 Test Integración - DELETE

```java
@Test
@DisplayName("DELETE /api/v1/events/{id} - Debe eliminar un evento")
@WithMockUser(username = "admin", roles = "ADMIN")
void shouldDeleteEvent() throws Exception {
    // Arrange
    doNothing().when(eventService).deleteById(1L);
    
    // Act & Assert
    mockMvc.perform(delete("/api/v1/events/{id}", 1L))
        .andExpect(status().isNoContent());
    
    verify(eventService, times(1)).deleteById(1L);
}
```

---

## 9.9 Test Integración - 404 Not Found

```java
@Test
@DisplayName("GET /api/v1/events/{id} - Debe retornar 404 cuando no existe")
@WithMockUser(username = "user", roles = "USER")
void shouldReturn404WhenNotFound() throws Exception {
    // Arrange
    when(eventService.findById(99L)).thenThrow(
        new ResourceNotFoundException("Evento no encontrado con id: 99")
    );
    
    // Act & Assert
    mockMvc.perform(get("/api/v1/events/{id}", 99L)
            .accept(MediaType.APPLICATION_JSON))
        
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.message").value("Evento no encontrado con id: 99"));
    
    verify(eventService, times(1)).findById(99L);
}
```
