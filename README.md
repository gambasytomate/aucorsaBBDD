# AUCORSA

![Java](https://img.shields.io/badge/Java-17+-red)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![Maven](https://img.shields.io/badge/Maven-3.8+-yellow)
![Swing](https://img.shields.io/badge/GUI-Swing-orange)
![Estado](https://img.shields.io/badge/estado-entregable-brightgreen)

> Aplicación de escritorio con interfaz gráfica (Java Swing) para gestionar la base de datos `Aucorsa` (tablas y datos precargados). Proyecto académico para la asignatura de Programación.

## Tabla de contenidos
- [Descripcion](#descripcion)
- [Caracteristicas](#caracteristicas)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Requisitos previos](#requisitos-previos)
- [Configuracion de la base de datos](#configuracion-de-la-base-de-datos)
- [Descarga del archivo](#descarga-del-archivo)
- [Ejecucion de la aplicacion](#ejecucion-de-la-aplicacion)
- [Uso de la interfaz grafica](#uso-de-la-interfaz-grafica)
  

## Descripcion
**AUCORSA** es una aplicacion Java que proporciona una interfaz grafica construida con **Swing** para gestionar una base de datos MySQL local llamada `Aucorsa`. La base de datos ya incluye tablas y valores por defecto (proporcionados en el archivo `Aucorsa.sql`). La aplicacion permite realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) sobre los datos de forma visual, sin necesidad de escribir SQL directamente.

Este proyecto ha sido desarrollado como trabajo de clase para el modulo de Programacion, utilizando **IntelliJ IDEA** como entorno de desarrollo y **Maven** para la gestion de dependencias.

## Caracteristicas
- Interfaz grafica intuitiva con componentes Swing (JFrame, JTable, JButton, etc.).
- Conexion directa a MySQL mediante el conector oficial.
- Operaciones CRUD sobre las tablas predefinidas.
- Visualizacion de datos en tablas dinamicas (JTable).
- Botones para insertar, modificar, eliminar y refrescar registros.
- Mensajes de error controlados (errores de conexion, SQL, etc.).
- Base de datos precargada con datos de ejemplo.

## Tecnologias utilizadas
- **Lenguaje**: Java 17 o superior.
- **IDE recomendado**: IntelliJ IDEA (Community o Ultimate).
- **Gestor de dependencias**: Maven.
- **Conector MySQL**: `mysql-connector-j` version 9.4.0.
- **Base de datos**: MySQL 8.0+ (local, no online).
- **Interfaz grafica**: Java Swing (incluido en el JDK, no requiere dependencias extra).
- **Plugin de ejecucion**: `exec-maven-plugin` para lanzar la clase `app.Principal`.

## Requisitos previos
Antes de empezar, asegurate de tener instalado:

- **Java JDK** (version 17 o superior).  
  [Descargar de Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o [OpenJDK](https://adoptium.net/).
- **MySQL Server** (version 8.0 o superior).  
  [Descargar MySQL](https://dev.mysql.com/downloads/mysql/).
- **IntelliJ IDEA** (Community Edition es suficiente).  
  [Descargar IntelliJ](https://www.jetbrains.com/idea/download/).
- **Git** (opcional, para clonar el repositorio).  
  [Descargar Git](https://git-scm.com/).

## Configuracion de la base de datos

La base de datos `Aucorsa` se entrega lista para usar. Solo tienes que ejecutar el archivo `Aucorsa.sql` que se encuentra en la carpeta `sql/` del proyecto. Ese archivo contiene todas las instrucciones necesarias para crear la base de datos, sus tablas y los valores iniciales.

Pasos:

1. Asegurate de que el servidor MySQL este en marcha.
2. Abre tu cliente MySQL preferido (MySQL Workbench, DBeaver, o la linea de comandos).
3. Conectate con un usuario con privilegios (por ejemplo, `root` y contraseña `1234`).
4. Abre el archivo `Aucorsa.sql` (se encuentra en `sql/Aucorsa.sql` dentro del repositorio).
5. Ejecuta el script completo. Esto creara automaticamente la base de datos `Aucorsa`, sus tablas y los datos de ejemplo.

No necesitas crear la base de datos manualmente; el script ya lo hace.

Nota: La aplicacion utiliza la clase ConnectionBBDD con los siguientes parametros fijos:

URL: jdbc:mysql://127.0.0.1:3306/Aucorsa

Usuario: root

Contrasena: 1234

Por lo tanto, es obligatorio que tu servidor MySQL local tenga exactamente esas credenciales y que la base de datos se llame Aucorsa
Si deseas cambiarlo, deberas modificar la clase ConnectionBBDD.

Si usas la linea de comandos, puedes hacer:
mysql -u root -p1234 < ruta_del_proyecto/sql/Aucorsa.sql


## Descarga del archivo
Opcion A – Clonar desde IntelliJ (recomendada)
1. Abre IntelliJ.
2. En la pantalla de bienvenida, selecciona Get from VCS (o desde el menu: File -> New -> Project from Version Control).
3. En la URL del repositorio, introduce:
   https://github.com/gambasytomate/aucorsaBBDD
5. Elige el directorio de destino y haz clic en Clone.
6. IntelliJ reconocera automaticamente el pom.xml y cargara las dependencias.

Opcion B – Descargar los archivos directamente
1. Ve a la pagina del repositorio en GitHub y haz clic en Code -> Download ZIP.
2. Extrae el ZIP en una carpeta.
3. En IntelliJ, selecciona File -> Open y elige la carpeta del proyecto.
4. IntelliJ importara el proyecto Maven (si no lo hace automaticamente, haz clic derecho en pom.xml y elige Add as Maven Project).
Una vez importado, espera a que Maven descargue la dependencia mysql-connector-j y el plugin exec-maven-plugin.

## Uso de la interfaz grafica

Cuando lo ejecutes te encontrarás con arriba unas pestañas que cada una será específicamente de una de las tablas, arriba del todo unos botones de añadir, eliminar, refrescar y modificar.
si le haces click a alguno de esos botones te dará la opción de alterar la tabla.
Para ir a la información detallada tienes que darle doble click a alguna de las casillas, igualmente no se preocupe porque puede navegar dentro de esa pestaña hacia otras filas.
En la Información detallada aparecerá un botón de editar abajo a la derecha donde le tendrá que dar click para activar el modo de edición, cuando termines de editar vuelve a darle al botón para guardan los cambios.

## Ejecucion de la aplicacion
Para lanzar la interfaz grafica (Swing), ejecuta la clase principal app.Principal
