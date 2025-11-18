Aplicación de gestión de capacitaciones con:
- Backend: Spring Boot
- Base de datos: PostgreSQL
- Frontend: Vue 3
- Almacenamiento de archivos: MinIO (compatible con S3)
- Correo de prueba: MailHog
- Todos los servicios se ejecutan en contenedores Docker.
Requisitos
- Docker y Docker Compose
Ejecución de la aplicación
Cloran el repositorio en el directorio deseado
- cd DirectorioDeseado
- git clone https://github.com/JulianDiaz-Dev/Capacitaciones-App
- cd Capacitaciones-App
Ejecutar Docker Compose para montar todos los contenedores
- docker-compose up -d --build
Descargar MinIo
- Ir a: https://min.io/download
- Descargar el MinIO Client para Windows (mc.exe)
- Guardar mc.exe en un directorio, por ejemplo: C:\MinIO\
Configurar MinIO publico y crear bucket
- C:\MinIO\mc.exe alias set myminio http://localhost:9000 minioadmin minioadmin
- C:\MinIO\mc.exe mb myminio/archivos
- C:\MinIO\mc.exe anonymous set download myminio/archivos
Acceder a la BD para creación de datos
- docker exec -it postgres_db psql -U miusuario -d mibasedatos
Ejecutar Scripts

insert into rol(nombre) values('ADMIN');
insert into rol(nombre) values('USER');

--password 123456

insert into usuario(nombre,apellido,password,email,activo,contrasenia_temporal,insignias)
values('Julian','Diaz','$2a$12$mk0pIhZJ762trvZHn8cgbOBQZTunFZZHYsBYyDv.43BH2omd6kUfG','julian@gmail.com',true,false,2);

insert into usuario(nombre,apellido,password,email,activo,contrasenia_temporal,insignias)
values('Andres','Perez','$2a$12$mk0pIhZJ762trvZHn8cgbOBQZTunFZZHYsBYyDv.43BH2omd6kUfG','andres@gmail.com',true,true,1);

insert into usuario(nombre,apellido,password,email,activo,contrasenia_temporal,insignias)
values('Maria','Lopez','$2a$12$mk0pIhZJ762trvZHn8cgbOBQZTunFZZHYsBYyDv.43BH2omd6kUfG','maria@gmail.com',true,false,1);

insert into usuario(nombre,apellido,password,email,activo,contrasenia_temporal,insignias)
values('Carlos','Martinez','$2a$12$mk0pIhZJ762trvZHn8cgbOBQZTunFZZHYsBYyDv.43BH2omd6kUfG','carlos@gmail.com',true,true,0);

insert into usuario(nombre,apellido,password,email,activo,contrasenia_temporal,insignias)
values('Lucia','Gomez','$2a$12$mk0pIhZJ762trvZHn8cgbOBQZTunFZZHYsBYyDv.43BH2omd6kUfG','lucia@gmail.com',true,false,0);

insert into usuario_roles(id_rol,id_usuario) values(1,1);
insert into usuario_roles(id_rol,id_usuario) values(2,1);
insert into usuario_roles(id_rol,id_usuario) values(2,2);
insert into usuario_roles(id_rol,id_usuario) values(2,3);
insert into usuario_roles(id_rol,id_usuario) values(2,4);
insert into usuario_roles(id_rol,id_usuario) values(1,5);

insert into capacitacion(titulo,descripcion,fecha_creacion)
values('Spring Boot','Curso completo de Spring Boot con ejemplos y buenas prácticas',CURRENT_TIMESTAMP);

insert into seccion(titulo,orden,id_capacitacion)
values ('Model',1,1),
       ('Controller',2,1),
       ('Repository',3,1);

insert into entrada(orden,id_seccion,titulo,contenido,archivo_url)
values 
(1,1,'Modelo de Datos','En Spring Boot, los modelos representan las entidades de la aplicación y se mapean a tablas de la base de datos mediante JPA/Hibernate. Se recomienda usar anotaciones como @Entity, @Id, @GeneratedValue y relaciones @OneToMany o @ManyToOne para definir relaciones.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/200px-Spring_Framework_Logo_2018.png'),
(2,2,'Controladores','Los controladores gestionan las solicitudes HTTP y devuelven las respuestas. Se usan anotaciones como @RestController y @RequestMapping. Un buen diseño separa la lógica de negocio de la lógica de presentación.', 'https://cdn-icons-png.flaticon.com/512/919/919841.png'),
(3,3,'Repositorio','Los repositorios permiten acceder a la base de datos mediante interfaces que extienden JpaRepository o CrudRepository. Esto facilita operaciones CRUD y consultas personalizadas con JPQL o Query Methods.', 'https://cdn-icons-png.flaticon.com/512/1077/1077042.png');

