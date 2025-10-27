Proyecto Spring Boot - Servicios SOAP y REST
📋 Descripción del Proyecto
Este proyecto implementa un sistema completo con servicios web SOAP y REST para la gestión de usuarios y teléfonos, desarrollado con Spring Boot 3.3.1. Cumple con todos los requisitos de la práctica EBAC Módulo 65 utilizando alternativas funcionales sin necesidad de software adicional.

🎯 Objetivos Cumplidos
✅ Parte I - Servicio Web SOAP
Proyecto nuevo con soporte Spring Boot

Servicio web SOAP con WSDL

WSDL que acepta al menos 1 parámetro de entrada

Implementación similar al ejercicio de referencia

✅ Parte II - Mejoras y Validaciones
Casos de prueba corregidos y completos

Logs extensivos en todas las clases relevantes

Validaciones de integridad en operaciones CRUD

Manejo de errores y respuestas apropiadas

🛠️ Tecnologías Utilizadas
Java 17

Spring Boot 3.3.1

Spring Web Services (SOAP)

Spring Data JPA

H2 Database (Base de datos en memoria/archivo)

Maven

JUnit 5 + Mockito (Testing)

Logback (Sistema de logging)

🔄 Alternativas Implementadas
Requisito Original	Alternativa Implementada	Estado
MySQL	H2 Database (embebida)	✅ Funcional
SoapUI	Curl/Invoke-RestMethod	✅ Funcional
Instalación adicional	Solo dependencias Maven	✅ Funcional
🏗️ Arquitectura del Proyecto
text
modulo65/
├── 📁 src/main/java/com/ebac/modulo65/
│   ├── 📁 config/          # Configuración SOAP
│   ├── 📁 controller/      # Controladores REST
│   ├── 📁 dto/            # Entidades JPA
│   ├── 📁 repository/      # Repositorios Data JPA
│   ├── 📁 service/         # Lógica de negocio
│   ├── 📁 soap/           # Endpoints SOAP
│   └── Modulo65Application.java
├── 📁 src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   ├── application-prod.properties
│   ├── logback.xml
│   └── usuarios.xsd
└── 📁 src/test/java/      # Pruebas unitarias
🚀 Cómo Ejecutar el Proyecto
Requisitos Previos
Java 17 o superior

Maven 3.6+

IntelliJ IDEA (recomendado) o cualquier IDE

Ejecución en IntelliJ
Abrir el proyecto en IntelliJ IDEA

Ejecutar la aplicación:

Click derecho en Modulo65Application.java

Seleccionar "Run Modulo65Application"

O usar el botón ▶️ verde

Verificar inicio:

text
✅ Aplicación iniciada en puerto 8080
✅ Perfil 'dev' activo (H2 Database)
✅ Tablas creadas automáticamente
Ejecución con Maven
bash
# Compilar y ejecutar
mvn clean spring-boot:run

# Solo ejecutar
mvn spring-boot:run

# Ejecutar tests
mvn test
🌐 Servicios Disponibles
🔗 REST APIs
Método	Endpoint	Descripción
GET	/usuarios	Obtener todos los usuarios
GET	/usuarios/{id}	Obtener usuario por ID
POST	/usuarios	Crear nuevo usuario
DELETE	/usuarios/{id}	Eliminar usuario
GET	/telefonos	Obtener todos los teléfonos
POST	/telefonos/{usuarioId}	Crear teléfono para usuario
GET	/telefonos/usuario/{usuarioId}	Obtener teléfonos por usuario
📡 Servicio SOAP
Elemento	URL
WSDL	http://localhost:8080/ws/usuarios.wsdl
Endpoint	http://localhost:8080/ws
NameSpace	http://ebac.com/modulo65/soap
🗄️ Base de Datos H2
Elemento	URL
Consola H2	http://localhost:8080/h2-console
JDBC URL	jdbc:h2:file:./data/modulo65
Usuario	sa
Contraseña	(vacío)
🧪 Pruebas y Ejemplos
Pruebas REST con PowerShell
powershell
# 1. Crear usuario
Invoke-RestMethod -Uri "http://localhost:8080/usuarios" -Method POST -ContentType "application/json" -Body '{"nombre":"Juan Perez","edad":25}'

# 2. Obtener usuarios
Invoke-RestMethod -Uri "http://localhost:8080/usuarios" -Method GET

# 3. Crear teléfono
Invoke-RestMethod -Uri "http://localhost:8080/telefonos/1" -Method POST -ContentType "application/json" -Body '{"numero":"5512345678","lada":"55","tipoTelefono":"Celular"}'

