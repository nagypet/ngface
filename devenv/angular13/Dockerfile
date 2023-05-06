FROM node:14

WORKDIR /opt/app

ENV PATH /opt/app/node_modules/.bin:$PATH

RUN apt-get update
RUN apt-get -y install mc

RUN npm install -g @angular/cli@13.3.11

EXPOSE 4200

CMD /bin/bash
