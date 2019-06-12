FROM ubuntu:16.04
MAINTAINER Fran Trapero Cerezo "frantracer@gmail.com"

RUN apt-get update -y
RUN apt-get install -y python3-pip python3-dev build-essential
RUN pip3 install --upgrade pip

RUN mkdir -p /app
WORKDIR /app

ADD ./src src
ADD ./goods goods

COPY goods/coretents-api.ini /etc/coretents-api.ini
RUN pip3 install -r goods/requirements.txt

EXPOSE 51415

CMD ["python3", "/app/src/app.py"]