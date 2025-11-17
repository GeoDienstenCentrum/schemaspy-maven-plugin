#!/usr/bin/env bash
# Oracle FREE
docker pull ghcr.io/gvenzl/oracle-free:"$1"

# start the dockerized oracle-free instance
# this container can be stopped/removed using:
#
#    docker stop schemaspy
#
# this container has the following admin user/credentials (user/password = system/oracle)
docker run --rm -p 1521:1521 --name schemaspy -h schemaspy -e ORACLE_PASSWORD=oracle -d ghcr.io/gvenzl/oracle-free:"$1"

printf "\n\nStarting Oracle FREE container, this could take a few minutes..."
printf "\nWaiting for Oracle FREE database to start up.... "
_WAIT=0;
while :
do
    printf ' %s'" $_WAIT"
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
