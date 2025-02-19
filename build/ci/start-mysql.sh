#!/bin/bash -e

VERSION=${1:-"8"}
docker pull mysql:$VERSION

printf "\n\nStarting MySQL $VERSION container, this could take a while..."
# start the dockerized mysql instance (the container will be destroyed/removed on stopping)
# this container can be stopped using: docker stop mysql
docker run --rm -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mysql --name mysql1 -h mysql1 -d mysql:$VERSION

# the mysql docker images start a temporary server to init and then restart listening on 3306
printf "\nWaiting for MySQL $VERSION database to start up.... "
_WAIT=0;
while :
do
    printf " $_WAIT"
    # look for a line with: "mysqld: ready for connections."
    # followed by a line with "port: 3306 " (maybe on the same line)
    if $(docker logs mysql1 2>&1 | grep -A2 'ready for connections' | grep -q 'port: 3306 '); then
        printf "\nMySQL ready for connections\n\n"
        break
    fi

    sleep 10
    _WAIT=$(($_WAIT+10))
done

# print logs
# docker logs mysql1