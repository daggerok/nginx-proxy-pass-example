# nginx (mode proxy_pass) edge gateway [![ CI ](https://github.com/daggerok/nginx-proxy-pass-gateway-example/workflows/%20CI%20/badge.svg)](https://github.com/daggerok/nginx-proxy-pass-gateway-example/actions?query=workflow%3A%22+CI+%22)
Yet another nginx reverse proxy gateway example

## build backing-services

_after 2.3.x_

```bash
./mvnw -f backing-services clean package spring-boot:build-image
```

see https://spring.io/blog/2020/01/27/creating-docker-images-with-spring-boot-2-3-0-m1

_before 2.3.x_

```bash
./mvnw -f backing-services clean package com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=backing-services:0.0.1-SNAPSHOT
```

## build nginx-proxy-pass-gateway

```bash
docker build -t nginx-proxy-pass-gateway:0.0.1-SNAPSHOT nginx-proxy-pass-gateway
```

## run all in docker and expose app behind nginx reverse-proxy

```bash
docker network rm     apps-dot-net || echo ''
docker network create apps-dot-net

docker run -d --rm --net apps-dot-net --name backing-services backing-services:0.0.1-SNAPSHOT
docker run -d --rm --net apps-dot-net -p 80:80 nginx-proxy-pass-gateway:0.0.1-SNAPSHOT
``` 

## test

just open http://127.0.0.1/

<!--
docker rm -f -v `docker ps -aq`
-->

## resources

* https://serveradmin.ru/nginx-proxy_pass/
