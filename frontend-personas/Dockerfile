FROM node:18

WORKDIR /app/

# Copiar el código fuente al contenedor
COPY . .

RUN npm install @angular/cli@15.0.4 -g 
RUN npm install --legacy-peer-deps --force 

RUN apt-get update && apt-get install -y nginx && rm -rf /var/cache/apt/archives /var/lib/apt/lists/*

RUN ng config -g cli.warnings.versionMismatch false

# Borro carpeta de builds previos, mas que nada para moverme entre environments.
RUN rm -r -f dist/frontend-personas/

RUN ng build 

# Borro carpetas para evitar problemas de actualizaciones de archivos yml
RUN rm -r /var/www/html/* /usr/share/nginx/html/*

# Copiar build completo
RUN cp -r /app/frontend-almacenes/dist/frontend-personas/* /var/www/html/
RUN cp -r /app/frontend-almacenes/dist/frontend-personas/* /usr/share/nginx/html/


RUN rm -f /var/log/nginx/access.log && rm -f /var/log/nginx/error.log && ln -s /dev/stderr /var/log/nginx/error.log && ln -s /dev/stdout /var/log/nginx/access.log

# Configuraciones de nginx y reinicio del server.

RUN cp nginx.conf /etc/nginx/nginx.conf 
RUN nginx -t && service nginx reload

CMD ["nginx", "-g", "daemon off;"]

EXPOSE 4200
