#!/usr/bin/env bash
set -e
docker version

docker pull postgis/postgis:$1

# this container can be stopped using: docker stop pgsql1
docker run --rm -p 5432:5432 -e POSTGRES_PASSWORD=postgres --name pgsql1 -h pgsql1 -d postgis/postgis:$1 -c autovacuum_max_workers=4 -c maintenance_work_mem=2GB

printf "\n\nStarting PostGIS $1 container, this could take a few seconds..."
printf "\nWaiting for PostGIS $1 database to start up.... "
_WAIT=0;
while :
do
    printf " $_WAIT"
    if $(docker logs pgsql1 | grep -q 'database system is ready to accept connections'); then
        printf "\PostGIS $1 Database started\n\n"
        break
    fi
    sleep 10
    _WAIT=$(($_WAIT+10))
done