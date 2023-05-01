#!/bin/bash
MY_PATH=/home/np/github/ngface/frontend/angular-wsp
docker run -it --rm -p 4200:4200 --name angular-devenv-11 -v $MY_PATH:/app/angular-wsp np/node-angular-11
