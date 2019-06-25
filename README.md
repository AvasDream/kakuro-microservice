## Credentials for docker

couchdb admin/hallowelt

## Docker

```
docker build . -t akka

docker run -it -p 8080:8080 --volume="$(pwd):/root/src" akka /bin/bash
```
## Docker Compose

```
docker-compose up

# Stop
docker-compose stop

# Stop & Delete
docker-compose down
```


## ToDo

* MongoDb save kakuro POST
* MongoDB persist default field
* MongoDb get field
* Implement JSON and MongoDB
* Think about which Parts of the model layer or the whole model layer can be here
* Docker compose for container startup [Documentation](https://docs.docker.com/compose/compose-file/)

## Network

* When running akka in docker container dont use localhost because of the nating! -> 0.0.0.0

## Database Mongodb

* Buggy and no fixes in github, workaround delete all layers of image and pull again, then the first time it works :'( 
* Github bugs say it is a problem with the filesystem implementation in windows_x64 so no easy fix fml

`docker run -p 27017:27017  --volume "$(pwd)/mongodb/data:/data/" -d  own_mongo`

delete all docker images in Powershell

`docker ps -aq | foreach {docker rm $_}`

## Database Couchdb

Run couchdb in daemon mode and expose port 5984

```
docker run -p 5984:5984 -d couchdb

# Run with persistent storage

`docker run -p 5984:5984 -v "$(pwd)/couchdb/data:/opt/couchdb/data" -d couch`

`docker container top <Container>`


```

Database is completly saved inside of the /database/data directory. BUT the whole database has only a size of 77kB containing 2 
Fields. So in my humble opinion this is reasonable for this project and ease of use.

Connect to container flouton at 

[CouchDb-Fouton](http://127.0.0.1:5984/_utils/)

[Docker couchdb](https://hub.docker.com/_/couchdb)

[couchdb-scala library](https://github.com/beloglazov/couchdb-scala)


## Librarys

[Mongodb](https://mongodb.github.io/mongo-scala-driver/2.6/getting-started/quick-tour-primer/)


## Remember

* Docker has a Server-Client Architecture. So if you have tight iptables rules which block everything you cant share files via volumes! Totally worth investigating for 2.

* Access Docker Host from Docker Container with local Docker Host IP [Fuck_my_life.jpg](https://nickjanetakis.com/blog/docker-tip-35-connect-to-a-database-running-on-your-docker-host)