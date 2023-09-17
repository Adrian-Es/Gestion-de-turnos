
# Gestion de Turnos
Prueba técnica para Certant, consiste en un proyecto MVC que gestiona los turnos de una clínica.

# Dependencias
Este proyecto requiere tener instalado, como mínimo, Java 17, Maven 3.8.6 y MySQL Workbench 8.0 (que vendrá con MySQL server)

# Antes de ejecutar
Se debera acceder a MySql Workbench y ejecutar la peticion:
```
CREATE DATABASE IF NOT EXISTS `gestion-de-consultorios`;
```
Y en ```src/main/resources``` se deben configurar las siguientes líneas del ```application.properties```:
```
# Con la url donde se ejecutará la base de datos del proyecto (ej: localhost)
enviroment.db.host.url =
# Con el puerto donde se ejecutará la base de datos del proyecto (ej: 3306) 
enviroment.db.host.port = 
# Con el usuario de la base de datos (ej: root)
enviroment.db.host.username = 
# Con la contraseña de la base de datos (ej: root)
enviroment.db.host.password = 
```
# Ejecutando el proyecto
Es recomendable iniciar el proyecto mediante la herramienta ```Spring Tool Suite```, ejecutando el archivo ```src/main/java/com/prueba/GestionDeTunosCertantApplication.java```

Importante: es necesario que el puerto local 8080 esté disponible porque ahí se va a ejecutar el proyecto.

A partir de aquí, el proyecto se estará ejecutando en la url y el puerto especificados en el ```application.properties```
# Breve explicación del funcionamiento
Dentro de la pagina van a poder encontrarse varias categorias:
## Turnos
Se pueden ver todos los turnos, además de ingresar a las páginas específicas de su médico, paciente y consultorio.

Se van a poder agregar nuevos turnos siempre y cuando el médico, el paciente y el consultorio estén disponibles en ese horario; el consultorio se adecue a la consulta de ese médico; y el horario sea válido.

También se van a poder modificar y eliminar turnos siempre y cuando no reste una hora para su realización.
## Medicos
Se pueden ver todos los médicos y sus especialidades, agregar nuevos médicos y ver la página personal de cada uno donde estarán todos sus turnos y horarios; también, desde su página, se pueden agregar nuevos horarios en los que el médico va a estar disponible.
## Pacientes
Se pueden ver todos los pacientes y agregar más, además de ver la página personal de cada uno con su información y turnos.
## Especialidades
Se pueden ver todas las especialidades y agregar más. En la página de cada especialidad se verán todos los turnos de la misma.
## Consultorios
Está el listado de todos los consultorios, pudiéndose agregar nuevos. Dentro de la página de cada consultorio, se pueden ver las especialidades médicas para las cuales está adecuado y se puede agregarle nuevas.
