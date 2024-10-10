FROM node:18.19.1

WORKDIR /opt/app

ENV PATH /opt/app/node_modules/.bin:$PATH

RUN apt-get update
RUN apt-get -y install mc

RUN npm install -g @angular/cli@18

EXPOSE 4200

CMD /bin/bash
