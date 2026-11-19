#!/usr/bin/env bash
docker stop bb_db | true
docker rm bb_db | true
docker run --name bb_db --rm -d -p 3306:3306 -e MYSQL_DATABASE=build_bruh -e MYSQL_USER=build_bruh -e MYSQL_PASSWORD=build_bruh -e MYSQL_ROOT_PASSWORD=build_bruh mariadb:10.10
