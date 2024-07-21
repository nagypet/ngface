FROM nginx:latest

RUN apt-get update && apt-get install -y tzdata
RUN ln -fs /usr/share/zoneinfo/Europe/Budapest /etc/localtime && dpkg-reconfigure --frontend noninteractive tzdata

COPY dist/demo-app/* /usr/share/app/html/
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/nginx.conf
