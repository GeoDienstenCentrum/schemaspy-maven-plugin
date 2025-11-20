#!/usr/bin/env bash
set -euo pipefail

VERSION=${1:-"9"}
docker pull mysql:"$VERSION"

printf '\n\nStarting MySQL %s container, this could take a while...'"$VERSION"
# start the dockerized mysql instance (the container will be destroyed/removed on stopping)
# this container can be stopped using: docker stop mysql
docker run --rm -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mysql -e MYSQL_DATABASE=test --name mysql1 -h mysql1 -d mysql:"$VERSION"

# the mysql docker images start a temporary server to init and then restart listening on 3306
printf '\nWaiting for MySQL %s database to start up.... '"$VERSION"
_WAIT=0;
while :
do
    printf '%s'" $_WAIT"
    # look for a line with: "mysqld: ready for connections."
    # followed by a line with "port: 3306 " (maybe on the same line)
    if eval "docker logs mysql1 2>&1 | grep -A2 'ready for connections' | grep -q 'port: 3306 '"; then
        printf "\nMySQL ready for connections\n\n"
        break
    fi

    sleep 10
    _WAIT=$(("$_WAIT"+10))
done

docker exec -i mysql1 mysql -uroot -pmysql test < src/test/resources/sql/mysql.sql
