#!/usr/bin/env bash
set -euo pipefail

VERSION=${1:-"2025-latest"}
docker pull mcr.microsoft.com/mssql/server:"$VERSION"

printf '\n\nStarting MS SQL Server %s container, this could take a while...\n'"$VERSION"
# start the dockerized ms sql instance (the container will be destroyed/removed on stopping)
# this container can be stopped using: docker stop sql1
docker run -v "$(pwd)"/:/checkout -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=Password12!' --rm -p 1433:1433 --name sql1 -h sql1 -d mcr.microsoft.com/mssql/server:"$VERSION"

printf '\nWaiting for MS SQL Server %s database to start up.... '"$VERSION"
_WAIT=0;
while :
do
    printf '%s'" $_WAIT"
    if eval "docker logs sql1 2>&1 | grep -q 'SQL Server is now ready for client connections'"; then
        printf "\nSQL Server is now ready for client connections\n\n"
        break
    fi

    sleep 10
    _WAIT=$(("$_WAIT"+10))
done

docker exec -i sql1 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Password12!' -No -d 'master' -Q 'CREATE DATABASE test'
docker exec -i sql1 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Password12!' -No -d 'test' -i /checkout/src/test/resources/sql/mssql.sql