insert into capacitacion(titulo,descripcion,fecha_creacion)
values('Java Básico','Curso de fundamentos de Java, programación orientada a objetos y estructuras de datos',CURRENT_TIMESTAMP);

insert into seccion(titulo,orden,id_capacitacion)
values ('Sintaxis',1,2),
       ('POO',2,2),
       ('Colecciones',3,2);

insert into entrada(orden,id_seccion,titulo,contenido,archivo_url)
values 
(1,1,'Conceptos Básicos','Java es un lenguaje de programación orientado a objetos. Aprende variables, tipos de datos, operadores, estructuras de control como if, switch y bucles como for y while. La práctica constante es clave para dominar la sintaxis.', 'https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.png'),
(2,2,'Programación Orientada a Objetos','Clases, objetos, herencia, polimorfismo, encapsulación y abstracción. Diseñar correctamente tus clases permite reutilización y mantenimiento eficiente del código. También se deben aplicar patrones de diseño cuando sea necesario.', 'https://cdn-icons-png.flaticon.com/512/919/919831.png'),
(3,3,'Colecciones Java','Aprender sobre List, Set, Map y Queue es fundamental. Estas estructuras permiten almacenar y manipular datos de forma eficiente. Ejemplos prácticos de ArrayList, HashSet y HashMap son imprescindibles para cualquier desarrollador.', 'https://cdn-icons-png.flaticon.com/512/906/906334.png');

insert into capacitacion(titulo,descripcion,fecha_creacion)
values('Vue 3','Curso completo de Vue.js 3, desde instalación hasta componentes avanzados',CURRENT_TIMESTAMP);

insert into seccion(titulo,orden,id_capacitacion)
values ('Instalación',1,3),
       ('Componentes',2,3),
       ('Directivas',3,3);

insert into entrada(orden,id_seccion,titulo,contenido,archivo_url)
values 
(1,1,'Instalando Vue 3','Vue 3 es un framework progresivo de JavaScript para construir interfaces de usuario. Se instala mediante npm o yarn. Se recomienda configurar un proyecto con Vite para un desarrollo más rápido.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Vue.js_Logo_2.svg/200px-Vue.js_Logo_2.png'),
(2,2,'Componentes Básicos','Los componentes son bloques reutilizables de la interfaz. Cada componente tiene template, script y estilo. Se pueden pasar props y emitir eventos entre componentes para comunicación.', 'https://cdn-icons-png.flaticon.com/512/919/919825.png'),
(3,3,'Directivas Comunes','Directivas como v-if, v-for, v-model y v-bind permiten manipular el DOM dinámicamente. Son esenciales para construir interfaces interactivas y reactivas.', 'https://cdn-icons-png.flaticon.com/512/906/906292.png');

insert into capacitacion(titulo,descripcion,fecha_creacion)
values('Docker','Curso completo de Docker y contenedores, incluyendo Docker Compose y mejores prácticas',CURRENT_TIMESTAMP);

insert into seccion(titulo,orden,id_capacitacion)
values ('Contenedores',1,4),
       ('Dockerfile',2,4),
       ('Compose',3,4);

insert into entrada(orden,id_seccion,titulo,contenido,archivo_url)
values 
(1,1,'Qué es un contenedor','Los contenedores son entornos ligeros que permiten ejecutar aplicaciones de manera aislada y reproducible. Contienen todo lo necesario: código, dependencias y configuraciones.', 'https://www.docker.com/wp-content/uploads/2022/03/Moby-logo.png'),
(2,2,'Dockerfile','Un Dockerfile es un archivo de texto con instrucciones para construir una imagen Docker. Incluye FROM, RUN, COPY, WORKDIR, EXPOSE y CMD para definir cómo se debe construir la imagen.', 'https://cdn-icons-png.flaticon.com/512/919/919825.png'),
(3,3,'Docker Compose','Docker Compose permite definir y ejecutar aplicaciones multi-contenedor mediante un archivo docker-compose.yml. Se pueden definir servicios, redes y volúmenes.', 'https://cdn-icons-png.flaticon.com/512/906/906292.png');

insert into usuario_capacitacion (id_usuario, id_capacitacion, progreso)
values
(1, 1, 0),
(1, 2, 0),
(1, 3, 0),
(1, 4, 0);

insert into usuario_capacitacion (id_usuario, id_capacitacion, progreso)
values
(2, 2, 0),
(2, 3, 0),
(3, 1, 0),
(4, 4, 0),
(5, 1, 0),
(5, 3, 0);
