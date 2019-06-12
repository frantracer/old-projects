FROM ubuntu:16.04
MAINTAINER Fran Trapero Cerezo "frantracer@gmail.com"

RUN apt-get update -y
RUN apt-get install -y nginx

# Main configuration
RUN mv /etc/nginx/nginx.conf /etc/nginx/nginx-backup.conf
ADD ./goods/nginx.conf /etc/nginx/
ADD ./goods/sites/* /etc/nginx/sites-available/

# Source code of the web
ADD ./src /app/src

CMD rm -rf /var/www/html
RUN ln -sfn /app/src /var/www/html

# SSL certificates
# To generate self-signed certicates execute:
# openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ./goods/credentials/corentents-web.key -out ./goods/credentials/corentents-web.crt
#
# If you have valid certificates replace the .crt and .key files before building the dockerfile
ADD ./goods/credentials/* /etc/nginx/ssl/


EXPOSE 80 443

ADD ./goods/entrypoint.pl /
ENTRYPOINT ["perl", "/entrypoint.pl"]
CMD ["production"]