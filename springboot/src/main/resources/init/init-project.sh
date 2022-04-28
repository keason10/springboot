#!/bin/bash

#安装mysql
rm -rf data
docker stop mysqllocal
docker rm mysqllocal
mkdir data
docker run -v "$PWD/data":/var/lib/mysql --name mysqllocal -e MYSQL_ROOT_PASSWORD=root -p 33060:3306 -d mysql:5.7.37

sleep 20

docker exec -i mysqllocal sh -c 'exec mysql -uroot -proot' < `pwd`/init.sql


docker stop redislocal
docker rm redislocal
docker run -itd --name redislocal -p 63790:6379 redis:7.0.0

