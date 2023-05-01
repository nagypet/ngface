set MY_PATH=c:\np\github\ngface\frontend\angular-wsp\
@rem docker run -it -v %cd%:/usr/src/project np/node /bin/bash
docker run --rm -it -p 4200:4200 --name nodejs -v %MY_PATH%:/project np/node /bin/bash