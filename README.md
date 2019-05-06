## Docker

```
docker build . -t akka

docker run -it -p 8080:8080 --volume="$(pwd):/root/src" akka /bin/bash
```


## ToDo

* Implement JSON and MongoDB
* Think about which Parts of the model layer or the whole model layer can be here


## Network

* When running akka in docker container dont use localhost because of the nating! -> 0.0.0.0
