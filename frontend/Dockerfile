FROM nginx:1.27-alpine

# Configuración propia: sirve los estáticos y hace de proxy hacia el backend.
COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY . /usr/share/nginx/html

EXPOSE 80
