FROM node:18

WORKDIR /app/

# Copiar el código fuente al contenedor
COPY . .

# Instalar Angular CLI y dependencias del proyecto
RUN npm install @angular/cli@15.0.4 -g \
    && npm install --legacy-peer-deps --force \
    && ng config -g cli.warnings.versionMismatch false

# Construir la aplicación Angular
RUN ng build

RUN apt-get update && apt-get install -y nginx && rm -rf /var/cache/apt/archives /var/lib/apt/lists/* \
    && rm -f /var/log/nginx/access.log && rm -f /var/log/nginx/error.log \
    && ln -s /dev/stderr /var/log/nginx/error.log && ln -s /dev/stdout /var/log/nginx/access.log

# Copiar build completo a Nginx
RUN cp -r dist/frontend-personas/* /var/www/html/

# Configuraciones de Nginx
COPY nginx.conf /etc/nginx/nginx.conf
RUN nginx -t && service nginx reload

CMD ["nginx", "-g", "daemon off;"]

EXPOSE 80