# 4. Probar validación (debe fallar)
Invoke-RestMethod -Uri "http://localhost:8080/telefonos/99" -Method POST -ContentType "application/json" -Body '{"numero":"123","lada":"55","tipoTelefono":"Celular"}'
Pruebas SOAP con curl
bash
# Obtener WSDL
curl http://localhost:8080/ws/usuarios.wsdl

# Consultar usuario vía SOAP
curl -X POST http://localhost:8080/ws \
-H "Content-Type: text/xml" \
-H "SOAPAction: http://ebac.com/modulo65/soap/getUsuarioRequest" \
-d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://ebac.com/modulo65/soap">
<soapenv:Header/>
<soapenv:Body>
<soap:getUsuarioRequest>
<soap:idUsuario>1</soap:idUsuario>
</soap:getUsuarioRequest>
</soapenv:Body>
</soapenv:Envelope>'
Consultas H2 Database
sql
-- Ver todas las tablas
SHOW TABLES;

-- Consultar usuarios
SELECT * FROM USUARIOS;

-- Consultar teléfonos con información de usuario
SELECT t.*, u.nombre as usuario_nombre
FROM TELEFONOS t
JOIN USUARIOS u ON t.usuario_id = u.id_usuario;
📊 Validaciones Implementadas
✅ Validaciones de Usuario
Nombre no puede estar vacío o nulo

Edad debe estar entre 0 y 150 años

No se puede eliminar usuario inexistente

✅ Validaciones de Teléfono
Número no puede estar vacío

Tipo de teléfono no puede estar vacío

No se puede crear teléfono con usuario inexistente

No se puede eliminar teléfono inexistente

✅ Respuestas de Error
json
{
"success": false,
"message": "Usuario no encontrado"
}
🧪 Suite de Pruebas
Pruebas Unitarias Implementadas
UsuarioControllerTest - 8 casos de prueba

TelefonoControllerTest - 3 casos de prueba

Modulo65ApplicationTests - Prueba de contexto

Cobertura de Casos
✅ Casos de éxito

✅ Casos de error (usuario no existe)

✅ Validaciones fallidas

✅ Operaciones de eliminación

📝 Configuración
Perfiles de Ejecución
Perfil	Base de Datos	Propósito
dev	H2 (archivo)	Desarrollo local
prod	MySQL	Producción
Archivos de Configuración
application.properties - Configuración principal

application-dev.properties - H2 Database

application-prod.properties - MySQL (para producción)

logback.xml - Configuración de logging

🔍 Monitoreo y Logs
Niveles de Log
INFO - Operaciones principales

DEBUG - Información detallada

WARN - Advertencias

ERROR - Errores críticos

Archivos de Log
logs/ebacSpringBootApp.log - Log principal

logs/archived/ - Logs rotados (máximo 10MB)

🎯 Características Destacadas
✨ Implementadas
✅ Servicio SOAP completo con WSDL automático

✅ API RESTful con respuestas estandarizadas

✅ Validaciones de integridad referencial

✅ Manejo de errores con respuestas apropiadas

✅ Logs extensivos con información contextual

✅ Pruebas unitarias completas

✅ Base de datos embebida sin instalación

✅ Configuración por perfiles (dev/prod)

🛡️ Seguridad de Datos
Validaciones en capa de servicio

Manejo de excepciones controlado

Respuestas HTTP apropiadas (200, 400, 404, 500)

Integridad referencial asegurada

📈 Estado del Proyecto
✅ COMPLETADO - 100% Funcional
Componente	Estado	Notas
Servicio SOAP	✅ Operativo	WSDL generado automáticamente
API REST	✅ Operativo	Todos los endpoints funcionales
Base de Datos	✅ Operativo	H2 embebida
Validaciones	✅ Implementadas	Integridad asegurada
Pruebas	✅ Completas	Cobertura extensiva
Logs	✅ Configurados	Múltiples niveles
Documentación	✅ Completa	Este README
🚨 Solución de Problemas
Error común: Puerto en uso
bash
# Cambiar puerto en application.properties
server.port=8081
Error: No se conecta a H2
Verificar URL: jdbc:h2:file:./data/modulo65

Asegurar que existe carpeta data/

Error: Dependencias faltantes
bash
# Actualizar dependencias Maven
mvn clean install
👥 Contribución
Este proyecto fue desarrollado como práctica educativa para EBAC Módulo 65, implementando todas las funcionalidades requeridas utilizando mejores prácticas y alternativas funcionales.

📄 Licencia
Proyecto educativo - Desarrollado para fines de aprendizaje.