#!/bin/bash
docker run -it --rm -v $(pwd):/opt/app np/node-angular-16 /opt/app/build.sh
docker build -t docker-registry:5000/ngface-app:latest .
docker push docker-registry:5000/ngface-app:latest
