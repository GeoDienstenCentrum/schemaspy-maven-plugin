#!/usr/bin/env bash
set -euo pipefail

VERSION=${1:-"23.26.0-slim"}

docker pull ghcr.io/gvenzl/oracle-free:"$VERSION"
# start the dockerized oracle-free instance
# this container can be stopped/removed using:
#
#    docker stop schemaspy
#
docker run --rm -p 1521:1521 --name schemaspy -h schemaspy -e ORACLE_PASSWORD=oracle -e APP_USER=schemaspy -e APP_USER_PASSWORD=schemaspy -d ghcr.io/gvenzl/oracle-free:"$VERSION"

printf '\n\nStarting Oracle FREE %s container, this could take a few minutes...'"$VERSION"
printf '\nWaiting for Oracle FREE database to start up.... '
_WAIT=0;
while :
do
    printf ' %s'"$_WAIT"
    if eval "docker logs schemaspy | grep -q 'DATABASE IS READY TO USE!'"; then
        printf "\nOracle FREE Database started\n\n"
        break
    fi
    if ((_WAIT > 150)); then
      printf "\nWaited >150 seconds for Oracle FREE Database to start\n\n"
      break
    fi
    sleep 10
    _WAIT=$(("$_WAIT"+10))
done

docker exec -i schemaspy sqlplus -l schemaspy/schemaspy@//localhost:1521/FREEPDB1 < src/test/resources/sql/oracle.sql
