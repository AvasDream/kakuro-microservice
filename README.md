## Docker

```
docker build . -t akka

docker run -it -p 8080:8080 --volume="$(pwd):/root/src" akka /bin/bash
```


## ToDo

* Implement JSON and MongoDB
* Think about which Parts of the model layer or the whole model layer can be here
* Docker compose for container startup [Documentation](https://docs.docker.com/compose/compose-file/)

## Network

* When running akka in docker container dont use localhost because of the nating! -> 0.0.0.0


## Database

Run couchdb in daemon mode and expose port 5984

```
docker run -p 5984:5984 -d couchdb

# Run with persistent storage
docker run -p 5984:5984 -v "$(pwd)/database/data:/opt/couchdb/data" -d couch

docker container top <Container>
```

Database is completly saved inside of the /database/data directory. BUT the whole database has only a size of 77kB containing 2 
Fields. So in my humble opinion this is reasonable for this project and ease of use.

Connect to container flouton at 

[CouchDb-Fouton](http://127.0.0.1:5984/_utils/)

[Docker couchdb](https://hub.docker.com/_/couchdb)

[couchdb-scala library](https://github.com/beloglazov/couchdb-scala)