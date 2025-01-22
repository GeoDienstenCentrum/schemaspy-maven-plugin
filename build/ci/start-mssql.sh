#!/bin/bash -e

VERSION=${1:-"2022-latest"}

docker pull mcr.microsoft.com/mssql/server:$VERSION

printf "\n\nStarting MS SQL Server $VERSION container, this could take a while..."
# start the dockerized ms sql instance (the container will be destroyed/removed on stopping)
# this container can be stopped using: docker stop sql1
docker run -v "$(pwd)"/:/checkout -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=Password12!' --rm -p 1433:1433 --name sql1 -h sql1 -d mcr.microsoft.com/mssql/server:$VERSION

printf "\nWaiting for MS SQL Server $VERSION database to start up.... "
_WAIT=0;
while :
do
    printf " $_WAIT"
    if $(docker logs sql1 2>&1 | grep -q 'SQL Server is now ready for client connections'); then
        printf "\nSQL Server is now ready for client connections\n\n"
        break
    fi

    sleep 10
    _WAIT=$(($_WAIT+10))
done

