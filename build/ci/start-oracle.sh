#!/usr/bin/env bash
# Oracle XE 18.0.0.0
# this docker image has the following users/credentials (user/password = system/oracle)
docker pull imnotjames/oracle-xe:18c

# start the dockerized oracle-xe instance
# this container can be stopped using:
#
#    docker stop schemaspy
#
docker run --rm -p 1521:1521 --cpus=2 --name schemaspy -h schemaspy -d imnotjames/oracle-xe:18c

printf "\n\nStarting Oracle XE container, this could take a few minutes..."
printf "\nWaiting for Oracle XE database to start up.... "
_WAIT=0;
while :
do
    printf " $_WAIT"
    if $(docker logs schemaspy | grep -q 'DATABASE IS READY TO USE!'); then
        printf "\nOracle XE Database started\n\n"
        break
    fi
    if ((_WAIT > 150)); then
      printf "\nWaited >150 seconds for Oracle XE Database to start\n\n"
      break
    fi
    sleep 10
    _WAIT=$(($_WAIT+10))
done

# docker ps -a
# print logs
#docker logs schemaspy
