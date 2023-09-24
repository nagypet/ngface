#!/bin/bash
docker run -it --rm -v $(pwd):/opt/app np/node-angular-14 /opt/app/build.sh
docker build -t docker-registry:5000/ngface-app:latest .
