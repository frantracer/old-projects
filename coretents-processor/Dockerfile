FROM ubuntu:16.04
MAINTAINER Fran Trapero Cerezo "frantracer@gmail.com"

RUN apt-get update
RUN apt-get install -y apt-transport-https apt-utils

RUN echo "deb https://dl.bintray.com/sbt/debian /" > /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
RUN apt-get update
RUN apt-get install -y openjdk-8-jre openjdk-8-jdk
RUN apt-get install -y sbt

ADD ./src/ /app/src
ADD ./project/ /app/project
ADD ./build.sbt /app/

WORKDIR /app

ADD ./goods/entrypoint.pl /usr/bin/entrypoint
ENTRYPOINT ["perl", "/usr/bin/entrypoint"]
CMD ["production"]