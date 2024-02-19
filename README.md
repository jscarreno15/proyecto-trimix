# proyecto-trimix

## Prerequisitos
Antes de comenzar, asegúrate de tener instalado Docker en tu sistema.

## Levantar la Aplicación
Para levantar la aplicación, sigue estos pasos:

1. Abre una terminal o línea de comandos.
2. Navega hasta el directorio raíz de la aplicación.
3. Ejecuta el siguiente comando:

   docker-compose up

Este comando iniciará los contenedores necesarios para ejecutar la aplicación.

Acceder a la Aplicación
Una vez que los contenedores estén en funcionamiento, puedes acceder a la aplicación a través de tu navegador web. URL en tu navegador:

http://localhost:4200/inicio

Documentación de Swagger
La aplicación cuenta con documentación de Swagger que describe sus endpoints y funcionalidades. URL en tu navegador:

http://localhost:8080/swagger-ui.html#

## informacion adicional:
Esta aplicación cuenta con una base de datos tipo H2. La misma, al iniciar no contiene registros. Se recomienda, primeramente generar inserciones para ver el funcionamiento de la tabla.