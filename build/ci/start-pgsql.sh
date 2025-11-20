#!/usr/bin/env bash
set -euo pipefail

VERSION=${1:-"18-3.6"}
docker pull postgis/postgis:"$VERSION"

# this container can be stopped using: docker stop pgsql1
docker run --rm -p 5432:5432 -e POSTGRES_PASSWORD=test -e POSTGRES_USER=test -e POSTGRES_DB=test --name pgsql1 -h pgsql1 -d postgis/postgis:"$VERSION"

printf '\n\nStarting PostGIS %s container, this could take a few seconds...'"$VERSION"
printf '\nWaiting for PostGIS %s database to start up.... '"$VERSION"
_WAIT=0;
while :
do
    printf ' %s'"$_WAIT"
     if eval "docker logs pgsql1 | grep -q 'database system is ready to accept connections'"; then
        printf '\nPostGIS %s Database started\n\n'"$VERSION"
        break
    fi
    sleep 10
    _WAIT=$(("$_WAIT"+10))
done

POSTGRES_PASSWORD=test docker exec -u postgres -i pgsql1 psql --username test --dbname test < ./src/test/resources/sql/pgsql.sql