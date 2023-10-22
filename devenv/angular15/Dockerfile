FROM node:18.12.0

WORKDIR /opt/app

ENV PATH /opt/app/node_modules/.bin:$PATH

RUN apt-get update
RUN apt-get -y install mc

RUN npm install -g @angular/cli@15.0.3

EXPOSE 4200

CMD /bin/bash
